package earth.terrarium.pastel.api.entity;

import earth.terrarium.pastel.entity.entity.SpectrumFishingBobberEntity;

public interface PlayerEntityAccessor {
	
	void setSpectrumBobber(SpectrumFishingBobberEntity bobber);
	
	SpectrumFishingBobberEntity getSpectrumBobber();

	void setSleepTimer(int ticks);
}