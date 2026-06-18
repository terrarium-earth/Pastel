package earth.terrarium.pastel.blocks;

import com.cmdpro.databank.model.animation.DatabankAnimationReference;
import com.cmdpro.databank.model.animation.DatabankAnimationState;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TeaTableBlockEntity extends BlockEntity {
    public DatabankAnimationState animState = new DatabankAnimationState("idle")
        .addAnim(
            new DatabankAnimationReference(
                "idle",
                (state, anim) -> {
                },
                (state, anim) -> {
                }
            )
        );

    public TeaTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(PastelBlockEntities.TEA_TABLE.get(), pos, blockState);
    }
}
