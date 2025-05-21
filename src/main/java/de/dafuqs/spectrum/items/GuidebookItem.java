package de.dafuqs.spectrum.items;

import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.client.gui.book.BookAddress;
import de.dafuqs.revelationary.advancement_criteria.AdvancementGottenCriterion;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.LoomPatternProvider;
import de.dafuqs.spectrum.registries.SpectrumBannerPatterns;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.server.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GuidebookItem extends Item implements LoomPatternProvider {
	
	public static final ResourceLocation GUIDEBOOK_ID = SpectrumCommon.locate("guidebook");
	public static final BookAddress GUIDEBOOK_ADDRESS = BookAddress.defaultFor(GUIDEBOOK_ID);
	
	public static final ResourceLocation CUISINE_CATEGORY_ID = SpectrumCommon.locate("cuisine");
	public static final ResourceLocation DIMENSION_CATEGORY_ID = SpectrumCommon.locate("dimension");
	
	
	public static BookAddress addressOf(ResourceLocation category, ResourceLocation entryId) {
		return BookAddress.of(GUIDEBOOK_ID, category, entryId, 0);
	}
	
	public GuidebookItem(Properties settings) {
		super(settings);
	}
	
	
	private static final Set<UUID> alreadyReprocessedPlayers = new HashSet<>();
	
	public static void reprocessAdvancementUnlocks(ServerPlayer serverPlayerEntity) {
		if (serverPlayerEntity.getServer() == null || ServerLifecycleHooks.getCurrentServer() == null) {
			return;
		}
		
		UUID uuid = serverPlayerEntity.getUUID();
		if (alreadyReprocessedPlayers.contains(uuid)) {
			return;
		}
		alreadyReprocessedPlayers.add(uuid);
		
		PlayerAdvancements tracker = serverPlayerEntity.getAdvancements();
		
		for (var advancement : serverPlayerEntity.getServer().getAdvancements().getAllAdvancements()) {
			var hasAdvancement = tracker.getOrStartProgress(advancement);
			if (!hasAdvancement.isDone()) {
				for (var criterionEntry : advancement.value().criteria().entrySet()) {
					var conditions = criterionEntry.getValue().triggerInstance();
					if (conditions instanceof AdvancementGottenCriterion.Conditions hasAdvancementConditions) {
						var advancementCriterionAdvancement = ServerLifecycleHooks.getCurrentServer().getAdvancements().get(hasAdvancementConditions.getAdvancementIdentifier());
						if (advancementCriterionAdvancement != null) {
							var hasAdvancementCriterionAdvancement = tracker.getOrStartProgress(advancementCriterionAdvancement);
							if (hasAdvancementCriterionAdvancement.isDone()) {
								tracker.award(advancement, criterionEntry.getKey());
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (world.isClientSide()) {
			// if the player has never opened the book before
			// automatically open the introduction page
			openGuidebook();
		} else if (user instanceof ServerPlayer serverPlayerEntity) {
			// Process new advancement unlocks that got added
			// after spectrum has been installed / updated
			reprocessAdvancementUnlocks(serverPlayerEntity);
		}
		user.awardStat(Stats.ITEM_USED.get(this));
		
		return InteractionResultHolder.sidedSuccess(user.getItemInHand(hand), world.isClientSide);
	}
	
	public void openGuidebook() {
		BookGuiManager.get().openBook(GUIDEBOOK_ADDRESS);
	}
	
	public void openGuidebook(BookAddress address) {
		BookGuiManager.get().openBook(address);
	}
	
	@Override
	public ResourceKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.GUIDEBOOK;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}
