package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

public class CrystallarieumGrownCriterion extends AbstractCriterion<CrystallarieumGrownCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("crystallarieum_growing");
	
	public void trigger(ServerPlayerEntity player, ServerWorld world, BlockPos pos, ItemStack catalystStack) {
		this.trigger(player, (conditions) -> conditions.matches(world, pos, catalystStack));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<LootContextPredicate> player,
			BlockPredicate blockPredicate,
			ItemPredicate catalystPredicate
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<CrystallarieumGrownCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(CrystallarieumGrownCriterion.Conditions::player),
				BlockPredicate.CODEC.optionalFieldOf("grown_block", BlockPredicate.Builder.create().build()).forGetter(CrystallarieumGrownCriterion.Conditions::blockPredicate),
				ItemPredicate.CODEC.optionalFieldOf("used_catalyst", ItemPredicate.Builder.create().build()).forGetter(CrystallarieumGrownCriterion.Conditions::catalystPredicate)
		).apply(instance, CrystallarieumGrownCriterion.Conditions::new));
		
		public boolean matches(ServerWorld world, BlockPos blockPos, ItemStack catalystStack) {
			return this.blockPredicate.test(world, blockPos) && this.catalystPredicate.test(catalystStack);
		}
	}
	
}
