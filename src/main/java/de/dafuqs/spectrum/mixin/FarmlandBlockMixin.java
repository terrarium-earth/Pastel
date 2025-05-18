package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(FarmBlock.class)
public abstract class FarmlandBlockMixin extends Block {
	public FarmlandBlockMixin(Properties settings) {
		super(settings);
	}
	
	@Inject(method = {"fallOn"}, at = {@At("HEAD")}, cancellable = true)
	private void spectrum$onLandedUpon(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo info) {
		super.fallOn(world, state, pos, entity, fallDistance); // fall damage
		
		// if carrying puff circlet: no trampling
		if (entity instanceof LivingEntity livingEntity) {
			Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(livingEntity);
			if (component.isPresent()) {
				if (!component.get().getEquipped(SpectrumItems.PUFF_CIRCLET).isEmpty()) {
					info.cancel();
				}
			}
		}
	}
}