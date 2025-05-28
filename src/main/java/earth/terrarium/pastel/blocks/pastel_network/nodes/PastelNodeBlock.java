package earth.terrarium.pastel.blocks.pastel_network.nodes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.block.ColorableBlock;
import earth.terrarium.pastel.blocks.decoration.SpectrumFacingBlock;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelNetwork;
import earth.terrarium.pastel.progression.SpectrumAdvancementCriteria;
import earth.terrarium.pastel.registries.SpectrumAdvancements;
import earth.terrarium.pastel.registries.SpectrumItemTags;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PastelNodeBlock extends SpectrumFacingBlock implements EntityBlock, ColorableBlock {

	public static final MapCodec<PastelNodeBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			propertiesCodec(),
			StringRepresentable.fromEnum(PastelNodeType::values).fieldOf("node_type").forGetter(b -> b.pastelNodeType)
	).apply(i, PastelNodeBlock::new));

	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty REDSTONE_EMITTING = BlockStateProperties.POWERED;
	
	public static final Map<Direction, VoxelShape> SHAPES = new HashMap<>() {{
		put(Direction.UP, Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D));
		put(Direction.DOWN, Block.box(5.0D, 12.0D, 5.0D, 11.0D, 16.0D, 11.0D));
		put(Direction.NORTH, Block.box(5.0D, 5.0D, 12.0D, 11.0D, 11.0D, 16.0D));
		put(Direction.SOUTH, Block.box(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 4.0D));
		put(Direction.EAST, Block.box(0.0D, 5.0D, 5.0D, 4.0D, 11.0D, 11.0D));
		put(Direction.WEST, Block.box(12.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
	}};
	
	protected final PastelNodeType pastelNodeType;
	
	public PastelNodeBlock(Properties settings, PastelNodeType pastelNodeType) {
		super(settings.lightLevel(s -> s.getValue(LIT) ? 13 : 0));
		this.pastelNodeType = pastelNodeType;
		registerDefaultState(defaultBlockState().setValue(LIT, false).setValue(REDSTONE_EMITTING, false));
	}

	@Override
	public MapCodec<? extends PastelNodeBlock> codec() {
		return CODEC;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return SpectrumCommon.CONFIG.MinimalNodes ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		Direction targetDirection = state.getValue(FACING).getOpposite();
		BlockPos targetPos = pos.relative(targetDirection);
		return world.getBlockState(targetPos).isSolid();
	}
	
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		if (!newState.is(state.getBlock())) {
			PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
			if (blockEntity != null) {
				blockEntity.onBroken();
				blockEntity.getOuterRing().ifPresent(r -> popResource(world, pos, r.upgradeItem.getDefaultInstance()));
				blockEntity.getInnerRing().ifPresent(r -> popResource(world, pos, r.upgradeItem.getDefaultInstance()));
				blockEntity.getRedstoneRing().ifPresent(r -> popResource(world, pos, r.upgradeItem.getDefaultInstance()));
			}
		}
		super.onRemove(state, world, pos, newState, moved);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Direction direction = ctx.getClickedFace();
		BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().relative(direction.getOpposite()));
		return blockState.is(this) && blockState.getValue(FACING) == direction ? this.defaultBlockState().setValue(FACING, direction.getOpposite()) : this.defaultBlockState().setValue(FACING, direction);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return ((w, p, s, b) -> PastelNodeBlockEntity.tick(w, p, s, (PastelNodeBlockEntity) b));
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.setPlacedBy(world, pos, state, placer, itemStack);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.addAll(this.pastelNodeType.getTooltips());
		tooltip.add(Component.translatable("block.pastel.pastel_network_nodes.tooltip.range", PastelNodeBlockEntity.RANGE).withStyle(ChatFormatting.GRAY));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT, REDSTONE_EMITTING);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : state;
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		@Nullable PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
		if (blockEntity == null) {
			return super.useItemOn(stack, state, world, pos, player, hand, hit);
		}
		
		if (player.isShiftKeyDown() && stack.isEmpty()) {
			if (AdvancementHelper.hasAdvancement(player, SpectrumAdvancements.PASTEL_NODE_UPGRADING)) {
				if (!world.isClientSide) {
					var removed = blockEntity.tryRemoveUpgrade();
					if (!removed.isEmpty()) {
						if (!player.getAbilities().instabuild) {
							player.getInventory().placeItemBackInInventory(removed);
						}
						blockEntity.updateUpgrades();
						blockEntity.setChanged();
						blockEntity.updateInClientWorld();
					}
				}
				return ItemInteractionResult.sidedSuccess(world.isClientSide());
			}
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		} else if (stack.is(SpectrumItems.TUNING_STAMP.get())) {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		} else if (player.isCreative() && stack.is(SpectrumItems.PAINTBRUSH.get())) {
			sendDebugMessage(world, pos, player, blockEntity);
			return ItemInteractionResult.sidedSuccess(world.isClientSide());
		} else if (AdvancementHelper.hasAdvancement(player, SpectrumAdvancements.PASTEL_NODE_UPGRADING) && stack.is(SpectrumItemTags.PASTEL_NODE_UPGRADES)) {
			if (!world.isClientSide() && blockEntity.tryInteractRings(stack, pastelNodeType)) {
				SpectrumAdvancementCriteria.PASTEL_NODE_UPGRADING.trigger((ServerPlayer) player, stack);
				if (!player.getAbilities().instabuild)
					stack.shrink(1);
				blockEntity.updateUpgrades();
				blockEntity.setChanged();
				blockEntity.updateInClientWorld();
			}
			
			world.playLocalSound(pos, SpectrumSoundEvents.MEDIUM_CRYSTAL_RING, SoundSource.BLOCKS, 0.25F, 0.9F + world.getRandom().nextFloat() * 0.2F, true);
			return ItemInteractionResult.sidedSuccess(world.isClientSide());
		} else if (this.pastelNodeType.usesFilters()) {
			if (!world.isClientSide) {
				player.openMenu(blockEntity);
			}
			return ItemInteractionResult.sidedSuccess(world.isClientSide());
		}
		
		return super.useItemOn(stack, state, world, pos, player, hand, hit);
	}
	
	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}
	
	@Override
	public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
		return state.getValue(REDSTONE_EMITTING) ? 15 : 0;
	}
	
	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		world.setBlockAndUpdate(pos, state.setValue(REDSTONE_EMITTING, false));
	}
	
	private static void sendDebugMessage(Level world, BlockPos pos, Player player, PastelNodeBlockEntity blockEntity) {
		if (blockEntity != null) {
			Optional<? extends PastelNetwork<?>> network = blockEntity.networkUUID.isPresent() ? Pastel.getInstance(world.isClientSide).getNetwork(blockEntity.networkUUID.get()) : Optional.empty();
			String prefix = world.isClientSide ? "C (" : "S (";
			Optional<DyeColor> color = blockEntity.getColor();
			String colorString = color.isEmpty() ? "<uncolored>" : color.get().toString();
			if (network.isEmpty()) {
				player.sendSystemMessage(Component.literal(prefix + colorString + "): No connected network :("));
			} else {
				player.sendSystemMessage(Component.literal(prefix + colorString + "): " + network.get().getNodeDebugText()));
			}
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPES.get(state.getValue(FACING));
	}
	
	public @Nullable PastelNodeBlockEntity getBlockEntity(LevelAccessor world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		if (blockEntity instanceof PastelNodeBlockEntity pastelNodeBlockEntity) {
			return pastelNodeBlockEntity;
		}
		return null;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PastelNodeBlockEntity(pos, state);
	}
	
	@Override
	public boolean color(Level world, BlockPos pos, Optional<DyeColor> color, @Nullable Entity user) {
		if (!(user instanceof Player player)) {
			return false;
		}
		if (!AdvancementHelper.hasAdvancement(player, SpectrumAdvancements.PASTEL_NODE_COLORING)) {
			return false;
		}
		@Nullable PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
		if (blockEntity == null) {
			return false;
		}
		return blockEntity.setColor(color, user);
	}
	
	@Override
	public Optional<DyeColor> getColor(Level world, BlockPos pos) {
		@Nullable PastelNodeBlockEntity blockEntity = getBlockEntity(world, pos);
		if (blockEntity == null) {
			return Optional.empty();
		}
		return blockEntity.getColor();
	}
	
}
