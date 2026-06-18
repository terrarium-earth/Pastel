package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.blocks.idols.FirestarterIdolBlock;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MoltenFishingBobberEntity extends PastelFishingBobberEntity {

    public MoltenFishingBobberEntity(EntityType<? extends PastelFishingBobberEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        Level world = this.level();
        super.tick();
        if (!world.isClientSide && tickCount % 20 == 0 && onGround()) {
            FirestarterIdolBlock.causeFire((ServerLevel) this.level(), blockPosition(), Direction.DOWN);
        }
    }

    public MoltenFishingBobberEntity(
        Player thrower,
        Level world,
        int luckBonus,
        int waitTimeReductionTicks,
        int exuberanceLevel,
        int bigCatchLevel,
        int serendipityReelLevel,
        boolean inventoryInsertion
    ) {
        super(
            PastelEntityTypes.MOLTEN_FISHING_BOBBER.get(),
            thrower,
            world,
            luckBonus,
            waitTimeReductionTicks,
            exuberanceLevel,
            bigCatchLevel,
            serendipityReelLevel,
            inventoryInsertion,
            true
        );
    }

    @Override
    public void hookedEntityTick(Entity hookedEntity) {
        hookedEntity.igniteForSeconds(2);
    }

    @Override
    public int getLineColor() {
        return 0xFFff8e24;
    }
}
