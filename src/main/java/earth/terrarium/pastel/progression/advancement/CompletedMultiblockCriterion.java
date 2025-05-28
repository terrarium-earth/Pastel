package earth.terrarium.pastel.progression.advancement;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.SpectrumCommon;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class CompletedMultiblockCriterion extends SimpleCriterionTrigger<CompletedMultiblockCriterion.Conditions> {
	
	public static final ResourceLocation ID = SpectrumCommon.locate("completed_multiblock");
	
	public void trigger(ServerPlayer player, Multiblock iMultiblock) {
		this.trigger(player, (conditions) -> conditions.matches(iMultiblock));
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(Optional<ContextAwarePredicate> player, Optional<ResourceLocation> identifier) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ResourceLocation.CODEC.optionalFieldOf("multiblock_identifier").forGetter(Conditions::identifier)
		).apply(instance, Conditions::new));
		
		public boolean matches(Multiblock multiblock) {
			return identifier.isEmpty() || multiblock.getId().equals(identifier.get());
		}
	}
	
}
