package earth.terrarium.pastel.attachments.data;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;

public class JeopardantBonusData {
    public static final AttachmentType<Boolean> ATTACHMENT = AttachmentType
        .builder(() -> false)
        .serialize(Codec.BOOL)
        .build();
}
