package de.dafuqs.spectrum.mixin.accessors;


import net.minecraft.world.entity.animal.Fox;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.UUID;

@Mixin(Fox.class)
public interface FoxEntityAccessor {
	
	@Invoker
	void invokeAddTrustedUuid(@Nullable UUID uuid);
	
}