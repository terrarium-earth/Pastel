package de.dafuqs.spectrum.loot;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.mob_head.*;
import de.dafuqs.spectrum.compat.gofish.*;
import de.dafuqs.spectrum.entity.predicates.*;
import de.dafuqs.spectrum.loot.functions.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.loot.v3.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.loot.entry.*;
import net.minecraft.loot.provider.number.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SpectrumLootPoolModifiers {
	
	// TODO: Make data driven / introduce a constant for the 0.02 drops
	private static final Map<RegistryKey<LootTable>, TreasureHunterDropDefinition> treasureHunterLootPools = new HashMap<>() {{
		// Additional vanilla head drops
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/creeper")), new TreasureHunterDropDefinition(Items.CREEPER_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/skeleton")), new TreasureHunterDropDefinition(Items.SKELETON_SKULL, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wither_skeleton")), new TreasureHunterDropDefinition(Items.WITHER_SKELETON_SKULL, 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombie")), new TreasureHunterDropDefinition(Items.ZOMBIE_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/piglin")), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/piglin_brute")), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ender_dragon")), new TreasureHunterDropDefinition(Items.DRAGON_HEAD, 0.35F)); // why not!
		
		// Spectrum head drops
		// ATTENTION: No specific enough loot tables exist for fox, axolotl, parrot and shulker variants.
		// Those are handled separately in setup()
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/sheep")), new TreasureHunterDropDefinition(SpectrumSkullType.SHEEP, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/bat")), new TreasureHunterDropDefinition(SpectrumSkullType.BAT, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/blaze")), new TreasureHunterDropDefinition(SpectrumSkullType.BLAZE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/cat")), new TreasureHunterDropDefinition(SpectrumSkullType.CAT, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/cave_spider")), new TreasureHunterDropDefinition(SpectrumSkullType.CAVE_SPIDER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/chicken")), new TreasureHunterDropDefinition(SpectrumSkullType.CHICKEN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/cow")), new TreasureHunterDropDefinition(SpectrumSkullType.COW, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/donkey")), new TreasureHunterDropDefinition(SpectrumSkullType.DONKEY, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/drowned")), new TreasureHunterDropDefinition(SpectrumSkullType.DROWNED, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/elder_guardian")), new TreasureHunterDropDefinition(SpectrumSkullType.ELDER_GUARDIAN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/enderman")), new TreasureHunterDropDefinition(SpectrumSkullType.ENDERMAN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/endermite")), new TreasureHunterDropDefinition(SpectrumSkullType.ENDERMITE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/evoker")), new TreasureHunterDropDefinition(SpectrumSkullType.EVOKER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ghast")), new TreasureHunterDropDefinition(SpectrumSkullType.GHAST, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/guardian")), new TreasureHunterDropDefinition(SpectrumSkullType.GUARDIAN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/hoglin")), new TreasureHunterDropDefinition(SpectrumSkullType.HOGLIN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/horse")), new TreasureHunterDropDefinition(SpectrumSkullType.HORSE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/husk")), new TreasureHunterDropDefinition(SpectrumSkullType.HUSK, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/illusioner")), new TreasureHunterDropDefinition(SpectrumSkullType.ILLUSIONER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/iron_golem")), new TreasureHunterDropDefinition(SpectrumSkullType.IRON_GOLEM, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/llama")), new TreasureHunterDropDefinition(SpectrumSkullType.LLAMA, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/magma_cube")), new TreasureHunterDropDefinition(SpectrumSkullType.MAGMA_CUBE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/mule")), new TreasureHunterDropDefinition(SpectrumSkullType.MULE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ocelot")), new TreasureHunterDropDefinition(SpectrumSkullType.OCELOT, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/panda")), new TreasureHunterDropDefinition(SpectrumSkullType.PANDA, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/phantom")), new TreasureHunterDropDefinition(SpectrumSkullType.PHANTOM, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/pig")), new TreasureHunterDropDefinition(SpectrumSkullType.PIG, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/polar_bear")), new TreasureHunterDropDefinition(SpectrumSkullType.POLAR_BEAR, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/pufferfish")), new TreasureHunterDropDefinition(SpectrumSkullType.PUFFERFISH, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/rabbit")), new TreasureHunterDropDefinition(SpectrumSkullType.RABBIT, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/ravager")), new TreasureHunterDropDefinition(SpectrumSkullType.RAVAGER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/salmon")), new TreasureHunterDropDefinition(SpectrumSkullType.SALMON, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/silverfish")), new TreasureHunterDropDefinition(SpectrumSkullType.SILVERFISH, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/slime")), new TreasureHunterDropDefinition(SpectrumSkullType.SLIME, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/snow_golem")), new TreasureHunterDropDefinition(SpectrumSkullType.SNOW_GOLEM, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/spider")), new TreasureHunterDropDefinition(SpectrumSkullType.SPIDER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/squid")), new TreasureHunterDropDefinition(SpectrumSkullType.SQUID, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/stray")), new TreasureHunterDropDefinition(SpectrumSkullType.STRAY, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/strider")), new TreasureHunterDropDefinition(SpectrumSkullType.STRIDER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/trader_llama")), new TreasureHunterDropDefinition(SpectrumSkullType.LLAMA, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/turtle")), new TreasureHunterDropDefinition(SpectrumSkullType.TURTLE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/vex")), new TreasureHunterDropDefinition(SpectrumSkullType.VEX, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/villager")), new TreasureHunterDropDefinition(SpectrumSkullType.VILLAGER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/vindicator")), new TreasureHunterDropDefinition(SpectrumSkullType.VINDICATOR, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wandering_trader")), new TreasureHunterDropDefinition(SpectrumSkullType.WANDERING_TRADER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/witch")), new TreasureHunterDropDefinition(SpectrumSkullType.WITCH, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wither")), new TreasureHunterDropDefinition(SpectrumSkullType.WITHER, 0.15F)); // he has 3 heads, after all!
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/wolf")), new TreasureHunterDropDefinition(SpectrumSkullType.WOLF, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zoglin")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOGLIN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombie_villager")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOMBIE_VILLAGER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombified_piglin")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOMBIFIED_PIGLIN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/bee")), new TreasureHunterDropDefinition(SpectrumSkullType.BEE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/tropical_fish")), new TreasureHunterDropDefinition(SpectrumSkullType.TROPICAL_FISH, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/goat")), new TreasureHunterDropDefinition(SpectrumSkullType.GOAT, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/glow_squid")), new TreasureHunterDropDefinition(SpectrumSkullType.GLOW_SQUID, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/warden")), new TreasureHunterDropDefinition(SpectrumSkullType.WARDEN, 0.2F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/tadpole")), new TreasureHunterDropDefinition(SpectrumSkullType.TADPOLE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/allay")), new TreasureHunterDropDefinition(SpectrumSkullType.ALLAY, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/camel")), new TreasureHunterDropDefinition(SpectrumSkullType.CAMEL, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/sniffer")), new TreasureHunterDropDefinition(SpectrumSkullType.SNIFFER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/skeleton_horse")), new TreasureHunterDropDefinition(SpectrumSkullType.SKELETON_HORSE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/zombie_horse")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOMBIE_HORSE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/dolphin")), new TreasureHunterDropDefinition(SpectrumSkullType.DOLPHIN, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/pillager")), new TreasureHunterDropDefinition(SpectrumSkullType.PILLAGER, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/armadillo")), new TreasureHunterDropDefinition(SpectrumSkullType.ARMADILLO, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/breeze")), new TreasureHunterDropDefinition(SpectrumSkullType.BREEZE, 0.02F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/bogged")), new TreasureHunterDropDefinition(SpectrumSkullType.BOGGED, 0.02F));
		
		// TODO: ...ooooor we could simply put them in the json loot table
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("entities/egg_laying_wooly_pig")), new TreasureHunterDropDefinition(SpectrumSkullType.EGG_LAYING_WOOLY_PIG, 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("entities/kindling")), new TreasureHunterDropDefinition(SpectrumSkullType.KINDLING, 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("entities/preservation_turret")), new TreasureHunterDropDefinition(SpectrumSkullType.PRESERVATION_TURRET, 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("entities/monstrosity")), new TreasureHunterDropDefinition(SpectrumSkullType.MONSTROSITY, 0.1F));
		put(RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("entities/eraser")), new TreasureHunterDropDefinition(SpectrumSkullType.ERASER, 0.1F));
	}};
	
	public static void setup() {
		LootTableEvents.MODIFY.register((key, builder, lootTableSource, wrapperLookup) -> {
			// Treasure hunter pools
			
			if (treasureHunterLootPools.containsKey(key)) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				TreasureHunterDropDefinition dropDefinition = treasureHunterLootPools.get(key);
				builder.pool(getLootPool(enchant, dropDefinition));
				// Some treasure hunter pools use custom loot conditions
				// because vanillas are too generic (fox/snow fox both use "fox" loot table)
			} else if (key.equals(LootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY) || key.equals(LootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY)
					|| key.equals(LootTables.DESERT_PYRAMID_ARCHAEOLOGY) || key.equals(LootTables.DESERT_WELL_ARCHAEOLOGY)) {
				builder.modifyPools(modifier -> modifier.with(ItemEntry.builder(SpectrumItems.NIGHTDEW_SPROUT).weight(2).quality(-1)));
			} else if (key.equals(LootTables.TRAIL_RUINS_RARE_ARCHAEOLOGY)) {
				builder.modifyPools(modifier -> modifier.with(ItemEntry.builder(SpectrumItems.NIGHTDEW_SPROUT).weight(3).quality(-1)));
			} else if (key.equals(LootTables.SNIFFER_DIGGING_GAMEPLAY)) {
				builder.modifyPools(modifier -> {
					modifier.with(ItemEntry.builder(SpectrumBlocks.WEEPING_GALA_SPRIG).weight(1));
					modifier.with(ItemEntry.builder(SpectrumItems.NIGHTDEW_SPROUT).weight(2));
				});
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/fox")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getFoxLootPool(enchant, FoxEntity.Type.RED, new TreasureHunterDropDefinition(SpectrumSkullType.FOX, 0.02F)));
				builder.pool(getFoxLootPool(enchant, FoxEntity.Type.SNOW, new TreasureHunterDropDefinition(SpectrumSkullType.FOX_ARCTIC, 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/mooshroom")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getMooshroomLootPool(enchant, MooshroomEntity.Type.BROWN, new TreasureHunterDropDefinition(SpectrumSkullType.MOOSHROOM_BROWN, 0.02F)));
				builder.pool(getMooshroomLootPool(enchant, MooshroomEntity.Type.RED, new TreasureHunterDropDefinition(SpectrumSkullType.MOOSHROOM_RED, 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/shulker")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getShulkerLootPool(enchant, null, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.BLACK, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_BLACK, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_BLUE, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.BROWN, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_BROWN, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.CYAN, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_CYAN, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_GRAY, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.GREEN, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_GREEN, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.LIGHT_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_LIGHT_BLUE, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.LIGHT_GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_LIGHT_GRAY, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.LIME, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_LIME, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.MAGENTA, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_MAGENTA, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.ORANGE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_ORANGE, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.PINK, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_PINK, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.PURPLE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_PURPLE, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.RED, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_RED, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.WHITE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_WHITE, 0.05F)));
				builder.pool(getShulkerLootPool(enchant, DyeColor.YELLOW, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_YELLOW, 0.05F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("entities/lizard")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getLizardLootPool(enchant, InkColors.BLACK, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_BLACK, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_BLUE, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.BROWN, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_BROWN, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.CYAN, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_CYAN, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_GRAY, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.GREEN, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_GREEN, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.LIGHT_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_LIGHT_BLUE, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.LIGHT_GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_LIGHT_GRAY, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.LIME, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_LIME, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.MAGENTA, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_MAGENTA, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.ORANGE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_ORANGE, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.PINK, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_PINK, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.PURPLE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_PURPLE, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.RED, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_RED, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.WHITE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_WHITE, 0.05F)));
				builder.pool(getLizardLootPool(enchant, InkColors.YELLOW, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_YELLOW, 0.05F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/axolotl")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_BLUE, 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.CYAN, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_CYAN, 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.GOLD, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_GOLD, 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.LUCY, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_LEUCISTIC, 0.02F)));
				builder.pool(getAxolotlLootPool(enchant, AxolotlEntity.Variant.WILD, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_WILD, 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/parrot")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.RED_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_RED, 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_BLUE, 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.GREEN, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_GREEN, 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.YELLOW_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_CYAN, 0.02F)));
				builder.pool(getParrotLootPool(enchant, ParrotEntity.Variant.GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_GRAY, 0.02F)));
			} else if (key.equals(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("entities/frog")))) {
				RegistryEntry.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
				builder.pool(getFrogLootPool(enchant, FrogVariant.TEMPERATE, new TreasureHunterDropDefinition(SpectrumSkullType.FROG_TEMPERATE, 0.02F)));
				builder.pool(getFrogLootPool(enchant, FrogVariant.COLD, new TreasureHunterDropDefinition(SpectrumSkullType.FROG_COLD, 0.02F)));
				builder.pool(getFrogLootPool(enchant, FrogVariant.WARM, new TreasureHunterDropDefinition(SpectrumSkullType.FROG_WARM, 0.02F)));
			} else if (GoFishCompat.isLoaded()) {
				//Go-Fish compat: fishing of crates & go-fish fishies
				if (key.equals(SpectrumLootTables.LAVA_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.NETHER_FISH_LOOT_TABLE_ID).weight(80).quality(-1).build()));
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.NETHER_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.END_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.END_FISH_LOOT_TABLE_ID).weight(90).quality(-1).build()));
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.END_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.DEEPER_DOWN_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.GOO_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.LIQUID_CRYSTAL_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				} else if (key.equals(SpectrumLootTables.MIDNIGHT_SOLUTION_FISHING)) {
					builder.modifyPools(modifier -> modifier.with(LootTableEntry.builder(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).weight(5).quality(2).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().typeSpecific(FishingHookPredicate.of(true)).build()))));
				}
			}
		});
	}
	
	private static RegistryEntry.Reference<Enchantment> getTreasureHunter(RegistryWrapper.WrapperLookup wrapperLookup) {
		RegistryWrapper.Impl<Enchantment> wrapper = wrapperLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
		return wrapper.getOrThrow(SpectrumEnchantments.TREASURE_HUNTER);
	}
	
	public static LootCondition.Builder treasureHunter(RegistryEntry.Reference<Enchantment> enchantment, float chance) {
		return AnyOfLootCondition.builder(
				DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create().tag(TagPredicate.expected(SpectrumDamageTypeTags.ALWAYS_DROPS_MOB_HEAD))),
				() -> new RandomChanceWithEnchantedBonusLootCondition(0.0F, new EnchantmentLevelBasedValue.Linear(chance + chance, chance), enchantment)
		);
	}
	
	private static LootPool getLootPool(RegistryEntry.Reference<Enchantment> enchantment, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getFoxLootPool(RegistryEntry.Reference<Enchantment> enchantment, FoxEntity.Type foxType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.FOX.createPredicate(foxType)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getMooshroomLootPool(RegistryEntry.Reference<Enchantment> enchantment, MooshroomEntity.Type mooshroomType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.MOOSHROOM.createPredicate(mooshroomType)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getShulkerLootPool(RegistryEntry.Reference<Enchantment> enchantment, @Nullable DyeColor dyeColor, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(new ShulkerPredicate(Optional.ofNullable(dyeColor))).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getLizardLootPool(RegistryEntry.Reference<Enchantment> enchantment, InkColor linkColor, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(new LizardPredicate(Optional.of(linkColor), Optional.empty(), Optional.empty())).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getAxolotlLootPool(RegistryEntry.Reference<Enchantment> enchantment, AxolotlEntity.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.AXOLOTL.createPredicate(variant)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getFrogLootPool(RegistryEntry.Reference<Enchantment> enchantment, RegistryKey<FrogVariant> variant, TreasureHunterDropDefinition dropDefinition) {
		RegistryEntry<FrogVariant> entry = Registries.FROG_VARIANT.entryOf(variant);
		
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.FROG.createPredicate(RegistryEntryList.of(entry))).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private static LootPool getParrotLootPool(RegistryEntry.Reference<Enchantment> enchantment, ParrotEntity.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(treasureHunter(enchantment, dropDefinition.chancePerLevel).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(EntitySubPredicateTypes.PARROT.createPredicate(variant)).build()).build())
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.with(ItemEntry.builder(dropDefinition.drop).build())
				.build();
	}
	
	private record TreasureHunterDropDefinition(Item drop, float chancePerLevel, Identifier advancementUnlockId) {
		
		public TreasureHunterDropDefinition(Item drop, float chancePerLevel) {
			this(drop, chancePerLevel, Registries.ITEM.getId(drop));
		}
		
		public TreasureHunterDropDefinition(SpectrumSkullType skullType, float chancePerLevel) {
			this(SpectrumSkullBlock.getBlock(skullType).orElse(Blocks.AIR).asItem(), chancePerLevel);
		}
		
	}
	
}
