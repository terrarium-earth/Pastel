package earth.terrarium.pastel.api.item;

import com.cmdpro.databank.misc.ColorGradient;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.resources.ResourceLocation;

public interface HasColorGradient {
    ResourceLocation LUNGE = PastelCommon.locate("lunge");

    ColorGradient getColorGradient(ResourceLocation gradient);
}
