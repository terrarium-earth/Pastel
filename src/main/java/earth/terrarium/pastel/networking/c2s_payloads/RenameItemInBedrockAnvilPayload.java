package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.inventories.BedrockAnvilScreenHandler;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.StringUtil;

public record RenameItemInBedrockAnvilPayload(String name) implements CustomPacketPayload {

    public static final Type<RenameItemInBedrockAnvilPayload> ID = PastelC2SPackets.makeId(
        "rename_item_in_bedrock_anvil");
    public static final StreamCodec<FriendlyByteBuf, RenameItemInBedrockAnvilPayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, RenameItemInBedrockAnvilPayload::name, RenameItemInBedrockAnvilPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static IPayloadHandler<RenameItemInBedrockAnvilPayload> getPayloadHandler() {
        return (payload, context) -> {
            if (context.player().containerMenu instanceof BedrockAnvilScreenHandler bedrockAnvilScreenHandler) {
                String string = StringUtil.filterText(payload.name);
                if (string.length() <= 50) {
                    bedrockAnvilScreenHandler.setNewItemName(string);
                }
            }
        };
    }

}
