package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.entity.PastelEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BedrockFishingBobberEntity extends PastelFishingBobberEntity {

	public BedrockFishingBobberEntity(EntityType<? extends PastelFishingBobberEntity> entityType, Level world) {
		super(entityType, world);
	}

	public BedrockFishingBobberEntity(Player thrower, Level world, int luckOfTheSeaLevel, int waitTimeReductionTicks, int exuberanceLevel, int bigCatchLevel, int serendipityReelLevel, boolean inventoryInsertion, boolean foundry) {
		super(PastelEntityTypes.BEDROCK_FISHING_BOBBER.get(), thrower, world, luckOfTheSeaLevel, waitTimeReductionTicks, exuberanceLevel, bigCatchLevel, serendipityReelLevel, inventoryInsertion, foundry);
	}
	
	@Override
	public int getLineColor() {
		return 0xFFa10f1d;
	}
}
