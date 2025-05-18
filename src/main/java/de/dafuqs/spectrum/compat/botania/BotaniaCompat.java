package de.dafuqs.spectrum.compat.botania;

import de.dafuqs.fractal.api.ItemSubGroupEvents;
import de.dafuqs.spectrum.api.color.ItemColors;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.interaction.ItemProvider;
import de.dafuqs.spectrum.api.interaction.ItemProviderRegistry;
import de.dafuqs.spectrum.api.item_group.ItemGroupIDs;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import vazkii.botania.common.item.BlackHoleTalismanItem;
import vazkii.botania.common.item.BotaniaItems;

import static de.dafuqs.spectrum.registries.SpectrumItems.item;
import static de.dafuqs.spectrum.registries.SpectrumItems.simple;

@SuppressWarnings("unused")
public class BotaniaCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Item LEAST_BLACK_LOTUS = SpectrumItems.register(simple(item("least_black_lotus", new LeastBlackLotusItem(new Item.Properties()), InkColors.BLACK)));
	public static Item BLACKEST_LOTUS = SpectrumItems.register(simple(item("blackest_lotus", new BlackestLotusItem(new Item.Properties()), InkColors.BLACK)));

	private static void onServerStarted(ServerStartedEvent event) {
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.overgrowthSeed, InkColors.LIME);
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.blackLotus, InkColors.BLACK);
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.blackerLotus, InkColors.BLACK);
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.terrasteel, InkColors.LIME);

		ItemProviderRegistry.register(BotaniaItems.blackHoleTalisman, new ItemProvider() {
			@Override
			public int getItemCount(Player player, ItemStack stack, Item requestedItem) {
				if (requestedItem instanceof BlockItem blockItem) {
					Block storedBlock = BlackHoleTalismanItem.getBlock(stack);
					if (blockItem.getBlock() == storedBlock) {
						return BlackHoleTalismanItem.getBlockCount(stack);
					}
				}
				return 0;
			}

			@Override
			public int provideItems(Player player, ItemStack stack, Item requestedItem, int amount) {
				if (requestedItem instanceof BlockItem blockItem) {
					Block storedBlock = BlackHoleTalismanItem.getBlock(stack);
					if (blockItem.getBlock() == storedBlock) {
						int storedAmount = BlackHoleTalismanItem.getBlockCount(stack);
						int amountToRemove = Math.min(storedAmount, amount);
						BlackHoleTalismanItem.setCount(stack, storedAmount - amountToRemove);
						return amountToRemove;
					}
				}
				return 0;
			}
		});
	}

	@Override
	public void register() {
		SpectrumItems.ITEM_REGISTRAR.flush();
		
		// registering it late, since Botania might not have been initialized yet
		NeoForge.EVENT_BUS.addListener(BotaniaCompat::onServerStarted);
		
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_EQUIPMENT).register(entries -> {
			entries.accept(LEAST_BLACK_LOTUS);
			entries.accept(BLACKEST_LOTUS);
		});
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerClient() {
	
	}
	
}
