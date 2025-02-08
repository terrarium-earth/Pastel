package de.dafuqs.spectrum.items;

import com.klikli_dev.modonomicon.client.gui.*;
import com.klikli_dev.modonomicon.client.gui.book.*;
import de.dafuqs.revelationary.advancement_criteria.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.advancement.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class GuidebookItem extends Item implements LoomPatternProvider {
	
	public static final Identifier GUIDEBOOK_ID = SpectrumCommon.locate("guidebook");
	public static final BookAddress GUIDEBOOK_ADDRESS = BookAddress.defaultFor(GUIDEBOOK_ID);
	
	public static final Identifier CUISINE_CATEGORY_ID = SpectrumCommon.locate("cuisine");
	public static final Identifier DIMENSION_CATEGORY_ID = SpectrumCommon.locate("dimension");
	
	
	public static BookAddress addressOf(Identifier category, Identifier entryId) {
		return BookAddress.of(GUIDEBOOK_ID, category, entryId, 0);
	}
	
	public GuidebookItem(Settings settings) {
		super(settings);
	}
	
	
	private static final Set<UUID> alreadyReprocessedPlayers = new HashSet<>();
	
	public static void reprocessAdvancementUnlocks(ServerPlayerEntity serverPlayerEntity) {
		if (serverPlayerEntity.getServer() == null || SpectrumCommon.minecraftServer == null) {
			return;
		}
		
		UUID uuid = serverPlayerEntity.getUuid();
		if (alreadyReprocessedPlayers.contains(uuid)) {
			return;
		}
		alreadyReprocessedPlayers.add(uuid);
		
		PlayerAdvancementTracker tracker = serverPlayerEntity.getAdvancementTracker();
		
		for (var advancement : serverPlayerEntity.getServer().getAdvancementLoader().getAdvancements()) {
			var hasAdvancement = tracker.getProgress(advancement);
			if (!hasAdvancement.isDone()) {
				for (var criterionEntry : advancement.value().criteria().entrySet()) {
					var conditions = criterionEntry.getValue().conditions();
					if (conditions instanceof AdvancementGottenCriterion.Conditions hasAdvancementConditions) {
						var advancementCriterionAdvancement = SpectrumCommon.minecraftServer.getAdvancementLoader().get(hasAdvancementConditions.getAdvancementIdentifier());
						if (advancementCriterionAdvancement != null) {
							var hasAdvancementCriterionAdvancement = tracker.getProgress(advancementCriterionAdvancement);
							if (hasAdvancementCriterionAdvancement.isDone()) {
								tracker.grantCriterion(advancement, criterionEntry.getKey());
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient()) {
			// if the player has never opened the book before
			// automatically open the introduction page
			openGuidebook();
		} else if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			// Process new advancement unlocks that got added
			// after spectrum has been installed / updated
			reprocessAdvancementUnlocks(serverPlayerEntity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		
		return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
	}
	
	public void openGuidebook() {
		BookGuiManager.get().openBook(GUIDEBOOK_ADDRESS);
	}
	
	public void openGuidebook(BookAddress address) {
		BookGuiManager.get().openBook(address);
	}
	
	@Override
	public RegistryKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.GUIDEBOOK;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}
