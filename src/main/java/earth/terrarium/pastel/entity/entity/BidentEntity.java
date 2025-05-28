package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.entity.SpectrumEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class BidentEntity extends BidentBaseEntity {
	
	public BidentEntity(Level world) {
		this(SpectrumEntityTypes.BIDENT, world);
	}
	
	public BidentEntity(EntityType<? extends ThrownTrident> entityType, Level world) {
		super(entityType, world);
	}
	
	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
	}
    
}
