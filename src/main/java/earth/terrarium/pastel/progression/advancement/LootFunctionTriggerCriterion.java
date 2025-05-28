package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.SpectrumCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;

public class LootFunctionTriggerCriterion extends SimpleCriterionTrigger<LootFunctionTriggerCriterion.Conditions> {
	
	public static final ResourceLocation ID = SpectrumCommon.locate("loot_function_trigger");
	
	public void trigger(ServerPlayer player, ResourceLocation id) {
		this.trigger(player, (conditions) -> conditions.matches(id));
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(Optional<ContextAwarePredicate> player,
							 List<ResourceLocation> ids) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ResourceLocation.CODEC.listOf().optionalFieldOf("tags", List.of()).forGetter(Conditions::ids)
		).apply(instance, Conditions::new));
		
		public boolean matches(ResourceLocation id) {
			return this.ids.isEmpty() || this.ids.contains(id);
		}
	}
	
}
