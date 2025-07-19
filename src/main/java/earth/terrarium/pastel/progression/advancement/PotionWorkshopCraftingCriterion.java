package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class PotionWorkshopCraftingCriterion extends SimpleCriterionTrigger<PotionWorkshopCraftingCriterion.Conditions> {
	
	public static final ResourceLocation ID = PastelCommon.locate("crafted_with_potion_workshop");
	
	public void trigger(ServerPlayer player, ItemStack itemStack) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack));
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			List<ItemPredicate> itemPredicates
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.listOf().optionalFieldOf("items", List.of()).forGetter(Conditions::itemPredicates)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack itemStack) {
			List<ItemPredicate> list = new ObjectArrayList<>(this.itemPredicates);
			if (list.isEmpty()) {
				return true;
			} else {
				if (!itemStack.isEmpty()) {
					list.removeIf((itemPredicate) -> itemPredicate.test(itemStack));
				}
				return list.isEmpty();
			}
		}
	}
	
}
