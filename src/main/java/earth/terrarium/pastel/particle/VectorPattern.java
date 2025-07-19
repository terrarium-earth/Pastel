package earth.terrarium.pastel.particle;

import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public enum VectorPattern {

    FOUR(List.of(
        new Vec3(1.0D, 0, 0.0D),
        new Vec3(0.0D, 0, 1.0D),
        new Vec3(-1.0, 0, 0.0D),
        new Vec3(0.0D, 0, -1.0D)
    )),
    EIGHT(List.of(
        new Vec3(1.0D, 0, 0.0D),
        new Vec3(0.7D, 0, 0.7D),
        new Vec3(0.0D, 0, 1.0D),
        new Vec3(-0.7D, 0, 0.7D),
        new Vec3(-1.0D, 0, 0.0D),
        new Vec3(-0.7D, 0, -0.7D),
        new Vec3(0.0D, 0, -1.0D),
        new Vec3(0.7D, 0, -0.7D)
    )),
    EIGHT_OFFSET(List.of( // Like eight, just turned clockwise
                          new Vec3(0.75D, 0, 0.5D),
                          new Vec3(0.5D, 0, 0.75D),
                          new Vec3(-0.5D, 0, 0.75D),
                          new Vec3(-0.75D, 0, 0.5D),
                          new Vec3(-0.75D, 0, 0.5D),
                          new Vec3(-0.5D, 0, -0.75D),
                          new Vec3(0.5D, 0, -0.75D),
                          new Vec3(0.75D, 0, -0.5D)
    )),
    SIXTEEN(List.of(
        new Vec3(1.0D, 0, 0.0D),
        new Vec3(0.75D, 0, 0.5D),
        new Vec3(0.7D, 0, 0.7D),
        new Vec3(0.5D, 0, 0.75D),
        new Vec3(0.0D, 0, 1.0D),
        new Vec3(-0.5D, 0, 0.75D),
        new Vec3(-0.7D, 0, 0.7D),
        new Vec3(-0.75D, 0, 0.5D),
        new Vec3(-1.0D, 0, 0.0D),
        new Vec3(-0.75D, 0, 0.5D),
        new Vec3(-0.7D, 0, -0.7D),
        new Vec3(-0.5D, 0, -0.75D),
        new Vec3(0.0D, 0, -1.0D),
        new Vec3(0.5D, 0, -0.75D),
        new Vec3(0.7D, 0, -0.7D),
        new Vec3(0.75D, 0, -0.5D)
    ));

    private final List<Vec3> v;

    VectorPattern(List<Vec3> vectors) {
        v = vectors;
    }

    public List<Vec3> getVectors() {
        return v;
    }

    public static final StreamCodec<ByteBuf, VectorPattern> STREAM_CODEC = PacketCodecHelper.enumOf(
        VectorPattern::values);

}
