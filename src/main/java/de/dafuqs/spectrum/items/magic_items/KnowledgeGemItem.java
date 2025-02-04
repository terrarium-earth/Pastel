package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class KnowledgeGemItem extends Item implements ExperienceStorageItem, LoomPatternProvider {
	
	private final int maxStorageBase;
	
	// these are copies from the item model file
	// and specify the sprite used for its texture
	protected final int[] displayTiers = {1, 10, 25, 50, 100, 250, 500, 1000, 2500, 5000};
	
	public KnowledgeGemItem(Settings settings, int maxStorageBase) {
		super(settings);
		this.maxStorageBase = maxStorageBase;
	}
	
	public static ItemStack getKnowledgeDropStackWithXP(int experience, boolean noStoreTooltip) {
		ItemStack stack = new ItemStack(SpectrumItems.KNOWLEDGE_GEM);
		stack.set(SpectrumDataComponentTypes.STORED_EXPERIENCE, experience);
		if (noStoreTooltip) stack.set(SpectrumDataComponentTypes.HIDE_USAGE_TOOLTIP, Unit.INSTANCE);
		return stack;
	}
	
	@Override
	public int getMaxStoredExperience(RegistryWrapper.WrapperLookup lookup, ItemStack itemStack) {
		int efficiencyLevel = SpectrumEnchantmentHelper.getLevel(lookup, Enchantments.EFFICIENCY, itemStack);
		return maxStorageBase * (int) Math.pow(10, Math.min(5, efficiencyLevel)); // to not exceed int max
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
	
	public int getTransferableExperiencePerTick(RegistryWrapper.WrapperLookup lookup, ItemStack itemStack) {
		int quickChargeLevel = SpectrumEnchantmentHelper.getLevel(lookup, Enchantments.QUICK_CHARGE, itemStack);
		return (int) (2 * Math.pow(2, Math.min(10, quickChargeLevel)));
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			
			int playerExperience = serverPlayerEntity.totalExperience;
			int itemExperience = ExperienceStorageItem.getStoredExperience(stack);
			int transferableExperience = getTransferableExperiencePerTick(world.getRegistryManager(), stack);
			
			if (serverPlayerEntity.isSneaking()) {
				int maxStorage = getMaxStoredExperience(world.getRegistryManager(), stack);
				int experienceToTransfer = serverPlayerEntity.isCreative() ? Math.min(transferableExperience, maxStorage - itemExperience) : Math.min(Math.min(transferableExperience, playerExperience), maxStorage - itemExperience);
				
				// store experience in gem; drain from player
				if (experienceToTransfer > 0 && itemExperience < maxStorage && removePlayerExperience(serverPlayerEntity, experienceToTransfer)) {
					ExperienceStorageItem.addStoredExperience(world.getRegistryManager(), stack, experienceToTransfer);
					
					if (remainingUseTicks % 4 == 0) {
						world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.3F, 0.8F + world.getRandom().nextFloat() * 0.4F);
					}
				}
			} else {
				// drain experience from gem; give to player
				if (itemExperience > 0 && playerExperience != Integer.MAX_VALUE) {
					int experienceToTransfer = Math.min(Math.min(transferableExperience, itemExperience), Integer.MAX_VALUE - playerExperience);
					
					if (experienceToTransfer > 0) {
						if (!serverPlayerEntity.isCreative()) {
							serverPlayerEntity.addExperience(experienceToTransfer);
						}
						ExperienceStorageItem.removeStoredExperience(stack, experienceToTransfer);
						
						if (remainingUseTicks % 4 == 0) {
							world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.3F, 0.8F + world.getRandom().nextFloat() * 0.4F);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		
		SpectrumCommon.getRegistryLookup().ifPresent(lookup -> {
			int maxExperience = getMaxStoredExperience(lookup, stack);
			int storedExperience = ExperienceStorageItem.getStoredExperience(stack);
			if (storedExperience == 0) {
				tooltip.add(Text.literal("0 ").formatted(Formatting.DARK_GRAY).append(Text.translatable("item.spectrum.knowledge_gem.tooltip.stored_experience", maxExperience).formatted(Formatting.GRAY)));
			} else {
				tooltip.add(Text.literal(storedExperience + " ").formatted(Formatting.GREEN).append(Text.translatable("item.spectrum.knowledge_gem.tooltip.stored_experience", maxExperience).formatted(Formatting.GRAY)));
			}
			if (shouldDisplayUsageTooltip(stack)) {
				tooltip.add(Text.translatable("item.spectrum.knowledge_gem.tooltip.use", getTransferableExperiencePerTick(lookup, stack)).formatted(Formatting.GRAY));
				addBannerPatternProviderTooltip(tooltip);
			}
		});
		
	}
	
	public boolean shouldDisplayUsageTooltip(ItemStack itemStack) {
		return itemStack.contains(SpectrumDataComponentTypes.HIDE_USAGE_TOOLTIP);
	}
	
	public boolean removePlayerExperience(@NotNull PlayerEntity playerEntity, int experience) {
		if (playerEntity.isCreative()) {
			return true;
		} else if (playerEntity.totalExperience < experience) {
			return false;
		} else {
			playerEntity.addExperience(-experience);
			return true;
		}
	}
	
	public boolean changedDisplayTier(int currentStoredExperience, int destinationStoredExperience) {
		return getDisplayTierForExperience(currentStoredExperience) != getDisplayTierForExperience(destinationStoredExperience);
	}
	
	public int getDisplayTierForExperience(int experience) {
		for (int i = 0; i < displayTiers.length; i++) {
			if (experience < displayTiers[i]) {
				return i;
			}
		}
		return displayTiers.length;
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.KNOWLEDGE_GEM;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantability() {
		return 5;
	}

//	@Override
//	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
//		super.appendStacks(group, stacks);
//		if (this.isIn(group)) {
//			ItemStack stack = getDefaultStack();
//			ExperienceStorageItem.addStoredExperience(stack, getMaxStoredExperience(stack));
//			stacks.add(stack);
//
//			ItemStack enchantedStack = SpectrumEnchantmentHelper.getMaxEnchantedStack(this);
//			ExperienceStorageItem.addStoredExperience(enchantedStack, getMaxStoredExperience(stack));
//			stacks.add(enchantedStack);
//		}
//	}

}
