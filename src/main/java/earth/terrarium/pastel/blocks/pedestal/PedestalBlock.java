package earth.terrarium.pastel.blocks.pedestal;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.block.PaintbrushTriggered;
import earth.terrarium.pastel.api.block.PedestalVariant;
import earth.terrarium.pastel.api.block.RedstonePoweredBlock;
import earth.terrarium.pastel.blocks.InWorldInteractionBlock;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconHelper;
import earth.terrarium.pastel.networking.s2c_payloads.PlayPedestalStartCraftingParticlePayload;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelMultiblocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Optional;

public class PedestalBlock extends BaseEntityBlock implements RedstonePoweredBlock, PaintbrushTriggered {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("place_pedestal");
	private static final VoxelShape SHAPE;
	private final PedestalVariant variant;
	
	public PedestalBlock(Properties settings, PedestalVariant variant) {
		super(settings);
		this.variant = variant;
		registerDefaultState(getStateDefinition().any().setValue(BlockStateProperties.POWERED, false));
	}

	@Override
	public MapCodec<? extends PedestalBlock> codec() {
		return null;
	}

	public static void upgradeTo(@NotNull Level world, BlockPos blockPos, BlockState old, PedestalVariant upgrade) {
		world.setBlockAndUpdate(blockPos, upgrade.getPedestalBlock().defaultBlockState()
				.setValue(BlockStateProperties.POWERED, old.getValue(BlockStateProperties.POWERED)));
	}
	
	public static void clearCurrentlyRenderedMultiBlock(Level world) {
		if (world.isClientSide) {
			ModonomiconHelper.clearRenderedMultiblock(PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_SIMPLE));
			ModonomiconHelper.clearRenderedMultiblock(PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_ADVANCED));
			ModonomiconHelper.clearRenderedMultiblock(PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_COMPLEX));
			ModonomiconHelper.clearRenderedMultiblock(PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_COMPLEX_WITHOUT_MOONSTONE));
		}
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof ServerPlayer) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
				pedestalBlockEntity.setOwner((ServerPlayer) placer);
				blockEntity.setChanged();
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(BlockStateProperties.POWERED);
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack handStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemInteractionResult actionResult = checkAndDoPaintbrushTrigger(state, level, pos, player, hand, hit);
		if (actionResult.consumesAction()) {
			return actionResult;
		}
		
		if (level.isClientSide) {
			return ItemInteractionResult.SUCCESS;
		} else {
			level.getBlockEntity(pos, PastelBlockEntities.PEDESTAL.get())
					.ifPresent(p -> p.giveStoredXp(player));
			this.openScreen(level, pos, player);
			return ItemInteractionResult.CONSUME;
		}
	}
	
	protected void openScreen(Level world, BlockPos pos, Player player) {
		Optional<PedestalBlockEntity> blockEntity = world.getBlockEntity(pos, PastelBlockEntities.PEDESTAL.get());
		if (blockEntity.isPresent()) {
			PedestalBlockEntity pedestalBlockEntity = blockEntity.get();
			pedestalBlockEntity.setOwner(player);
			player.openMenu(pedestalBlockEntity);
		}
	}
	
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		if (newState.getBlock() instanceof PedestalBlock newPed) {
			if (!state.is(newPed)) {
				// pedestal is getting upgraded. Keep the blockEntity with its contents
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity instanceof PedestalBlockEntity pedestal) {
					pedestal.refreshVariant();
				}
			}
		} else {
			InWorldInteractionBlock.scatterContents(world, pos);
			super.onRemove(state, world, pos, newState, moved);
		}
	}
	
	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PedestalBlockEntity(pos, state);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, @NotNull Level world, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, PastelBlockEntities.PEDESTAL.get(), world.isClientSide ? PedestalBlockEntity::clientTick : PedestalBlockEntity::serverTick);
	}
	
	@Override
	public void neighborChanged(BlockState state, @NotNull Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!world.isClientSide) {
			if (this.checkGettingPowered(world, pos)) {
				this.power(world, pos);
			} else {
				this.unPower(world, pos);
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(@NotNull BlockState state, Level world, BlockPos pos, RandomSource random) {
		if (state.getValue(BlockStateProperties.POWERED)) {
			Vector3f color = new Vector3f(0.6F, 0.33F, 0.1F);
			float xOffset = random.nextFloat();
			float zOffset = random.nextFloat();
			world.addParticle(new DustParticleOptions(color, 1.0F), pos.getX() + xOffset, pos.getY() + 1, pos.getZ() + zOffset, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.isClientSide()) {
			clearCurrentlyRenderedMultiBlock((Level) world);
		}
	}
	
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		BlockState placementState = this.defaultBlockState();
		
		if (ctx.getLevel().getBestNeighborSignal(ctx.getClickedPos()) > 0) {
			placementState = placementState.setValue(BlockStateProperties.POWERED, true);
		}
		
		return placementState;
	}
	
	public PedestalVariant getVariant() {
		return this.variant;
	}
	
	static {
		var foot = Block.box(3, 0, 3, 13, 3, 13);
		var neck = Block.box(5, 3, 5, 11, 12, 11);
		var head = Block.box(0, 12, 0, 16, 16, 16);
		foot = Shapes.or(foot, neck);
		SHAPE = Shapes.or(foot, head);
	}
	
	@Override
	public ItemInteractionResult onPaintBrushTrigger(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		var be = level.getBlockEntity(pos, PastelBlockEntities.PEDESTAL.get());
		if (be.isEmpty())
			return ItemInteractionResult.FAIL;

		var pedestal = be.get();

		if (pedestal.active || pedestal.craftingTime <= 0) {
			return ItemInteractionResult.FAIL;
		}

		if (!level.isClientSide) {
			pedestal.setActive(true);
			PlayPedestalStartCraftingParticlePayload.spawnPedestalStartCraftingParticles(pedestal);
		}

		return ItemInteractionResult.sidedSuccess(level.isClientSide);
	}
	
}
