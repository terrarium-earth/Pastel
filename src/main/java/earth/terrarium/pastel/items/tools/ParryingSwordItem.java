package earth.terrarium.pastel.items.tools;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ParryingSwordItem extends SwordItem implements ExtendedItemBar {

    public static final int DEFAULT_MAX_BLOCK_TIME = 40;
    public static final int DEFAULT_PERFECT_PARRY_WINDOW = 5;

    public ParryingSwordItem(
        Tier material, int attackDamage, float attackSpeed, float crit, float reach, Properties settings) {
        super(
            material, settings.attributes(ItemAttributeModifiers.builder()
                                                                .add(
                                                                    Attributes.ATTACK_DAMAGE,
                                                                    new AttributeModifier(
                                                                        BASE_ATTACK_DAMAGE_ID,
                                                                        material.getAttackDamageBonus() +
                                                                        attackDamage,
                                                                        AttributeModifier.Operation.ADD_VALUE
                                                                    ), EquipmentSlotGroup.MAINHAND
                                                                )
                                                                .add(
                                                                    Attributes.ATTACK_SPEED,
                                                                    new AttributeModifier(
                                                                        BASE_ATTACK_SPEED_ID,
                                                                        attackSpeed,
                                                                        AttributeModifier.Operation.ADD_VALUE
                                                                    ), EquipmentSlotGroup.MAINHAND
                                                                )
                                                                .add(
                                                                    AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE,
                                                                    new AttributeModifier(
                                                                        PastelEntityAttributes.CRIT_MODIFIER_ID, crit,
                                                                        AttributeModifier.Operation.ADD_VALUE
                                                                    ), EquipmentSlotGroup.MAINHAND
                                                                )
                                                                .add(
                                                                    Attributes.ENTITY_INTERACTION_RANGE,
                                                                    new AttributeModifier(
                                                                        PastelEntityAttributes.REACH_MODIFIER_ID, reach,
                                                                        AttributeModifier.Operation.ADD_VALUE
                                                                    ), EquipmentSlotGroup.MAINHAND
                                                                )
                                                                .build())
        );
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        super.releaseUsing(stack, world, user, remainingUseTicks);
        var usedTime = getMaxShieldingTime(user, stack) - remainingUseTicks;

        if (!(user instanceof Player player))
            return;

        cooldownAndDamage(stack, player, usedTime);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        releaseUsing(stack, world, user, 0);
        return stack;
    }

    private void cooldownAndDamage(ItemStack stack, Player player, int usedTime) {
        if (usedTime > 1) {
            player.getCooldowns()
                  .addCooldown(this, Math.max(usedTime, 10));
        }
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, user, hand);
    }

    public abstract float getBlockingMultiplier(
        DamageSource source, ItemStack stack, LivingEntity entity, int usedTime);

    public boolean canPerfectParry(ItemStack stack, LivingEntity entity, int usedTime) {
        return usedTime <= getPerfectParryWindow(entity, stack);
    }

    public boolean canBluffParry(ItemStack stack, LivingEntity entity, int usedTime) {
        return usedTime <= getPerfectParryWindow(entity, stack) * 2;
    }

    public boolean canDeflect(DamageSource source, boolean perfect) {
        if (source.is(DamageTypeTags.NO_IMPACT) || source.is(DamageTypeTags.BYPASSES_ARMOR))
            return false;

        if (source.is(DamageTypeTags.BYPASSES_SHIELD)) {
            return perfect;
        }

        return true;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return DEFAULT_MAX_BLOCK_TIME;
    }

    public int getMaxShieldingTime(LivingEntity user, ItemStack stack) {
        return getUseDuration(stack, user);
    }

    @SuppressWarnings("unused")
    public int getPerfectParryWindow(LivingEntity user, ItemStack stack) {
        return DEFAULT_PERFECT_PARRY_WINDOW;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int barCount(ItemStack stack) {
        return 1;
    }

    protected abstract int getBarColor();

    @Override
    public BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
        if (player == null || !player.isUsingItem())
            return ExtendedItemBar.PASS;

        var activeStack = player.getItemInHand(player.getUsedItemHand());
        if (activeStack != stack)
            return ExtendedItemBar.PASS;


        var progress = Math.round(
            Mth.clampedLerp(13, 0, ((float) player.getTicksUsingItem() / getMaxShieldingTime(player, stack))));
        return new BarSignature(2, 13, 13, progress, 1, getBarColor(), 2, ExtendedItemBar.DEFAULT_BACKGROUND_COLOR);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return super.canPerformAction(stack, itemAbility) || itemAbility == ItemAbilities.SHIELD_BLOCK;
    }
}
