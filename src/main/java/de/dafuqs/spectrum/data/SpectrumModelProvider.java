package de.dafuqs.spectrum.data;

import com.google.gson.*;
import de.dafuqs.spectrum.blocks.decoration.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.data.client.*;
import net.minecraft.data.family.*;
import net.minecraft.item.*;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;
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
	
	// Item Models
	
	public static void registerItemModel(ItemModelGenerator ctx, Item item) {
		registerItemModel(ctx, item, "");
	}
	
	public static void registerItemModel(ItemModelGenerator ctx, Item item, String suffix) {
		Models.GENERATED.upload(ModelIds.getItemModelId(item), SpectrumTextureMaps.layer0(item, suffix), ctx.writer);
	}
	
	public static void registerBlockTexturedItemModel(ItemModelGenerator ctx, Block block) {
		registerBlockTexturedItemModel(ctx, block, "");
	}
	
	public static void registerBlockTexturedItemModel(ItemModelGenerator ctx, Block block, String suffix) {
		Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), SpectrumTextureMaps.layer0(block, suffix), ctx.writer);
	}
	
	// Block Models
	
	public static BlockStateSupplier simpleMirroredBlockModel(BlockStateModelGenerator ctx, Block block) {
		return createMirroredVariantsSupplier(block, TexturedModel.CUBE_ALL, TexturedModel.CUBE_MIRRORED_ALL, ctx.modelCollector);
	}
	
	public static BlockStateSupplier logBlockModel(BlockStateModelGenerator ctx, Block logBlock) {
		TextureMap textureMap = SpectrumTextureMaps.sideEnd(logBlock, "", logBlock, "_top");
		Identifier vertical = Models.CUBE_COLUMN.upload(logBlock, textureMap, ctx.modelCollector);
		Identifier horizonal = Models.CUBE_COLUMN_HORIZONTAL.upload(logBlock, textureMap, ctx.modelCollector);
		return VariantsBlockStateSupplier.create(logBlock).coordinate(createAxisRotatedVariantMap(vertical, horizonal));
	}
	
	public static BlockStateSupplier woodBlockModel(BlockStateModelGenerator ctx, Block woodBlock, Block logBlock) {
		TextureMap textureMap = SpectrumTextureMaps.sideEnd(logBlock, "", logBlock, "");
		Identifier model = Models.CUBE_COLUMN.upload(woodBlock, textureMap, ctx.modelCollector);
		return VariantsBlockStateSupplier.create(woodBlock, createModelVariant(model)).coordinate(createAxisRotatedVariantMap());
	}
	
	public static BlockStateSupplier tintableCrossBlockModel(BlockStateModelGenerator ctx, Block block, boolean tinted) {
		return createVariantsSupplier(block, SpectrumTexturedModels.tintableCross(b -> b, "", tinted).upload(block, ctx.modelCollector));
	}
	
	public static BlockStateSupplier pottedPlantBlockModel(BlockStateModelGenerator ctx, FlowerPotBlock block, boolean tinted) {
		BlockStateModelGenerator.TintType tintType = tinted ? BlockStateModelGenerator.TintType.TINTED : BlockStateModelGenerator.TintType.NOT_TINTED;
		TextureMap textureMap = TextureMap.plant(block.getContent());
		Identifier identifier = tintType.getFlowerPotCrossModel().upload(block, textureMap, ctx.modelCollector);
		return BlockStateModelGenerator.createSingletonBlockState(block, identifier);
	}
	
	public static BlockStateSupplier glassPaneBlockModel(BlockStateModelGenerator ctx, Block glassPaneBlock, Block glassBlock) {
		TextureMap textureMap = TextureMap.paneAndTopForEdge(glassBlock, glassPaneBlock);
		Identifier post = Models.TEMPLATE_GLASS_PANE_POST.upload(glassPaneBlock, textureMap, ctx.modelCollector);
		Identifier side = Models.TEMPLATE_GLASS_PANE_SIDE.upload(glassPaneBlock, textureMap, ctx.modelCollector);
		Identifier sideAlt = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(glassPaneBlock, textureMap, ctx.modelCollector);
		Identifier noside = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(glassPaneBlock, textureMap, ctx.modelCollector);
		Identifier nosideAlt = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(glassPaneBlock, textureMap, ctx.modelCollector);
		Models.GENERATED.upload(ModelIds.getItemModelId(glassPaneBlock.asItem()), TextureMap.layer0(glassBlock), ctx.modelCollector);
		return MultipartBlockStateSupplier.create(glassPaneBlock)
				.with(BlockStateVariant.create().put(VariantSettings.MODEL, post))
				.with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, side))
				.with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt))
				.with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, noside))
				.with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, nosideAlt))
				.with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, nosideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, noside).put(VariantSettings.Y, VariantSettings.Rotation.R270));
	}
	
	public static BlockFamily registerBlockFamily(BlockFamily family) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> ctx.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family));
		return family;
	}
	
	public static BlockFamily registerBlockFamilyExceptBase(BlockFamily family, TexturedModel.Factory variantFactory) {
		BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
			TexturedModel texturedModel = variantFactory.get(family.getBaseBlock());
			BlockStateModelGenerator.BlockTexturePool texturePool = ctx.new BlockTexturePool(texturedModel.getTextures());
			texturePool.baseModelId = ModelIds.getBlockModelId(family.getBaseBlock());
			texturePool.family(family);
		});
		return family;
	}
	
	// Variant Suppliers
	
	public static VariantsBlockStateSupplier createVariantsSupplier(Block block, Identifier... modelIds) {
		return VariantsBlockStateSupplier.create(block, Arrays.stream(modelIds).map(modelId -> BlockStateVariant.create().put(VariantSettings.MODEL, modelId)).toArray(BlockStateVariant[]::new));
	}
	
	public static VariantsBlockStateSupplier createVariantsSupplier(BlockStateModelGenerator ctx, Block block, TexturedModel.Factory factory) {
		return createVariantsSupplier(block, factory.upload(block, ctx.modelCollector));
	}
	
	public static VariantsBlockStateSupplier createMirroredVariantsSupplier(Block block, TexturedModel.Factory factory, TexturedModel.Factory mirroredFactory, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
		return VariantsBlockStateSupplier.create(block,
				createModelVariant(factory.upload(block, modelCollector)),
				createModelVariant(mirroredFactory.upload(block, modelCollector))
		);
	}
	
	// Variant Lists
	
	public static List<BlockStateVariant> createHorizontalRotationVariantList(Identifier modelId) {
		return List.of(
				createModelVariant(modelId),
				createModelVariant(modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90),
				createModelVariant(modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180),
				createModelVariant(modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270)
		);
	}
	
	// Variant Maps
	
	public static BlockStateVariantMap createBooleanModelMap(BooleanProperty property, Identifier trueModel, Identifier falseModel) {
		return BlockStateVariantMap.create(property)
				.register(false, createModelVariant(falseModel))
				.register(true, createModelVariant(trueModel));
	}
	
	public static BlockStateVariantMap createCardinalFacingVariantMap() {
		return BlockStateVariantMap.create(CardinalFacingBlock.CARDINAL_FACING)
				.register(false, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(true, BlockStateVariant.create());
	}
	
	public static BlockStateVariantMap createAxisRotatedVariantMap() {
		return BlockStateVariantMap.create(Properties.AXIS)
				.register(Direction.Axis.X, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Direction.Axis.Y, BlockStateVariant.create())
				.register(Direction.Axis.Z, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90));
	}
	
	public static BlockStateVariantMap createAxisRotatedVariantMap(Identifier verticalModelId, Identifier horizontalModelId) {
		return BlockStateVariantMap.create(Properties.AXIS)
				.register(Direction.Axis.X, createModelVariant(horizontalModelId).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Direction.Axis.Y, createModelVariant(verticalModelId))
				.register(Direction.Axis.Z, createModelVariant(horizontalModelId).put(VariantSettings.X, VariantSettings.Rotation.R90));
	}
	
	public static BlockStateVariantMap createUpDefaultFacingVariantMap() {
		return BlockStateVariantMap.create(Properties.FACING)
				.register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180))
				.register(Direction.UP, BlockStateVariant.create())
				.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
				.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90));
	}
	
	public static BlockStateVariantMap createDownDefaultFacingVariantMap(Identifier horizontalModelId, Identifier verticalModelId) {
		return BlockStateVariantMap.create(FacingBlock.FACING)
				.register(Direction.DOWN, createModelVariant(verticalModelId))
				.register(Direction.UP, createModelVariant(verticalModelId).put(VariantSettings.X, VariantSettings.Rotation.R180))
				.register(Direction.NORTH, createModelVariant(horizontalModelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Direction.SOUTH, createModelVariant(horizontalModelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Direction.WEST, createModelVariant(horizontalModelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Direction.EAST, createModelVariant(horizontalModelId));
	}
	
	public static BlockStateVariantMap createNorthDefaultRotationStates() {
		return BlockStateVariantMap.create(Properties.FACING)
				.register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
				.register(Direction.UP, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R270))
				.register(Direction.NORTH, BlockStateVariant.create())
				.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90));
	}
	
	public static BlockStateVariantMap createNorthDefaultHorizontalRotationStates() {
		return BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
				.register(Direction.NORTH, BlockStateVariant.create())
				.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90));
	}
	
	public static BlockStateVariantMap createSouthDefaultHorizontalRotationStates() {
		return BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
				.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Direction.SOUTH, BlockStateVariant.create())
				.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270));
	}
	
	public static BlockStateVariantMap createWestDefaultHorizontalFacingVariantMap() {
		return BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
				.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Direction.WEST, BlockStateVariant.create())
				.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180));
	}
	
	public static BlockStateVariantMap createEastDefaultHorizontalFacingVariantMap() {
		return BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
				.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Direction.EAST, BlockStateVariant.create());
	}
	
	public static BlockStateVariantMap createUpNorthDefaultOrientationVariantMap() {
		return BlockStateVariantMap.create(Properties.ORIENTATION)
				.register(Orientation.DOWN_NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
				.register(Orientation.DOWN_SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Orientation.DOWN_WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Orientation.DOWN_EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Orientation.UP_NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Orientation.UP_SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R270))
				.register(Orientation.UP_WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.register(Orientation.UP_EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Orientation.NORTH_UP, BlockStateVariant.create())
				.register(Orientation.SOUTH_UP, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.register(Orientation.WEST_UP, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.register(Orientation.EAST_UP, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90));
	}
	
	// Variants
	
	public static BlockStateVariant createModelVariant(Identifier modelId) {
		return BlockStateVariant.create().put(VariantSettings.MODEL, modelId);
	}
	
	public static BlockStateVariant createModelVariant(Block block, String suffix) {
		return createModelVariant(ModelIds.getBlockSubModelId(block, suffix));
	}
	
	public static BlockStateVariant applyUpDefaultRotation(BlockStateVariant variant, Direction direction) {
		return switch (direction) {
			case Direction.DOWN -> variant.put(VariantSettings.X, VariantSettings.Rotation.R180);
			case Direction.NORTH -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90);
			case Direction.SOUTH -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180);
			case Direction.WEST -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270);
			case Direction.EAST -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90);
			default -> variant;
		};
	}
	
	public static VariantSettings.Rotation getSouthDefaultRotation(Direction direction) {
		return switch (direction) {
			case Direction.WEST -> VariantSettings.Rotation.R90;
			case Direction.NORTH -> VariantSettings.Rotation.R180;
			case Direction.EAST -> VariantSettings.Rotation.R270;
			default -> VariantSettings.Rotation.R0;
		};
	}
	
}