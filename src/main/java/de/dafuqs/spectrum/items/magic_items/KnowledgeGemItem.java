package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import de.dafuqs.spectrum.api.item.LoomPatternProvider;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.registries.SpectrumBannerPatterns;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KnowledgeGemItem extends Item implements ExperienceStorageItem, LoomPatternProvider {
	
	private final int maxStorageBase;
	
	// these are copies from the item model file
	// and specify the sprite used for its texture
	protected final int[] displayTiers = {1, 10, 25, 50, 100, 250, 500, 1000, 2500, 5000};
	
	public KnowledgeGemItem(Properties settings, int maxStorageBase) {
		super(settings);
		this.maxStorageBase = maxStorageBase;
	}
	
	public static ItemStack getKnowledgeDropStackWithXP(int experience, boolean noStoreTooltip) {
		ItemStack stack = new ItemStack(SpectrumItems.KNOWLEDGE_GEM.get());
		stack.set(SpectrumDataComponentTypes.STORED_EXPERIENCE, experience);
		if (noStoreTooltip) stack.set(SpectrumDataComponentTypes.HIDE_USAGE_TOOLTIP, Unit.INSTANCE);
		return stack;
	}
	
	@Override
	public int getMaxStoredExperience(HolderLookup.Provider lookup, ItemStack itemStack) {
		int efficiencyLevel = SpectrumEnchantmentHelper.getLevel(lookup, Enchantments.EFFICIENCY, itemStack);
		return maxStorageBase * (int) Math.pow(10, Math.min(5, efficiencyLevel)); // to not exceed int max
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}
	
	public int getTransferableExperiencePerTick(HolderLookup.Provider lookup, ItemStack itemStack) {
		int quickChargeLevel = SpectrumEnchantmentHelper.getLevel(lookup, Enchantments.QUICK_CHARGE, itemStack);
		return (int) (2 * Math.pow(2, Math.min(10, quickChargeLevel)));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(world, user, hand);
	}
	
	@Override
	public int getUseDuration(ItemStack stack, LivingEntity user) {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.onUseTick(world, user, stack, remainingUseTicks);
		if (user instanceof ServerPlayer serverPlayerEntity) {
			
			int playerExperience = serverPlayerEntity.totalExperience;
			int itemExperience = ExperienceStorageItem.getStoredExperience(stack);
			int transferableExperience = getTransferableExperiencePerTick(world.registryAccess(), stack);
			
			if (serverPlayerEntity.isShiftKeyDown()) {
				int maxStorage = getMaxStoredExperience(world.registryAccess(), stack);
				int experienceToTransfer = serverPlayerEntity.isCreative() ? Math.min(transferableExperience, maxStorage - itemExperience) : Math.min(Math.min(transferableExperience, playerExperience), maxStorage - itemExperience);
				
				// store experience in gem; drain from player
				if (experienceToTransfer > 0 && itemExperience < maxStorage && removePlayerExperience(serverPlayerEntity, experienceToTransfer)) {
					ExperienceStorageItem.addStoredExperience(world.registryAccess(), stack, experienceToTransfer);
					
					if (remainingUseTicks % 4 == 0) {
						world.playSound(null, user.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.3F, 0.8F + world.getRandom().nextFloat() * 0.4F);
					}
				}
			} else {
				// drain experience from gem; give to player
				if (itemExperience > 0 && playerExperience != Integer.MAX_VALUE) {
					int experienceToTransfer = Math.min(Math.min(transferableExperience, itemExperience), Integer.MAX_VALUE - playerExperience);
					
					if (experienceToTransfer > 0) {
						if (!serverPlayerEntity.isCreative()) {
							serverPlayerEntity.giveExperiencePoints(experienceToTransfer);
						}
						ExperienceStorageItem.removeStoredExperience(stack, experienceToTransfer);
						
						if (remainingUseTicks % 4 == 0) {
							world.playSound(null, user.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.3F, 0.8F + world.getRandom().nextFloat() * 0.4F);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		
		HolderLookup.Provider lookup = context.registries();
		int maxExperience = getMaxStoredExperience(lookup, stack);
		int storedExperience = ExperienceStorageItem.getStoredExperience(stack);
		if (storedExperience == 0) {
			tooltip.add(Component.literal("0 ").withStyle(ChatFormatting.DARK_GRAY).append(Component.translatable("item.pastel.knowledge_gem.tooltip.stored_experience", maxExperience).withStyle(ChatFormatting.GRAY)));
		} else {
			tooltip.add(Component.literal(storedExperience + " ").withStyle(ChatFormatting.GREEN).append(Component.translatable("item.pastel.knowledge_gem.tooltip.stored_experience", maxExperience).withStyle(ChatFormatting.GRAY)));
		}
		if (shouldDisplayUsageTooltip(stack)) {
			tooltip.add(Component.translatable("item.pastel.knowledge_gem.tooltip.use", getTransferableExperiencePerTick(lookup, stack)).withStyle(ChatFormatting.GRAY));
			addBannerPatternProviderTooltip(tooltip);
		}
	}
	
	public boolean shouldDisplayUsageTooltip(ItemStack itemStack) {
		return itemStack.has(SpectrumDataComponentTypes.HIDE_USAGE_TOOLTIP);
	}
	
	public boolean removePlayerExperience(@NotNull Player playerEntity, int experience) {
		if (playerEntity.isCreative()) {
			return true;
		} else if (playerEntity.totalExperience < experience) {
			return false;
		} else {
			playerEntity.giveExperiencePoints(-experience);
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
	public ResourceKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.KNOWLEDGE_GEM;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantmentValue() {
		return 5;
	}

	@Override
	public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.EFFICIENCY) || enchantment.is(Enchantments.QUICK_CHARGE);
	}

}
