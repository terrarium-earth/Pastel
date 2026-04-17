package earth.terrarium.pastel.blocks.geology;

import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SnowgraveBlockEntity extends BlockEntity {
    public Mob cachedMob;

    public SnowgraveBlockEntity(BlockPos pos, BlockState blockState) {
        super(PastelBlockEntities.SNOWGRAVE.get(), pos, blockState);
    }
}
