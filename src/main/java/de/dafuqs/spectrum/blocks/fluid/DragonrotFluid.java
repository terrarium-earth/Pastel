package de.dafuqs.spectrum.blocks.fluid;

import de.dafuqs.spectrum.injectors.StatusEffectInstanceInjector;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.recipe.fluid_converting.FluidConvertingRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumDamageTypes;
import de.dafuqs.spectrum.registries.SpectrumEntityTypeTags;
import de.dafuqs.spectrum.registries.SpectrumFluidTags;
import de.dafuqs.spectrum.registries.SpectrumFluids;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeTypes;
import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class DragonrotFluid extends SpectrumFluid {

	@Override
	public Fluid getSource() {
		return SpectrumFluids.DRAGONROT.get();
	}
	
	@Override
	public Fluid getFlowing() {
		return SpectrumFluids.FLOWING_DRAGONROT.get();
	}
	
	@Override
	public Item getBucket() {
		return SpectrumItems.DRAGONROT_BUCKET;
	}
	
	@Override
	protected BlockState createLegacyBlock(FluidState fluidState) {
		return SpectrumBlocks.DRAGONROT.get().defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(fluidState));
	}
	
	@Override
	public boolean isSame(Fluid fluid) {
		return fluid == SpectrumFluids.DRAGONROT.get() || fluid == SpectrumFluids.FLOWING_DRAGONROT.get();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
		BlockPos topPos = pos.above();
		BlockState topState = world.getBlockState(topPos);
		if (topState.isAir() && random.nextInt(3) == 0) {
			float soundRandom = random.nextFloat();
			if (soundRandom < 0.0003F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.HONEY_DRINK, SoundSource.AMBIENT, random.nextFloat() * 0.65F + 0.25F, random.nextFloat() * 0.2F, false);
			}else if (soundRandom < 0.0006F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.HONEY_BLOCK_SLIDE, SoundSource.AMBIENT, random.nextFloat() * 0.4F + 0.25F, random.nextFloat() * 0.5F + 0.1F, false);
			} else if (soundRandom < 0.0008F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FROG_AMBIENT, SoundSource.AMBIENT, random.nextFloat() + 0.25F, random.nextFloat() * 0.3F + 0.01F, false);
			} else if (soundRandom < 0.001F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SCULK_BLOCK_PLACE, SoundSource.AMBIENT, random.nextFloat() + 0.25F, random.nextFloat() * 0.4F + 0.2F, false);
			}  else if (soundRandom < 0.00148F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PARROT_DEATH, SoundSource.AMBIENT, random.nextFloat() * 0.334F + 0.1F, 1F, false);
			} else if (soundRandom < 0.00152F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.CAT_DEATH, SoundSource.AMBIENT, random.nextFloat() * 0.334F + 0.1F, 1F, false);
			} else if (soundRandom < 0.00156F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOLF_DEATH, SoundSource.AMBIENT, random.nextFloat() * 0.3F + 0.1F, 1F, false);
			} else if (soundRandom < 0.001564F) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.AMBIENT, 2F, 0.1F, false);
			}
		}
	}
	
	@Override
	protected int getDropOff(LevelReader worldView) {
		return 3;
	}
	
	@Override
	public int getTickDelay(LevelReader worldView) {
		return 40;
	}
	
	@Override
	public ParticleOptions getDripParticle() {
		return SpectrumParticleTypes.DRIPPING_DRAGONROT;
	}
	
	@Override
	public ParticleOptions getSplashParticle() {
		return SpectrumParticleTypes.DRAGONROT_SPLASH;
	}
	
	
	@Override
	public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		
		if (world instanceof ServerLevel serverWorld && entity instanceof LivingEntity livingEntity) {
			// just check every 20 ticks for performance
			if (!livingEntity.isDeadOrDying() && world.getGameTime() % 20 == 0 && !(livingEntity instanceof Enemy)) {
				var dragon = entity.getType().is(SpectrumEntityTypeTags.DRACONIC);
				var damage = dragon ? 30 : 6;
				var ticks = dragon ? 20 : 5;
				var cut = dragon ? 100 : 40;

				if (livingEntity.isEyeInFluid(SpectrumFluidTags.DRAGONROT)) {
					livingEntity.hurt(SpectrumDamageTypes.dragonrot(world), damage);
				} else {
					livingEntity.hurt(SpectrumDamageTypes.dragonrot(world), damage / 2F);
				}
				if (!livingEntity.isDeadOrDying()) {
					MobEffectInstance existingEffect = livingEntity.getEffect(SpectrumStatusEffects.LIFE_DRAIN);
					if (existingEffect == null) {
						livingEntity.addEffect(new MobEffectInstance(SpectrumStatusEffects.LIFE_DRAIN, 600, 0));
					}
					else if(existingEffect.getDuration() < 500) {
						((StatusEffectInstanceInjector) existingEffect).spectrum$setDuration(300);

						serverWorld.getChunkSource().broadcastAndSend(livingEntity, new ClientboundUpdateMobEffectPacket(livingEntity.getId(), existingEffect, true));
					}

					existingEffect = livingEntity.getEffect(SpectrumStatusEffects.DEADLY_POISON);
					if (existingEffect == null || existingEffect.getDuration() < 80) {
						livingEntity.addEffect(new MobEffectInstance(SpectrumStatusEffects.DEADLY_POISON, 160, 0));
					}

					existingEffect = livingEntity.getEffect(SpectrumStatusEffects.IMMUNITY);
					if (existingEffect != null) {
						if (existingEffect.getDuration() <= cut) {
							livingEntity.removeEffect(SpectrumStatusEffects.IMMUNITY);
						} else {
							((StatusEffectInstanceInjector) existingEffect).spectrum$setDuration(existingEffect.getDuration() - cut);
							serverWorld.getChunkSource().broadcastAndSend(livingEntity, new ClientboundUpdateMobEffectPacket(livingEntity.getId(), existingEffect, true));
						}
					}

					if (!dragon)
						return;

					existingEffect = livingEntity.getEffect(SpectrumStatusEffects.DENSITY);
					if (existingEffect == null || existingEffect.getDuration() < 120) {
						livingEntity.addEffect(new MobEffectInstance(SpectrumStatusEffects.DENSITY, 2000, 1));
					}
				}
			}
		}
	}
	
	@Override
	public RecipeType<? extends FluidConvertingRecipe> getDippingRecipeType() {
		return SpectrumRecipeTypes.DRAGONROT_CONVERTING;
	}
	
	public static class Flowing extends DragonrotFluid {
		
		@Override
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}
		
		@Override
		public int getAmount(FluidState fluidState) {
			return fluidState.getValue(LEVEL);
		}
		
		@Override
		public boolean isSource(FluidState fluidState) {
			return false;
		}
		
	}
	
	public static class Still extends DragonrotFluid {
		
		@Override
		public int getAmount(FluidState fluidState) {
			return 8;
		}
		
		@Override
		public boolean isSource(FluidState fluidState) {
			return true;
		}
		
	}
}