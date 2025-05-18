package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.items.magic_items.CelestialPocketWatchItem;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public abstract class ItemFrameEntityMixin {
	
	@Shadow
	public abstract ItemStack getItem();
	
	@Inject(method = "interact",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;setRotation(I)V"))
	public void interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		if (getItem().is(SpectrumItems.CELESTIAL_POCKETWATCH) && (((ItemFrame) (Object) this).level() instanceof ServerLevel serverWorld)) {
			CelestialPocketWatchItem.tryAdvanceTime(serverWorld, (ServerPlayer) player);
		}
	}
	
}
