package earth.terrarium.pastel.loot;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.mob_head.SpectrumSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.SpectrumSkullType;
import earth.terrarium.pastel.compat.gofish.GoFishCompat;
import earth.terrarium.pastel.entity.predicates.LizardPredicate;
import earth.terrarium.pastel.entity.predicates.ShulkerPredicate;
import earth.terrarium.pastel.loot.functions.GrantAdvancementLootFunction;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumDamageTypeTags;
import earth.terrarium.pastel.registries.SpectrumEnchantments;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumLootTables;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpectrumLootPoolModifiers {
	
	// TODO: Make data driven / introduce a constant for the 0.02 drops
	private static final Map<ResourceKey<LootTable>, TreasureHunterDropDefinition> treasureHunterLootPools = new HashMap<>() {{
		// Additional vanilla head drops
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/creeper")), new TreasureHunterDropDefinition(Items.CREEPER_HEAD, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/skeleton")), new TreasureHunterDropDefinition(Items.SKELETON_SKULL, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/wither_skeleton")), new TreasureHunterDropDefinition(Items.WITHER_SKELETON_SKULL, 0.1F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/zombie")), new TreasureHunterDropDefinition(Items.ZOMBIE_HEAD, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/piglin")), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/piglin_brute")), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/ender_dragon")), new TreasureHunterDropDefinition(Items.DRAGON_HEAD, 0.35F)); // why not!
		
		// Spectrum head drops
		// ATTENTION: No specific enough loot tables exist for fox, axolotl, parrot and shulker variants.
		// Those are handled separately in setup()
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/sheep")), new TreasureHunterDropDefinition(SpectrumSkullType.SHEEP, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/bat")), new TreasureHunterDropDefinition(SpectrumSkullType.BAT, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/blaze")), new TreasureHunterDropDefinition(SpectrumSkullType.BLAZE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/cat")), new TreasureHunterDropDefinition(SpectrumSkullType.CAT, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/cave_spider")), new TreasureHunterDropDefinition(SpectrumSkullType.CAVE_SPIDER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/chicken")), new TreasureHunterDropDefinition(SpectrumSkullType.CHICKEN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/cow")), new TreasureHunterDropDefinition(SpectrumSkullType.COW, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/donkey")), new TreasureHunterDropDefinition(SpectrumSkullType.DONKEY, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/drowned")), new TreasureHunterDropDefinition(SpectrumSkullType.DROWNED, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/elder_guardian")), new TreasureHunterDropDefinition(SpectrumSkullType.ELDER_GUARDIAN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/enderman")), new TreasureHunterDropDefinition(SpectrumSkullType.ENDERMAN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/endermite")), new TreasureHunterDropDefinition(SpectrumSkullType.ENDERMITE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/evoker")), new TreasureHunterDropDefinition(SpectrumSkullType.EVOKER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/ghast")), new TreasureHunterDropDefinition(SpectrumSkullType.GHAST, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/guardian")), new TreasureHunterDropDefinition(SpectrumSkullType.GUARDIAN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/hoglin")), new TreasureHunterDropDefinition(SpectrumSkullType.HOGLIN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/horse")), new TreasureHunterDropDefinition(SpectrumSkullType.HORSE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/husk")), new TreasureHunterDropDefinition(SpectrumSkullType.HUSK, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/illusioner")), new TreasureHunterDropDefinition(SpectrumSkullType.ILLUSIONER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/iron_golem")), new TreasureHunterDropDefinition(SpectrumSkullType.IRON_GOLEM, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/llama")), new TreasureHunterDropDefinition(SpectrumSkullType.LLAMA, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/magma_cube")), new TreasureHunterDropDefinition(SpectrumSkullType.MAGMA_CUBE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/mule")), new TreasureHunterDropDefinition(SpectrumSkullType.MULE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/ocelot")), new TreasureHunterDropDefinition(SpectrumSkullType.OCELOT, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/panda")), new TreasureHunterDropDefinition(SpectrumSkullType.PANDA, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/phantom")), new TreasureHunterDropDefinition(SpectrumSkullType.PHANTOM, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/pig")), new TreasureHunterDropDefinition(SpectrumSkullType.PIG, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/polar_bear")), new TreasureHunterDropDefinition(SpectrumSkullType.POLAR_BEAR, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/pufferfish")), new TreasureHunterDropDefinition(SpectrumSkullType.PUFFERFISH, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/rabbit")), new TreasureHunterDropDefinition(SpectrumSkullType.RABBIT, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/ravager")), new TreasureHunterDropDefinition(SpectrumSkullType.RAVAGER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/salmon")), new TreasureHunterDropDefinition(SpectrumSkullType.SALMON, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/silverfish")), new TreasureHunterDropDefinition(SpectrumSkullType.SILVERFISH, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/slime")), new TreasureHunterDropDefinition(SpectrumSkullType.SLIME, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/snow_golem")), new TreasureHunterDropDefinition(SpectrumSkullType.SNOW_GOLEM, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/spider")), new TreasureHunterDropDefinition(SpectrumSkullType.SPIDER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/squid")), new TreasureHunterDropDefinition(SpectrumSkullType.SQUID, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/stray")), new TreasureHunterDropDefinition(SpectrumSkullType.STRAY, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/strider")), new TreasureHunterDropDefinition(SpectrumSkullType.STRIDER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/trader_llama")), new TreasureHunterDropDefinition(SpectrumSkullType.LLAMA, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/turtle")), new TreasureHunterDropDefinition(SpectrumSkullType.TURTLE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/vex")), new TreasureHunterDropDefinition(SpectrumSkullType.VEX, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/villager")), new TreasureHunterDropDefinition(SpectrumSkullType.VILLAGER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/vindicator")), new TreasureHunterDropDefinition(SpectrumSkullType.VINDICATOR, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/wandering_trader")), new TreasureHunterDropDefinition(SpectrumSkullType.WANDERING_TRADER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/witch")), new TreasureHunterDropDefinition(SpectrumSkullType.WITCH, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/adder")), new TreasureHunterDropDefinition(SpectrumSkullType.WITHER, 0.15F)); // he has 3 heads, after all!
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/wolf")), new TreasureHunterDropDefinition(SpectrumSkullType.WOLF, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/zoglin")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOGLIN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/zombie_villager")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOMBIE_VILLAGER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/zombified_piglin")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOMBIFIED_PIGLIN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/bee")), new TreasureHunterDropDefinition(SpectrumSkullType.BEE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/tropical_fish")), new TreasureHunterDropDefinition(SpectrumSkullType.TROPICAL_FISH, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/goat")), new TreasureHunterDropDefinition(SpectrumSkullType.GOAT, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/glow_squid")), new TreasureHunterDropDefinition(SpectrumSkullType.GLOW_SQUID, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/warden")), new TreasureHunterDropDefinition(SpectrumSkullType.WARDEN, 0.2F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/tadpole")), new TreasureHunterDropDefinition(SpectrumSkullType.TADPOLE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/allay")), new TreasureHunterDropDefinition(SpectrumSkullType.ALLAY, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/camel")), new TreasureHunterDropDefinition(SpectrumSkullType.CAMEL, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/sniffer")), new TreasureHunterDropDefinition(SpectrumSkullType.SNIFFER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/skeleton_horse")), new TreasureHunterDropDefinition(SpectrumSkullType.SKELETON_HORSE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/zombie_horse")), new TreasureHunterDropDefinition(SpectrumSkullType.ZOMBIE_HORSE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/dolphin")), new TreasureHunterDropDefinition(SpectrumSkullType.DOLPHIN, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/pillager")), new TreasureHunterDropDefinition(SpectrumSkullType.PILLAGER, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/armadillo")), new TreasureHunterDropDefinition(SpectrumSkullType.ARMADILLO, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/breeze")), new TreasureHunterDropDefinition(SpectrumSkullType.BREEZE, 0.02F));
		put(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("entities/bogged")), new TreasureHunterDropDefinition(SpectrumSkullType.BOGGED, 0.02F));
		
		// TODO: ...ooooor we could simply put them in the json loot table
		put(ResourceKey.create(Registries.LOOT_TABLE, SpectrumCommon.locate("entities/egg_laying_wooly_pig")), new TreasureHunterDropDefinition(SpectrumSkullType.EGG_LAYING_WOOLY_PIG, 0.1F));
		put(ResourceKey.create(Registries.LOOT_TABLE, SpectrumCommon.locate("entities/kindling")), new TreasureHunterDropDefinition(SpectrumSkullType.KINDLING, 0.1F));
		put(ResourceKey.create(Registries.LOOT_TABLE, SpectrumCommon.locate("entities/preservation_turret")), new TreasureHunterDropDefinition(SpectrumSkullType.PRESERVATION_TURRET, 0.1F));
		put(ResourceKey.create(Registries.LOOT_TABLE, SpectrumCommon.locate("entities/eraser")), new TreasureHunterDropDefinition(SpectrumSkullType.ERASER, 0.1F));
	}};

	private static void addEntry(LootPool pool, LootPoolEntryContainer.Builder<?> entry) {
		// TODO
		pool.entries.add(entry.build());
	}

	// TODO Migrate to loot modifiers add AddTableLootModifier and custom LootModifiers
	public static void loadLootTable(LootTableLoadEvent event) {
		ResourceLocation key = event.getName();
		LootTable table = event.getTable();
		HolderLookup.Provider wrapperLookup = null;

		// Treasure hunter pools
		if (treasureHunterLootPools.containsKey(key)) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			TreasureHunterDropDefinition dropDefinition = treasureHunterLootPools.get(key);
			table.addPool(getLootPool(enchant, dropDefinition));
			// Some treasure hunter pools use custom loot conditions
			// because vanillas are too generic (fox/snow fox both use "fox" loot table)
		} else if (key.equals(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY) || key.equals(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY)
				|| key.equals(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY) || key.equals(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY)) {
			for (LootPool pool : table.pools) {
				addEntry(pool, LootItem.lootTableItem(SpectrumItems.NIGHTDEW_SPROUT.get()).setWeight(2).setQuality(-1));
			}
		} else if (key.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE)) {
			for (LootPool pool : table.pools) {
				addEntry(pool, LootItem.lootTableItem(SpectrumItems.NIGHTDEW_SPROUT.get()).setWeight(3).setQuality(-1));
			}
		} else if (key.equals(BuiltInLootTables.SNIFFER_DIGGING)) {
			for (LootPool pool : table.pools) {
				addEntry(pool, LootItem.lootTableItem(SpectrumBlocks.WEEPING_GALA_SPRIG.get()).setWeight(1));
				addEntry(pool, LootItem.lootTableItem(SpectrumItems.NIGHTDEW_SPROUT.get()).setWeight(2));
			}
		} else if (key.equals(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("entities/fox")))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getFoxLootPool(enchant, Fox.Type.RED, new TreasureHunterDropDefinition(SpectrumSkullType.FOX, 0.02F)));
			table.addPool(getFoxLootPool(enchant, Fox.Type.SNOW, new TreasureHunterDropDefinition(SpectrumSkullType.FOX_ARCTIC, 0.02F)));
		} else if (key.equals(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("entities/mooshroom")))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getMooshroomLootPool(enchant, MushroomCow.MushroomType.BROWN, new TreasureHunterDropDefinition(SpectrumSkullType.MOOSHROOM_BROWN, 0.02F)));
			table.addPool(getMooshroomLootPool(enchant, MushroomCow.MushroomType.RED, new TreasureHunterDropDefinition(SpectrumSkullType.MOOSHROOM_RED, 0.02F)));
		} else if (key.equals(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("entities/shulker")))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getShulkerLootPool(enchant, null, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.BLACK, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_BLACK, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_BLUE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.BROWN, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_BROWN, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.CYAN, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_CYAN, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_GRAY, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.GREEN, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_GREEN, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.LIGHT_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_LIGHT_BLUE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.LIGHT_GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_LIGHT_GRAY, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.LIME, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_LIME, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.MAGENTA, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_MAGENTA, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.ORANGE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_ORANGE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.PINK, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_PINK, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.PURPLE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_PURPLE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.RED, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_RED, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.WHITE, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_WHITE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.YELLOW, new TreasureHunterDropDefinition(SpectrumSkullType.SHULKER_YELLOW, 0.05F)));
		} else if (key.equals(ResourceKey.create(Registries.LOOT_TABLE, SpectrumCommon.locate("entities/lizard")))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getLizardLootPool(enchant, InkColors.BLACK, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_BLACK, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_BLUE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.BROWN, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_BROWN, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.CYAN, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_CYAN, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_GRAY, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.GREEN, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_GREEN, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.LIGHT_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_LIGHT_BLUE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.LIGHT_GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_LIGHT_GRAY, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.LIME, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_LIME, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.MAGENTA, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_MAGENTA, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.ORANGE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_ORANGE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.PINK, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_PINK, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.PURPLE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_PURPLE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.RED, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_RED, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.WHITE, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_WHITE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.YELLOW, new TreasureHunterDropDefinition(SpectrumSkullType.LIZARD_YELLOW, 0.05F)));
		} else if (key.equals(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("entities/axolotl")))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_BLUE, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.CYAN, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_CYAN, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.GOLD, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_GOLD, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.LUCY, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_LEUCISTIC, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.WILD, new TreasureHunterDropDefinition(SpectrumSkullType.AXOLOTL_WILD, 0.02F)));
		} else if (key.equals(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("entities/parrot")))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.RED_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_RED, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_BLUE, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.GREEN, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_GREEN, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.YELLOW_BLUE, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_CYAN, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.GRAY, new TreasureHunterDropDefinition(SpectrumSkullType.PARROT_GRAY, 0.02F)));
		} else if (key.equals(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse("entities/frog")))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getFrogLootPool(enchant, FrogVariant.TEMPERATE, new TreasureHunterDropDefinition(SpectrumSkullType.FROG_TEMPERATE, 0.02F)));
			table.addPool(getFrogLootPool(enchant, FrogVariant.COLD, new TreasureHunterDropDefinition(SpectrumSkullType.FROG_COLD, 0.02F)));
			table.addPool(getFrogLootPool(enchant, FrogVariant.WARM, new TreasureHunterDropDefinition(SpectrumSkullType.FROG_WARM, 0.02F)));
		} else if (GoFishCompat.isLoaded()) {
			//Go-Fish compat: fishing of crates & go-fish fishies
			if (key.equals(SpectrumLootTables.LAVA_FISHING)) {
				for (LootPool pool : table.pools) {
					addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.NETHER_FISH_LOOT_TABLE_ID).setWeight(80).setQuality(-1));
					addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.NETHER_CRATES_LOOT_TABLE_ID).setWeight(5).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().subPredicate(FishingHookPredicate.inOpenWater(true)))));
				}
			} else if (key.equals(SpectrumLootTables.END_FISHING)) {
				for (LootPool pool : table.pools) {
                    addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.END_FISH_LOOT_TABLE_ID).setWeight(90).setQuality(-1));
                    addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.END_CRATES_LOOT_TABLE_ID).setWeight(5).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().subPredicate(FishingHookPredicate.inOpenWater(true)).build())));
                }
			} else if (key.equals(SpectrumLootTables.DEEPER_DOWN_FISHING)) {
                for (LootPool pool : table.pools) {
				    addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).setWeight(5).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().subPredicate(FishingHookPredicate.inOpenWater(true)).build())));
				}
			} else if (key.equals(SpectrumLootTables.GOO_FISHING)) {
                for (LootPool pool : table.pools) {
				    addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).setWeight(5).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().subPredicate(FishingHookPredicate.inOpenWater(true)).build())));
				}
			} else if (key.equals(SpectrumLootTables.LIQUID_CRYSTAL_FISHING)) {
                for (LootPool pool : table.pools) {
				    addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).setWeight(5).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().subPredicate(FishingHookPredicate.inOpenWater(true)).build())));
				}
			} else if (key.equals(SpectrumLootTables.MIDNIGHT_SOLUTION_FISHING)) {
                for (LootPool pool : table.pools) {
                    addEntry(pool, NestedLootTable.lootTableReference(GoFishCompat.DEFAULT_CRATES_LOOT_TABLE_ID).setWeight(5).setQuality(2).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().subPredicate(FishingHookPredicate.inOpenWater(true)).build())));
                }
			}
		}
	}
	
	private static Holder.Reference<Enchantment> getTreasureHunter(HolderLookup.Provider wrapperLookup) {
		HolderLookup.RegistryLookup<Enchantment> wrapper = wrapperLookup.lookupOrThrow(Registries.ENCHANTMENT);
		return wrapper.getOrThrow(SpectrumEnchantments.TREASURE_HUNTER);
	}
	
	public static LootItemCondition.Builder treasureHunter(Holder.Reference<Enchantment> enchantment, float chance) {
		return AnyOfCondition.anyOf(
				DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(SpectrumDamageTypeTags.ALWAYS_DROPS_MOB_HEAD))),
				() -> new LootItemRandomChanceWithEnchantedBonusCondition(0.0F, new LevelBasedValue.Linear(chance + chance, chance), enchantment)
		);
	}
	
	private static LootPool getLootPool(Holder.Reference<Enchantment> enchantment, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
                .when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private static LootPool getFoxLootPool(Holder.Reference<Enchantment> enchantment, Fox.Type foxType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.FOX.createPredicate(foxType)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private static LootPool getMooshroomLootPool(Holder.Reference<Enchantment> enchantment, MushroomCow.MushroomType mooshroomType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.MOOSHROOM.createPredicate(mooshroomType)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private static LootPool getShulkerLootPool(Holder.Reference<Enchantment> enchantment, @Nullable DyeColor dyeColor, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new ShulkerPredicate(Optional.ofNullable(dyeColor))).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private static LootPool getLizardLootPool(Holder.Reference<Enchantment> enchantment, InkColor linkColor, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new LizardPredicate(Optional.of(linkColor), Optional.empty(), Optional.empty())).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private static LootPool getAxolotlLootPool(Holder.Reference<Enchantment> enchantment, Axolotl.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.AXOLOTL.createPredicate(variant)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private static LootPool getFrogLootPool(Holder.Reference<Enchantment> enchantment, ResourceKey<FrogVariant> variant, TreasureHunterDropDefinition dropDefinition) {
		Holder<FrogVariant> entry = BuiltInRegistries.FROG_VARIANT.getHolderOrThrow(variant);
		
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.FROG.createPredicate(HolderSet.direct(entry))).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private static LootPool getParrotLootPool(Holder.Reference<Enchantment> enchantment, Parrot.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.PARROT.createPredicate(variant)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(SpectrumCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}
	
	private record TreasureHunterDropDefinition(Item drop, float chancePerLevel, ResourceLocation advancementUnlockId) {
		
		public TreasureHunterDropDefinition(Item drop, float chancePerLevel) {
			this(drop, chancePerLevel, BuiltInRegistries.ITEM.getKey(drop));
		}
		
		public TreasureHunterDropDefinition(SpectrumSkullType skullType, float chancePerLevel) {
			this(SpectrumSkullBlock.getBlock(skullType).orElse(Blocks.AIR).asItem(), chancePerLevel);
		}
		
	}
	
}
