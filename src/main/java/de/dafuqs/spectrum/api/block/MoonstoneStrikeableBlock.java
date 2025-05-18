package de.dafuqs.spectrum.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface MoonstoneStrikeableBlock {
    
    void onMoonstoneStrike(Level world, BlockPos pos, @Nullable LivingEntity striker);
    
}
