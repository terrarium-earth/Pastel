package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public abstract class EndermanEntityMixin {
	
	@Unique
	private final BlockState carriedBlockState = PastelBlocks.RADIATING_ENDER.get().defaultBlockState();
	
	@Shadow
	@Nullable
	public abstract BlockState getCarriedBlock();
	
	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(CallbackInfo info) {
		EnderMan endermanEntity = ((EnderMan) (Object) this);
		Level world = endermanEntity.getCommandSenderWorld();
		if (world instanceof ServerLevel) {
			RandomSource random = world.random;
			
			float chance;
			if (world.dimension().equals(Level.END)) {
				chance = PastelCommon.CONFIG.EndermanHoldingEnderTreasureInEndChance;
			} else {
				chance = PastelCommon.CONFIG.EndermanHoldingEnderTreasureChance;
			}
			
			if (random.nextFloat() < chance) {
				if (endermanEntity.getCarriedBlock() == null) {
					endermanEntity.setCarriedBlock(carriedBlockState);
				}
			}
		}
	}
	
	@Inject(at = @At("RETURN"), method = "requiresCustomPersistence", cancellable = true)
	public void cannotDespawn(CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue() && this.getCarriedBlock() != null && this.getCarriedBlock().is(PastelBlocks.RADIATING_ENDER.get())) {
			cir.setReturnValue(false);
		}
	}
	
}
