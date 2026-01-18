package earth.terrarium.pastel.data.models.item.tools;

import com.cmdpro.databank.misc.ColorGradient;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.items.armor.BedrockArmorItem;
import earth.terrarium.pastel.items.armor.GemstoneArmorItem;
import earth.terrarium.pastel.items.magic_items.ampoules.AzuriteGlassAmpouleItem;
import earth.terrarium.pastel.items.magic_items.ampoules.BloodstoneGlassAmpouleItem;
import earth.terrarium.pastel.items.magic_items.ampoules.MalachiteGlassAmpouleItem;
import earth.terrarium.pastel.items.tools.*;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelArmorMaterials;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelToolMaterial;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.registries.DeferredItem;

import java.awt.*;
import java.util.Map;

public class WeaponArmorItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {

        PastelModelHelper.ITEM.handheld(generators, PastelItems.RAZOR_FALCHION);

        PastelModelHelper.ITEM.simple(generators, PastelItems.MALACHITE_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.TOPAZ_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AMETHYST_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CITRINE_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ONYX_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MOONSTONE_GLASS_ARROW);

        PastelModelHelper.ITEM.simple(generators, PastelItems.AZURITE_GLASS_AMPOULE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BLOODSTONE_GLASS_AMPOULE);
        PastelModelHelper.ITEM.layered(generators, PastelItems.MALACHITE_GLASS_AMPOULE, "_base", "_overlay");

        PastelModelHelper.registerLayeredItemModel(
            generators, PastelItems.NIGHTFALLS_BLADE.get(), PastelModels.HANDHELD_THREE_LAYERS, "", "_tint",
            "_overlay"
        );

        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_HELMET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_CHESTPLATE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_LEGGINGS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_BOOTS);

        PastelModelHelper.ITEM.simple(generators, PastelItems.FETCHLING_HELMET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FEROCIOUS_CHESTPLATE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SYLPH_LEGGINGS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.OREAD_BOOTS);
    }
}
