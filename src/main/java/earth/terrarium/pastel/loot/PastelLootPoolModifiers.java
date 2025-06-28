package earth.terrarium.pastel.loot;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.compat.gofish.GoFishCompat;
import earth.terrarium.pastel.entity.predicates.LizardPredicate;
import earth.terrarium.pastel.entity.predicates.ShulkerPredicate;
import earth.terrarium.pastel.loot.functions.GrantAdvancementLootFunction;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelDamageTypeTags;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelLootTables;
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
import net.minecraft.server.ReloadableServerRegistries;
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

public class PastelLootPoolModifiers {

	// TODO: Make data driven / introduce a constant for the 0.02 drops
	private static final Map<ResourceLocation, TreasureHunterDropDefinition> treasureHunterLootPools = new HashMap<>() {{
		// Additional vanilla head drops
		put(ResourceLocation.withDefaultNamespace("entities/creeper"), new TreasureHunterDropDefinition(Items.CREEPER_HEAD, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/skeleton"), new TreasureHunterDropDefinition(Items.SKELETON_SKULL, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/wither_skeleton"), new TreasureHunterDropDefinition(Items.WITHER_SKELETON_SKULL, 0.1F));
		put(ResourceLocation.withDefaultNamespace("entities/zombie"), new TreasureHunterDropDefinition(Items.ZOMBIE_HEAD, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/piglin"), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/piglin_brute"), new TreasureHunterDropDefinition(Items.PIGLIN_HEAD, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/ender_dragon"), new TreasureHunterDropDefinition(Items.DRAGON_HEAD, 0.35F)); // why not!

		// Spectrum head drops
		// ATTENTION: No specific enough loot tables exist for fox, axolotl, parrot and shulker variants.
		// Those are handled separately in setup()
		put(ResourceLocation.withDefaultNamespace("entities/sheep"), new TreasureHunterDropDefinition(PastelSkullType.SHEEP, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/bat"), new TreasureHunterDropDefinition(PastelSkullType.BAT, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/blaze"), new TreasureHunterDropDefinition(PastelSkullType.BLAZE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/cat"), new TreasureHunterDropDefinition(PastelSkullType.CAT, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/cave_spider"), new TreasureHunterDropDefinition(PastelSkullType.CAVE_SPIDER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/chicken"), new TreasureHunterDropDefinition(PastelSkullType.CHICKEN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/cow"), new TreasureHunterDropDefinition(PastelSkullType.COW, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/donkey"), new TreasureHunterDropDefinition(PastelSkullType.DONKEY, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/drowned"), new TreasureHunterDropDefinition(PastelSkullType.DROWNED, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/elder_guardian"), new TreasureHunterDropDefinition(PastelSkullType.ELDER_GUARDIAN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/enderman"), new TreasureHunterDropDefinition(PastelSkullType.ENDERMAN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/endermite"), new TreasureHunterDropDefinition(PastelSkullType.ENDERMITE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/evoker"), new TreasureHunterDropDefinition(PastelSkullType.EVOKER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/ghast"), new TreasureHunterDropDefinition(PastelSkullType.GHAST, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/guardian"), new TreasureHunterDropDefinition(PastelSkullType.GUARDIAN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/hoglin"), new TreasureHunterDropDefinition(PastelSkullType.HOGLIN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/horse"), new TreasureHunterDropDefinition(PastelSkullType.HORSE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/husk"), new TreasureHunterDropDefinition(PastelSkullType.HUSK, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/illusioner"), new TreasureHunterDropDefinition(PastelSkullType.ILLUSIONER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/iron_golem"), new TreasureHunterDropDefinition(PastelSkullType.IRON_GOLEM, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/llama"), new TreasureHunterDropDefinition(PastelSkullType.LLAMA, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/magma_cube"), new TreasureHunterDropDefinition(PastelSkullType.MAGMA_CUBE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/mule"), new TreasureHunterDropDefinition(PastelSkullType.MULE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/ocelot"), new TreasureHunterDropDefinition(PastelSkullType.OCELOT, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/panda"), new TreasureHunterDropDefinition(PastelSkullType.PANDA, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/phantom"), new TreasureHunterDropDefinition(PastelSkullType.PHANTOM, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/pig"), new TreasureHunterDropDefinition(PastelSkullType.PIG, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/polar_bear"), new TreasureHunterDropDefinition(PastelSkullType.POLAR_BEAR, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/pufferfish"), new TreasureHunterDropDefinition(PastelSkullType.PUFFERFISH, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/rabbit"), new TreasureHunterDropDefinition(PastelSkullType.RABBIT, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/ravager"), new TreasureHunterDropDefinition(PastelSkullType.RAVAGER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/salmon"), new TreasureHunterDropDefinition(PastelSkullType.SALMON, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/silverfish"), new TreasureHunterDropDefinition(PastelSkullType.SILVERFISH, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/slime"), new TreasureHunterDropDefinition(PastelSkullType.SLIME, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/snow_golem"), new TreasureHunterDropDefinition(PastelSkullType.SNOW_GOLEM, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/spider"), new TreasureHunterDropDefinition(PastelSkullType.SPIDER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/squid"), new TreasureHunterDropDefinition(PastelSkullType.SQUID, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/stray"), new TreasureHunterDropDefinition(PastelSkullType.STRAY, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/strider"), new TreasureHunterDropDefinition(PastelSkullType.STRIDER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/trader_llama"), new TreasureHunterDropDefinition(PastelSkullType.LLAMA, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/turtle"), new TreasureHunterDropDefinition(PastelSkullType.TURTLE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/vex"), new TreasureHunterDropDefinition(PastelSkullType.VEX, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/villager"), new TreasureHunterDropDefinition(PastelSkullType.VILLAGER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/vindicator"), new TreasureHunterDropDefinition(PastelSkullType.VINDICATOR, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/wandering_trader"), new TreasureHunterDropDefinition(PastelSkullType.WANDERING_TRADER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/witch"), new TreasureHunterDropDefinition(PastelSkullType.WITCH, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/adder"), new TreasureHunterDropDefinition(PastelSkullType.WITHER, 0.15F)); // he has 3 heads, after all!
		put(ResourceLocation.withDefaultNamespace("entities/wolf"), new TreasureHunterDropDefinition(PastelSkullType.WOLF, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/zoglin"), new TreasureHunterDropDefinition(PastelSkullType.ZOGLIN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/zombie_villager"), new TreasureHunterDropDefinition(PastelSkullType.ZOMBIE_VILLAGER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/zombified_piglin"), new TreasureHunterDropDefinition(PastelSkullType.ZOMBIFIED_PIGLIN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/bee"), new TreasureHunterDropDefinition(PastelSkullType.BEE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/tropical_fish"), new TreasureHunterDropDefinition(PastelSkullType.TROPICAL_FISH, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/goat"), new TreasureHunterDropDefinition(PastelSkullType.GOAT, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/glow_squid"), new TreasureHunterDropDefinition(PastelSkullType.GLOW_SQUID, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/warden"), new TreasureHunterDropDefinition(PastelSkullType.WARDEN, 0.2F));
		put(ResourceLocation.withDefaultNamespace("entities/tadpole"), new TreasureHunterDropDefinition(PastelSkullType.TADPOLE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/allay"), new TreasureHunterDropDefinition(PastelSkullType.ALLAY, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/camel"), new TreasureHunterDropDefinition(PastelSkullType.CAMEL, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/sniffer"), new TreasureHunterDropDefinition(PastelSkullType.SNIFFER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/skeleton_horse"), new TreasureHunterDropDefinition(PastelSkullType.SKELETON_HORSE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/zombie_horse"), new TreasureHunterDropDefinition(PastelSkullType.ZOMBIE_HORSE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/dolphin"), new TreasureHunterDropDefinition(PastelSkullType.DOLPHIN, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/pillager"), new TreasureHunterDropDefinition(PastelSkullType.PILLAGER, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/armadillo"), new TreasureHunterDropDefinition(PastelSkullType.ARMADILLO, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/breeze"), new TreasureHunterDropDefinition(PastelSkullType.BREEZE, 0.02F));
		put(ResourceLocation.withDefaultNamespace("entities/bogged"), new TreasureHunterDropDefinition(PastelSkullType.BOGGED, 0.02F));

		// TODO: ...ooooor we could simply put them in the json loot table
		put(PastelCommon.locate("entities/egg_laying_wooly_pig"), new TreasureHunterDropDefinition(PastelSkullType.EGG_LAYING_WOOLY_PIG, 0.1F));
		put(PastelCommon.locate("entities/kindling"), new TreasureHunterDropDefinition(PastelSkullType.KINDLING, 0.1F));
		put(PastelCommon.locate("entities/preservation_turret"), new TreasureHunterDropDefinition(PastelSkullType.PRESERVATION_TURRET, 0.1F));
		put(PastelCommon.locate("entities/eraser"), new TreasureHunterDropDefinition(PastelSkullType.ERASER, 0.1F));
	}};

	private static final Map<ResourceKey<LootTable>, LootTable> DELAYED_TABLES = new HashMap<>();

	private static void addEntry(LootPool pool, LootPoolEntryContainer.Builder<?> entry) {
		// TODO
		pool.entries.add(entry.build());
	}

	// TODO Migrate to loot modifiers add AddTableLootModifier and custom LootModifiers
	public static void loadLootTable(LootTableLoadEvent event) {
		ResourceLocation key = event.getName();
		LootTable table = event.getTable();
		HolderLookup.Provider wrapperLookup =
				new ReloadableServerRegistries.EmptyTagLookupWrapper(PastelLootGrossHacks.access);

		// Treasure hunter pools
		if (treasureHunterLootPools.containsKey(key)) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			TreasureHunterDropDefinition dropDefinition = treasureHunterLootPools.get(key);
			table.addPool(getLootPool(enchant, dropDefinition));
			// Some treasure hunter pools use custom loot conditions
			// because vanillas are too generic (fox/snow fox both use "fox" loot table)
		} else if (key.equals(ResourceLocation.parse("entities/fox"))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getFoxLootPool(enchant, Fox.Type.RED, new TreasureHunterDropDefinition(PastelSkullType.FOX, 0.02F)));
			table.addPool(getFoxLootPool(enchant, Fox.Type.SNOW, new TreasureHunterDropDefinition(PastelSkullType.FOX_ARCTIC, 0.02F)));
		} else if (key.equals(ResourceLocation.parse("entities/mooshroom"))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getMooshroomLootPool(enchant, MushroomCow.MushroomType.BROWN, new TreasureHunterDropDefinition(PastelSkullType.MOOSHROOM_BROWN, 0.02F)));
			table.addPool(getMooshroomLootPool(enchant, MushroomCow.MushroomType.RED, new TreasureHunterDropDefinition(PastelSkullType.MOOSHROOM_RED, 0.02F)));
		} else if (key.equals(ResourceLocation.parse("entities/shulker"))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getShulkerLootPool(enchant, null, new TreasureHunterDropDefinition(PastelSkullType.SHULKER, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.BLACK, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_BLACK, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.BLUE, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_BLUE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.BROWN, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_BROWN, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.CYAN, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_CYAN, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.GRAY, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_GRAY, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.GREEN, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_GREEN, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.LIGHT_BLUE, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_LIGHT_BLUE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.LIGHT_GRAY, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_LIGHT_GRAY, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.LIME, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_LIME, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.MAGENTA, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_MAGENTA, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.ORANGE, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_ORANGE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.PINK, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_PINK, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.PURPLE, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_PURPLE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.RED, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_RED, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.WHITE, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_WHITE, 0.05F)));
			table.addPool(getShulkerLootPool(enchant, DyeColor.YELLOW, new TreasureHunterDropDefinition(PastelSkullType.SHULKER_YELLOW, 0.05F)));
		} else if (key.equals(PastelCommon.locate("entities/lizard"))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getLizardLootPool(enchant, InkColors.BLACK, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_BLACK, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.BLUE, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_BLUE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.BROWN, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_BROWN, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.CYAN, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_CYAN, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.GRAY, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_GRAY, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.GREEN, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_GREEN, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.LIGHT_BLUE, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_LIGHT_BLUE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.LIGHT_GRAY, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_LIGHT_GRAY, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.LIME, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_LIME, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.MAGENTA, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_MAGENTA, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.ORANGE, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_ORANGE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.PINK, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_PINK, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.PURPLE, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_PURPLE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.RED, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_RED, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.WHITE, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_WHITE, 0.05F)));
			table.addPool(getLizardLootPool(enchant, InkColors.YELLOW, new TreasureHunterDropDefinition(PastelSkullType.LIZARD_YELLOW, 0.05F)));
		} else if (key.equals(ResourceLocation.parse("entities/axolotl"))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.BLUE, new TreasureHunterDropDefinition(PastelSkullType.AXOLOTL_BLUE, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.CYAN, new TreasureHunterDropDefinition(PastelSkullType.AXOLOTL_CYAN, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.GOLD, new TreasureHunterDropDefinition(PastelSkullType.AXOLOTL_GOLD, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.LUCY, new TreasureHunterDropDefinition(PastelSkullType.AXOLOTL_LEUCISTIC, 0.02F)));
			table.addPool(getAxolotlLootPool(enchant, Axolotl.Variant.WILD, new TreasureHunterDropDefinition(PastelSkullType.AXOLOTL_WILD, 0.02F)));
		} else if (key.equals(ResourceLocation.parse("entities/parrot"))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.RED_BLUE, new TreasureHunterDropDefinition(PastelSkullType.PARROT_RED, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.BLUE, new TreasureHunterDropDefinition(PastelSkullType.PARROT_BLUE, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.GREEN, new TreasureHunterDropDefinition(PastelSkullType.PARROT_GREEN, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.YELLOW_BLUE, new TreasureHunterDropDefinition(PastelSkullType.PARROT_CYAN, 0.02F)));
			table.addPool(getParrotLootPool(enchant, Parrot.Variant.GRAY, new TreasureHunterDropDefinition(PastelSkullType.PARROT_GRAY, 0.02F)));
		} else if (key.equals(ResourceLocation.parse("entities/frog"))) {
			Holder.Reference<Enchantment> enchant = getTreasureHunter(wrapperLookup);
			table.addPool(getFrogLootPool(enchant, FrogVariant.TEMPERATE, new TreasureHunterDropDefinition(PastelSkullType.FROG_TEMPERATE, 0.02F)));
			table.addPool(getFrogLootPool(enchant, FrogVariant.COLD, new TreasureHunterDropDefinition(PastelSkullType.FROG_COLD, 0.02F)));
			table.addPool(getFrogLootPool(enchant, FrogVariant.WARM, new TreasureHunterDropDefinition(PastelSkullType.FROG_WARM, 0.02F)));
		} else if (key.equals(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.location()) || key.equals(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.location())
				|| key.equals(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY.location()) || key.equals(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY.location())) {
			for (LootPool pool : table.pools) {
				addEntry(pool, LootItem.lootTableItem(PastelItems.NIGHTDEW_SPROUT.get()).setWeight(2).setQuality(-1));
			}
		} else if (key.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.location())) {
			for (LootPool pool : table.pools) {
				addEntry(pool, LootItem.lootTableItem(PastelItems.NIGHTDEW_SPROUT.get()).setWeight(3).setQuality(-1));
			}
		} else if (key.equals(BuiltInLootTables.SNIFFER_DIGGING.location())) {
			for (LootPool pool : table.pools) {
				addEntry(pool, LootItem.lootTableItem(PastelBlocks.WEEPING_GALA_SPRIG.get()).setWeight(1));
				addEntry(pool, LootItem.lootTableItem(PastelItems.NIGHTDEW_SPROUT.get()).setWeight(2));
			}
		}
	}

	private static Holder.Reference<Enchantment> getTreasureHunter(HolderLookup.Provider wrapperLookup) {
		HolderLookup.RegistryLookup<Enchantment> wrapper = wrapperLookup.lookupOrThrow(Registries.ENCHANTMENT);
		return wrapper.getOrThrow(PastelEnchantments.TREASURE_HUNTER);
	}

	public static LootItemCondition.Builder treasureHunter(Holder.Reference<Enchantment> enchantment, float chance) {
		return AnyOfCondition.anyOf(
				DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(PastelDamageTypeTags.ALWAYS_DROPS_MOB_HEAD))),
				() -> new LootItemRandomChanceWithEnchantedBonusCondition(0.0F, new LevelBasedValue.Linear(chance + chance, chance), enchantment)
		);
	}

	private static LootPool getLootPool(Holder.Reference<Enchantment> enchantment, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
                .when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private static LootPool getFoxLootPool(Holder.Reference<Enchantment> enchantment, Fox.Type foxType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.FOX.createPredicate(foxType)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private static LootPool getMooshroomLootPool(Holder.Reference<Enchantment> enchantment, MushroomCow.MushroomType mooshroomType, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.MOOSHROOM.createPredicate(mooshroomType)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private static LootPool getShulkerLootPool(Holder.Reference<Enchantment> enchantment, @Nullable DyeColor dyeColor, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new ShulkerPredicate(Optional.ofNullable(dyeColor))).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private static LootPool getLizardLootPool(Holder.Reference<Enchantment> enchantment, InkColor linkColor, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(new LizardPredicate(Optional.of(linkColor), Optional.empty(), Optional.empty())).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private static LootPool getAxolotlLootPool(Holder.Reference<Enchantment> enchantment, Axolotl.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.AXOLOTL.createPredicate(variant)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private static LootPool getFrogLootPool(Holder.Reference<Enchantment> enchantment, ResourceKey<FrogVariant> variant, TreasureHunterDropDefinition dropDefinition) {
		Holder<FrogVariant> entry = BuiltInRegistries.FROG_VARIANT.getHolderOrThrow(variant);

		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.FROG.createPredicate(HolderSet.direct(entry))).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private static LootPool getParrotLootPool(Holder.Reference<Enchantment> enchantment, Parrot.Variant variant, TreasureHunterDropDefinition dropDefinition) {
		return new LootPool.Builder()
				.setRolls(ConstantValue.exactly(1))
				.when(treasureHunter(enchantment, dropDefinition.chancePerLevel))
				.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(EntitySubPredicates.PARROT.createPredicate(variant)).build()))
				.apply(GrantAdvancementLootFunction.builder(LootContext.EntityTarget.ATTACKING_PLAYER, List.of(PastelCommon.locate("mob_head"), dropDefinition.advancementUnlockId)))
				.add(LootItem.lootTableItem(dropDefinition.drop))
				.build();
	}

	private record TreasureHunterDropDefinition(Item drop, float chancePerLevel, ResourceLocation advancementUnlockId) {

		public TreasureHunterDropDefinition(Item drop, float chancePerLevel) {
			this(drop, chancePerLevel, BuiltInRegistries.ITEM.getKey(drop));
		}

		public TreasureHunterDropDefinition(PastelSkullType skullType, float chancePerLevel) {
			this(PastelSkullBlock.getBlock(skullType).orElse(Blocks.AIR).asItem(), chancePerLevel);
		}

	}

}
