package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.helpers.*;
import net.fabricmc.api.*;
import net.minecraft.client.world.*;
import net.minecraft.world.event.*;
import org.joml.*;

@Environment(EnvType.CLIENT)
public class ColoredTransmissionParticle extends TransmissionParticle {
	
	public ColoredTransmissionParticle(ClientWorld world, double x, double y, double z, PositionSource positionSource, int maxAge, int color) {
		super(world, x, y, z, positionSource, maxAge);
		
		Vector3f colorVec = SpectrumColorHelper.colorIntToVec(color);
		this.setColor(colorVec.x(), colorVec.y(), colorVec.z());
	}
	
}