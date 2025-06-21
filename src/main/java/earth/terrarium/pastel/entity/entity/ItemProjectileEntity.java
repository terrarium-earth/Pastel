package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.api.interaction.projectile_behavior.ItemProjectileBehavior;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ItemProjectileEntity extends ThrowableItemProjectile {

	public ItemProjectileEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
		super(entityType, world);
	}

	public ItemProjectileEntity(Level world, LivingEntity owner) {
		super(PastelEntityTypes.ITEM_PROJECTILE.get(), owner, world);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		ItemStack stack = getItem();

		if (!this.level().isClientSide) {
			ItemProjectileBehavior behavior = ItemProjectileBehavior.get(stack);
			if (behavior != null) {
				HitResult.Type type = hitResult.getType();
				if (type == HitResult.Type.ENTITY) {
					this.level().gameEvent(GameEvent.PROJECTILE_LAND, hitResult.getLocation(), GameEvent.Context.of(this, null));
					behavior.onEntityHit(this, stack, getOwner(), (EntityHitResult) hitResult);
				} else if (type == HitResult.Type.BLOCK) {
					BlockHitResult blockHitResult = (BlockHitResult) hitResult;
					BlockPos blockPos = blockHitResult.getBlockPos();
					this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Context.of(this, this.level().getBlockState(blockPos)));
					behavior.onBlockHit(this, stack, getOwner(), (BlockHitResult) hitResult);
				}
			}
			
			this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
			
			if (!stack.isEmpty()) {
				Entity owner = this.getOwner();
				if (!(owner instanceof Player player) || !player.isCreative()) {
					Containers.dropItemStack(level(), this.getX(), this.getY(), this.getZ(), stack);
				}
			}
			
			this.discard();
		}
	}

	@Override
	public void handleEntityEvent(byte status) {
		if (status == EntityEvent.DEATH) {
			ItemStack itemStack = this.getItem();
			ParticleOptions particleEffect = (itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemStack));

			for(int i = 0; i < 8; ++i) {
				this.level().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	protected Item getDefaultItem() {
		return Items.AIR;
	}

}
