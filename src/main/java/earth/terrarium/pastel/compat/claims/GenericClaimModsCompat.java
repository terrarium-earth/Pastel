package earth.terrarium.pastel.compat.claims;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

public class GenericClaimModsCompat {

    // TODO: Add Odyssey Claims compat.

    /**
     * Call this for all kinds of world modifications
     * For each supported protection mod add a single check here
     * instead of spreading individual protection mods over the whole codebase
     * <p>
     * This also means we do not need any kind of hard compat
     *
     * @param world the world that should get modified
     * @param pos   the pos that should get modified
     * @return if modification is allowed
     */
    public static boolean canBreak(Level world, BlockPos pos, @Nullable Entity cause) {
        return true;
    }

    public static boolean canInteract(Level world, Entity entity, @Nullable Entity cause) {
        return true;
    }

    public static boolean canInteract(Level world, BlockPos pos, @Nullable Entity cause) {
        return true;
    }

    /**
     * Used to determine whether you can break and place blocks in this area, which is useful
     * for swapping blocks
     *
     * @param world the world that should get modified
     * @param pos   the pos that should get modified
     * @return if modification is allowed
     */
    public static boolean canModify(Level world, BlockPos pos, @Nullable Entity cause) {
        return true;
    }

    public static boolean canPlaceBlock(Level world, BlockPos pos, @Nullable Entity cause) {
        return true;
    }

}
