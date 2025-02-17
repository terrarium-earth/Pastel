package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

public class SpectrumEntityColorProcessors {
	
	public static void register() {
		// VANILLA
		EntityColorProcessorRegistry.register(EntityType.SHEEP, (entity, dyeColor) -> {
			if (dyeColor.isEmpty()) {
				return false;
			}
			DyeColor color = dyeColor.get();
			
			if (entity.getColor() == color) {
				return false;
			}
			entity.setColor(color);
			return true;
		});
		EntityColorProcessorRegistry.register(EntityType.WOLF, (entity, dyeColor) -> {
			if (dyeColor.isEmpty()) {
				return false;
			}
			if (!entity.isTamed() || !entity.isOwner(player)) {
				return false;
			}
			DyeColor color = dyeColor.get();
			
			if (entity.getCollarColor() == color) {
				return false;
			}
			
			entity.setColarColor(color);
			return true;
		});
		EntityColorProcessorRegistry.register(EntityType.CAT, (entity, dyeColor) -> {
			if (entity.getCollarColor() == dyeColor) {
				return false;
			}
			if (!entity.isTamed() || !entity.isOwner(player)) {
				return false;
			}
			
			entity.setColarColor(color);
			return true;
		});
		EntityColorProcessorRegistry.register(EntityType.SHULKER, (entity, dyeColor) -> {
			@Nullable DyeColor shulkerColor = entity.getColor();
			if (shulkerColor == null && dyeColor.isEmpty() || shulkerColor == dyeColor.get()) {
				return false;
			}
			entity.setVariant(dyeColor);
			return true;
		});
		
		// SPECTRUM
		EntityColorProcessorRegistry.register(SpectrumEntityTypes.EGG_LAYING_WOOLY_PIG, (entity, dyeColor) -> {
			if (entity.getColor() == dyeColor) {
				return false;
			}
			entity.setColor(dyeColor);
			return true;
		});
		EntityColorProcessorRegistry.register(SpectrumEntityTypes.INK_PROJECTILE, (entity, dyeColor) -> {
			if (entity.getInkColor().getDyeColor() == dyeColor) {
				return false;
			}
			entity.setColor(InkColor.ofDyeColor(dyeColor));
			return true;
		});
	}
	
}
