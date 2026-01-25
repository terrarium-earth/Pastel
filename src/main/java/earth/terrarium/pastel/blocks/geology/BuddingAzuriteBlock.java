package earth.terrarium.pastel.blocks.geology;

import earth.terrarium.pastel.api.block.WardDisruptableBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelBuddingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BuddingAzuriteBlock extends PastelBuddingBlock implements WardDisruptableBlock {

    public BuddingAzuriteBlock(
        Properties settings, Block smallBlock, Block mediumBlock, Block largeBlock, Block clusterBlock,
        SoundEvent hitSoundEvent, SoundEvent chimeSoundEvent
    ) {
        super(settings, smallBlock, mediumBlock, largeBlock, clusterBlock, hitSoundEvent, chimeSoundEvent);
    }

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
