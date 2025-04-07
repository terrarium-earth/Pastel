package de.dafuqs.spectrum.data;

import java.util.*;
import java.util.concurrent.*;

import com.mojang.datafixers.util.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.recipe.crystallarieum.*;
import de.dafuqs.spectrum.recipe.enchanter.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.block.*;
import net.minecraft.data.server.recipe.*;
import net.minecraft.enchantment.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.state.property.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public class SpectrumRecipeProvider extends FabricRecipeProvider {
	
	public SpectrumRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	public void generate(RecipeExporter ctx) {
		generateCrystallarieumRecipes(ctx);
		generateEnchantmentUpgradeRecipes(ctx);
	}
	
	private void generateCrystallarieumRecipes(RecipeExporter ctx) {
		generateCrystallarieumRecipe(ctx, "minecraft/coal", Items.COAL, null, null, 60, InkColors.BROWN, 1, false,
				List.of(SpectrumBlocks.SMALL_COAL_BUD, SpectrumBlocks.LARGE_COAL_BUD, SpectrumBlocks.COAL_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.CHARCOAL), 2.0f, 0.4f, 0.2f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.INCANDESCENT_ESSENCE), 16.0f, 2.0f, 0.05f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.VEGETAL), 0.75f, 0.05f, 0.4f)
				),
				List.of(SpectrumItems.PURE_COAL.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/copper", Items.RAW_COPPER, null, null, 60, InkColors.BROWN, 2, false,
				List.of(SpectrumBlocks.SMALL_COPPER_BUD, SpectrumBlocks.LARGE_COPPER_BUD, SpectrumBlocks.COPPER_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.RAW_MALACHITE), 4.0f, 0.5f, 0.2f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.HONEYCOMB), 8.0f, 2.0f, 0.05f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.NEOLITH), 1.5f, 0.25f, 0.02f)
				),
				List.of(SpectrumItems.PURE_COPPER.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/diamond", Items.DIAMOND, null, null, 480, InkColors.CYAN, 3, false,
				List.of(SpectrumBlocks.SMALL_DIAMOND_BUD, SpectrumBlocks.LARGE_DIAMOND_BUD, SpectrumBlocks.DIAMOND_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.COAL), 8.0f, 0.25f, 0.2f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.COAL_BLOCK), 10.0f, 0.25f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.CHARCOAL), 16.0f, 0.25f, 1.0f)
				),
				List.of(SpectrumItems.PURE_DIAMOND.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/echo", Items.ECHO_SHARD, SpectrumFluids.MIDNIGHT_SOLUTION, null, 960, InkColors.BROWN, 3, false,
				List.of(SpectrumBlocks.SMALL_ECHO_BUD, SpectrumBlocks.LARGE_ECHO_BUD, SpectrumBlocks.ECHO_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.FROSTBITE_ESSENCE), 1.5f, 2.0f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.ENDER_PEARL), 1.0f, 0.25f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.EXPERIENCE_BOTTLE), 8.0f, 2.0f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.SCULK), 2.0f, 0.125f, 0.02f)
				),
				List.of(SpectrumItems.PURE_ECHO.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/emerald", Items.EMERALD, null, null, 60, InkColors.CYAN, 3, false,
				List.of(SpectrumBlocks.SMALL_EMERALD_BUD, SpectrumBlocks.LARGE_EMERALD_BUD, SpectrumBlocks.EMERALD_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.GUNPOWDER), 4.0f, 3.0f, 0.04f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.FROSTBITE_ESSENCE), 1.0f, 0.125f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.MIDNIGHT_CHIP), 16.0f, 4.0f, 0.2f)
				),
				List.of(SpectrumItems.PURE_EMERALD.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/glowstone", Items.GLOWSTONE, null, null, 120, InkColors.YELLOW, 2, false,
				List.of(SpectrumBlocks.SMALL_GLOWSTONE_BUD, SpectrumBlocks.LARGE_GLOWSTONE_BUD, SpectrumBlocks.GLOWSTONE_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.SHIMMERSTONE_GEM), 16.0f, 1.0f, 0.1f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.FROSTBITE_ESSENCE), 4.0f, 0.25f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.MOONSTONE_SHARD), 1.0f, 0.01f, 0.05f)
				),
				List.of(SpectrumItems.PURE_GLOWSTONE.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/gold", Items.RAW_GOLD, null, null, 60, InkColors.BROWN, 2, false,
				List.of(SpectrumBlocks.SMALL_GOLD_BUD, SpectrumBlocks.LARGE_GOLD_BUD, SpectrumBlocks.GOLD_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.GOLD_NUGGET), 4.0f, 0.5f, 0.2f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.SHIMMERSTONE_GEM), 8.0f, 2.0f, 0.05f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.NEOLITH), 1.5f, 0.25f, 0.02f)
				),
				List.of(SpectrumItems.PURE_GOLD.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/iron", Items.RAW_IRON, null, null, 60, InkColors.BROWN, 2, false,
				List.of(SpectrumBlocks.SMALL_IRON_BUD, SpectrumBlocks.LARGE_IRON_BUD, SpectrumBlocks.IRON_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.IRON_NUGGET), 4.0f, 0.5f, 0.2f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.BEDROCK_DUST), 8.0f, 2.0f, 0.05f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.NEOLITH), 1.5f, 0.25f, 0.02f)
				),
				List.of(SpectrumItems.PURE_IRON.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/lapis", Items.LAPIS_LAZULI, null, null, 60, InkColors.PURPLE, 2, false,
				List.of(SpectrumBlocks.SMALL_LAPIS_BUD, SpectrumBlocks.LARGE_LAPIS_BUD, SpectrumBlocks.LAPIS_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.EXPERIENCE_BOTTLE), 8.0f, 4.0f, 0.05f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.RAW_AZURITE), 0.5f, 0.1f, 0.004f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.MIDNIGHT_CHIP), 1.2f, 1.5f, 0.2f)
				),
				List.of(SpectrumItems.PURE_LAPIS.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/netherite_scrap", Items.NETHERITE_SCRAP, Fluids.LAVA, null, 960, InkColors.BROWN, 3, false,
				List.of(SpectrumBlocks.SMALL_NETHERITE_SCRAP_BUD, SpectrumBlocks.LARGE_NETHERITE_SCRAP_BUD, SpectrumBlocks.NETHERITE_SCRAP_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.INCANDESCENT_ESSENCE), 1.5f, 2.0f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.FIRE_CHARGE), 1.0f, 0.25f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.GOLD_INGOT), 16.0f, 2.5f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.STRATINE_FRAGMENTS), 2.0f, 0.125f, 0.02f)
				),
				List.of(SpectrumItems.PURE_NETHERITE_SCRAP.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/prismarine_crystal", Items.PRISMARINE_CRYSTALS, Fluids.WATER, null, 60, InkColors.CYAN, 2, false,
				List.of(SpectrumBlocks.SMALL_PRISMARINE_BUD, SpectrumBlocks.LARGE_PRISMARINE_BUD, SpectrumBlocks.PRISMARINE_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.PRISMARINE_SHARD), 2.0f, 0.5f, 0.04f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.WET_SPONGE), 1.0f, 0.7f, 0.0002f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.MERMAIDS_GEM), 32.0f, 2.0f, 0.1f)
				),
				List.of(SpectrumItems.PURE_PRISMARINE.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/quartz", Items.QUARTZ, Fluids.WATER, null, 180, InkColors.CYAN, 2, false,
				List.of(SpectrumBlocks.SMALL_QUARTZ_BUD, SpectrumBlocks.LARGE_QUARTZ_BUD, SpectrumBlocks.QUARTZ_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.SAND), 3.0f, 2.0f, 0.25f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.MIDNIGHT_CHIP), 1.0f, 0.25f, 0.01f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumBlocks.ROCK_CRYSTAL), 12.0f, 0.5f, 0.1f)
				),
				List.of(SpectrumItems.PURE_QUARTZ.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "minecraft/redstone", Items.REDSTONE, null, null, 60, InkColors.YELLOW, 2, false,
				List.of(SpectrumBlocks.SMALL_REDSTONE_BUD, SpectrumBlocks.LARGE_REDSTONE_BUD, SpectrumBlocks.REDSTONE_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.STORM_STONE), 8.0f, 2.0f, 0.01f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.SHIMMERSTONE_GEM), 1.0f, 0.25f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.STARDUST), 1.5f, 0.5f, 0.005f)
				),
				List.of(SpectrumItems.PURE_REDSTONE.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "spectrum/azurite", SpectrumItems.RAW_AZURITE, null, SpectrumAdvancements.COLLECT_AZURITE, 300, InkColors.BLUE, 4, false,
				List.of(SpectrumBlocks.SMALL_AZURITE_BUD, SpectrumBlocks.LARGE_AZURITE_BUD, SpectrumBlocks.AZURITE_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.RAW_COPPER), 7.5f, 10.0f, 0.05f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.COPPER_INGOT), 1.0f, 0.5f, 0.15f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.PURE_COPPER), 1.708f, 0.707f, 0.01f)
				),
				List.of(SpectrumItems.PURE_AZURITE.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "spectrum/bismuth", SpectrumItems.BISMUTH_FLAKE, null, null, 120, InkColors.CYAN, 4, false,
				List.of(SpectrumBlocks.SMALL_BISMUTH_BUD, SpectrumBlocks.LARGE_BISMUTH_BUD, SpectrumBlocks.BISMUTH_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.BISMUTH_FLAKE), 8.0f, 1.0f, 0.2f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.STARDUST), 2.0f, 0.25f, 0.02f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.STAR_FRAGMENT), 1.25f, 1.0f, 0.0002f)
				),
				List.of(SpectrumItems.BISMUTH_CRYSTAL.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "spectrum/bloodstone", SpectrumItems.RAW_BLOODSTONE, null, SpectrumAdvancements.UNLOCK_BLOODSTONE, 300, InkColors.RED, 4, false,
				List.of(SpectrumBlocks.SMALL_BLOODSTONE_BUD, SpectrumBlocks.LARGE_BLOODSTONE_BUD, SpectrumBlocks.BLOODSTONE_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.RAW_COPPER), 7.5f, 10.0f, 0.05f),
						new CrystallarieumCatalyst(Ingredient.ofItems(Items.COPPER_INGOT), 1.0f, 0.5f, 0.15f),
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.PURE_COPPER), 1.708f, 0.707f, 0.01f)
				),
				List.of(SpectrumItems.PURE_BLOODSTONE.getDefaultStack()));
		
		generateCrystallarieumRecipe(ctx, "spectrum/malachite", SpectrumItems.RAW_MALACHITE, null, SpectrumAdvancements.COLLECT_MALACHITE, 300, InkColors.WHITE, 4, false,
				List.of(SpectrumBlocks.SMALL_MALACHITE_BUD, SpectrumBlocks.LARGE_MALACHITE_BUD, SpectrumBlocks.MALACHITE_CLUSTER),
				List.of(
						new CrystallarieumCatalyst(Ingredient.ofItems(SpectrumItems.MOONSTONE_POWDER), 1.0f, 1.0f, 0.04f)
				),
				List.of(SpectrumItems.PURE_MALACHITE.getDefaultStack()));
	}
	
	private void generateEnchantmentUpgradeRecipes(RecipeExporter ctx) {
		//TODO These could benefit from a revisit
		
		// Spectrum
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.BIG_CATCH, SpectrumAdvancements.ENCHANTMENTS_BIG_CATCH, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{400, 32}, {800, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOVERS_FAVOR, SpectrumAdvancements.ENCHANTMENTS_CLOVERS_FAVOR, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{200, 8}, {400, 32}, {200, 128}, {10000, 512}, {40000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.DISARMING, SpectrumAdvancements.ENCHANTMENTS_DISARMING, SpectrumItems.RED_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.EXUBERANCE, SpectrumAdvancements.ENCHANTMENTS_EXUBERANCE, SpectrumItems.PURPLE_PIGMENT, new int[][]{{400, 8}, {600, 16}, {800, 32}, {1000, 64}, {1200, 128}, {1400, 256}, {1600, 512}, {1800, 512}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.FIRST_STRIKE, SpectrumAdvancements.ENCHANTMENTS_FIRST_STRIKE, SpectrumItems.PINK_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.IMPROVED_CRITICAL, SpectrumAdvancements.ENCHANTMENTS_IMPROVED_CRITICAL, SpectrumItems.BLACK_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.INERTIA, SpectrumAdvancements.ENCHANTMENTS_INERTIA, SpectrumItems.BROWN_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.RAZING, SpectrumAdvancements.ENCHANTMENTS_RAZING, SpectrumItems.GRAY_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 256}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.SERENDIPITY_REEL, SpectrumAdvancements.ENCHANTMENTS_SERENDIPITY_REEL, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{400, 32}, {800, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.SNIPING, SpectrumAdvancements.ENCHANTMENTS_SNIPING, SpectrumItems.GREEN_PIGMENT, new int[][]{{200, 8}, {1000, 32}, {5000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.TIGHT_GRIP, SpectrumAdvancements.ENCHANTMENTS_TIGHT_GRIP, SpectrumItems.YELLOW_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.TREASURE_HUNTER, SpectrumAdvancements.ENCHANTMENTS_TREASURE_HUNTER, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {10000, 512}});
		
		// Vanilla
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.BANE_OF_ARTHROPODS, SpectrumAdvancements.ENCHANTMENTS_VANILLA_DAMAGE, SpectrumItems.BLACK_PIGMENT, new int[][]{{100, 8}, {200, 16}, {300, 32}, {400, 64}, {800, 128}, {1200, 256}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.BLAST_PROTECTION, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROTECTION, SpectrumItems.PINK_PIGMENT, new int[][]{{100, 8}, {200, 16}, {300, 32}, {400, 64}, {800, 128}, {1200, 256}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.DEPTH_STRIDER, SpectrumAdvancements.ENCHANTMENTS_VANILLA_WATER, SpectrumItems.BLUE_PIGMENT, new int[][]{{200, 8}, {400, 32}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.EFFICIENCY, SpectrumAdvancements.ENCHANTMENTS_VANILLA_QUITOXIC, SpectrumItems.YELLOW_PIGMENT, new int[][]{{200, 8}, {400, 16}, {600, 32}, {800, 64}, {1600, 128}, {2600, 256}, {4000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.FEATHER_FALLING, SpectrumAdvancements.ENCHANTMENTS_VANILLA_QUITOXIC, SpectrumItems.BLUE_PIGMENT, new int[][]{{200, 8}, {400, 16}, {800, 32}, {1600, 64}, {4800, 256}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.FIRE_ASPECT, SpectrumAdvancements.ENCHANTMENTS_VANILLA_DAMAGE, SpectrumItems.RED_PIGMENT, new int[][]{{200, 8}, {2000, 32}, {8000, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.FIRE_PROTECTION, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROTECTION, SpectrumItems.PINK_PIGMENT, new int[][]{{100, 8}, {200, 16}, {300, 32}, {400, 64}, {800, 128}, {1300, 256}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.FORTUNE, SpectrumAdvancements.ENCHANTMENTS_VANILLA_LUCK, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{100, 8}, {400, 32}, {3000, 256}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.FROST_WALKER, SpectrumAdvancements.ENCHANTMENTS_VANILLA_TREASURE, SpectrumItems.LIGHT_GRAY_PIGMENT, new int[][]{{400, 8}, {1600, 32}, {3200, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.IMPALING, SpectrumAdvancements.ENCHANTMENTS_VANILLA_TRIDENT, SpectrumItems.BROWN_PIGMENT, new int[][]{{100, 8}, {200, 16}, {300, 32}, {400, 64}, {800, 128}, {1300, 256}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.KNOCKBACK, SpectrumAdvancements.ENCHANTMENTS_VANILLA_DAMAGE, SpectrumItems.BLACK_PIGMENT, new int[][]{{200, 8}, {1600, 32}, {3200, 128}, {6400, 256}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.LOOTING, SpectrumAdvancements.ENCHANTMENTS_VANILLA_LUCK, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{200, 8}, {500, 32}, {2400, 128}, {10000, 512}, {40000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.LOYALTY, SpectrumAdvancements.ENCHANTMENTS_VANILLA_TRIDENT, SpectrumItems.BROWN_PIGMENT, new int[][]{{200, 8}, {800, 32}, {3200, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.LUCK_OF_THE_SEA, SpectrumAdvancements.ENCHANTMENTS_VANILLA_WATER_LUCK, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {4000, 256}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.LURE, SpectrumAdvancements.ENCHANTMENTS_VANILLA_WATER, SpectrumItems.BLUE_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {4000, 256}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.PIERCING, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROJECTILE, SpectrumItems.RED_PIGMENT, new int[][]{{100, 8}, {200, 16}, {300, 32}, {400, 64}, {800, 128}, {1300, 256}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.POWER, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROJECTILE, SpectrumItems.RED_PIGMENT, new int[][]{{200, 8}, {400, 16}, {600, 32}, {800, 64}, {1600, 128}, {2600, 256}, {4000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.PROJECTILE_PROTECTION, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROTECTION, SpectrumItems.PINK_PIGMENT, new int[][]{{100, 8}, {200, 16}, {300, 32}, {400, 64}, {800, 128}, {1300, 256}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.PROTECTION, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROTECTION, SpectrumItems.PINK_PIGMENT, new int[][]{{200, 8}, {400, 16}, {600, 32}, {800, 64}, {1600, 128}, {4000, 256}, {8000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.PUNCH, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROJECTILE, SpectrumItems.RED_PIGMENT, new int[][]{{200, 8}, {1600, 32}, {3200, 128}, {6400, 256}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.QUICK_CHARGE, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROJECTILE, SpectrumItems.RED_PIGMENT, new int[][]{{200, 8}, {1600, 32}, {5000, 512}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.RESPIRATION, SpectrumAdvancements.ENCHANTMENTS_VANILLA_WATER, SpectrumItems.BLUE_PIGMENT, new int[][]{{100, 8}, {200, 32}, {1600, 128}, {4800, 256}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.RIPTIDE, SpectrumAdvancements.ENCHANTMENTS_VANILLA_TRIDENT, SpectrumItems.BROWN_PIGMENT, new int[][]{{200, 8}, {2400, 32}, {10000, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.SHARPNESS, SpectrumAdvancements.ENCHANTMENTS_VANILLA_DAMAGE, SpectrumItems.BLACK_PIGMENT, new int[][]{{200, 8}, {400, 16}, {600, 32}, {800, 64}, {1600, 128}, {4000, 256}, {8000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.SMITE, SpectrumAdvancements.ENCHANTMENTS_VANILLA_DAMAGE, SpectrumItems.BLACK_PIGMENT, new int[][]{{100, 8}, {200, 16}, {300, 32}, {400, 64}, {800, 128}, {1300, 256}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.SOUL_SPEED, SpectrumAdvancements.ENCHANTMENTS_VANILLA_TREASURE, SpectrumItems.LIGHT_GRAY_PIGMENT, new int[][]{{200, 8}, {2400, 32}, {10000, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.SWEEPING_EDGE, SpectrumAdvancements.ENCHANTMENTS_VANILLA_DAMAGE, SpectrumItems.RED_PIGMENT, new int[][]{{100, 8}, {400, 32}, {1000, 64}, {2000, 128}, {5000, 256}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.SWIFT_SNEAK, SpectrumAdvancements.ENCHANTMENTS_VANILLA_SWIFT_SNEAK, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{200, 8}, {600, 32}, {2000, 128}, {5000, 256}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.THORNS, SpectrumAdvancements.ENCHANTMENTS_VANILLA_PROTECTION, SpectrumItems.PINK_PIGMENT, new int[][]{{100, 8}, {400, 32}, {2000, 128}, {5000, 256}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "vanilla", Enchantments.UNBREAKING, SpectrumAdvancements.ENCHANTMENTS_VANILLA_UNBREAKING, SpectrumItems.CYAN_PIGMENT, new int[][]{{100, 8}, {400, 32}, {200, 512}, {4000, 512}, {10000, 512}});
	}
	
	private void generateCrystallarieumRecipe(RecipeExporter ctx, String id, Item base, @Nullable Fluid medium, @Nullable Identifier advancement, int secondsPerStage, InkColor inkColor, int inkCostTier, boolean growsWithoutCatalyst, List<Block> stages, List<CrystallarieumCatalyst> catalysts, List<ItemStack> additionalResults) {
		generateRecipe(ctx, "crystallarieum/" + id, new CrystallarieumRecipe("", false, Optional.ofNullable(advancement), Ingredient.ofItems(base),
				stages.stream().map(s -> s.getDefaultState().with(Properties.FACING, Direction.UP)).toList(),
				secondsPerStage, inkColor, 1 << (inkCostTier - 1), growsWithoutCatalyst,
				catalysts,
				FluidVariant.of(medium == null ? SpectrumFluids.LIQUID_CRYSTAL : medium),
				additionalResults));
	}
	
	private void generateEnchantmentUpgradeRecipes(RecipeExporter ctx, String group, RegistryKey<Enchantment> enchantment, Identifier advancement, Item requiredItem, int[][] levels) {
		ctx = withConditions(ctx, new SpectrumResourceConditions.EnchantmentsExistResourceCondition(List.of(enchantment)));
		String namespace = enchantment.getValue().getNamespace();
		String base = "enchantment_upgrade/" + namespace + "/" + enchantment.getValue().getPath().replace("cloaked/", "").replace("/", ".") + "/";
		
		for (int i = 0; i < levels.length; i++) {
			generateRecipe(ctx, base + (i + 1) + "_to_" + (i + 2), new EnchantmentUpgradeRecipe(group, false, Optional.of(advancement), Either.right(enchantment), i + 2, levels[i][0], requiredItem, levels[i][1]));
		}
	}
	
	private void generateRecipe(RecipeExporter ctx, String id, Recipe<?> recipe) {
		ctx.accept(SpectrumCommon.locate(id), recipe, null);
	}
	
}
