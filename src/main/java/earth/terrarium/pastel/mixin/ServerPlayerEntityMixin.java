package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.helpers.SpectrumEnchantmentHelper;
import earth.terrarium.pastel.helpers.enchantments.DisarmingHelper;
import earth.terrarium.pastel.items.trinkets.GleamingPinItem;
import earth.terrarium.pastel.items.trinkets.SpectrumTrinketItem;
import earth.terrarium.pastel.registries.SpectrumEnchantments;
import earth.terrarium.pastel.registries.SpectrumItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

// TODO: Migrate these mixins to FAPI ServerEntityCombatEvents
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin {
	
	@Shadow
	public abstract ServerLevel serverLevel();
	
	@Unique
	private long spectrum$lastGleamingPinTriggerTick = 0;
	
	@Inject(at = @At("RETURN"), method = "hurt")
	public void spectrum$damageReturn(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		ServerLevel world = this.serverLevel();
		
		// true if the entity got hurt
		if (cir.getReturnValue() != null && cir.getReturnValue()) {
			ServerPlayer thisPlayer = (ServerPlayer) (Object) this;
			Optional<ItemStack> gleamingPinStack = SpectrumTrinketItem.getFirstEquipped(thisPlayer, SpectrumItems.GLEAMING_PIN.get());
			if (gleamingPinStack.isPresent() && world.getGameTime() - this.spectrum$lastGleamingPinTriggerTick > GleamingPinItem.COOLDOWN_TICKS) {
				GleamingPinItem.doGleamingPinEffect(thisPlayer, world, gleamingPinStack.get());
				this.spectrum$lastGleamingPinTriggerTick = world.getGameTime();
			}
			
			if (source.getEntity() instanceof LivingEntity livingSource) {
				int disarmingLevel = SpectrumEnchantmentHelper.getLevel(world.registryAccess(), SpectrumEnchantments.DISARMING, livingSource.getMainHandItem());
				if (disarmingLevel > 0 && Math.random() < disarmingLevel * SpectrumCommon.CONFIG.DisarmingChancePerLevelPlayers) {
					DisarmingHelper.disarmEntity(thisPlayer);
				}
			}
		}
	}
	
}
