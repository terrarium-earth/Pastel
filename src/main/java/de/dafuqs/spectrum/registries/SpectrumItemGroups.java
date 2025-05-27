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
				
				entries.accept(SpectrumItems.GUIDEBOOK.get());
				entries.accept(SpectrumItems.PAINTBRUSH.get());
				entries.accept(SpectrumItems.TUNING_STAMP.get());
				entries.accept(SpectrumItems.BOTTLE_OF_FADING.get());
				entries.accept(SpectrumItems.BOTTLE_OF_FAILING.get());
				entries.accept(SpectrumItems.BOTTLE_OF_RUIN.get());
				entries.accept(SpectrumItems.BOTTLE_OF_FORFEITURE.get());
				entries.accept(SpectrumItems.BOTTLE_OF_DECAY_AWAY.get());
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MULTITOOL.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.TENDER_PICKAXE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.LUCKY_PICKAXE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.RAZOR_FALCHION.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.OBLIVION_PICKAXE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.RESONANT_PICKAXE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.DRAGONRENDING_PICKAXE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.LAGOON_ROD.get()));
				entries.accept(SpectrumItems.MOLTEN_ROD.get());
				entries.accept(SpectrumItems.FETCHLING_HELMET.get());
				entries.accept(SpectrumItems.FEROCIOUS_CHESTPLATE.get());
				entries.accept(SpectrumItems.SYLPH_LEGGINGS.get());
				entries.accept(SpectrumItems.OREAD_BOOTS.get());
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_PICKAXE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_AXE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_SHOVEL.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_SWORD.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_HOE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_BOW.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_CROSSBOW.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_SHEARS.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_FISHING_ROD.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_HELMET.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_CHESTPLATE.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_LEGGINGS.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.BEDROCK_BOOTS.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_WORKSTAFF.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_ULTRA_GREATSWORD.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_CROSSBOW.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.MALACHITE_BIDENT.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.GLASS_CREST_WORKSTAFF.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.GLASS_CREST_ULTRA_GREATSWORD.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.FEROCIOUS_GLASS_CREST_BIDENT.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.FRACTAL_GLASS_CREST_BIDENT.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.GLASS_CREST_CROSSBOW.get()));
				entries.accept(SpectrumItems.MALACHITE_GLASS_ARROW.get());
				entries.accept(SpectrumItems.TOPAZ_GLASS_ARROW.get());
				entries.accept(SpectrumItems.AMETHYST_GLASS_ARROW.get());
				entries.accept(SpectrumItems.CITRINE_GLASS_ARROW.get());
				entries.accept(SpectrumItems.ONYX_GLASS_ARROW.get());
				entries.accept(SpectrumItems.MOONSTONE_GLASS_ARROW.get());
				entries.accept(SpectrumItems.AZURITE_GLASS_AMPOULE.get());
				entries.accept(SpectrumItems.MALACHITE_GLASS_AMPOULE.get());
				entries.accept(SpectrumItems.BLOODSTONE_GLASS_AMPOULE.get());
				entries.accept(SpectrumItems.DREAMFLAYER.get());
				entries.accept(SpectrumItems.NIGHTFALLS_BLADE.get());
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.DRACONIC_TWINSWORD.get()));
				entries.accept(Preenchanted.getDefaultEnchantedStack(lookup, SpectrumItems.DRAGON_TALON.get()));
				entries.accept(SpectrumItems.KNOTTED_SWORD.get());
				entries.accept(SpectrumItems.NECTAR_LANCE.get());
				entries.accept(SpectrumItems.OMNI_ACCELERATOR.get());
				entries.accept(SpectrumItems.FANCIFUL_TUFF_RING.get());
				entries.accept(SpectrumItems.FANCIFUL_BELT.get());
				entries.accept(SpectrumItems.FANCIFUL_PENDANT.get());
				entries.accept(SpectrumItems.FANCIFUL_CIRCLET.get());
				entries.accept(SpectrumItems.FANCIFUL_GLOVES.get());
				entries.accept(SpectrumItems.FANCIFUL_BISMUTH_RING.get());
				entries.accept(SpectrumItems.GLOW_VISION_GOGGLES.get());
				entries.accept(SpectrumItems.JEOPARDANT.get());
				entries.accept(SpectrumItems.SEVEN_LEAGUE_BOOTS.get());
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.SEVEN_LEAGUE_BOOTS.get(), Map.of(Enchantments.POWER, 5)));
				entries.accept(SpectrumItems.COTTON_CLOUD_BOOTS.get());
				entries.accept(SpectrumItems.RADIANCE_PIN.get());
				entries.accept(SpectrumItems.TOTEM_PENDANT.get());
				entries.accept(SpectrumItems.TAKE_OFF_BELT.get());
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.TAKE_OFF_BELT.get(), Map.of(Enchantments.POWER, 5, Enchantments.FEATHER_FALLING, 4)));
				entries.accept(SpectrumItems.AZURE_DIKE_BELT.get());
				entries.accept(SpectrumItems.AZURE_DIKE_RING.get());
				entries.accept(SpectrumItems.SHIELDGRASP_AMULET.get());
				entries.accept(SpectrumItems.SHIELDGRASP_AMULET.get().getFullStack());
				entries.accept(SpectrumItems.HEARTSINGERS_REWARD.get());
				entries.accept(SpectrumItems.HEARTSINGERS_REWARD.get().getFullStack());
				entries.accept(SpectrumItems.GLOVES_OF_DAWNS_GRASP.get());
				entries.accept(SpectrumItems.GLOVES_OF_DAWNS_GRASP.get().getFullStack());
				entries.accept(SpectrumItems.RING_OF_PURSUIT.get());
				entries.accept(SpectrumItems.RING_OF_PURSUIT.get().getFullStack());
				entries.accept(SpectrumItems.RING_OF_DENSER_STEPS.get());
				entries.accept(SpectrumItems.RING_OF_DENSER_STEPS.get().getFullStack());
				entries.accept(SpectrumItems.RING_OF_AERIAL_GRACE.get());
				entries.accept(SpectrumItems.RING_OF_AERIAL_GRACE.get().getFullStack());
				entries.accept(SpectrumItems.LAURELS_OF_SERENITY.get());
				entries.accept(SpectrumItems.LAURELS_OF_SERENITY.get().getFullStack());
				entries.accept(SpectrumItems.GLEAMING_PIN.get());
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.GLEAMING_PIN.get(), Map.of(SpectrumEnchantments.SNIPING, 2)));
				entries.accept(SpectrumItems.LESSER_POTION_PENDANT.get());
				entries.accept(SpectrumItems.GREATER_POTION_PENDANT.get());
				entries.accept(SpectrumItems.ASHEN_CIRCLET.get());
				entries.accept(SpectrumItems.WEEPING_CIRCLET.get());
				entries.accept(SpectrumItems.PUFF_CIRCLET.get());
				entries.accept(SpectrumItems.WHISPY_CIRCLET.get());
				entries.accept(SpectrumItems.AZURESQUE_DIKE_CORE.get());
				entries.accept(SpectrumItems.CIRCLET_OF_ARROGANCE.get());
				entries.accept(SpectrumItems.AETHER_GRACED_NECTAR_GLOVES.get());
				entries.accept(SpectrumItems.NEAT_RING.get());
				entries.accept(SpectrumItems.CRAFTING_TABLET.get());
				entries.accept(SpectrumBlocks.BOTTOMLESS_BUNDLE.get());
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumBlocks.BOTTOMLESS_BUNDLE.get().asItem(), Map.of(Enchantments.POWER, 5, SpectrumEnchantments.VOIDING, 1)));
				
				entries.accept(SpectrumItems.KNOWLEDGE_GEM.get());
				ItemStack enchantedKnowledgeGemStack = SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.KNOWLEDGE_GEM.get().asItem(), Map.of(Enchantments.EFFICIENCY, 5, Enchantments.QUICK_CHARGE, 3));
				entries.accept(enchantedKnowledgeGemStack.copy());
				
				ItemStack knowledgeGemStack = SpectrumItems.KNOWLEDGE_GEM.get().getDefaultInstance();
				ExperienceStorageItem.addStoredExperience(lookup, knowledgeGemStack, SpectrumItems.KNOWLEDGE_GEM.get().getMaxStoredExperience(lookup, knowledgeGemStack));
				entries.accept(knowledgeGemStack);
				
				ExperienceStorageItem.addStoredExperience(lookup, enchantedKnowledgeGemStack, SpectrumItems.KNOWLEDGE_GEM.get().getMaxStoredExperience(lookup, enchantedKnowledgeGemStack));
				entries.accept(enchantedKnowledgeGemStack);
				
				entries.accept(SpectrumItems.CELESTIAL_POCKETWATCH.get());
				entries.accept(SpectrumItems.GILDED_BOOK.get());
				entries.accept(SpectrumItems.ENCHANTMENT_CANVAS.get());
				entries.accept(SpectrumItems.NIGHT_SALTS.get());
				entries.accept(SpectrumItems.SOOTHING_BOUQUET.get());
				entries.accept(SpectrumItems.CONCEALING_OILS.get());
				entries.accept(SpectrumItems.BITTER_OILS.get());
				entries.accept(SpectrumItems.EVERPROMISE_RIBBON.get());
				entries.accept(SpectrumItems.BAG_OF_HOLDING.get());
				entries.accept(SpectrumItems.RADIANCE_STAFF.get());
				entries.accept(SpectrumItems.NATURES_STAFF.get());
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.NATURES_STAFF.get(), Map.of(Enchantments.EFFICIENCY, 5)));
				entries.accept(SpectrumItems.STAFF_OF_REMEMBRANCE.get());
				entries.accept(SpectrumItems.CONSTRUCTORS_STAFF.get());
				entries.accept(SpectrumItems.EXCHANGING_STAFF.get());
				SpectrumEnchantmentHelper.addOrUpgradeEnchantmentOpt(lookup, SpectrumItems.EXCHANGING_STAFF.get().getDefaultInstance(), Enchantments.FORTUNE, 3, false, false).ifPresent(entries::accept);
				SpectrumEnchantmentHelper.addOrUpgradeEnchantmentOpt(lookup, SpectrumItems.EXCHANGING_STAFF.get().getDefaultInstance(), Enchantments.SILK_TOUCH, 1, false, false).ifPresent(entries::accept);
				SpectrumEnchantmentHelper.addOrUpgradeEnchantmentOpt(lookup, SpectrumItems.EXCHANGING_STAFF.get().getDefaultInstance(), SpectrumEnchantments.RESONANCE, 1, false, false).ifPresent(entries::accept);
				entries.accept(SpectrumItems.BLOCK_FLOODER.get());
				entries.accept(SpectrumItems.ENDER_SPLICE.get());
				entries.accept(SpectrumEnchantmentHelper.getEnchantedStack(lookup, SpectrumItems.ENDER_SPLICE.get(), Map.of(SpectrumEnchantments.RESONANCE, 1, SpectrumEnchantments.INDESTRUCTIBLE, 1)));
				entries.accept(SpectrumItems.PERTURBED_EYE.get());
				entries.accept(SpectrumItems.PIPE_BOMB.get());
				entries.accept(SpectrumItems.CRESCENT_CLOCK.get());
				entries.accept(SpectrumItems.ARTISANS_ATLAS.get());
				entries.accept(SpectrumBlocks.PRIMORDIAL_TORCH.get());
				entries.accept(SpectrumItems.MYSTERIOUS_LOCKET.get());
				entries.accept(SpectrumItems.MYSTERIOUS_COMPASS.get());
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
				entries.accept(SpectrumItems.IMBRIFER_COOKBOOK.get());
				entries.accept(SpectrumItems.IMPERIAL_COOKBOOK.get());
				entries.accept(SpectrumItems.MELOCHITES_COOKBOOK_VOL_1.get());
				entries.accept(SpectrumItems.MELOCHITES_COOKBOOK_VOL_2.get());
				entries.accept(SpectrumItems.BREWERS_HANDBOOK.get());
				//entries.add(SpectrumItems.VARIA_COOKBOOK);
				entries.accept(SpectrumItems.POISONERS_HANDBOOK.get());
				
				entries.accept(SpectrumItems.TRIPLE_MEAT_POT_PIE.get());
				entries.accept(SpectrumItems.KIMCHI.get());
				entries.accept(SpectrumItems.CLOTTED_CREAM.get());
				entries.accept(SpectrumItems.FRESH_CHOCOLATE.get());
				entries.accept(SpectrumItems.BODACIOUS_BERRY_BAR.get());
				entries.accept(SpectrumItems.HOT_CHOCOLATE.get());
				entries.accept(SpectrumItems.KARAK_CHAI.get());
				entries.accept(SpectrumItems.RESTORATION_TEA.get());
				entries.accept(SpectrumItems.GLISTERING_JELLY_TEA.get());
				entries.accept(SpectrumItems.AZALEA_TEA.get());
				entries.accept(SpectrumItems.DEMON_TEA.get());
				entries.accept(SpectrumItems.ENCHANTED_GOLDEN_CARROT.get());
				entries.accept(SpectrumItems.JADE_JELLY.get());
				entries.accept(SpectrumItems.JARAMEL.get());
				entries.accept(SpectrumItems.MOONSTRUCK_NECTAR.get());
				entries.accept(SpectrumItems.GLASS_PEACH.get());
				entries.accept(SpectrumItems.FISSURE_PLUM.get());
				entries.accept(SpectrumItems.NIGHTDEW_SPROUT.get());
				entries.accept(SpectrumItems.NECTARDEW_BURGEON.get());
				entries.accept(SpectrumItems.BLOODBOIL_SYRUP.get());
				entries.accept(SpectrumItems.MILKY_RESIN.get());
				entries.accept(SpectrumItems.SCONE.get());
				entries.accept(SpectrumItems.STAR_CANDY.get());
				entries.accept(SpectrumItems.ENCHANTED_STAR_CANDY.get());
				entries.accept(SpectrumItems.CHEONG.get());
				entries.accept(SpectrumItems.MERMAIDS_JAM.get());
				entries.accept(SpectrumItems.MERMAIDS_POPCORN.get());
				entries.accept(SpectrumItems.LE_FISHE_AU_CHOCOLAT.get());
				//entries.add(SpectrumItems.STUFFED_PETALS);
				//entries.add(SpectrumItems.PASTICHE);
				//entries.add(SpectrumItems.VITTORIAS_ROAST);
				entries.accept(SpectrumItems.LUCKY_ROLL.get());
				entries.accept(SpectrumItems.HONEY_PASTRY.get());
				entries.accept(SpectrumItems.JARAMEL_TART.get());
				entries.accept(SpectrumItems.SALTED_JARAMEL_TART.get());
				entries.accept(SpectrumItems.ASHEN_TART.get());
				entries.accept(SpectrumItems.WEEPING_TART.get());
				entries.accept(SpectrumItems.WHISPY_TART.get());
				entries.accept(SpectrumItems.PUFF_TART.get());
				entries.accept(SpectrumItems.JARAMEL_TRIFLE.get());
				entries.accept(SpectrumItems.SALTED_JARAMEL_TRIFLE.get());
				entries.accept(SpectrumItems.MONSTER_TRIFLE.get());
				entries.accept(SpectrumItems.DEMON_TRIFLE.get());
				entries.accept(SpectrumItems.MYCEYLON.get());
				entries.accept(SpectrumItems.MYCEYLON_APPLE_PIE.get());
				entries.accept(SpectrumItems.MYCEYLON_PUMPKIN_PIE.get());
				entries.accept(SpectrumItems.MYCEYLON_COOKIE.get());
				entries.accept(SpectrumItems.ALOE_LEAF.get());
				entries.accept(SpectrumItems.SAWBLADE_HOLLY_BERRY.get());
				entries.accept(SpectrumItems.PRICKLY_BAYLEAF.get());
				entries.accept(SpectrumItems.TRIPLE_MEAT_POT_STEW.get());
				entries.accept(SpectrumItems.DRAGONBONE_BROTH.get());
				entries.accept(SpectrumItems.BAGNUN.get());
				entries.accept(SpectrumItems.BANYASH.get());
				entries.accept(SpectrumItems.BERLINER.get());
				entries.accept(SpectrumItems.BRISTLE_MEAD.get());
				entries.accept(SpectrumItems.CHAUVE_SOURIS_AU_VIN.get());
				entries.accept(SpectrumItems.CRAWFISH.get());
				entries.accept(SpectrumItems.CRAWFISH_COCKTAIL.get());
				entries.accept(SpectrumItems.CREAM_PASTRY.get());
				entries.accept(SpectrumItems.FADED_KOI.get());
				entries.accept(SpectrumItems.FISHCAKE.get());
				entries.accept(SpectrumItems.LIZARD_MEAT.get());
				entries.accept(SpectrumItems.COOKED_LIZARD_MEAT.get());
				entries.accept(SpectrumItems.GOLDEN_BRISTLE_TEA.get());
				entries.accept(SpectrumItems.HARE_ROAST.get());
				entries.accept(SpectrumItems.JUNKET.get());
				entries.accept(SpectrumItems.KOI.get());
				entries.accept(SpectrumItems.MEATLOAF.get());
				entries.accept(SpectrumItems.MEATLOAF_SANDWICH.get());
				entries.accept(SpectrumItems.MELLOW_SHALLOT_SOUP.get());
				entries.accept(SpectrumItems.PEACHES_FLAMBE.get());
				entries.accept(SpectrumItems.PEACH_CREAM.get());
				entries.accept(SpectrumItems.PEACH_JAM.get());
				entries.accept(SpectrumItems.RABBIT_CREAM_PIE.get());
				entries.accept(SpectrumItems.SEDATIVES.get());
				entries.accept(SpectrumItems.SLUSHSLIDE.get());
				entries.accept(SpectrumItems.SURSTROMMING.get());
				entries.accept(SpectrumItems.MORCHELLA.get());
				entries.accept(SpectrumItems.NECTERED_VIOGNIER.get());
				entries.accept(SpectrumItems.FREIGEIST.get());
				
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
				
				entries.accept(SpectrumItems.PURE_ALCOHOL.get());
				entries.accept(SpectrumItems.REPRISE.get());
				entries.accept(SpectrumItems.SUSPICIOUS_BREW.get());
				entries.accept(SpectrumItems.JADE_WINE.get());
				entries.accept(SpectrumItems.CHRYSOCOLLA.get());
				entries.accept(SpectrumItems.AQUA_REGIA.get());
				entries.accept(SpectrumItems.EVERNECTAR.get());
			}).build();
	
	public static final ItemSubGroup RESOURCES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_RESOURCES, Component.translatable("itemGroup.pastel.resources"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.TOPAZ_SHARD.get());
				entries.accept(Items.AMETHYST_SHARD);
				entries.accept(SpectrumItems.CITRINE_SHARD.get());
				entries.accept(SpectrumItems.ONYX_SHARD.get());
				entries.accept(SpectrumItems.MOONSTONE_SHARD.get());
				
				entries.accept(SpectrumBlocks.TOPAZ_BLOCK.get());
				entries.accept(Blocks.AMETHYST_BLOCK);
				entries.accept(SpectrumBlocks.CITRINE_BLOCK.get());
				entries.accept(SpectrumBlocks.ONYX_BLOCK.get());
				entries.accept(SpectrumBlocks.MOONSTONE_BLOCK.get());
				
				entries.accept(SpectrumItems.TOPAZ_POWDER.get());
				entries.accept(SpectrumItems.AMETHYST_POWDER.get());
				entries.accept(SpectrumItems.CITRINE_POWDER.get());
				entries.accept(SpectrumItems.ONYX_POWDER.get());
				entries.accept(SpectrumItems.MOONSTONE_POWDER.get());
				
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
				
				entries.accept(SpectrumItems.BISMUTH_FLAKE.get());
				entries.accept(SpectrumBlocks.SMALL_BISMUTH_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_BISMUTH_BUD.get());
				entries.accept(SpectrumBlocks.BISMUTH_CLUSTER.get());
				entries.accept(SpectrumItems.BISMUTH_CRYSTAL.get());
				
				entries.accept(SpectrumBlocks.MALACHITE_ORE.get());
				entries.accept(SpectrumBlocks.DEEPSLATE_MALACHITE_ORE.get());
				entries.accept(SpectrumBlocks.BLACKSLAG_MALACHITE_ORE.get());
				entries.accept(SpectrumItems.RAW_MALACHITE.get());
				entries.accept(SpectrumBlocks.SMALL_MALACHITE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_MALACHITE_BUD.get());
				entries.accept(SpectrumBlocks.MALACHITE_CLUSTER.get());
				entries.accept(SpectrumItems.PURE_MALACHITE.get());
				
				entries.accept(SpectrumItems.RAW_AZURITE.get());
				entries.accept(SpectrumBlocks.SMALL_AZURITE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_AZURITE_BUD.get());
				entries.accept(SpectrumBlocks.AZURITE_CLUSTER.get());
				entries.accept(SpectrumItems.PURE_AZURITE.get());
				
				entries.accept(SpectrumItems.RAW_BLOODSTONE.get());
				entries.accept(SpectrumBlocks.SMALL_BLOODSTONE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_BLOODSTONE_BUD.get());
				entries.accept(SpectrumBlocks.BLOODSTONE_CLUSTER.get());
				entries.accept(SpectrumItems.PURE_BLOODSTONE.get());
				
				entries.accept(SpectrumItems.FROSTBITE_ESSENCE.get());
				entries.accept(SpectrumBlocks.FROSTBITE_CRYSTAL.get());
				entries.accept(SpectrumItems.INCANDESCENT_ESSENCE.get());
				entries.accept(SpectrumBlocks.BLAZING_CRYSTAL.get());
				
				entries.accept(SpectrumBlocks.CLOVER.get());
				entries.accept(SpectrumBlocks.FOUR_LEAF_CLOVER.get());
				entries.accept(SpectrumItems.BLOOD_ORCHID_PETAL.get());
				entries.accept(SpectrumBlocks.BLOOD_ORCHID.get());
				entries.accept(SpectrumBlocks.QUITOXIC_REEDS.get());
				entries.accept(SpectrumItems.QUITOXIC_POWDER.get());
				
				entries.accept(SpectrumItems.AMARANTH_GRAINS.get());
				entries.accept(SpectrumBlocks.AMARANTH_BUSHEL.get());
				entries.accept(BuiltInRegistries.ITEM.get(SpectrumItems.GLISTERING_MELON_SEEDS));
				
				entries.accept(SpectrumBlocks.GLISTERING_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.FIERY_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.COLORFUL_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.PRISTINE_SHOOTING_STAR.get());
				entries.accept(SpectrumBlocks.GEMSTONE_SHOOTING_STAR.get());
				entries.accept(SpectrumItems.STARDUST.get());
				entries.accept(SpectrumBlocks.STARDUST_BLOCK.get());
				entries.accept(SpectrumItems.STAR_FRAGMENT.get());
				entries.accept(SpectrumBlocks.RADIATING_ENDER.get());
				
				entries.accept(SpectrumItems.WHITE_PIGMENT.get());
				entries.accept(SpectrumItems.ORANGE_PIGMENT.get());
				entries.accept(SpectrumItems.MAGENTA_PIGMENT.get());
				entries.accept(SpectrumItems.LIGHT_BLUE_PIGMENT.get());
				entries.accept(SpectrumItems.YELLOW_PIGMENT.get());
				entries.accept(SpectrumItems.LIME_PIGMENT.get());
				entries.accept(SpectrumItems.PINK_PIGMENT.get());
				entries.accept(SpectrumItems.GRAY_PIGMENT.get());
				entries.accept(SpectrumItems.LIGHT_GRAY_PIGMENT.get());
				entries.accept(SpectrumItems.CYAN_PIGMENT.get());
				entries.accept(SpectrumItems.PURPLE_PIGMENT.get());
				entries.accept(SpectrumItems.BLUE_PIGMENT.get());
				entries.accept(SpectrumItems.BROWN_PIGMENT.get());
				entries.accept(SpectrumItems.GREEN_PIGMENT.get());
				entries.accept(SpectrumItems.RED_PIGMENT.get());
				entries.accept(SpectrumItems.BLACK_PIGMENT.get());
				
				entries.accept(SpectrumItems.VEGETAL.get());
				entries.accept(SpectrumItems.NEOLITH.get());
				entries.accept(SpectrumItems.BEDROCK_DUST.get());
				entries.accept(SpectrumItems.MIDNIGHT_ABERRATION.get());
				entries.accept(SpectrumItems.MIDNIGHT_ABERRATION.get().getStableStack());
				entries.accept(SpectrumItems.MIDNIGHT_CHIP.get());
				
				entries.accept(SpectrumItems.SHIMMERSTONE_GEM.get());
				entries.accept(SpectrumItems.PALTAERIA_FRAGMENTS.get());
				entries.accept(SpectrumItems.PALTAERIA_GEM.get());
				entries.accept(SpectrumItems.STRATINE_FRAGMENTS.get());
				entries.accept(SpectrumItems.STRATINE_GEM.get());
				
				entries.accept(SpectrumItems.HIBERNATING_JADE_VINE_BULB.get());
				entries.accept(SpectrumItems.GERMINATED_JADE_VINE_BULB.get());
				entries.accept(SpectrumItems.JADE_VINE_PETALS.get());
				entries.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_BULB.get());
				entries.accept(SpectrumBlocks.JADEITE_LOTUS_BULB.get());
				entries.accept(SpectrumItems.JADEITE_PETALS.get());
				
				entries.accept(SpectrumItems.MERMAIDS_GEM.get());
				entries.accept(SpectrumItems.STORM_STONE.get());
				entries.accept(SpectrumItems.DOOMBLOOM_SEED.get());
				entries.accept(SpectrumItems.RESPLENDENT_FEATHER.get());
				entries.accept(SpectrumItems.DRAGONBONE_CHUNK.get());
				entries.accept(SpectrumItems.BONE_ASH.get());
				entries.accept(SpectrumItems.DOWNSTONE_FRAGMENTS.get());
				entries.accept(SpectrumItems.RESONANCE_SHARD.get());
				entries.accept(SpectrumItems.AETHER_VESTIGES.get());
				entries.accept(SpectrumItems.MOONSTONE_CORE.get());
				
				entries.accept(SpectrumItems.LIQUID_CRYSTAL_BUCKET.get());
				entries.accept(SpectrumItems.GOO_BUCKET.get());
				entries.accept(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET.get());
				entries.accept(SpectrumItems.DRAGONROT_BUCKET.get());
			}).build();
	
	public static final ItemSubGroup PURE_RESOURCES = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_PURE_RESOURCES, Component.translatable("itemGroup.pastel.pure_resources"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.PURE_COAL.get());
				entries.accept(SpectrumBlocks.SMALL_COAL_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_COAL_BUD.get());
				entries.accept(SpectrumBlocks.COAL_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_COAL_BLOCK.get());
				entries.accept(SpectrumItems.PURE_COPPER.get());
				entries.accept(SpectrumBlocks.SMALL_COPPER_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_COPPER_BUD.get());
				entries.accept(SpectrumBlocks.COPPER_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_COPPER_BLOCK.get());
				entries.accept(SpectrumItems.PURE_IRON.get());
				entries.accept(SpectrumBlocks.SMALL_IRON_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_IRON_BUD.get());
				entries.accept(SpectrumBlocks.IRON_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_IRON_BLOCK.get());
				entries.accept(SpectrumItems.PURE_GOLD.get());
				entries.accept(SpectrumBlocks.SMALL_GOLD_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_GOLD_BUD.get());
				entries.accept(SpectrumBlocks.GOLD_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_GOLD_BLOCK.get());
				entries.accept(SpectrumItems.PURE_LAPIS.get());
				entries.accept(SpectrumBlocks.SMALL_LAPIS_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_LAPIS_BUD.get());
				entries.accept(SpectrumBlocks.LAPIS_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_LAPIS_BLOCK.get());
				entries.accept(SpectrumItems.PURE_REDSTONE.get());
				entries.accept(SpectrumBlocks.SMALL_REDSTONE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_REDSTONE_BUD.get());
				entries.accept(SpectrumBlocks.REDSTONE_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_REDSTONE_BLOCK.get());
				entries.accept(SpectrumItems.PURE_DIAMOND.get());
				entries.accept(SpectrumBlocks.SMALL_DIAMOND_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_DIAMOND_BUD.get());
				entries.accept(SpectrumBlocks.DIAMOND_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_DIAMOND_BLOCK.get());
				entries.accept(SpectrumItems.PURE_EMERALD.get());
				entries.accept(SpectrumBlocks.SMALL_EMERALD_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_EMERALD_BUD.get());
				entries.accept(SpectrumBlocks.EMERALD_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_EMERALD_BLOCK.get());
				
				entries.accept(SpectrumItems.PURE_PRISMARINE.get());
				entries.accept(SpectrumBlocks.SMALL_PRISMARINE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_PRISMARINE_BUD.get());
				entries.accept(SpectrumBlocks.PRISMARINE_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_PRISMARINE_BLOCK.get());
				
				entries.accept(SpectrumItems.PURE_QUARTZ.get());
				entries.accept(SpectrumBlocks.SMALL_QUARTZ_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_QUARTZ_BUD.get());
				entries.accept(SpectrumBlocks.QUARTZ_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_QUARTZ_BLOCK.get());
				entries.accept(SpectrumItems.PURE_GLOWSTONE.get());
				entries.accept(SpectrumBlocks.SMALL_GLOWSTONE_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_GLOWSTONE_BUD.get());
				entries.accept(SpectrumBlocks.GLOWSTONE_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_GLOWSTONE_BLOCK.get());
				entries.accept(SpectrumItems.PURE_NETHERITE_SCRAP.get());
				entries.accept(SpectrumBlocks.SMALL_NETHERITE_SCRAP_BUD.get());
				entries.accept(SpectrumBlocks.LARGE_NETHERITE_SCRAP_BUD.get());
				entries.accept(SpectrumBlocks.NETHERITE_SCRAP_CLUSTER.get());
				entries.accept(SpectrumBlocks.PURE_NETHERITE_SCRAP_BLOCK.get());
				
				entries.accept(SpectrumItems.PURE_ECHO.get());
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
				
				entries.accept(SpectrumItems.ASH_FLAKES.get());
				entries.accept(SpectrumBlocks.ASH.get());
				entries.accept(SpectrumBlocks.ASH_PILE.get());
				
				entries.accept(SpectrumBlocks.ROCK_CRYSTAL.get());
				
				entries.accept(SpectrumItems.PYRITE_CHUNK.get());
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
				entries.accept(SpectrumItems.PHANTOM_FRAME.get());
				entries.accept(SpectrumItems.GLOW_PHANTOM_FRAME.get());
				entries.accept(SpectrumItems.LOGO_BANNER_PATTERN.get());
				entries.accept(SpectrumItems.AMETHYST_SHARD_BANNER_PATTERN.get());
				entries.accept(SpectrumItems.AMETHYST_CLUSTER_BANNER_PATTERN.get());
				entries.accept(SpectrumItems.ASTROLOGER_BANNER_PATTERN.get());
				entries.accept(SpectrumItems.VELVET_ASTROLOGER_BANNER_PATTERN.get());
				entries.accept(SpectrumItems.POISONBLOOM_BANNER_PATTERN.get());
				entries.accept(SpectrumItems.DEEP_LIGHT_BANNER_PATTERN.get());
				entries.accept(SpectrumItems.MUSIC_DISC_DISCOVERY.get());
				entries.accept(SpectrumItems.MUSIC_DISC_CREDITS.get());
				entries.accept(SpectrumItems.MUSIC_DISC_DIVINITY.get());
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
				entries.accept(SpectrumItems.EGG_LAYING_WOOLY_PIG_SPAWN_EGG.get());
				entries.accept(SpectrumItems.PRESERVATION_TURRET_SPAWN_EGG.get());
				entries.accept(SpectrumItems.KINDLING_SPAWN_EGG.get());
				entries.accept(SpectrumItems.LIZARD_SPAWN_EGG.get());
				entries.accept(SpectrumItems.ERASER_SPAWN_EGG.get());
				entries.accept(SpectrumItems.BUCKET_OF_ERASER.get());
				MemoryItem.appendEntries(displayContext.holders(), entries);
			}).build();
	
	public static final ItemSubGroup ENERGY = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_ENERGY, Component.translatable("itemGroup.pastel.energy"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.INK_FLASK.get());
				for (InkColor color : InkColors.all()) {
					entries.accept(SpectrumItems.INK_FLASK.get().getFullStack(color));
				}
				entries.accept(SpectrumItems.INK_ASSORTMENT.get());
				entries.accept(SpectrumItems.INK_ASSORTMENT.get().getFullStack());
				entries.accept(SpectrumItems.PIGMENT_PALETTE.get());
				entries.accept(SpectrumItems.PIGMENT_PALETTE.get().getFullStack());
				entries.accept(SpectrumItems.ARTISTS_PALETTE.get());
				entries.accept(SpectrumItems.ARTISTS_PALETTE.get().getFullStack());
			}).build();
	
	public static final ItemSubGroup CREATIVE = new ItemSubGroup.Builder(MAIN, ItemGroupIDs.SUBTAB_CREATIVE, Component.translatable("itemGroup.pastel.creative"))
			.styled(ItemGroupIDs.STYLE)
			.entries((displayContext, entries) -> {
				entries.accept(SpectrumItems.PEDESTAL_TIER_1_STRUCTURE_PLACER.get());
				entries.accept(SpectrumItems.PEDESTAL_TIER_2_STRUCTURE_PLACER.get());
				entries.accept(SpectrumItems.PEDESTAL_TIER_3_STRUCTURE_PLACER.get());
				entries.accept(SpectrumItems.FUSION_SHRINE_STRUCTURE_PLACER.get());
				entries.accept(SpectrumItems.ENCHANTER_STRUCTURE_PLACER.get());
				entries.accept(SpectrumItems.SPIRIT_INSTILLER_STRUCTURE_PLACER.get());
				entries.accept(SpectrumItems.CINDERHEARTH_STRUCTURE_PLACER.get());
				
				entries.accept(SpectrumBlocks.CREATIVE_PARTICLE_SPAWNER.get());
				entries.accept(SpectrumItems.CREATIVE_INK_ASSORTMENT.get());
				entries.accept(SpectrumItems.PRIMORDIAL_LIGHTER.get());
				
				entries.accept(SpectrumItems.CONNECTION_NODE_CRYSTAL.get());
				entries.accept(SpectrumItems.STORAGE_NODE_CRYSTAL.get());
				entries.accept(SpectrumItems.PROVIDER_NODE_CRYSTAL.get());
				entries.accept(SpectrumItems.SENDER_NODE_CRYSTAL.get());
				entries.accept(SpectrumItems.GATHER_NODE_CRYSTAL.get());
				entries.accept(SpectrumItems.BUFFER_NODE_CRYSTAL.get());
				
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
				
				entries.accept(SpectrumItems.DIVINATION_HEART.get());
				
				//entries.add(SpectrumItems.SPECTRAL_SHARD);
				//entries.add(SpectrumBlocks.SPECTRAL_SHARD_BLOCK);
				//entries.add(SpectrumBlocks.SPECTRAL_SHARD_STORAGE_BLOCK);
			}).build();
	
}
