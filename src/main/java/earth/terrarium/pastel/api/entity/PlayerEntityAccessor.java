package earth.terrarium.pastel.api.entity;

import earth.terrarium.pastel.entity.entity.PastelFishingBobberEntity;

public interface PlayerEntityAccessor {
	
	void setSpectrumBobber(PastelFishingBobberEntity bobber);
	
	PastelFishingBobberEntity getSpectrumBobber();

	void setSleepTimer(int ticks);
}