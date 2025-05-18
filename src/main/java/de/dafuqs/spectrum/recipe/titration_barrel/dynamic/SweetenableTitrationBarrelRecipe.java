package de.dafuqs.spectrum.recipe.titration_barrel.dynamic;

import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.components.BeverageComponent;
import de.dafuqs.spectrum.components.JadeWineComponent;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.helpers.TimeHelper;
import de.dafuqs.spectrum.recipe.titration_barrel.FermentationData;
import de.dafuqs.spectrum.recipe.titration_barrel.TitrationBarrelRecipe;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;
import java.util.Optional;

public abstract class SweetenableTitrationBarrelRecipe extends TitrationBarrelRecipe {
	
	public SweetenableTitrationBarrelRecipe(String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, List<IngredientStack> inputStacks, FluidIngredient fluid, ItemStack outputItemStack, Item tappingItem, int minFermentationTimeHours, FermentationData fermentationData) {
		super(group, secret, requiredAdvancementIdentifier, inputStacks, fluid, outputItemStack, tappingItem, minFermentationTimeHours, fermentationData);
	}
	
	@Override
	public ItemStack getPreviewTap(int timeMultiplier) {
		return tapWith(1, 3, false, 1.0F, this.minFermentationTimeHours * 60L * 60L * timeMultiplier, 0.4F);
	}
	
	protected abstract List<MobEffectInstance> getEffects(boolean nectar, double bloominess, double alcPercent);
	
	protected ItemStack tapWith(int bulbCount, int petalCount, boolean nectar, float thickness, long secondsFermented, float downfall) {
		double bloominess = getBloominess(bulbCount, petalCount);
		float ageIngameDays = TimeHelper.minecraftDaysFromSeconds(secondsFermented);
		if (nectar) {
			thickness *= 1.5F;
		}
		double alcPercent = getAlcPercentWithBloominess(ageIngameDays, downfall, bloominess, thickness);
		if (alcPercent >= 100) {
			return SpectrumItems.CHRYSOCOLLA.getDefaultInstance();
		} else {
			List<MobEffectInstance> effects = getEffects(nectar, bloominess, alcPercent);
			
			ItemStack outputStack = outputItemStack.copy();
			outputStack.setCount(1);
			outputStack.set(SpectrumDataComponentTypes.BEVERAGE, new BeverageComponent((long) ageIngameDays, (int) alcPercent, thickness));
			outputStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), effects));
			outputStack.set(SpectrumDataComponentTypes.JADE_WINE, new JadeWineComponent((float) bloominess, nectar));
			return outputStack;
		}
	}
	
	// bloominess reduces the possibility of negative effects to trigger (better on the tongue)
	// but also reduces the potency of positive effects a bit
	protected static double getBloominess(int bulbCount, int petalCount) {
		if (bulbCount == 0) {
			return 0;
		}
		return (double) petalCount / (double) bulbCount / 2F;
	}
	
	// the amount of solid to liquid
	protected float getThickness(int bulbCount, int petalCount) {
		return bulbCount + petalCount / 8F;
	}
	
	// the alc % determines the power of effects when drunk
	// it generally increases the longer the wine has fermented
	//
	// another detail: the more rainy the weather (downfall) the more water evaporates
	// compared to alcohol, making the drink stronger / weaker in return
	protected double getAlcPercentWithBloominess(float ageIngameDays, float downfall, double bloominess, double thickness) {
		return Support.logBase(1 + this.fermentationData.fermentationSpeedMod() + bloominess / 64, ageIngameDays * (0.5 + thickness / 2) * (0.5D + downfall / 2D));
	}
	
}
