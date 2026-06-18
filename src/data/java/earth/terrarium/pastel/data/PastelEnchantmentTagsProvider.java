package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PastelEnchantmentTagsProvider extends EnchantmentTagsProvider {

    public PastelEnchantmentTagsProvider(
        PackOutput packOutput,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(packOutput, lookupProvider, PastelCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        var pastelEnchantmentTag = tag(PastelEnchantmentTags.PASTEL_ENCHANTMENT);
        for (
            var key : PastelEnchantments.PASTEL_ENCHANTMENTS
        ) pastelEnchantmentTag.add(key);

        registerExclusiveSetTags();
    }

    private void registerExclusiveSetTags() {
        tag(PastelEnchantmentTags.ExclusiveSet.CLOVERS_FAVOR)
            .add(Enchantments.LOOTING)
            .addOptional(ResourceLocation.parse("malum:spirit_plunder"));
        tag(PastelEnchantmentTags.ExclusiveSet.FOUNDRY)
            .add(Enchantments.SILK_TOUCH)
            .addOptional(ResourceLocation.parse("gofish:deepfry"));
        tag(PastelEnchantmentTags.ExclusiveSet.IMPROVED_CRITICAL)
            .add(Enchantments.SHARPNESS)
            .addOptional(ResourceLocation.parse("malum:haunted"));
        tag(PastelEnchantmentTags.ExclusiveSet.INDESTRUCTIBLE)
            .add(Enchantments.INFINITY)
            .add(Enchantments.UNBREAKING)
            .add(Enchantments.EFFICIENCY)
            .add(Enchantments.MENDING)
            .add(Enchantments.PROTECTION)
            .add(Enchantments.BINDING_CURSE);
        tag(PastelEnchantmentTags.ExclusiveSet.INERTIA)
            .add(Enchantments.EFFICIENCY);
        tag(PastelEnchantmentTags.ExclusiveSet.PEST_CONTROL)
            .add(PastelEnchantments.RESONANCE);
        tag(PastelEnchantmentTags.ExclusiveSet.RAZING)
            .add(Enchantments.FORTUNE);
        tag(PastelEnchantmentTags.ExclusiveSet.RESONANCE)
            .addTag(EnchantmentTags.MINING_EXCLUSIVE)
            .add(PastelEnchantments.PEST_CONTROL);
        tag(PastelEnchantmentTags.ExclusiveSet.TIGHT_GRIP)
            .addOptional(ResourceLocation.parse("malum:rebound"));
        tag(PastelEnchantmentTags.ExclusiveSet.TREASURE_HUNTER)
            .add(Enchantments.LOOTING)
            .addOptional(ResourceLocation.parse("malum:spirit_plunder"));
    }

}
