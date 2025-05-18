package de.dafuqs.fractal.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(CreativeModeInventoryScreen.class)
public interface CreativeInventoryScreenAccessor {
	
	@Accessor("selectedTab")
	static CreativeModeTab fractal$getSelectedGroup() {
		throw new AssertionError();
	}
	
}