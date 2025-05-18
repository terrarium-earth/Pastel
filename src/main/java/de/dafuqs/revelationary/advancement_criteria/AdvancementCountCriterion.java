package de.dafuqs.revelationary.advancement_criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Optional;

public class AdvancementCountCriterion extends SimpleCriterionTrigger<AdvancementCountCriterion.Conditions> {
	public void trigger(ServerPlayer player) {
		this.trigger(player, (conditions) -> conditions.matches(player));
	}

	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}

	public record Conditions(Optional<ContextAwarePredicate> player, List<ResourceLocation> advancementIdentifiers, MinMaxBounds.Ints range) implements SimpleCriterionTrigger.SimpleInstance {

		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ResourceLocation.CODEC.listOf().fieldOf("advancement_identifiers").forGetter(Conditions::advancementIdentifiers),
				MinMaxBounds.Ints.CODEC.fieldOf("count").forGetter(Conditions::range))
			.apply(inst, Conditions::new));

		public boolean matches(ServerPlayer serverPlayerEntity) {
			ServerAdvancementManager loader = serverPlayerEntity.server.getAdvancements();
			if(loader == null) {
				return false;
			}
			PlayerAdvancements tracker = serverPlayerEntity.getAdvancements();
			if(tracker == null) {
				return false;
			}
			
			int matchingAdvancements = 0;
			boolean allMatched = true;
			for(ResourceLocation advancementIdentifier : this.advancementIdentifiers) {
				AdvancementHolder advancement = loader.get(advancementIdentifier);
				if(advancement != null && tracker.getOrStartProgress(advancement).isDone()) {
					matchingAdvancements++;
				} else {
					allMatched = false;
				}
			}
			
			return this.range == null ? allMatched : this.range.matches(matchingAdvancements);
		}
	}
}
