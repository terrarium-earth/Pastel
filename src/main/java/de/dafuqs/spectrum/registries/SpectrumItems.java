package de.dafuqs.spectrum.registries;

import de.dafuqs.revelationary.api.revelations.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.color.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.blocks.conditional.*;
import de.dafuqs.spectrum.blocks.fluid.*;
import de.dafuqs.spectrum.blocks.gravity.*;
import de.dafuqs.spectrum.blocks.jade_vines.*;
import de.dafuqs.spectrum.blocks.rock_candy.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.items.armor.*;
import de.dafuqs.spectrum.items.conditional.CloakedItem;
import de.dafuqs.spectrum.items.conditional.*;
import de.dafuqs.spectrum.items.energy.*;
import de.dafuqs.spectrum.items.food.*;
import de.dafuqs.spectrum.items.food.beverages.*;
import de.dafuqs.spectrum.items.item_frame.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.items.magic_items.ampoules.*;
import de.dafuqs.spectrum.items.map.*;
import de.dafuqs.spectrum.items.misc.*;
import de.dafuqs.spectrum.items.tools.*;
import de.dafuqs.spectrum.items.trinkets.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import net.fabricmc.fabric.api.registry.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.data.client.*;
import net.minecraft.enchantment.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;
import java.util.function.*;

import static de.dafuqs.spectrum.data.SpectrumDataGenerator.*;
import static de.dafuqs.spectrum.registries.SpectrumFluids.*;

public class SpectrumItems {
	
	public static final DeferredRegistrar REGISTRAR = new DeferredRegistrar();
	public static final DeferredRegistrar.Contextual<ItemModelGenerator> ITEM_MODEL_REGISTRAR = new DeferredRegistrar.Contextual<>(IS_DATAGEN);
	
	// Main items
	public static final Item GUIDEBOOK = registerDeferred("guidebook", new GuidebookItem(IS.of(1)), DyeColor.WHITE);
	public static final Item PAINTBRUSH = registerDeferredWithUniqueModel("paintbrush", new PaintbrushItem(IS.of(1)), DyeColor.WHITE);
	public static final Item CRAFTING_TABLET = registerDeferred("crafting_tablet", new CraftingTabletItem(IS.of(1)), DyeColor.LIGHT_GRAY);
	
	// Structure placers
	public static final Item PEDESTAL_TIER_1_STRUCTURE_PLACER = registerDeferred("pedestal_tier_1_structure_placer", new StructurePlacerItem(IS.of(1), SpectrumMultiblocks.PEDESTAL_SIMPLE), DyeColor.WHITE);
	public static final Item PEDESTAL_TIER_2_STRUCTURE_PLACER = registerDeferred("pedestal_tier_2_structure_placer", new StructurePlacerItem(IS.of(1), SpectrumMultiblocks.PEDESTAL_ADVANCED), DyeColor.WHITE);
	public static final Item PEDESTAL_TIER_3_STRUCTURE_PLACER = registerDeferred("pedestal_tier_3_structure_placer", new StructurePlacerItem(IS.of(1), SpectrumMultiblocks.PEDESTAL_COMPLEX), DyeColor.WHITE);
	public static final Item FUSION_SHRINE_STRUCTURE_PLACER = registerDeferred("fusion_shrine_structure_placer", new StructurePlacerItem(IS.of(1), SpectrumMultiblocks.FUSION_SHRINE), DyeColor.WHITE);
	public static final Item ENCHANTER_STRUCTURE_PLACER = registerDeferred("enchanter_structure_placer", new StructurePlacerItem(IS.of(1), SpectrumMultiblocks.ENCHANTER), DyeColor.WHITE);
	public static final Item SPIRIT_INSTILLER_STRUCTURE_PLACER = registerDeferred("spirit_instiller_structure_placer", new StructurePlacerItem(IS.of(1), SpectrumMultiblocks.SPIRIT_INSTILLER), DyeColor.WHITE);
	public static final Item CINDERHEARTH_STRUCTURE_PLACER = registerDeferred("cinderhearth_structure_placer", new StructurePlacerItem(IS.of(1), SpectrumMultiblocks.CINDERHEARTH), DyeColor.WHITE);
	
	// Gem shards and powders
	public static final Item TOPAZ_SHARD = registerDeferred("topaz_shard", new Item(IS.of()), DyeColor.CYAN);
	public static final Item CITRINE_SHARD = registerDeferred("citrine_shard", new Item(IS.of()), DyeColor.YELLOW);
	public static final Item ONYX_SHARD = registerDeferred("onyx_shard", new CloakedItem(IS.of(), SpectrumAdvancements.COLLECT_ALL_BASIC_PIGMENTS_BESIDES_BROWN, Items.BLACK_DYE), DyeColor.BLACK);
	public static final Item MOONSTONE_SHARD = registerDeferred("moonstone_shard", new CloakedItem(IS.of(), SpectrumAdvancements.BREAK_DECAYED_BEDROCK, Items.WHITE_DYE), DyeColor.WHITE);
	public static final Item SPECTRAL_SHARD = registerDeferred("spectral_shard", new Item(IS.of(Rarity.RARE)), DyeColor.WHITE);
	
	public static final Item TOPAZ_POWDER = registerDeferred("topaz_powder", new GemstonePowderItem(IS.of(), SpectrumAdvancements.COLLECT_TOPAZ, BuiltinGemstoneColor.CYAN), DyeColor.CYAN);
	public static final Item AMETHYST_POWDER = registerDeferred("amethyst_powder", new GemstonePowderItem(IS.of(), SpectrumAdvancements.COLLECT_AMETHYST, BuiltinGemstoneColor.MAGENTA), DyeColor.MAGENTA);
	public static final Item CITRINE_POWDER = registerDeferred("citrine_powder", new GemstonePowderItem(IS.of(), SpectrumAdvancements.COLLECT_CITRINE, BuiltinGemstoneColor.YELLOW), DyeColor.YELLOW);
	public static final Item ONYX_POWDER = registerDeferred("onyx_powder", new GemstonePowderItem(IS.of(), SpectrumAdvancements.CREATE_ONYX, BuiltinGemstoneColor.BLACK), DyeColor.BLACK);
	public static final Item MOONSTONE_POWDER = registerDeferred("moonstone_powder", new GemstonePowderItem(IS.of(), SpectrumAdvancements.COLLECT_MOONSTONE, BuiltinGemstoneColor.WHITE), DyeColor.WHITE);
	
	// Pigment
	public static final Item WHITE_PIGMENT = registerDeferred("white_pigment", new PigmentItem(IS.of(), DyeColor.WHITE), DyeColor.WHITE);
	public static final Item ORANGE_PIGMENT = registerDeferred("orange_pigment", new PigmentItem(IS.of(), DyeColor.ORANGE), DyeColor.ORANGE);
	public static final Item MAGENTA_PIGMENT = registerDeferred("magenta_pigment", new PigmentItem(IS.of(), DyeColor.MAGENTA), DyeColor.MAGENTA);
	public static final Item LIGHT_BLUE_PIGMENT = registerDeferred("light_blue_pigment", new PigmentItem(IS.of(), DyeColor.LIGHT_BLUE), DyeColor.LIGHT_BLUE);
	public static final Item YELLOW_PIGMENT = registerDeferred("yellow_pigment", new PigmentItem(IS.of(), DyeColor.YELLOW), DyeColor.YELLOW);
	public static final Item LIME_PIGMENT = registerDeferred("lime_pigment", new PigmentItem(IS.of(), DyeColor.LIME), DyeColor.LIME);
	public static final Item PINK_PIGMENT = registerDeferred("pink_pigment", new PigmentItem(IS.of(), DyeColor.PINK), DyeColor.PINK);
	public static final Item GRAY_PIGMENT = registerDeferred("gray_pigment", new PigmentItem(IS.of(), DyeColor.GRAY), DyeColor.GRAY);
	public static final Item LIGHT_GRAY_PIGMENT = registerDeferred("light_gray_pigment", new PigmentItem(IS.of(), DyeColor.LIGHT_GRAY), DyeColor.LIGHT_GRAY);
	public static final Item CYAN_PIGMENT = registerDeferred("cyan_pigment", new PigmentItem(IS.of(), DyeColor.CYAN), DyeColor.CYAN);
	public static final Item PURPLE_PIGMENT = registerDeferred("purple_pigment", new PigmentItem(IS.of(), DyeColor.PURPLE), DyeColor.PURPLE);
	public static final Item BLUE_PIGMENT = registerDeferred("blue_pigment", new PigmentItem(IS.of(), DyeColor.BLUE), DyeColor.BLUE);
	public static final Item BROWN_PIGMENT = registerDeferred("brown_pigment", new PigmentItem(IS.of(), DyeColor.BROWN), DyeColor.BROWN);
	public static final Item GREEN_PIGMENT = registerDeferred("green_pigment", new PigmentItem(IS.of(), DyeColor.GREEN), DyeColor.GREEN);
	public static final Item RED_PIGMENT = registerDeferred("red_pigment", new PigmentItem(IS.of(), DyeColor.RED), DyeColor.RED);
	public static final Item BLACK_PIGMENT = registerDeferred("black_pigment", new PigmentItem(IS.of(), DyeColor.BLACK), DyeColor.BLACK);
	
