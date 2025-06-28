package de.dafuqs.revelationary.compat.jade;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class RevelationaryJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if (accessor instanceof BlockAccessor blockAccessor) {
                Player player = accessor.getPlayer();
                if (player.isCreative() || player.isSpectator()) {
                    return accessor;
                }

                if (blockAccessor.getBlock() instanceof RevelationAware aware) {
                    if (!aware.isVisibleTo(player)) {
                        BlockState cloakedState = aware.getBlockStateCloaks().get(blockAccessor.getBlockState());
                        return registration.blockAccessor().from(blockAccessor).blockState(cloakedState).build();
                    }
                }
            }

            return accessor;
        });
    }
}
