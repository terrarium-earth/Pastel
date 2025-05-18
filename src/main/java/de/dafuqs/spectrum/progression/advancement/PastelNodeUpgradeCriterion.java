package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class PastelNodeUpgradeCriterion extends SimpleCriterionTrigger<PastelNodeUpgradeCriterion.Conditions> {
	
	public static final ResourceLocation ID = SpectrumCommon.locate("pastel_node_upgrade");
	
	public void trigger(ServerPlayer player, ItemStack stack) {
		this.trigger(player, conditions -> conditions.matches(stack));
	}
	
	@Override
	public Codec<PastelNodeUpgradeCriterion.Conditions> codec() {
		return PastelNodeUpgradeCriterion.Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			ItemPredicate item
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<PastelNodeUpgradeCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(PastelNodeUpgradeCriterion.Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("upgrade", ItemPredicate.Builder.item().build()).forGetter(PastelNodeUpgradeCriterion.Conditions::item)
		).apply(instance, PastelNodeUpgradeCriterion.Conditions::new));
		
		public boolean matches(ItemStack stack) {
			return this.item.test(stack);
		}
		
	}
}