	// Preenchanted tools
	public static final Item MULTITOOL = registerDeferredWithUniqueModel("multitool", new PreenchantedMultiToolItem(ToolMaterials.IRON, 2, -2.4F, IS.of(Rarity.UNCOMMON).maxDamage(ToolMaterials.IRON.getDurability())), DyeColor.BROWN);
	public static final Item TENDER_PICKAXE = registerDeferredWithUniqueModel("tender_pickaxe", new GlintlessPickaxe(SpectrumToolMaterial.LOW_HEALTH, IS.of(Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.LOW_HEALTH.getDurability()).attributeModifiers(PickaxeItem.createAttributeModifiers(SpectrumToolMaterial.LOW_HEALTH, 1, -2.8F))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(Enchantments.SILK_TOUCH, 1);
		}
	}, DyeColor.BLUE);
	public static final Item LUCKY_PICKAXE = registerDeferredWithUniqueModel("lucky_pickaxe", new GlintlessPickaxe(SpectrumToolMaterial.LOW_HEALTH, IS.of(Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.LOW_HEALTH.getDurability()).attributeModifiers(PickaxeItem.createAttributeModifiers(SpectrumToolMaterial.LOW_HEALTH, 1, -2.8F))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(Enchantments.FORTUNE, 3);
		}
	}, DyeColor.LIGHT_BLUE);
	public static final Item RAZOR_FALCHION = registerDeferredWithUniqueModel("razor_falchion", new RazorFalchionItem(SpectrumToolMaterial.LOW_HEALTH, IS.of(Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.LOW_HEALTH.getDurability()).attributeModifiers(SwordItem.createAttributeModifiers(SpectrumToolMaterial.LOW_HEALTH, 4, -2.2F))), DyeColor.RED);
	public static final Item OBLIVION_PICKAXE = registerDeferredWithUniqueModel("oblivion_pickaxe", new OblivionPickaxeItem(SpectrumToolMaterial.VOIDING, IS.of(Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.VOIDING.getDurability()).attributeModifiers(PickaxeItem.createAttributeModifiers(SpectrumToolMaterial.VOIDING, 1, -2.8F))), DyeColor.GRAY);
	public static final Item RESONANT_PICKAXE = registerDeferredWithUniqueModel("resonant_pickaxe", new GlintlessPickaxe(SpectrumToolMaterial.LOW_HEALTH_MINING_LEVEL_4, IS.of(Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.LOW_HEALTH.getDurability()).attributeModifiers(PickaxeItem.createAttributeModifiers(SpectrumToolMaterial.LOW_HEALTH_MINING_LEVEL_4, 1, -2.8F))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(SpectrumEnchantments.CLOAKED_RESONANCE, 1);
		}
	}, DyeColor.WHITE);
	public static final Item DRAGONRENDING_PICKAXE = registerDeferredWithUniqueModel("dragonrending_pickaxe", new GlintlessPickaxe(SpectrumToolMaterial.DRACONIC, IS.of(Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.DRACONIC.getDurability()).attributeModifiers(PickaxeItem.createAttributeModifiers(SpectrumToolMaterial.DRACONIC, 1, -2.8F))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(SpectrumEnchantments.CLOAKED_RAZING, 3);
		}
	}, DyeColor.WHITE);
	public static final SpectrumFishingRodItem LAGOON_ROD = registerDeferredWithUniqueModel("lagoon_rod", new LagoonRodItem(IS.of().maxDamage(256)), DyeColor.LIGHT_BLUE);
	public static final SpectrumFishingRodItem MOLTEN_ROD = registerDeferredWithUniqueModel("molten_rod", new MoltenRodItem(IS.of().maxDamage(256)), DyeColor.ORANGE);
	
	// Bedrock Tools
	public static final Item BEDROCK_PICKAXE = registerDeferredWithUniqueModel("bedrock_pickaxe", new SpectrumPickaxeItem(SpectrumToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON).attributeModifiers(PickaxeItem.createAttributeModifiers(SpectrumToolMaterial.BEDROCK, 1, -2.8F)).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(Enchantments.SILK_TOUCH, 1);
		}
	}, DyeColor.BLACK);
	public static final Item BEDROCK_AXE = registerDeferredWithUniqueModel("bedrock_axe", new BedrockAxeItem(SpectrumToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON).attributeModifiers(AxeItem.createAttributeModifiers(SpectrumToolMaterial.BEDROCK, 5, -3.0F)).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	public static final Item BEDROCK_SHOVEL = registerDeferredWithUniqueModel("bedrock_shovel", new BedrockShovelItem(SpectrumToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON).attributeModifiers(ShovelItem.createAttributeModifiers(SpectrumToolMaterial.BEDROCK, 1, -3.0F)).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	public static final Item BEDROCK_SWORD = registerDeferredWithUniqueModel("bedrock_sword", new BedrockSwordItem(SpectrumToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON).attributeModifiers(SwordItem.createAttributeModifiers(SpectrumToolMaterial.BEDROCK, 4, -2.4F)).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	public static final Item BEDROCK_HOE = registerDeferredWithUniqueModel("bedrock_hoe", new BedrockHoeItem(SpectrumToolMaterial.BEDROCK, IS.of(Rarity.UNCOMMON).attributeModifiers(HoeItem.createAttributeModifiers(SpectrumToolMaterial.BEDROCK, 2, -0.0F)).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	public static final Item BEDROCK_BOW = registerDeferredWithUniqueModel("bedrock_bow", new BedrockBowItem(IS.of(Rarity.UNCOMMON).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	public static final Item BEDROCK_CROSSBOW = registerDeferredWithUniqueModel("bedrock_crossbow", new BedrockCrossbowItem(IS.of(Rarity.UNCOMMON).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	public static final Item BEDROCK_SHEARS = registerDeferred("bedrock_shears", new BedrockShearsItem(IS.of(Rarity.UNCOMMON).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	public static final Item BEDROCK_FISHING_ROD = registerDeferredWithUniqueModel("bedrock_fishing_rod", new BedrockFishingRodItem(IS.of(Rarity.UNCOMMON).fireproof().maxDamage(SpectrumToolMaterial.BEDROCK.getDurability()).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))), DyeColor.BLACK);
	
	public static final Item MALACHITE_WORKSTAFF = registerDeferredWithUniqueModel("malachite_workstaff", new WorkstaffItem(SpectrumToolMaterial.MALACHITE, 1, -3.2F, IS.of(1, Rarity.UNCOMMON)), DyeColor.GREEN);
	public static final Item MALACHITE_ULTRA_GREATSWORD = registerDeferredWithUniqueModel("malachite_ultra_greatsword", new GreatswordItem(SpectrumToolMaterial.MALACHITE, 7, -2.8F, 1.0F, IS.of(1, Rarity.UNCOMMON)), DyeColor.GREEN);
	public static final Item MALACHITE_CROSSBOW = registerDeferredWithUniqueModel("malachite_crossbow", new MalachiteCrossbowItem(IS.of(1, Rarity.UNCOMMON).fireproof().maxDamage(SpectrumToolMaterial.MALACHITE.getDurability())), DyeColor.GREEN);
	public static final Item MALACHITE_BIDENT = registerDeferredWithUniqueModel("malachite_bident", new MalachiteBidentItem(IS.of(1, Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.MALACHITE.getDurability()), -2.4, 9, 0.25F, 0F), DyeColor.GREEN);
	
	// variants by socketing a moonstone core
	public static final Item GLASS_CREST_WORKSTAFF = registerDeferredWithUniqueModel("glass_crest_workstaff", new GlassCrestWorkstaffItem(SpectrumToolMaterial.GLASS_CREST, 1, -2.8F, IS.of(1, Rarity.UNCOMMON)), DyeColor.WHITE);
	public static final Item GLASS_CREST_ULTRA_GREATSWORD = registerDeferredWithUniqueModel("glass_crest_ultra_greatsword", new GlassCrestGreatswordItem(SpectrumToolMaterial.GLASS_CREST, 5, -2.8F, 1.0F, IS.of(1, Rarity.UNCOMMON)), DyeColor.WHITE);
	public static final Item GLASS_CREST_CROSSBOW = registerDeferredWithUniqueModel("glass_crest_crossbow", new GlassCrestCrossbowItem(IS.of(1, Rarity.UNCOMMON).fireproof().maxDamage(SpectrumToolMaterial.GLASS_CREST.getDurability())), DyeColor.WHITE);
	public static final Item FEROCIOUS_GLASS_CREST_BIDENT = registerDeferredWithUniqueModel("ferocious_glass_crest_bident", new FerociousBidentItem(IS.of(1, Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.GLASS_CREST.getDurability()), -2.2, 13, 0.33F, 0.33F), DyeColor.WHITE);
	public static final Item FRACTAL_GLASS_CREST_BIDENT = registerDeferredWithUniqueModel("fractal_glass_crest_bident", new FractalBidentItem(IS.of(1, Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.GLASS_CREST.getDurability()), -2.4, 6.5, 0.25F, 0.25F), DyeColor.WHITE);
	
	public static final Item MALACHITE_GLASS_ARROW = registerDeferred("malachite_glass_arrow", new GlassArrowItem(IS.of(Rarity.UNCOMMON), GlassArrowVariant.MALACHITE, SpectrumParticleTypes.LIME_CRAFTING), DyeColor.GREEN);
	public static final Item TOPAZ_GLASS_ARROW = registerDeferred("topaz_glass_arrow", new GlassArrowItem(IS.of(Rarity.UNCOMMON), GlassArrowVariant.TOPAZ, SpectrumParticleTypes.CYAN_CRAFTING), DyeColor.CYAN);
	public static final Item AMETHYST_GLASS_ARROW = registerDeferred("amethyst_glass_arrow", new GlassArrowItem(IS.of(Rarity.UNCOMMON), GlassArrowVariant.AMETHYST, SpectrumParticleTypes.MAGENTA_CRAFTING), DyeColor.MAGENTA);
	public static final Item CITRINE_GLASS_ARROW = registerDeferred("citrine_glass_arrow", new GlassArrowItem(IS.of(Rarity.UNCOMMON), GlassArrowVariant.CITRINE, SpectrumParticleTypes.YELLOW_CRAFTING), DyeColor.YELLOW);
	public static final Item ONYX_GLASS_ARROW = registerDeferred("onyx_glass_arrow", new GlassArrowItem(IS.of(Rarity.UNCOMMON), GlassArrowVariant.ONYX, SpectrumParticleTypes.BLACK_CRAFTING), DyeColor.BLACK);
	public static final Item MOONSTONE_GLASS_ARROW = registerDeferred("moonstone_glass_arrow", new GlassArrowItem(IS.of(Rarity.UNCOMMON), GlassArrowVariant.MOONSTONE, SpectrumParticleTypes.WHITE_CRAFTING), DyeColor.WHITE);
	
	public static final Item OMNI_ACCELERATOR = registerDeferredWithUniqueModel("omni_accelerator", new OmniAcceleratorItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.YELLOW);
	
	public static final Item AZURITE_GLASS_AMPOULE = registerDeferred("azurite_glass_ampoule", new GlassAmpouleItem(IS.of(Rarity.UNCOMMON)), DyeColor.BLUE);
	public static final Item BLOODSTONE_GLASS_AMPOULE = registerDeferred("bloodstone_glass_ampoule", new BloodstoneGlassAmpouleItem(IS.of(Rarity.UNCOMMON).attributeModifiers(BloodstoneGlassAmpouleItem.createAttributeModifiers())), DyeColor.RED);
	public static final Item MALACHITE_GLASS_AMPOULE = registerDeferredWithUniqueModel("malachite_glass_ampoule", new MalachiteGlassAmpouleItem(IS.of(Rarity.UNCOMMON)), DyeColor.GREEN);
	
	// Special tools
	// TODO: set attribute modifiers similarly to how vanilla swords do it
	public static final Item DREAMFLAYER = registerDeferredWithUniqueModel("dreamflayer", new DreamflayerItem(SpectrumToolMaterial.DREAMFLAYER, 3, -1.8F, IS.of(1, Rarity.UNCOMMON)), DyeColor.RED);
	public static final Item NIGHTFALLS_BLADE = registerDeferredWithUniqueModel("nightfalls_blade", new NightfallsBladeItem(ToolMaterials.DIAMOND, 3, -2.4F, IS.of(1, Rarity.UNCOMMON)), DyeColor.GRAY);
	public static final DraconicTwinswordItem DRACONIC_TWINSWORD = registerDeferredWithUniqueModel("draconic_twinsword", new DraconicTwinswordItem(SpectrumToolMaterial.DRACONIC, 6, -3.0F, IS.of(1, Rarity.RARE)), DyeColor.YELLOW);
	public static final DragonTalonItem DRAGON_TALON = registerDeferredWithUniqueModel("dragon_talon", new DragonTalonItem(SpectrumToolMaterial.DRACONIC, -3.0, -1.0, IS.of(1, Rarity.RARE).maxDamage(SpectrumToolMaterial.DRACONIC.getDurability())), DyeColor.YELLOW);
	public static final LightGreatswordItem KNOTTED_SWORD = registerDeferredWithUniqueModel("knotted_sword", new LightGreatswordItem(SpectrumToolMaterial.VERDIGRIS, 3, -2.4F, 0.25F, 0.5F, 0xFFd4d6ff, IS.of(1, Rarity.UNCOMMON).maxDamage(SpectrumToolMaterial.VERDIGRIS.getDurability())), DyeColor.GREEN);
	public static final LightGreatswordItem NECTAR_LANCE = registerDeferredWithUniqueModel("nectar_lance", new NectarLanceItem(SpectrumToolMaterial.NECTAR, 0, -2.4F, 0.5F, 1.5F, 0xFFf8e8ff, IS.of(1, Rarity.EPIC).maxDamage(SpectrumToolMaterial.NECTAR.getDurability())), DyeColor.PURPLE);
	
	// Bedrock Armor
	public static final Item BEDROCK_HELMET = registerDeferred("bedrock_helmet", new BedrockArmorItem(SpectrumArmorMaterials.BEDROCK, ArmorItem.Type.HELMET, IS.of(Rarity.UNCOMMON).fireproof().maxDamage(70 * 13).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(Enchantments.PROJECTILE_PROTECTION, 5);
		}
	}, DyeColor.BLACK);
	public static final Item BEDROCK_CHESTPLATE = registerDeferred("bedrock_chestplate", new BedrockArmorItem(SpectrumArmorMaterials.BEDROCK, ArmorItem.Type.CHESTPLATE, IS.of(Rarity.UNCOMMON).fireproof().maxDamage(70 * 15).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(Enchantments.PROTECTION, 5);
		}
	}, DyeColor.BLACK);
	public static final Item BEDROCK_LEGGINGS = registerDeferred("bedrock_leggings", new BedrockArmorItem(SpectrumArmorMaterials.BEDROCK, ArmorItem.Type.LEGGINGS, IS.of(Rarity.UNCOMMON).fireproof().maxDamage(70 * 16).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(Enchantments.BLAST_PROTECTION, 5);
		}
	}, DyeColor.BLACK);
	public static final Item BEDROCK_BOOTS = registerDeferred("bedrock_boots", new BedrockArmorItem(SpectrumArmorMaterials.BEDROCK, ArmorItem.Type.BOOTS, IS.of(Rarity.UNCOMMON).fireproof().maxDamage(70 * 11).component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(false))) {
		@Override
		public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
			return Map.of(Enchantments.FIRE_PROTECTION, 5);
		}
	}, DyeColor.BLACK);
	
	// Armor
	public static final Item FETCHLING_HELMET = registerDeferred("fetchling_helmet", new GemstoneArmorItem(SpectrumArmorMaterials.GEMSTONE, ArmorItem.Type.HELMET, IS.of(Rarity.UNCOMMON).maxDamage(9 * 13)), DyeColor.BLUE);
	public static final Item FEROCIOUS_CHESTPLATE = registerDeferred("ferocious_chestplate", new GemstoneArmorItem(SpectrumArmorMaterials.GEMSTONE, ArmorItem.Type.CHESTPLATE, IS.of(Rarity.UNCOMMON).maxDamage(9 * 15)), DyeColor.BLUE);
	public static final Item SYLPH_LEGGINGS = registerDeferred("sylph_leggings", new GemstoneArmorItem(SpectrumArmorMaterials.GEMSTONE, ArmorItem.Type.LEGGINGS, IS.of(Rarity.UNCOMMON).maxDamage(9 * 16)), DyeColor.BLUE);
	public static final Item OREAD_BOOTS = registerDeferred("oread_boots", new GemstoneArmorItem(SpectrumArmorMaterials.GEMSTONE, ArmorItem.Type.BOOTS, IS.of(Rarity.UNCOMMON).maxDamage(9 * 11)), DyeColor.BLUE);
	
	// Decay drops
	public static final Item VEGETAL = registerDeferred("vegetal", new CloakedItemWithLoomPattern(IS.of(), SpectrumAdvancements.CRAFT_BOTTLE_OF_FADING, Items.GUNPOWDER, SpectrumBannerPatterns.VEGETAL), DyeColor.LIME);
	public static final Item NEOLITH = registerDeferred("neolith", new CloakedItemWithLoomPattern(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.CRAFT_BOTTLE_OF_FAILING, Items.GUNPOWDER, SpectrumBannerPatterns.NEOLITH), DyeColor.PINK);
	public static final Item BEDROCK_DUST = registerDeferred("bedrock_dust", new CloakedItemWithLoomPattern(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.BREAK_DECAYED_BEDROCK, Items.GUNPOWDER, SpectrumBannerPatterns.BEDROCK_DUST), DyeColor.BLACK);
	
	public static final MidnightAberrationItem MIDNIGHT_ABERRATION = registerDeferred("midnight_aberration", new MidnightAberrationItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.CREATE_MIDNIGHT_ABERRATION, SpectrumItems.SPECTRAL_SHARD), DyeColor.GRAY);
	public static final Item MIDNIGHT_CHIP = registerDeferred("midnight_chip", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.CREATE_MIDNIGHT_ABERRATION, Items.GRAY_DYE), DyeColor.GRAY);
	
	public static final Item BISMUTH_FLAKE = registerDeferred("bismuth_flake", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.ENTER_DIMENSION, Items.CYAN_DYE), DyeColor.CYAN);
	public static final Item BISMUTH_CRYSTAL = registerDeferred("bismuth_crystal", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.ENTER_DIMENSION, Items.CYAN_DYE), DyeColor.CYAN);
	public static final Item RAW_MALACHITE = registerDeferred("raw_malachite", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.REVEAL_MALACHITE, Items.GREEN_DYE), DyeColor.GREEN);
	public static final Item REFINED_MALACHITE = registerDeferred("refined_malachite", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.REVEAL_MALACHITE, Items.GREEN_DYE), DyeColor.GREEN);
	
	// Fluid Buckets
	public static final Item LIQUID_CRYSTAL_BUCKET = registerDeferred("liquid_crystal_bucket", new BucketItem(LIQUID_CRYSTAL, IS.of(1).recipeRemainder(Items.BUCKET)), DyeColor.LIGHT_GRAY);
	public static final Item GOO_BUCKET = registerDeferred("goo_bucket", new BucketItem(GOO, IS.of(1).recipeRemainder(Items.BUCKET)), DyeColor.BROWN);
	public static final Item MIDNIGHT_SOLUTION_BUCKET = registerDeferred("midnight_solution_bucket", new BucketItem(MIDNIGHT_SOLUTION, IS.of(1).recipeRemainder(Items.BUCKET)), DyeColor.GRAY);
	public static final Item DRAGONROT_BUCKET = registerDeferred("dragonrot_bucket", new BucketItem(DRAGONROT, IS.of(1).recipeRemainder(Items.BUCKET)), DyeColor.LIGHT_GRAY);
	
	// Decay bottles
	public static final Item BOTTLE_OF_FADING = registerDeferred("bottle_of_fading", new DecayPlacerItem(SpectrumBlocks.FADING, IS.of(16), List.of(Text.translatable("item.spectrum.bottle_of_fading.tooltip"))), DyeColor.GRAY);
	public static final Item BOTTLE_OF_FAILING = registerDeferred("bottle_of_failing", new DecayPlacerItem(SpectrumBlocks.FAILING, IS.of(16), List.of(Text.translatable("item.spectrum.bottle_of_failing.tooltip"))), DyeColor.GRAY);
	public static final Item BOTTLE_OF_RUIN = registerDeferred("bottle_of_ruin", new DecayPlacerItem(SpectrumBlocks.RUIN, IS.of(16), List.of(Text.translatable("item.spectrum.bottle_of_ruin.tooltip"))), DyeColor.GRAY);
	public static final Item BOTTLE_OF_FORFEITURE = registerDeferred("bottle_of_forfeiture", new DecayPlacerItem(SpectrumBlocks.FORFEITURE, IS.of(16), List.of(CreativeOnlyItem.DESCRIPTION, Text.translatable("item.spectrum.bottle_of_forfeiture.tooltip"))), DyeColor.GRAY);
	public static final Item BOTTLE_OF_DECAY_AWAY = registerDeferred("bottle_of_decay_away", new DecayPlacerItem(SpectrumBlocks.DECAY_AWAY, IS.of(16), List.of(Text.translatable("item.spectrum.bottle_of_decay_away.tooltip"))), DyeColor.PINK);
	
	// Resources
	public static final Item SHIMMERSTONE_GEM = registerDeferred("shimmerstone_gem", new CloakedItemWithLoomPattern(IS.of(), ((RevelationAware) SpectrumBlocks.SHIMMERSTONE_ORE).getCloakAdvancementIdentifier(), Items.YELLOW_DYE, SpectrumBannerPatterns.SHIMMERSTONE), DyeColor.YELLOW);
	public static final Item RAW_AZURITE = registerDeferred("raw_azurite", new CloakedItemWithLoomPattern(IS.of(), SpectrumBlocks.AZURITE_ORE.getCloakAdvancementIdentifier(), Items.BLUE_DYE, SpectrumBannerPatterns.RAW_AZURITE), DyeColor.BLUE);
	public static final Item REFINED_AZURITE = registerDeferred("refined_azurite", new CloakedItem(IS.of(), SpectrumBlocks.AZURITE_ORE.getCloakAdvancementIdentifier(), Items.BLUE_DYE), DyeColor.BLUE);
	public static final CloakedFloatItem PALTAERIA_FRAGMENTS = registerDeferred("paltaeria_fragments", new CloakedFloatItem(IS.of(), 0.00125F, ((RevelationAware) SpectrumBlocks.PALTAERIA_ORE).getCloakAdvancementIdentifier(), Items.CYAN_DYE), DyeColor.LIGHT_BLUE);
	public static final CloakedFloatItem PALTAERIA_GEM = registerDeferred("paltaeria_gem", new CloakedFloatItem(IS.of(16), 0.01F, ((RevelationAware) SpectrumBlocks.PALTAERIA_ORE).getCloakAdvancementIdentifier(), Items.CYAN_DYE), DyeColor.LIGHT_BLUE);
	public static final CloakedFloatItem STRATINE_FRAGMENTS = registerDeferred("stratine_fragments", new CloakedFloatItem(IS.of().fireproof(), -0.00125F, ((RevelationAware) SpectrumBlocks.STRATINE_ORE).getCloakAdvancementIdentifier(), Items.RED_DYE), DyeColor.RED);
	public static final CloakedFloatItem STRATINE_GEM = registerDeferred("stratine_gem", new CloakedFloatItem(IS.of(16).fireproof(), -0.01F, ((RevelationAware) SpectrumBlocks.STRATINE_ORE).getCloakAdvancementIdentifier(), Items.RED_DYE), DyeColor.RED);
	public static final Item PYRITE_CHUNK = registerDeferred("pyrite_chunk", new Item(IS.of()), DyeColor.PURPLE);
	public static final Item DRAGONBONE_CHUNK = registerDeferred("dragonbone_chunk", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.BREAK_CRACKED_DRAGONBONE, Items.GRAY_DYE), DyeColor.GRAY);
	public static final Item BONE_ASH = registerDeferred("bone_ash", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.BREAK_CRACKED_DRAGONBONE, Items.GRAY_DYE), DyeColor.GRAY);
	public static final Item RESPLENDENT_FEATHER = registerDeferredWithUniqueModel("resplendent_feather", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.PLUCK_RESPLENDENT_FEATHER, Items.RED_DYE), DyeColor.YELLOW);
	public static final Item RAW_BLOODSTONE = registerDeferred("raw_bloodstone", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.PLUCK_RESPLENDENT_FEATHER, Items.RED_DYE), DyeColor.RED);
	public static final Item REFINED_BLOODSTONE = registerDeferred("refined_bloodstone", new CloakedItem(IS.of(Rarity.UNCOMMON), SpectrumAdvancements.PLUCK_RESPLENDENT_FEATHER, Items.RED_DYE), DyeColor.RED);
	public static final Item DOWNSTONE_FRAGMENTS = registerDeferred("downstone_fragments", new CloakedItem(IS.of(16, Rarity.UNCOMMON), SpectrumAdvancements.FIND_EXCAVATION_SITE, Items.LIGHT_GRAY_DYE), DyeColor.LIGHT_GRAY);
	public static final Item RESONANCE_SHARD = registerDeferred("resonance_shard", new CloakedItem(IS.of(16, Rarity.UNCOMMON), SpectrumAdvancements.STRIKE_UP_HUMMINGSTONE_HYMN, Items.LIGHT_BLUE_DYE), DyeColor.WHITE);
	public static final Item AETHER_VESTIGES = registerDeferred("aether_vestiges", new AetherVestigesItem(IS.of(1, Rarity.EPIC).fireproof(), "item.spectrum.aether_vestiges.tooltip"), DyeColor.WHITE);
	
	public static final Item QUITOXIC_POWDER = registerDeferred("quitoxic_powder", new CloakedItem(IS.of(), SpectrumAdvancements.REVEAL_QUITOXIC_REEDS, Items.PURPLE_DYE), DyeColor.PURPLE);
	public static final Item STORM_STONE = registerDeferred("mermaids_gem", new StormStoneItem(IS.of(), SpectrumAdvancements.REVEAL_STORM_STONES, Items.YELLOW_DYE), DyeColor.LIGHT_BLUE);
	public static final Item MERMAIDS_GEM = registerDeferred("storm_stone", new MermaidsGemItem(SpectrumBlocks.MERMAIDS_BRUSH, IS.of()), DyeColor.YELLOW);
	public static final CloakedItem STAR_FRAGMENT = registerDeferred("star_fragment", new CloakedItem(IS.of(16), SpectrumAdvancements.UNLOCK_SHOOTING_STARS, Items.PURPLE_DYE), DyeColor.PURPLE);
	public static final Item STARDUST = registerDeferred("stardust", new CloakedItemWithLoomPattern(IS.of(), SpectrumAdvancements.UNLOCK_SHOOTING_STARS, Items.PURPLE_DYE, SpectrumBannerPatterns.SHIMMER), DyeColor.PURPLE);
	public static final Item ASH_FLAKES = registerDeferred("ash_flakes", new AshItem(IS.of()), DyeColor.LIGHT_GRAY);
	
	public static final Item HIBERNATING_JADE_VINE_BULB = registerDeferred("hibernating_jade_vine_bulb", new ItemWithTooltip(IS.of(16), "item.spectrum.hibernating_jade_vine_bulb.tooltip"), DyeColor.GRAY);
	public static final Item GERMINATED_JADE_VINE_BULB = registerDeferred("germinated_jade_vine_bulb", new GerminatedJadeVineBulbItem(IS.of(16), SpectrumAdvancements.COLLECT_HIBERNATING_JADE_VINE_BULB, Items.LIME_DYE), DyeColor.LIME);
	public static final Item JADE_VINE_PETALS = registerDeferred("jade_vine_petals", new CloakedItemWithLoomPattern(IS.of(), SpectrumAdvancements.BUILD_SPIRIT_INSTILLER_STRUCTURE, Items.LIME_DYE, SpectrumBannerPatterns.JADE_VINE), DyeColor.LIME); // TODO: Funky unlock?
	
	public static final Item JADEITE_PETALS = registerDeferred("jadeite_petals", new Item(IS.of(Rarity.UNCOMMON)), DyeColor.BROWN);
	
	public static final Item BLOOD_ORCHID_PETAL = registerDeferred("blood_orchid_petal", new CloakedItem(IS.of(), SpectrumAdvancements.SOLVE_WIRELESS_REDSTONE_PRESERVATION_RUIN, Items.RED_DYE), DyeColor.RED);
	
	public static final Item ROCK_CANDY = registerDeferred("rock_candy", new RockCandyItem(IS.of().food(SpectrumFoodComponents.ROCK_CANDY), RockCandy.RockCandyVariant.SUGAR), DyeColor.PINK);
	public static final Item TOPAZ_ROCK_CANDY = registerDeferred("topaz_rock_candy", new RockCandyItem(IS.of().food(SpectrumFoodComponents.TOPAZ_ROCK_CANDY), RockCandy.RockCandyVariant.TOPAZ), DyeColor.CYAN);
	public static final Item AMETHYST_ROCK_CANDY = registerDeferred("amethyst_rock_candy", new RockCandyItem(IS.of().food(SpectrumFoodComponents.AMETHYST_ROCK_CANDY), RockCandy.RockCandyVariant.AMETHYST), DyeColor.MAGENTA);
	public static final Item CITRINE_ROCK_CANDY = registerDeferred("citrine_rock_candy", new RockCandyItem(IS.of().food(SpectrumFoodComponents.CITRINE_ROCK_CANDY), RockCandy.RockCandyVariant.CITRINE), DyeColor.YELLOW);
	public static final Item ONYX_ROCK_CANDY = registerDeferred("onyx_rock_candy", new RockCandyItem(IS.of().food(SpectrumFoodComponents.ONYX_ROCK_CANDY), RockCandy.RockCandyVariant.ONYX), DyeColor.BLACK);
	public static final Item MOONSTONE_ROCK_CANDY = registerDeferred("moonstone_rock_candy", new RockCandyItem(IS.of().food(SpectrumFoodComponents.MOONSTONE_ROCK_CANDY), RockCandy.RockCandyVariant.MOONSTONE), DyeColor.WHITE);
	
	public static final Item BLOODBOIL_SYRUP = registerDeferred("bloodboil_syrup", new DrinkItem(IS.of().food(SpectrumFoodComponents.BLOODBOIL_SYRUP).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.RED);
	public static final Item MILKY_RESIN = registerDeferred("milky_resin", new Item(IS.of(Rarity.UNCOMMON)), DyeColor.LIGHT_GRAY);
	
	// Food & drinks
	public static final Item MOONSTRUCK_NECTAR = registerDeferred("moonstruck_nectar", new MoonstruckNectarItem(IS.of(Rarity.UNCOMMON).food(SpectrumFoodComponents.MOONSTRUCK_NECTAR).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.LIME);
	public static final Item JADE_JELLY = registerDeferred("jade_jelly", new ItemWithTooltip(IS.of().food(SpectrumFoodComponents.JADE_JELLY), "item.spectrum.jade_jelly.tooltip"), DyeColor.LIME);
	public static final Item GLASS_PEACH = registerDeferred("glass_peach", new ItemWithTooltip(IS.of().food(SpectrumFoodComponents.GLASS_PEACH), "item.spectrum.glass_peach.tooltip"), DyeColor.PINK);
	public static final Item FISSURE_PLUM = registerDeferred("fissure_plum", new AliasedTooltipItem(SpectrumBlocks.ABYSSAL_VINES, IS.of().food(SpectrumFoodComponents.FISSURE_PLUM), "item.spectrum.fissure_plum.tooltip"), DyeColor.BROWN);
	public static final Item NIGHTDEW_SPROUT = registerDeferred("nightdew_sprout", new AliasedTooltipItem(SpectrumBlocks.NIGHTDEW, IS.of().food(SpectrumFoodComponents.NIGHTDEW_SPROUT), "item.spectrum.nightdew_sprout.tooltip"), DyeColor.PURPLE);
	public static final Item NECTARDEW_BURGEON = registerDeferred("nectardew_burgeon", new NectardewBurgeonItem(IS.of().food(SpectrumFoodComponents.NECTARDEW_BURGEON), "item.spectrum.nectardew_burgeon.tooltip", SpectrumAdvancements.COLLECT_NECTARDEW, SpectrumItems.NIGHTDEW_SPROUT), DyeColor.PURPLE);
	public static final Item RESTORATION_TEA = registerDeferred("restoration_tea", new RestorationTeaItem(IS.of(16).food(SpectrumFoodComponents.RESTORATION_TEA).recipeRemainder(Items.GLASS_BOTTLE).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.RESTORATION_TEA_SCONE_BONUS))), DyeColor.PINK);
	public static final Item KIMCHI = registerDeferred("kimchi", new KimchiItem(IS.of().food(SpectrumFoodComponents.KIMCHI)), DyeColor.PINK);
	public static final Item CLOTTED_CREAM = registerDeferred("clotted_cream", new ClottedCreamItem(IS.of().food(SpectrumFoodComponents.CLOTTED_CREAM), new String[]{"item.spectrum.clotted_cream.tooltip", "item.spectrum.clotted_cream.tooltip2"}), DyeColor.PINK);
	public static final Item FRESH_CHOCOLATE = registerDeferred("fresh_chocolate", new Item(IS.of().food(SpectrumFoodComponents.FRESH_CHOCOLATE)), DyeColor.PINK);
	public static final Item HOT_CHOCOLATE = registerDeferred("hot_chocolate", new DrinkItem(IS.of(16).food(SpectrumFoodComponents.HOT_CHOCOLATE).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.HOT_CHOCOLATE_SCONE_BONUS))), DyeColor.PINK);
	public static final Item KARAK_CHAI = registerDeferred("karak_chai", new DrinkItem(IS.of(16).food(SpectrumFoodComponents.KARAK_CHAI).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.KARAK_CHAI_SCONE_BONUS))), DyeColor.PINK);
	public static final Item AZALEA_TEA = registerDeferred("azalea_tea", new AzaleaTeaItem(IS.of(16).food(SpectrumFoodComponents.AZALEA_TEA).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.AZALEA_TEA_SCONE_BONUS))), DyeColor.PURPLE);
	public static final Item BODACIOUS_BERRY_BAR = registerDeferred("bodacious_berry_bar", new Item(IS.of().food(SpectrumFoodComponents.BODACIOUS_BERRY_BAR)), DyeColor.PINK);
	public static final Item DEMON_TEA = registerDeferred("demon_tea", new DrinkItem(IS.of(16).food(SpectrumFoodComponents.DEMON_TEA).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.DEMON_TEA_SCONE_BONUS))), DyeColor.RED);
	public static final Item SCONE = registerDeferred("scone", new Item(IS.of().food(SpectrumFoodComponents.SCONE)), DyeColor.PINK);
	
	public static final Item CHEONG = registerDeferredWithUniqueModel("cheong", new ItemWithTooltip(IS.of().food(SpectrumFoodComponents.CHEONG), "item.spectrum.cheong.tooltip"), DyeColor.PINK);
	public static final Item MERMAIDS_JAM = registerDeferred("mermaids_jam", new Item(IS.of().food(SpectrumFoodComponents.MERMAIDS_JAM)), DyeColor.PINK);
	public static final Item MERMAIDS_POPCORN = registerDeferred("mermaids_popcorn", new ItemWithTooltip(IS.of().food(SpectrumFoodComponents.MERMAIDS_POPCORN), "item.spectrum.mermaids_popcorn.tooltip"), DyeColor.PINK);
	public static final Item LE_FISHE_AU_CHOCOLAT = registerDeferred("le_fishe_au_chocolat", new Item(IS.of().food(SpectrumFoodComponents.LE_FISHE_AU_CHOCOLAT)), DyeColor.PINK);
	//public static final Item STUFFED_PETALS = registerDeferred("stuffed_petals", new Item(IS.of().food(SpectrumFoodComponents.STUFFED_PETALS)), DyeColor.PINK);
	//public static final Item PASTICHE = registerDeferred("pastiche", new Item(IS.of().food(SpectrumFoodComponents.PASTICHE)), DyeColor.PINK);
	//public static final Item VITTORIAS_ROAST = registerDeferred("vittorias_roast", new Item(IS.of().food(SpectrumFoodComponents.VITTORIAS_ROAST)), DyeColor.PINK);
	
	public static final Item INFUSED_BEVERAGE = registerDeferredWithUniqueModel("infused_beverage", new BeverageItem(IS.of(16).food(SpectrumFoodComponents.BEVERAGE).recipeRemainder(Items.GLASS_BOTTLE).component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).component(SpectrumDataComponentTypes.INFUSED_BEVERAGE, InfusedBeverageComponent.DEFAULT)), DyeColor.PINK);
	public static final Item SUSPICIOUS_BREW = registerDeferred("suspicious_brew", new SuspiciousBrewItem(IS.of(16).food(SpectrumFoodComponents.BEVERAGE).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.LIME);
	public static final Item REPRISE = registerDeferred("reprise", new RepriseItem(IS.of(16).food(SpectrumFoodComponents.BEVERAGE).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.PINK);
	public static final Item PURE_ALCOHOL = registerDeferred("pure_alcohol", new DrinkItem(IS.of(16, Rarity.UNCOMMON).food(SpectrumFoodComponents.PURE_ALCOHOL).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.WHITE);
	public static final Item JADE_WINE = registerDeferred("jade_wine", new JadeWineItem(IS.of(16, Rarity.UNCOMMON).food(SpectrumFoodComponents.BEVERAGE).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.LIME);
	public static final Item CHRYSOCOLLA = registerDeferred("chrysocolla", new DrinkItem(IS.of(16, Rarity.UNCOMMON).food(SpectrumFoodComponents.PURE_ALCOHOL).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.LIME);
	
	public static final Item HONEY_PASTRY = registerDeferred("honey_pastry", new Item(IS.of().food(SpectrumFoodComponents.HONEY_PASTRY)), DyeColor.PINK);
	public static final Item LUCKY_ROLL = registerDeferred("lucky_roll", new Item(IS.of(16).food(SpectrumFoodComponents.LUCKY_ROLL)), DyeColor.PINK);
	public static final Item TRIPLE_MEAT_POT_PIE = registerDeferred("triple_meat_pot_pie", new Item(IS.of(8).food(SpectrumFoodComponents.TRIPLE_MEAT_POT_PIE)), DyeColor.PINK);
	public static final Item GLISTERING_JELLY_TEA = registerDeferred("glistering_jelly_tea", new DrinkItem(IS.of(16).food(SpectrumFoodComponents.GLISTERING_JELLY_TEA).recipeRemainder(Items.GLASS_BOTTLE).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.GLISTERING_JELLY_TEA_SCONE_BONUS))), DyeColor.PINK);
	public static final Item FREIGEIST = registerDeferred("freigeist", new FreigeistItem(IS.of(16).food(SpectrumFoodComponents.FREIGEIST).recipeRemainder(Items.GLASS_BOTTLE)), DyeColor.RED);
	public static final Item DIVINATION_HEART = registerDeferred("divination_heart", new Item(IS.of().food(SpectrumFoodComponents.DIVINATION_HEART)), DyeColor.RED);
	
	public static final Item STAR_CANDY = registerDeferred("star_candy", new StarCandyItem(IS.of(Rarity.UNCOMMON).food(SpectrumFoodComponents.STAR_CANDY)), DyeColor.PINK);
	public static final Item ENCHANTED_STAR_CANDY = registerDeferred("enchanted_star_candy", new EnchantedStarCandyItem(IS.of(Rarity.UNCOMMON).food(SpectrumFoodComponents.ENCHANTED_STAR_CANDY)), DyeColor.PINK);
	
	public static final Item ENCHANTED_GOLDEN_CARROT = registerDeferredWithUniqueModel("enchanted_golden_carrot", new ItemWithGlint(IS.of(Rarity.EPIC).food(SpectrumFoodComponents.ENCHANTED_GOLDEN_CARROT)), DyeColor.PINK);
	public static final Item JARAMEL = registerDeferred("jaramel", new Item(IS.of().food(SpectrumFoodComponents.JARAMEL)), DyeColor.PINK);
	
	public static final Item JARAMEL_TART = registerDeferred("jaramel_tart", new Item(IS.of().food(SpectrumFoodComponents.JARAMEL_TART)), DyeColor.PINK);
	public static final Item SALTED_JARAMEL_TART = registerDeferred("salted_jaramel_tart", new Item(IS.of().food(SpectrumFoodComponents.SALTED_JARAMEL_TART)), DyeColor.PINK);
	public static final Item ASHEN_TART = registerDeferred("ashen_tart", new Item(IS.of().food(SpectrumFoodComponents.ASHEN_TART)), DyeColor.PINK);
	public static final Item WEEPING_TART = registerDeferred("weeping_tart", new Item(IS.of().food(SpectrumFoodComponents.WEEPING_TART)), DyeColor.PINK);
	public static final Item WHISPY_TART = registerDeferred("whispy_tart", new Item(IS.of().food(SpectrumFoodComponents.WHISPY_TART)), DyeColor.PINK);
	public static final Item PUFF_TART = registerDeferred("puff_tart", new Item(IS.of().food(SpectrumFoodComponents.PUFF_TART)), DyeColor.PINK);
	
	public static final Item JARAMEL_TRIFLE = registerDeferred("jaramel_trifle", new Item(IS.of().food(SpectrumFoodComponents.JARAMEL_TRIFLE)), DyeColor.PINK);
	public static final Item SALTED_JARAMEL_TRIFLE = registerDeferred("salted_jaramel_trifle", new Item(IS.of().food(SpectrumFoodComponents.SALTED_JARAMEL_TRIFLE)), DyeColor.PINK);
	public static final Item MONSTER_TRIFLE = registerDeferred("monster_trifle", new Item(IS.of().food(SpectrumFoodComponents.MONSTER_TRIFLE)), DyeColor.PINK);
	public static final Item DEMON_TRIFLE = registerDeferred("demon_trifle", new Item(IS.of().food(SpectrumFoodComponents.DEMON_TRIFLE)), DyeColor.PINK);
	
	public static final Item MYCEYLON = registerDeferred("myceylon", new CloakedItem(IS.of(), SpectrumAdvancements.COLLECT_MYCEYLON, Items.ORANGE_DYE), DyeColor.PINK);
	public static final Item MYCEYLON_APPLE_PIE = registerDeferred("myceylon_apple_pie", new Item(IS.of().food(SpectrumFoodComponents.MYCEYLON_APPLE_PIE)), DyeColor.PINK);
	public static final Item MYCEYLON_PUMPKIN_PIE = registerDeferred("myceylon_pumpkin_pie", new Item(IS.of().food(SpectrumFoodComponents.MYCEYLON_PUMPKIN_PIE)), DyeColor.PINK);
	public static final Item MYCEYLON_COOKIE = registerDeferred("myceylon_cookie", new Item(IS.of().food(SpectrumFoodComponents.MYCEYLON_COOKIE)), DyeColor.PINK);
	public static final Item ALOE_LEAF = registerDeferred("aloe_leaf", new AliasedBlockItem(SpectrumBlocks.ALOE, IS.of().food(SpectrumFoodComponents.ALOE_LEAF)), DyeColor.PINK);
	public static final Item SAWBLADE_HOLLY_BERRY = registerDeferred("sawblade_holly_berry", new AliasedBlockItem(SpectrumBlocks.SAWBLADE_HOLLY_BUSH, IS.of().food(FoodComponents.SWEET_BERRIES)), DyeColor.PINK);
	public static final Item PRICKLY_BAYLEAF = registerDeferred("prickly_bayleaf", new Item(IS.of().food(SpectrumFoodComponents.PRICKLY_BAYLEAF)), DyeColor.PINK);
	public static final Item TRIPLE_MEAT_POT_STEW = registerDeferred("triple_meat_pot_stew", new StackableStewItem(IS.of(8).food(SpectrumFoodComponents.TRIPLE_MEAT_POT_STEW)), DyeColor.PINK);
	public static final Item DRAGONBONE_BROTH = registerDeferred("dragonbone_broth", new StackableStewItem(IS.of(8).food(SpectrumFoodComponents.DRAGONBONE_BROTH)), DyeColor.GRAY);
	public static final Item DOOMBLOOM_SEED = registerDeferred("doombloom_seed", new AliasedBlockItem(SpectrumBlocks.DOOMBLOOM, IS.of().fireproof()), DyeColor.BLACK);
	
	public static final RegistryKey<Item> GLISTERING_MELON_SEEDS = registerDeferredSupplier("glistering_melon_seeds", () -> new AliasedBlockItem(Registries.BLOCK.get(SpectrumBlocks.GLISTERING_MELON_STEM), IS.of()), DyeColor.LIME);
	public static final Item AMARANTH_GRAINS = registerDeferred("amaranth_grains", new AliasedBlockItem(SpectrumBlocks.AMARANTH, IS.of()), DyeColor.LIME);
	
	// Cookbooks
	public static final Item MELOCHITES_COOKBOOK_VOL_1 = registerDeferred("melochites_cookbook_vol_1", new CookbookItem(IS.of().maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, SpectrumCommon.locate("cuisine/cookbooks/melochites_cookbook_vol_1"))), DyeColor.PURPLE);
	public static final Item MELOCHITES_COOKBOOK_VOL_2 = registerDeferred("melochites_cookbook_vol_2", new CookbookItem(IS.of().maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, SpectrumCommon.locate("cuisine/cookbooks/melochites_cookbook_vol_2"))), DyeColor.PURPLE);
	public static final Item IMBRIFER_COOKBOOK = registerDeferred("imbrifer_cookbook", new CookbookItem(IS.of().maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, SpectrumCommon.locate("cuisine/cookbooks/imbrifer_cookbook"))), DyeColor.PURPLE);
	public static final Item IMPERIAL_COOKBOOK = registerDeferred("imperial_cookbook", new CookbookItem(IS.of().maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, SpectrumCommon.locate("cuisine/cookbooks/imperial_cookbook"))), DyeColor.PURPLE);
	public static final Item BREWERS_HANDBOOK = registerDeferred("brewers_handbook", new CookbookItem(IS.of().maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, SpectrumCommon.locate("cuisine/cookbooks/brewers_handbook"))), DyeColor.PURPLE);
	//public static final Item VARIA_COOKBOOK = registerDeferred("varia_cookbook", new CookbookItem(IS.of().maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, SpectrumCommon.locate("cuisine/cookbooks/varia_cookbook"))), DyeColor.PURPLE);
	public static final Item POISONERS_HANDBOOK = registerDeferred("poisoners_handbook", new CookbookItem(IS.of().maxCount(1).rarity(Rarity.EPIC), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, SpectrumCommon.locate("dimension/lore/poisoners_handbook")), SpectrumStatusEffects.ETERNAL_SLUMBER_COLOR), DyeColor.PURPLE);
	
	public static final Item AQUA_REGIA = registerDeferred("aqua_regia", new JadeWineItem(IS.of(16).food(SpectrumFoodComponents.AQUA_REGIA)), DyeColor.PINK);
	public static final Item BAGNUN = registerDeferred("bagnun", new Item(IS.of().food(SpectrumFoodComponents.BAGNUN)), DyeColor.PINK);
	public static final Item BANYASH = registerDeferred("banyash", new Item(IS.of().food(SpectrumFoodComponents.BANYASH)), DyeColor.PINK);
	public static final Item BERLINER = registerDeferred("berliner", new Item(IS.of().food(SpectrumFoodComponents.BERLINER)), DyeColor.PINK);
	public static final Item BRISTLE_MEAD = registerDeferred("bristle_mead", new BeverageItem(IS.of(16).food(SpectrumFoodComponents.BEVERAGE).component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)), DyeColor.PINK);
	public static final Item CHAUVE_SOURIS_AU_VIN = registerDeferred("chauve_souris_au_vin", new Item(IS.of().food(SpectrumFoodComponents.CHAUVE_SOURIS_AU_VIN)), DyeColor.PINK);
	public static final Item CRAWFISH = registerDeferred("crawfish", new Item(IS.of().food(SpectrumFoodComponents.CRAWFISH)), DyeColor.PINK);
	public static final Item CRAWFISH_COCKTAIL = registerDeferred("crawfish_cocktail", new Item(IS.of().food(SpectrumFoodComponents.CRAWFISH_COCKTAIL)), DyeColor.PINK);
	public static final Item CREAM_PASTRY = registerDeferred("cream_pastry", new Item(IS.of().food(SpectrumFoodComponents.CREAM_PASTRY)), DyeColor.PINK);
	public static final Item FADED_KOI = registerDeferred("faded_koi", new Item(IS.of().food(SpectrumFoodComponents.FADED_KOI)), DyeColor.PINK);
	public static final Item FISHCAKE = registerDeferred("fishcake", new Item(IS.of().food(SpectrumFoodComponents.FISHCAKE)), DyeColor.PINK);
	public static final Item LIZARD_MEAT = registerDeferred("lizard_meat", new Item(IS.of().food(SpectrumFoodComponents.LIZARD_MEAT)), DyeColor.PINK);
	public static final Item COOKED_LIZARD_MEAT = registerDeferred("cooked_lizard_meat", new Item(IS.of().food(SpectrumFoodComponents.COOKED_LIZARD_MEAT)), DyeColor.PINK);
	public static final Item GOLDEN_BRISTLE_TEA = registerDeferred("golden_bristle_tea", new DrinkItem(IS.of(16).food(SpectrumFoodComponents.GOLDEN_BRISTLE_TEA).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.GOLDEN_BRISTLE_TEA_SCONE_BONUS))), DyeColor.PINK);
	public static final Item HARE_ROAST = registerDeferred("hare_roast", new Item(IS.of().food(SpectrumFoodComponents.HARE_ROAST)), DyeColor.PINK);
	public static final Item JUNKET = registerDeferred("junket", new Item(IS.of().food(SpectrumFoodComponents.JUNKET)), DyeColor.PINK);
	public static final Item KOI = registerDeferred("koi", new Item(IS.of().food(SpectrumFoodComponents.KOI)), DyeColor.PINK);
	public static final Item MEATLOAF = registerDeferred("meatloaf", new Item(IS.of().food(SpectrumFoodComponents.MEATLOAF)), DyeColor.PINK);
	public static final Item MEATLOAF_SANDWICH = registerDeferred("meatloaf_sandwich", new Item(IS.of().food(SpectrumFoodComponents.MEATLOAF_SANDWICH)), DyeColor.PINK);
	public static final Item MELLOW_SHALLOT_SOUP = registerDeferred("mellow_shallot_soup", new Item(IS.of().food(SpectrumFoodComponents.MELLOW_SHALLOT_SOUP)), DyeColor.PINK);
	public static final Item MORCHELLA = registerDeferred("morchella", new BeverageItem(IS.of(16).food(SpectrumFoodComponents.BEVERAGE).component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)), DyeColor.PINK);
	public static final Item NECTERED_VIOGNIER = registerDeferred("nectered_viognier", new JadeWineItem(IS.of(16).food(SpectrumFoodComponents.NECTERED_VIOGNIER)), DyeColor.PINK);
	public static final Item PEACHES_FLAMBE = registerDeferred("peaches_flambe", new Item(IS.of().food(SpectrumFoodComponents.PEACHES_FLAMBE)), DyeColor.PINK);
	public static final Item PEACH_CREAM = registerDeferred("peach_cream", new DrinkItem(IS.of(16).food(SpectrumFoodComponents.PEACH_CREAM).component(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT, teaSconeBonus(SpectrumFoodComponents.PEACH_CREAM_SCONE_BONUS))), DyeColor.PINK);
	public static final Item PEACH_JAM = registerDeferred("peach_jam", new Item(IS.of().food(SpectrumFoodComponents.PEACH_JAM)), DyeColor.PINK);
	public static final Item RABBIT_CREAM_PIE = registerDeferred("rabbit_cream_pie", new ItemWithTooltip(IS.of().food(SpectrumFoodComponents.RABBIT_CREAM_PIE), "item.spectrum.rabbit_cream_pie.tooltip"), DyeColor.PINK);
	public static final Item SEDATIVES = registerDeferred("sedatives", new SedativesItem(IS.of().food(SpectrumFoodComponents.SEDATIVES), "item.spectrum.sedatives.tooltip"), DyeColor.PINK);
	public static final Item SLUSHSLIDE = registerDeferred("slushslide", new ItemWithTooltip(IS.of().food(SpectrumFoodComponents.SLUSHSLIDE), "item.spectrum.slushslide.tooltip"), DyeColor.PINK);
	public static final Item SURSTROMMING = registerDeferred("surstromming", new Item(IS.of().food(SpectrumFoodComponents.SURSTROMMING)), DyeColor.PINK);
	public static final Item EVERNECTAR = registerDeferred("evernectar", new EvernectarItem(IS.of(1, Rarity.EPIC).food(SpectrumFoodComponents.EVERNECTAR).recipeRemainder(Items.GLASS_BOTTLE), "item.spectrum.evernectar.tooltip"), DyeColor.LIME);
	
	// Banner Patterns
	public static final Item LOGO_BANNER_PATTERN = registerDeferredWithUniqueModel("logo_banner_pattern", new BannerPatternItem(SpectrumBannerPatternTags.SPECTRUM_LOGO_TAG, IS.of(1, Rarity.UNCOMMON)), DyeColor.LIGHT_BLUE);
	public static final Item AMETHYST_SHARD_BANNER_PATTERN = registerDeferredWithUniqueModel("amethyst_shard_banner_pattern", new BannerPatternItem(SpectrumBannerPatternTags.AMETHYST_SHARD_TAG, IS.of(1)), DyeColor.LIGHT_BLUE);
	public static final Item AMETHYST_CLUSTER_BANNER_PATTERN = registerDeferredWithUniqueModel("amethyst_cluster_banner_pattern", new BannerPatternItem(SpectrumBannerPatternTags.AMETHYST_CLUSTER_TAG, IS.of(1)), DyeColor.LIGHT_BLUE);
	public static final Item ASTROLOGER_BANNER_PATTERN = registerDeferredWithUniqueModel("astrologer_banner_pattern", new BannerPatternItem(SpectrumBannerPatternTags.ASTROLOGER_TAG, IS.of(1, Rarity.UNCOMMON)), DyeColor.LIGHT_BLUE);
	public static final Item VELVET_ASTROLOGER_BANNER_PATTERN = registerDeferredWithUniqueModel("velvet_astrologer_banner_pattern", new BannerPatternItem(SpectrumBannerPatternTags.VELVET_ASTROLOGER_TAG, IS.of(1, Rarity.UNCOMMON)), DyeColor.LIGHT_BLUE);
	public static final Item POISONBLOOM_BANNER_PATTERN = registerDeferredWithUniqueModel("poisonbloom_banner_pattern", new BannerPatternItem(SpectrumBannerPatternTags.POISONBLOOM_TAG, IS.of(1, Rarity.RARE)), DyeColor.LIGHT_BLUE);
	public static final Item DEEP_LIGHT_BANNER_PATTERN = registerDeferredWithUniqueModel("deep_light_banner_pattern", new BannerPatternItem(SpectrumBannerPatternTags.DEEP_LIGHT_TAG, IS.of(1, Rarity.RARE)), DyeColor.LIGHT_BLUE);
	
	// Spawning items
	public static final Item BUCKET_OF_ERASER = registerDeferred("bucket_of_eraser", new EmptyFluidEntityBucketItem(SpectrumEntityTypes.ERASER, Fluids.EMPTY, SoundEvents.ITEM_BUCKET_EMPTY, IS.of()), DyeColor.PINK);
	public static final Item EGG_LAYING_WOOLY_PIG_SPAWN_EGG = registerDeferredWithUniqueModel("egg_laying_wooly_pig_spawn_egg", new SpawnEggItem(SpectrumEntityTypes.EGG_LAYING_WOOLY_PIG, 0x3a2c38, 0xfff2e0, IS.of()), DyeColor.WHITE);
	public static final Item PRESERVATION_TURRET_SPAWN_EGG = registerDeferredWithUniqueModel("preservation_turret_spawn_egg", new SpawnEggItem(SpectrumEntityTypes.PRESERVATION_TURRET, 0xf3f6f8, 0xc8c5be, IS.of()), DyeColor.WHITE);
	public static final Item KINDLING_SPAWN_EGG = registerDeferredWithUniqueModel("kindling_spawn_egg", new SpawnEggItem(SpectrumEntityTypes.KINDLING, 0xda4261, 0xffd452, IS.of()), DyeColor.WHITE);
	public static final Item LIZARD_SPAWN_EGG = registerDeferredWithUniqueModel("lizard_spawn_egg", new SpawnEggItem(SpectrumEntityTypes.LIZARD, 0x896459, 0x503a40, IS.of()), DyeColor.WHITE);
	public static final Item ERASER_SPAWN_EGG = registerDeferredWithUniqueModel("eraser_spawn_egg", new SpawnEggItem(SpectrumEntityTypes.ERASER, 0x200d29, 0xc83e93, IS.of()), DyeColor.WHITE);
	
	// Magical Tools
	public static final Item BAG_OF_HOLDING = registerDeferred("bag_of_holding", new BagOfHoldingItem(IS.of(1)), DyeColor.PURPLE);
	public static final Item RADIANCE_STAFF = registerDeferredWithUniqueModel("radiance_staff", new RadianceStaffItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.YELLOW);
	public static final NaturesStaffItem NATURES_STAFF = registerDeferredWithUniqueModel("natures_staff", new NaturesStaffItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.LIME);
	public static final Item STAFF_OF_REMEMBRANCE = registerDeferredWithUniqueModel("staff_of_remembrance", new StaffOfRemembranceItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.LIME);
	public static final Item CONSTRUCTORS_STAFF = registerDeferredWithUniqueModel("constructors_staff", new ConstructorsStaffItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.LIGHT_GRAY);
	public static final Item EXCHANGING_STAFF = registerDeferredWithUniqueModel("exchanging_staff", new ExchangeStaffItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.LIGHT_GRAY);
	public static final Item BLOCK_FLOODER = registerDeferred("block_flooder", new BlockFlooderItem(IS.of(Rarity.UNCOMMON)), DyeColor.LIGHT_GRAY);
	public static final Item PIPE_BOMB = registerDeferredWithUniqueModel("pipe_bomb", new PipeBombItem(IS.of(1)), DyeColor.ORANGE);
	public static final EnderSpliceItem ENDER_SPLICE = registerDeferredWithUniqueModel("ender_splice", new EnderSpliceItem(IS.of(16, Rarity.UNCOMMON)), DyeColor.PURPLE);
	public static final Item PERTURBED_EYE = registerDeferred("perturbed_eye", new PerturbedEyeItem(IS.of(Rarity.UNCOMMON)), DyeColor.RED);
	public static final Item CRESCENT_CLOCK = registerDeferredWithUniqueModel("crescent_clock", new ItemWithTooltip(IS.of(1), "item.spectrum.crescent_clock.tooltip"), DyeColor.MAGENTA);
	public static final Item PRIMORDIAL_LIGHTER = registerDeferred("primordial_lighter", new PrimordialLighterItem(IS.of(1)), DyeColor.ORANGE);
	
	public static final Item NIGHT_SALTS = registerDeferred("night_salts", new NightSaltsItem(IS.of(16)), DyeColor.PURPLE);
	public static final Item SOOTHING_BOUQUET = registerDeferred("soothing_bouquet", new SoothingBouquetItem(IS.of(1, Rarity.RARE)), DyeColor.PURPLE);
	public static final Item CONCEALING_OILS = registerDeferredWithUniqueModel("concealing_oils", new ConcealingOilsItem(IS.of(1)), DyeColor.BLACK);
	public static final Item BITTER_OILS = registerDeferred("bitter_oils", new DrinkItem(IS.of(16).food(SpectrumFoodComponents.BITTER_OILS)), DyeColor.BLUE);
	
	public static final Item INCANDESCENT_ESSENCE = registerDeferred("incandescent_essence", new CloakedItem(IS.of().fireproof(), SpectrumAdvancements.MIDGAME, Items.ORANGE_DYE), DyeColor.ORANGE);
	public static final Item FROSTBITE_ESSENCE = registerDeferred("frostbite_essence", new CloakedItem(IS.of(), SpectrumAdvancements.MIDGAME, Items.LIGHT_BLUE_DYE), DyeColor.LIGHT_BLUE);
	public static final Item MOONSTONE_CORE = registerDeferred("moonstone_core", new CloakedItem(IS.of(16, Rarity.RARE), SpectrumAdvancements.FIND_FORGOTTEN_CITY, Items.WHITE_DYE), DyeColor.WHITE);
	
	// Music discs
	public static final Item MUSIC_DISC_DISCOVERY = registerDeferred("music_disc_discovery", new Item(IS.of(1, Rarity.RARE).jukeboxPlayable(SpectrumJukeboxSongs.DISCOVERY)), DyeColor.GREEN);
	public static final Item MUSIC_DISC_CREDITS = registerDeferred("music_disc_credits", new Item(IS.of(1, Rarity.RARE).jukeboxPlayable(SpectrumJukeboxSongs.CREDITS)), DyeColor.GREEN);
	public static final Item MUSIC_DISC_DIVINITY = registerDeferred("music_disc_divinity", new Item(IS.of(1, Rarity.RARE).jukeboxPlayable(SpectrumJukeboxSongs.DIVINITY)), DyeColor.GREEN);
	
	// Item Frames
	public static final Item PHANTOM_FRAME = registerDeferred("phantom_frame", new PhantomFrameItem(SpectrumEntityTypes.PHANTOM_FRAME, IS.of()), DyeColor.YELLOW);
	public static final Item GLOW_PHANTOM_FRAME = registerDeferred("glow_phantom_frame", new PhantomGlowFrameItem(SpectrumEntityTypes.GLOW_PHANTOM_FRAME, IS.of()), DyeColor.YELLOW);
	
	// Specialty Magical Tools
	public static final KnowledgeGemItem KNOWLEDGE_GEM = registerDeferredWithUniqueModel("knowledge_gem", new KnowledgeGemItem(IS.of(1, Rarity.UNCOMMON), 10000), DyeColor.PURPLE);
	public static final Item CELESTIAL_POCKETWATCH = registerDeferred("celestial_pocketwatch", new CelestialPocketWatchItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.MAGENTA);
	public static final Item ARTISANS_ATLAS = registerDeferred("artisans_atlas", new ArtisansAtlasItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.YELLOW);
	public static final Item GILDED_BOOK = registerDeferred("gilded_book", new GildedBookItem(IS.of(Rarity.UNCOMMON)), DyeColor.PURPLE);
	public static final Item ENCHANTMENT_CANVAS = registerDeferredWithUniqueModel("enchantment_canvas", new EnchantmentCanvasItem(IS.of(16, Rarity.UNCOMMON)), DyeColor.PURPLE);
	public static final Item EVERPROMISE_RIBBON = registerDeferred("everpromise_ribbon", new EverpromiseRibbonItem(IS.of()), DyeColor.PINK);
	
	// Lore
	public static final Item MYSTERIOUS_LOCKET = registerDeferredWithUniqueModel("mysterious_locket", new MysteriousLocketItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.GRAY);
	public static final Item MYSTERIOUS_COMPASS = registerDeferredWithUniqueModel("mysterious_compass", new MysteriousCompassItem(IS.of(1, Rarity.RARE)), DyeColor.GRAY);
	
	// Trinkets
	public static final Item FANCIFUL_TUFF_RING = registerDeferred("fanciful_tuff_ring", new Item(IS.of(16, Rarity.UNCOMMON)), DyeColor.GREEN);
	public static final Item FANCIFUL_BELT = registerDeferred("fanciful_belt", new Item(IS.of(16, Rarity.UNCOMMON)), DyeColor.GREEN);
	public static final Item FANCIFUL_PENDANT = registerDeferred("fanciful_pendant", new Item(IS.of(16, Rarity.UNCOMMON)), DyeColor.GREEN);
	public static final Item FANCIFUL_CIRCLET = registerDeferred("fanciful_circlet", new Item(IS.of(16, Rarity.UNCOMMON)), DyeColor.GREEN);
	public static final Item FANCIFUL_GLOVES = registerDeferred("fanciful_gloves", new Item(IS.of(16, Rarity.UNCOMMON)), DyeColor.GREEN);
	public static final Item FANCIFUL_BISMUTH_RING = registerDeferred("fanciful_bismuth_ring", new Item(IS.of(16, Rarity.UNCOMMON)), DyeColor.GREEN);
	
	public static final Item GLOW_VISION_GOGGLES = registerDeferred("glow_vision_goggles", new GlowVisionGogglesItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.WHITE);
	public static final Item JEOPARDANT = registerDeferred("jeopardant", new AttackRingItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.RED);
	public static final SevenLeagueBootsItem SEVEN_LEAGUE_BOOTS = registerDeferred("seven_league_boots", new SevenLeagueBootsItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.PURPLE);
	public static final Item COTTON_CLOUD_BOOTS = registerDeferred("cotton_cloud_boots", new CottonCloudBootsItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.PURPLE);
	public static final Item RADIANCE_PIN = registerDeferred("radiance_pin", new RadiancePinItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.BLUE);
	public static final Item TOTEM_PENDANT = registerDeferred("totem_pendant", new TotemPendantItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.BLUE);
	public static final TakeOffBeltItem TAKE_OFF_BELT = registerDeferred("take_off_belt", new TakeOffBeltItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.YELLOW);
	public static final Item AZURE_DIKE_BELT = registerDeferred("azure_dike_belt", new AzureDikeBeltItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.BLUE);
	public static final Item AZURE_DIKE_RING = registerDeferred("azure_dike_ring", new AzureDikeRingItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.BLUE);
	public static final Item AZURESQUE_DIKE_CORE = registerDeferred("azuresque_dike_core", new AzureDikeCoreItem(IS.of(1, Rarity.EPIC)), DyeColor.WHITE);
	public static final InkDrainTrinketItem SHIELDGRASP_AMULET = registerDeferred("shieldgrasp_amulet", new AzureDikeAmuletItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.BLUE);
	public static final InkDrainTrinketItem HEARTSINGERS_REWARD = registerDeferred("heartsingers_reward", new ExtraHealthRingItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.PINK);
	public static final InkDrainTrinketItem GLOVES_OF_DAWNS_GRASP = registerDeferred("gloves_of_dawns_grasp", new ExtraReachGlovesItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.YELLOW);
	public static final InkDrainTrinketItem RING_OF_PURSUIT = registerDeferred("ring_of_pursuit", new ExtraMiningSpeedRingItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.MAGENTA);
	public static final InkDrainTrinketItem RING_OF_DENSER_STEPS = registerDeferred("ring_of_denser_steps", new RingOfDenserStepsItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.BROWN);
	public static final InkDrainTrinketItem RING_OF_AERIAL_GRACE = registerDeferred("ring_of_aerial_grace", new RingOfAerialGraceItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.WHITE);
	public static final InkDrainTrinketItem LAURELS_OF_SERENITY = registerDeferred("laurels_of_serenity", new LaurelsOfSerenityItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.PURPLE);
	
	// Ink storage
	public static final InkFlaskItem INK_FLASK = registerDeferredWithUniqueModel("ink_flask", new InkFlaskItem(IS.of(1), 64 * 64 * 100), DyeColor.WHITE); // 64 stacks of pigments (1 pigment => 100 energy)
	public static final InkAssortmentItem INK_ASSORTMENT = registerDeferred("ink_assortment", new InkAssortmentItem(IS.of(1), 64 * 100), DyeColor.WHITE);
	public static final PigmentPaletteItem PIGMENT_PALETTE = registerDeferred("pigment_palette", new PigmentPaletteItem(IS.of(1, Rarity.UNCOMMON), 64 * 64 * 100), DyeColor.WHITE);
	public static final ArtistsPaletteItem ARTISTS_PALETTE = registerDeferred("artists_palette", new ArtistsPaletteItem(IS.of(1, Rarity.UNCOMMON), 64 * 64 * 64 * 64 * 100), DyeColor.WHITE);
	public static final CreativeInkAssortmentItem CREATIVE_INK_ASSORTMENT = registerDeferredWithUniqueModel("creative_ink_assortment", new CreativeInkAssortmentItem(IS.of(1, Rarity.EPIC)), DyeColor.WHITE);
	
	public static final GleamingPinItem GLEAMING_PIN = registerDeferred("gleaming_pin", new GleamingPinItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.YELLOW);
	public static final Item LESSER_POTION_PENDANT = registerDeferredWithUniqueModel("lesser_potion_pendant", new PotionPendantItem(IS.of(1, Rarity.UNCOMMON), 1, SpectrumCommon.CONFIG.MaxLevelForEffectsInLesserPotionPendant - 1, SpectrumAdvancements.UNLOCK_LESSER_POTION_PENDANT), DyeColor.PINK);
	public static final Item GREATER_POTION_PENDANT = registerDeferredWithUniqueModel("greater_potion_pendant", new PotionPendantItem(IS.of(1, Rarity.UNCOMMON), 3, SpectrumCommon.CONFIG.MaxLevelForEffectsInGreaterPotionPendant - 1, SpectrumAdvancements.UNLOCK_GREATER_POTION_PENDANT), DyeColor.PINK);
	public static final Item ASHEN_CIRCLET = registerDeferredWithUniqueModel("ashen_circlet", new AshenCircletItem(IS.of(1, Rarity.UNCOMMON).fireproof()), DyeColor.ORANGE);
	public static final Item WEEPING_CIRCLET = registerDeferred("weeping_circlet", new WeepingCircletItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.LIGHT_BLUE);
	public static final Item PUFF_CIRCLET = registerDeferred("puff_circlet", new PuffCircletItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.WHITE);
	public static final Item WHISPY_CIRCLET = registerDeferred("whispy_circlet", new WhispyCircletItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.BROWN);
	public static final Item CIRCLET_OF_ARROGANCE = registerDeferred("circlet_of_arrogance", new CircletOfArroganceItem(IS.of(1, Rarity.UNCOMMON)), DyeColor.RED);
	public static final Item NEAT_RING = registerDeferred("neat_ring", new NeatRingItem(IS.of(1, Rarity.EPIC)), DyeColor.GREEN);
	
	public static final Item AETHER_GRACED_NECTAR_GLOVES = registerDeferred("aether_graced_nectar_gloves", new AetherGracedNectarGlovesItem(IS.of(1, Rarity.EPIC)), DyeColor.PURPLE);
	
	// Pure Clusters
	public static final Item PURE_COAL = registerDeferred("pure_coal", new Item(IS.of()), DyeColor.BROWN);
	public static final Item PURE_IRON = registerDeferred("pure_iron", new Item(IS.of()), DyeColor.BROWN);
	public static final Item PURE_GOLD = registerDeferred("pure_gold", new Item(IS.of()), DyeColor.BROWN);
	public static final Item PURE_DIAMOND = registerDeferred("pure_diamond", new Item(IS.of()), DyeColor.CYAN);
	public static final Item PURE_EMERALD = registerDeferred("pure_emerald", new Item(IS.of()), DyeColor.CYAN);
	public static final Item PURE_REDSTONE = registerDeferred("pure_redstone", new Item(IS.of()), DyeColor.RED);
	public static final Item PURE_LAPIS = registerDeferred("pure_lapis", new Item(IS.of()), DyeColor.PURPLE);
	public static final Item PURE_COPPER = registerDeferred("pure_copper", new Item(IS.of()), DyeColor.BROWN);
	public static final Item PURE_QUARTZ = registerDeferred("pure_quartz", new Item(IS.of()), DyeColor.BROWN);
	public static final Item PURE_GLOWSTONE = registerDeferred("pure_glowstone", new Item(IS.of()), DyeColor.YELLOW);
	public static final Item PURE_PRISMARINE = registerDeferred("pure_prismarine", new Item(IS.of()), DyeColor.CYAN);
	public static final Item PURE_NETHERITE_SCRAP = registerDeferred("pure_netherite_scrap", new Item(IS.of().fireproof()), DyeColor.BROWN);
	public static final Item PURE_ECHO = registerDeferred("pure_echo", new Item(IS.of()), DyeColor.BROWN);
	
	//Technical Items
	public static final Item CONNECTION_NODE_CRYSTAL = registerDeferredWithUniqueModel("connection_node_crystal", new Item(IS.of()), DyeColor.LIGHT_GRAY);
	public static final Item PROVIDER_NODE_CRYSTAL = registerDeferredWithUniqueModel("provider_node_crystal", new Item(IS.of()), DyeColor.MAGENTA);
	public static final Item SENDER_NODE_CRYSTAL = registerDeferredWithUniqueModel("sender_node_crystal", new Item(IS.of()), DyeColor.YELLOW);
	public static final Item STORAGE_NODE_CRYSTAL = registerDeferredWithUniqueModel("storage_node_crystal", new Item(IS.of()), DyeColor.CYAN);
	public static final Item BUFFER_NODE_CRYSTAL = registerDeferredWithUniqueModel("buffer_node_crystal", new Item(IS.of()), DyeColor.GREEN);
	public static final Item GATHER_NODE_CRYSTAL = registerDeferredWithUniqueModel("gather_node_crystal", new Item(IS.of()), DyeColor.BLACK);
	public static final Item EXTENDED_BUNDLE_ITEM = registerDeferredWithUniqueModel("extended_bundle", new Item(IS.of()), DyeColor.BROWN);
	
	public static <T extends Item> RegistryKey<Item> registerDeferredSupplier(String id, Supplier<T> supplier, DyeColor dyeColor) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, SpectrumCommon.locate(id));
		REGISTRAR.defer(() -> {
			T item = supplier.get();
			Registry.register(Registries.ITEM, key, item);
			ItemColors.ITEM_COLORS.registerColorMapping(item, dyeColor);
		});
		return key;
	}
	
	public static <T extends Item> T registerDeferred(String id, T item, DyeColor dyeColor) {
		REGISTRAR.defer(() -> {
			Registry.register(Registries.ITEM, SpectrumCommon.locate(id), item);
			ItemColors.ITEM_COLORS.registerColorMapping(item, dyeColor);
		});
		ITEM_MODEL_REGISTRAR.defer(ctx -> ctx.register(item, Models.GENERATED));
		return item;
	}
	
	public static <T extends Item> T registerDeferredWithUniqueModel(String id, T item, DyeColor dyeColor) {
		REGISTRAR.defer(() -> {
			Registry.register(Registries.ITEM, SpectrumCommon.locate(id), item);
			ItemColors.ITEM_COLORS.registerColorMapping(item, dyeColor);
		});
		return item;
	}
	
	public static void register() {
		REGISTRAR.flush();
		
		FluidStorage.combinedItemApiProvider(SpectrumItems.MERMAIDS_GEM).register(context ->
				new RemainderlessItemFluidStorage(context, FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET));
	}
	
	public static void provideItemModels(ItemModelGenerator generator) {
		ITEM_MODEL_REGISTRAR.flush(generator);
	}
	
	public static void registerFuelRegistry() {
		FuelRegistry.INSTANCE.add(SpectrumBlocks.WET_LAVA_SPONGE.asItem(), 12800);
		
		FuelRegistry.INSTANCE.add(SpectrumBlocks.LIGHT_LEVEL_DETECTOR.asItem(), 300);
		FuelRegistry.INSTANCE.add(SpectrumBlocks.WEATHER_DETECTOR.asItem(), 300);
		FuelRegistry.INSTANCE.add(SpectrumBlocks.ITEM_DETECTOR.asItem(), 300);
		FuelRegistry.INSTANCE.add(SpectrumBlocks.PLAYER_DETECTOR.asItem(), 300);
		FuelRegistry.INSTANCE.add(SpectrumBlocks.CREATURE_DETECTOR.asItem(), 300);
		
		FuelRegistry.INSTANCE.add(SpectrumItems.PURE_COAL, 3200);
		FuelRegistry.INSTANCE.add(SpectrumBlocks.PURE_COAL_BLOCK, 32000);
		
		FuelRegistry.INSTANCE.add(SpectrumItems.VEGETAL, 800);
		FuelRegistry.INSTANCE.add(SpectrumBlocks.VEGETAL_BLOCK, 8000);
		
		FuelRegistry.INSTANCE.add(SpectrumItems.PURE_ALCOHOL, 16000);
		FuelRegistry.INSTANCE.add(SpectrumItems.CHRYSOCOLLA, 16000);
		
		FuelRegistry.INSTANCE.add(SpectrumItems.INCANDESCENT_ESSENCE, 2400);
		
		FuelRegistry.INSTANCE.add(SpectrumItemTags.COLORED_FENCES, 300);
		FuelRegistry.INSTANCE.add(SpectrumItemTags.COLORED_FENCE_GATES, 300);
	}
	
	public static class IS {
		
		public static final Item.Settings DEFAULT = new Item.Settings();
		
		public static Item.Settings of() {
			return new Item.Settings();
		}
		
		public static Item.Settings of(int maxCount) {
			return new Item.Settings().maxCount(maxCount);
		}
		
		public static Item.Settings of(Rarity rarity) {
			return new Item.Settings().rarity(rarity);
		}
		
		public static Item.Settings of(int maxCount, Rarity rarity) {
			return new Item.Settings().maxCount(maxCount).rarity(rarity);
		}
		
	}
	
	public static PairedFoodComponent teaSconeBonus(FoodComponent foodComponent) {
		return new PairedFoodComponent(SpectrumItems.SCONE, true, foodComponent);
	}
	
}
