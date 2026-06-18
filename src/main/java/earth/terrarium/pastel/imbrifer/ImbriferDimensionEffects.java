package earth.terrarium.pastel.imbrifer;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(
    Dist.CLIENT
)
public class ImbriferDimensionEffects extends DimensionSpecialEffects {

    public ImbriferDimensionEffects() {
        super(Float.NaN, false, DimensionSpecialEffects.SkyType.NONE, false, true);
    }

    @Override
    public @Nullable float[] getSunriseColor(float skyAngle, float tickDelta) {
        return null;
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
        return color;
    }

    @Override
    public boolean isFoggyAt(int camX, int camY) {
        return true;
    }

}
