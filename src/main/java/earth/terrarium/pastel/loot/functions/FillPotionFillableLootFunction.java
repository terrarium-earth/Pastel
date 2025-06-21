package earth.terrarium.pastel.loot.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPoweredStatusEffectInstance;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.item.InkPoweredPotionFillable;
import earth.terrarium.pastel.helpers.CodecHelper;
import earth.terrarium.pastel.loot.PastelLootFunctionTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

import java.util.List;

public class FillPotionFillableLootFunction extends LootItemConditionalFunction {
	
	public static final MapCodec<FillPotionFillableLootFunction> CODEC = RecordCodecBuilder.mapCodec(i -> commonFields(i).and(
			InkPoweredPotionTemplate.CODEC.forGetter(c -> c.template)
	).apply(i, FillPotionFillableLootFunction::new));
	
	public record InkPoweredPotionTemplate(
			boolean ambient, boolean showParticles, NumberProvider duration,
			List<Holder<MobEffect>> statusEffects, int color, NumberProvider amplifier,
			List<InkColor> inkColors, NumberProvider inkCost, boolean unidentifiable, boolean incurable
	) {
		
		public static final MapCodec<InkPoweredPotionTemplate> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.BOOL.optionalFieldOf("ambient", false).forGetter(InkPoweredPotionTemplate::ambient),
				Codec.BOOL.optionalFieldOf("show_particles", false).forGetter(InkPoweredPotionTemplate::showParticles),
				NumberProviders.CODEC.fieldOf("duration").forGetter(InkPoweredPotionTemplate::duration),
				CodecHelper.singleOrList(BuiltInRegistries.MOB_EFFECT.holderByNameCodec()).fieldOf("status_effect").forGetter(InkPoweredPotionTemplate::statusEffects),
				Codec.INT.optionalFieldOf("color", -1).forGetter(InkPoweredPotionTemplate::color),
				NumberProviders.CODEC.fieldOf("amplifier").forGetter(InkPoweredPotionTemplate::amplifier),
				CodecHelper.singleOrList(InkColor.CODEC).fieldOf("ink_color").forGetter(InkPoweredPotionTemplate::inkColors),
				NumberProviders.CODEC.fieldOf("ink_cost").forGetter(InkPoweredPotionTemplate::inkCost),
				Codec.BOOL.optionalFieldOf("unidentifiable", false).forGetter(InkPoweredPotionTemplate::unidentifiable),
				Codec.BOOL.optionalFieldOf("incurable", false).forGetter(InkPoweredPotionTemplate::incurable)
		).apply(i, InkPoweredPotionTemplate::new));
		
		public InkPoweredStatusEffectInstance get(LootContext context) {
			Holder<MobEffect> statusEffect = this.statusEffects.get(context.getRandom().nextInt(this.statusEffects.size()));
			MobEffectInstance statusEffectInstance = new MobEffectInstance(statusEffect, this.duration.getInt(context), this.amplifier.getInt(context), ambient, showParticles, true);
			InkColor inkColor = this.inkColors.get(context.getRandom().nextInt(this.inkColors.size()));
			int cost = this.inkCost.getInt(context);
			return new InkPoweredStatusEffectInstance(statusEffectInstance, new InkCost(inkColor, cost), this.color, this.unidentifiable, this.incurable);
		}
		
	}
	
	private final InkPoweredPotionTemplate template;
	
	FillPotionFillableLootFunction(List<LootItemCondition> conditions, InkPoweredPotionTemplate template) {
		super(conditions);
		this.template = template;
	}
	
	@Override
	public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
		return PastelLootFunctionTypes.FILL_POTION_FILLABLE;
	}
	
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if (this.template == null)
			return stack;

		if (!(stack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable))
			return stack;

		if (inkPoweredPotionFillable.isFull(stack))
			return stack;
		
		InkPoweredStatusEffectInstance effect = template.get(context);
		inkPoweredPotionFillable.addOrUpgradeEffects(stack, List.of(effect));
		
		return stack;
	}
	
	public static LootItemConditionalFunction.Builder<?> builder(InkPoweredPotionTemplate template) {
		return simpleBuilder((conditions) -> new FillPotionFillableLootFunction(conditions, template));
	}
	
}
