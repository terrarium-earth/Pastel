package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.helpers.SpectrumColorHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.gameevent.PositionSource;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ColoredTransmissionParticle extends TransmissionParticle {
	
	public ColoredTransmissionParticle(ClientLevel world, double x, double y, double z, PositionSource positionSource, int maxAge, int color) {
		super(world, x, y, z, positionSource, maxAge);
		
		Vector3f colorVec = SpectrumColorHelper.colorIntToVec(color);
		this.setColor(colorVec.x(), colorVec.y(), colorVec.z());
	}
	
}