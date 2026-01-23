package earth.terrarium.pastel.attachments.data.azure_dike;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.resources.ResourceLocation;

public interface DikeShieldData {

    ResourceLocation AZURE_DIKE_BAR_TEXTURE = PastelCommon.locate("textures/gui/azure_dike_overlay.png");

    float getCurrentProtection();

    float getMaxProtection();

    int getTicksPerPointOfRecharge();

    int getCurrentRechargeDelay();

    int getRechargeDelayTicksAfterGettingHit();

    float absorbDamage(float incomingDamage, boolean effective);

    void set(float maxProtection, int rechargeDelayDefault, int fasterRechargeAfterDamageTicks, boolean resetCharge);
}
