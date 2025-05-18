package de.dafuqs.spectrum.api.entity;

import de.dafuqs.spectrum.entity.entity.SpectrumFishingBobberEntity;

public interface PlayerEntityAccessor {
	
	void setSpectrumBobber(SpectrumFishingBobberEntity bobber);
	
	SpectrumFishingBobberEntity getSpectrumBobber();

	void setSleepTimer(int ticks);
}