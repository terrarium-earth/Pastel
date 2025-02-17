package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import static de.dafuqs.spectrum.data.SpectrumDataGenerator.*;

public class SpectrumModelProvider extends FabricModelProvider {
	
	public static final DeferredRegistrar.Contextual<BlockStateModelGenerator> BLOCK_STATE_MODEL_REGISTRAR = new DeferredRegistrar.Contextual<>(IS_DATAGEN);
	
	public SpectrumModelProvider(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		BLOCK_STATE_MODEL_REGISTRAR.flush(blockStateModelGenerator);
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		SpectrumBlocks.provideItemModels(itemModelGenerator);
		SpectrumItems.provideItemModels(itemModelGenerator);
	}
	
	public static void registerSingletonBlockModel(Block block, TexturedModel.Factory factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerSingleton(block, factory));
	}
	
	public static void registerAxisRotatedBlockModel(Block block, TexturedModel.Factory factory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerAxisRotated(block, factory));
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
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
			VariantsBlockStateSupplier variants = VariantsBlockStateSupplier.create(block,
					BlockStateVariant.create().put(VariantSettings.MODEL, TexturedModel.CUBE_ALL.upload(block, ctx.modelCollector)),
					BlockStateVariant.create().put(VariantSettings.MODEL, TexturedModel.CUBE_MIRRORED_ALL.upload(block, ctx.modelCollector))
			);
			ctx.blockStateCollector.accept(variants);
		});
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
	
}