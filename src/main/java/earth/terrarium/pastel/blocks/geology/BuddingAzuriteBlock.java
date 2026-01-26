package earth.terrarium.pastel.blocks.geology;

import earth.terrarium.pastel.api.block.WardDisruptableBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelBuddingBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class BuddingAzuriteBlock extends Block implements WardDisruptableBlock {

    public BuddingAzuriteBlock(
        Properties settings
    ) {
        super(settings);
    }

    // todo make it actually bud

    @Override
    public float defaultDestroyTime() {
        return -1;
    }

    // we do a little :STASIS:
    @Override
    public void onWardDisrupt(BlockPos pos, BlockState state, Level level, Entity trigger) {
        if(level.isClientSide())return;
        level.setBlockAndUpdate(pos, Blocks.DEEPSLATE.defaultBlockState());
    }
}
