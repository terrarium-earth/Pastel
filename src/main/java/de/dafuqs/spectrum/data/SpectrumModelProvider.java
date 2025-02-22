package de.dafuqs.spectrum.data;

import com.google.gson.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.data.family.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;

import java.util.function.*;

public class SpectrumModelProvider extends FabricModelProvider {
	
	public static final DeferredRegistrar.Contextual<ItemModelGenerator> ITEM_MODEL_REGISTRAR = new DeferredRegistrar.Contextual<>(DatagenProxy.IS_DATAGEN);
	public static final DeferredRegistrar.Contextual<BlockStateModelGenerator> BLOCK_STATE_MODEL_REGISTRAR = new DeferredRegistrar.Contextual<>(DatagenProxy.IS_DATAGEN);
	
	public SpectrumModelProvider(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		BLOCK_STATE_MODEL_REGISTRAR.flush(blockStateModelGenerator);
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		ITEM_MODEL_REGISTRAR.flush(itemModelGenerator);
	}
	
	public static void excludeFromSimpleItemModelGeneration(Block block) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.excludeFromSimpleItemModelGeneration(block));
	}
	
	// Item Models
	
	public static void registerCustomItemModel(Block block, Model model) {
		registerCustomItemModel(block, TextureMap::layer0, model);
	}
	
	public static void registerCustomItemModel(Block block, Function<Block, TextureMap> textureSupplier, Model model) {
		excludeFromSimpleItemModelGeneration(block);
		ITEM_MODEL_REGISTRAR.defer(ctx -> model.upload(ModelIds.getItemModelId(block.asItem()), textureSupplier.apply(block), ctx.writer));
	}
	
	public static void registerParentedItemModel(Block block, Block parent) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
			ctx.excludeFromSimpleItemModelGeneration(block);
			ctx.registerParentedItemModel(block, ModelIds.getBlockModelId(parent));
		});
	}
	
	// Block Models
	
	public static void registerSingletonBlockModel(Block block, TexturedModel.Factory factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerSingleton(block, factory));
	}
	
	public static void registerParentedBlockModel(Block block, Block parentBlock) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerParented(parentBlock, block));
	}
	
	public static void registerBlockModel(Function<BlockStateModelGenerator, BlockStateSupplier> factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.blockStateCollector.accept(factory.apply(ctx)));
	}
	
	public static void registerAxisRotatedBlockModel(Block block, TexturedModel.Factory factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerAxisRotated(block, factory));
	}
	
	public static void registerMirroredAxisRotatedBlockModel(Block block, TexturedModel.Factory factory, TexturedModel.Factory mirroredFactory) {
		registerBlockModel(ctx -> createMirroredVariantsSupplier(block, factory, mirroredFactory, ctx.modelCollector).coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
	}
	
	public static void registerDefaultFacingUpBlockModel(Block block, TexturedModel.Factory factory) {
		registerBlockModel(ctx -> VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, factory.upload(block, ctx.modelCollector))).coordinate(ctx.createUpDefaultFacingVariantMap()));
	}
	
	public static void registerSimpleMirroredBlockModel(Block block) {
		registerBlockModel(ctx -> createMirroredVariantsSupplier(block, TexturedModel.CUBE_ALL, TexturedModel.CUBE_MIRRORED_ALL, ctx.modelCollector));
	}
	
	public static void registerLogBlockModel(Block block) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerLog(block).log(block));
	}
	
	public static void registerWoodBlockModel(Block block, Block logBlock) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerLog(logBlock).wood(block));
	}
	
	public static void registerTintableCrossBlockModel(Block block, boolean tinted) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx ->
				ctx.registerTintableCrossBlockState(block, tinted ? BlockStateModelGenerator.TintType.TINTED : BlockStateModelGenerator.TintType.NOT_TINTED));
	}
	
	public static void registerPottedPlantBlockModel(FlowerPotBlock block, boolean tinted) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
			BlockStateModelGenerator.TintType tintType = tinted ? BlockStateModelGenerator.TintType.TINTED : BlockStateModelGenerator.TintType.NOT_TINTED;
			TextureMap textureMap = TextureMap.plant(block.getContent());
			Identifier identifier = tintType.getFlowerPotCrossModel().upload(block, textureMap, ctx.modelCollector);
			ctx.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		});
	}
	
	public static void registerGlassPaneBlockModel(Block glassPaneBlock, Block glassBlock) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
			TextureMap textureMap = TextureMap.paneAndTopForEdge(glassBlock, glassPaneBlock);
			Identifier post = Models.TEMPLATE_GLASS_PANE_POST.upload(glassPaneBlock, textureMap, ctx.modelCollector);
			Identifier side = Models.TEMPLATE_GLASS_PANE_SIDE.upload(glassPaneBlock, textureMap, ctx.modelCollector);
			Identifier sideAlt = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(glassPaneBlock, textureMap, ctx.modelCollector);
			Identifier noside = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(glassPaneBlock, textureMap, ctx.modelCollector);
			Identifier nosideAlt = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(glassPaneBlock, textureMap, ctx.modelCollector);
			Models.GENERATED.upload(ModelIds.getItemModelId(glassPaneBlock.asItem()), TextureMap.layer0(glassBlock), ctx.modelCollector);
			ctx.blockStateCollector.accept(MultipartBlockStateSupplier.create(glassPaneBlock)
					.with(BlockStateVariant.create().put(VariantSettings.MODEL, post))
					.with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, side))
					.with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R90))
					.with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt))
					.with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
					.with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, noside))
					.with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, nosideAlt))
					.with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, nosideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
					.with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, noside).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
		});
	}
	
	public static BlockFamily registerBlockFamilyBlockModels(BlockFamily family) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family));
		return family;
	}
	
	public static BlockFamily registerBlockFamilyBlockModelsExceptBase(BlockFamily family, TexturedModel.Factory variantFactory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
			TexturedModel texturedModel = variantFactory.get(family.getBaseBlock());
			BlockStateModelGenerator.BlockTexturePool texturePool = ctx.new BlockTexturePool(texturedModel.getTextures());
			texturePool.baseModelId = ModelIds.getBlockModelId(family.getBaseBlock());
			texturePool.family(family);
		});
		return family;
	}
	
	// Variant Suppliers
	
	public static VariantsBlockStateSupplier createMirroredVariantsSupplier(Block block, TexturedModel.Factory factory, TexturedModel.Factory mirroredFactory, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
		return VariantsBlockStateSupplier.create(block,
				BlockStateVariant.create().put(VariantSettings.MODEL, factory.upload(block, modelCollector)),
				BlockStateVariant.create().put(VariantSettings.MODEL, mirroredFactory.upload(block, modelCollector))
		);
	}
	
}