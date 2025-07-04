package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.helpers.data.ColorHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.gameevent.PositionSource;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ColoredTransmissionParticle extends TransmissionParticle {
	
	public ColoredTransmissionParticle(ClientLevel world, double x, double y, double z, PositionSource positionSource, int maxAge, int color) {
		super(world, x, y, z, positionSource, maxAge);
		
		Vector3f colorVec = ColorHelper.colorIntToVec(color);
		this.setColor(colorVec.x(), colorVec.y(), colorVec.z());
	}
	
}