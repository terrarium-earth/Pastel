package earth.terrarium.pastel.compat.travelersbackpack;

import com.tiviacz.travelersbackpack.api.fluids.EffectFluid;
import com.tiviacz.travelersbackpack.fluids.EffectFluidRegistry;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.helpers.PastelEnchantmentHelper;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.PastelStatusEffects;
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

public class TravelersBackpackCompat extends PastelIntegrationPacks.ModIntegrationPack {

	public abstract static class PastelEffectFluid extends EffectFluid {

		public PastelEffectFluid(String id, Fluid fluid) {
			super(id, fluid, 81000);
		}

		public boolean canExecuteEffect(FluidStack stack, Level world, Entity entity) {
			return stack.getAmount() >= this.amountRequired;
		}

	}

	@Override
	public void register() {
		EffectFluidRegistry.registerFluidEffect(new PastelEffectFluid("pastel:humus", PastelFluids.HUMUS.get().getSource()) {
			@Override
			public void affectDrinker(FluidStack fluidVariantWrapper, Level world, Entity entity) {
				if (entity instanceof LivingEntity livingEntity) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
					livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2));
					livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 3));
				}
			}

		});

		EffectFluidRegistry.registerFluidEffect(new PastelEffectFluid("pastel:liquid_crystal", PastelFluids.LIQUID_CRYSTAL.get().getSource()) {
			@Override
			public void affectDrinker(FluidStack fluidStack, Level world, Entity entity) {
				if (entity instanceof Player player) {
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
				}
			}
		});

		EffectFluidRegistry.registerFluidEffect(new PastelEffectFluid("pastel:midnight_solution", PastelFluids.MIDNIGHT_SOLUTION.get().getSource()) {
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
							PastelEnchantmentHelper.removeEnchantments(equip, enchantment);
						}
					}
				}
			}
		});

		EffectFluidRegistry.registerFluidEffect(new PastelEffectFluid("pastel:dragonrot", PastelFluids.DRAGONROT.get().getSource()) {
			@Override
			public void affectDrinker(FluidStack fluidStack, Level world, Entity entity) {
				if (entity instanceof LivingEntity livingEntity) {
					livingEntity.addEffect(new MobEffectInstance(PastelStatusEffects.LIFE_DRAIN, 600, 3));
					livingEntity.hurt(PastelDamageTypes.dragonrot(world), 1000); // 💀
				}
			}
		});
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerClient() {

	}

}
