package earth.terrarium.pastel.compat.botania;

import earth.terrarium.pastel.api.color.ItemColors;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.interaction.ItemProvider;
import earth.terrarium.pastel.compat.SpectrumIntegrationPacks;
import earth.terrarium.pastel.registries.SpectrumItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.*;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.*;
import vazkii.botania.common.item.BlackHoleTalismanItem;
import vazkii.botania.common.item.BotaniaItems;

import static earth.terrarium.pastel.registries.SpectrumItems.item;
import static earth.terrarium.pastel.registries.SpectrumItems.simple;

@SuppressWarnings("unused")
public class BotaniaCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static DeferredItem<Item> LEAST_BLACK_LOTUS = SpectrumItems.register(simple(item("least_black_lotus", () -> new LeastBlackLotusItem(new Item.Properties()), InkColors.BLACK)));
	public static DeferredItem<Item> BLACKEST_LOTUS = SpectrumItems.register(simple(item("blackest_lotus", () -> new BlackestLotusItem(new Item.Properties()), InkColors.BLACK)));

	private static void onServerStarted(ServerStartedEvent event) {
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.overgrowthSeed, InkColors.LIME);
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.blackLotus, InkColors.BLACK);
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.blackerLotus, InkColors.BLACK);
		ItemColors.ITEM_COLORS.registerColorMapping(BotaniaItems.terrasteel, InkColors.LIME);
	}
	
	private static void onRegisterCaps(RegisterCapabilitiesEvent event) {
		event.registerItem(ItemProvider.CAPABILITY, (ignored, ignored2) -> new ItemProvider() {
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
		}, BotaniaItems.blackHoleTalisman);
	}

	@Override
	public void register() {
		
		// registering it late, since Botania might not have been initialized yet
		NeoForge.EVENT_BUS.addListener(BotaniaCompat::onServerStarted);
		ModLoadingContext.get().getActiveContainer().getEventBus().addListener(BotaniaCompat::onRegisterCaps);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerClient() {
	
	}
	
}
