package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.ClientAdvancements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {

	@Inject(at = @At("HEAD"), method = "disconnect*")
	public void revelationary$onLogout(Screen screen, CallbackInfo info) {
		ClientAdvancements.playerLogout();
	}
}