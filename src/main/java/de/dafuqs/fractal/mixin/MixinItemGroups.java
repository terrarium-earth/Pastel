package de.dafuqs.fractal.mixin;

import de.dafuqs.fractal.api.ItemSubGroup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeTabs.class)
public class MixinItemGroups {
	
	@Inject(at = @At("HEAD"), method = "buildAllTabContents")
	private static void updateEntries(CreativeModeTab.ItemDisplayParameters displayContext, CallbackInfo ci) {
		ItemSubGroup.SUB_GROUPS.forEach((group) -> {
			group.buildContents(displayContext);
		});
	}
	
}
