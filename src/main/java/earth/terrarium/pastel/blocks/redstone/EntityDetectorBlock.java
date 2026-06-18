package earth.terrarium.pastel.blocks.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.List;

public class EntityDetectorBlock extends DetectorBlock {

    public static final MapCodec<EntityDetectorBlock> CODEC = simpleCodec(EntityDetectorBlock::new);

    public EntityDetectorBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends EntityDetectorBlock> codec() {
        return CODEC;
    }

    @Override
    protected void updateState(BlockState state, Level world, BlockPos pos) {
        List<LivingEntity> entities = world
            .getEntities(
                EntityTypeTest.forClass(LivingEntity.class),
                getDetectionBox(pos),
                LivingEntity::isAlive
            );

        int power = Math.min(entities.size(), 15);

        power = state.getValue(INVERTED) ? 15 - power : power;
        if (state.getValue(POWER) != power) {
            world.setBlock(pos, state.setValue(POWER, power), 3);
        }
    }

    @Override
    int getUpdateFrequencyTicks() {
        return 20;
    }

}
