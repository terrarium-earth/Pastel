package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.api.interaction.OmniAcceleratorProjectile;
import de.dafuqs.spectrum.blocks.shooting_star.ShootingStarItem;
import de.dafuqs.spectrum.entity.entity.BlockFlooderProjectile;
import de.dafuqs.spectrum.entity.entity.ShootingStarEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class SpectrumOmniAcceleratorProjectiles {
	
	public static void register() {
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				ThrownPotion potionEntity = new ThrownPotion(world, shooter);
				potionEntity.setItem(stack);
				potionEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
				world.addFreshEntity(potionEntity);
				return potionEntity;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.SPLASH_POTION_THROW;
			}
		}, Items.SPLASH_POTION);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				ThrownPotion potionEntity = new ThrownPotion(world, shooter);
				potionEntity.setItem(stack);
				potionEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
				world.addFreshEntity(potionEntity);
				return potionEntity;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.LINGERING_POTION_THROW;
			}
		}, Items.LINGERING_POTION);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				ThrownEnderpearl enderPearlEntity = new ThrownEnderpearl(world, shooter);
				enderPearlEntity.setItem(stack);
				enderPearlEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
				world.addFreshEntity(enderPearlEntity);
				return enderPearlEntity;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.ENDER_PEARL_THROW;
			}
		}, Items.ENDER_PEARL);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				if (stack.getItem() instanceof ArrowItem arrowItem) {
					AbstractArrow arrowEntity = arrowItem.createArrow(world, stack, shooter, shotFrom);
					arrowEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
					world.addFreshEntity(arrowEntity);
					return arrowEntity;
				}
				return null;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.ARROW_SHOOT;
			}
		}, ItemTags.ARROWS);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				Snowball snowballEntity = new Snowball(world, shooter);
				snowballEntity.setItem(stack);
				snowballEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
				world.addFreshEntity(snowballEntity);
				return snowballEntity;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.SNOWBALL_THROW;
			}
		}, Items.SNOWBALL);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				ThrownEgg eggEntity = new ThrownEgg(world, shooter);
				eggEntity.setItem(stack);
				eggEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
				world.addFreshEntity(eggEntity);
				return eggEntity;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.EGG_THROW;
			}
		}, Items.EGG);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				Vec3 pos = shooter.getEyePosition();
				PrimedTnt tntEntity = new PrimedTnt(world, pos.x() + 0.5, pos.y(), pos.z() + 0.5, shooter);
				OmniAcceleratorProjectile.setVelocity(tntEntity, shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
				if (world.addFreshEntity(tntEntity)) {
					world.gameEvent(shooter, GameEvent.PRIME_FUSE, pos);
					return tntEntity;
				}
				return null;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.TNT_PRIMED;
			}
		}, Items.TNT);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
            @Override
            public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
                BlockFlooderProjectile blockFlooderProjectile = new BlockFlooderProjectile(world, shooter);
                blockFlooderProjectile.setItem(stack);
                blockFlooderProjectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 2.5F, 1.0F);
                world.addFreshEntity(blockFlooderProjectile);
                return blockFlooderProjectile;
            }

            @Override
            public SoundEvent getSoundEffect() {
                return SpectrumSoundEvents.ENTITY_BLOCK_FLOODER_THROW;
            }
        }, SpectrumItems.BLOCK_FLOODER.get());
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, stack, shooter);
				fireworkRocketEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0, 2.5F, 0F);
				world.addFreshEntity(fireworkRocketEntity);
				return fireworkRocketEntity;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.FIREWORK_ROCKET_LAUNCH;
			}
		}, Items.FIREWORK_ROCKET);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				ShootingStarEntity shootingStarEntity = ((ShootingStarItem) stack.getItem()).getEntityForStack(world, shooter.getEyePosition(), stack);
				OmniAcceleratorProjectile.setVelocity(shootingStarEntity, shooter, shooter.getXRot(), shooter.getYRot(), 0, 2.5F, 0F);
				world.addFreshEntity(shootingStarEntity);
				
				shootingStarEntity.noPhysics = true;
				shootingStarEntity.move(MoverType.SELF, shootingStarEntity.getDeltaMovement()); // leave the owner
				shootingStarEntity.move(MoverType.SELF, shootingStarEntity.getDeltaMovement()); // leave the owner
				shootingStarEntity.noPhysics = false;
				
				return shootingStarEntity;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SpectrumSoundEvents.SHOOTING_STAR_CRACKER;
			}
		}, SpectrumItemTags.SHOOTING_STARS);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				Vec3 pos = shooter.getEyePosition();
				
				if (stack.getItem() instanceof BlockItem blockItem) {
					FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, pos.x() + 0.5, pos.y(), pos.z() + 0.5, blockItem.getBlock().defaultBlockState());
					OmniAcceleratorProjectile.setVelocity(fallingBlockEntity, shooter, shooter.getXRot(), shooter.getYRot(), 0, 2.5F, 0F);
					world.addFreshEntity(fallingBlockEntity);
					world.addFreshEntity(fallingBlockEntity);
					return fallingBlockEntity;
				}
				
				return null;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.ANVIL_PLACE;
			}
		}, ItemTags.ANVIL);
		
		OmniAcceleratorProjectile.register(new OmniAcceleratorProjectile() {
			@Override
			public Entity createProjectile(ItemStack stack, LivingEntity shooter, Level world, ItemStack shotFrom) {
				if (shooter instanceof Player player) {
					WindCharge windChargeEntity = new WindCharge(player, world, shooter.position().x(), shooter.getEyePosition().y(), shooter.position().z());
					windChargeEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0, 2.5F, 0F);
					world.addFreshEntity(windChargeEntity);
				}
				return null;
			}
			
			@Override
			public SoundEvent getSoundEffect() {
				return SoundEvents.WIND_CHARGE_THROW;
			}
		}, Items.WIND_CHARGE);
	}
	
}
