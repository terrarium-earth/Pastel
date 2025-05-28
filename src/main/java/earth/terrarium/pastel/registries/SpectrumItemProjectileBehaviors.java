package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.interaction.ItemProjectileBehavior;
import earth.terrarium.pastel.api.interaction.OmniAcceleratorProjectile;
import earth.terrarium.pastel.api.item.ExperienceStorageItem;
import earth.terrarium.pastel.blocks.amalgam.IncandescentAmalgamBlock;
import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.entity.entity.ItemProjectileEntity;
import earth.terrarium.pastel.items.magic_items.CraftingTabletItem;
import earth.terrarium.pastel.items.magic_items.EnchantmentCanvasItem;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.items.magic_items.PipeBombItem;
import earth.terrarium.pastel.items.magic_items.ampoules.GlassAmpouleItem;
import earth.terrarium.pastel.items.tools.OmniAcceleratorItem;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpectrumItemProjectileBehaviors {
	
	public static void register() {
		registerHarmless();
		if (SpectrumCommon.CONFIG.OmniAcceleratorPvP) {
			registerPvP();
		}
	}
	
	protected static void registerHarmless() {
		ItemProjectileBehavior.register(new ItemProjectileBehavior() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, Entity owner, EntityHitResult hitResult) {
				if (strikeLightning(hitResult.getEntity().level(), hitResult.getEntity().blockPosition())) {
					stack.shrink(1);
				}
				return stack;
			}

			@Override
			public ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, Entity owner, BlockHitResult hitResult) {
				if (strikeLightning(projectile.level(), hitResult.getBlockPos())) {
					stack.shrink(1);
				}
				return stack;
			}

			private boolean strikeLightning(Level world, BlockPos pos) {
				if (world.canSeeSky(pos.above())) {
					LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
					if (lightningEntity != null) {
						lightningEntity.moveTo(Vec3.atBottomCenterOf(pos));
						world.addFreshEntity(lightningEntity);
						return true;
					}
				}
				return false;
			}
		}, SpectrumItems.STORM_STONE.get());
		
		ItemProjectileBehavior.register(ItemProjectileBehavior.damaging(4F, true), SpectrumItemTags.GEMSTONE_SHARDS);
		ItemProjectileBehavior.register(ItemProjectileBehavior.damaging(6F, true), Items.POINTED_DRIPSTONE);
		ItemProjectileBehavior.register(ItemProjectileBehavior.damaging(6F, true), Items.END_ROD);
		ItemProjectileBehavior.register(ItemProjectileBehavior.damaging(6F, true), Items.BLAZE_ROD);
		ItemProjectileBehavior.register(ItemProjectileBehavior.damaging(8F, true), SpectrumItems.STAR_FRAGMENT.get());
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Damaging() {
			
			@Override
			public boolean destroyItemOnHit() {
				return false;
			}
			
			@Override
			public boolean dealDamage(ThrowableItemProjectile projectile, Entity owner, Entity target) {
				return target.hurt(target.damageSources().thrown(projectile, owner), 6F);
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
		}, Tags.Items.MUSIC_DISCS);
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				Entity entity = hitResult.getEntity();
				if (!entity.fireImmune()) {
					entity.igniteForSeconds(15);
					if (entity.hurt(entity.damageSources().inFire(), 4.0F)) {
						entity.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + entity.level().getRandom().nextFloat() * 0.4F);
					}
					stack.shrink(1);
				}
				return stack;
			}
		}, Items.FIRE_CHARGE);
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				IncandescentAmalgamBlock.explode(projectile.level(), BlockPos.containing(hitResult.getLocation()), owner, stack);
				stack.shrink(1);
				return stack;
			}
			
			@Override
			public ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, BlockHitResult hitResult) {
				IncandescentAmalgamBlock.explode(projectile.level(), BlockPos.containing(hitResult.getLocation()), owner, stack);
				stack.shrink(1);
				return stack;
			}
		}, SpectrumBlocks.INCANDESCENT_AMALGAM.get().asItem());
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				return stack;
			}

			@Override
			public ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack accelerator, @Nullable Entity owner, BlockHitResult hitResult) {
				Optional<ItemStack> optionalAcceleratorContentStack = OmniAcceleratorItem.getFirstStack(projectile.level().registryAccess(), accelerator);
				if (optionalAcceleratorContentStack.isPresent() && owner instanceof LivingEntity livingOwner) {
					ItemStack acceleratorContentStack = optionalAcceleratorContentStack.get();

					Level world = projectile.level();
					OmniAcceleratorProjectile newProjectile = OmniAcceleratorProjectile.get(optionalAcceleratorContentStack.get());
					Entity newEntity = newProjectile.createProjectile(acceleratorContentStack, livingOwner, world, accelerator);

					if (newEntity != null) {
						Vec3 pos = hitResult.getLocation();
						newEntity.setPosRaw(pos.x(), pos.y(), pos.z());
						OmniAcceleratorProjectile.setVelocity(newEntity, projectile, 20, world.getRandom().nextFloat() * 360, 0.0F, 2.0F, 1.0F);
						world.playSound(null, pos.x(), pos.y(), pos.z(), newProjectile.getSoundEffect(), SoundSource.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
						OmniAcceleratorItem.decrementFirstItem(accelerator);
					}
				}
				return accelerator;
			}
		}, SpectrumItems.OMNI_ACCELERATOR.get());
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				Entity target = hitResult.getEntity();
				if (target instanceof LivingEntity livingTarget) {
					livingTarget.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 0));
					livingTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
				}
				stack.shrink(1);
				return stack;
			}
		}, Items.CAKE);
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				if (MemoryBlockEntity.manifest((ServerLevel) projectile.level(), hitResult.getEntity().blockPosition(), stack, owner == null ? null : owner.getUUID())) {
					stack.shrink(1);
				}
				return stack;
			}
			
			@Override
			public ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, BlockHitResult hitResult) {
				if (MemoryBlockEntity.manifest((ServerLevel) projectile.level(), hitResult.getBlockPos().relative(hitResult.getDirection()), stack, owner == null ? null : owner.getUUID())) {
					stack.shrink(1);
				}
				return stack;
			}
			
		}, SpectrumBlocks.MEMORY.get().asItem());
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				PipeBombItem.prime(stack, projectile.level(), projectile.position(), owner);
				return stack;
			}

			@Override
			public ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, BlockHitResult hitResult) {
				PipeBombItem.prime(stack, projectile.level(), projectile.position(), owner);
				return stack;
			}

		}, SpectrumItems.PIPE_BOMB.get());
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				if (projectile.getOwner() instanceof LivingEntity livingOwner && hitResult.getEntity() instanceof LivingEntity livingTarget && ((GlassAmpouleItem) stack.getItem()).trigger(projectile.level(), stack, livingOwner, livingTarget, hitResult.getLocation())) {
					stack.shrink(1);
				}
				return stack;
			}

			@Override
			public ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, BlockHitResult hitResult) {
				if (projectile.getOwner() instanceof LivingEntity livingOwner && ((GlassAmpouleItem) stack.getItem()).trigger(projectile.level(), stack, livingOwner, null, hitResult.getLocation())) {
					stack.shrink(1);
				}
				return stack;
			}

		}, SpectrumItems.AZURITE_GLASS_AMPOULE.get(), SpectrumItems.MALACHITE_GLASS_AMPOULE.get(), SpectrumItems.BLOODSTONE_GLASS_AMPOULE.get());
	}
	
	protected static void registerPvP() {
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				if (hitResult.getEntity() instanceof LivingEntity livingTarget) {
					List<ItemStack> equipment = new ArrayList<>();
					livingTarget.getAllSlots().forEach(equipment::add);
					Collections.shuffle(equipment);

					for (ItemStack equip : equipment) {
						if (EnchantmentCanvasItem.tryExchangeEnchantments(stack, equip, livingTarget)) {
							return stack;
						}
					}
				}
				return stack;
			}
		}, SpectrumItems.ENCHANTMENT_CANVAS.get());
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				if (hitResult.getEntity() instanceof Player target) {
					int playerExperience = target.totalExperience;
					if (playerExperience > 0) {
						KnowledgeGemItem item = (KnowledgeGemItem) stack.getItem();
						long transferableExperiencePerTick = item.getTransferableExperiencePerTick(target.level().registryAccess(), stack);
						int xpToTransfer = (int) Math.min(target.totalExperience, transferableExperiencePerTick * 100);
						int experienceOverflow = ExperienceStorageItem.addStoredExperience(target.level().registryAccess(), stack, xpToTransfer);

						target.giveExperiencePoints(-xpToTransfer + experienceOverflow);
						target.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.3F, 0.8F + target.level().getRandom().nextFloat() * 0.4F);
						return stack;
					}
				}
				return stack;
			}
		}, SpectrumItems.KNOWLEDGE_GEM.get());
		
		ItemProjectileBehavior.register(new ItemProjectileBehavior.Default() {
			@Override
			public ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult) {
				var recipe = CraftingTabletItem.getStoredRecipe(projectile.level(), stack).value();
				if (recipe instanceof CraftingRecipe craftingRecipe && hitResult.getEntity() instanceof ServerPlayer target) {
					CraftingTabletItem.tryCraftRecipe(target, craftingRecipe, projectile.level());
				}
				return stack;
			}
		}, SpectrumItems.CRAFTING_TABLET.get());
	}
	
}
