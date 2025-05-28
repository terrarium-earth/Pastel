package earth.terrarium.pastel.blocks.spirit_instiller;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.modonomicon.client.render.MultiblockPreviewRenderer;
import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.blocks.InWorldInteractionBlock;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.progression.SpectrumAdvancementCriteria;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import earth.terrarium.pastel.registries.SpectrumMultiblocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpiritInstillerBlock extends InWorldInteractionBlock {

	public static final MapCodec<SpiritInstillerBlock> CODEC = simpleCodec(SpiritInstillerBlock::new);

	public SpiritInstillerBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends SpiritInstillerBlock> codec() {
		return CODEC;
	}
	
	public static void clearCurrentlyRenderedMultiBlock(Level world) {
		if (world.isClientSide) {
			ModonomiconHelper.clearRenderedMultiblock(SpectrumMultiblocks.get(SpectrumMultiblocks.SPIRIT_INSTILLER));
		}
	}
	
	public static boolean verifyStructure(Level world, @NotNull BlockPos blockPos, @Nullable ServerPlayer serverPlayerEntity, @NotNull SpiritInstillerBlockEntity instiller) {
		Multiblock multiblock = SpectrumMultiblocks.get(SpectrumMultiblocks.SPIRIT_INSTILLER);
		
		Rotation lastBlockRotation = instiller.getMultiblockRotation();
		boolean valid = false;
		
		// try all 4 rotations
		int offset = -4;
		Rotation checkRotation = lastBlockRotation;
		for (int i = 0; i < Rotation.values().length; i++) {
			valid = multiblock.validate(world, blockPos.below(1).relative(Support.directionFromRotation(checkRotation), offset), checkRotation);
			if (valid) {
				if (i != 0) {
					instiller.setMultiblockRotation(checkRotation);
				}
				break;
			} else {
				checkRotation = Rotation.values()[(checkRotation.ordinal() + 1) % Rotation.values().length];
			}
		}
		
		instiller.setValidStructure(valid);
		
		if (valid) {
			if (serverPlayerEntity != null) {
				SpectrumAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger(serverPlayerEntity, multiblock);
			}
		} else {
			if (world.isClientSide) {
				Multiblock currentMultiBlock = MultiblockPreviewRenderer.getMultiblock();
				if (currentMultiBlock == multiblock) {
					lastBlockRotation = Rotation.values()[(MultiblockPreviewRenderer.getFacingRotation().ordinal() + 1) % Rotation.values().length]; // cycle rotation
					instiller.setMultiblockRotation(lastBlockRotation);
				}
				ModonomiconHelper.renderMultiblock(SpectrumMultiblocks.get(SpectrumMultiblocks.SPIRIT_INSTILLER), SpectrumMultiblocks.SPIRIT_INSTILLER_TEXT, blockPos.below(2).relative(Support.directionFromRotation(lastBlockRotation), offset), lastBlockRotation);
			} else {
				scatterContents(world, blockPos);
			}
		}
		
		return valid;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SpiritInstillerBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, SpectrumBlockEntities.SPIRIT_INSTILLER.get(), world.isClientSide ? SpiritInstillerBlockEntity::clientTick : SpiritInstillerBlockEntity::serverTick);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.block();
	}
	
	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.isClientSide()) {
			clearCurrentlyRenderedMultiBlock((Level) world);
		}
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack handStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (world.isClientSide) {
			if (blockEntity instanceof SpiritInstillerBlockEntity spiritInstillerBlockEntity) {
				verifyStructure(world, pos, null, spiritInstillerBlockEntity);
			}
			return ItemInteractionResult.SUCCESS;
		} else {
			if (blockEntity instanceof SpiritInstillerBlockEntity spiritInstillerBlockEntity) {
				if (verifyStructure(world, pos, (ServerPlayer) player, spiritInstillerBlockEntity)) {
					if (exchangeStack(world, pos, player, hand, handStack, spiritInstillerBlockEntity)) {
						spiritInstillerBlockEntity.setOwner(player);
						spiritInstillerBlockEntity.inventoryChanged();
					}
				}
			}
			return ItemInteractionResult.CONSUME;
		}
	}
	
}
