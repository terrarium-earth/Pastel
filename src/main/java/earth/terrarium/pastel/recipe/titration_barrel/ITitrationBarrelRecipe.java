package earth.terrarium.pastel.recipe.titration_barrel;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.capabilities.item.*;
import earth.terrarium.pastel.helpers.*;
import net.neoforged.neoforge.fluids.capability.templates.*;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import earth.terrarium.pastel.api.recipe.GatedRecipe;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.components.BeverageComponent;
import earth.terrarium.pastel.recipe.FluidRecipeInput;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumDataComponentTypes;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumRecipeTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

/**
 * In contrast to most other Minecraft things, the titration barrel also counts the fermenting time
 * when the game is not running (comparing the time of sealing to the time of opening)
 * Making it a non-ticking block entity and also "fermenting" when the game is not running
 * This also means TitrationBarrelRecipes have to calculate their time using real life seconds, instead of game ticks
 */
public interface ITitrationBarrelRecipe extends GatedRecipe<FluidRecipeInput<FluidTank>> {
	
	ResourceLocation UNLOCK_ADVANCEMENT_IDENTIFIER = SpectrumCommon.locate("unlocks/blocks/titration_barrel");
	
	// Called by the titration barrel when tapped
	default ItemStack getTitrationResult(FriendlyStackHandler inventory, long secondsFermented, float downfall) {
		// Dr. Who would be proud
		if (secondsFermented < 0) {
			float ageIngameDays = TimeHelper.minecraftDaysFromSeconds(secondsFermented);
			List<MobEffectInstance> statusEffects = List.of(new MobEffectInstance(MobEffects.INVISIBILITY, 3600, 0));
			
			var stack = SpectrumItems.SUSPICIOUS_BREW.get().getDefaultInstance();
			stack.set(SpectrumDataComponentTypes.BEVERAGE, new BeverageComponent((long) ageIngameDays, 0, 0));
			stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), statusEffects));
			LoreHelper.setLore(stack, Component.translatable("lore.pastel.time_travel_tap"));
			return stack;
		}
		
		return tap(inventory, secondsFermented, downfall);
	}
	
	ItemStack tap(FriendlyStackHandler inventory, long secondsFermented, float downfall);
	
	Item getTappingItem();
	
	FluidIngredient getFluidInput();
	
	float getAngelsSharePerMcDay();
	
	// the amount of bottles able to get out of a single barrel
	default int getOutputCountAfterAngelsShare(Level world, float temperature, long secondsFermented) {
		int originalOutputCount = getResultItem(world.registryAccess()).getCount();
		
		if (getFermentationData() == null) {
			return originalOutputCount;
		}
		
		// Linearly adjust the output count based on angel's share
		float angelsShareResultCountMod = getAngelsShareResultCountMod(secondsFermented, temperature);
		if (angelsShareResultCountMod > 0) {
			return Math.max(1, (int) Math.ceil((originalOutputCount - angelsShareResultCountMod)));
		} else {
			return Math.max(1, (int) Math.floor((originalOutputCount - angelsShareResultCountMod)));
		}
	}
	
	// the amount of fluid that evaporated while fermenting
	// the higher the temperature in the biome is, the more evaporates
	// making colder biomes more desirable
	default float getAngelsShareResultCountMod(long secondsFermented, float temperature) {
		return Math.max(0.1F, temperature / 10F) * TimeHelper.minecraftDaysFromSeconds(secondsFermented) * getAngelsSharePerMcDay();
	}
	
	@Override
	default boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	
	@Override
	default ItemStack getToastSymbol() {
		return SpectrumBlocks.TITRATION_BARREL.get().asItem().getDefaultInstance();
	}
	
	@Override
	default RecipeType<?> getType() {
		return SpectrumRecipeTypes.TITRATION_BARREL;
	}
	
	List<IngredientStack> getIngredientStacks();
	
	int getMinFermentationTimeHours();
	
	default boolean isFermentingLongEnoughToTap(long secondsFermented) {
		return secondsFermented / 60 / 60 >= getMinFermentationTimeHours();
	}
	
	FermentationData getFermentationData();
	
}
