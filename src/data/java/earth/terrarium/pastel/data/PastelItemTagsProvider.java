package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PastelItemTagsProvider extends ItemTagsProvider {

    public PastelItemTagsProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagLookup<Block>> blockTags,
        @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, blockTags, PastelCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        generateEnchantableTags();

        tag(PastelItemTags.COOKBOOKS)
            .add(
                PastelItems.BREWERS_HANDBOOK.get(),
                PastelItems.IMBRIFER_COOKBOOK.get(),
                PastelItems.IMPERIAL_COOKBOOK.get(),
                PastelItems.MELOCHITES_COOKBOOK_VOL_1.get(),
                PastelItems.MELOCHITES_COOKBOOK_VOL_2.get(),
                PastelItems.POISONERS_HANDBOOK.get()
            );

        tag(ItemTags.BOOKSHELF_BOOKS)
            .addTag(PastelItemTags.COOKBOOKS)
            .add(PastelItems.GILDED_BOOK.get(), PastelItems.GUIDEBOOK.get());
        tag(PastelItemTags.METAL_ARMOR)
            .add(
                Items.IRON_HELMET,
                Items.IRON_CHESTPLATE,
                Items.IRON_LEGGINGS,
                Items.IRON_BOOTS,
                Items.CHAINMAIL_HELMET,
                Items.CHAINMAIL_CHESTPLATE,
                Items.CHAINMAIL_LEGGINGS,
                Items.CHAINMAIL_BOOTS,
                Items.GOLDEN_HELMET,
                Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS,
                Items.GOLDEN_BOOTS,
                Items.NETHERITE_HELMET,
                Items.NETHERITE_CHESTPLATE,
                Items.NETHERITE_LEGGINGS,
                Items.NETHERITE_BOOTS,
                Items.IRON_HORSE_ARMOR,
                Items.GOLDEN_HORSE_ARMOR
            );

        // the enchant switching on these would make this an absolute pain to deal with so we just don't :)
        tag(PastelItemTags.CRYSTAL_EMPOWER_BLACKLIST);
    }

    public void generateEnchantableTags() {
        tag(PastelItemTags.EnchantableWith.BIG_CATCH).addTag(ItemTags.FISHING_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.CLOVERS_FAVOR)
            .addTag(ItemTags.SWORD_ENCHANTABLE)
            .addOptionalTag(ResourceLocation.parse("malum:scythe"));
        tag(PastelItemTags.EnchantableWith.DISARMING).addTag(ItemTags.WEAPON_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.EXUBERANCE)
            .addTag(ItemTags.WEAPON_ENCHANTABLE)
            .addTag(ItemTags.MINING_LOOT_ENCHANTABLE)
            .addTag(PastelItemTags.FISHING_RODS)
            .addOptionalTag(ResourceLocation.parse("malum:scythe"));
        tag(PastelItemTags.EnchantableWith.FIRST_STRIKE)
            .addTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(ResourceLocation.parse("malum:scythe"));
        tag(PastelItemTags.EnchantableWith.FOUNDRY)
            .addTag(ItemTags.MINING_LOOT_ENCHANTABLE)
            .addTag(PastelItemTags.FISHING_RODS);
        tag(PastelItemTags.EnchantableWith.IMPROVED_CRITICAL)
            .addTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(ResourceLocation.parse("malum:scythe"));
        tag(PastelItemTags.EnchantableWith.INDESTRUCTIBLE).addTag(ItemTags.DURABILITY_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.INERTIA).addTag(ItemTags.MINING_LOOT_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.INEXORABLE)
            .addTag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
            .addTag(ItemTags.MINING_LOOT_ENCHANTABLE)
            .addTag(ItemTags.TRIDENT_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.INVENTORY_INSERTION)
            .addTag(ItemTags.MINING_ENCHANTABLE)
            .addTag(ItemTags.WEAPON_ENCHANTABLE)
            .addTag(ItemTags.BOW_ENCHANTABLE)
            .addTag(ItemTags.CROSSBOW_ENCHANTABLE)
            .addTag(PastelItemTags.FISHING_RODS);
        tag(PastelItemTags.EnchantableWith.PEST_CONTROL).addTag(ItemTags.MINING_LOOT_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.RAZING).addTag(ItemTags.MINING_LOOT_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.RESONANCE)
            .addTag(ItemTags.MINING_ENCHANTABLE)
            .add(PastelItems.ENDER_SPLICE.getKey())
            .add(PastelItems.ENDER_CANVAS.getKey())
            .add(PastelItems.EXCHANGING_STAFF.getKey());
        tag(PastelItemTags.EnchantableWith.SERENDIPITY_REEL).addTag(ItemTags.FISHING_ENCHANTABLE);
        tag(PastelItemTags.EnchantableWith.STEADFAST)
            .addTag(ItemTags.DURABILITY_ENCHANTABLE)
            .addTag(ItemTags.MINING_ENCHANTABLE)
            .addTag(ItemTags.VANISHING_ENCHANTABLE)
            .addOptionalTag(
                ResourceLocation.parse("trinkets:enchantable/enchantable")
            );
        tag(PastelItemTags.EnchantableWith.TIGHT_GRIP)
            .addTag(ItemTags.SWORD_ENCHANTABLE)
            .addOptionalTag(ResourceLocation.parse("malum:scythe"));
        tag(PastelItemTags.EnchantableWith.TREASURE_HUNTER)
            .addTag(ItemTags.WEAPON_ENCHANTABLE)
            .addOptionalTag(ResourceLocation.parse("malum:scythe"));
        tag(PastelItemTags.EnchantableWith.VOIDING)
            .addTag(ItemTags.MINING_LOOT_ENCHANTABLE)
            .add(
                PastelBlocks.BOTTOMLESS_BUNDLE
                    .get()
                    .asItem()
                    .builtInRegistryHolder()
                    .key()
            );

    }

}
