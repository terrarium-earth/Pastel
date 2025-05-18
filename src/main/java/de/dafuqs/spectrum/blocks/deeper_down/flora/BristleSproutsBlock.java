package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.SpectrumConfiguredFeatures;
import de.dafuqs.spectrum.registries.SpectrumDamageTypes;
import de.dafuqs.spectrum.registries.SpectrumEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BristleSproutsBlock extends BushBlock implements BonemealableBlock {

	public static final MapCodec<BristleSproutsBlock> CODEC = simpleCodec(BristleSproutsBlock::new);

	public static final float DAMAGE = 2.0F;

	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0);

	public BristleSproutsBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends BristleSproutsBlock> codec() {
		return CODEC;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 vec3d = state.getOffset(world, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && !entity.getType().is(SpectrumEntityTypeTags.POKING_DAMAGE_IMMUNE)) {
			if (!world.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double difX = Math.abs(entity.getX() - entity.xOld);
				double difZ = Math.abs(entity.getZ() - entity.zOld);
				if (difX >= 0.003 || difZ >= 0.003) {
					entity.hurt(SpectrumDamageTypes.bristeSprouts(world), DAMAGE);
				}
			}
		}
    }

	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return true;
	}

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		world.registryAccess()
				.registryOrThrow(Registries.CONFIGURED_FEATURE)
				.get(SpectrumConfiguredFeatures.BRISTLE_SPROUT_PATCH)
				.place(world, world.getChunkSource().getGenerator(), random, pos);
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.265F;
    }
}
