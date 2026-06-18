package earth.terrarium.pastel.data.dynamicregistries;

import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.api.predicate.block.BrokenBlockPredicate;
import earth.terrarium.pastel.data_loaders.resonance_processors.DropSelfResonanceProcessor;
import earth.terrarium.pastel.data_loaders.resonance_processors.ModifyDropsResonanceProcessor;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.function.UnaryOperator;

public class ResonanceProcessorsRegistry {

    public static void registerResonanceProcessors(
        BootstrapContext<ResonanceProcessor> bootstrap
    ) {
        var blocks = bootstrap.lookup(Registries.BLOCK);
        bootstrap
            .register(
                PastelResonanceProcessors.PURE_RESONANCES_FROM_ORE,
                ModifyDropsResonanceProcessor
                    .builder(
                        BrokenBlockPredicate.Builder
                            .create()
                            .registryEntryList(blocks.getOrThrow(Tags.Blocks.ORES))
                            .build()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.COAL
                            ),
                        PastelItems.PURE_COAL.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.RAW_COPPER
                            ),
                        PastelItems.PURE_COPPER.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.DIAMOND
                            ),
                        PastelItems.PURE_DIAMOND.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.ECHO_SHARD
                            ),
                        PastelItems.PURE_ECHO.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.EMERALD
                            ),
                        PastelItems.PURE_EMERALD.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.GLOWSTONE_DUST
                            ),
                        PastelItems.PURE_GLOWSTONE.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.RAW_GOLD
                            ),
                        PastelItems.PURE_GOLD.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.RAW_IRON
                            ),
                        PastelItems.PURE_IRON.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.LAPIS_LAZULI
                            ),
                        PastelItems.PURE_LAPIS.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.PRISMARINE_CRYSTALS
                            ),
                        PastelItems.PURE_PRISMARINE.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.QUARTZ
                            ),
                        PastelItems.PURE_QUARTZ.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.REDSTONE
                            ),
                        PastelItems.PURE_REDSTONE.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.ANCIENT_DEBRIS
                            ),
                        PastelItems.PURE_NETHERITE_SCRAP.get()
                    )
                    .addModifiedDrop(
                        Ingredient
                            .of(
                                Items.NETHERITE_SCRAP
                            ),
                        PastelItems.PURE_NETHERITE_SCRAP.get()
                    )
                    .build()
            );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.BLACK_MATERIA,
            PastelBlocks.BLACK_MATERIA.get(),
            builder -> builder
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.BRUSHABLE_BLOCKS,
            PastelBlockTags.C_BRUSHABLE_BLOCKS,
            builder -> builder.copyNbt("LootTable", "LootTableSeed", "item")
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.BUDDING_BLOCKS,
            Tags.Blocks.BUDDING_BLOCKS,
            builder -> builder
        );

        registerDropSelf(bootstrap, PastelResonanceProcessors.BUDS, Tags.Blocks.BUDS, builder -> builder);

        registerDropSelf(bootstrap, PastelResonanceProcessors.CAKE, Blocks.CAKE, builder -> builder.copyState("bites"));

        registerDropSelf(bootstrap, PastelResonanceProcessors.CLUSTERS, Tags.Blocks.CLUSTERS, builder -> builder);

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.COMPOSTER,
            Blocks.COMPOSTER,
            builder -> builder.copyState("level")
        );

        registerDropSelf(bootstrap, PastelResonanceProcessors.FROGSPAWN, Blocks.FROGSPAWN, builder -> builder);

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.GILDED_BLACKSTONE,
            Blocks.GILDED_BLACKSTONE,
            builder -> builder
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.INFESTED_BLOCKS,
            PastelBlockTags.C_INFESTED_BLOCKS,
            builder -> builder
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.REINFORCED_DEEPSLATE,
            Blocks.REINFORCED_DEEPSLATE,
            builder -> builder
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.RESPAWN_ANCHOR,
            Blocks.RESPAWN_ANCHOR,
            builder -> builder.copyState("charges")
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.SCULK_SHRIEKER,
            Blocks.SCULK_SHRIEKER,
            builder -> builder.copyState("can_summon")
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.SIGNS,
            BlockTags.ALL_SIGNS,
            builder -> builder.copyNbt("front_text", "back_text", "is_waxed")
        );

        registerDropSelf(
            bootstrap,
            PastelResonanceProcessors.SPAWNER,
            Blocks.SPAWNER,
            builder -> builder
                .copyNbt(
                    "SpawnData",
                    "SpawnCount",
                    "MinSpawnDelay",
                    "MaxSpawnDelay",
                    "SpawnRange",
                    "RequiredPlayerRange",
                    "SpawnPotentials",
                    "MaxNearbyEntities"
                )
        );

    }

    public static void registerDropSelf(
        BootstrapContext<ResonanceProcessor> bootstrap,
        ResourceKey<ResonanceProcessor> key,
        Block block,
        UnaryOperator<DropSelfResonanceProcessor.Builder> builder
    ) {
        bootstrap
            .register(
                key,
                builder
                    .apply(
                        DropSelfResonanceProcessor
                            .builder(
                                BrokenBlockPredicate.Builder
                                    .create()
                                    .blocks(block)
                                    .build()
                            )
                    )
                    .build()
            );
    }

    public static void registerDropSelf(
        BootstrapContext<ResonanceProcessor> bootstrap,
        ResourceKey<ResonanceProcessor> key,
        TagKey<Block> tag,
        UnaryOperator<DropSelfResonanceProcessor.Builder> builder
    ) {
        bootstrap
            .register(
                key,
                builder
                    .apply(
                        DropSelfResonanceProcessor
                            .builder(
                                BrokenBlockPredicate.Builder
                                    .create()
                                    .registryEntryList(
                                        bootstrap
                                            .lookup(
                                                Registries.BLOCK
                                            )
                                            .getOrThrow(
                                                tag
                                            )
                                    )
                                    .build()
                            )
                    )
                    .build()
            );
    }
}
