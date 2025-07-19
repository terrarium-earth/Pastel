package earth.terrarium.pastel.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import de.dafuqs.revelationary.RevelationRegistry;
import de.dafuqs.revelationary.advancement_criteria.AdvancementCountCriterion;
import de.dafuqs.revelationary.advancement_criteria.AdvancementGottenCriterion;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.color.ColorRegistry;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.recipe.GatedRecipe;
import earth.terrarium.pastel.blocks.PlacedItemBlock;
import earth.terrarium.pastel.blocks.deeper_down.flora.WeepingGalaFrondsBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelBuddingBlock;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.recipe.GatedPastelRecipe;
import earth.terrarium.pastel.recipe.anvil_crushing.AnvilCrushingRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchanterCraftingRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.recipe.pedestal.BuiltinGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipeTier;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SanityCommand {

    private static final ResourceLocation WIP_ADVANCEMENT_ID = PastelCommon.locate("__wip");

    private static final List<ResourceLocation> ADVANCEMENT_GATING_WARNING_WHITELIST = List.of(
        PastelCommon.locate("find_preservation_ruins"),                     // does not have a prerequisite
        PastelCommon.locate("fail_to_glitch_into_preservation_ruin"),       // does not have a prerequisite
        PastelCommon.locate("midgame/craft_blacklisted_memory_success"),    // its parent is 2 parents in
        PastelCommon.locate("lategame/collect_myceylon"),                   // its parent is 2 parents in
        PastelCommon.locate("lategame/strike_up_hummingstone_hymn")         // its parent is 2 parents in
    );

    private static final List<ResourceLocation> GUIDEBOOK_WARNING_WHITELIST = List.of(
        PastelCommon.locate("cuisine/cookbooks/brewers_handbook")           // "*_fluid" mod compat recipe page
    );

    public static void register(LiteralCommandNode<CommandSourceStack> root) {
        LiteralCommandNode<CommandSourceStack> sanity = Commands.literal("sanity")
                                                                .requires((source) -> source.hasPermission(2))
                                                                .executes((context) -> execute(
                                                                    context.getSource(),
                                                                    PastelCommon.MOD_ID
                                                                ))
                                                                .build();
        ArgumentCommandNode<CommandSourceStack, String> modId = Commands.argument("mod_id", StringArgumentType.word())
                                                                        .executes(
                                                                            (context) -> execute(
                                                                                context.getSource(),
                                                                                StringArgumentType.getString(
                                                                                    context,
                                                                                    "mod_id"
                                                                                )
                                                                            ))
                                                                        .build();

        sanity.addChild(modId);
        root.addChild(sanity);
    }

    private static int execute(CommandSourceStack source, String modId) {
        RegistryAccess registryManager = source.registryAccess();

        PastelCommon.logInfo("##### SANITY CHECK START ######");

        // All blocks that do not have a mineable tag
        for (Map.Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet()) {
            ResourceKey<Block> registryKey = entry.getKey();
            if (registryKey.location()
                           .getNamespace()
                           .equals(modId)) {
                BlockState blockState = entry.getValue()
                                             .defaultBlockState();

                // unbreakable or instabreak blocks do not need to have an entry
                if (blockState.getBlock()
                              .defaultDestroyTime() <= 0) {
                    continue;
                }

                if (!blockState.is(BlockTags.MINEABLE_WITH_PICKAXE)
                    && !blockState.is(BlockTags.MINEABLE_WITH_AXE)
                    && !blockState.is(BlockTags.MINEABLE_WITH_SHOVEL)
                    && !blockState.is(BlockTags.MINEABLE_WITH_HOE)
                    && !blockState.is(BlockTags.SWORD_EFFICIENT)
                    && !blockState.is(PastelBlockTags.EXEMPT_FROM_MINEABLE_DEBUG_CHECK)) {
                    PastelCommon.logWarning("[SANITY: Mineable Tags] Block " + registryKey.location() +
                                            " is not contained in a any vanilla mineable tag.");
                }
            }
        }

        // All blocks without a loot table
        for (Map.Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet()) {
            ResourceKey<Block> registryKey = entry.getKey();
            if (registryKey.location()
                           .getNamespace()
                           .equals(modId)) {
                Block block = entry.getValue();

                if (block instanceof PlacedItemBlock) {
                    continue; // that one always drops itself via code
                }
                if (block instanceof PastelBuddingBlock) {
                    continue; // does not have any drop by default
                }
                if (block instanceof WeepingGalaFrondsBlock) {
                    continue; // Fronds do not drop anything by default
                }

                BlockState blockState = entry.getValue()
                                             .defaultBlockState();
                ResourceKey<LootTable> lootTableKey = block.getLootTable();
                ResourceLocation lootTableID = lootTableKey.location();

                // unbreakable blocks do not need to have a loot table
                if (blockState.getBlock()
                              .defaultDestroyTime() <= -1) {
                    continue;
                }

                if (!blockState.is(PastelBlockTags.EXEMPT_FROM_LOOT_TABLE_DEBUG_CHECK)) {
                    if (lootTableKey.equals(BuiltInLootTables.EMPTY) || lootTableID.getPath()
                                                                                   .equals("blocks/air")) {
                        PastelCommon.logWarning("[SANITY: Loot Tables] Block " + registryKey.location() +
                                                " has a non-existent loot table (" + lootTableID + ")");
                    } else {
                        LootTable lootTable = source.getLevel()
                                                    .getServer()
                                                    .reloadableRegistries()
                                                    .getLootTable(lootTableKey);
                        List<LootPool> lootPools = lootTable.pools;
                        if (lootPools.isEmpty()) {
                            PastelCommon.logWarning(
                                "[SANITY: Loot Tables] Block " + registryKey.location() + " has an empty loot table");
                        }
                    }
                }
            }
        }

        // Statistic: Build an empty map of maps for counting used gem colors for each tier
        // This info can be used to balance usage a bit
        Map<PedestalRecipeTier, Map<GemstoneColor, Integer>> usedColorsForEachTier = new HashMap<>();
        for (PedestalRecipeTier pedestalRecipeTier : PedestalRecipeTier.values()) {
            Map<GemstoneColor, Integer> colorMap = new HashMap<>();
            for (GemstoneColor gemstoneColor : BuiltinGemstoneColor.values()) {
                colorMap.put(gemstoneColor, 0);
            }
            usedColorsForEachTier.put(pedestalRecipeTier, colorMap);
        }

        MinecraftServer minecraftServer = source.getLevel()
                                                .getServer();
        RecipeManager recipeManager = minecraftServer.getRecipeManager();
        ServerAdvancementManager advancementLoader = minecraftServer.getAdvancements();

        // Pedestal recipes that use gemstone powder not available at that tier yet
        for (RecipeHolder<PedestalRecipe> pedestalRecipeEntry : recipeManager.getAllRecipesFor(
            PastelRecipeTypes.PEDESTAL)) {
			/* There are some recipes that use advanced ingredients by design
			   despite being of a low tier, like black colored lights.
			   While the player does not have access to that yet it is no problem at all
			*/
            PedestalRecipe pedestalRecipe = pedestalRecipeEntry.value();
            if (pedestalRecipe.getTier() == PedestalRecipeTier.BASIC ||
                pedestalRecipe.getTier() == PedestalRecipeTier.SIMPLE) {
                if (pedestalRecipe.getPowderInputs()
                                  .getOrDefault(BuiltinGemstoneColor.BLACK, 0) > 0) {
                    PastelCommon.logWarning(
                        "[SANITY: Pedestal Recipe Ingredients] Pedestal recipe '" + pedestalRecipeEntry.id() +
                        "' of tier '" + pedestalRecipe.getTier() +
                        "' is using onyx powder as input! Players will not have access to Onyx at that tier");
                }
            }
            if (pedestalRecipe.getTier() != PedestalRecipeTier.COMPLEX) {
                if (pedestalRecipe.getPowderInputs()
                                  .getOrDefault(BuiltinGemstoneColor.WHITE, 0) > 0) {
                    PastelCommon.logWarning(
                        "[SANITY: Pedestal Recipe Ingredients] Pedestal recipe '" + pedestalRecipeEntry.id() +
                        "' of tier '" + pedestalRecipe.getTier() +
                        "' is using moonstone powder as input! Players will not have access to Moonstone at that tier");
                }
            }
            for (Map.Entry<GemstoneColor, Integer> powderInput : pedestalRecipe.getPowderInputs()
                                                                               .entrySet()) {
                usedColorsForEachTier.get(pedestalRecipe.getTier())
                                     .put(
                                         powderInput.getKey(), usedColorsForEachTier.get(pedestalRecipe.getTier())
                                                                                    .get(powderInput.getKey()) +
                                                               powderInput.getValue()
                                     );
            }
        }

        // Checking for missing translation strings
        for (Map.Entry<ResourceKey<Item>, Item> item : BuiltInRegistries.ITEM.entrySet()) {
            if (!item.getKey()
                     .location()
                     .getNamespace()
                     .equals(modId)) {
                continue;
            }

            if (!Language.getInstance()
                         .has(item.getValue()
                                  .getDescriptionId())) {
                PastelCommon.logWarning("[SANITY: Item Lang] Missing translation string " + item.getValue()
                                                                                                .getDescriptionId());
            }
        }
        for (Map.Entry<ResourceKey<Block>, Block> block : BuiltInRegistries.BLOCK.entrySet()) {
            if (!block.getKey()
                      .location()
                      .getNamespace()
                      .equals(modId)) {
                continue;
            }
            if (!Language.getInstance()
                         .has(block.getValue()
                                   .getDescriptionId())) {
                PastelCommon.logWarning("[SANITY: Block Lang] Missing translation string " + block.getValue()
                                                                                                  .getDescriptionId());
            }
        }
        for (Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>> entityType :
            BuiltInRegistries.ENTITY_TYPE.entrySet()) {
            if (!entityType.getKey()
                           .location()
                           .getNamespace()
                           .equals(modId)) {
                continue;
            }
            if (!Language.getInstance()
                         .has(entityType.getValue()
                                        .getDescriptionId())) {
                PastelCommon.logWarning("[SANITY: EntityType Lang] Missing translation string " + entityType.getValue()
                                                                                                            .getDescriptionId());
            }
        }
        for (Map.Entry<ResourceKey<MobEffect>, MobEffect> entityType : BuiltInRegistries.MOB_EFFECT.entrySet()) {
            if (!entityType.getKey()
                           .location()
                           .getNamespace()
                           .equals(modId)) {
                continue;
            }
            if (!Language.getInstance()
                         .has(entityType.getValue()
                                        .getDescriptionId())) {
                PastelCommon.logWarning("[SANITY: Status Effect Lang] Missing translation string " +
                                        entityType.getValue()
                                                  .getDescriptionId());
            }
        }
        for (Map.Entry<ResourceKey<Attribute>, Attribute> entityType : BuiltInRegistries.ATTRIBUTE.entrySet()) {
            if (!entityType.getKey()
                           .location()
                           .getNamespace()
                           .equals(modId)) {
                continue;
            }
            if (!Language.getInstance()
                         .has(entityType.getValue()
                                        .getDescriptionId())) {
                PastelCommon.logWarning("[SANITY: Attribute Lang] Missing translation string " + entityType.getValue()
                                                                                                           .getDescriptionId());
            }
        }
        for (Map.Entry<ResourceKey<Attribute>, Attribute> entityType : BuiltInRegistries.ATTRIBUTE.entrySet()) {
            if (!entityType.getKey()
                           .location()
                           .getNamespace()
                           .equals(modId)) {
                continue;
            }
            if (!Language.getInstance()
                         .has(entityType.getValue()
                                        .getDescriptionId())) {
                PastelCommon.logWarning("[SANITY: Attribute Lang] Missing translation string " + entityType.getValue()
                                                                                                           .getDescriptionId());
            }
            if (!Language.getInstance()
                         .has(entityType.getValue()
                                        .getDescriptionId() + ".desc")) {
                PastelCommon.logWarning("[SANITY: Attribute Lang] Missing description string " + entityType.getValue()
                                                                                                           .getDescriptionId() +
                                        ".desc");
            }
        }

        // recipe groups without localization
        Set<String> recipeGroups = new HashSet<>();
        recipeManager.getRecipeIds()
                     .forEach(identifier -> {
                         Optional<RecipeHolder<?>> recipe = recipeManager.byKey(identifier);
                         if (recipe.isPresent()) {
                             if (recipe.get()
                                       .value() instanceof GatedPastelRecipe<?> gatedSpectrumRecipe) {
                                 String group = gatedSpectrumRecipe.getGroup();
                                 if (group == null) {
                                     PastelCommon.logWarning(
                                         "Recipe with null group found! :" + gatedSpectrumRecipe.getGroup());
                                 } else if (!group.isEmpty()) {
                                     recipeGroups.add(group);
                                 }
                             }
                         }
                     });
        for (String recipeGroup : recipeGroups) {
            if (!Language.getInstance()
                         .has("recipeGroup.pastel." + recipeGroup)) {
                PastelCommon.logWarning(
                    "[SANITY: Recipe Group Lang] Recipe group " + recipeGroup + " is not localized.");
            }
        }

        // Impossible to unlock recipes
        for (RecipeHolder<?> recipeEntry : recipeManager.getRecipes()) {
            if (recipeEntry.value() instanceof GatedRecipe<?> gatedRecipe) {
                Optional<ResourceLocation> advancementIdentifier = gatedRecipe.getRequiredAdvancementIdentifier();
                if (advancementIdentifier.isPresent() && advancementLoader.get(advancementIdentifier.get()) == null) {
                    PastelCommon.logWarning(
                        "[SANITY: " + gatedRecipe.getRecipeTypeShortID() + " Recipe Unlocks] Advancement '" +
                        gatedRecipe.getRequiredAdvancementIdentifier() + "' in recipe '" + recipeEntry.id() +
                        "' does not exist");
                }
            }
        }

        // Recipes that spawn effects based on item color,
        // but input / output items do not have a color registered
        testIngredientsAndOutputInColorRegistry(
            PastelRecipeTypes.FUSION_SHRINE, "Fusion Shrine", recipeManager, registryManager);
        testIngredientsAndOutputInColorRegistry(
            PastelRecipeTypes.ENCHANTER, "Enchanting", recipeManager, registryManager);
        testIngredientsAndOutputInColorRegistry(
            PastelRecipeTypes.ENCHANTMENT_UPGRADE, "Enchantment Upgrade", recipeManager, registryManager);
        testIngredientsAndOutputInColorRegistry(
            PastelRecipeTypes.SPIRIT_INSTILLING, "Spirit Instiller", recipeManager, registryManager);


        // Impossible to unlock block cloaks
        for (Map.Entry<ResourceLocation, List<BlockState>> cloaks : RevelationRegistry.getBlockStateEntries()
                                                                                      .entrySet()) {
            if (advancementLoader.get(cloaks.getKey()) == null) {
                PastelCommon.logWarning("[SANITY: Block Cloaks] Advancement '" + cloaks.getKey()
                                                                                       .toString() +
                                        "' for block cloaking does not exist. Registered cloaks: " + cloaks.getValue()
                                                                                                           .size());
            }
        }
        for (Map.Entry<ResourceLocation, List<Item>> cloaks : RevelationRegistry.getItemEntries()
                                                                                .entrySet()) {
            if (advancementLoader.get(cloaks.getKey()) == null) {
                PastelCommon.logWarning("[SANITY: Item Cloaks] Advancement '" + cloaks.getKey()
                                                                                      .toString() +
                                        "' for item cloaking does not exist. Registered cloaks: " + cloaks.getValue()
                                                                                                          .size());
            }
        }

        for (AdvancementHolder advancementEntry : advancementLoader.getAllAdvancements()) {
            Advancement advancement = advancementEntry.value();
            for (Criterion<?> criterion : advancement.criteria()
                                                     .values()) {
                CriterionTriggerInstance conditions = criterion.triggerInstance();

                // "has advancement" criteria with nonexistent advancements
                if (conditions instanceof AdvancementGottenCriterion.Conditions hasAdvancementConditions) {
                    ResourceLocation advancementIdentifier = hasAdvancementConditions.getAdvancementIdentifier();
                    if (advancementIdentifier.equals(WIP_ADVANCEMENT_ID)) {
                        continue;
                    }
                    AdvancementHolder advancementCriterionAdvancement = advancementLoader.get(advancementIdentifier);
                    if (advancementCriterionAdvancement == null) {
                        PastelCommon.logWarning(
                            "[SANITY: Has_Advancement Criteria] Advancement '" + advancementEntry.id() +
                            "' references advancement '" + advancementIdentifier + "' that does not exist");
                    }
                    // "advancement count" criteria with nonexistent advancements
                } else if (conditions instanceof AdvancementCountCriterion.Conditions hasAdvancementConditions) {
                    for (ResourceLocation advancementIdentifier : hasAdvancementConditions.advancementIdentifiers()) {
                        if (advancementIdentifier.equals(WIP_ADVANCEMENT_ID)) {
                            continue;
                        }
                        AdvancementHolder advancementCriterionAdvancement = advancementLoader.get(
                            advancementIdentifier);
                        if (advancementCriterionAdvancement == null) {
                            PastelCommon.logWarning(
                                "[SANITY: Advancement_Count Criteria] Advancement '" + advancementEntry.id() +
                                "' references advancement '" + advancementIdentifier + "' that does not exist");
                        }
                    }
                }
            }
        }

        // advancements that don't require their parent (or parents of their parents, for 'collecting' type
        // advancements)
        for (AdvancementHolder advancement : advancementLoader.getAllAdvancements()) {
            ResourceLocation advancementId = advancement.id();
            String path = advancementId.getPath();
            Optional<ResourceLocation> parentId = advancement.value()
                                                             .parent();
            if (advancementId.getNamespace()
                             .equals(modId) && !path.startsWith("hidden") && !path.startsWith("progression") &&
                !path.startsWith("milestones") && parentId.isPresent()) {
                ResourceLocation gottenPreviousAdvancementIdentifier = null;
                for (List<String> requirement : advancement.value()
                                                           .requirements()
                                                           .requirements()) {
                    if (!requirement.isEmpty() && requirement.getFirst()
                                                             .equals("gotten_previous")) { // TODO: is that correct?
                        CriterionTriggerInstance conditions = advancement.value()
                                                                         .criteria()
                                                                         .get("gotten_previous")
                                                                         .triggerInstance();
                        if (conditions instanceof AdvancementGottenCriterion.Conditions advancementConditions) {
                            gottenPreviousAdvancementIdentifier = advancementConditions.getAdvancementIdentifier();
                            break;
                        } else {
                            PastelCommon.logWarning("[SANITY: Advancement Gating] Advancement '" + advancementId +
                                                    "' has a \"gotten_previous\" requirement, but its not of type " +
                                                    "revelationary:advancement_gotten");
                        }
                    }
                }
                if (!ADVANCEMENT_GATING_WARNING_WHITELIST.contains(advancementId)) {
                    if (gottenPreviousAdvancementIdentifier == null) {
                        PastelCommon.logWarning("[SANITY: Advancement Gating] Advancement '" + advancementId +
                                                "' does not have its parent set as requirement");
                    } else {
                        AdvancementHolder parentEntry = advancementLoader.get(parentId.get());
                        if (parentEntry == null) {
                            PastelCommon.logWarning("[SANITY: Advancement Gating] Advancement '" + advancementId +
                                                    "' has its \"gotten_previous\" advancement set an advancement " +
                                                    "that does not exist.");
                            continue;
                        }
                        if (parentEntry.id()
                                       .equals(gottenPreviousAdvancementIdentifier)) {
                            continue;
                        }
                        Optional<ResourceLocation> parentOfParentId = parentEntry.value()
                                                                                 .parent();
                        if (parentOfParentId.isPresent() && parentOfParentId.get()
                                                                            .equals(
                                                                                gottenPreviousAdvancementIdentifier)) {
                            continue; // "collect stuff" advancements with its 2nd parent being the requirement
                        }
                        PastelCommon.logWarning("[SANITY: Advancement Gating] Advancement '" + advancementId +
                                                "' has its \"gotten_previous\" advancement set to something else than" +
                                                " their parent. Intended?");
                    }
                }
            }
        }

        // Pedestal Recipes in wrong data folder
        for (RecipeHolder<PedestalRecipe> recipeEntry : recipeManager.getAllRecipesFor(PastelRecipeTypes.PEDESTAL)) {
            PedestalRecipe recipe = recipeEntry.value();

            ResourceLocation id = recipeEntry.id();
            if (id.getPath()
                  .startsWith("mod_integration/") || id.getPath()
                                                       .contains("/glass/") || id.getPath()
                                                                                 .contains("/saplings/") || id.getPath()
                                                                                                              .contains(
                                                                                                                  "/detectors/") ||
                id.getPath()
                  .contains("/gemstone_lights/") || id.getPath()
                                                      .contains("/pylons/")
                || id.getPath()
                     .contains("/runes/") || id.getPath()
                                               .contains("/pastel_network/") || id.getPath()
                                                                                  .contains("/gemstone_chimes/") ||
                id.getPath()
                  .contains("/semi_permeable_glass/")
                || id.getPath()
                     .contains("/colored_lamps/") || id.getPath()
                                                       .contains("/colored_spore_blossoms/") || id.getPath()
                                                                                                  .contains(
                                                                                                      "/glowblocks/")) {
                continue;
            }

            if (recipe.getTier() == PedestalRecipeTier.BASIC && !id.getPath()
                                                                   .contains("/tier1/")) {
                PastelCommon.logWarning(
                    "[SANITY: Pedestal Recipes] BASIC recipe not in the correct tier folder: '" + id + "'");
            } else if (recipe.getTier() == PedestalRecipeTier.SIMPLE && !id.getPath()
                                                                           .contains("/tier2/")) {
                PastelCommon.logWarning(
                    "[SANITY: Pedestal Recipes] SIMPLE recipe not in the correct tier folder: '" + id + "'");
            } else if (recipe.getTier() == PedestalRecipeTier.ADVANCED && !id.getPath()
                                                                             .contains("/tier3/")) {
                PastelCommon.logWarning(
                    "[SANITY: Pedestal Recipes] ADVANCED recipe not in the correct tier folder: '" + id + "'");
            } else if (recipe.getTier() == PedestalRecipeTier.COMPLEX && !id.getPath()
                                                                            .contains("/tier4/")) {
                PastelCommon.logWarning(
                    "[SANITY: Pedestal Recipes] COMPLEX recipe not in the correct tier folder: '" + id + "'");
            }
        }

        // Item Crushing recipes with nonexistent sounds
        for (RecipeHolder<AnvilCrushingRecipe> recipeEntry : recipeManager.getAllRecipesFor(
            PastelRecipeTypes.ANVIL_CRUSHING)) {
            AnvilCrushingRecipe recipe = recipeEntry.value();
            SoundEvent soundEvent = recipe.getSoundEvent();
            if (soundEvent == null) {
                PastelCommon.logWarning(
                    "[SANITY: Item Crushing] Recipe '" + recipeEntry.id() + "' has a nonexistent sound set");
            }
        }

        // Enchantments with nonexistent unlock enchantment
        //TODO: Sanity between cloaked+revealed enchants and their tags
//		for (Map.Entry<RegistryKey<Enchantment>, Enchantment> enchantment : registryManager.get(RegistryKeys
//		.ENCHANTMENT).getEntrySet()) {
//			if (enchantment.getValue() instanceof SpectrumEnchantment spectrumEnchantment) {
//				Identifier advancementIdentifier = spectrumEnchantment.getUnlockAdvancementIdentifier();
//				AdvancementEntry advancementCriterionAdvancement = advancementLoader.get(advancementIdentifier);
//				if (advancementCriterionAdvancement == null) {
//					PastelCommon.logWarning("[SANITY: Enchantments] Enchantment '" + enchantment.getKey().getValue() +
//					"' references advancement '" + advancementIdentifier + "' that does not exist");
//				}
//			}
//		}

        // Enchantments without recipe
        Map<Holder<Enchantment>, InkColor> craftingColors = new HashMap<>();
        Map<Holder<Enchantment>, InkColor> upgradeColors = new HashMap<>();
        for (RecipeHolder<EnchanterCraftingRecipe> recipeEntry : recipeManager.getAllRecipesFor(
            PastelRecipeTypes.ENCHANTER)) {
            EnchanterCraftingRecipe recipe = recipeEntry.value();
            ItemStack output = recipe.getResultItem(source.registryAccess());
            if (output.getItem() == Items.ENCHANTED_BOOK) {
                ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(output);
                if (!enchantments.isEmpty()) {
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        for (ItemStack matchingStack : ingredient.getItems()) {
                            if (matchingStack.getItem() instanceof PigmentItem pigmentItem) {
                                craftingColors.put(
                                    enchantments.keySet()
                                                .stream()
                                                .toList()
                                                .getFirst(), pigmentItem.getInkColor()
                                );
                            }
                        }
                    }
                }
            }
        }
        for (RecipeHolder<EnchantmentUpgradeRecipe> recipeEntry : recipeManager.getAllRecipesFor(
            PastelRecipeTypes.ENCHANTMENT_UPGRADE)) {
            EnchantmentUpgradeRecipe recipe = recipeEntry.value();
            ItemStack output = recipe.getResultItem(source.registryAccess());
            if (output.getItem() == Items.ENCHANTED_BOOK) {
                ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(output);
                if (!enchantments.isEmpty() && recipe.getBulkItem() instanceof PigmentItem pigmentItem) {
                    upgradeColors.put(
                        enchantments.keySet()
                                    .stream()
                                    .toList()
                                    .getFirst(), pigmentItem.getInkColor()
                    );
                }
            }
        }
        for (Enchantment enchantment : registryManager.registryOrThrow(Registries.ENCHANTMENT)) {
            Holder<Enchantment> entry = registryManager.registryOrThrow(Registries.ENCHANTMENT)
                                                       .wrapAsHolder(enchantment);
            if (!craftingColors.containsKey(entry)) {
                PastelCommon.logWarning("[SANITY: Enchantment Recipes] Enchantment '" + entry.getRegisteredName() +
                                        "' does not have a crafting recipe");
            }
            if (!upgradeColors.containsKey(entry) && enchantment.getMaxLevel() > 1) {
                PastelCommon.logWarning("[SANITY: Enchantment Recipes] Enchantment '" + entry.getRegisteredName() +
                                        "' does not have a upgrading recipe");
            }
            if (craftingColors.containsKey(entry) && upgradeColors.containsKey(entry) && craftingColors.get(entry) !=
                                                                                         upgradeColors.get(entry)) {
                PastelCommon.logWarning(
                    "[SANITY: Enchantment Recipes] Enchantment recipes for '" + entry.getRegisteredName() +
                    "' use different pigments");
            }
        }
        for (Enchantment enchantment : registryManager.registryOrThrow(Registries.ENCHANTMENT)) {
            Holder<Enchantment> entry = registryManager.registryOrThrow(Registries.ENCHANTMENT)
                                                       .wrapAsHolder(enchantment);
            ResourceLocation id = entry.unwrapKey()
                                       .get()
                                       .location();
            if (id.getNamespace()
                  .equals(modId) && !entry.is(PastelEnchantmentTags.SPECTRUM_ENCHANTMENT)) {
                PastelCommon.logWarning(
                    "[SANITY: Enchantment Tags] Enchantment '" + id + "' is missing in the pastel:enchantments tag");
            }
        }

        // Trinkets that have invalid equip advancement and thus can't be equipped
        for (Map.Entry<ResourceKey<Item>, Item> item : BuiltInRegistries.ITEM.entrySet()) {
            if (item.getValue() instanceof PastelTrinketItem trinketItem) {
                ResourceLocation advancementIdentifier = trinketItem.getUnlockIdentifier();
                @Nullable AdvancementHolder advancementCriterionAdvancement = advancementLoader.get(
                    advancementIdentifier);
                if (advancementCriterionAdvancement == null) {
                    PastelCommon.logWarning("[SANITY: Trinkets] Trinket '" + item.getKey()
                                                                                 .location() +
                                            "' references advancement '" + advancementIdentifier +
                                            "' that does not exist");
                }
            }
        }

        PastelCommon.logInfo("##### SANITY CHECK FINISHED ######");

        PastelCommon.logInfo("##### SANITY CHECK PEDESTAL RECIPE STATISTICS ######");
        for (PedestalRecipeTier pedestalRecipeTier : PedestalRecipeTier.values()) {
            Map<GemstoneColor, Integer> entry = usedColorsForEachTier.get(pedestalRecipeTier);
            PastelCommon.logInfo("[SANITY: Pedestal Recipe Gemstone Usages] Gemstone Powder for tier " +
                                 StringUtils.leftPad(pedestalRecipeTier.toString(), 8) +
                                 ": C:" + StringUtils.leftPad(
                entry.get(BuiltinGemstoneColor.CYAN)
                     .toString(), 4
            ) +
                                 " M:" + StringUtils.leftPad(
                entry.get(BuiltinGemstoneColor.MAGENTA)
                     .toString(), 4
            ) +
                                 " Y:" + StringUtils.leftPad(
                entry.get(BuiltinGemstoneColor.YELLOW)
                     .toString(), 4
            ) +
                                 " K:" + StringUtils.leftPad(
                entry.get(BuiltinGemstoneColor.BLACK)
                     .toString(), 4
            ) +
                                 " W:" + StringUtils.leftPad(
                entry.get(BuiltinGemstoneColor.WHITE)
                     .toString(), 4
            ));
        }

        if (source.getEntity() instanceof ServerPlayer serverPlayerEntity) {
            serverPlayerEntity.displayClientMessage(
                Component.translatable("commands.pastel.progression_sanity.success"), false);
        }

        return 0;
    }

    private static <R extends GatedRecipe<C>, C extends RecipeInput> void testRecipeUnlocks(
        RecipeType<R> recipeType, String name, RecipeManager recipeManager,
        ServerAdvancementManager advancementLoader
    ) {
        for (RecipeHolder<R> recipe : recipeManager.getAllRecipesFor(recipeType)) {
            Optional<ResourceLocation> advancementIdentifier = recipe.value()
                                                                     .getRequiredAdvancementIdentifier();
            if (advancementIdentifier.isPresent() && advancementLoader.get(advancementIdentifier.get()) == null) {
                PastelCommon.logWarning(
                    "[SANITY: " + name + " Recipe Unlocks] Advancement '" + advancementIdentifier + "' in recipe '" +
                    recipe.id() + "' does not exist");
            }
        }
    }

    private static <R extends GatedRecipe<C>, C extends RecipeInput> void testIngredientsAndOutputInColorRegistry(
        RecipeType<R> recipeType, String name, RecipeManager recipeManager, RegistryAccess registryManager) {
        for (RecipeHolder<R> recipe : recipeManager.getAllRecipesFor(recipeType)) {
            for (Ingredient inputIngredient : recipe.value()
                                                    .getIngredients()) {
                for (ItemStack matchingItemStack : inputIngredient.getItems()) {
                    if (ColorRegistry.ITEM_COLORS.getMapping(matchingItemStack.getItem())
                                                 .isEmpty()) {
                        PastelCommon.logWarning("[SANITY: " + name + " Recipe] Input '" +
                                                BuiltInRegistries.ITEM.getKey(matchingItemStack.getItem()) +
                                                "' in recipe '" + recipe.id() +
                                                "', does not exist in the item color registry. Add it for nice " +
                                                "effects!");
                    }
                }
            }
            Item outputItem = recipe.value()
                                    .getResultItem(registryManager)
                                    .getItem();
            if (outputItem != null && outputItem != Items.AIR && ColorRegistry.ITEM_COLORS.getMapping(outputItem)
                                                                                          .isEmpty()) {
                PastelCommon.logWarning(
                    "[SANITY: " + name + " Recipe] Output '" + BuiltInRegistries.ITEM.getKey(outputItem) +
                    "' in recipe '" + recipe.id() +
                    "', does not exist in the item color registry. Add it for nice effects!");
            }
        }
    }

}
