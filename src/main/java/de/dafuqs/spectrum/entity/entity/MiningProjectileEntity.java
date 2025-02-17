package de.dafuqs.spectrum.entity.entity;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.particle.effect.*;
import de.dafuqs.spectrum.spells.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.hit.*;
import net.minecraft.world.*;

import java.util.function.*;

public class MiningProjectileEntity extends MagicProjectileEntity {
	
	private static final int MINING_RANGE = 1;
	private ItemStack toolStack = ItemStack.EMPTY;

	public MiningProjectileEntity(EntityType<MiningProjectileEntity> type, World world) {
		super(type, world);
	}

	public MiningProjectileEntity(double x, double y, double z, World world) {
		this(SpectrumEntityTypes.MINING_PROJECTILE, world);
		this.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
		this.refreshPosition();
	}

	public MiningProjectileEntity(World world, LivingEntity owner) {
		this(owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), world);
		this.setOwner(owner);
		this.setRotation(owner.getYaw(), owner.getPitch());
	}
	
	public static void shoot(World world, LivingEntity entity, ItemStack stack) {
		MiningProjectileEntity projectile = new MiningProjectileEntity(world, entity);
		projectile.setVelocity(entity, entity.getPitch(), entity.getYaw(), 0.0F, 2.0F, 1.0F);
		projectile.toolStack = stack.copy();
		world.spawnEntity(projectile);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {

	}

	@Override
	public void tick() {
		super.tick();
		this.spawnParticles(1);
	}

	private void spawnParticles(int amount) {
		for (int j = 0; j < amount; ++j) {
			this.getWorld().addParticle(ColoredCraftingParticleEffect.WHITE, this.getParticleX(0.5D), this.getRandomBodyY(), this.getParticleZ(0.5D), 0, 0, 0);
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		MoonstoneStrike.create(this.getWorld(), this, null, this.getX(), this.getY(), this.getZ(), 1);
		this.discard();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);

		MoonstoneStrike.create(this.getWorld(), this, null, this.getX(), this.getY(), this.getZ(), 1);

		Entity entity = getOwner();
		if (entity instanceof PlayerEntity player) {
			Predicate<BlockState> minablePredicate = state -> {
				float hardness = state.getHardness(getWorld(), blockHitResult.getBlockPos());
				if (hardness < 0) {
					return false;
				}
				boolean suitable = this.toolStack.isSuitableFor(state);
				int efficiencyLevel = SpectrumEnchantmentHelper.getLevel(getWorld().getRegistryManager(), Enchantments.EFFICIENCY, this.toolStack);
				return hardness <= 6 + (suitable ? 4 + efficiencyLevel : 0);
			};
			AoEHelper.breakBlocksAround(player, this.toolStack, blockHitResult.getBlockPos(), MINING_RANGE, minablePredicate);
		}

		this.discard();
	}
	
	@Override
	public void spawnImpactParticles() {
	}

	@Override
	public InkColor getInkColor() {
		return InkColors.WHITE;
	}

}
