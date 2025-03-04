package de.dafuqs.spectrum.data;

import java.util.*;
import java.util.concurrent.*;

import com.mojang.datafixers.util.*;
import de.dafuqs.spectrum.recipe.enchanter.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.data.server.recipe.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

public class SpectrumRecipeProvider extends FabricRecipeProvider {
	
	public SpectrumRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	public void generate(RecipeExporter ctx) {
		generateEnchantmentUpgradeRecipes(ctx);
	}
	
	private void generateEnchantmentUpgradeRecipes(RecipeExporter ctx) {
		// TODO These values might need some touch-ups, clover's favor and unbreaking in particular
		
		// Spectrum
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_BIG_CATCH, SpectrumAdvancements.ENCHANTMENTS_BIG_CATCH, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{400, 32}, {800, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_CLOVERS_FAVOR, SpectrumAdvancements.ENCHANTMENTS_CLOVERS_FAVOR, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{200, 8}, {400, 32}, {200, 128}, {10000, 512}, {40000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_DISARMING, SpectrumAdvancements.ENCHANTMENTS_DISARMING, SpectrumItems.RED_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_EXUBERANCE, SpectrumAdvancements.ENCHANTMENTS_EXUBERANCE, SpectrumItems.PURPLE_PIGMENT, new int[][]{{400, 8}, {600, 16}, {800, 32}, {1000, 64}, {1200, 128}, {1400, 256}, {1600, 512}, {1800, 512}, {2000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_FIRST_STRIKE, SpectrumAdvancements.ENCHANTMENTS_FIRST_STRIKE, SpectrumItems.PINK_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_IMPROVED_CRITICAL, SpectrumAdvancements.ENCHANTMENTS_IMPROVED_CRITICAL, SpectrumItems.BLACK_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_INERTIA, SpectrumAdvancements.ENCHANTMENTS_INERTIA, SpectrumItems.BROWN_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_RAZING, SpectrumAdvancements.ENCHANTMENTS_RAZING, SpectrumItems.GRAY_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 256}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_SERENDIPITY_REEL, SpectrumAdvancements.ENCHANTMENTS_SERENDIPITY_REEL, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{400, 32}, {800, 128}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_SNIPING, SpectrumAdvancements.ENCHANTMENTS_SNIPING, SpectrumItems.GREEN_PIGMENT, new int[][]{{200, 8}, {1000, 32}, {5000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_TIGHT_GRIP, SpectrumAdvancements.ENCHANTMENTS_TIGHT_GRIP, SpectrumItems.YELLOW_PIGMENT, new int[][]{{400, 32}, {2000, 128}, {10000, 512}});
		generateEnchantmentUpgradeRecipes(ctx, "", SpectrumEnchantments.CLOAKED_TREASURE_HUNTER, SpectrumAdvancements.ENCHANTMENTS_TREASURE_HUNTER, SpectrumItems.LIGHT_BLUE_PIGMENT, new int[][]{{200, 8}, {400, 32}, {2000, 128}, {10000, 512}});
		
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
	
	private void generateEnchantmentUpgradeRecipes(RecipeExporter ctx, String group, RegistryKey<Enchantment> enchantment, Identifier advancement, Item requiredItem, int[][] levels) {
		ctx = withConditions(ctx, new SpectrumResourceConditions.EnchantmentsExistResourceCondition(List.of(enchantment)));
		String namespace = enchantment.getValue().getNamespace();
		String base = "enchantment_upgrade/" + namespace + "/" + enchantment.getValue().getPath().replace("cloaked/", "").replace("/", ".") + "/";
		
		for (int i = 0; i < levels.length; i++) {
			ctx.accept(
					Identifier.of(namespace, base + (i + 1) + "_to_" + (i + 2)),
					new EnchantmentUpgradeRecipe(group, false, Optional.of(advancement), Either.right(enchantment), i + 2, levels[i][0], requiredItem, levels[i][1]),
					null);
		}
	}
	
}
