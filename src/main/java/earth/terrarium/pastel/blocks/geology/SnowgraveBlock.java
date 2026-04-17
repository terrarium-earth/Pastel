package earth.terrarium.pastel.blocks.geology;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class SnowgraveBlock extends Block implements EntityBlock {
    public static final EnumProperty<FrozenMob> FROZEN_MOB = EnumProperty.create("frozen_mob", FrozenMob.class);
    public static final BooleanProperty SHOULD_RENDER = BooleanProperty.create("should_render");

    public SnowgraveBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                                            .setValue(SHOULD_RENDER, true)
                                            .setValue(FROZEN_MOB, FrozenMob.SKELETON));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FROZEN_MOB, SHOULD_RENDER);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SnowgraveBlockEntity(pos, state);
    }

    public enum FrozenMob implements StringRepresentable {
        ZOMBIE("zombie"),
        SKELETON("skeleton"),
        SPIDER("spider");

        private final String name;

        FrozenMob(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        public static Codec<FrozenMob> CODEC = StringRepresentable.fromEnum(FrozenMob::values);
    }

    // we have to do this twice because otherwise we will miss one corner of the spider variant
    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        for (Direction dir1 : Direction.values()) {
            var newPos = pos.relative(dir1);
            for (Direction dir2 : Direction.values()) {
                if (level.getBlockState(newPos.relative(dir2))
                         .is(PastelBlocks.SNOWGRAVE)) {
                    level.setBlock(newPos.relative(dir2), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }
    }
}
