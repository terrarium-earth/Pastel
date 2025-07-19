package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.*;
import earth.terrarium.pastel.*;
import earth.terrarium.pastel.entity.entity.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public record MusicDiscProjectileBehavior() implements DamagingProjectileBehavior {
	public static final MusicDiscProjectileBehavior INSTANCE = new MusicDiscProjectileBehavior();
	public static final ProjectileBehaviorType<MusicDiscProjectileBehavior> TYPE = new ProjectileBehaviorType<>(
			PastelCommon.ofPastel("music_disc"),
			MapCodec.unit(INSTANCE)
	);
	
	@Override
	public boolean destroyItemOnHit() {
		return false;
	}
	
	@Override
	public boolean dealDamage(ThrowableItemProjectile projectile, Entity owner, Entity target) {
		return target.hurt(target.damageSources().thrown(projectile, owner), 6f);
	}
	
	@Override
	public ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, BlockHitResult hitResult) {
		Level world = projectile.level();
		BlockEntity blockEntity = world.getBlockEntity(hitResult.getBlockPos());
		if (blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity && !blockEntity.isRemoved()) {
			ItemStack currentStack = jukeboxBlockEntity.getItem(0);
			if (!currentStack.isEmpty()) {
				jukeboxBlockEntity.popOutTheItem();
			}
			jukeboxBlockEntity.setTheItem(stack.copy());
			stack.shrink(1);
		}
		return stack;
	}
	
	@Override
	public ProjectileBehaviorType<?> type() {
		return TYPE;
	}
}
