package de.dafuqs.spectrum.attachments.data.azure_dike;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.resources.ResourceLocation;

public interface DikeShieldData {

	ResourceLocation AZURE_DIKE_BAR_TEXTURE = SpectrumCommon.locate("textures/gui/azure_dike_overlay.png");
	
	float getCurrentProtection();
	
	float getMaxProtection();
	
	int getTicksPerPointOfRecharge();
	
	int getCurrentRechargeDelay();
	
	int getRechargeDelayTicksAfterGettingHit();
	
	float absorbDamage(float incomingDamage);
	
	void set(float maxProtection, int rechargeDelayDefault, int fasterRechargeAfterDamageTicks, boolean resetCharge);
}