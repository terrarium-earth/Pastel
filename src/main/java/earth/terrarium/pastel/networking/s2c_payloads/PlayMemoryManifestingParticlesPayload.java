package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.particle.effect.DynamicParticleEffect;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record PlayMemoryManifestingParticlesPayload(BlockPos pos, int eggColor1, int eggColor2, int amount)
    implements CustomPacketPayload {

    public static final Type<PlayMemoryManifestingParticlesPayload> ID = PastelC2SPackets.makeId(
        "play_memory_manifesting_particles");
    public static final StreamCodec<FriendlyByteBuf, PlayMemoryManifestingParticlesPayload> CODEC
        = StreamCodec.composite(
        BlockPos.STREAM_CODEC, PlayMemoryManifestingParticlesPayload::pos,
        ByteBufCodecs.INT, PlayMemoryManifestingParticlesPayload::eggColor1,
        ByteBufCodecs.INT, PlayMemoryManifestingParticlesPayload::eggColor2,
        ByteBufCodecs.INT, PlayMemoryManifestingParticlesPayload::amount,
        PlayMemoryManifestingParticlesPayload::new
    );

    public static void playMemoryManifestingParticles(
        ServerLevel serverWorld, @NotNull BlockPos pos, EntityType<?> entityType, int amount) {
        Tuple<Integer, Integer> eggColors = MemoryBlockEntity.getEggColorsForEntity(entityType);
        PacketDistributor.sendToPlayersTrackingChunk(
            serverWorld, new ChunkPos(pos), new PlayMemoryManifestingParticlesPayload(
                pos, eggColors.getA(),
                eggColors.getB(), amount
            )
        );
    }

    @SuppressWarnings("resource")
    public static void execute(PlayMemoryManifestingParticlesPayload payload, IPayloadContext context) {
        ClientLevel level = (ClientLevel) context.player()
                                                 .level();
        RandomSource random = level.random;

        Vector3f colorVec1 = ColorHelper.colorIntToVec(payload.eggColor1);
        Vector3f colorVec2 = ColorHelper.colorIntToVec(payload.eggColor1);

        BlockPos pos = payload.pos;
        for (int i = 0; i < payload.amount; i++) {
            int randomLifetime = 30 + random.nextInt(20);

            // color1
            level.addParticle(
                new DynamicParticleEffect(
                    ColoredCraftingParticleEffect.WHITE.getType(), 0.5F, colorVec1, 1.0F, randomLifetime, false, true),
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ(),
                0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3
            );

            // color2
            level.addParticle(
                new DynamicParticleEffect(
                    ColoredCraftingParticleEffect.WHITE.getType(), 0.5F, colorVec2, 1.0F, randomLifetime, false, true),
                pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3
            );
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
