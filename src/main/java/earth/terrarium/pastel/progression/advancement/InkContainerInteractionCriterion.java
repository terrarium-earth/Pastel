package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkStorage;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.helpers.data.CodecHelper;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Optional;

public class InkContainerInteractionCriterion extends SimpleCriterionTrigger<InkContainerInteractionCriterion.Conditions> {
	
	public static final ResourceLocation ID = PastelCommon.locate("ink_container_interaction");
	
	public void trigger(ServerPlayer player, ItemStack stack, InkStorage storage, InkColor changeColor, long changeAmount) {
		this.trigger(player, (conditions) -> conditions.matches(stack, storage.getEnergy(), changeColor, changeAmount));
	}
	
	@Override
	public Codec<Conditions> codec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
			Optional<ContextAwarePredicate> player,
			ItemPredicate itemPredicate,
			Map<InkColor, LongRange> colorRanges,
			ColorPredicate changeColorPredicate,
			LongRange changeRange
	) implements SimpleCriterionTrigger.SimpleInstance {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
				ItemPredicate.CODEC.optionalFieldOf("item", ItemPredicate.Builder.item().build()).forGetter(Conditions::itemPredicate),
				CodecHelper.registryMap(PastelRegistries.INK_COLOR, LongRange.CODEC).forGetter(Conditions::colorRanges),
				ColorPredicate.CODEC.optionalFieldOf("change_color", ColorPredicate.ANY).forGetter(Conditions::changeColorPredicate),
				LongRange.CODEC.optionalFieldOf("change_amount", LongRange.ANY).forGetter(Conditions::changeRange)
		).apply(instance, Conditions::new));
		
		public boolean matches(ItemStack stack, Map<InkColor, Long> colors, InkColor changeColor, long change) {
			return itemPredicate.test(stack)
					&& changeRange.test(change)
					&& changeColorPredicate.test(changeColor)
					&& colorRanges.entrySet().stream().allMatch(entry -> colorRanges.get(entry.getKey()).test(colors.get(entry.getKey())));
			
		}
	}
	
}