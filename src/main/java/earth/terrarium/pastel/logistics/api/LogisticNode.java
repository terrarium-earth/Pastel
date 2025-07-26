package earth.terrarium.pastel.logistics.api;

import net.minecraft.core.BlockPos;

import java.util.UUID;

public interface LogisticNode<P extends Payload<?, ?>> {

    BlockPos getPos();

    UUID getNetwork();

    UUID getInitialId();

    int getInitialColor();

    int transferCap();

    int segmentLatency();

    float transfersPerSecond();

    P payloadType();
}
