package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LiquidBlock.class)
public interface FluidBlockAccessor {
	
	@Accessor("fluid")
	FlowingFluid getFlowableFluid();
	
}