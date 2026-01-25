package earth.terrarium.pastel.data.databank;

import com.cmdpro.databank.datagen.HiddenDatagenProvider;
import com.cmdpro.databank.hidden.HiddenCondition;
import com.cmdpro.databank.hidden.conditions.ActualPlayerCondition;
import com.cmdpro.databank.hidden.conditions.NotCondition;
import com.cmdpro.databank.hidden.types.BlockHiddenType;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.FluidLogging;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSaplingBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredTree;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.PottedColoredSaplingBlock;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PastelHiddenProvider extends HiddenDatagenProvider {

    public PastelHiddenProvider(
        PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
        @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, PastelCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void gather() {
        allColors((color) -> addLog(ColoredLogBlock.byColor(color)));
        allColors((color) -> addLeaves(ColoredLeavesBlock.byColor(color)));
        allColors((color) -> addSapling(ColoredSaplingBlock.byColor(color)));
        allColors((color) -> addWood(ColoredWoodBlock.byColor(color)));
        allColors((color) -> addStrippedLog(ColoredStrippedLogBlock.byColor(color)));
        allColors((color) -> addStrippedWood(ColoredStrippedWoodBlock.byColor(color)));
        allColors((color) -> addPottedSapling(PottedColoredSaplingBlock.byColor(color)));
        allColors((color) -> hideItem(
            PigmentItem.byColor(color), PigmentItem.byColor(color).hiddenAs,
            PastelAdvancements.CRAFT_COLORED_SAPLING
        ));

        hideBlockWithItem(
            PastelBlocks.RADIATING_ENDER.get(), Blocks.COBBLESTONE,
            PastelAdvancements.Milestones.REVEAL_RADIATING_ENDER
        );
        hideBlockWithItem(
            PastelBlocks.FOUR_LEAF_CLOVER.get(), PastelBlocks.CLOVER.get(),
            PastelAdvancements.Milestones.REVEAL_FOUR_LEAF_CLOVER
        );
        hideBlockWithItem(
            PastelBlocks.AMARANTH_BUSHEL.get(), Blocks.FERN, PastelAdvancements.Milestones.REVEAL_AMARANTH);
        hideBlockWithItem(
            PastelBlocks.RESONANT_LILY.get(), Blocks.WHITE_TULIP, PastelAdvancements.Midgame.COLLECT_RESONANT_LILY);
        hideBlockWithItem(
            PastelBlocks.DRAGONBONE.get(), Blocks.BONE_BLOCK, PastelAdvancements.Milestones.REVEAL_DRAGONBONE);
        hideBlockWithItem(
            PastelBlocks.STRATINE_ORE.get(), Blocks.NETHERRACK, PastelAdvancements.Milestones.REVEAL_STRATINE);
        hideBlockWithItem(
            PastelBlocks.PALTAERIA_ORE.get(), Blocks.END_STONE, PastelAdvancements.Milestones.REVEAL_PALTAERIA);

        hideBlock(
            PastelBlocks.BLOOD_ORCHID.get(), Blocks.RED_TULIP, PastelAdvancements.Midgame.COLLECT_BLOOD_ORCHID_PETAL);
        hideBlock(
            PastelBlocks.POTTED_BLOOD_ORCHID.get(), Blocks.POTTED_RED_TULIP,
            PastelAdvancements.Midgame.COLLECT_BLOOD_ORCHID_PETAL
        );
        hideBlock(
            PastelBlocks.MERMAIDS_BRUSH.get(), Blocks.SEAGRASS, PastelAdvancements.Milestones.REVEAL_MERMAIDS_BRUSH);
        hideBlock(
            PastelBlocks.POTTED_AMARANTH_BUSHEL.get(), Blocks.POTTED_FERN,
            PastelAdvancements.Milestones.REVEAL_AMARANTH
        );
        hideBlock(PastelBlocks.STUCK_STORM_STONE.get(), Blocks.AIR, PastelAdvancements.Milestones.REVEAL_STORM_STONES);
        hideBlock(
            PastelBlocks.POTTED_RESONANT_LILY.get(), Blocks.POTTED_WHITE_TULIP,
            PastelAdvancements.Midgame.COLLECT_RESONANT_LILY
        );

        hideItem(
            PastelBlocks.BLOOD_ORCHID.asItem(), Blocks.RED_TULIP.asItem(),
            PastelAdvancements.Midgame.COLLECT_BLOOD_ORCHID_PETAL
        );
        hideItem(PastelItems.AMARANTH_GRAINS.get(), Items.LARGE_FERN, PastelAdvancements.Milestones.REVEAL_AMARANTH);
        hideItem(PastelItems.MERMAIDS_GEM.get(), Items.KELP, PastelAdvancements.PLACE_PEDESTAL);
        hideItem(PastelItems.STORM_STONE.get(), Items.YELLOW_DYE, PastelAdvancements.Milestones.REVEAL_STORM_STONES);
        hideItem(PastelItems.VEGETAL.get(), Items.GUNPOWDER, PastelAdvancements.CRAFT_BOTTLE_OF_FADING);
        hideItem(PastelItems.NEOLITH.get(), Items.GUNPOWDER, PastelAdvancements.Midgame.CRAFT_BOTTLE_OF_FAILING);
        hideItem(PastelItems.BEDROCK_DUST.get(), Items.GUNPOWDER, PastelAdvancements.Midgame.BREAK_DECAYED_BEDROCK);
        hideItem(
            PastelItems.SHIMMERSTONE_GEM.get(), Items.YELLOW_DYE, PastelAdvancements.Milestones.REVEAL_SHIMMERSTONE);
        hideItem(PastelItems.RAW_AZURITE.get(), Items.BLUE_DYE, PastelAdvancements.Milestones.REVEAL_AZURITE);
        hideItem(PastelItems.STARDUST.get(), Items.PURPLE_DYE, PastelAdvancements.Milestones.UNLOCK_SHOOTING_STARS);
        hideItem(
            PastelItems.JADE_PETALS.get(), Items.LIME_DYE,
            PastelAdvancements.Midgame.BUILD_SPIRIT_INSTILLER_STRUCTURE
        );
        hideItem(
            PastelItems.STAR_FRAGMENT.get(), Items.PURPLE_DYE, PastelAdvancements.Milestones.UNLOCK_SHOOTING_STARS);
        hideItem(
            PastelItems.QUITOXIC_POWDER.get(), Items.PURPLE_DYE, PastelAdvancements.Milestones.REVEAL_QUITOXIC_REEDS);
        hideItem(
            PastelItems.GERMINATED_JADE_VINE_BULB.get(), Items.LIME_DYE,
            PastelAdvancements.Hidden.COLLECT_HIBERNATING_JADE_VINE_BULB
        );
        hideItem(
            PastelItems.BLOOD_ORCHID_PETAL.get(), Items.RED_DYE,
            PastelAdvancements.Milestones.REVEAL_BLOOD_ORCHID_PETALS
        );
        hideItem(PastelItems.PURE_AZURITE.get(), Items.BLUE_DYE, PastelAdvancements.Milestones.REVEAL_AZURITE);
        hideItem(PastelItems.DRAGONBONE_CHUNK.get(), Items.GRAY_DYE, PastelAdvancements.BREAK_CRACKED_DRAGONBONE);
        hideItem(PastelItems.BONE_ASH.get(), Items.GRAY_DYE, PastelAdvancements.BREAK_CRACKED_DRAGONBONE);
        hideItem(PastelItems.RESPLENDENT_FEATHER.get(), Items.RED_DYE, PastelAdvancements.PLUCK_RESPLENDENT_FEATHER);
        hideItem(PastelItems.RAW_BLOODSTONE.get(), Items.RED_DYE, PastelAdvancements.PLUCK_RESPLENDENT_FEATHER);
        hideItem(PastelItems.PURE_BLOODSTONE.get(), Items.RED_DYE, PastelAdvancements.PLUCK_RESPLENDENT_FEATHER);
        hideItem(
            PastelItems.DOWNSTONE_FRAGMENTS.get(), Items.LIGHT_GRAY_DYE,
            PastelAdvancements.Lategame.FIND_EXCAVATION_SITE
        );
        hideItem(
            PastelItems.RESONANCE_SHARD.get(), Items.LIGHT_BLUE_DYE,
            PastelAdvancements.Lategame.STRIKE_UP_HUMMINGSTONE_HYMN
        );
        hideItem(
            PastelItems.NECTARDEW_BURGEON.get(), PastelItems.NIGHTDEW_SPROUT.get(),
            PastelAdvancements.Lategame.COLLECT_NECTARDEW
        );
        hideItem(PastelItems.MYCEYLON.get(), Items.ORANGE_DYE, PastelAdvancements.Lategame.COLLECT_MYCEYLON);
        hideItem(PastelItems.INCANDESCENT_ESSENCE.get(), Items.ORANGE_DYE, PastelAdvancements.Midgame.PASTEL_MIDGAME);
        hideItem(PastelItems.FROSTBITE_ESSENCE.get(), Items.LIGHT_BLUE_DYE, PastelAdvancements.Midgame.PASTEL_MIDGAME);
        hideItem(PastelItems.MOONSTONE_CORE.get(), Items.WHITE_DYE, PastelAdvancements.Lategame.FIND_FORGOTTEN_CITY);
        hideItem(
            PastelItems.MIDNIGHT_CHIP.get(), Items.GRAY_DYE, PastelAdvancements.Midgame.CREATE_MIDNIGHT_ABERRATION);
        hideItem(PastelItems.BISMUTH_FLAKE.get(), Items.CYAN_DYE, PastelAdvancements.Midgame.ENTER_DIMENSION);
        hideItem(PastelItems.BISMUTH_CRYSTAL.get(), Items.CYAN_DYE, PastelAdvancements.Midgame.ENTER_DIMENSION);
        hideItem(PastelItems.RAW_MALACHITE.get(), Items.GREEN_DYE, PastelAdvancements.Milestones.REVEAL_MALACHITE);
        hideItem(PastelItems.PURE_MALACHITE.get(), Items.GREEN_DYE, PastelAdvancements.Milestones.REVEAL_MALACHITE);
        hideItem(PastelItems.TOPAZ_POWDER.get(), Items.CYAN_DYE, PastelAdvancements.Hidden.CollectShards.TOPAZ);
        hideItem(
            PastelItems.AMETHYST_POWDER.get(), Items.MAGENTA_DYE, PastelAdvancements.Hidden.CollectShards.AMETHYST);
        hideItem(PastelItems.CITRINE_POWDER.get(), Items.YELLOW_DYE, PastelAdvancements.Hidden.CollectShards.CITRINE);
        hideItem(PastelItems.ONYX_POWDER.get(), Items.BLACK_DYE, PastelAdvancements.CREATE_ONYX_SHARD);
        hideItem(PastelItems.MOONSTONE_POWDER.get(), Items.WHITE_DYE, PastelAdvancements.Lategame.COLLECT_MOONSTONE);
        hideItem(
            PastelItems.ONYX_SHARD.get(), Items.BLACK_DYE, PastelAdvancements.COLLECT_ALL_BASIC_PIGMENTS_BESIDES_BROWN);
        hideItem(PastelItems.MOONSTONE_SHARD.get(), Items.WHITE_DYE, PastelAdvancements.Midgame.BREAK_DECAYED_BEDROCK);
        hideItem(PastelItems.STRATINE_FRAGMENTS.get(), Items.RED_DYE, PastelAdvancements.Milestones.REVEAL_STRATINE);
        hideItem(PastelItems.STRATINE_GEM.get(), Items.RED_DYE, PastelAdvancements.Milestones.REVEAL_STRATINE);
        hideItem(PastelItems.PALTAERIA_FRAGMENTS.get(), Items.CYAN_DYE, PastelAdvancements.Milestones.REVEAL_PALTAERIA);
        hideItem(PastelItems.PALTAERIA_GEM.get(), Items.CYAN_DYE, PastelAdvancements.Lategame.CRAFT_RESONANT_TOOL);

        createQuitoxicReeds(
            PastelBlocks.QUITOXIC_REEDS.get(), Blocks.SUGAR_CANE.asItem(),
            PastelAdvancements.Milestones.REVEAL_QUITOXIC_REEDS
        );

        hideOre(
            PastelBlocks.AZURITE_ORE.get(), PastelBlocks.DEEPSLATE_AZURITE_ORE.get(),
            null, PastelAdvancements.Milestones.REVEAL_AZURITE
        );
        hideOre(
            PastelBlocks.MALACHITE_ORE.get(), PastelBlocks.DEEPSLATE_MALACHITE_ORE.get(),
            PastelBlocks.BLACKSLAG_MALACHITE_ORE.get(), PastelAdvancements.Milestones.REVEAL_MALACHITE
        );
        hideOre(
            PastelBlocks.SHIMMERSTONE_ORE.get(), PastelBlocks.DEEPSLATE_SHIMMERSTONE_ORE.get(),null,
            PastelAdvancements.Milestones.REVEAL_SHIMMERSTONE
        );
        hideOre(
            PastelBlocks.TOPAZ_ORE.get(), PastelBlocks.DEEPSLATE_TOPAZ_ORE.get(),
            PastelBlocks.BLACKSLAG_TOPAZ_ORE.get(), PastelAdvancements.Hidden.CollectShards.TOPAZ
        );
        hideOre(
            PastelBlocks.AMETHYST_ORE.get(), PastelBlocks.DEEPSLATE_AMETHYST_ORE.get(),
            PastelBlocks.BLACKSLAG_AMETHYST_ORE.get(), PastelAdvancements.Hidden.CollectShards.AMETHYST
        );
        hideOre(
            PastelBlocks.CITRINE_ORE.get(), PastelBlocks.DEEPSLATE_CITRINE_ORE.get(),
            PastelBlocks.BLACKSLAG_CITRINE_ORE.get(), PastelAdvancements.Hidden.CollectShards.CITRINE
        );
        hideOre(
            PastelBlocks.ONYX_ORE.get(), PastelBlocks.DEEPSLATE_ONYX_ORE.get(), PastelBlocks.BLACKSLAG_ONYX_ORE.get(),
            PastelAdvancements.CREATE_ONYX_SHARD
        );
        hideOre(
            PastelBlocks.MOONSTONE_ORE.get(), PastelBlocks.DEEPSLATE_MOONSTONE_ORE.get(),
            PastelBlocks.BLACKSLAG_MOONSTONE_ORE.get(), PastelAdvancements.Lategame.COLLECT_MOONSTONE
        );

        hideItemWithNameOverride(
            PastelItems.MIDNIGHT_ABERRATION.get(), PastelItems.SPECTRAL_SHARD.get(),
            PastelAdvancements.Midgame.CREATE_MIDNIGHT_ABERRATION,
            Component.translatable("item.pastel.midnight_aberration.cloaked")
        );
    }

    public String getName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block)
                                      .getPath();
    }

    public String getName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item)
                                     .getPath();
    }

    //Maybe rename this into something more general? Not sure what to change it to though
    private void createQuitoxicReeds(Block block, Item itemHiddenAs, ResourceLocation advancement) {
        BlockHiddenType.BlockHiddenOverride waterOverride = createOverride(
            StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(FluidLogging.ANY_INCLUDING_NONE, "water")
                                            .build()
                                            .orElseThrow(), Blocks.WATER
        );
        BlockHiddenType.BlockHiddenOverride crystalOverride = createOverride(
            StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(FluidLogging.ANY_INCLUDING_NONE, "liquid_crystal")
                                            .build()
                                            .orElseThrow(), PastelBlocks.LIQUID_CRYSTAL.get()
        );
        createHidden(
            PastelCommon.locate("blocks/" + getName(block)), addOverride(
                addOverride(
                    setOriginalLootCondition(
                        createBlockInstance(block, Blocks.AIR),
                        new NotCondition(new ActualPlayerCondition())
                    ), waterOverride
                ), crystalOverride
            ), createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
        );
        hideItem(block.asItem(), itemHiddenAs, advancement);
    }

    public void hideOre(Block stone, Block deepslate, Block blackslag, ResourceLocation advancement) {
        if (stone != null) {
            hideBlockWithItem(stone, Blocks.STONE, advancement);
        }
        if (deepslate != null) {
            hideBlockWithItem(deepslate, Blocks.DEEPSLATE, advancement);
        }
        if (blackslag != null) {
            hideBlockWithItem(blackslag, PastelBlocks.BLACKSLAG.get(), advancement);
        }
    }

    public void hideBlockWithItem(Block block, Block hiddenAs, ResourceLocation advancement) {
        hideBlock(block, hiddenAs, advancement);
        hideItem(block.asItem(), hiddenAs.asItem(), advancement);
    }

    public void hideBlock(Block block, Block hiddenAs, ResourceLocation advancement) {
        createHidden(
            PastelCommon.locate("blocks/" + getName(block)), setOriginalLootCondition(
                setNameOverride(
                    createBlockInstance(block, hiddenAs), Component.translatable(hiddenAs.getDescriptionId())
                                                                   .append(
                                                                       Component.translatable("pastel.cloaked.suffix"))
                ), new NotCondition(new ActualPlayerCondition())
            ), createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
        );
    }

    public void hideItem(Item item, Item hiddenAs, ResourceLocation advancement) {
        createHidden(
            PastelCommon.locate("items/" + getName(item)), setNameOverride(
                createItemInstance(item, hiddenAs), Component.translatable(hiddenAs.getDescriptionId())
                                                             .append(Component.translatable("pastel.cloaked.suffix"))
            ), createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
        );
    }

    public void hideItemWithNameOverride(
        Item item, Item hiddenAs, ResourceLocation advancement, Component nameOverride) {
        createHidden(
            PastelCommon.locate("items/" + getName(item)),
            setNameOverride(createItemInstance(item, hiddenAs), nameOverride),
            createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
        );
    }

    public void allColors(Consumer<InkColor> consumer) {
        for (InkColor i : InkColors.all()) {
            consumer.accept(i);
        }
    }

    public void addTreePart(Block block, Block hiddenAs, String directory, ColoredTree.TreePart part, boolean item) {
        if (block instanceof ColoredTree tree) {
            String name = getName(block);
            ResourceLocation advancement = ColoredTree.getTreeCloakAdvancementIdentifier(part, tree.getColor());
            createHidden(
                PastelCommon.locate("blocks/" + directory + "/" + name), setOriginalLootCondition(
                    setNameOverride(
                        createBlockInstance(block, hiddenAs), Component.translatable(hiddenAs.getDescriptionId())
                                                                       .append(Component.translatable(
                                                                           "pastel.cloaked.suffix"))
                    ), new NotCondition(new ActualPlayerCondition())
                ), createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
            );
            createHidden(
                PastelCommon.locate("items/" + directory + "/" + name), setNameOverride(
                    createItemInstance(block.asItem(), hiddenAs.asItem()),
                    Component.translatable(hiddenAs.getDescriptionId())
                             .append(Component.translatable("pastel.cloaked.suffix"))
                ), createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
            );
        }
    }

    public void addTreePart(Block block, Block hiddenAs, String directory, ColoredTree.TreePart part) {
        addTreePart(block, hiddenAs, directory, part, true);
    }

    public void addLog(Block block) {
        addTreePart(block, Blocks.OAK_LOG, "logs", ColoredTree.TreePart.LOG);
    }

    public void addLeaves(Block block) {
        addTreePart(block, Blocks.OAK_LEAVES, "leaves", ColoredTree.TreePart.LEAVES);
    }

    public void addSapling(Block block) {
        addTreePart(block, Blocks.OAK_SAPLING, "saplings", ColoredTree.TreePart.SAPLING);
    }

    public void addWood(Block block) {
        addTreePart(block, Blocks.OAK_WOOD, "woods", ColoredTree.TreePart.WOOD);
    }

    public void addStrippedWood(Block block) {
        addTreePart(block, Blocks.STRIPPED_OAK_WOOD, "stripped_woods", ColoredTree.TreePart.STRIPPED_WOOD);
    }

    public void addStrippedLog(Block block) {
        addTreePart(block, Blocks.STRIPPED_OAK_LOG, "stripped_logs", ColoredTree.TreePart.STRIPPED_LOG);
    }

    public void addPottedSapling(Block block) {
        addTreePart(block, Blocks.POTTED_OAK_SAPLING, "potted_saplings", ColoredTree.TreePart.SAPLING, false);
    }
}
