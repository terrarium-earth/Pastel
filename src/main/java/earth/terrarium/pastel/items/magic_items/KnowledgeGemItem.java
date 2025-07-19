package earth.terrarium.pastel.items.magic_items;

import earth.terrarium.pastel.capabilities.ExperienceHandler;
import earth.terrarium.pastel.api.item.LoomPatternProvider;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.registries.PastelBannerPatterns;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
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

public class KnowledgeGemItem extends Item implements LoomPatternProvider {
	
	private static final int DEFAULT_MAX = 10000;
	
	public KnowledgeGemItem(Properties settings) {
		super(settings);
	}
	
	public static ItemStack getKnowledgeDropStackWithXP(int experience, boolean noStoreTooltip) {
		ItemStack stack = new ItemStack(PastelItems.KNOWLEDGE_GEM.get());
		stack.set(PastelDataComponentTypes.STORED_EXPERIENCE, experience);
		if (noStoreTooltip) stack.set(PastelDataComponentTypes.HIDE_USAGE_TOOLTIP, Unit.INSTANCE);
		return stack;
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}
	
	public int getTransferRate(HolderLookup.Provider lookup, ItemStack itemStack) {
		int quickChargeLevel = Ench.getLevel(lookup, Enchantments.QUICK_CHARGE, itemStack);
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
		if (!(user instanceof ServerPlayer player))
			return;

		var storage = stack.getCapability(PastelCapabilities.Misc.XP, world.registryAccess());

		if (storage == null)
			return;

		int available = player.totalExperience;
		int rate = getTransferRate(world.registryAccess(), stack);

		if (player.isShiftKeyDown()) {
			var offer = player.isCreative() ? rate : Math.min(rate, available);
			var inserted = offer - storage.insert(offer, true);

			if (inserted == 0)
				return;

			removePlayerExperience(player, inserted);
			storage.insert(inserted, false);

			if (remainingUseTicks % 4 == 0) {
				world.playSound(null, user.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.3F, 0.8F + world.getRandom().nextFloat() * 0.4F);
			}
		} else {
			// drain experience from gem; give to player
			var drain = storage.extract(rate, true);
			if (drain == 0)
				return;

			player.giveExperiencePoints(drain);
			if (player.isCreative())
				return;

			storage.extract(drain, false);

			if (remainingUseTicks % 4 == 0) {
				world.playSound(null, user.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.3F, 0.8F + world.getRandom().nextFloat() * 0.4F);
			}
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);

		var registries = context.registries();
		if (registries== null)
			return;

		var storage = stack.getCapability(PastelCapabilities.Misc.XP, registries);
		int maxExperience = DEFAULT_MAX;
		int storedExperience = 0;

		if (storage != null) {
			maxExperience = storage.getCapacity();
			storedExperience = storage.getStoredAmount();
		}

		if (storedExperience == 0) {
			tooltip.add(Component.literal("0 ").withStyle(ChatFormatting.DARK_GRAY).append(Component.translatable("item.pastel.knowledge_gem.tooltip.stored_experience", maxExperience).withStyle(ChatFormatting.GRAY)));
		} else {
			tooltip.add(Component.literal(storedExperience + " ").withStyle(ChatFormatting.GREEN).append(Component.translatable("item.pastel.knowledge_gem.tooltip.stored_experience", maxExperience).withStyle(ChatFormatting.GRAY)));
		}
		if (shouldDisplayUsageTooltip(stack)) {
			tooltip.add(Component.translatable("item.pastel.knowledge_gem.tooltip.use", getTransferRate(registries, stack)).withStyle(ChatFormatting.GRAY));
			addBannerPatternProviderTooltip(tooltip);
		}
	}
	
	public boolean shouldDisplayUsageTooltip(ItemStack itemStack) {
		return itemStack.has(PastelDataComponentTypes.HIDE_USAGE_TOOLTIP);
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
	
	@Override
	public ResourceKey<BannerPattern> getPattern() {
		return PastelBannerPatterns.KNOWLEDGE_GEM;
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

	public static class Wrapper implements ExperienceHandler {

		private final ItemStack holder;
		private final HolderLookup.Provider lookup;

		public Wrapper(ItemStack holder, HolderLookup.Provider lookup) {
			if (!(holder.getItem() instanceof KnowledgeGemItem))
				throw new IllegalArgumentException("Tried to make a knowledge gem wrapper for a non-knowledge gem stack");

			this.holder = holder;
			this.lookup = lookup;
		}

		private int get() {
			return holder.getOrDefault(PastelDataComponentTypes.STORED_EXPERIENCE, 0);
		}

		private void set(int amount) {
			holder.set(PastelDataComponentTypes.STORED_EXPERIENCE, amount);
		}

		@Override
		public int getStoredAmount() {
			return get();
		}

		@Override
		public int insert(int amount, boolean simulate) {
			var insertedAmount = getStoredAmount() + amount;
			var fill = Math.min(insertedAmount, getCapacity());
			var overflow = insertedAmount - fill;

			if (!simulate)
				set(fill);

			return overflow;
		}

		@Override
		public boolean extractOrFail(int amount) {
			if (extract(amount, true) == amount) {
				extract(amount, false);
				return true;
			}

			return false;
		}

		@Override
		public int extract(int amount, boolean simulate) {
			var extractAmount = getStoredAmount() - amount;
			var drain = amount + Math.min(extractAmount, 0);

			if (!simulate)
				set(getStoredAmount() - drain);

			return drain;
		}

		@Override
		public int getCapacity() {
			int efficiencyLevel = Ench.getLevel(lookup, Enchantments.EFFICIENCY, holder);
			return DEFAULT_MAX * (int) Math.pow(10, Math.min(5, efficiencyLevel)); // to not exceed int max
		}
	}
}
