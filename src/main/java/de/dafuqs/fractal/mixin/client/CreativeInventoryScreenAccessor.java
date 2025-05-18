package de.dafuqs.fractal.mixin.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@OnlyIn(Dist.CLIENT)
@Mixin(CreativeModeInventoryScreen.class)
public interface CreativeInventoryScreenAccessor {
	
	@Accessor("selectedTab")
	static CreativeModeTab fractal$getSelectedGroup() {
		throw new AssertionError();
	}
	
}