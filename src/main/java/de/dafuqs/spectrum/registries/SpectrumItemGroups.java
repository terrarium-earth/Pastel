package de.dafuqs.spectrum.registries;

import de.dafuqs.fractal.api.ItemSubGroup;
import de.dafuqs.fractal.interfaces.ItemGroupParent;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import de.dafuqs.spectrum.api.item.Preenchanted;
import de.dafuqs.spectrum.api.item_group.ItemGroupIDs;
import de.dafuqs.spectrum.blocks.memory.MemoryItem;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlock;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.compat.ae2.AE2Compat;
import de.dafuqs.spectrum.compat.create.CreateCompat;
import de.dafuqs.spectrum.compat.gobber.GobberCompat;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.recipe.titration_barrel.ITitrationBarrelRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import net.neoforged.neoforge.server.*;

import java.util.Map;

@SuppressWarnings("unused")
public class SpectrumItemGroups {


	public static final CreativeModeTab MAIN = CreativeModeTab.builder()
			.icon(() -> new ItemStack(SpectrumBlocks.PEDESTAL_ALL_BASIC.get()))
			.displayItems((displayContext, entries) -> {
				entries.accept(SpectrumBlocks.PEDESTAL_ALL_BASIC.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
				for (ItemSubGroup subGroup : ((ItemGroupParent) SpectrumItemGroups.MAIN).fractal$getChildren()) {
					entries.acceptAll(subGroup.getSearchTabDisplayItems(), CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
				}
			})
			.hideTitle()
			.title(Component.translatable("itemGroup.pastel"))
			.build();
	
	public static void register(IEventBus bus) {
		var register = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpectrumCommon.MOD_ID);

		register.register(ItemGroupIDs.MAIN_GROUP_ID.getPath(), () -> MAIN);
		register.register(bus);

	}
	
	public static final ItemSubGroup EQUIPMENT = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_EQUIPMENT, Component.translatable("itemGroup.pastel.equipment"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				HolderLookup.Provider lookup = displayContext.holders();
				
				entries.accept(SpectrumItems.GUIDEBOOK);
				entries.accept(SpectrumItems.PAINTBRUSH);
				entries.accept(SpectrumItems.TUNING_STAMP);
				entries.accept(SpectrumItems.BOTTLE_OF_FADING);
				entries.accept(SpectrumItems.BOTTLE_OF_FAILING);
				entries.accept(SpectrumItems.BOTTLE_OF_RUIN);
				entries.accept(SpectrumItems.BOTTLE_OF_FORFEITURE);
				entries.accept(SpectrumItems.BOTTLE_OF_DECAY_AWAY);
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MULTITOOL));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.TENDER_PICKAXE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.LUCKY_PICKAXE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.RAZOR_FALCHION));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.OBLIVION_PICKAXE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.RESONANT_PICKAXE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.DRAGONRENDING_PICKAXE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.LAGOON_ROD));
				entries.accept(SpectrumItems.MOLTEN_ROD);
				entries.accept(SpectrumItems.FETCHLING_HELMET);
				entries.accept(SpectrumItems.FEROCIOUS_CHESTPLATE);
				entries.accept(SpectrumItems.SYLPH_LEGGINGS);
				entries.accept(SpectrumItems.OREAD_BOOTS);
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_PICKAXE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_AXE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_SHOVEL));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_SWORD));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_HOE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_BOW));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_CROSSBOW));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_SHEARS));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_FISHING_ROD));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_HELMET));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_CHESTPLATE));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_LEGGINGS));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_BOOTS));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_WORKSTAFF));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_ULTRA_GREATSWORD));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_CROSSBOW));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_BIDENT));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.GLASS_CREST_WORKSTAFF));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.GLASS_CREST_ULTRA_GREATSWORD));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.FEROCIOUS_GLASS_CREST_BIDENT));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.FRACTAL_GLASS_CREST_BIDENT));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.GLASS_CREST_CROSSBOW));
				entries.accept(SpectrumItems.MALACHITE_GLASS_ARROW);
				entries.accept(SpectrumItems.TOPAZ_GLASS_ARROW);
				entries.accept(SpectrumItems.AMETHYST_GLASS_ARROW);
				entries.accept(SpectrumItems.CITRINE_GLASS_ARROW);
				entries.accept(SpectrumItems.ONYX_GLASS_ARROW);
				entries.accept(SpectrumItems.MOONSTONE_GLASS_ARROW);
				entries.accept(SpectrumItems.AZURITE_GLASS_AMPOULE);
				entries.accept(SpectrumItems.MALACHITE_GLASS_AMPOULE);
				entries.accept(SpectrumItems.BLOODSTONE_GLASS_AMPOULE);
				entries.accept(SpectrumItems.DREAMFLAYER);
				entries.accept(SpectrumItems.NIGHTFALLS_BLADE);
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.DRACONIC_TWINSWORD));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.DRAGON_TALON));
				entries.accept(SpectrumItems.KNOTTED_SWORD);
				entries.accept(SpectrumItems.NECTAR_LANCE);
				entries.accept(SpectrumItems.OMNI_ACCELERATOR);
				entries.accept(SpectrumItems.FANCIFUL_TUFF_RING);
				entries.accept(SpectrumItems.FANCIFUL_BELT);
				entries.accept(SpectrumItems.FANCIFUL_PENDANT);
				entries.accept(SpectrumItems.FANCIFUL_CIRCLET);
				entries.accept(SpectrumItems.FANCIFUL_GLOVES);
				entries.accept(SpectrumItems.FANCIFUL_BISMUTH_RING);
				entries.accept(SpectrumItems.GLOW_VISION_GOGGLES);
				entries.accept(SpectrumItems.JEOPARDANT);
				entries.accept(SpectrumItems.SEVEN_LEAGUE_BOOTS);
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.SEVEN_LEAGUE_BOOTS, Map.of(Enchantments.POWER, 5)));
				entries.accept(SpectrumItems.COTTON_CLOUD_BOOTS);
				entries.accept(SpectrumItems.RADIANCE_PIN);
				entries.accept(SpectrumItems.TOTEM_PENDANT);
				entries.accept(SpectrumItems.TAKE_OFF_BELT);
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.TAKE_OFF_BELT, Map.of(Enchantments.POWER, 5, Enchantments.FEATHER_FALLING, 4)));
				entries.accept(SpectrumItems.AZURE_DIKE_BELT);
				entries.accept(SpectrumItems.AZURE_DIKE_RING);
				entries.accept(SpectrumItems.SHIELDGRASP_AMULET);
				entries.accept(SpectrumItems.SHIELDGRASP_AMULET.getFullStack());
				entries.accept(SpectrumItems.HEARTSINGERS_REWARD);
				entries.accept(SpectrumItems.HEARTSINGERS_REWARD.getFullStack());
				entries.accept(SpectrumItems.GLOVES_OF_DAWNS_GRASP);
				entries.accept(SpectrumItems.GLOVES_OF_DAWNS_GRASP.getFullStack());
				entries.accept(SpectrumItems.RING_OF_PURSUIT);
				entries.accept(SpectrumItems.RING_OF_PURSUIT.getFullStack());
				entries.accept(SpectrumItems.RING_OF_DENSER_STEPS);
				entries.accept(SpectrumItems.RING_OF_DENSER_STEPS.getFullStack());
				entries.accept(SpectrumItems.RING_OF_AERIAL_GRACE);
				entries.accept(SpectrumItems.RING_OF_AERIAL_GRACE.getFullStack());
				entries.accept(SpectrumItems.LAURELS_OF_SERENITY);
				entries.accept(SpectrumItems.LAURELS_OF_SERENITY.getFullStack());
				entries.accept(SpectrumItems.GLEAMING_PIN);
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.GLEAMING_PIN, Map.of(SpectrumEnchantments.SNIPING, 2)));
				entries.accept(SpectrumItems.LESSER_POTION_PENDANT);
				entries.accept(SpectrumItems.GREATER_POTION_PENDANT);
				entries.accept(SpectrumItems.ASHEN_CIRCLET);
				entries.accept(SpectrumItems.WEEPING_CIRCLET);
				entries.accept(SpectrumItems.PUFF_CIRCLET);
				entries.accept(SpectrumItems.WHISPY_CIRCLET);
				entries.accept(SpectrumItems.AZURESQUE_DIKE_CORE);
				entries.accept(SpectrumItems.CIRCLET_OF_ARROGANCE);
				entries.accept(SpectrumItems.AETHER_GRACED_NECTAR_GLOVES);
				entries.accept(SpectrumItems.NEAT_RING);
				entries.accept(SpectrumItems.CRAFTING_TABLET);
				entries.accept(SpectrumBlocks.BOTTOMLESS_BUNDLE.get());
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumBlocks.BOTTOMLESS_BUNDLE.get().asItem(), Map.of(Enchantments.POWER, 5, SpectrumEnchantments.VOIDING, 1)));
				
				entries.accept(SpectrumItems.KNOWLEDGE_GEM);
				ItemStack enchantedKnowledgeGemStack = SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.KNOWLEDGE_GEM.asItem(), Map.of(Enchantments.EFFICIENCY, 5, Enchantments.QUICK_CHARGE, 3));
				entries.accept(enchantedKnowledgeGemStack.copy());
				
				ItemStack knowledgeGemStack = SpectrumItems.KNOWLEDGE_GEM.getDefaultInstance();
				ExperienceStorageItem.addStoredExperience(lookup, knowledgeGemStack, SpectrumItems.KNOWLEDGE_GEM.getMaxStoredExperience(lookup, knowledgeGemStack));
				entries.accept(knowledgeGemStack);
				
				ExperienceStorageItem.addStoredExperience(lookup, enchantedKnowledgeGemStack, SpectrumItems.KNOWLEDGE_GEM.getMaxStoredExperience(lookup, enchantedKnowledgeGemStack));
				entries.accept(enchantedKnowledgeGemStack);
				
				entries.accept(SpectrumItems.CELESTIAL_POCKETWATCH);
				entries.accept(SpectrumItems.GILDED_BOOK);
				entries.accept(SpectrumItems.ENCHANTMENT_CANVAS);
				entries.accept(SpectrumItems.NIGHT_SALTS);
				entries.accept(SpectrumItems.SOOTHING_BOUQUET);
				entries.accept(SpectrumItems.CONCEALING_OILS);
				entries.accept(SpectrumItems.BITTER_OILS);
				entries.accept(SpectrumItems.EVERPROMISE_RIBBON);
				entries.accept(SpectrumItems.BAG_OF_HOLDING);
				entries.accept(SpectrumItems.RADIANCE_STAFF);
				entries.accept(SpectrumItems.NATURES_STAFF);
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.NATURES_STAFF, Map.of(Enchantments.EFFICIENCY, 5)));
				entries.accept(SpectrumItems.STAFF_OF_REMEMBRANCE);
				entries.accept(SpectrumItems.CONSTRUCTORS_STAFF);
				entries.accept(SpectrumItems.EXCHANGING_STAFF);
				SpectrumEnchantmentHelper.addOrUpgradeEnchantmentOpt(lookup, SpectrumItems.EXCHANGING_STAFF.getDefaultInstance(), Enchantments.FORTUNE, 3, false, false).ifPresent(entries::accept);
				SpectrumEnchantmentHelper.addOrUpgradeEnchantmentOpt(lookup, SpectrumItems.EXCHANGING_STAFF.getDefaultInstance(), Enchantments.SILK_TOUCH, 1, false, false).ifPresent(entries::accept);
				SpectrumEnchantmentHelper.addOrUpgradeEnchantmentOpt(lookup, SpectrumItems.EXCHANGING_STAFF.getDefaultInstance(), SpectrumEnchantments.RESONANCE, 1, false, false).ifPresent(entries::accept);
				entries.accept(SpectrumItems.BLOCK_FLOODER);
				entries.accept(SpectrumItems.ENDER_SPLICE);
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.ENDER_SPLICE, Map.of(SpectrumEnchantments.RESONANCE, 1, SpectrumEnchantments.INDESTRUCTIBLE, 1)));
				entries.accept(SpectrumItems.PERTURBED_EYE);
				entries.accept(SpectrumItems.PIPE_BOMB);
				entries.accept(SpectrumItems.CRESCENT_CLOCK);
				entries.accept(SpectrumItems.ARTISANS_ATLAS);
				entries.accept(SpectrumBlocks.PRIMORDIAL_TORCH.get());
				entries.accept(SpectrumItems.MYSTERIOUS_LOCKET);
				entries.accept(SpectrumItems.MYSTERIOUS_COMPASS);
			}).build();
	
	public static final ItemSubGroup FUNCTIONAL = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_FUNCTIONAL, Component.translatable("itemGroup.pastel.functional"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumBlocks.PEDESTAL_BASIC_TOPAZ.get());
				entries.accept(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.get());
				entries.accept(SpectrumBlocks.PEDESTAL_BASIC_CITRINE.get());
				entries.accept(SpectrumBlocks.PEDESTAL_ALL_BASIC.get());
				entries.accept(SpectrumBlocks.PEDESTAL_ONYX.get());
				entries.accept(SpectrumBlocks.PEDESTAL_MOONSTONE.get());
				entries.accept(SpectrumBlocks.FUSION_SHRINE_BASALT.get());
				entries.accept(SpectrumBlocks.FUSION_SHRINE_CALCITE.get());
				entries.accept(SpectrumBlocks.ENCHANTER.get());
				entries.accept(SpectrumBlocks.ITEM_BOWL_BASALT.get());
				entries.accept(SpectrumBlocks.ITEM_BOWL_CALCITE.get());
				entries.accept(SpectrumBlocks.ITEM_ROUNDEL.get());
				entries.accept(SpectrumBlocks.POTION_WORKSHOP.get());
				entries.accept(SpectrumBlocks.SPIRIT_INSTILLER.get());
				entries.accept(SpectrumBlocks.CRYSTALLARIEUM.get());
				entries.accept(SpectrumBlocks.CINDERHEARTH.get());
				entries.accept(SpectrumBlocks.CRYSTAL_APOTHECARY.get());
				entries.accept(SpectrumBlocks.COLOR_PICKER.get());
				
				entries.accept(SpectrumBlocks.UPGRADE_SPEED.get());
				entries.accept(SpectrumBlocks.UPGRADE_SPEED2.get());
				entries.accept(SpectrumBlocks.UPGRADE_SPEED3.get());
				entries.accept(SpectrumBlocks.UPGRADE_EFFICIENCY.get());
				entries.accept(SpectrumBlocks.UPGRADE_EFFICIENCY2.get());
				entries.accept(SpectrumBlocks.UPGRADE_YIELD.get());
				entries.accept(SpectrumBlocks.UPGRADE_YIELD2.get());
				entries.accept(SpectrumBlocks.UPGRADE_EXPERIENCE.get());
				entries.accept(SpectrumBlocks.UPGRADE_EXPERIENCE2.get());
				
				entries.accept(SpectrumBlocks.CONNECTION_NODE.get());
				entries.accept(SpectrumBlocks.PROVIDER_NODE.get());
				entries.accept(SpectrumBlocks.SENDER_NODE.get());
				entries.accept(SpectrumBlocks.STORAGE_NODE.get());
				entries.accept(SpectrumBlocks.BUFFER_NODE.get());
				entries.accept(SpectrumBlocks.GATHER_NODE.get());
				
				entries.accept(SpectrumBlocks.LIGHT_LEVEL_DETECTOR.get());
				entries.accept(SpectrumBlocks.WEATHER_DETECTOR.get());
				entries.accept(SpectrumBlocks.ITEM_DETECTOR.get());
				entries.accept(SpectrumBlocks.PLAYER_DETECTOR.get());
				entries.accept(SpectrumBlocks.CREATURE_DETECTOR.get());
				entries.accept(SpectrumBlocks.REDSTONE_TIMER.get());
				entries.accept(SpectrumBlocks.REDSTONE_CALCULATOR.get());
				entries.accept(SpectrumBlocks.REDSTONE_TRANSCEIVER.get());
				entries.accept(SpectrumBlocks.REDSTONE_SAND.get());
				entries.accept(SpectrumBlocks.ENDER_GLASS.get());
				entries.accept(SpectrumBlocks.BLOCK_DETECTOR.get());
				entries.accept(SpectrumBlocks.BLOCK_PLACER.get());
				entries.accept(SpectrumBlocks.BLOCK_BREAKER.get());
				
				entries.accept(SpectrumBlocks.HEARTBOUND_CHEST.get());
				entries.accept(SpectrumBlocks.COMPACTING_CHEST.get());
				entries.accept(SpectrumBlocks.FABRICATION_CHEST.get());
				entries.accept(SpectrumBlocks.BLACK_HOLE_CHEST.get());
				
				entries.accept(SpectrumBlocks.ENDER_HOPPER.get());
				entries.accept(SpectrumBlocks.ENDER_DROPPER.get());
				
				entries.accept(SpectrumBlocks.PARTICLE_SPAWNER.get());
				
				entries.accept(BuiltInRegistries.BLOCK.get(SpectrumBlocks.GLISTERING_MELON)); // ???
				entries.accept(SpectrumBlocks.LAVA_SPONGE.get());
				entries.accept(SpectrumBlocks.WET_LAVA_SPONGE.get());
				entries.accept(SpectrumBlocks.ETHEREAL_PLATFORM.get());
				entries.accept(SpectrumBlocks.UNIVERSE_SPYHOLE.get());
				entries.accept(SpectrumBlocks.PRESENT.get());
				entries.accept(SpectrumBlocks.TITRATION_BARREL.get());
				
				entries.accept(SpectrumBlocks.INCANDESCENT_AMALGAM.get());
				entries.accept(SpectrumBlocks.BEDROCK_ANVIL.get());
				entries.accept(SpectrumBlocks.CRACKED_END_PORTAL_FRAME.get());
				
				entries.accept(SpectrumBlocks.SEMI_PERMEABLE_GLASS.get());
				entries.accept(SpectrumBlocks.TINTED_SEMI_PERMEABLE_GLASS.get());
				entries.accept(SpectrumBlocks.RADIANT_SEMI_PERMEABLE_GLASS.get());
				entries.accept(SpectrumBlocks.TOPAZ_SEMI_PERMEABLE_GLASS.get());
				entries.accept(SpectrumBlocks.AMETHYST_SEMI_PERMEABLE_GLASS.get());
				entries.accept(SpectrumBlocks.CITRINE_SEMI_PERMEABLE_GLASS.get());
				entries.accept(SpectrumBlocks.ONYX_SEMI_PERMEABLE_GLASS.get());
				entries.accept(SpectrumBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS.get());
				
				entries.accept(SpectrumBlocks.AXOLOTL_IDOL.get());
				entries.accept(SpectrumBlocks.BAT_IDOL.get());
				entries.accept(SpectrumBlocks.BEE_IDOL.get());
				entries.accept(SpectrumBlocks.BLAZE_IDOL.get());
				entries.accept(SpectrumBlocks.CAT_IDOL.get());
				entries.accept(SpectrumBlocks.CHICKEN_IDOL.get());
				entries.accept(SpectrumBlocks.COW_IDOL.get());
				entries.accept(SpectrumBlocks.CREEPER_IDOL.get());
				entries.accept(SpectrumBlocks.ENDER_DRAGON_IDOL.get());
				entries.accept(SpectrumBlocks.ENDERMAN_IDOL.get());
				entries.accept(SpectrumBlocks.ENDERMITE_IDOL.get());
				entries.accept(SpectrumBlocks.EVOKER_IDOL.get());
				entries.accept(SpectrumBlocks.FISH_IDOL.get());
				entries.accept(SpectrumBlocks.FOX_IDOL.get());
				entries.accept(SpectrumBlocks.GHAST_IDOL.get());
				entries.accept(SpectrumBlocks.GLOW_SQUID_IDOL.get());
				entries.accept(SpectrumBlocks.GOAT_IDOL.get());
				entries.accept(SpectrumBlocks.GUARDIAN_IDOL.get());
				entries.accept(SpectrumBlocks.HORSE_IDOL.get());
				entries.accept(SpectrumBlocks.ILLUSIONER_IDOL.get());
				entries.accept(SpectrumBlocks.OCELOT_IDOL.get());
				entries.accept(SpectrumBlocks.PARROT_IDOL.get());
				entries.accept(SpectrumBlocks.PHANTOM_IDOL.get());
				entries.accept(SpectrumBlocks.PIG_IDOL.get());
				entries.accept(SpectrumBlocks.PIGLIN_IDOL.get());
				entries.accept(SpectrumBlocks.POLAR_BEAR_IDOL.get());
				entries.accept(SpectrumBlocks.PUFFERFISH_IDOL.get());
				entries.accept(SpectrumBlocks.RABBIT_IDOL.get());
				entries.accept(SpectrumBlocks.SHEEP_IDOL.get());
				entries.accept(SpectrumBlocks.SHULKER_IDOL.get());
				entries.accept(SpectrumBlocks.SILVERFISH_IDOL.get());
				entries.accept(SpectrumBlocks.SKELETON_IDOL.get());
				entries.accept(SpectrumBlocks.SLIME_IDOL.get());
				entries.accept(SpectrumBlocks.SNOW_GOLEM_IDOL.get());
				entries.accept(SpectrumBlocks.SPIDER_IDOL.get());
				entries.accept(SpectrumBlocks.SQUID_IDOL.get());
				entries.accept(SpectrumBlocks.STRAY_IDOL.get());
				entries.accept(SpectrumBlocks.STRIDER_IDOL.get());
				entries.accept(SpectrumBlocks.TURTLE_IDOL.get());
				entries.accept(SpectrumBlocks.WITCH_IDOL.get());
				entries.accept(SpectrumBlocks.WITHER_IDOL.get());
				entries.accept(SpectrumBlocks.WITHER_SKELETON_IDOL.get());
				entries.accept(SpectrumBlocks.ZOMBIE_IDOL.get());
			}).build();
	
	public static final ItemSubGroup CUISINE = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_CUISINE, Component.translatable("itemGroup.pastel.cuisine"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.IMBRIFER_COOKBOOK);
				entries.accept(SpectrumItems.IMPERIAL_COOKBOOK);
				entries.accept(SpectrumItems.MELOCHITES_COOKBOOK_VOL_1);
				entries.accept(SpectrumItems.MELOCHITES_COOKBOOK_VOL_2);
				entries.accept(SpectrumItems.BREWERS_HANDBOOK);
				//entries.add(SpectrumItems.VARIA_COOKBOOK);
				entries.accept(SpectrumItems.POISONERS_HANDBOOK);
				
				entries.accept(SpectrumItems.TRIPLE_MEAT_POT_PIE);
				entries.accept(SpectrumItems.KIMCHI);
				entries.accept(SpectrumItems.CLOTTED_CREAM);
				entries.accept(SpectrumItems.FRESH_CHOCOLATE);
				entries.accept(SpectrumItems.BODACIOUS_BERRY_BAR);
				entries.accept(SpectrumItems.HOT_CHOCOLATE);
				entries.accept(SpectrumItems.KARAK_CHAI);
				entries.accept(SpectrumItems.RESTORATION_TEA);
				entries.accept(SpectrumItems.GLISTERING_JELLY_TEA);
				entries.accept(SpectrumItems.AZALEA_TEA);
				entries.accept(SpectrumItems.DEMON_TEA);
				entries.accept(SpectrumItems.ENCHANTED_GOLDEN_CARROT);
				entries.accept(SpectrumItems.JADE_JELLY);
				entries.accept(SpectrumItems.JARAMEL);
				entries.accept(SpectrumItems.MOONSTRUCK_NECTAR);
				entries.accept(SpectrumItems.GLASS_PEACH);
				entries.accept(SpectrumItems.FISSURE_PLUM);
				entries.accept(SpectrumItems.NIGHTDEW_SPROUT);
				entries.accept(SpectrumItems.NECTARDEW_BURGEON);
				entries.accept(SpectrumItems.BLOODBOIL_SYRUP);
				entries.accept(SpectrumItems.MILKY_RESIN);
				entries.accept(SpectrumItems.SCONE);
				entries.accept(SpectrumItems.STAR_CANDY);
				entries.accept(SpectrumItems.ENCHANTED_STAR_CANDY);
				entries.accept(SpectrumItems.CHEONG);
				entries.accept(SpectrumItems.MERMAIDS_JAM);
				entries.accept(SpectrumItems.MERMAIDS_POPCORN);
				entries.accept(SpectrumItems.LE_FISHE_AU_CHOCOLAT);
				//entries.add(SpectrumItems.STUFFED_PETALS);
				//entries.add(SpectrumItems.PASTICHE);
				//entries.add(SpectrumItems.VITTORIAS_ROAST);
				entries.accept(SpectrumItems.LUCKY_ROLL);
				entries.accept(SpectrumItems.HONEY_PASTRY);
				entries.accept(SpectrumItems.JARAMEL_TART);
				entries.accept(SpectrumItems.SALTED_JARAMEL_TART);
				entries.accept(SpectrumItems.ASHEN_TART);
				entries.accept(SpectrumItems.WEEPING_TART);
				entries.accept(SpectrumItems.WHISPY_TART);
				entries.accept(SpectrumItems.PUFF_TART);
				entries.accept(SpectrumItems.JARAMEL_TRIFLE);
				entries.accept(SpectrumItems.SALTED_JARAMEL_TRIFLE);
				entries.accept(SpectrumItems.MONSTER_TRIFLE);
				entries.accept(SpectrumItems.DEMON_TRIFLE);
				entries.accept(SpectrumItems.MYCEYLON);
				entries.accept(SpectrumItems.MYCEYLON_APPLE_PIE);
				entries.accept(SpectrumItems.MYCEYLON_PUMPKIN_PIE);
				entries.accept(SpectrumItems.MYCEYLON_COOKIE);
				entries.accept(SpectrumItems.ALOE_LEAF);
				entries.accept(SpectrumItems.SAWBLADE_HOLLY_BERRY);
				entries.accept(SpectrumItems.PRICKLY_BAYLEAF);
				entries.accept(SpectrumItems.TRIPLE_MEAT_POT_STEW);
				entries.accept(SpectrumItems.DRAGONBONE_BROTH);
				entries.accept(SpectrumItems.BAGNUN);
				entries.accept(SpectrumItems.BANYASH);
				entries.accept(SpectrumItems.BERLINER);
				entries.accept(SpectrumItems.BRISTLE_MEAD);
				entries.accept(SpectrumItems.CHAUVE_SOURIS_AU_VIN);
				entries.accept(SpectrumItems.CRAWFISH);
				entries.accept(SpectrumItems.CRAWFISH_COCKTAIL);
				entries.accept(SpectrumItems.CREAM_PASTRY);
				entries.accept(SpectrumItems.FADED_KOI);
				entries.accept(SpectrumItems.FISHCAKE);
				entries.accept(SpectrumItems.LIZARD_MEAT);
				entries.accept(SpectrumItems.COOKED_LIZARD_MEAT);
				entries.accept(SpectrumItems.GOLDEN_BRISTLE_TEA);
				entries.accept(SpectrumItems.HARE_ROAST);
				entries.accept(SpectrumItems.JUNKET);
				entries.accept(SpectrumItems.KOI);
				entries.accept(SpectrumItems.MEATLOAF);
				entries.accept(SpectrumItems.MEATLOAF_SANDWICH);
				entries.accept(SpectrumItems.MELLOW_SHALLOT_SOUP);
				entries.accept(SpectrumItems.PEACHES_FLAMBE);
				entries.accept(SpectrumItems.PEACH_CREAM);
				entries.accept(SpectrumItems.PEACH_JAM);
				entries.accept(SpectrumItems.RABBIT_CREAM_PIE);
				entries.accept(SpectrumItems.SEDATIVES);
				entries.accept(SpectrumItems.SLUSHSLIDE);
				entries.accept(SpectrumItems.SURSTROMMING);
				entries.accept(SpectrumItems.MORCHELLA);
				entries.accept(SpectrumItems.NECTERED_VIOGNIER);
				entries.accept(SpectrumItems.FREIGEIST);
				
				// adding all beverages from recipes
				if (ServerLifecycleHooks.getCurrentServer() != null) {
					for (RecipeHolder<ITitrationBarrelRecipe> recipe : ServerLifecycleHooks.getCurrentServer().getRecipeManager().getAllRecipesFor(SpectrumRecipeTypes.TITRATION_BARREL)) {
						ItemStack output = recipe.value().getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).copy();
						if (output.getItem().components().has(SpectrumDataComponentTypes.INFUSED_BEVERAGE)) {
							output.setCount(1);
							entries.accept(output);
						}
					}
				}
				
				entries.accept(SpectrumItems.PURE_ALCOHOL);
				entries.accept(SpectrumItems.REPRISE);
				entries.accept(SpectrumItems.SUSPICIOUS_BREW);
				entries.accept(SpectrumItems.JADE_WINE);
				entries.accept(SpectrumItems.CHRYSOCOLLA);
				entries.accept(SpectrumItems.AQUA_REGIA);
				entries.accept(SpectrumItems.EVERNECTAR);
			}).build();
	
	public static final ItemSubGroup RESOURCES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_RESOURCES, Component.translatable("itemGroup.pastel.resources"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.TOPAZ_SHARD);
				entries.accept(Items.AMETHYST_SHARD);
				entries.accept(SpectrumItems.CITRINE_SHARD);
				entries.accept(SpectrumItems.ONYX_SHARD);
				entries.accept(SpectrumItems.MOONSTONE_SHARD);
				
				entries.accept(SpectrumBlocks.TOPAZ_BLOCK.get());
				entries.accept(Blocks.AMETHYST_BLOCK);
				entries.accept(SpectrumBlocks.CITRINE_BLOCK.get());
				entries.accept(SpectrumBlocks.ONYX_BLOCK.get());
				entries.accept(SpectrumBlocks.MOONSTONE_BLOCK.get());
				
				entries.accept(SpectrumItems.TOPAZ_POWDER);
				entries.accept(SpectrumItems.AMETHYST_POWDER);
				entries.accept(SpectrumItems.CITRINE_POWDER);
				entries.accept(SpectrumItems.ONYX_POWDER);
				entries.accept(SpectrumItems.MOONSTONE_POWDER);
				
				entries.accept(SpectrumBlocks.TOPAZ_POWDER_BLOCK.get());
				entries.accept(SpectrumBlocks.AMETHYST_POWDER_BLOCK.get());
				entries.accept(SpectrumBlocks.CITRINE_POWDER_BLOCK.get());
				entries.accept(SpectrumBlocks.ONYX_POWDER_BLOCK.get());
				entries.accept(SpectrumBlocks.MOONSTONE_POWDER_BLOCK.get());
				
				entries.accept(SpectrumBlocks.BUDDING_TOPAZ.get());
				entries.accept(Blocks.BUDDING_AMETHYST);
				entries.accept(SpectrumBlocks.BUDDING_CITRINE.get());
				entries.accept(SpectrumBlocks.BUDDING_ONYX.get());
				entries.accept(SpectrumBlocks.BUDDING_MOONSTONE.get());
				
				entries.accept(SpectrumBlocks.SMALL_TOPAZ_BUD.get());
				entries.accept(SpectrumBlocks.MEDIUM_TOPAZ_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_TOPAZ_BUD.get());
				entries.accept(SpectrumBlocks.TOPAZ_CLUSTER.get());
				
				entries.accept(Blocks.SMALL_AMETHYST_BUD);
				entries.accept(Blocks.MEDIUM_AMETHYST_BUD);
				entries.accept(Blocks.LARGE_AMETHYST_BUD);
				entries.accept(Blocks.AMETHYST_CLUSTER);
				
				entries.accept(SpectrumBlocks.SMALL_CITRINE_BUD.get());
				entries.accept(SpectrumBlocks.MEDIUM_CITRINE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_CITRINE_BUD.get());
				entries.accept(SpectrumBlocks.CITRINE_CLUSTER.get());
				
				entries.accept(SpectrumBlocks.SMALL_ONYX_BUD.get());
				entries.accept(SpectrumBlocks.MEDIUM_ONYX_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_ONYX_BUD.get());
				entries.accept(SpectrumBlocks.ONYX_CLUSTER.get());
				
				entries.accept(SpectrumBlocks.SMALL_MOONSTONE_BUD.get());
				entries.accept(SpectrumBlocks.MEDIUM_MOONSTONE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_MOONSTONE_BUD.get());
				entries.accept(SpectrumBlocks.MOONSTONE_CLUSTER.get());
				
				entries.accept(SpectrumBlocks.TOPAZ_ORE.get());
				entries.accept(SpectrumBlocks.AMETHYST_ORE.get());
				entries.accept(SpectrumBlocks.CITRINE_ORE.get());
				entries.accept(SpectrumBlocks.ONYX_ORE.get());
				entries.accept(SpectrumBlocks.MOONSTONE_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_TOPAZ_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_AMETHYST_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_CITRINE_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_ONYX_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_MOONSTONE_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_TOPAZ_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_AMETHYST_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_CITRINE_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_ONYX_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_MOONSTONE_ORE.get());
				entries.accept(SpectrumBlocks.SHIMMERSTONE_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_SHIMMERSTONE_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_SHIMMERSTONE_ORE.get());
				entries.accept(SpectrumBlocks.AZURITE_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_AZURITE_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_AZURITE_ORE.get());
				entries.accept(SpectrumBlocks.STRATINE_ORE.get());
				entries.accept(SpectrumBlocks.PALTAERIA_ORE.get());
				
				entries.accept(SpectrumBlocks.BLACKSLAG_COAL_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_COPPER_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_IRON_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_GOLD_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_DIAMOND_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_REDSTONE_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_LAPIS_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_EMERALD_ORE.get());
				
				entries.accept(SpectrumItems.BISMUTH_FLAKE);
				entries.accept(SpectrumBlocks.SMALL_BISMUTH_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_BISMUTH_BUD.get());
				entries.accept(SpectrumBlocks.BISMUTH_CLUSTER.get());
				entries.accept(SpectrumItems.BISMUTH_CRYSTAL);
				
				entries.accept(SpectrumBlocks.MALACHITE_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_MALACHITE_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_MALACHITE_ORE.get());
				entries.accept(SpectrumItems.RAW_MALACHITE);
				entries.accept(SpectrumBlocks.SMALL_MALACHITE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_MALACHITE_BUD.get());
				entries.accept(SpectrumBlocks.MALACHITE_CLUSTER.get());
				entries.accept(SpectrumItems.PURE_MALACHITE);
				
				entries.accept(SpectrumItems.RAW_AZURITE);
				entries.accept(SpectrumBlocks.SMALL_AZURITE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_AZURITE_BUD.get());
				entries.accept(SpectrumBlocks.AZURITE_CLUSTER.get());
				entries.accept(SpectrumItems.PURE_AZURITE);
				
				entries.accept(SpectrumItems.RAW_BLOODSTONE);
				entries.accept(SpectrumBlocks.SMALL_BLOODSTONE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_BLOODSTONE_BUD.get());
				entries.accept(SpectrumBlocks.BLOODSTONE_CLUSTER.get());
				entries.accept(SpectrumItems.PURE_BLOODSTONE);
				
				entries.accept(SpectrumItems.FROSTBITE_ESSENCE);
				entries.accept(SpectrumBlocks.FROSTBITE_CRYSTAL.get());
				entries.accept(SpectrumItems.INCANDESCENT_ESSENCE);
				entries.accept(SpectrumBlocks.BLAZING_CRYSTAL.get());
				
				entries.accept(SpectrumBlocks.CLOVER.get());
				entries.accept(SpectrumBlocks.FOUR_LEAF_CLOVER.get());
				entries.accept(SpectrumItems.BLOOD_ORCHID_PETAL);
				entries.accept(SpectrumBlocks.BLOOD_ORCHID.get());
				entries.accept(SpectrumBlocks.QUITOXIC_REEDS.get());
				entries.accept(SpectrumItems.QUITOXIC_POWDER);
				
				entries.accept(SpectrumItems.AMARANTH_GRAINS);
				entries.accept(SpectrumBlocks.AMARANTH_BUSHEL.get());
				entries.accept(BuiltInRegistries.ITEM.get(SpectrumItems.GLISTERING_MELON_SEEDS));
				
				entries.accept(SpectrumBlocks.GLISTERING_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.FIERY_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.COLORFUL_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.PRISTINE_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.GEMSTONE_SHOOTING_STAR.get());
				entries.accept(SpectrumItems.STARDUST);
				entries.accept(SpectrumBlocks.STARDUST_BLOCK.get());
				entries.accept(SpectrumItems.STAR_FRAGMENT);
				entries.accept(SpectrumBlocks.RADIATING_ENDER.get());
				
				entries.accept(SpectrumItems.WHITE_PIGMENT);
				entries.accept(SpectrumItems.ORANGE_PIGMENT);
				entries.accept(SpectrumItems.MAGENTA_PIGMENT);
				entries.accept(SpectrumItems.LIGHT_BLUE_PIGMENT);
				entries.accept(SpectrumItems.YELLOW_PIGMENT);
				entries.accept(SpectrumItems.LIME_PIGMENT);
				entries.accept(SpectrumItems.PINK_PIGMENT);
				entries.accept(SpectrumItems.GRAY_PIGMENT);
				entries.accept(SpectrumItems.LIGHT_GRAY_PIGMENT);
				entries.accept(SpectrumItems.CYAN_PIGMENT);
				entries.accept(SpectrumItems.PURPLE_PIGMENT);
				entries.accept(SpectrumItems.BLUE_PIGMENT);
				entries.accept(SpectrumItems.BROWN_PIGMENT);
				entries.accept(SpectrumItems.GREEN_PIGMENT);
				entries.accept(SpectrumItems.RED_PIGMENT);
				entries.accept(SpectrumItems.BLACK_PIGMENT);
				
				entries.accept(SpectrumItems.VEGETAL);
				entries.accept(SpectrumItems.NEOLITH);
				entries.accept(SpectrumItems.BEDROCK_DUST);
				entries.accept(SpectrumItems.MIDNIGHT_ABERRATION);
				entries.accept(SpectrumItems.MIDNIGHT_ABERRATION.getStableStack());
				entries.accept(SpectrumItems.MIDNIGHT_CHIP);
				
				entries.accept(SpectrumItems.SHIMMERSTONE_GEM);
				entries.accept(SpectrumItems.PALTAERIA_FRAGMENTS);
				entries.accept(SpectrumItems.PALTAERIA_GEM);
				entries.accept(SpectrumItems.STRATINE_FRAGMENTS);
				entries.accept(SpectrumItems.STRATINE_GEM);
				
				entries.accept(SpectrumItems.HIBERNATING_JADE_VINE_BULB);
				entries.accept(SpectrumItems.GERMINATED_JADE_VINE_BULB);
				entries.accept(SpectrumItems.JADE_VINE_PETALS);
				entries.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_BULB.get());
				entries.accept(SpectrumBlocks.JADEITE_LOTUS_BULB.get());
				entries.accept(SpectrumItems.JADEITE_PETALS);
				
				entries.accept(SpectrumItems.MERMAIDS_GEM);
				entries.accept(SpectrumItems.STORM_STONE);
				entries.accept(SpectrumItems.DOOMBLOOM_SEED);
				entries.accept(SpectrumItems.RESPLENDENT_FEATHER);
				entries.accept(SpectrumItems.DRAGONBONE_CHUNK);
				entries.accept(SpectrumItems.BONE_ASH);
				entries.accept(SpectrumItems.DOWNSTONE_FRAGMENTS);
				entries.accept(SpectrumItems.RESONANCE_SHARD);
				entries.accept(SpectrumItems.AETHER_VESTIGES);
				entries.accept(SpectrumItems.MOONSTONE_CORE);
				
				entries.accept(SpectrumItems.LIQUID_CRYSTAL_BUCKET);
				entries.accept(SpectrumItems.GOO_BUCKET);
				entries.accept(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET);
				entries.accept(SpectrumItems.DRAGONROT_BUCKET);
			}).build();
	
	public static final ItemSubGroup PURE_RESOURCES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_PURE_RESOURCES, Component.translatable("itemGroup.pastel.pure_resources"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.PURE_COAL);
				entries.accept(SpectrumBlocks.SMALL_COAL_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_COAL_BUD.get());
				entries.accept(SpectrumBlocks.COAL_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_COAL_BLOCK.get());
				entries.accept(SpectrumItems.PURE_COPPER);
				entries.accept(SpectrumBlocks.SMALL_COPPER_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_COPPER_BUD.get());
				entries.accept(SpectrumBlocks.COPPER_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_COPPER_BLOCK.get());
				entries.accept(SpectrumItems.PURE_IRON);
				entries.accept(SpectrumBlocks.SMALL_IRON_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_IRON_BUD.get());
				entries.accept(SpectrumBlocks.IRON_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_IRON_BLOCK.get());
				entries.accept(SpectrumItems.PURE_GOLD);
				entries.accept(SpectrumBlocks.SMALL_GOLD_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_GOLD_BUD.get());
				entries.accept(SpectrumBlocks.GOLD_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_GOLD_BLOCK.get());
				entries.accept(SpectrumItems.PURE_LAPIS);
				entries.accept(SpectrumBlocks.SMALL_LAPIS_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_LAPIS_BUD.get());
				entries.accept(SpectrumBlocks.LAPIS_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_LAPIS_BLOCK.get());
				entries.accept(SpectrumItems.PURE_REDSTONE);
				entries.accept(SpectrumBlocks.SMALL_REDSTONE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_REDSTONE_BUD.get());
				entries.accept(SpectrumBlocks.REDSTONE_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_REDSTONE_BLOCK.get());
				entries.accept(SpectrumItems.PURE_DIAMOND);
				entries.accept(SpectrumBlocks.SMALL_DIAMOND_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_DIAMOND_BUD.get());
				entries.accept(SpectrumBlocks.DIAMOND_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_DIAMOND_BLOCK.get());
				entries.accept(SpectrumItems.PURE_EMERALD);
				entries.accept(SpectrumBlocks.SMALL_EMERALD_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_EMERALD_BUD.get());
				entries.accept(SpectrumBlocks.EMERALD_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_EMERALD_BLOCK.get());
				
				entries.accept(SpectrumItems.PURE_PRISMARINE);
				entries.accept(SpectrumBlocks.SMALL_PRISMARINE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_PRISMARINE_BUD.get());
				entries.accept(SpectrumBlocks.PRISMARINE_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_PRISMARINE_BLOCK.get());
				
				entries.accept(SpectrumItems.PURE_QUARTZ);
				entries.accept(SpectrumBlocks.SMALL_QUARTZ_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_QUARTZ_BUD.get());
				entries.accept(SpectrumBlocks.QUARTZ_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_QUARTZ_BLOCK.get());
				entries.accept(SpectrumItems.PURE_GLOWSTONE);
				entries.accept(SpectrumBlocks.SMALL_GLOWSTONE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_GLOWSTONE_BUD.get());
				entries.accept(SpectrumBlocks.GLOWSTONE_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_GLOWSTONE_BLOCK.get());
				entries.accept(SpectrumItems.PURE_NETHERITE_SCRAP);
				entries.accept(SpectrumBlocks.SMALL_NETHERITE_SCRAP_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_NETHERITE_SCRAP_BUD.get());
				entries.accept(SpectrumBlocks.NETHERITE_SCRAP_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_NETHERITE_SCRAP_BLOCK.get());
				
				entries.accept(SpectrumItems.PURE_ECHO);
				entries.accept(SpectrumBlocks.SMALL_ECHO_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_ECHO_BUD.get());
				entries.accept(SpectrumBlocks.ECHO_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_ECHO_BLOCK.get());
				
				if (SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.AE2_ID)) {
					entries.accept(AE2Compat.PURE_CERTUS_QUARTZ);
					entries.accept(AE2Compat.SMALL_CERTUS_QUARTZ_BUD);
					entries.accept(AE2Compat.LARGE_CERTUS_QUARTZ_BUD);
					entries.accept(AE2Compat.CERTUS_QUARTZ_CLUSTER);
					entries.accept(AE2Compat.PURE_CERTUS_QUARTZ_BLOCK);
					
					entries.accept(AE2Compat.PURE_FLUIX);
					entries.accept(AE2Compat.SMALL_FLUIX_BUD);
					entries.accept(AE2Compat.LARGE_FLUIX_BUD);
					entries.accept(AE2Compat.FLUIX_CLUSTER);
					entries.accept(AE2Compat.PURE_FLUIX_BLOCK);
				}
				
				if (SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.CREATE_ID)) {
					entries.accept(CreateCompat.PURE_ZINC);
					entries.accept(CreateCompat.SMALL_ZINC_BUD);
					entries.accept(CreateCompat.LARGE_ZINC_BUD);
					entries.accept(CreateCompat.ZINC_CLUSTER);
					entries.accept(CreateCompat.PURE_ZINC_BLOCK);
				}
				
				if (SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.GOBBER_ID)) {
					entries.accept(GobberCompat.PURE_GLOBETTE);
					entries.accept(GobberCompat.SMALL_GLOBETTE_BUD);
					entries.accept(GobberCompat.LARGE_GLOBETTE_BUD);
					entries.accept(GobberCompat.GLOBETTE_CLUSTER);
					entries.accept(GobberCompat.PURE_GLOBETTE_BLOCK);
					
					entries.accept(GobberCompat.PURE_GLOBETTE_NETHER);
					entries.accept(GobberCompat.SMALL_GLOBETTE_NETHER_BUD);
					entries.accept(GobberCompat.LARGE_GLOBETTE_NETHER_BUD);
					entries.accept(GobberCompat.GLOBETTE_NETHER_CLUSTER);
					entries.accept(GobberCompat.PURE_GLOBETTE_NETHER_BLOCK);
					
					entries.accept(GobberCompat.PURE_GLOBETTE_END);
					entries.accept(GobberCompat.SMALL_GLOBETTE_END_BUD);
					entries.accept(GobberCompat.LARGE_GLOBETTE_END_BUD);
					entries.accept(GobberCompat.GLOBETTE_END_CLUSTER);
					entries.accept(GobberCompat.PURE_GLOBETTE_END_BLOCK);
				}
				
			}).build();
	
	public static final ItemSubGroup BLOCKS = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_BLOCKS, Component.translatable("itemGroup.pastel.blocks"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumBlocks.SMOOTH_BASALT_SLAB.get());
				entries.accept(SpectrumBlocks.SMOOTH_BASALT_WALL.get());
				entries.accept(SpectrumBlocks.SMOOTH_BASALT_STAIRS.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT_PILLAR.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT_CREST.get());
				entries.accept(SpectrumBlocks.CHISELED_POLISHED_BASALT.get());
				entries.accept(SpectrumBlocks.NOTCHED_POLISHED_BASALT.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT_SLAB.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT_WALL.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT_STAIRS.get());
				entries.accept(SpectrumBlocks.BASALT_BRICKS.get());
				entries.accept(SpectrumBlocks.BASALT_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.BASALT_BRICK_WALL.get());
				entries.accept(SpectrumBlocks.BASALT_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.CRACKED_BASALT_BRICKS.get());
				entries.accept(SpectrumBlocks.BASALT_TILES.get());
				entries.accept(SpectrumBlocks.BASALT_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.BASALT_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.BASALT_TILE_WALL.get());
				entries.accept(SpectrumBlocks.PLANED_BASALT.get());
				entries.accept(SpectrumBlocks.PLANED_BASALT_SLAB.get());
				entries.accept(SpectrumBlocks.PLANED_BASALT_STAIRS.get());
				entries.accept(SpectrumBlocks.PLANED_BASALT_WALL.get());
				entries.accept(SpectrumBlocks.CRACKED_BASALT_TILES.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT_BUTTON.get());
				entries.accept(SpectrumBlocks.POLISHED_BASALT_PRESSURE_PLATE.get());
				
				entries.accept(SpectrumBlocks.CALCITE_SLAB.get());
				entries.accept(SpectrumBlocks.CALCITE_WALL.get());
				entries.accept(SpectrumBlocks.CALCITE_STAIRS.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE_PILLAR.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE_CREST.get());
				entries.accept(SpectrumBlocks.CHISELED_POLISHED_CALCITE.get());
				entries.accept(SpectrumBlocks.NOTCHED_POLISHED_CALCITE.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE_SLAB.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE_WALL.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE_STAIRS.get());
				entries.accept(SpectrumBlocks.CALCITE_BRICKS.get());
				entries.accept(SpectrumBlocks.CALCITE_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.CALCITE_BRICK_WALL.get());
				entries.accept(SpectrumBlocks.CALCITE_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.CRACKED_CALCITE_BRICKS.get());
				entries.accept(SpectrumBlocks.CALCITE_TILES.get());
				entries.accept(SpectrumBlocks.CALCITE_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.CALCITE_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.CALCITE_TILE_WALL.get());
				entries.accept(SpectrumBlocks.PLANED_CALCITE.get());
				entries.accept(SpectrumBlocks.PLANED_CALCITE_SLAB.get());
				entries.accept(SpectrumBlocks.PLANED_CALCITE_STAIRS.get());
				entries.accept(SpectrumBlocks.PLANED_CALCITE_WALL.get());
				entries.accept(SpectrumBlocks.CRACKED_CALCITE_TILES.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE_BUTTON.get());
				entries.accept(SpectrumBlocks.POLISHED_CALCITE_PRESSURE_PLATE.get());
				
				entries.accept(SpectrumBlocks.BLACKSLAG.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_SLAB.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_WALL.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_STAIRS.get());
				entries.accept(SpectrumBlocks.COBBLED_BLACKSLAG.get());
				entries.accept(SpectrumBlocks.COBBLED_BLACKSLAG_STAIRS.get());
				entries.accept(SpectrumBlocks.COBBLED_BLACKSLAG_SLAB.get());
				entries.accept(SpectrumBlocks.COBBLED_BLACKSLAG_WALL.get());
				entries.accept(SpectrumBlocks.POLISHED_BLACKSLAG.get());
				entries.accept(SpectrumBlocks.POLISHED_BLACKSLAG_STAIRS.get());
				entries.accept(SpectrumBlocks.POLISHED_BLACKSLAG_SLAB.get());
				entries.accept(SpectrumBlocks.POLISHED_BLACKSLAG_WALL.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_TILES.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_TILE_WALL.get());
				entries.accept(SpectrumBlocks.CRACKED_BLACKSLAG_TILES.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_BRICKS.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_BRICK_WALL.get());
				entries.accept(SpectrumBlocks.CRACKED_BLACKSLAG_BRICKS.get());
				entries.accept(SpectrumBlocks.POLISHED_BLACKSLAG_PILLAR.get());
				entries.accept(SpectrumBlocks.CHISELED_POLISHED_BLACKSLAG.get());
				entries.accept(SpectrumBlocks.ANCIENT_CHISELED_POLISHED_BLACKSLAG.get());
				entries.accept(SpectrumBlocks.POLISHED_BLACKSLAG_BUTTON.get());
				entries.accept(SpectrumBlocks.POLISHED_BLACKSLAG_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.INFESTED_BLACKSLAG.get());
				entries.accept(SpectrumBlocks.SHALE_CLAY.get());
				entries.accept(SpectrumBlocks.TILLED_SHALE_CLAY.get());
				entries.accept(SpectrumBlocks.POLISHED_SHALE_CLAY.get());
				entries.accept(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY.get());
				entries.accept(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY.get());
				entries.accept(SpectrumBlocks.POLISHED_SHALE_CLAY_STAIRS.get());
				entries.accept(SpectrumBlocks.POLISHED_SHALE_CLAY_SLAB.get());
				entries.accept(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS.get());
				entries.accept(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB.get());
				entries.accept(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY_STAIRS.get());
				entries.accept(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB.get());
				entries.accept(SpectrumBlocks.SHALE_CLAY_BRICKS.get());
				entries.accept(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICKS.get());
				entries.accept(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICKS.get());
				entries.accept(SpectrumBlocks.SHALE_CLAY_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.SHALE_CLAY_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.SHALE_CLAY_TILES.get());
				entries.accept(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILES.get());
				entries.accept(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILES.get());
				entries.accept(SpectrumBlocks.SHALE_CLAY_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.SHALE_CLAY_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.POLISHED_BONE_ASH.get());
				entries.accept(SpectrumBlocks.POLISHED_BONE_ASH_SLAB.get());
				entries.accept(SpectrumBlocks.POLISHED_BONE_ASH_STAIRS.get());
				entries.accept(SpectrumBlocks.POLISHED_BONE_ASH_WALL.get());
				entries.accept(SpectrumBlocks.BONE_ASH_BRICKS.get());
				entries.accept(SpectrumBlocks.BONE_ASH_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.BONE_ASH_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.BONE_ASH_BRICK_WALL.get());
				entries.accept(SpectrumBlocks.BONE_ASH_TILES.get());
				entries.accept(SpectrumBlocks.BONE_ASH_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.BONE_ASH_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.BONE_ASH_TILE_WALL.get());
				entries.accept(SpectrumBlocks.POLISHED_BONE_ASH_PILLAR.get());
				entries.accept(SpectrumBlocks.BONE_ASH_SHINGLES.get());
				entries.accept(SpectrumBlocks.BLACK_MATERIA.get());
				entries.accept(SpectrumBlocks.SLUSH.get());
				entries.accept(SpectrumBlocks.OVERGROWN_SLUSH.get());
				entries.accept(SpectrumBlocks.TILLED_SLUSH.get());
				entries.accept(SpectrumBlocks.HORNSLAKE.get());
				
				entries.accept(SpectrumItems.ASH_FLAKES);
				entries.accept(SpectrumBlocks.ASH.get());
				entries.accept(SpectrumBlocks.ASH_PILE.get());
				
				entries.accept(SpectrumBlocks.ROCK_CRYSTAL.get());
				
				entries.accept(SpectrumItems.PYRITE_CHUNK);
				entries.accept(SpectrumBlocks.PYRITE.get());
				entries.accept(SpectrumBlocks.PYRITE_SLAB.get());
				entries.accept(SpectrumBlocks.PYRITE_STAIRS.get());
				entries.accept(SpectrumBlocks.PYRITE_WALL.get());
				entries.accept(SpectrumBlocks.PYRITE_PILE.get());
				entries.accept(SpectrumBlocks.PYRITE_TILES.get());
				entries.accept(SpectrumBlocks.PYRITE_TILES_SLAB.get());
				entries.accept(SpectrumBlocks.PYRITE_TILES_STAIRS.get());
				entries.accept(SpectrumBlocks.PYRITE_TILES_WALL.get());
				entries.accept(SpectrumBlocks.PYRITE_PLATING.get());
				entries.accept(SpectrumBlocks.PYRITE_TUBING.get());
				entries.accept(SpectrumBlocks.PYRITE_RELIEF.get());
				entries.accept(SpectrumBlocks.PYRITE_STACK.get());
				entries.accept(SpectrumBlocks.PYRITE_PANELING.get());
				entries.accept(SpectrumBlocks.PYRITE_VENT.get());
				entries.accept(SpectrumBlocks.PYRITE_RIPPER.get());
				entries.accept(SpectrumBlocks.PYRITE_PROJECTOR.get());
				
				entries.accept(SpectrumBlocks.BASAL_MARBLE.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_STAIRS.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_SLAB.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_WALL.get());
				entries.accept(SpectrumBlocks.POLISHED_BASAL_MARBLE.get());
				entries.accept(SpectrumBlocks.POLISHED_BASAL_MARBLE_STAIRS.get());
				entries.accept(SpectrumBlocks.POLISHED_BASAL_MARBLE_SLAB.get());
				entries.accept(SpectrumBlocks.POLISHED_BASAL_MARBLE_WALL.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_PILLAR.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_TILES.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_TILE_STAIRS.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_TILE_SLAB.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_TILE_WALL.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_BRICKS.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_BRICK_STAIRS.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_BRICK_SLAB.get());
				entries.accept(SpectrumBlocks.BASAL_MARBLE_BRICK_WALL.get());
				entries.accept(SpectrumBlocks.LONGING_CHIMERA.get());
				
				entries.accept(SpectrumBlocks.DRAGONBONE.get());
				entries.accept(SpectrumBlocks.CRACKED_DRAGONBONE.get());
				entries.accept(SpectrumBlocks.SAWBLADE_GRASS.get());
				entries.accept(SpectrumBlocks.OVERGROWN_BLACKSLAG.get());
				entries.accept(SpectrumBlocks.SHIMMEL.get());
				entries.accept(SpectrumBlocks.ASHEN_BLACKSLAG.get());
				entries.accept(SpectrumBlocks.FLAYED_EARTH.get());
				entries.accept(SpectrumBlocks.SLATE_NOXSHROOM.get());
				entries.accept(SpectrumBlocks.SLATE_NOXCAP_BLOCK.get());
				entries.accept(SpectrumBlocks.SLATE_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.STRIPPED_SLATE_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.SLATE_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.SLATE_NOXCAP_GILLS.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_PLANKS.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_STAIRS.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_SLAB.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_FENCE.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_DOOR.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_TRAPDOOR.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_BUTTON.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_PILLAR.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_AMPHORA.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_LANTERN.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_LIGHT.get());
				entries.accept(SpectrumBlocks.SLATE_NOXWOOD_LAMP.get());
				entries.accept(SpectrumBlocks.EBONY_NOXSHROOM.get());
				entries.accept(SpectrumBlocks.EBONY_NOXCAP_BLOCK.get());
				entries.accept(SpectrumBlocks.EBONY_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.STRIPPED_EBONY_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.EBONY_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.EBONY_NOXCAP_GILLS.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_PLANKS.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_STAIRS.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_SLAB.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_FENCE.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_DOOR.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_TRAPDOOR.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_BUTTON.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_PILLAR.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_AMPHORA.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_LANTERN.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_LIGHT.get());
				entries.accept(SpectrumBlocks.EBONY_NOXWOOD_LAMP.get());
				entries.accept(SpectrumBlocks.IVORY_NOXSHROOM.get());
				entries.accept(SpectrumBlocks.IVORY_NOXCAP_BLOCK.get());
				entries.accept(SpectrumBlocks.IVORY_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.STRIPPED_IVORY_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.IVORY_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.IVORY_NOXCAP_GILLS.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_PLANKS.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_STAIRS.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_SLAB.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_FENCE.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_DOOR.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_TRAPDOOR.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_BUTTON.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_PILLAR.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_AMPHORA.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_LANTERN.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_LIGHT.get());
				entries.accept(SpectrumBlocks.IVORY_NOXWOOD_LAMP.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXSHROOM.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXCAP_BLOCK.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXCAP_GILLS.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_PLANKS.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_STAIRS.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_SLAB.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_DOOR.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_TRAPDOOR.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_BUTTON.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_PILLAR.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_AMPHORA.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_LANTERN.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_LIGHT.get());
				entries.accept(SpectrumBlocks.CHESTNUT_NOXWOOD_LAMP.get());
				
				entries.accept(SpectrumBlocks.WEEPING_GALA_SPRIG.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_LEAVES.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_WEEPING_GALA_LOG.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_WEEPING_GALA_WOOD.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_PLANKS.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_STAIRS.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_DOOR.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_FENCE.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_TRAPDOOR.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_BUTTON.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_SLAB.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_PILLAR.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_BARREL.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_AMPHORA.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_LANTERN.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_LAMP.get());
				entries.accept(SpectrumBlocks.WEEPING_GALA_LIGHT.get());
				
				entries.accept(SpectrumBlocks.SMALL_RED_DRAGONJAG.get());
				entries.accept(SpectrumBlocks.SMALL_YELLOW_DRAGONJAG.get());
				entries.accept(SpectrumBlocks.SMALL_PINK_DRAGONJAG.get());
				entries.accept(SpectrumBlocks.SMALL_PURPLE_DRAGONJAG.get());
				entries.accept(SpectrumBlocks.SMALL_BLACK_DRAGONJAG.get());
				entries.accept(SpectrumBlocks.BRISTLE_SPROUTS.get());
				entries.accept(SpectrumBlocks.SNAPPING_IVY.get());
				entries.accept(SpectrumBlocks.SWEET_PEA.get());
				entries.accept(SpectrumBlocks.APRICOTTI.get());
				entries.accept(SpectrumBlocks.HUMMING_BELL.get());
				entries.accept(SpectrumBlocks.HUMMINGSTONE.get());
				entries.accept(SpectrumBlocks.WAXED_HUMMINGSTONE.get());
				entries.accept(SpectrumBlocks.HUMMINGSTONE_GLASS.get());
				entries.accept(SpectrumBlocks.HUMMINGSTONE_GLASS_PANE.get());
				entries.accept(SpectrumBlocks.MOSS_BALL.get());
				entries.accept(SpectrumBlocks.GIANT_MOSS_BALL.get());
				entries.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_STEM.get());
				entries.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_LEAVES.get());
				entries.accept(SpectrumBlocks.VARIA_SPROUT.get());
				entries.accept(SpectrumBlocks.JADEITE_LOTUS_STEM.get());
				entries.accept(SpectrumBlocks.JADEITE_LOTUS_FLOWER.get());
			}).build();
	
	public static final ItemSubGroup DECORATION = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_DECORATION, Component.translatable("itemGroup.pastel.decoration"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumBlocks.POLISHED_TOPAZ_BLOCK.get());
				entries.accept(SpectrumBlocks.POLISHED_AMETHYST_BLOCK.get());
				entries.accept(SpectrumBlocks.POLISHED_CITRINE_BLOCK.get());
				entries.accept(SpectrumBlocks.POLISHED_ONYX_BLOCK.get());
				entries.accept(SpectrumBlocks.POLISHED_MOONSTONE_BLOCK.get());
				
				entries.accept(SpectrumBlocks.VEGETAL_BLOCK.get());
				entries.accept(SpectrumBlocks.NEOLITH_BLOCK.get());
				entries.accept(SpectrumBlocks.BEDROCK_DUST_BLOCK.get());
				
				entries.accept(SpectrumBlocks.SHIMMERSTONE_BLOCK.get());
				entries.accept(SpectrumBlocks.AZURITE_BLOCK.get());
				entries.accept(SpectrumBlocks.MALACHITE_BLOCK.get());
				entries.accept(SpectrumBlocks.BLOODSTONE_BLOCK.get());
				entries.accept(SpectrumBlocks.BISMUTH_BLOCK.get());
				
				entries.accept(SpectrumBlocks.STRATINE_FLOATBLOCK.get());
				entries.accept(SpectrumBlocks.PALTAERIA_FLOATBLOCK.get());
				entries.accept(SpectrumBlocks.HOVER_BLOCK.get());
				
				entries.accept(SpectrumBlocks.TOPAZ_CALCITE_LIGHT.get());
				entries.accept(SpectrumBlocks.AMETHYST_CALCITE_LIGHT.get());
				entries.accept(SpectrumBlocks.CITRINE_CALCITE_LIGHT.get());
				entries.accept(SpectrumBlocks.ONYX_CALCITE_LIGHT.get());
				entries.accept(SpectrumBlocks.MOONSTONE_CALCITE_LIGHT.get());
				entries.accept(SpectrumBlocks.TOPAZ_BASALT_LIGHT.get());
				entries.accept(SpectrumBlocks.AMETHYST_BASALT_LIGHT.get());
				entries.accept(SpectrumBlocks.CITRINE_BASALT_LIGHT.get());
				entries.accept(SpectrumBlocks.ONYX_BASALT_LIGHT.get());
				entries.accept(SpectrumBlocks.MOONSTONE_BASALT_LIGHT.get());
				
				entries.accept(SpectrumBlocks.BASALT_SHIMMERSTONE_LIGHT.get());
				entries.accept(SpectrumBlocks.CALCITE_SHIMMERSTONE_LIGHT.get());
				entries.accept(SpectrumBlocks.STONE_SHIMMERSTONE_LIGHT.get());
				entries.accept(SpectrumBlocks.GRANITE_SHIMMERSTONE_LIGHT.get());
				entries.accept(SpectrumBlocks.DIORITE_SHIMMERSTONE_LIGHT.get());
				entries.accept(SpectrumBlocks.ANDESITE_SHIMMERSTONE_LIGHT.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_SHIMMERSTONE_LIGHT.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_SHIMMERSTONE_LIGHT.get());
				
				entries.accept(SpectrumBlocks.TOPAZ_CHISELED_BASALT.get());
				entries.accept(SpectrumBlocks.AMETHYST_CHISELED_BASALT.get());
				entries.accept(SpectrumBlocks.CITRINE_CHISELED_BASALT.get());
				entries.accept(SpectrumBlocks.ONYX_CHISELED_BASALT.get());
				entries.accept(SpectrumBlocks.MOONSTONE_CHISELED_BASALT.get());
				entries.accept(SpectrumBlocks.TOPAZ_CHISELED_CALCITE.get());
				entries.accept(SpectrumBlocks.AMETHYST_CHISELED_CALCITE.get());
				entries.accept(SpectrumBlocks.CITRINE_CHISELED_CALCITE.get());
				entries.accept(SpectrumBlocks.ONYX_CHISELED_CALCITE.get());
				entries.accept(SpectrumBlocks.MOONSTONE_CHISELED_CALCITE.get());
				
				entries.accept(SpectrumBlocks.TOPAZ_GLASS.get());
				entries.accept(SpectrumBlocks.AMETHYST_GLASS.get());
				entries.accept(SpectrumBlocks.CITRINE_GLASS.get());
				entries.accept(SpectrumBlocks.ONYX_GLASS.get());
				entries.accept(SpectrumBlocks.MOONSTONE_GLASS.get());
				entries.accept(SpectrumBlocks.RADIANT_GLASS.get());
				
				entries.accept(SpectrumBlocks.TOPAZ_GLASS_PANE.get());
				entries.accept(SpectrumBlocks.AMETHYST_GLASS_PANE.get());
				entries.accept(SpectrumBlocks.CITRINE_GLASS_PANE.get());
				entries.accept(SpectrumBlocks.ONYX_GLASS_PANE.get());
				entries.accept(SpectrumBlocks.MOONSTONE_GLASS_PANE.get());
				entries.accept(SpectrumBlocks.RADIANT_GLASS_PANE.get());
				
				entries.accept(SpectrumBlocks.TOPAZ_CHIME.get());
				entries.accept(SpectrumBlocks.AMETHYST_CHIME.get());
				entries.accept(SpectrumBlocks.CITRINE_CHIME.get());
				entries.accept(SpectrumBlocks.ONYX_CHIME.get());
				entries.accept(SpectrumBlocks.MOONSTONE_CHIME.get());
				
				entries.accept(SpectrumBlocks.AMETHYST_PYLON.get());
				entries.accept(SpectrumBlocks.TOPAZ_PYLON.get());
				entries.accept(SpectrumBlocks.CITRINE_PYLON.get());
				entries.accept(SpectrumBlocks.ONYX_PYLON.get());
				entries.accept(SpectrumBlocks.MOONSTONE_PYLON.get());
				
				entries.accept(SpectrumBlocks.JADE_VINE_PETAL_BLOCK.get());
				entries.accept(SpectrumBlocks.JADE_VINE_PETAL_CARPET.get());
				
				entries.accept(SpectrumBlocks.JADEITE_PETAL_BLOCK.get());
				entries.accept(SpectrumBlocks.JADEITE_PETAL_CARPET.get());
				
				entries.accept(SpectrumBlocks.RESPLENDENT_BLOCK.get());
				entries.accept(SpectrumBlocks.RESPLENDENT_CUSHION.get());
				entries.accept(SpectrumBlocks.RESPLENDENT_CARPET.get());
				entries.accept(SpectrumBlocks.RESPLENDENT_BED.get());
				
				entries.accept(SpectrumBlocks.WHITE_BLOCK.get());
				entries.accept(SpectrumBlocks.ORANGE_BLOCK.get());
				entries.accept(SpectrumBlocks.MAGENTA_BLOCK.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_BLOCK.get());
				entries.accept(SpectrumBlocks.YELLOW_BLOCK.get());
				entries.accept(SpectrumBlocks.LIME_BLOCK.get());
				entries.accept(SpectrumBlocks.PINK_BLOCK.get());
				entries.accept(SpectrumBlocks.GRAY_BLOCK.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_BLOCK.get());
				entries.accept(SpectrumBlocks.CYAN_BLOCK.get());
				entries.accept(SpectrumBlocks.PURPLE_BLOCK.get());
				entries.accept(SpectrumBlocks.BLUE_BLOCK.get());
				entries.accept(SpectrumBlocks.BROWN_BLOCK.get());
				entries.accept(SpectrumBlocks.GREEN_BLOCK.get());
				entries.accept(SpectrumBlocks.RED_BLOCK.get());
				entries.accept(SpectrumBlocks.BLACK_BLOCK.get());
				entries.accept(SpectrumBlocks.WHITE_LAMP.get());
				entries.accept(SpectrumBlocks.ORANGE_LAMP.get());
				entries.accept(SpectrumBlocks.MAGENTA_LAMP.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_LAMP.get());
				entries.accept(SpectrumBlocks.YELLOW_LAMP.get());
				entries.accept(SpectrumBlocks.LIME_LAMP.get());
				entries.accept(SpectrumBlocks.PINK_LAMP.get());
				entries.accept(SpectrumBlocks.GRAY_LAMP.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_LAMP.get());
				entries.accept(SpectrumBlocks.CYAN_LAMP.get());
				entries.accept(SpectrumBlocks.PURPLE_LAMP.get());
				entries.accept(SpectrumBlocks.BLUE_LAMP.get());
				entries.accept(SpectrumBlocks.BROWN_LAMP.get());
				entries.accept(SpectrumBlocks.GREEN_LAMP.get());
				entries.accept(SpectrumBlocks.RED_LAMP.get());
				entries.accept(SpectrumBlocks.BLACK_LAMP.get());
				entries.accept(SpectrumBlocks.WHITE_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.ORANGE_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.MAGENTA_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.YELLOW_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.LIME_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.PINK_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.GRAY_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.CYAN_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.PURPLE_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.BLUE_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.BROWN_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.GREEN_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.RED_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.BLACK_GLOWBLOCK.get());
				entries.accept(SpectrumBlocks.WHITE_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.ORANGE_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.MAGENTA_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.YELLOW_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.LIME_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.PINK_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.GRAY_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.CYAN_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.PURPLE_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.BLUE_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.BROWN_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.GREEN_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.RED_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.BLACK_SPORE_BLOSSOM.get());
				entries.accept(SpectrumBlocks.RESONANT_LILY.get());
				entries.accept(SpectrumItems.PHANTOM_FRAME);
				entries.accept(SpectrumItems.GLOW_PHANTOM_FRAME);
				entries.accept(SpectrumItems.LOGO_BANNER_PATTERN);
				entries.accept(SpectrumItems.AMETHYST_SHARD_BANNER_PATTERN);
				entries.accept(SpectrumItems.AMETHYST_CLUSTER_BANNER_PATTERN);
				entries.accept(SpectrumItems.ASTROLOGER_BANNER_PATTERN);
				entries.accept(SpectrumItems.VELVET_ASTROLOGER_BANNER_PATTERN);
				entries.accept(SpectrumItems.POISONBLOOM_BANNER_PATTERN);
				entries.accept(SpectrumItems.DEEP_LIGHT_BANNER_PATTERN);
				entries.accept(SpectrumItems.MUSIC_DISC_DISCOVERY);
				entries.accept(SpectrumItems.MUSIC_DISC_CREDITS);
				entries.accept(SpectrumItems.MUSIC_DISC_DIVINITY);
			}).build();
	
	public static final ItemSubGroup COLORED_WOOD = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_COLORED_WOOD, Component.translatable("itemGroup.pastel.colored_wood"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumBlocks.WHITE_LOG.get());
				entries.accept(SpectrumBlocks.ORANGE_LOG.get());
				entries.accept(SpectrumBlocks.MAGENTA_LOG.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_LOG.get());
				entries.accept(SpectrumBlocks.YELLOW_LOG.get());
				entries.accept(SpectrumBlocks.LIME_LOG.get());
				entries.accept(SpectrumBlocks.PINK_LOG.get());
				entries.accept(SpectrumBlocks.GRAY_LOG.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_LOG.get());
				entries.accept(SpectrumBlocks.CYAN_LOG.get());
				entries.accept(SpectrumBlocks.PURPLE_LOG.get());
				entries.accept(SpectrumBlocks.BLUE_LOG.get());
				entries.accept(SpectrumBlocks.BROWN_LOG.get());
				entries.accept(SpectrumBlocks.GREEN_LOG.get());
				entries.accept(SpectrumBlocks.RED_LOG.get());
				entries.accept(SpectrumBlocks.BLACK_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_WHITE_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_ORANGE_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_MAGENTA_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_LIGHT_BLUE_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_YELLOW_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_LIME_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_PINK_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_GRAY_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_LIGHT_GRAY_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_CYAN_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_PURPLE_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_BLUE_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_BROWN_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_GREEN_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_RED_LOG.get());
				entries.accept(SpectrumBlocks.STRIPPED_BLACK_LOG.get());
				entries.accept(SpectrumBlocks.WHITE_WOOD.get());
				entries.accept(SpectrumBlocks.ORANGE_WOOD.get());
				entries.accept(SpectrumBlocks.MAGENTA_WOOD.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_WOOD.get());
				entries.accept(SpectrumBlocks.YELLOW_WOOD.get());
				entries.accept(SpectrumBlocks.LIME_WOOD.get());
				entries.accept(SpectrumBlocks.PINK_WOOD.get());
				entries.accept(SpectrumBlocks.GRAY_WOOD.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_WOOD.get());
				entries.accept(SpectrumBlocks.CYAN_WOOD.get());
				entries.accept(SpectrumBlocks.PURPLE_WOOD.get());
				entries.accept(SpectrumBlocks.BLUE_WOOD.get());
				entries.accept(SpectrumBlocks.BROWN_WOOD.get());
				entries.accept(SpectrumBlocks.GREEN_WOOD.get());
				entries.accept(SpectrumBlocks.RED_WOOD.get());
				entries.accept(SpectrumBlocks.BLACK_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_WHITE_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_ORANGE_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_MAGENTA_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_LIGHT_BLUE_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_YELLOW_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_LIME_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_PINK_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_GRAY_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_LIGHT_GRAY_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_CYAN_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_PURPLE_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_BLUE_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_BROWN_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_GREEN_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_RED_WOOD.get());
				entries.accept(SpectrumBlocks.STRIPPED_BLACK_WOOD.get());
				entries.accept(SpectrumBlocks.WHITE_LEAVES.get());
				entries.accept(SpectrumBlocks.ORANGE_LEAVES.get());
				entries.accept(SpectrumBlocks.MAGENTA_LEAVES.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_LEAVES.get());
				entries.accept(SpectrumBlocks.YELLOW_LEAVES.get());
				entries.accept(SpectrumBlocks.LIME_LEAVES.get());
				entries.accept(SpectrumBlocks.PINK_LEAVES.get());
				entries.accept(SpectrumBlocks.GRAY_LEAVES.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_LEAVES.get());
				entries.accept(SpectrumBlocks.CYAN_LEAVES.get());
				entries.accept(SpectrumBlocks.PURPLE_LEAVES.get());
				entries.accept(SpectrumBlocks.BLUE_LEAVES.get());
				entries.accept(SpectrumBlocks.BROWN_LEAVES.get());
				entries.accept(SpectrumBlocks.GREEN_LEAVES.get());
				entries.accept(SpectrumBlocks.RED_LEAVES.get());
				entries.accept(SpectrumBlocks.BLACK_LEAVES.get());
				entries.accept(SpectrumBlocks.WHITE_SAPLING.get());
				entries.accept(SpectrumBlocks.ORANGE_SAPLING.get());
				entries.accept(SpectrumBlocks.MAGENTA_SAPLING.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_SAPLING.get());
				entries.accept(SpectrumBlocks.YELLOW_SAPLING.get());
				entries.accept(SpectrumBlocks.LIME_SAPLING.get());
				entries.accept(SpectrumBlocks.PINK_SAPLING.get());
				entries.accept(SpectrumBlocks.GRAY_SAPLING.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_SAPLING.get());
				entries.accept(SpectrumBlocks.CYAN_SAPLING.get());
				entries.accept(SpectrumBlocks.PURPLE_SAPLING.get());
				entries.accept(SpectrumBlocks.BLUE_SAPLING.get());
				entries.accept(SpectrumBlocks.BROWN_SAPLING.get());
				entries.accept(SpectrumBlocks.GREEN_SAPLING.get());
				entries.accept(SpectrumBlocks.RED_SAPLING.get());
				entries.accept(SpectrumBlocks.BLACK_SAPLING.get());
				entries.accept(SpectrumBlocks.WHITE_PLANKS.get());
				entries.accept(SpectrumBlocks.ORANGE_PLANKS.get());
				entries.accept(SpectrumBlocks.MAGENTA_PLANKS.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_PLANKS.get());
				entries.accept(SpectrumBlocks.YELLOW_PLANKS.get());
				entries.accept(SpectrumBlocks.LIME_PLANKS.get());
				entries.accept(SpectrumBlocks.PINK_PLANKS.get());
				entries.accept(SpectrumBlocks.GRAY_PLANKS.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_PLANKS.get());
				entries.accept(SpectrumBlocks.CYAN_PLANKS.get());
				entries.accept(SpectrumBlocks.PURPLE_PLANKS.get());
				entries.accept(SpectrumBlocks.BLUE_PLANKS.get());
				entries.accept(SpectrumBlocks.BROWN_PLANKS.get());
				entries.accept(SpectrumBlocks.GREEN_PLANKS.get());
				entries.accept(SpectrumBlocks.RED_PLANKS.get());
				entries.accept(SpectrumBlocks.BLACK_PLANKS.get());
				entries.accept(SpectrumBlocks.WHITE_STAIRS.get());
				entries.accept(SpectrumBlocks.ORANGE_STAIRS.get());
				entries.accept(SpectrumBlocks.MAGENTA_STAIRS.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_STAIRS.get());
				entries.accept(SpectrumBlocks.YELLOW_STAIRS.get());
				entries.accept(SpectrumBlocks.LIME_STAIRS.get());
				entries.accept(SpectrumBlocks.PINK_STAIRS.get());
				entries.accept(SpectrumBlocks.GRAY_STAIRS.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_STAIRS.get());
				entries.accept(SpectrumBlocks.CYAN_STAIRS.get());
				entries.accept(SpectrumBlocks.PURPLE_STAIRS.get());
				entries.accept(SpectrumBlocks.BLUE_STAIRS.get());
				entries.accept(SpectrumBlocks.BROWN_STAIRS.get());
				entries.accept(SpectrumBlocks.GREEN_STAIRS.get());
				entries.accept(SpectrumBlocks.RED_STAIRS.get());
				entries.accept(SpectrumBlocks.BLACK_STAIRS.get());
				entries.accept(SpectrumBlocks.WHITE_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.ORANGE_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.MAGENTA_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.YELLOW_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.LIME_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.PINK_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.GRAY_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.CYAN_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.PURPLE_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.BLUE_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.BROWN_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.GREEN_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.RED_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.BLACK_PRESSURE_PLATE.get());
				entries.accept(SpectrumBlocks.WHITE_FENCE.get());
				entries.accept(SpectrumBlocks.ORANGE_FENCE.get());
				entries.accept(SpectrumBlocks.MAGENTA_FENCE.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_FENCE.get());
				entries.accept(SpectrumBlocks.YELLOW_FENCE.get());
				entries.accept(SpectrumBlocks.LIME_FENCE.get());
				entries.accept(SpectrumBlocks.PINK_FENCE.get());
				entries.accept(SpectrumBlocks.GRAY_FENCE.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_FENCE.get());
				entries.accept(SpectrumBlocks.CYAN_FENCE.get());
				entries.accept(SpectrumBlocks.PURPLE_FENCE.get());
				entries.accept(SpectrumBlocks.BLUE_FENCE.get());
				entries.accept(SpectrumBlocks.BROWN_FENCE.get());
				entries.accept(SpectrumBlocks.GREEN_FENCE.get());
				entries.accept(SpectrumBlocks.RED_FENCE.get());
				entries.accept(SpectrumBlocks.BLACK_FENCE.get());
				entries.accept(SpectrumBlocks.WHITE_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.ORANGE_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.MAGENTA_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.YELLOW_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.LIME_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.PINK_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.GRAY_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.CYAN_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.PURPLE_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.BLUE_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.BROWN_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.GREEN_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.RED_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.BLACK_FENCE_GATE.get());
				entries.accept(SpectrumBlocks.WHITE_BUTTON.get());
				entries.accept(SpectrumBlocks.ORANGE_BUTTON.get());
				entries.accept(SpectrumBlocks.MAGENTA_BUTTON.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_BUTTON.get());
				entries.accept(SpectrumBlocks.YELLOW_BUTTON.get());
				entries.accept(SpectrumBlocks.LIME_BUTTON.get());
				entries.accept(SpectrumBlocks.PINK_BUTTON.get());
				entries.accept(SpectrumBlocks.GRAY_BUTTON.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_BUTTON.get());
				entries.accept(SpectrumBlocks.CYAN_BUTTON.get());
				entries.accept(SpectrumBlocks.PURPLE_BUTTON.get());
				entries.accept(SpectrumBlocks.BLUE_BUTTON.get());
				entries.accept(SpectrumBlocks.BROWN_BUTTON.get());
				entries.accept(SpectrumBlocks.GREEN_BUTTON.get());
				entries.accept(SpectrumBlocks.RED_BUTTON.get());
				entries.accept(SpectrumBlocks.BLACK_BUTTON.get());
				entries.accept(SpectrumBlocks.WHITE_SLAB.get());
				entries.accept(SpectrumBlocks.ORANGE_SLAB.get());
				entries.accept(SpectrumBlocks.MAGENTA_SLAB.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_SLAB.get());
				entries.accept(SpectrumBlocks.YELLOW_SLAB.get());
				entries.accept(SpectrumBlocks.LIME_SLAB.get());
				entries.accept(SpectrumBlocks.PINK_SLAB.get());
				entries.accept(SpectrumBlocks.GRAY_SLAB.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_SLAB.get());
				entries.accept(SpectrumBlocks.CYAN_SLAB.get());
				entries.accept(SpectrumBlocks.PURPLE_SLAB.get());
				entries.accept(SpectrumBlocks.BLUE_SLAB.get());
				entries.accept(SpectrumBlocks.BROWN_SLAB.get());
				entries.accept(SpectrumBlocks.GREEN_SLAB.get());
				entries.accept(SpectrumBlocks.RED_SLAB.get());
				entries.accept(SpectrumBlocks.BLACK_SLAB.get());
			}).build();
	
	public static final ItemSubGroup MOB_HEADS = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_MOB_HEADS, Component.translatable("itemGroup.pastel.mob_heads"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				for (Block skullBlock : SpectrumSkullBlock.MOB_HEADS.values()) {
					entries.accept(skullBlock.asItem());
				}
			}).build();
	
	public static final ItemSubGroup CREATURES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_CREATURES, Component.translatable("itemGroup.pastel.creatures"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.EGG_LAYING_WOOLY_PIG_SPAWN_EGG);
				entries.accept(SpectrumItems.PRESERVATION_TURRET_SPAWN_EGG);
				entries.accept(SpectrumItems.KINDLING_SPAWN_EGG);
				entries.accept(SpectrumItems.LIZARD_SPAWN_EGG);
				entries.accept(SpectrumItems.ERASER_SPAWN_EGG);
				entries.accept(SpectrumItems.BUCKET_OF_ERASER);
				MemoryItem.appendEntries(displayContext.holders(), entries);
			}).build();
	
	public static final ItemSubGroup ENERGY = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_ENERGY, Component.translatable("itemGroup.pastel.energy"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.INK_FLASK);
				for (InkColor color : InkColors.all()) {
					entries.accept(SpectrumItems.INK_FLASK.getFullStack(color));
				}
				entries.accept(SpectrumItems.INK_ASSORTMENT);
				entries.accept(SpectrumItems.INK_ASSORTMENT.getFullStack());
				entries.accept(SpectrumItems.PIGMENT_PALETTE);
				entries.accept(SpectrumItems.PIGMENT_PALETTE.getFullStack());
				entries.accept(SpectrumItems.ARTISTS_PALETTE);
				entries.accept(SpectrumItems.ARTISTS_PALETTE.getFullStack());
			}).build();
	
	public static final ItemSubGroup CREATIVE = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_CREATIVE, Component.translatable("itemGroup.pastel.creative"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.PEDESTAL_TIER_1_STRUCTURE_PLACER);
				entries.accept(SpectrumItems.PEDESTAL_TIER_2_STRUCTURE_PLACER);
				entries.accept(SpectrumItems.PEDESTAL_TIER_3_STRUCTURE_PLACER);
				entries.accept(SpectrumItems.FUSION_SHRINE_STRUCTURE_PLACER);
				entries.accept(SpectrumItems.ENCHANTER_STRUCTURE_PLACER);
				entries.accept(SpectrumItems.SPIRIT_INSTILLER_STRUCTURE_PLACER);
				entries.accept(SpectrumItems.CINDERHEARTH_STRUCTURE_PLACER);
				
				entries.accept(SpectrumBlocks.CREATIVE_PARTICLE_SPAWNER.get());
				entries.accept(SpectrumItems.CREATIVE_INK_ASSORTMENT);
				entries.accept(SpectrumItems.PRIMORDIAL_LIGHTER);
				
				entries.accept(SpectrumItems.CONNECTION_NODE_CRYSTAL);
				entries.accept(SpectrumItems.STORAGE_NODE_CRYSTAL);
				entries.accept(SpectrumItems.PROVIDER_NODE_CRYSTAL);
				entries.accept(SpectrumItems.SENDER_NODE_CRYSTAL);
				entries.accept(SpectrumItems.GATHER_NODE_CRYSTAL);
				entries.accept(SpectrumItems.BUFFER_NODE_CRYSTAL);
				
				entries.accept(SpectrumBlocks.DOWNSTONE.get());
				entries.accept(SpectrumBlocks.PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.PRESERVATION_STAIRS.get());
				entries.accept(SpectrumBlocks.PRESERVATION_SLAB.get());
				entries.accept(SpectrumBlocks.PRESERVATION_WALL.get());
				entries.accept(SpectrumBlocks.PRESERVATION_BRICKS.get());
				entries.accept(SpectrumBlocks.SHIMMERING_PRESERVATION_BRICKS.get());
				entries.accept(SpectrumBlocks.POWDER_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.DIKE_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.DREAM_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.DEEP_LIGHT_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.TREASURE_ITEM_BOWL.get());
				entries.accept(SpectrumBlocks.PRESERVATION_GLASS.get());
				entries.accept(SpectrumBlocks.TINTED_PRESERVATION_GLASS.get());
				entries.accept(SpectrumBlocks.PRESERVATION_ROUNDEL.get());
				entries.accept(SpectrumBlocks.PRESERVATION_BLOCK_DETECTOR.get());
				entries.accept(SpectrumBlocks.DIKE_GATE_FOUNTAIN.get());
				entries.accept(SpectrumBlocks.DIKE_GATE.get());
				entries.accept(SpectrumBlocks.DREAM_GATE.get());
				entries.accept(SpectrumBlocks.PRESERVATION_CONTROLLER.get());
				
				entries.accept(SpectrumBlocks.BLACK_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.BLUE_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.BROWN_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.CYAN_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.GRAY_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.GREEN_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.LIGHT_BLUE_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.LIGHT_GRAY_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.LIME_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.MAGENTA_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.ORANGE_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.PINK_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.PURPLE_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.RED_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.WHITE_CHISELED_PRESERVATION_STONE.get());
				entries.accept(SpectrumBlocks.YELLOW_CHISELED_PRESERVATION_STONE.get());
				
				entries.accept(SpectrumBlocks.INVISIBLE_WALL.get());
				entries.accept(SpectrumBlocks.COURIER_STATUE.get());
				entries.accept(SpectrumBlocks.PRESERVATION_CHEST.get());
				
				entries.accept(SpectrumItems.DIVINATION_HEART);
				
				//entries.add(SpectrumItems.SPECTRAL_SHARD);
				//entries.add(SpectrumBlocks.SPECTRAL_SHARD_BLOCK);
				//entries.add(SpectrumBlocks.SPECTRAL_SHARD_STORAGE_BLOCK);
			}).build();
	
}
