package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.ClientRevelationHolder;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(BlockModelShaper.class)
public class BlockModelsMixin {
	@Shadow
	private Map<BlockState, BakedModel> modelByStateCache;
	
	@Shadow
	@Final
	private ModelManager modelManager;
	
	@Inject(method = "getBlockModel", at = @At("HEAD"), cancellable = true)
	private void revelationary$getModel(BlockState blockState, CallbackInfoReturnable<BakedModel> callbackInfoReturnable) {
		if (ClientRevelationHolder.isCloaked(blockState)) {
			BlockState destinationBlockState = ClientRevelationHolder.getCloakTarget(blockState);
			BakedModel overriddenModel = this.modelByStateCache.getOrDefault(destinationBlockState, modelManager.getMissingModel());
			callbackInfoReturnable.setReturnValue(overriddenModel);
		}
	}
}
