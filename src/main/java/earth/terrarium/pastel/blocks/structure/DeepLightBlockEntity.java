package earth.terrarium.pastel.blocks.structure;

import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DeepLightBlockEntity extends BlockEntity {

    public DeepLightBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.DEEP_LIGHT.get(), pos, state);
    }
}
