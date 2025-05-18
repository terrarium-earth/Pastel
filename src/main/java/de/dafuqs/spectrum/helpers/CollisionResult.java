package de.dafuqs.spectrum.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record CollisionResult<T>(Level world, T collision, CollisionType type, Vec3 collisionPoint) {

    public boolean sanityCheck() {
        if (type != CollisionType.BLOCK) {
            var collisionBox = ((Entity) collision).getBoundingBox();
            return collisionBox.contains(collisionPoint);
        }
        else {
            var pos = BlockPos.containing(collisionPoint);
            return world.getBlockState(pos).getInteractionShape(world, pos).toAabbs().stream().anyMatch(box -> box.contains(collisionPoint));
        }
    }

    public enum CollisionType {
        LIVING,
        NON_LIVING,
        BLOCK
    }
}
