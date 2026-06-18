package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.inventories.BedrockAnvilScreenHandler;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record AddLoreBedrockAnvilPayload(String lore) implements CustomPacketPayload {

    public static final Type<AddLoreBedrockAnvilPayload> ID = PastelC2SPackets
        .makeId(
            "add_lore_to_item_in_bedrock_anvil"
        );

    public static final StreamCodec<FriendlyByteBuf, AddLoreBedrockAnvilPayload> CODEC = StreamCodec
        .composite(
            ByteBufCodecs.STRING_UTF8,
            AddLoreBedrockAnvilPayload::lore,
            AddLoreBedrockAnvilPayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static IPayloadHandler<AddLoreBedrockAnvilPayload> getPayloadHandler() {
        return (payload, context) -> {
            if (context.player().containerMenu instanceof BedrockAnvilScreenHandler bedrockAnvilScreenHandler) {
                if (!bedrockAnvilScreenHandler.stillValid(context.player())) {
                    PastelCommon.LOGGER
                        .debug(
                            "Player {} interacted with invalid menu {} while setting lore",
                            context.player(),
                            bedrockAnvilScreenHandler
                        );
                }
                bedrockAnvilScreenHandler.setNewItemLore(payload.lore());
            }
        };
    }

}
