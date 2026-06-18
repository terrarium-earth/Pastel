package earth.terrarium.pastel.items.magic_items;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.PrioritizedEntityInteraction;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.effect.ColoredExplosionParticleEffect;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelEntityTypeTags;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class StaffOfRemembranceItem extends Item implements InkPowered, PrioritizedEntityInteraction {

    public static final InkColor USED_COLOR = InkColors.LIGHT_GRAY;

    public static final InkCost TURN_NEUTRAL_TO_MEMORY_COST = new InkCost(USED_COLOR, 1000);

    public static final InkCost TURN_HOSTILE_TO_MEMORY_COST = new InkCost(USED_COLOR, 10000);

    public StaffOfRemembranceItem(Properties settings) {
        super(settings);
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        tooltip
            .add(
                Component
                    .translatable("item.pastel.staff_of_remembrance.tooltip")
                    .withStyle(ChatFormatting.GRAY)
            );
        addInkPoweredTooltip(tooltip);
    }

    @Override
    public InteractionResult interactLivingEntity(
        ItemStack stack,
        Player user,
        LivingEntity entity,
        InteractionHand hand
    ) {
        Level world = user.level();
        Vec3 pos = entity.position();

        if (!GenericClaimModsCompat.canInteract(world, entity, user)) {
            return InteractionResult.FAIL;
        }

        if (!world.isClientSide && entity instanceof Mob mobEntity) {
            if (turnEntityToMemory(user, mobEntity)) {
                PlayParticleWithRandomOffsetAndVelocityPayload
                    .playParticleWithRandomOffsetAndVelocity(
                        (ServerLevel) world,
                        entity.position(),
                        ColoredSparkleRisingParticleEffect.LIGHT_GRAY,
                        10,
                        Vec3.ZERO,
                        new Vec3(0.2, 0.2, 0.2)
                    );
                PlayParticleWithExactVelocityPayload
                    .playParticleWithExactVelocity(
                        (ServerLevel) world,
                        entity.position(),
                        ColoredExplosionParticleEffect.LIGHT_GRAY,
                        1,
                        Vec3.ZERO
                    );
                world
                    .playSound(
                        null,
                        pos.x(),
                        pos.y(),
                        pos.z(),
                        PastelSounds.RADIANCE_STAFF_PLACE,
                        SoundSource.PLAYERS,
                        1.0F,
                        0.8F + world.random.nextFloat() * 0.4F
                    );
            } else {
                world
                    .playSound(
                        null,
                        pos.x(),
                        pos.y(),
                        pos.z(),
                        PastelSounds.USE_FAIL,
                        SoundSource.PLAYERS,
                        1.0F,
                        0.8F + world.random.nextFloat() * 0.4F
                    );
            }
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    private boolean turnEntityToMemory(Player user, Mob entity) {
        if (!entity.isAlive() || entity.isRemoved() || entity.isVehicle()) {
            return false;
        }
        if (entity
            .getType()
            .is(PastelEntityTypeTags.STAFF_OF_REMEMBRANCE_BLACKLISTED)) {
            return false;
        }

        MobCategory spawnGroup = entity
            .getType()
            .getCategory();
        if (spawnGroup == MobCategory.MONSTER && (user.isCreative() || DatabankUtils
            .hasAdvancement(
                user,
                PastelAdvancements.Milestones.UNLOCK_HOSTILE_MEMORIZING
            ))) {
            if (!InkPowered.tryDrainEnergy(user, TURN_HOSTILE_TO_MEMORY_COST)) {
                return false;
            }
        } else if (!InkPowered.tryDrainEnergy(user, TURN_NEUTRAL_TO_MEMORY_COST)) {
            return false;
        }

        entity.dropLeash(true, true);
        entity.playAmbientSound();
        entity.spawnAnim();

        ItemStack memoryStack = MemoryItem.getMemoryForEntity(entity);
        MemoryItem.setTicksToManifest(memoryStack, 1);
        MemoryItem.setSpawnAsAdult(memoryStack, true);

        Vec3 entityPos = entity.position();
        ItemEntity itemEntity = new ItemEntity(
            entity.level(),
            entityPos.x(),
            entityPos.y(),
            entityPos.z(),
            memoryStack
        );
        itemEntity.setDeltaMovement(new Vec3(0.0, 0.15, 0.0));
        entity
            .level()
            .addFreshEntity(itemEntity);
        entity.remove(Entity.RemovalReason.DISCARDED);

        return true;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(USED_COLOR);
    }

}
