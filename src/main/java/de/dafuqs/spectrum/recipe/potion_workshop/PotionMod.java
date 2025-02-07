package de.dafuqs.spectrum.recipe.potion_workshop;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.util.math.random.Random;

import java.util.*;
import java.util.stream.*;

public record PotionMod(
		int flatDurationBonusTicks,
		float flatPotencyBonus,
		float durationMultiplier,
		float potencyMultiplier,
		float flatPotencyBonusPositiveEffects,
		float flatPotencyBonusNegativeEffects,
		int flatDurationBonusPositiveEffects,
		int flatDurationBonusNegativeEffects,
		float additionalRandomPositiveEffectCount,
		float additionalRandomNegativeEffectCount,
		float chanceToAddLastEffect,
		float lastEffectDurationMultiplier,
		float lastEffectPotencyMultiplier,
		float yield,
		int additionalDrinkDurationTicks,
		PotionFlags flags
) {
	
	public static final Codec<PotionMod> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.optionalFieldOf("flat_duration_bonus_ticks", 0).forGetter(PotionMod::flatDurationBonusTicks),
			Codec.FLOAT.optionalFieldOf("flat_potency_bonus", 0f).forGetter(PotionMod::flatPotencyBonus),
			Codec.FLOAT.optionalFieldOf("duration_multiplier", 1f).forGetter(PotionMod::durationMultiplier),
			Codec.FLOAT.optionalFieldOf("potency_multiplier", 1f).forGetter(PotionMod::potencyMultiplier),
			Codec.FLOAT.optionalFieldOf("flat_potency_bonus_positive_effects", 0f).forGetter(PotionMod::flatPotencyBonusPositiveEffects),
			Codec.FLOAT.optionalFieldOf("flat_potency_bonus_negative_effects", 0f).forGetter(PotionMod::flatPotencyBonusNegativeEffects),
			Codec.INT.optionalFieldOf("flat_duration_bonus_ticks_positive_effects", 0).forGetter(PotionMod::flatDurationBonusPositiveEffects),
			Codec.INT.optionalFieldOf("flat_duration_bonus_ticks_negative_effects", 0).forGetter(PotionMod::flatDurationBonusNegativeEffects),
			Codec.FLOAT.optionalFieldOf("additional_random_positive_effect_count", 0f).forGetter(PotionMod::additionalRandomPositiveEffectCount),
			Codec.FLOAT.optionalFieldOf("additional_random_negative_effect_count", 0f).forGetter(PotionMod::additionalRandomNegativeEffectCount),
			Codec.FLOAT.optionalFieldOf("chance_to_add_last_effect", 0f).forGetter(PotionMod::chanceToAddLastEffect),
			Codec.FLOAT.optionalFieldOf("last_effect_duration_modifier", 1f).forGetter(PotionMod::lastEffectDurationMultiplier),
			Codec.FLOAT.optionalFieldOf("last_effect_potency_modifier", 1f).forGetter(PotionMod::lastEffectPotencyMultiplier),
			Codec.FLOAT.optionalFieldOf("flat_yield_bonus", 0f).forGetter(PotionMod::yield),
			Codec.INT.optionalFieldOf("additional_drink_duration_ticks", 0).forGetter(PotionMod::additionalDrinkDurationTicks),
			PotionFlags.CODEC.forGetter(PotionMod::flags)
	).apply(i, PotionMod::new));
	
	public static final PacketCodec<RegistryByteBuf, PotionMod> PACKET_CODEC = PacketCodecHelper.tuple(
			PacketCodecs.VAR_INT, PotionMod::flatDurationBonusTicks,
			PacketCodecs.FLOAT, PotionMod::flatPotencyBonus,
			PacketCodecs.FLOAT, PotionMod::durationMultiplier,
			PacketCodecs.FLOAT, PotionMod::potencyMultiplier,
			PacketCodecs.FLOAT, PotionMod::flatPotencyBonusPositiveEffects,
			PacketCodecs.FLOAT, PotionMod::flatPotencyBonusNegativeEffects,
			PacketCodecs.VAR_INT, PotionMod::flatDurationBonusPositiveEffects,
			PacketCodecs.VAR_INT, PotionMod::flatDurationBonusNegativeEffects,
			PacketCodecs.FLOAT, PotionMod::additionalRandomPositiveEffectCount,
			PacketCodecs.FLOAT, PotionMod::additionalRandomNegativeEffectCount,
			PacketCodecs.FLOAT, PotionMod::chanceToAddLastEffect,
			PacketCodecs.FLOAT, PotionMod::lastEffectDurationMultiplier,
			PacketCodecs.FLOAT, PotionMod::lastEffectPotencyMultiplier,
			PacketCodecs.FLOAT, PotionMod::yield,
			PacketCodecs.VAR_INT, PotionMod::additionalDrinkDurationTicks,
			PotionFlags.PACKET_CODEC, PotionMod::flags,
			PotionMod::new
	);
	
	public record PotionFlags(
			boolean makeSplashing,
			boolean makeLingering,
			boolean noParticles,
			boolean unidentifiable,
			boolean makeEffectsPositive,
			boolean potentDecreasingEffect,
			boolean negateDecreasingDuration,
			boolean randomColor,
			boolean incurable,
			List<Pair<PotionRecipeEffect, Float>> additionalEffects
	) {
		private static final Codec<Pair<PotionRecipeEffect, Float>> ENTRY_CODEC = RecordCodecBuilder.create(i -> i.group(
				PotionRecipeEffect.CODEC.forGetter(Pair::getLeft),
				Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(Pair::getRight)
		).apply(i, Pair::new));
		
		private static final PacketCodec<RegistryByteBuf, Pair<PotionRecipeEffect, Float>> ENTRY_PACKET_CODEC = PacketCodecHelper.pair(
				PotionRecipeEffect.PACKET_CODEC,
				PacketCodecs.FLOAT
		);
		
		public static final MapCodec<PotionFlags> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.BOOL.optionalFieldOf("make_splashing", false).forGetter(PotionFlags::makeSplashing),
				Codec.BOOL.optionalFieldOf("make_lingering", false).forGetter(PotionFlags::makeLingering),
				Codec.BOOL.optionalFieldOf("no_particles", false).forGetter(PotionFlags::noParticles),
				Codec.BOOL.optionalFieldOf("un_identifiable", false).forGetter(PotionFlags::unidentifiable),
				Codec.BOOL.optionalFieldOf("make_effects_positive", false).forGetter(PotionFlags::makeEffectsPositive),
				Codec.BOOL.optionalFieldOf("potent_decreasing_effect", false).forGetter(PotionFlags::potentDecreasingEffect),
				Codec.BOOL.optionalFieldOf("negate_decreasing_duration", false).forGetter(PotionFlags::negateDecreasingDuration),
				Codec.BOOL.optionalFieldOf("random_color", false).forGetter(PotionFlags::randomColor),
				Codec.BOOL.optionalFieldOf("incurable", false).forGetter(PotionFlags::incurable),
				ENTRY_CODEC.listOf().optionalFieldOf("additional_effects", List.of()).forGetter(PotionFlags::additionalEffects)
		).apply(i, PotionFlags::new));
		
		public static final PacketCodec<RegistryByteBuf, PotionFlags> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.BOOL, PotionFlags::makeSplashing,
				PacketCodecs.BOOL, PotionFlags::makeLingering,
				PacketCodecs.BOOL, PotionFlags::noParticles,
				PacketCodecs.BOOL, PotionFlags::unidentifiable,
				PacketCodecs.BOOL, PotionFlags::makeEffectsPositive,
				PacketCodecs.BOOL, PotionFlags::potentDecreasingEffect,
				PacketCodecs.BOOL, PotionFlags::negateDecreasingDuration,
				PacketCodecs.BOOL, PotionFlags::randomColor,
				PacketCodecs.BOOL, PotionFlags::incurable,
				ENTRY_PACKET_CODEC.collect(PacketCodecs.toList()), PotionFlags::additionalEffects,
				PotionFlags::new
		);
	}
	
	public int getColor(Random random) {
		return flags.randomColor ? java.awt.Color.getHSBColor(random.nextFloat(), 0.7F, 0.9F).getRGB() : flags.unidentifiable ? 0x2f2f2f : -1; // dark gray
	}
	
	public static class Builder {
		public int flatDurationBonusTicks = 0;
		public float flatPotencyBonus = 0.0F;
		public float durationMultiplier = 1.0F;
		public float potencyMultiplier = 1.0F;
		public float flatPotencyBonusPositiveEffects = 0.0F;
		public float flatPotencyBonusNegativeEffects = 0.0F;
		public int flatDurationBonusPositiveEffects = 0;
		public int flatDurationBonusNegativeEffects = 0;
		public float additionalRandomPositiveEffectCount = 0;
		public float additionalRandomNegativeEffectCount = 0;
		public float chanceToAddLastEffect = 0.0F;
		public float lastEffectDurationMultiplier = 1.0F;
		public float lastEffectPotencyMultiplier = 1.0F;
		public float yield = 0;
		public int additionalDrinkDurationTicks = 0;
		public PotionFlags flags = new PotionFlags(
				false, false, false, false, false,
				false, false, false, false, List.of()
		);
		;
		
		public Builder() {
		}
		
		public Builder(PotionMod potionMod) {
			combine(potionMod);
		}
		
		public Builder combine(PotionMod potionMod) {
			this.flatDurationBonusTicks += potionMod.flatDurationBonusTicks;
			this.flatPotencyBonus += potionMod.flatPotencyBonus;
			this.durationMultiplier += potionMod.durationMultiplier - 1;
			this.potencyMultiplier += potionMod.potencyMultiplier - 1;
			this.flatPotencyBonusPositiveEffects += potionMod.flatPotencyBonusPositiveEffects;
			this.flatPotencyBonusNegativeEffects += potionMod.flatPotencyBonusNegativeEffects;
			this.flatDurationBonusPositiveEffects += potionMod.flatDurationBonusPositiveEffects;
			this.flatDurationBonusNegativeEffects += potionMod.flatDurationBonusNegativeEffects;
			this.additionalRandomPositiveEffectCount += potionMod.additionalRandomPositiveEffectCount;
			this.additionalRandomNegativeEffectCount += potionMod.additionalRandomNegativeEffectCount;
			this.chanceToAddLastEffect += potionMod.chanceToAddLastEffect;
			this.lastEffectPotencyMultiplier += potionMod.lastEffectPotencyMultiplier - 1;
			this.lastEffectDurationMultiplier += potionMod.lastEffectDurationMultiplier - 1;
			this.yield += potionMod.yield;
			this.additionalDrinkDurationTicks += potionMod.additionalDrinkDurationTicks;
			this.flags = new PotionFlags(
					this.flags.makeSplashing | potionMod.flags.makeSplashing,
					this.flags.makeLingering | potionMod.flags.makeLingering,
					this.flags.noParticles | potionMod.flags.noParticles,
					this.flags.unidentifiable | potionMod.flags.unidentifiable,
					this.flags.makeEffectsPositive | potionMod.flags.makeEffectsPositive,
					this.flags.potentDecreasingEffect | potionMod.flags.potentDecreasingEffect,
					this.flags.negateDecreasingDuration | potionMod.flags.negateDecreasingDuration,
					this.flags.randomColor | potionMod.flags.randomColor,
					this.flags.incurable | potionMod.flags.incurable,
					Stream.concat(this.flags.additionalEffects.stream(), potionMod.flags.additionalEffects.stream()).toList()
			);
			return this;
		}
		
		public Builder potencyMultiplier(float potencyMultiplier) {
			this.potencyMultiplier = potencyMultiplier;
			return this;
		}
		
		public Builder durationMultiplier(float durationMultiplier) {
			this.durationMultiplier = durationMultiplier;
			return this;
		}
		
		public PotionMod build() {
			return new PotionMod(
					this.flatDurationBonusTicks,
					this.flatPotencyBonus,
					this.durationMultiplier,
					this.potencyMultiplier,
					this.flatPotencyBonusPositiveEffects,
					this.flatPotencyBonusNegativeEffects,
					this.flatDurationBonusPositiveEffects,
					this.flatDurationBonusNegativeEffects,
					this.additionalRandomPositiveEffectCount,
					this.additionalRandomNegativeEffectCount,
					this.chanceToAddLastEffect,
					this.lastEffectDurationMultiplier,
					this.lastEffectPotencyMultiplier,
					this.yield,
					this.additionalDrinkDurationTicks,
					this.flags
			);
		}
		
	}
	
}