package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.data.family.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

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
	
	// Block Models
	
	public static void registerSingletonBlockModel(Block block, TexturedModel.Factory factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerSingleton(block, factory));
	}
	
	public static void registerParentedBlockModel(Block block, Block parentBlock) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerParented(parentBlock, block));
	}
	
	public static void registerVariantsBlockModel(Function<BlockStateModelGenerator, BlockStateSupplier> factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.blockStateCollector.accept(factory.apply(ctx)));
	}
	
	public static void registerSimpleCubeAllBlockModel(Block block) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerSimpleCubeAll(block));
	}
	
	public static void registerAxisRotatedBlockModel(Block block, TexturedModel.Factory factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerAxisRotated(block, factory));
	}
	
	public static void registerMirroredAxisRotatedBlockModel(Block block, TexturedModel.Factory factory, TexturedModel.Factory mirroredFactory) {
		registerVariantsBlockModel(ctx -> createMirroredVariantsSupplier(ctx, block, factory, mirroredFactory).coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
	}
	
	public static void registerDefaultFacingUpBlockModel(Block block, TexturedModel.Factory factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
			BlockStateVariantMap variants = BlockStateVariantMap.create(FacingBlock.FACING)
					.register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180))
					.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
					.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
					.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180))
					.register(Direction.UP, BlockStateVariant.create())
					.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270));
			ctx.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, factory.upload(block, ctx.modelCollector)))
					.coordinate(variants));
		});
	}
	
	public static void registerSimpleMirroredBlockModel(Block block) {
		registerVariantsBlockModel(ctx -> createMirroredVariantsSupplier(ctx, block, TexturedModel.CUBE_ALL, TexturedModel.CUBE_MIRRORED_ALL));
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
	
	public static VariantsBlockStateSupplier createMirroredVariantsSupplier(BlockStateModelGenerator ctx, Block block, TexturedModel.Factory factory, TexturedModel.Factory mirroredFactory) {
		return VariantsBlockStateSupplier.create(block,
				BlockStateVariant.create().put(VariantSettings.MODEL, factory.upload(block, ctx.modelCollector)),
				BlockStateVariant.create().put(VariantSettings.MODEL, mirroredFactory.upload(block, ctx.modelCollector))
		);
	}
	
}