package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.Mth;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseMixin {
	
	@SuppressWarnings("unchecked")
	@ModifyExpressionValue(method = "turnPlayer", at = @At(value = "INVOKE", target = "net/minecraft/client/OptionInstance.get ()Ljava/lang/Object;", ordinal = 0))
	public <T> T makeMouseSluggish(T original) {
		var sensitivity = (double) original;
		var player = Minecraft.getInstance().player;
		
		if (player == null)
			return original;
		
		var potency = SleepStatusEffect.getSleepScaling(player);
		
		if (potency == -1)
			return original;
		
		return (T) (Object) Mth.clampedLerp(sensitivity, sensitivity / 2, potency / 2.5);
	}
	
	// AHAHAHAH, I FINALLY FIGURED OUT HOW TO DO IT!
	@ModifyExpressionValue(method = "turnPlayer", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Options;smoothCamera:Z"))
	public boolean forceSmoothCamera(boolean original) {
		var player = Minecraft.getInstance().player;
		
		if (player == null)
			return original;
		
		var potency = SleepStatusEffect.getSleepScaling(player);
		
		if (potency < 1.9)
			return original;
		
		return true;
	}
}
