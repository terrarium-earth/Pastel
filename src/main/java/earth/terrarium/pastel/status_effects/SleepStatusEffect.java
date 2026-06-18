package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import earth.terrarium.pastel.registries.PastelEntityTypeTags;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCure;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SleepStatusEffect extends MobEffect {

    private final boolean scales;

    public SleepStatusEffect(MobEffectCategory category, int color, boolean scales) {
        super(category, color);
        this.scales = scales;
    }

    // oh my god
    // TODO: can the tag check be implemented into the entities base attribute modifier somehow?
    public static float getSleepResistance(@Nullable MobEffectInstance sleepEffect, LivingEntity entity) {

        var type = entity.getType();

        if (sleepEffect == null || type.is(PastelEntityTypeTags.SOULLESS))
            return Float.MAX_VALUE;

        float scaling;
        if (entity instanceof Player player && player
            .level()
            .isClientSide()) {
            scaling = (float) MiscPlayerData
                .get(player)
                .getLastSyncedSleepPotency();
        } else {
            scaling = (float) entity.getAttributeValue(PastelEntityAttributes.MENTAL_PRESENCE);
        }

        if (type.is(PastelEntityTypeTags.SLEEP_WEAK)) {
            scaling /= 3F;
        } else if (type.is(PastelEntityTypeTags.SLEEP_RESISTANT)) {
            scaling *= 2.0F;
        } else if (isResistedBy(entity)) {
            scaling *= 10F;
        }

        return scaling;
    }

    // TODO: can the tag check be implemented into the entities base attribute modifier somehow?
    public static boolean isResistedBy(LivingEntity entity) {
        if (entity.hasEffect(PastelMobEffects.FRENZY))
            return true;

        var type = entity.getType();
        if (type.is(PastelEntityTypeTags.SLEEP_WEAK))
            return false;

        return type.is(PastelEntityTypeTags.SLEEP_IMMUNEISH) || isConstruct(type);
    }

    /**
     * @return -1 = false
     */
    public static float getGeneralSleepResistanceIfEntityHasSoporificEffect(LivingEntity entity) {
        if (!isConstruct(entity.getType()) && PastelMobEffectTags
            .hasEffectWithTag(
                entity,
                PastelMobEffectTags.SOPORIFIC
            )) {
            return getSleepResistance(entity.getEffect(getStrongestSleepEffect(entity)), entity);
        }
        return -1F;
    }

    /**
     * @return -1 = false
     */
    public static float getSleepScaling(LivingEntity entity) {
        if (entity == null) return -1;
        var potency = getGeneralSleepResistanceIfEntityHasSoporificEffect(entity);

        if (potency == -1 || potency >= 1)
            return -1;

        // Converts a range of [0, infinity] to [0, 2]
        // Also accounts for a smaller resist meaning stronger sleep
        return 2 * (float) Math.pow(1 - potency, 2);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        var holder = effectInstance.getEffect();

        if (holder.equals(PastelMobEffects.SOMNOLENCE) || holder.equals(PastelMobEffects.CALMING))
            cures.add(PastelMobEffects.Cures.SEDATIVES);

        cures.add(PastelMobEffects.Cures.BLOOD_ORCHID);
    }

    private static boolean isConstruct(EntityType<?> type) {
        return type.is(PastelEntityTypeTags.SOULLESS);
    }

    public static @Nullable Holder<MobEffect> getStrongestSleepEffect(LivingEntity entity) {
        if (entity.hasEffect(PastelMobEffects.FATAL_SLUMBER)) {
            return PastelMobEffects.FATAL_SLUMBER;
        } else if (entity.hasEffect(PastelMobEffects.ETERNAL_SLUMBER)) {
            return PastelMobEffects.ETERNAL_SLUMBER;
        } else if (entity.hasEffect(PastelMobEffects.SOMNOLENCE)) {
            return PastelMobEffects.SOMNOLENCE;
        }
        return null;
    }

    // Sleep effects don't scale except for uh, calming ufck
    //TODO verify that you can't more than one level of eternal or fatal slumber
//    @Override
//    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier) {
//        if (scales)
//            return super.adjustModifierAmount(amplifier, modifier);
//        return modifier.getValue();
//    }
}
