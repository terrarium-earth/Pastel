package de.dafuqs.spectrum.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class FixedBodyControl extends BodyRotationControl {
	public FixedBodyControl(Mob mobEntity) {
		super(mobEntity);
	}
	
	@Override
	public void clientTick() {
	}
	
}
