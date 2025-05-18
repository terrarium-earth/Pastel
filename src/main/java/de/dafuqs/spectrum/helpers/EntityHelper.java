package de.dafuqs.spectrum.helpers;

import de.dafuqs.spectrum.mixin.accessors.FoxEntityAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

import java.util.UUID;

public class EntityHelper {
	
	public static void addPlayerTrust(Entity entity, Player player) {
		addPlayerTrust(entity, player.getUUID());
	}
	
	public static void addPlayerTrust(Entity entity, UUID playerUUID) {
		if (entity instanceof AbstractHorse horseBaseEntity) {
			if (horseBaseEntity.getOwnerUUID() == null) {
				horseBaseEntity.setOwnerUUID(playerUUID);
			}
		} else if (entity instanceof Fox foxEntity) {
			((FoxEntityAccessor) foxEntity).invokeAddTrustedUuid(playerUUID);
		}
	}
	
	public static boolean isRealPlayer(Entity entity) {
		// this should filter out most fake players (kibe, FAPI)
		return entity instanceof Player && entity.getClass().getCanonicalName().startsWith("net.minecraft");
	}
	
	public static boolean isRealPlayerProjectileOrPet(Entity entity) {
		if (entity instanceof TamableAnimal tameableEntity) {
			Entity owner = tameableEntity.getOwner();
			return isRealPlayer(owner);
		}
		if (entity instanceof Projectile projectileEntity) {
			Entity owner = projectileEntity.getOwner();
			return isRealPlayer(owner);
		}
		return isRealPlayer(entity);
	}
}