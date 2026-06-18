package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.ActivatableItem;
import earth.terrarium.pastel.api.item.ArmorPiercingHandler;
import earth.terrarium.pastel.api.item.Preenchanted;
import earth.terrarium.pastel.api.item.TooltipExtensions;
import earth.terrarium.pastel.entity.entity.BidentBaseEntity;
import earth.terrarium.pastel.entity.entity.BidentEntity;
import earth.terrarium.pastel.entity.entity.BidentMirrorImageEntity;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelSounds;
import earth.terrarium.pastel.registries.PastelToolMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class MalachiteBidentItem extends TridentItem implements Preenchanted, TooltipExtensions, ArmorPiercingHandler {

    private final float armorPierce, protPierce;

    public MalachiteBidentItem(
        Item.Properties settings,
        double attackSpeed,
        double damage,
        float armorPierce,
        float protPierce
    ) {
        super(
            settings
                .attributes(
                    ItemAttributeModifiers
                        .builder()
                        .add(
                            Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID,
                                damage,
                                AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.MAINHAND
                        )
                        .add(
                            Attributes.ATTACK_SPEED,
                            new AttributeModifier(
                                BASE_ATTACK_SPEED_ID,
                                attackSpeed,
                                AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.MAINHAND
                        )
                        .build()
                )
        );
        this.armorPierce = armorPierce;
        this.protPierce = protPierce;
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
        return Map.of(Enchantments.IMPALING, 6);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack handStack = user.getItemInHand(hand);
        if (handStack.getDamageValue() >= handStack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(handStack);
        }
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(handStack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player player) {
            int useTime = this.getUseDuration(stack, user) - remainingUseTicks;
            if (useTime >= 10) {
                player.awardStat(Stats.ITEM_USED.get(this));

                if (canStartRiptide(player, stack)) {
                    riptide(world, player, stack, getRiptideLevel(world.registryAccess(), stack));
                } else if (!world.isClientSide) {
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(user.getUsedItemHand()));
                    throwBident(stack, (ServerLevel) world, player);
                }
            }
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return PastelToolMaterial.MALACHITE
            .getRepairIngredient()
            .test(ingredient) || super.isValidRepairItem(stack, ingredient);
    }

    public int getRiptideLevel(HolderLookup.Provider lookup, ItemStack stack) {
        return Ench.getLevel(lookup, Enchantments.RIPTIDE, stack);
    }

    protected void riptide(Level world, Player playerEntity, ItemStack stack, int riptideLevel) {
        yeetPlayer(playerEntity, (float) riptideLevel);
        playerEntity.startAutoSpinAttack(20, (float) playerEntity.getAttributeValue(Attributes.ATTACK_DAMAGE), stack);
        if (playerEntity.onGround()) {
            playerEntity.move(MoverType.SELF, new Vec3(0.0, 1.2, 0.0));
        }

        SoundEvent soundEvent;
        if (riptideLevel >= 3) {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_3.value();
        } else if (riptideLevel == 2) {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_2.value();
        } else {
            soundEvent = SoundEvents.TRIDENT_RIPTIDE_1.value();
        }

        world.playSound(null, playerEntity, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    protected void yeetPlayer(Player playerEntity, float riptideLevel) {
        float f = playerEntity.getYRot();
        float g = playerEntity.getXRot();
        float h = -Mth.sin(f * 0.017453292F) * Mth.cos(g * 0.017453292F);
        float k = -Mth.sin(g * 0.017453292F);
        float l = Mth.cos(f * 0.017453292F) * Mth.cos(g * 0.017453292F);
        float m = Mth.sqrt(h * h + k * k + l * l);
        float n = 3.0F * ((1.0F + riptideLevel) / 4.0F);
        h *= n / m;
        k *= n / m;
        l *= n / m;
        playerEntity.push(h, k, l);
    }

    protected void throwBident(ItemStack stack, ServerLevel world, Player playerEntity) {
        boolean mirrorImage = isThrownAsMirrorImage(stack, world, playerEntity);

        BidentBaseEntity bidentBaseEntity = mirrorImage ? new BidentMirrorImageEntity(world) : new BidentEntity(world);
        bidentBaseEntity.setPickupItemStack(stack);
        bidentBaseEntity.setOwner(playerEntity);
        bidentBaseEntity.absMoveTo(playerEntity.getX(), playerEntity.getEyeY() - 0.1, playerEntity.getZ());
        bidentBaseEntity
            .shootFromRotation(
                playerEntity,
                playerEntity.getXRot(),
                playerEntity.getYRot(),
                0.0F,
                getThrowSpeed(stack),
                1.0F
            );
        if (!mirrorImage && playerEntity.getAbilities().instabuild) {
            bidentBaseEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        world.addFreshEntity(bidentBaseEntity);
        var soundEvent = SoundEvents.TRIDENT_THROW.value();
        if (mirrorImage) {
            PlayParticleWithRandomOffsetAndVelocityPayload
                .playParticleWithRandomOffsetAndVelocity(
                    world,
                    bidentBaseEntity.position(),
                    PastelParticleTypes.MIRROR_IMAGE,
                    8,
                    Vec3.ZERO,
                    new Vec3(
                        0.2,
                        0.2,
                        0.2
                    )
                );
            bidentBaseEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            soundEvent = PastelSounds.BIDENT_MIRROR_IMAGE_THROWN;
        } else if (playerEntity.getAbilities().instabuild) {
            bidentBaseEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        world.playSound(null, playerEntity, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!playerEntity.getAbilities().instabuild && !mirrorImage) {
            playerEntity
                .getInventory()
                .removeItem(stack);
        }
    }

    public void markDisabled(ItemStack stack, boolean disabled) {
        ActivatableItem.setActivated(stack, !disabled);
    }

    public boolean isDisabled(ItemStack stack) {
        return !ActivatableItem.isActivated(stack);
    }

    public boolean canBeDisabled() {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        if (isDisabled(stack))
            tooltip
                .add(
                    Component
                        .translatable("item.pastel.bident.toolTip.disabled")
                        .withStyle(ChatFormatting.RED, ChatFormatting.ITALIC)
                );
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
        if (canBeDisabled() && clickType == ClickAction.SECONDARY) {
            markDisabled(stack, !isDisabled(stack));
            return true;
        }
        return false;
    }

    public float getThrowSpeed(ItemStack stack) {
        return 3F;
    }

    public boolean canStartRiptide(Player player, ItemStack stack) {
        return getRiptideLevel(
            player
                .level()
                .registryAccess(),
            stack
        ) > 0 && player.isInWaterOrRain();
    }

    public boolean isThrownAsMirrorImage(ItemStack stack, ServerLevel world, Player player) {
        return false;
    }

    @Override
    public float getDefenseMultiplier(LivingEntity target, ItemStack stack) {
        return 1 - armorPierce;
    }

    @Override
    public float getProtReduction(LivingEntity target, ItemStack stack) {
        return protPierce;
    }

    @Override
    public DamageComposition getDamageComposition(
        LivingEntity attacker,
        LivingEntity target,
        ItemStack stack,
        float damage
    ) {
        var composition = new DamageComposition();
        var source = composition.getPlayerOrEntity(attacker);
        composition.add(source, damage);
        return composition;
    }

    @Override
    public void expandTooltipPostStats(
        ItemStack stack,
        @Nullable Player player,
        List<Component> tooltip,
        TooltipContext context
    ) {
        if (Screen.hasShiftDown()) {
            tooltip
                .add(
                    Component
                        .translatable("item.pastel.bident.postToolTip.ap", armorPierce * 100)
                        .withStyle(ChatFormatting.DARK_GREEN)
                );

            if (protPierce > 0) {
                tooltip
                    .add(
                        Component
                            .translatable("item.pastel.bident.postToolTip.pp", protPierce * 100)
                            .withStyle(ChatFormatting.DARK_GREEN)
                    );
            }
            if (canBeDisabled()) {
                tooltip
                    .add(
                        Component
                            .translatable("item.pastel.bident.postToolTip.disable")
                            .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)
                    );
            }
        } else {
            tooltip
                .add(
                    Component
                        .translatable("pastel.tooltip.press_shift_for_more")
                        .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)
                );
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.SHARPNESS) || enchantment
            .is(Enchantments.SMITE) || enchantment.is(Enchantments.BANE_OF_ARTHROPODS) || enchantment
                .is(
                    Enchantments.LOOTING
                ) || enchantment.is(PastelEnchantments.CLOVERS_FAVOR);
    }

}
