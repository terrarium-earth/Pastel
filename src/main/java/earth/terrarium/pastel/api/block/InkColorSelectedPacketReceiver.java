package earth.terrarium.pastel.api.block;

import earth.terrarium.pastel.api.energy.color.InkColor;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public interface InkColorSelectedPacketReceiver {

    void onInkColorSelectedPacket(Optional<Holder<InkColor>> inkColor);

    BlockEntity getBlockEntity();

}
