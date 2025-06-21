package earth.terrarium.pastel.blocks;

import com.google.common.base.Predicates;
import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrackedEndPortalFrameBlock extends Block {

	public static final MapCodec<CrackedEndPortalFrameBlock> CODEC = simpleCodec(CrackedEndPortalFrameBlock::new);

	public static final BooleanProperty FACING_VERTICAL;
	public static final EnumProperty<EndPortalFrameEye> EYE_TYPE;
	protected static final VoxelShape FRAME_SHAPE;
	protected static final VoxelShape EYE_SHAPE;
	protected static final VoxelShape FRAME_WITH_EYE_SHAPE;
	private static BlockPattern COMPLETED_FRAME;
	private static BlockPattern END_PORTAL;

	static {
		FACING_VERTICAL = BooleanProperty.create("facing_vertical");
		EYE_TYPE = EnumProperty.create("eye_type", CrackedEndPortalFrameBlock.EndPortalFrameEye.class);
		FRAME_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
		EYE_SHAPE = Block.box(4.0D, 13.0D, 4.0D, 12.0D, 16.0D, 12.0D);
		FRAME_WITH_EYE_SHAPE = Shapes.or(FRAME_SHAPE, EYE_SHAPE);
	}

	@Override
	public MapCodec<? extends CrackedEndPortalFrameBlock> codec() {
		return CODEC;
	}
	
	public CrackedEndPortalFrameBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING_VERTICAL, false).setValue(EYE_TYPE, EndPortalFrameEye.NONE));
	}

	public static void checkAndFillEndPortal(Level world, BlockPos blockPos) {
		BlockPattern.BlockPatternMatch result = CrackedEndPortalFrameBlock.getCompletedFramePattern().find(world, blockPos);
		if (result != null) {
			// since the custom portal does not have
			// fixed directions we can estimate the
			// portal position based on some simple checks instead
			BlockPos portalTopLeft = result.getFrontTopLeft().offset(-3, 0, -3);
			if (world.getBlockState(portalTopLeft.offset(7, 0, 0)).getBlock().equals(PastelBlocks.CRACKED_END_PORTAL_FRAME.get())) {
				portalTopLeft = portalTopLeft.offset(4, 0, 0);
			} else if (world.getBlockState(portalTopLeft.offset(0, 0, 7)).getBlock().equals(PastelBlocks.CRACKED_END_PORTAL_FRAME.get())) {
				portalTopLeft = portalTopLeft.offset(0, 0, 4);
			}
			
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					world.setBlock(portalTopLeft.offset(i, 0, j), Blocks.END_PORTAL.defaultBlockState(), 2);
				}
			}
			
			world.globalLevelEvent(LevelEvent.SOUND_END_PORTAL_SPAWN, portalTopLeft.offset(1, 0, 1), 0);
		}
	}
	
	public static void destroyPortals(Level world, BlockPos blockPos) {
		BlockPattern.BlockPatternMatch result = CrackedEndPortalFrameBlock.getActiveEndPortalPattern().find(world, blockPos);
		if (result != null) {
			// since the custom portal does not have
			// fixed directions we can estimate the
			// portal position based on some simple checks instead
			BlockPos portalTopLeft = result.getFrontTopLeft().offset(-3, 0, -3);
			Block b1 = world.getBlockState(portalTopLeft.offset(7, 0, 0)).getBlock();
			Block b2 = world.getBlockState(portalTopLeft.offset(0, 0, 7)).getBlock();
			if (b1.equals(PastelBlocks.CRACKED_END_PORTAL_FRAME.get()) || b1.equals(Blocks.END_PORTAL_FRAME)) {
				portalTopLeft = portalTopLeft.offset(4, 0, 0);
			} else if (b2.equals(PastelBlocks.CRACKED_END_PORTAL_FRAME.get()) || b2.equals(Blocks.END_PORTAL_FRAME)) {
				portalTopLeft = portalTopLeft.offset(0, 0, 4);
			}
			
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					world.setBlock(portalTopLeft.offset(i, 0, j), Blocks.AIR.defaultBlockState(), 2);
				}
			}
			
			world.globalLevelEvent(LevelEvent.SOUND_END_PORTAL_SPAWN, portalTopLeft.offset(1, 0, 1), 0);
		}
	}
	
	public static BlockPattern getCompletedFramePattern() {
		if (COMPLETED_FRAME == null) {
			COMPLETED_FRAME = BlockPatternBuilder.start()
					.aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
					.where('?', BlockInWorld.hasState(BlockStatePredicate.ANY))
					.where('^', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.where(EndPortalFrameBlock.HAS_EYE, Predicates.equalTo(true))
									.where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.SOUTH))
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get())
											.where(EYE_TYPE, Predicates.equalTo(EndPortalFrameEye.WITH_EYE_OF_ENDER))
											.where(FACING_VERTICAL, Predicates.equalTo(false)))))
					.where('>', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.where(EndPortalFrameBlock.HAS_EYE, Predicates.equalTo(true))
									.where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.WEST))
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get())
											.where(EYE_TYPE, Predicates.equalTo(EndPortalFrameEye.WITH_EYE_OF_ENDER))
											.where(FACING_VERTICAL, Predicates.equalTo(true)))))
					.where('v', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.where(EndPortalFrameBlock.HAS_EYE, Predicates.equalTo(true))
									.where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.NORTH))
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get())
											.where(EYE_TYPE, Predicates.equalTo(EndPortalFrameEye.WITH_EYE_OF_ENDER))
											.where(FACING_VERTICAL, Predicates.equalTo(false)))))
					.where('<', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.where(EndPortalFrameBlock.HAS_EYE, Predicates.equalTo(true))
									.where(EndPortalFrameBlock.FACING, Predicates.equalTo(Direction.EAST))
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get())
											.where(EYE_TYPE, Predicates.equalTo(EndPortalFrameEye.WITH_EYE_OF_ENDER))
											.where(FACING_VERTICAL, Predicates.equalTo(true)))))
					.build();
		}
		return COMPLETED_FRAME;
	}
	
	public static BlockPattern getActiveEndPortalPattern() {
		if (END_PORTAL == null) {
			END_PORTAL = BlockPatternBuilder.start()
					.aisle("?vvv?", ">ppp<", ">ppp<", ">ppp<", "?^^^?")
					.where('?', BlockInWorld.hasState(BlockStatePredicate.ANY))
					.where('^', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get()))))
					.where('>', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get()))))
					.where('v', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get()))))
					.where('<', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL_FRAME)
									.or(BlockStatePredicate.forBlock(PastelBlocks.CRACKED_END_PORTAL_FRAME.get()))))
					.where('p', BlockInWorld.hasState(
							BlockStatePredicate.forBlock(Blocks.END_PORTAL)))
					.build();
		}
		return END_PORTAL;
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return state.getValue(EYE_TYPE).hasEye() ? FRAME_WITH_EYE_SHAPE : FRAME_SHAPE;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Direction facing = ctx.getHorizontalDirection();
		boolean facingVertical = facing.equals(Direction.EAST) || facing.equals(Direction.WEST);
		return (this.defaultBlockState().setValue(FACING_VERTICAL, facingVertical).setValue(EYE_TYPE, EndPortalFrameEye.NONE));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING_VERTICAL, !state.getValue(FACING_VERTICAL));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING_VERTICAL, EYE_TYPE);
	}
	
	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		return state.getValue(EYE_TYPE).getRedstonePower();
	}
	
	@Override
	@Deprecated
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		// when placed via perturbed eye => fuse
		if (state.getValue(EYE_TYPE).hasExplosions()) {
			world.scheduleTick(pos, this, 40);
		}
	}
	
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		if (state.getValue(EYE_TYPE).hasExplosions()) {
			double d = (double) pos.getX() + random.nextDouble();
			double e = (double) pos.getY() + 1.05D;
			double f = (double) pos.getZ() + random.nextDouble();
			world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	@Deprecated
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (state.getValue(EYE_TYPE).hasExplosions()) {
			// 10% chance to break portal
			float randomFloat = random.nextFloat();
			if (randomFloat < 0.05) {
				world.explode(null, null, new ExplosionDamageCalculator() {
					@Override
					public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
						return false;
					}
				}, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4, false, Level.ExplosionInteraction.BLOCK);
				destroyPortals(world, pos);
				world.destroyBlock(pos, true);
			} else if (randomFloat < 0.2) {
				world.explode(null, null, new ExplosionDamageCalculator() {
					@Override
					public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
						return false;
					}
				}, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, false, Level.ExplosionInteraction.BLOCK);
			} else {
				double d = (double) pos.getX() + random.nextDouble();
				double e = (double) pos.getY() + 0.8D;
				double f = (double) pos.getZ() + random.nextDouble();
				world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
			}
		}
		world.scheduleTick(pos, this, 10);
	}

	public enum EndPortalFrameEye implements StringRepresentable {
		VANILLA_WITH_PERTURBED_EYE("vanilla_cracker", true, true, 8),
		NONE("none", false, false, 0),
		WITH_EYE_OF_ENDER("ender", true, false, 15),
		WITH_PERTURBED_EYE("cracker", true, true, 8);

		private final String name;
		private final boolean hasEye;
		private final boolean hasExplosions; // TIL `volatile` is a keyword in java
		private final int redstonePower;

		EndPortalFrameEye(String name, boolean hasEye, boolean hasExplosions, int redstonePower) {
			this.name = name;
			this.hasEye = hasEye;
			this.redstonePower = redstonePower;
			this.hasExplosions = hasExplosions;
		}

		public String toString() {
			return this.name;
		}
		
		@Override
		public String getSerializedName() {
			return this.name;
		}

		public boolean hasEye() {
			return hasEye;
		}

		public boolean hasExplosions() {
			return this.hasExplosions;
		}

		public int getRedstonePower() {
			return this.redstonePower;
		}

	}
	
}
