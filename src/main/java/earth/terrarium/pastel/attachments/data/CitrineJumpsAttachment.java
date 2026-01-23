package earth.terrarium.pastel.attachments.data;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;

public class CitrineJumpsAttachment {
    public static final AttachmentType<Integer> ATTACHMENT = AttachmentType.builder(() -> 0)
                                                                           .serialize(Codec.INT)
                                                                           .build();
}
