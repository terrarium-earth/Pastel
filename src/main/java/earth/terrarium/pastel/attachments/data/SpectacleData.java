package earth.terrarium.pastel.attachments.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.imbrifer.Environmental;
import earth.terrarium.pastel.items.trinkets.PriscillentSpectaclesItem;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelLevels;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SpectacleData {

    public static final AttachmentType<SpectacleData> ATTACHMENT = AttachmentType
        .builder(h -> new SpectacleData((Player) h))
        .build();

    public static final InkCost INK_COST = new InkCost(InkColors.LIGHT_BLUE, 20);

    public static final ItemStack ITEM_COST = new ItemStack(Items.GLOW_INK_SAC, 1);

    private final Player holder;

    private long duration;

    private float potency;

    private boolean active;

    private boolean sessionStart = true;

    public SpectacleData(Player holder) {
        this.holder = holder;
    }

    public static void tickClient(Player holder) {
        var data = holder.getData(ATTACHMENT);

        if (data.active && data.potency < 0.8F) {
            data.potency += 0.02F;
        } else if (!data.active && data.potency > 0) {
            data.potency -= 0.04F;
        }

        if (data.duration > 0 && isActive(holder))
            data.duration--;
    }

    public void tickServer() {
        if (sessionStart) {
            sessionStart = false;
            sync();
        }

        var level = holder.level();
        var activate = level.getMaxLocalRawBrightness(holder.blockPosition()) < 8 || level
            .dimension()
            .equals(PastelLevels.DIMENSION_KEY);

        if (!updateDuration()) {
            activate = false;
        }

        if (activate == active)
            return;

        active = activate;
        sync();
    }

    private boolean updateDuration() {
        if (duration > 0) {
            if (active)
                duration--;
            return true;
        }

        var paid = holder.isCreative();
        if (!paid) {
            paid = InkPowered.tryDrainEnergy(holder, INK_COST) || InventoryHelper
                .removeFromInventoryWithRemainders(holder, ITEM_COST);
        }

        if (!paid) {
            return false;
        }

        duration = PastelCommon.CONFIG.GlowVisionGogglesDuration * 20L;
        holder
            .level()
            .playSound(
                null,
                holder,
                PastelSounds.ITEM_ARMOR_EQUIP_GLOW_VISION,
                SoundSource.PLAYERS,
                0.2F,
                1.0F
            );
        return true;
    }

    public boolean isActive() {
        return active;
    }

    public static boolean isActive(Player player) {
        return player.getData(ATTACHMENT).potency > 0 && PriscillentSpectaclesItem
            .hasEquipped(player, PastelItems.PRISCILLENT_SPECTACLES.get());
    }

    public float getPotency() {
        var finalPot = Math.clamp(potency - 0.15F, 0, 0.5F);

        if (Environmental
            .isActive()
            .force()) {
            finalPot /= 3F;
        }
        finalPot *= 1F - Environmental
            .getEnvData()
            .darkening();

        return finalPot;
    }

    public float getRemainingDuration() {
        return (float) duration / (PastelCommon.CONFIG.GlowVisionGogglesDuration * 20L);
    }

    public void sync() {
        AttachmentUtil.syncToPlayer(new Payload(duration, active), holder);
    }

    public record Payload(long duration, boolean active) implements CustomPacketPayload {

        public static final StreamCodec<RegistryFriendlyByteBuf, Payload> CODEC = StreamCodec
            .composite(
                ByteBufCodecs.VAR_LONG,
                Payload::duration,
                ByteBufCodecs.BOOL,
                Payload::active,
                Payload::new
            );

        public static final CustomPacketPayload.Type<Payload> TYPE = AttachmentUtil.create("spectacle");

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void execute(Payload payload, IPayloadContext context) {
            var data = context
                .player()
                .getData(ATTACHMENT);

            data.duration = payload.duration();
            data.active = payload.active();
        }
    }
}
