package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.entity.entity.ItemProjectileEntity;
import earth.terrarium.pastel.registries.PastelItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public record DefaultProjectileBehavior() implements ItemProjectileBehavior {
    public static final DefaultProjectileBehavior INSTANCE = new DefaultProjectileBehavior();

    public static final ProjectileBehaviorType<DefaultProjectileBehavior> TYPE = new ProjectileBehaviorType<>(
        PastelCommon.ofPastel("default"),
        MapCodec.unit(INSTANCE)
    );

    @Override
    public ItemStack onEntityHit(
        ItemProjectileEntity projectile,
        ItemStack stack,
        Entity owner,
        EntityHitResult hitResult
    ) {
        Entity target = hitResult.getEntity();

        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return stack;
        }

        // Lots of fun(tm) is to be had
        if (stack.is(ItemTags.CREEPER_IGNITERS) && target instanceof Creeper creeperEntity) {
            Level world = projectile.level();
            SoundEvent soundEvent = stack.is(Items.FIRE_CHARGE)
                ? SoundEvents.FIRECHARGE_USE
                : SoundEvents.FLINTANDSTEEL_USE;
            world
                .playSound(
                    null,
                    projectile.getX(),
                    projectile.getY(),
                    projectile.getZ(),
                    soundEvent,
                    projectile.getSoundSource(),
                    1.0F,
                    world
                        .getRandom()
                        .nextFloat() * 0.4F + 0.8F
                );
            creeperEntity.ignite();

            if (stack.isDamageableItem() && world instanceof ServerLevel serverWorld) {
                stack
                    .hurtAndBreak(
                        1,
                        serverWorld,
                        null,
                        item -> {
                        }
                    );
            } else if (!stack.has(DataComponents.UNBREAKABLE)) {
                stack
                    .shrink(
                        1
                    ); // In Vanilla unbreakable Flint & Steel is not handled correctly and therefore consumed. This
                // here probably still does not handle every modded item perfectly
            }
        }

        if (target instanceof LivingEntity livingTarget) {
            if (target instanceof Player && !PastelCommon.CONFIG.OmniAcceleratorPvP) {
                if (stack.is(PastelItemTags.REQUIRES_OMNI_ACCELERATOR_PVP_ENABLED)) {
                    return stack;
                }
            }

            // attaching name tags, saddle horses, memorize entities...
            if (owner instanceof Player playerOwner && stack
                .interactLivingEntity(
                    playerOwner,
                    livingTarget,
                    InteractionHand.MAIN_HAND
                )
                .consumesAction()) {
                return stack;
            }

            // Force-feeds food, applies potions, ...
            stack
                .getItem()
                .finishUsingItem(stack, livingTarget.level(), livingTarget);
        }
        return stack;
    }

    @Override
    public ItemStack onBlockHit(
        ItemProjectileEntity projectile,
        ItemStack stack,
        Entity owner,
        BlockHitResult hitResult
    ) {
        Level world = projectile.level();
        BlockPos hitPos = hitResult.getBlockPos();

        hitResult.withDirection(hitResult.getDirection());
        Direction facing = hitResult
            .getDirection()
            .getOpposite();
        BlockPos placementPos = hitPos.relative(facing.getOpposite());
        Direction placementDirection = world.isEmptyBlock(placementPos.below()) ? facing : Direction.UP;

        if (!GenericClaimModsCompat.canPlaceBlock(world, placementPos, owner)) {
            return stack;
        }

        try {
            if (stack.getItem() instanceof BlockItem blockItem) {
                blockItem.place(new ItemProjectilePlacementContext(world, projectile, hitResult));
            } else {
                stack.useOn(new DirectionalPlaceContext(world, placementPos, facing, stack, placementDirection));
            }
        } catch (Exception e) {
            PastelCommon
                .logError(
                    "Item Projectile failed to use item " + stack
                        .getItem()
                        .getDescription()
                );
            e.printStackTrace();
        }

        return stack;
    }

    @Override
    public ProjectileBehaviorType<?> type() {
        return null;
    }
}
