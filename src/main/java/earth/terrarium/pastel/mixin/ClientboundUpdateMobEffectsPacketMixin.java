package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.helpers.level.MobEffectHelper;
import earth.terrarium.pastel.injectors.ClientboundUpdateMobEffectPacketInjector;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientboundUpdateMobEffectPacket.class)
public abstract class ClientboundUpdateMobEffectsPacketMixin implements ClientboundUpdateMobEffectPacketInjector {
	
	@Unique
	private boolean incurable;
	
	@Inject(method = "<init>(ILnet/minecraft/world/effect/MobEffectInstance;Z)V", at = @At("RETURN"))
	public void initIncurable(int entityId, MobEffectInstance effect, boolean keepFading, CallbackInfo ci) {
		this.incurable = MobEffectHelper.resistsRemoval(effect);
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
	public boolean isIncurable() {
		return incurable;
	}
}
