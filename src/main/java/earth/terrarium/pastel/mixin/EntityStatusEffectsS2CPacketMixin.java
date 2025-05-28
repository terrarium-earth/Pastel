package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.helpers.StatusEffectHelper;
import earth.terrarium.pastel.injectors.EntityStatusEffectS2CPacketInjector;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientboundUpdateMobEffectPacket.class)
public abstract class EntityStatusEffectsS2CPacketMixin implements EntityStatusEffectS2CPacketInjector {
	
	@Unique
	private boolean incurable;
	
	@Inject(method = "<init>(ILnet/minecraft/world/effect/MobEffectInstance;Z)V", at = @At("RETURN"))
	public void initIncurable(int entityId, MobEffectInstance effect, boolean keepFading, CallbackInfo ci) {
		this.incurable = StatusEffectHelper.isIncurable(effect);
	}
	
	@Inject(method = "<init>(Lnet/minecraft/network/RegistryFriendlyByteBuf;)V", at = @At("RETURN"))
	public void initIncurable(RegistryFriendlyByteBuf buf, CallbackInfo ci) {
		this.incurable = buf.readBoolean();
	}
	
	@Inject(method = "write", at = @At("RETURN"))
	public void writeIncurable(RegistryFriendlyByteBuf buf, CallbackInfo ci) {
		buf.writeBoolean(incurable);
	}
	
	@Override
	public boolean spectrum$isIncurable() {
		return incurable;
	}
}
