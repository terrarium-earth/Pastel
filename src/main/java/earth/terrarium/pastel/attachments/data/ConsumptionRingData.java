package earth.terrarium.pastel.attachments.data;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;

public class ConsumptionRingData {
    // same as the jeopardant, this will be checked every time an entity is hurt by a player, so we don't want to
    // iterate over the entire curios inv every time something takes damage
    public static final AttachmentType<Boolean> ATTACHMENT = AttachmentType.builder(() -> false)
                                                                           .serialize(Codec.BOOL)
                                                                           .build();
}
