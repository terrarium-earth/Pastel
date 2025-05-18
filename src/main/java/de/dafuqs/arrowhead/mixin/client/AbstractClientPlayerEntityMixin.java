package de.dafuqs.arrowhead.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.dafuqs.arrowhead.api.ArrowheadBow;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@OnlyIn(Dist.CLIENT)
@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin {

	@ModifyReturnValue(method = "getFieldOfViewModifier", at = @At("RETURN"))
	private float arrowhead$applyCustomBowZoom(float original) {
		AbstractClientPlayer thisPlayer = (AbstractClientPlayer)(Object) this;
		ItemStack activeStack = thisPlayer.getUseItem();
		if (thisPlayer.isUsingItem() && activeStack.getItem() instanceof ArrowheadBow arrowheadBow) {
			int useTime = thisPlayer.getTicksUsingItem();
			float g = Math.min(useTime / arrowheadBow.getZoom(activeStack), 1.0F);
			original *= 1.0F - Mth.square(g) * 0.15F;
		}

		return original;
	}

}
