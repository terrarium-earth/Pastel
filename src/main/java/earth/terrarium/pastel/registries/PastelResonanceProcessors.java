package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.api.predicate.block.BrokenBlockPredicate;
import earth.terrarium.pastel.data.DatagenProxy;
import earth.terrarium.pastel.data_loaders.resonance_processors.DropSelfResonanceProcessor;
import earth.terrarium.pastel.data_loaders.resonance_processors.ModifyDropsResonanceProcessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class PastelResonanceProcessors {
	
	private static final DeferredRegistrar.Contextual<DatagenProxy.BootstrapContext<ResonanceProcessor>> REGISTRAR = new DeferredRegistrar.Contextual<>(DatagenProxy.IS_DATAGEN);
	
	public static final ResourceKey<ResonanceProcessor> PURE_RESONANCES_FROM_ORE = register("pure_resonances_from_ore", ctx -> ModifyDropsResonanceProcessor
			.builder(BrokenBlockPredicate.Builder.create().registryEntryList(ctx.blocks().getOrThrow(Tags.Blocks.ORES)).build())
			.addModifiedDrop(Ingredient.of(Items.COAL), PastelItems.PURE_COAL.get())
			.addModifiedDrop(Ingredient.of(Items.RAW_COPPER), PastelItems.PURE_COPPER.get())
			.addModifiedDrop(Ingredient.of(Items.DIAMOND), PastelItems.PURE_DIAMOND.get())
			.addModifiedDrop(Ingredient.of(Items.ECHO_SHARD), PastelItems.PURE_ECHO.get())
			.addModifiedDrop(Ingredient.of(Items.EMERALD), PastelItems.PURE_EMERALD.get())
			.addModifiedDrop(Ingredient.of(Items.GLOWSTONE_DUST), PastelItems.PURE_GLOWSTONE.get())
			.addModifiedDrop(Ingredient.of(Items.RAW_GOLD), PastelItems.PURE_GOLD.get())
			.addModifiedDrop(Ingredient.of(Items.RAW_IRON), PastelItems.PURE_IRON.get())
			.addModifiedDrop(Ingredient.of(Items.LAPIS_LAZULI), PastelItems.PURE_LAPIS.get())
			.addModifiedDrop(Ingredient.of(Items.PRISMARINE_CRYSTALS), PastelItems.PURE_PRISMARINE.get())
			.addModifiedDrop(Ingredient.of(Items.QUARTZ), PastelItems.PURE_QUARTZ.get())
			.addModifiedDrop(Ingredient.of(Items.REDSTONE), PastelItems.PURE_REDSTONE.get())
			.addModifiedDrop(Ingredient.of(Items.ANCIENT_DEBRIS), PastelItems.PURE_NETHERITE_SCRAP.get())
			.addModifiedDrop(Ingredient.of(Items.NETHERITE_SCRAP), PastelItems.PURE_NETHERITE_SCRAP.get())
			.build());
	
	public static final ResourceKey<ResonanceProcessor> BLACK_MATERIA = registerDropSelf("black_materia", PastelBlocks.BLACK_MATERIA.get(), builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> BRUSHABLE_BLOCKS = registerDropSelf("brushable_blocks", PastelBlockTags.C_BRUSHABLE_BLOCKS, builder -> builder
			.copyNbt("LootTable", "LootTableSeed", "item"));
	
	public static final ResourceKey<ResonanceProcessor> BUDDING_BLOCKS = registerDropSelf("budding_blocks", Tags.Blocks.BUDDING_BLOCKS, builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> BUDS = registerDropSelf("buds", Tags.Blocks.BUDS, builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> CAKE = registerDropSelf("cake", Blocks.CAKE, builder -> builder
			.copyState("bites"));
	
	public static final ResourceKey<ResonanceProcessor> CLUSTERS = registerDropSelf("clusters", Tags.Blocks.CLUSTERS, builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> COMPOSTER = registerDropSelf("composter", Blocks.COMPOSTER, builder -> builder
			.copyState("level"));
	
	public static final ResourceKey<ResonanceProcessor> FROGSPAWN = registerDropSelf("frogspawn", Blocks.FROGSPAWN, builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> GILDED_BLACKSTONE = registerDropSelf("gilded_blackstone", Blocks.GILDED_BLACKSTONE, builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> INFESTED_BLOCKS = registerDropSelf("infested_blocks", PastelBlockTags.C_INFESTED_BLOCKS, builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> REINFORCED_DEEPSLATE = registerDropSelf("reinforced_deepslate", Blocks.REINFORCED_DEEPSLATE, builder -> builder);
	
	public static final ResourceKey<ResonanceProcessor> RESPAWN_ANCHOR = registerDropSelf("respawn_anchor", Blocks.RESPAWN_ANCHOR, builder -> builder
			.copyState("charges"));
	
	public static final ResourceKey<ResonanceProcessor> SCULK_SHRIEKER = registerDropSelf("sculk_shrieker", Blocks.SCULK_SHRIEKER, builder -> builder
			.copyState("can_summon"));
	
	public static final ResourceKey<ResonanceProcessor> SIGNS = registerDropSelf("signs", BlockTags.ALL_SIGNS, builder -> builder
			.copyNbt("front_text", "back_text", "is_waxed"));
	
	public static final ResourceKey<ResonanceProcessor> SPAWNER = registerDropSelf("spawner", Blocks.SPAWNER, builder -> builder
			.copyNbt("SpawnData", "SpawnCount", "MinSpawnDelay", "MaxSpawnDelay", "SpawnRange", "RequiredPlayerRange", "SpawnPotentials", "MaxNearbyEntities"));
	
	public static ResourceKey<ResonanceProcessor> registerDropSelf(String id, Block block, UnaryOperator<DropSelfResonanceProcessor.Builder> builder) {
		return register(id, ctx -> builder.apply(DropSelfResonanceProcessor.builder(BrokenBlockPredicate.Builder.create().blocks(block).build())).build());
	}
	
	public static ResourceKey<ResonanceProcessor> registerDropSelf(String id, TagKey<Block> tag, UnaryOperator<DropSelfResonanceProcessor.Builder> builder) {
		return register(id, ctx -> builder.apply(DropSelfResonanceProcessor.builder(BrokenBlockPredicate.Builder.create().registryEntryList(ctx.blocks().getOrThrow(tag)).build())).build());
	}
	
	public static ResourceKey<ResonanceProcessor> register(String id, Function<DatagenProxy.BootstrapContext<ResonanceProcessor>, ResonanceProcessor> processor) {
		ResourceKey<ResonanceProcessor> key = ResourceKey.create(PastelRegistryKeys.RESONANCE_PROCESSOR, PastelCommon.locate(id));
		REGISTRAR.defer(ctx -> ctx.registerable().register(key, processor.apply(ctx)));
		return key;
	}
	
	public static void provideResonanceProcessors(DatagenProxy.BootstrapContext<ResonanceProcessor> ctx) {
		REGISTRAR.flush(ctx);
	}
	
}
