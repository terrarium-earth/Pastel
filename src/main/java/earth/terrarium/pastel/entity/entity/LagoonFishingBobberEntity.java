package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.entity.PastelEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LagoonFishingBobberEntity extends PastelFishingBobberEntity {

    public LagoonFishingBobberEntity(EntityType<? extends LagoonFishingBobberEntity> entityType, Level world) {
        super(entityType, world);
    }

    public LagoonFishingBobberEntity(
        Player thrower, Level world, int luckOfTheSeaLevel, int waitTimeReductionTicks, int exuberanceLevel,
        int bigCatchLevel, int serendipityReelLevel, boolean inventoryInsertion, boolean foundry
    ) {
        super(
            PastelEntityTypes.LAGOON_FISHING_BOBBER.get(), thrower, world, luckOfTheSeaLevel, waitTimeReductionTicks,
            exuberanceLevel, bigCatchLevel, serendipityReelLevel, inventoryInsertion, foundry
        );
    }

    @Override
    public int getLineColor() {
        return 0xb6c6c8FF;
    }
}
