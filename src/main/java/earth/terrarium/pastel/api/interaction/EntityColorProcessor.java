package earth.terrarium.pastel.api.interaction;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface EntityColorProcessor<E extends Entity> {

    /**
     * Logic for coloring an entity type in a dyecolor
     *
     * @param entity   The entity to be colored
     * @param dyeColor The color to color the entity in
     * @param player   The player that dyed the entity
     * @return if the coloring was successful (true if colored, false when failed, like the entity already being that
     * color)
     */
    boolean colorEntity(E entity, Optional<DyeColor> dyeColor, @Nullable Player player);

}
