package de.dafuqs.revelationary.advancement_criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class HadRevelationCriterion extends SimpleCriterionTrigger<HadRevelationCriterion.Conditions> {
	public void trigger(ServerPlayer player, Block block) {
		this.trigger(player, (conditions) -> conditions.matches(block));
	}

	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}

	public record Conditions(Optional<ContextAwarePredicate> player, ResourceLocation identifier) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ResourceLocation.CODEC.fieldOf("revelation_identifier").forGetter(Conditions::identifier))
			.apply(inst, Conditions::new));
		
		public boolean matches(Object object) {
			if (identifier.getPath().isEmpty()) {
				// if "revelation_identifier": "" => trigger with any revelation
				return true;
			} else if (object instanceof Block cloakableBlock) {
				return BuiltInRegistries.BLOCK.getKey(cloakableBlock).equals(identifier);
			} else if (object instanceof Item cloakableItem) {
				return BuiltInRegistries.ITEM.getKey(cloakableItem).equals(identifier);
			} else {
				return false;
			}
		}
	}
}
