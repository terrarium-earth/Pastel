package earth.terrarium.pastel.recipe.titration_barrel.dynamic;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.capabilities.item.*;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.neoforged.neoforge.fluids.capability.templates.*;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.recipe.FluidRecipeInput;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NecteredViognierRecipe extends SweetenableTitrationBarrelRecipe {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("hidden/collect_cookbooks/imperial_cookbook");
	
	public static final int MIN_FERMENTATION_TIME_HOURS = 24;
	public static final ItemStack OUTPUT_STACK = getDefaultStackWithCount(PastelItems.NECTERED_VIOGNIER.get(), 4);
	public static final Item TAPPING_ITEM = Items.GLASS_BOTTLE;
	public static final List<IngredientStack> INGREDIENT_STACKS = new ArrayList<>() {{
		add(IngredientStack.ofItems(PastelBlocks.NEPHRITE_BLOSSOM_BULB.get().asItem()));
		add(IngredientStack.ofItems(PastelItems.GLASS_PEACH.get(), 4));
	}};
	
	public NecteredViognierRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), INGREDIENT_STACKS, FluidIngredient.of(Fluids.WATER), OUTPUT_STACK, TAPPING_ITEM, MIN_FERMENTATION_TIME_HOURS, new FermentationData(0.15F, 0.01F, List.of()));
	}
	
	@Override
	public ItemStack tap(FriendlyStackHandler inventory, long secondsFermented, float downfall) {
		int bulbCount = InventoryHelper.getItemCountInInventory(inventory, PastelBlocks.NEPHRITE_BLOSSOM_BULB.get().asItem());
		int petalCount = InventoryHelper.getItemCountInInventory(inventory, PastelItems.GLASS_PEACH.get());
		boolean nectar = InventoryHelper.getItemCountInInventory(inventory, PastelItems.MOONSTRUCK_NECTAR.get()) > 0;
		
		float thickness = getThickness(bulbCount, petalCount);
		return tapWith(bulbCount, petalCount, nectar, thickness, secondsFermented, downfall);
	}
	
	@Override
	protected @NotNull List<MobEffectInstance> getEffects(boolean nectar, double bloominess, double alcPercent) {
		List<MobEffectInstance> effects = new ArrayList<>();
		
		//TODO should this be a float, and only casted to int at the end?
		int effectDuration = (int) (150 * Math.round(alcPercent / 10));
		if (alcPercent >= 35) {
			effects.add(new MobEffectInstance(PastelMobEffects.MAGIC_ANNULATION, effectDuration, (int) (alcPercent / 10)));
		}
		if (alcPercent >= 35) {
			effects.add(new MobEffectInstance(PastelMobEffects.TOUGHNESS, effectDuration, (int) (alcPercent / 10)));
			effectDuration *= 1.5;
		}
		if (alcPercent >= 30) {
			effects.add(new MobEffectInstance(MobEffects.DAMAGE_BOOST, effectDuration, (int) (alcPercent / 10)));
			effectDuration *= 1.5;
		}
		if (alcPercent >= 20) {
			effects.add(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, effectDuration, (int) (alcPercent / 45)));
			effectDuration *= 1.5;
		}
		if (alcPercent >= 10) {
			effects.add(new MobEffectInstance(PastelMobEffects.NOURISHING, effectDuration));
			effectDuration *= 1.5;
		}
		if (nectar) {
			effects.add(new MobEffectInstance(PastelMobEffects.IMMUNITY, effectDuration / 2));
		}
		
		int nectarMod = nectar ? 3 : 1;
		effectDuration = 1200;
		int alcAfterBloominess = (int) (alcPercent / (nectarMod + bloominess));
		if (alcAfterBloominess >= 40) {
			effects.add(new MobEffectInstance(MobEffects.BLINDNESS, effectDuration));
			effectDuration *= 2;
		}
		if (alcAfterBloominess >= 30) {
			effects.add(new MobEffectInstance(MobEffects.POISON, effectDuration));
			effectDuration *= 2;
		}
		if (alcAfterBloominess >= 20) {
			effects.add(new MobEffectInstance(MobEffects.CONFUSION, effectDuration));
			effectDuration *= 2;
		}
		if (alcAfterBloominess >= 10) {
			effects.add(new MobEffectInstance(MobEffects.WEAKNESS, effectDuration));
		}
		return effects;
	}
	
	@Override
	public boolean matches(FluidRecipeInput<FluidTank> recipeInput, Level world) {
		boolean bulbsFound = false;
		
		for (int i = 0; i < recipeInput.size(); i++) {
			ItemStack stack = recipeInput.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.is(PastelBlocks.NEPHRITE_BLOSSOM_BULB.get().asItem())) {
				bulbsFound = true;
			} else if (!stack.is(PastelItems.GLASS_PEACH.get()) && !stack.is(PastelItems.MOONSTRUCK_NECTAR.get())) {
				return false;
			}
		}
		
		return bulbsFound;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.TITRATION_BARREL_NECTERED_VIOGNIER;
	}
	
}
