package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.api.predicate.block.*;
import de.dafuqs.spectrum.data_loaders.resonance_processors.*;
import net.fabricmc.fabric.api.tag.convention.v2.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

import java.util.function.*;

import static de.dafuqs.spectrum.SpectrumDataGenerator.*;

@SuppressWarnings("unused")
public class SpectrumResonanceProcessors {
	
	private static final Deferrer.Contextual<BootstrapContext<ResonanceProcessor>> DEFERRER = new Deferrer.Contextual<>();
	
	public static final RegistryKey<ResonanceProcessor> PURE_RESONANCES_FROM_ORE = register("pure_resonances_from_ore", ctx -> ModifyDropsResonanceProcessor
			.builder(BrokenBlockPredicate.Builder.create().tag(ctx.blocks().getOrThrow(ConventionalBlockTags.ORES)).build())
			.addModifiedDrop(Ingredient.ofItems(Items.COAL), SpectrumItems.PURE_COAL)
			.addModifiedDrop(Ingredient.ofItems(Items.RAW_COPPER), SpectrumItems.PURE_COPPER)
			.addModifiedDrop(Ingredient.ofItems(Items.DIAMOND), SpectrumItems.PURE_DIAMOND)
			.addModifiedDrop(Ingredient.ofItems(Items.ECHO_SHARD), SpectrumItems.PURE_ECHO)
			.addModifiedDrop(Ingredient.ofItems(Items.EMERALD), SpectrumItems.PURE_EMERALD)
			.addModifiedDrop(Ingredient.ofItems(Items.GLOWSTONE_DUST), SpectrumItems.PURE_GLOWSTONE)
			.addModifiedDrop(Ingredient.ofItems(Items.RAW_GOLD), SpectrumItems.PURE_GOLD)
			.addModifiedDrop(Ingredient.ofItems(Items.RAW_IRON), SpectrumItems.PURE_IRON)
			.addModifiedDrop(Ingredient.ofItems(Items.LAPIS_LAZULI), SpectrumItems.PURE_LAPIS)
			.addModifiedDrop(Ingredient.ofItems(Items.PRISMARINE_CRYSTALS), SpectrumItems.PURE_PRISMARINE)
			.addModifiedDrop(Ingredient.ofItems(Items.QUARTZ), SpectrumItems.PURE_QUARTZ)
			.addModifiedDrop(Ingredient.ofItems(Items.REDSTONE), SpectrumItems.PURE_REDSTONE)
			.addModifiedDrop(Ingredient.ofItems(Items.ANCIENT_DEBRIS), SpectrumItems.PURE_NETHERITE_SCRAP)
			.addModifiedDrop(Ingredient.ofItems(Items.NETHERITE_SCRAP), SpectrumItems.PURE_NETHERITE_SCRAP)
			.build());
	
	public static final RegistryKey<ResonanceProcessor> BLACK_MATERIA = registerDropSelf("black_materia", SpectrumBlocks.BLACK_MATERIA, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> BRUSHABLE_BLOCKS = registerDropSelf("brushable_blocks", SpectrumBlockTags.C_BRUSHABLE_BLOCKS, builder -> builder
			.copyNbt("LootTable", "LootTableSeed", "item"));
	
	public static final RegistryKey<ResonanceProcessor> BUDDING_BLOCKS = registerDropSelf("budding_blocks", ConventionalBlockTags.BUDDING_BLOCKS, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> BUDS = registerDropSelf("buds", ConventionalBlockTags.BUDS, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> CAKE = registerDropSelf("cake", Blocks.CAKE, builder -> builder
			.copyState("bites"));
	
	public static final RegistryKey<ResonanceProcessor> CLUSTERS = registerDropSelf("clusters", ConventionalBlockTags.CLUSTERS, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> COMPOSTER = registerDropSelf("composter", Blocks.COMPOSTER, builder -> builder
			.copyState("level"));
	
	public static final RegistryKey<ResonanceProcessor> FROGSPAWN = registerDropSelf("frogspawn", Blocks.FROGSPAWN, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> GILDED_BLACKSTONE = registerDropSelf("gilded_blackstone", Blocks.GILDED_BLACKSTONE, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> INFESTED_BLOCKS = registerDropSelf("infested_blocks", SpectrumBlockTags.C_INFESTED_BLOCKS, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> REINFORCED_DEEPSLATE = registerDropSelf("reinforced_deepslate", Blocks.REINFORCED_DEEPSLATE, builder -> builder);
	
	public static final RegistryKey<ResonanceProcessor> RESPAWN_ANCHOR = registerDropSelf("respawn_anchor", Blocks.RESPAWN_ANCHOR, builder -> builder
			.copyState("charges"));
	
	public static final RegistryKey<ResonanceProcessor> SCULK_SHRIEKER = registerDropSelf("sculk_shrieker", Blocks.SCULK_SHRIEKER, builder -> builder
			.copyState("can_summon"));
	
	public static final RegistryKey<ResonanceProcessor> SIGNS = registerDropSelf("signs", BlockTags.ALL_SIGNS, builder -> builder
			.copyNbt("front_text", "back_text", "is_waxed"));
	
	public static final RegistryKey<ResonanceProcessor> SPAWNER = registerDropSelf("spawner", Blocks.SPAWNER, builder -> builder
			.copyNbt("SpawnData", "SpawnCount", "MinSpawnDelay", "MaxSpawnDelay", "SpawnRange", "RequiredPlayerRange", "SpawnPotentials", "MaxNearbyEntities"));
	
	public static RegistryKey<ResonanceProcessor> registerDropSelf(String id, Block block, UnaryOperator<DropSelfResonanceProcessor.Builder> builder) {
		return register(id, ctx -> builder.apply(DropSelfResonanceProcessor.builder(BrokenBlockPredicate.Builder.create().blocks(block).build())).build());
	}
	
	public static RegistryKey<ResonanceProcessor> registerDropSelf(String id, TagKey<Block> tag, UnaryOperator<DropSelfResonanceProcessor.Builder> builder) {
		return register(id, ctx -> builder.apply(DropSelfResonanceProcessor.builder(BrokenBlockPredicate.Builder.create().tag(ctx.blocks().getOrThrow(tag)).build())).build());
	}
	
	public static RegistryKey<ResonanceProcessor> register(String id, Function<BootstrapContext<ResonanceProcessor>, ResonanceProcessor> processor) {
		RegistryKey<ResonanceProcessor> key = RegistryKey.of(SpectrumRegistries.RESONANCE_PROCESSORS_KEY, SpectrumCommon.locate(id));
		if (IS_DATAGEN) {
			DEFERRER.defer(ctx -> ctx.registerable().register(key, processor.apply(ctx)));
		}
		return key;
	}
	
	public static void provideResonanceProcessors(BootstrapContext<ResonanceProcessor> ctx) {
		DEFERRER.flush(ctx);
	}
	
}
