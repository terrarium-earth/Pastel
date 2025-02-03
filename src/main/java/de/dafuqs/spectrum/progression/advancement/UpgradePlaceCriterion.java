package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.upgrade.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

public class UpgradePlaceCriterion extends AbstractCriterion<UpgradePlaceCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("upgrade_place");
	
	public void trigger(ServerPlayerEntity player, ServerWorld world, BlockPos pos, int upgradeCount, Map<Upgradeable.UpgradeType, Integer> upgradeModifiers) {
		this.trigger(player, (conditions) -> conditions.matches(world, pos, upgradeCount, upgradeModifiers));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			Optional<BlockPredicate> blockPredicate,
			Optional<NumberRange.IntRange> countRange,
			Optional<NumberRange.IntRange> speedRange,
			Optional<NumberRange.IntRange> experienceRange,
			Optional<NumberRange.IntRange> efficiencyRange,
			Optional<NumberRange.IntRange> yieldRange
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				BlockPredicate.CODEC.optionalFieldOf("block").forGetter(Conditions::blockPredicate),
				NumberRange.IntRange.CODEC.optionalFieldOf("count").forGetter(Conditions::countRange),
				NumberRange.IntRange.CODEC.optionalFieldOf("speed_mod").forGetter(Conditions::speedRange),
				NumberRange.IntRange.CODEC.optionalFieldOf("experience_mod").forGetter(Conditions::experienceRange),
				NumberRange.IntRange.CODEC.optionalFieldOf("efficiency_mod").forGetter(Conditions::efficiencyRange),
				NumberRange.IntRange.CODEC.optionalFieldOf("yield_mod").forGetter(Conditions::yieldRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ServerWorld world, BlockPos pos, int upgradeCount, Map<Upgradeable.UpgradeType, Integer> upgradeModifiers) {
			// FIXME - Determine the best logic for this
			return this.blockPredicate.get().test(world, pos)
					&& this.countRange.get().test(upgradeCount)
					&& this.speedRange.get().test(upgradeModifiers.get(Upgradeable.UpgradeType.SPEED))
					&& this.experienceRange.get().test(upgradeModifiers.get(Upgradeable.UpgradeType.EXPERIENCE))
					&& this.efficiencyRange.get().test(upgradeModifiers.get(Upgradeable.UpgradeType.EFFICIENCY))
					&& this.yieldRange.get().test(upgradeModifiers.get(Upgradeable.UpgradeType.YIELD));
		}
	}
	
}