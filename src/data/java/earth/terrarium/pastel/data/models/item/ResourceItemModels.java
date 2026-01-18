package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.gravity.FloatItem;
import earth.terrarium.pastel.blocks.jade_vines.GerminatedJadeVineBulbItem;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.items.ItemWithLoomPattern;
import earth.terrarium.pastel.items.ItemWithTooltip;
import earth.terrarium.pastel.items.MidnightAberrationItem;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.items.conditional.GemstonePowderItem;
import earth.terrarium.pastel.items.conditional.StormStoneItem;
import earth.terrarium.pastel.items.misc.AetherVestigesItem;
import earth.terrarium.pastel.items.misc.AshItem;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredItem;

import static net.minecraft.world.item.Items.*;
import static net.minecraft.world.item.Items.BLACK_DYE;
import static net.minecraft.world.item.Items.BLUE_DYE;
import static net.minecraft.world.item.Items.BROWN_DYE;
import static net.minecraft.world.item.Items.CYAN_DYE;
import static net.minecraft.world.item.Items.GRAY_DYE;
import static net.minecraft.world.item.Items.GREEN_DYE;
import static net.minecraft.world.item.Items.LIGHT_BLUE_DYE;
import static net.minecraft.world.item.Items.LIGHT_GRAY_DYE;
import static net.minecraft.world.item.Items.LIME_DYE;
import static net.minecraft.world.item.Items.PINK_DYE;
import static net.minecraft.world.item.Items.PURPLE_DYE;
import static net.minecraft.world.item.Items.RED_DYE;
import static net.minecraft.world.item.Items.YELLOW_DYE;

public class ResourceItemModels {
    public static void generateItemModels(ItemModelGenerators generators){
        PastelModelHelper.ITEM.simple(generators, PastelItems.TOPAZ_SHARD);
        PastelModelHelper.ITEM.simple(generators,PastelItems.CITRINE_SHARD);
        PastelModelHelper.ITEM.simple(generators,PastelItems.ONYX_SHARD);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MOONSTONE_SHARD);
        PastelModelHelper.ITEM.simple(generators,PastelItems.SPECTRAL_SHARD);

        PastelModelHelper.ITEM.simple(generators,PastelItems.TOPAZ_POWDER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.AMETHYST_POWDER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.CITRINE_POWDER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.ONYX_POWDER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MOONSTONE_POWDER);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.WHITE_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.ORANGE_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MAGENTA_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.LIGHT_BLUE_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.YELLOW_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.LIME_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PINK_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.GRAY_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.LIGHT_GRAY_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.CYAN_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURPLE_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BLUE_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BROWN_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.GREEN_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.RED_PIGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BLACK_PIGMENT);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.VEGETAL);
        PastelModelHelper.ITEM.simple(generators,PastelItems.NEOLITH);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BEDROCK_DUST);

        PastelModelHelper.ITEM.simple(generators,PastelItems.MIDNIGHT_ABERRATION);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MIDNIGHT_CHIP);

        PastelModelHelper.ITEM.simple(generators,PastelItems.BISMUTH_FLAKE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BISMUTH_CRYSTAL);
        PastelModelHelper.ITEM.simple(generators,PastelItems.RAW_MALACHITE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_MALACHITE);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.LIQUID_CRYSTAL_BUCKET);
        PastelModelHelper.ITEM.simple(generators,PastelItems.HUMUS_BUCKET);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MIDNIGHT_SOLUTION_BUCKET);
        PastelModelHelper.ITEM.simple(generators,PastelItems.DRAGONROT_BUCKET);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.SHIMMERSTONE_GEM);
        PastelModelHelper.ITEM.simple(generators,PastelItems.RAW_AZURITE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_AZURITE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PALTAERIA_FRAGMENTS);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PALTAERIA_GEM);
        PastelModelHelper.ITEM.simple(generators,PastelItems.STRATINE_FRAGMENTS);
        PastelModelHelper.ITEM.simple(generators,PastelItems.STRATINE_GEM);

        PastelModelHelper.ITEM.simple(generators,PastelItems.DRAGONBONE_CHUNK);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BONE_ASH);
        PastelModelHelper.ITEM.simple(generators,PastelItems.RESPLENDENT_FEATHER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.RAW_BLOODSTONE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_BLOODSTONE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.DOWNSTONE_FRAGMENTS);
        PastelModelHelper.ITEM.simple(generators,PastelItems.RESONANCE_SHARD);
        PastelModelHelper.ITEM.simple(generators,PastelItems.AETHER_VESTIGES);

        PastelModelHelper.ITEM.simple(generators,PastelItems.QUITOXIC_POWDER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.STORM_STONE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MERMAIDS_GEM);
        PastelModelHelper.ITEM.simple(generators,PastelItems.STAR_FRAGMENT);
        PastelModelHelper.ITEM.simple(generators,PastelItems.STARDUST);

        PastelModelHelper.ITEM.simple(generators,PastelItems.HIBERNATING_JADE_VINE_BULB);
        PastelModelHelper.ITEM.simple(generators,PastelItems.GERMINATED_JADE_VINE_BULB);
        PastelModelHelper.ITEM.simple(generators,PastelItems.JADE_PETALS);
        PastelModelHelper.ITEM.simple(generators,PastelItems.JADEITE_PETALS);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.BLOOD_ORCHID_PETAL);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.DOOMBLOOM_SEED);

        PastelModelHelper.ITEM.simple(generators,PastelItems.GLISTERING_MELON_SEEDS);
        PastelModelHelper.ITEM.simple(generators,PastelItems.AMARANTH_GRAINS);

        PastelModelHelper.ITEM.simple(generators,PastelItems.INCANDESCENT_ESSENCE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.FROSTBITE_ESSENCE);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.MOONSTONE_CORE);
        
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_COAL);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_IRON);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_GOLD);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_DIAMOND);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_EMERALD);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_REDSTONE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_LAPIS);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_COPPER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_QUARTZ);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_GLOWSTONE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_PRISMARINE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_NETHERITE_SCRAP);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PURE_ECHO);
    }
}
