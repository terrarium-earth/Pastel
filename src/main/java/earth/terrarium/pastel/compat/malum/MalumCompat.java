package earth.terrarium.pastel.compat.malum;

import com.sammy.malum.registry.common.MalumAttachmentTypes;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;

public class MalumCompat {
    public static int getSoulWardRows(LocalPlayer player) {
        return Mth.ceil(player.getData(MalumAttachmentTypes.SOUL_WARD)
                                                          .getSoulWard() / 30);
    }
}
