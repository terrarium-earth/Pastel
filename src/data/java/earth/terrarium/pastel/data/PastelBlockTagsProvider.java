package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PastelBlockTagsProvider extends BlockTagsProvider {

    public PastelBlockTagsProvider(
        PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
        @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, PastelCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(PastelBlockTags.C_LIGHTNING_RODS).add(Blocks.LIGHTNING_ROD.builtInRegistryHolder()
                                                                      .key())
                                             .addOptionalTag(ResourceLocation.parse("friendsandfoes:lightning_rods"));

        tag(PastelBlockTags.C_BRUSHABLE_BLOCKS).add(Blocks.SUSPICIOUS_SAND.builtInRegistryHolder()
                                                                          .key())
                                               .add(Blocks.SUSPICIOUS_GRAVEL.builtInRegistryHolder()
                                                                            .key())
                                               .addOptional(
                                                   ResourceLocation.parse("the_bumblezone:pile_of_pollen_suspicious"));
        tag(PastelBlockTags.C_INFESTED_BLOCKS).add(Blocks.INFESTED_COBBLESTONE.builtInRegistryHolder()
                                                                              .key())
                                              .add(Blocks.INFESTED_CHISELED_STONE_BRICKS.builtInRegistryHolder()
                                                                                        .key())
                                              .add(Blocks.INFESTED_CRACKED_STONE_BRICKS.builtInRegistryHolder()
                                                                                       .key())
                                              .add(Blocks.INFESTED_DEEPSLATE.builtInRegistryHolder()
                                                                            .key())
                                              .add(Blocks.INFESTED_STONE.builtInRegistryHolder()
                                                                        .key())
                                              .add(Blocks.INFESTED_MOSSY_STONE_BRICKS.builtInRegistryHolder()
                                                                                     .key())
                                              .add(Blocks.INFESTED_STONE_BRICKS.builtInRegistryHolder()
                                                                               .key())
                                              .add(PastelBlocks.INFESTED_BLACKSLAG.get()
                                                                                  .builtInRegistryHolder()
                                                                                  .key());

    }

}
