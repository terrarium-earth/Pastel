package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class CrystalApothecaryCollectingCriterion extends SimpleCriterionTrigger<CrystalApothecaryCollectingCriterion.Conditions> {
	
	public static final ResourceLocation ID = PastelCommon.locate("collect_using_crystal_apothecary");
	
	public void trigger(ServerPlayer player, ItemStack itemStack) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack));
	}
	
	@Override
	public Codec<CrystalApothecaryCollectingCriterion.Conditions> codec() {
		return CrystalApothecaryCollectingCriterion.Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			ItemPredicate item
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<CrystalApothecaryCollectingCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(CrystalApothecaryCollectingCriterion.Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("item", ItemPredicate.Builder.item().build()).forGetter(CrystalApothecaryCollectingCriterion.Conditions::item)
		).apply(instance, CrystalApothecaryCollectingCriterion.Conditions::new));
		
		public boolean matches(ItemStack stack) {
			return this.item.test(stack);
		}
		
	}
	
}
