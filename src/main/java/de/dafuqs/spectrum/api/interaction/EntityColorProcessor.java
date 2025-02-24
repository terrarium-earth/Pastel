package de.dafuqs.spectrum.api.interaction;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface EntityColorProcessor<E extends Entity> {
	
	/**
	 * Logic for coloring an entity type in a dyecolor
	 *
	 * @param entity   The entity to be colored
	 * @param dyeColor The color to color the entity in
	 * @param player   The player that dyed the entity
	 * @return if the coloring was successful (true if colored, false when failed, like the entity already being that color)
	 */
	boolean colorEntity(E entity, Optional<DyeColor> dyeColor, @Nullable PlayerEntity player);
	
}
