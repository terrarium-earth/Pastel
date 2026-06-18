package earth.terrarium.pastel.data.loot.block;

import earth.terrarium.pastel.blocks.decay.DecayBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

public class FunctionalBlockLootTables extends BlockLootSubProvider {
    public FunctionalBlockLootTables(
        HolderLookup.Provider registries
    ) {
        super(Set.of(PastelBlocks.CRACKED_END_PORTAL_FRAME.asItem()), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    private static final List<Block> dropSelfBlocks = List.of(
        PastelBlocks.TEA_TABLE.get(), PastelBlocks.ETHEREAL_PLATFORM.get(), PastelBlocks.UNIVERSE_SPYHOLE.get(),
        PastelBlocks.HOVERBLOCK.get(), PastelBlocks.HEARTBOUND_CHEST.get(), PastelBlocks.COMPACTING_CHEST.get(),
        PastelBlocks.FABRICATION_CHEST.get(), PastelBlocks.BLACK_HOLE_CHEST.get(), PastelBlocks.PARTICLE_SPAWNER.get(),
        PastelBlocks.BEDROCK_ANVIL.get(), PastelBlocks.CRACKED_END_PORTAL_FRAME.get(), PastelBlocks.LAVA_SPONGE.get(),
        PastelBlocks.WET_LAVA_SPONGE.get(), PastelBlocks.BLOCK_LIGHT_DETECTOR.get(),
        PastelBlocks.WEATHER_DETECTOR.get(), PastelBlocks.PLAYER_DETECTOR.get(), PastelBlocks.ITEM_DETECTOR.get(),
        PastelBlocks.CREATURE_DETECTOR.get(), PastelBlocks.REDSTONE_TIMER.get(), PastelBlocks.REDSTONE_CALCULATOR.get(),
        PastelBlocks.REDSTONE_TRANSCEIVER.get(), PastelBlocks.BLOCK_PLACER.get(), PastelBlocks.BLOCK_DETECTOR.get(),
        PastelBlocks.BLOCK_BREAKER.get(), PastelBlocks.ENDER_DROPPER.get(), PastelBlocks.UPGRADE_EFFICIENCY.get(),
        PastelBlocks.UPGRADE_EFFICIENCY2.get(), PastelBlocks.UPGRADE_EXPERIENCE.get(),
        PastelBlocks.UPGRADE_EXPERIENCE2.get(), PastelBlocks.UPGRADE_SPEED.get(), PastelBlocks.UPGRADE_SPEED2.get(),
        PastelBlocks.UPGRADE_SPEED3.get(), PastelBlocks.UPGRADE_YIELD.get(), PastelBlocks.UPGRADE_YIELD2.get(),
        PastelBlocks.REDSTONE_SAND.get(), PastelBlocks.INCANDESCENT_AMALGAM.get(),

        PastelBlocks.CRYSTAL_APOTHECARY.get(), PastelBlocks.ENDER_HOPPER.get(),

        PastelBlocks.CONNECTION_NODE.get(), PastelBlocks.PROVIDER_NODE.get(), PastelBlocks.STORAGE_NODE.get(),
        PastelBlocks.BUFFER_NODE.get(), PastelBlocks.SENDER_NODE.get(), PastelBlocks.GATHER_NODE.get(),

        PastelBlocks.AXOLOTL_IDOL.get(), PastelBlocks.BAT_IDOL.get(), PastelBlocks.BEE_IDOL.get(),
        PastelBlocks.BLAZE_IDOL.get(), PastelBlocks.CAT_IDOL.get(), PastelBlocks.CHICKEN_IDOL.get(),
        PastelBlocks.COW_IDOL.get(), PastelBlocks.CREEPER_IDOL.get(), PastelBlocks.ENDER_DRAGON_IDOL.get(),
        PastelBlocks.ENDERMAN_IDOL.get(), PastelBlocks.ENDERMITE_IDOL.get(), PastelBlocks.EVOKER_IDOL.get(),
        PastelBlocks.FISH_IDOL.get(), PastelBlocks.FOX_IDOL.get(), PastelBlocks.GHAST_IDOL.get(),
        PastelBlocks.GLOW_SQUID_IDOL.get(), PastelBlocks.GOAT_IDOL.get(), PastelBlocks.GUARDIAN_IDOL.get(),
        PastelBlocks.HORSE_IDOL.get(), PastelBlocks.ILLUSIONER_IDOL.get(), PastelBlocks.OCELOT_IDOL.get(),
        PastelBlocks.PARROT_IDOL.get(), PastelBlocks.PHANTOM_IDOL.get(), PastelBlocks.PIG_IDOL.get(),
        PastelBlocks.PIGLIN_IDOL.get(), PastelBlocks.POLAR_BEAR_IDOL.get(), PastelBlocks.PUFFERFISH_IDOL.get(),
        PastelBlocks.RABBIT_IDOL.get(), PastelBlocks.SHEEP_IDOL.get(), PastelBlocks.SHULKER_IDOL.get(),
        PastelBlocks.SILVERFISH_IDOL.get(), PastelBlocks.SKELETON_IDOL.get(), PastelBlocks.SLIME_IDOL.get(),
        PastelBlocks.SNOW_GOLEM_IDOL.get(), PastelBlocks.SPIDER_IDOL.get(), PastelBlocks.SQUID_IDOL.get(),
        PastelBlocks.STRAY_IDOL.get(), PastelBlocks.STRIDER_IDOL.get(), PastelBlocks.TURTLE_IDOL.get(),
        PastelBlocks.WITCH_IDOL.get(), PastelBlocks.WITHER_IDOL.get(), PastelBlocks.WITHER_SKELETON_IDOL.get(),
        PastelBlocks.ZOMBIE_IDOL.get()
    );

    private static final List<Block> silkTouchBlocks = List.of(PastelBlocks.ENDER_GLASS.get());

    private static final Map<Block, ItemLike> decayBlocks = Map.ofEntries(
        entry(PastelBlocks.FADING.get(), PastelItems.VEGETAL), entry(PastelBlocks.FAILING.get(), PastelItems.NEOLITH),
        entry(PastelBlocks.RUIN.get(), PastelItems.BEDROCK_DUST),
        entry(PastelBlocks.FORFEITURE.get(), PastelItems.BEDROCK_DUST)
    );

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> knownBlocks = new ArrayList<>(dropSelfBlocks);
        knownBlocks.addAll(decayBlocks.keySet());
        knownBlocks.addAll(silkTouchBlocks);
        return knownBlocks;
    }

    // and we never had to think about this code again
    private void addConversion(Block decay, ItemLike decayDrop) {
        add(
            decay, LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                              .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(decay)
                                                                                       .setProperties(
                                                                                           StatePropertiesPredicate.Builder.properties()
                                                                                                                           .hasProperty(
                                                                                                                               DecayBlock.CONVERSION,
                                                                                                                               DecayBlock.Conversion.DEFAULT
                                                                                                                           )))
                                              .setRolls(ConstantValue.exactly(1))
                                              .add(LootItem.lootTableItem(decayDrop)))
                            .withPool(LootPool.lootPool()
                                              .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(decay)
                                                                                       .setProperties(
                                                                                           StatePropertiesPredicate.Builder.properties()
                                                                                                                           .hasProperty(
                                                                                                                               DecayBlock.CONVERSION,
                                                                                                                               DecayBlock.Conversion.SPECIAL
                                                                                                                           )))
                                              .setRolls(UniformGenerator.between(3.0f, 5.0f))
                                              .add(LootItem.lootTableItem(decayDrop)))
        );
    }

    @Override
    protected void generate() {
        dropSelfBlocks.forEach(this::dropSelf);
        silkTouchBlocks.forEach(this::dropWhenSilkTouch);
        decayBlocks.forEach(this::addConversion);
    }
}
