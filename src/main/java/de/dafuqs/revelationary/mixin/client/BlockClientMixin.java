package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.ClientRevelationHolder;
import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockClientMixin {
	@Inject(method = "getName", at = @At("RETURN"), cancellable = true)
	private void revelationary$getCloakedName(CallbackInfoReturnable<MutableComponent> callbackInfoReturnable) {
		Block thisBlock = (Block) (Object) this;
		if (ClientRevelationHolder.isCloaked(thisBlock)) {
			callbackInfoReturnable.setReturnValue(RevelationRegistry.getTranslationString(thisBlock));
		}
	}
}
