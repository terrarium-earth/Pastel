package de.dafuqs.spectrum.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class EmptyLookControl extends LookControl {
	
	public EmptyLookControl(Mob entity) {
		super(entity);
	}
	
	@Override
	public void tick() {
	}
	
}
	