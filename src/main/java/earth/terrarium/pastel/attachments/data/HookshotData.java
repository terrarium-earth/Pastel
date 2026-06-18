package earth.terrarium.pastel.attachments.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class HookshotData {

    public static final Codec<HookshotData> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    UUIDUtil.CODEC.optionalFieldOf("linked_hook").forGetter(h -> h.linkedHook)
                )
                .apply(i, HookshotData::ofCodec)
        );

    public static final AttachmentType<HookshotData> ATTACHMENT = AttachmentType
        .builder(h -> new HookshotData((Player) h))
        .serialize(CODEC)
        .build();

    private Optional<UUID> linkedHook = Optional.empty();

    private int hookId = -1;

    private Player holder;

    public HookshotData(Player holder) {
        this.holder = holder;
    }

    private HookshotData() {
    }

    public static HookshotData ofCodec(Optional<UUID> linkedHook) {
        var data = new HookshotData();
        data.linkedHook = linkedHook;
        return data;
    }

    public Optional<UUID> getLinkedHook() {
        return linkedHook;
    }

    public Entity getHookEntity(Level level) {
        if (linkedHook.isEmpty())
            return null;

        if (level instanceof ServerLevel sl)
            return sl.getEntity(linkedHook.get());

        return level.getEntity(hookId);
    }

    public void setLinkedHook(Entity entity) {
        linkedHook = Optional.of(entity.getUUID());
        hookId = entity.getId();
    }

    public boolean isAlreadyHooked() {
        if (linkedHook.isEmpty())
            return false;

        var entity = getHookEntity(holder.level());

        if (entity == null || entity.isRemoved() || !entity.isAlive()) {
            linkedHook = Optional.empty();
            return false;
        }

        return true;
    }

    public float getFrictionModifier() {
        if (getHookEntity(holder.level()) instanceof FrictionProvider fp)
            return fp.getFrictionMod();
        return 0F;
    }

    public static HookshotData get(@NotNull Player player) {
        var data = player.getData(ATTACHMENT);
        if (data.holder == null)
            data.holder = player;

        return data;
    }

    public interface FrictionProvider {
        float getFrictionMod();
    }
}
