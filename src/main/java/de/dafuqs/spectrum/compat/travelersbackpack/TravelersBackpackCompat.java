package de.dafuqs.spectrum.compat.travelersbackpack;

import com.tiviacz.travelersbackpack.api.fluids.EffectFluid;
import com.tiviacz.travelersbackpack.fluids.EffectFluidRegistry;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.registries.SpectrumDamageTypes;
import de.dafuqs.spectrum.registries.SpectrumFluids;
import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TravelersBackpackCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public abstract static class SpectrumEffectFluid extends EffectFluid {
		
		public SpectrumEffectFluid(String id, Fluid fluid) {
			super(id, fluid, 81000);
		}
		
		public boolean canExecuteEffect(FluidStack stack, Level world, Entity entity) {
			return stack.getAmount() >= this.amountRequired;
		}
		
	}
	
	@Override
	public void register() {
		EffectFluidRegistry.registerFluidEffect(new SpectrumEffectFluid("spectrum:goo", SpectrumFluids.GOO.getSource()) {
			@Override
			public void affectDrinker(FluidStack fluidVariantWrapper, Level world, Entity entity) {
				if (entity instanceof LivingEntity livingEntity) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
					livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2));
					livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 3));
				}
			}
			
		});
		
		EffectFluidRegistry.registerFluidEffect(new SpectrumEffectFluid("spectrum:liquid_crystal", SpectrumFluids.LIQUID_CRYSTAL.getSource()) {
			@Override
			public void affectDrinker(FluidStack fluidStack, Level world, Entity entity) {
				if (entity instanceof Player player) {
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
				}
			}
		});
		
		EffectFluidRegistry.registerFluidEffect(new SpectrumEffectFluid("spectrum:midnight_solution", SpectrumFluids.MIDNIGHT_SOLUTION.getSource()) {
			@Override
			public void affectDrinker(FluidStack fluidStack, Level world, Entity entity) {
				if (entity instanceof Player player) {
					player.giveExperiencePoints(-20);
					
					// disenchant random enchanted item
					List<ItemStack> equipment = new ArrayList<>();
					player.getAllSlots().forEach(equipment::add);
					Collections.shuffle(equipment);
					
					for (ItemStack equip : equipment) {
						ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(equip);
						if (!enchants.isEmpty()) {
							var enchantments = enchants.keySet();
							var enchantment = enchantments.stream().toList().get(new Random().nextInt(enchantments.size()));
							SpectrumEnchantmentHelper.removeEnchantments(equip, enchantment);
						}
					}
				}
			}
		});
		
		EffectFluidRegistry.registerFluidEffect(new SpectrumEffectFluid("spectrum:dragonrot", SpectrumFluids.DRAGONROT.getSource()) {
			@Override
			public void affectDrinker(FluidStack fluidStack, Level world, Entity entity) {
				if (entity instanceof LivingEntity livingEntity) {
					livingEntity.addEffect(new MobEffectInstance(SpectrumStatusEffects.LIFE_DRAIN, 600, 3));
					livingEntity.hurt(SpectrumDamageTypes.dragonrot(world), 1000); // 💀
				}
			}
		});
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerClient() {
	
	}
	
}
