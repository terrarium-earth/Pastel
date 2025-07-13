package earth.terrarium.pastel.attachments.data;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public class InertiaData {

    public static final AttachmentType<InertiaData> ATTACHMENT =
            AttachmentType.builder(h -> new InertiaData((Player) h)).build();

    private static final int CAP = 12 * 3;
    private final Player holder;

    private Optional<Block> currentTarget = Optional.empty();
    private int stacks = 0;
    private float strength, lastStrength;
    private long minedTimeStamp;

    public InertiaData(Player holder) {
        this.holder = holder;
    }

    public float getPotency(boolean client) {
        if (client)
            return strength;

        strength = (float) Math.min(stacks, CAP) / CAP;
        return strength;
    }

    public void record(BlockState state, long time, int inertia) {
        if (time - 120 > minedTimeStamp && currentTarget.isPresent()) {
            currentTarget = Optional.empty();
            stacks = 0;
            strength = 0F;
            sync();
            return;
        }
        else if (stacks == 0) {
            currentTarget = Optional.of(state.getBlock());
        }

        if (currentTarget.map(state::is).orElse(false)) {
            if (stacks < CAP + Math.pow(inertia, 1.5) + 2)
                stacks += (inertia - 1) / 2 + 1;
        }
        else if(stacks > 0) {
            stacks--;
        }

        minedTimeStamp = time;
        strength = getPotency(false);
        sync();
    }

    public static void tick(Player player) {
        var data = player.getData(ATTACHMENT);
        data.tickInner(player);
    }

    private void tickInner(Player player) {
        var time = player.level().getGameTime();
        if (time - 10 > minedTimeStamp && stacks > 0) {
            stacks--;

            if (stacks == 0) {
                currentTarget = Optional.empty();
                strength = 0F;
            }
        }

        if (!Mth.equal(strength, lastStrength) && player instanceof ServerPlayer)
            sync();

        lastStrength = strength;
    }

    public void sync() {
        AttachmentUtil.syncToPlayer(new Payload(strength, minedTimeStamp),  holder);
    }

    public record Payload(float strength, long timeStamp) implements CustomPacketPayload {

        public static final StreamCodec<RegistryFriendlyByteBuf, Payload> CODEC = StreamCodec.composite(
                ByteBufCodecs.FLOAT, Payload::strength,
                ByteBufCodecs.VAR_LONG, Payload::timeStamp,
                Payload::new
        );

        public static final CustomPacketPayload.Type<Payload> TYPE = AttachmentUtil.create("inertia");

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void execute(Payload payload, IPayloadContext context) {
            var data = context.player().getData(ATTACHMENT);
            data.strength = payload.strength;
            data.minedTimeStamp = payload.timeStamp();
        }
    }
}
