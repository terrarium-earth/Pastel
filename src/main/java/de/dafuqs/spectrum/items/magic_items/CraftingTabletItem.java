package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.items.tooltip.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

import java.util.*;

public class CraftingTabletItem extends Item implements LoomPatternProvider {
	
	private static final Text TITLE = Text.translatable("item.spectrum.crafting_tablet");
	
	public CraftingTabletItem(Settings settings) {
		super(settings);
	}
	
	public static void setStoredRecipe(ItemStack craftingTabletItemStack, RecipeEntry<?> recipe) {
		craftingTabletItemStack.set(SpectrumDataComponentTypes.STORED_RECIPE, recipe.id());
	}
	
	public static void clearStoredRecipe(ItemStack craftingTabletItemStack) {
		craftingTabletItemStack.remove(SpectrumDataComponentTypes.STORED_RECIPE);
	}
	
	public static RecipeEntry<?> getStoredRecipe(World world, ItemStack itemStack) {
		if (world != null) {
			var id = itemStack.get(SpectrumDataComponentTypes.STORED_RECIPE);
			if (id != null)
				return world.getRecipeManager().get(id).orElse(null);
		}
		return null;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		
		var storedRecipe = getStoredRecipe(world, itemStack);
		if (storedRecipe == null || user.isSneaking()) {
			if (world.isClient) {
				return TypedActionResult.success(user.getStackInHand(hand));
			} else {
				user.openHandledScreen(createScreenHandlerFactory(world, (ServerPlayerEntity) user, itemStack));
				return TypedActionResult.consume(user.getStackInHand(hand));
			}
		} else {
			if (storedRecipe.value() instanceof PedestalRecipe) {
				return TypedActionResult.pass(user.getStackInHand(hand));
			} else {
				if (tryCraftRecipe(user, storedRecipe.value(), world)) {
					if (world.isClient) {
						return TypedActionResult.success(user.getStackInHand(hand));
					} else {
						return TypedActionResult.consume(user.getStackInHand(hand));
					}
				}
				user.playSound(SpectrumSoundEvents.USE_FAIL, 1.0F, 1.0F);
				return TypedActionResult.fail(user.getStackInHand(hand));
			}
		}
	}
	
	public NamedScreenHandlerFactory createScreenHandlerFactory(World world, ServerPlayerEntity serverPlayerEntity, ItemStack itemStack) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new CraftingTabletScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, serverPlayerEntity.getBlockPos()), itemStack), TITLE);
	}
	
	public static boolean tryCraftRecipe(PlayerEntity serverPlayerEntity, Recipe<?> recipe, World world) {
		DefaultedList<Ingredient> ingredients = recipe.getIngredients();
		
		Inventory playerInventory = serverPlayerEntity.getInventory();
		boolean hasInInventory = InventoryHelper.hasInInventory(ingredients, playerInventory);
		if (world.isClient) {
			return hasInInventory;
		}
		
		if (InventoryHelper.hasInInventory(ingredients, playerInventory)) {
			List<ItemStack> remainders = InventoryHelper.removeFromInventoryWithRemainders(ingredients, playerInventory);
			
			ItemStack craftingResult = recipe.getResult(serverPlayerEntity.getWorld().getRegistryManager()).copy();
			serverPlayerEntity.getInventory().offerOrDrop(craftingResult);
			
			for (ItemStack remainder : remainders) {
				serverPlayerEntity.getInventory().offerOrDrop(remainder);
			}
			return true;
		}
		return false;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		var recipe = getStoredRecipe(MinecraftClient.getInstance().world, stack);
		if (recipe == null) {
			tooltip.add(Text.translatable("item.spectrum.crafting_tablet.tooltip.no_recipe").formatted(Formatting.GRAY));
		} else {
			if (recipe.value() instanceof PedestalRecipe) {
				tooltip.add(Text.translatable("item.spectrum.crafting_tablet.tooltip.pedestal_recipe").formatted(Formatting.GRAY));
			} else {
				tooltip.add(Text.translatable("item.spectrum.crafting_tablet.tooltip.crafting_recipe").formatted(Formatting.GRAY));
			}
			tooltip.add(Text.translatable("item.spectrum.crafting_tablet.tooltip.shift_to_view_gui").formatted(Formatting.GRAY));
		}
		
		addBannerPatternProviderTooltip(tooltip);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		MinecraftClient client = MinecraftClient.getInstance();
		var storedRecipe = CraftingTabletItem.getStoredRecipe(client.world, stack);
		if (storedRecipe != null) {
			return Optional.of(new CraftingTabletTooltipData(storedRecipe.value(), client.world));
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.CRAFTING_TABLET;
	}
	
}
