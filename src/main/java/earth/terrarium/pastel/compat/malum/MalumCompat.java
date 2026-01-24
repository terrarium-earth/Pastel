package earth.terrarium.pastel.compat.malum;

import com.sammy.malum.registry.common.MalumAttachmentTypes;
import net.minecraft.client.player.LocalPlayer;

public class MalumCompat {
    public static int getSoulWardRows(LocalPlayer player) {
        // java why
        return Math.toIntExact(Math.round(Math.ceil(player.getData(MalumAttachmentTypes.SOUL_WARD)
                                                          .getSoulWard() / 30)));
    }
}
