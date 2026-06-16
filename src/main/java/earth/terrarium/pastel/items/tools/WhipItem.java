package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.EntityAttackAwareItem;
import earth.terrarium.pastel.api.item.EquipAwareItem;
import earth.terrarium.pastel.api.item.SplitDamageHandler;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.fml.util.thread.EffectiveSide;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WhipItem extends SwordItem implements ExtendedItemBar, EntityAttackAwareItem, EquipAwareItem,
    SplitDamageHandler {
    public static final ResourceLocation FERVOR_ATK_SPEED = PastelCommon.locate("fervor_atk_speed");
    protected final int maxFervor, fervorOnHit, minFervorForEffects;
    protected final float maxFervorSpeed;

    public WhipItem(
        Tier tier, int attackDamage, float attackSpeed, float reach, int maxFervor, int fervorOnHit,
        int minFervorForEffects, float maxFervorSpeed, Properties properties
    ) {
        super(tier, properties.attributes(ItemAttributeModifiers.builder()
                                                              .add(
                                                                  Attributes.ATTACK_DAMAGE,
                                                                  new AttributeModifier(
                                                                      BASE_ATTACK_DAMAGE_ID,
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
                                                                  Attributes.ENTITY_INTERACTION_RANGE,
                                                                  new AttributeModifier(
                                                                      PastelEntityAttributes.REACH_MODIFIER_ID, reach,
                                                                      AttributeModifier.Operation.ADD_VALUE
                                                                  ), EquipmentSlotGroup.MAINHAND
                                                              )
                                                              .build()));
        this.maxFervor = maxFervor;
        this.fervorOnHit = fervorOnHit;
        this.minFervorForEffects = minFervorForEffects;
        this.maxFervorSpeed = maxFervorSpeed;
    }

    @Override
    public AABB getSweepHitBox(ItemStack stack, Player player, Entity target) {
        return target.getBoundingBox()
                     .inflate(5.0, 0.25, 5.0); // it's a whip
    }

    @Override
    public void appendHoverText(
        ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if(EffectiveSide.get().isClient()) {
            tooltipComponents.add(Component.translatable("item.pastel.whip.tooltip"));
            tooltipComponents.add(Component.translatable("item.pastel.whip.tooltip1"));
            tooltipComponents.add(Component.translatable("item.pastel.whip.tooltip2"));
        }
    }

    @Override
    public int barCount(ItemStack stack) {
        return 1;
    }

    // we want the fervor debuff to apply to all enemies in one 'swing'. so, as soon as you try to attack again, your
    // fervor is cleared. this would look jank, except your fervor bar 'resets to 0' visually while you have negative
    // fervor! so it's entirely invisible to the player
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (stack.getOrDefault(PastelDataComponentTypes.FERVOR_RESET, false)) {
            stack.set(PastelDataComponentTypes.FERVOR, 0);
            stack.set(PastelDataComponentTypes.FERVOR_RESET, false);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);
        var fervor = stack
                           .getOrDefault(PastelDataComponentTypes.FERVOR, 0);
        if(stack.getOrDefault(PastelDataComponentTypes.FERVOR_RESET, false)) {
            stack.set(PastelDataComponentTypes.FERVOR, 0);
            stack.set(PastelDataComponentTypes.FERVOR_RESET, false);
        }
        if (fervor < 0) { // we're using it while we're already charged up and full of fervor; apply buffs to self
            stack
                  .set(PastelDataComponentTypes.FERVOR, 0);
            healOrBuff(player, player, -fervor);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        if (fervor < this.minFervorForEffects) return super.use(
            level, player, usedHand); // not negative fervor, not enough for overcharge; do nothing
        level.playSound(null, player.blockPosition(), PastelSounds.WHIP_FERVOR_EXPEND, SoundSource.PLAYERS, 1.0F, 1.0F);
        stack
              .set(PastelDataComponentTypes.FERVOR, -fervor); // this is how we handle 'consuming' devotion
        return InteractionResultHolder.consume(stack);
    }

    public void healOrBuff(Player user, LivingEntity target, int fervor) {
        user.level()
            .playSound(null, target.blockPosition(), PastelSounds.GLASS_SHIMMER, SoundSource.PLAYERS, 1.0F, 1.0F);
        float heal_mult = user.is(target) ? 0.5f : 1f; // half potency when using it on yourself
        target.heal(fervor * heal_mult); // pretty basic. override this for the fancy whip™
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Mth.floor(200 * heal_mult * ((float) fervor / maxFervor)))); // up to 5 seconds of speed to yourself, 10 seconds to another. as a treat
    }

    @Override
    public InteractionResult interactLivingEntity(
        ItemStack stack, Player player, LivingEntity interactionTarget,
        InteractionHand usedHand
    ) {
        if(stack.getOrDefault(PastelDataComponentTypes.FERVOR_RESET, false)) {
            stack.set(PastelDataComponentTypes.FERVOR, 0);
            stack.set(PastelDataComponentTypes.FERVOR_RESET, false);
        }
        var fervor = stack.getOrDefault(PastelDataComponentTypes.FERVOR, 0);
        if (fervor < 0) // we're "activated", correct the value to use
            fervor = -fervor;
        if (fervor != 0) {
            healOrBuff(player, interactionTarget, fervor);
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    @Override
    public @NotNull BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
        var fervor = stack.getOrDefault(PastelDataComponentTypes.FERVOR, 0);
        if(fervor == 0) return ExtendedItemBar.PASS;
        var progress = Math.round(
            Mth.clampedLerp(0, 13, (float) fervor / maxFervor));
        var vanillaBarRendered = stack.isDamageableItem() && stack.getOrDefault(DataComponents.DAMAGE, 0) != 0;
        return new BarSignature(2, vanillaBarRendered ? 11 : 13, 13, progress, 1, 0xFFDE3163, 2, ExtendedItemBar.DEFAULT_BACKGROUND_COLOR);
    }

//    @Override
//    public boolean allowVanillaDurabilityBarRendering(@Nullable Player player, ItemStack stack) {
//        return false;
//    }

    @Override
    public void onEntityDamage(
        ItemStack stack, LivingEntity target, LivingEntity attacker, DamageSource source,
        float amount
    ) {
        if (amount <= 0f) return;
        var fervor = stack.getOrDefault(PastelDataComponentTypes.FERVOR, 0);
        if (fervor < 0) {
            applyDebuffs(attacker, target, -fervor);
            stack.set(PastelDataComponentTypes.FERVOR_RESET, true);
        } else {
            var newFervor = Math.min(stack.getOrDefault(PastelDataComponentTypes.FERVOR, 0) + fervorOnHit, maxFervor);
            stack.set(PastelDataComponentTypes.FERVOR, newFervor);
            var atk_spd = attacker.getAttribute(Attributes.ATTACK_SPEED);
            if (atk_spd != null) {
                var mod = (float) newFervor / maxFervor * maxFervorSpeed;
                atk_spd.removeModifier(FERVOR_ATK_SPEED);
                atk_spd.addTransientModifier(
                    new AttributeModifier(FERVOR_ATK_SPEED, mod, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
            if(newFervor == maxFervor){
                attacker.level().playSound(null, attacker.blockPosition(), PastelSounds.WHIP_FERVOR_PEAK, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    protected void applyDebuffs(LivingEntity attacker, LivingEntity target, int fervorExpended) {
        return;
    }

    // somewhat counterintuitively, this also applies to main and offhand slots... so we just add and remove the
    // snapthorn buff here
    @Override
    public void onEquipChange(LivingEntity entity, ItemStack stack, EquipmentSlot slot, boolean unequip) {
        var atk_spd = entity.getAttribute(Attributes.ATTACK_SPEED);
        if (atk_spd != null) {
            if (unequip) atk_spd.removeModifier(FERVOR_ATK_SPEED);
            else {
                var mod = (float) stack.getOrDefault(PastelDataComponentTypes.FERVOR, 0) / maxFervor * maxFervorSpeed;
                if (mod > 0) {
                    atk_spd.removeModifier(FERVOR_ATK_SPEED);
                    atk_spd.addTransientModifier(
                        new AttributeModifier(FERVOR_ATK_SPEED, mod, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }
        }
    }

    @Override
    public DamageComposition getDamageComposition(
        LivingEntity attacker, LivingEntity target, ItemStack stack, float damage) {
        var composition = new DamageComposition();
        composition.add(PastelDamageTypes.lacerating(target.level(), attacker), damage);
        return composition;
    }
}
