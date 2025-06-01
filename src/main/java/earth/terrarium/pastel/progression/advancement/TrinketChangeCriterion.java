package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.items.trinkets.SpectrumTrinketItem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrinketChangeCriterion extends SimpleCriterionTrigger<TrinketChangeCriterion.Conditions> {
	
	public static final ResourceLocation ID = SpectrumCommon.locate("trinket_change");
	
	public void trigger(ServerPlayer player) {
		this.trigger(player, (conditions) -> {
			Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
			if (curiosInventory.isPresent()) {
				List<ItemStack> equippedStacks = new ArrayList<>();
				int spectrumStacks = 0;
				IItemHandlerModifiable equippedCurios = curiosInventory.get().getEquippedCurios();

				for (int i = 0; i < equippedCurios.getSlots(); i++) {
					ItemStack itemStack = equippedCurios.getStackInSlot(i);

					equippedStacks.add(itemStack);
					if (itemStack.getItem() instanceof SpectrumTrinketItem) {
						spectrumStacks++;
					}
				}
				return conditions.matches(equippedStacks, equippedStacks.size(), spectrumStacks);
			}
			return false;
		});
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			Optional<List<ItemPredicate>> itemPredicates,
			Optional<MinMaxBounds.Ints> totalCountRange,
			Optional<MinMaxBounds.Ints> spectrumCountRange
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<TrinketChangeCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(TrinketChangeCriterion.Conditions::player),
				ItemPredicate.CODEC.listOf().optionalFieldOf("items").forGetter(TrinketChangeCriterion.Conditions::itemPredicates),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("total_count").forGetter(TrinketChangeCriterion.Conditions::totalCountRange),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("pastel_count").forGetter(TrinketChangeCriterion.Conditions::spectrumCountRange)
		).apply(instance, TrinketChangeCriterion.Conditions::new));
		
		public boolean matches(List<ItemStack> trinketStacks, int totalCount, int spectrumCount) {
			if ((this.totalCountRange.isPresent() && this.totalCountRange.get().matches(totalCount))
					|| (this.spectrumCountRange.isPresent() && this.spectrumCountRange.get().matches(spectrumCount))) {
				int i = this.itemPredicates.orElse(List.of()).size();
				if (i == 0) {
					return true;
				} else {
					List<ItemPredicate> requiredTrinkets = new ObjectArrayList<>(this.itemPredicates.get());
					for (ItemStack trinketStack : trinketStacks) {
						if (requiredTrinkets.isEmpty()) {
							return true;
						}
						if (!trinketStack.isEmpty()) {
							requiredTrinkets.removeIf((item) -> item.test(trinketStack));
						}
					}
					
					return requiredTrinkets.isEmpty();
				}
			}
			return false;
		}
	}
	
}
