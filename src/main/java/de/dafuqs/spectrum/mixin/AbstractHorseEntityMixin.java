package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.api.block.PlayerOwned;
import de.dafuqs.spectrum.api.item.GravitableItem;
import de.dafuqs.spectrum.helpers.Support;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseEntityMixin {
	
	@Shadow
	protected SimpleContainer inventory;
	
	/**
	 * This pretty much implements inventoryTick() for Donkeys
	 * since inventoryTick() only triggers for player inventories
	 */
	@Inject(at = @At("HEAD"), method = "tick()V")
	public void tick(CallbackInfo callbackInfo) {
		var horse = (AbstractHorse) (Object) this;
		if (horse instanceof AbstractChestedHorse thisEntity
				&& !thisEntity.isNoGravity()
				&& thisEntity.hasChest()
				&& thisEntity.level() instanceof ServerLevel serverWorld) {
			
			double addedGravity = 0;
			for (ItemStack stack : this.inventory.getItems()) {
				if (stack.getItem() instanceof GravitableItem gravitableItem) {
					addedGravity += gravitableItem.applyGravity(stack, serverWorld, thisEntity);
				}
			}
			
			// when the animal is sent flying trigger a hidden advancement
			if (addedGravity > 0.081 && serverWorld.getGameTime() % 20 == 0) {
				Player owner = PlayerOwned.getPlayerEntityIfOnline(thisEntity.getOwnerUUID());
				if (owner != null) {
					Support.grantAdvancementCriterion((ServerPlayer) owner, "lategame/put_too_many_low_gravity_blocks_into_animal", "gravity");
				}
				
				// take damage when at height heights
				// otherwise the animal would just be floating forever
				if (thisEntity.position().y > serverWorld.getHeight() + 1000) {
					thisEntity.hurt(thisEntity.damageSources().fellOutOfWorld(), 10);
				}
			}
		}
	}
	
}
