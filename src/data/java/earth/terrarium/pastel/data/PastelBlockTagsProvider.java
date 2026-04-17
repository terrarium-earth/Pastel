package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
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
        tag(PastelBlockTags.C_LIGHTNING_RODS).add(Blocks.LIGHTNING_ROD)
                                             .addOptionalTag(ResourceLocation.parse("friendsandfoes:lightning_rods"));

        tag(PastelBlockTags.C_BRUSHABLE_BLOCKS).add(Blocks.SUSPICIOUS_SAND)
                                               .add(Blocks.SUSPICIOUS_GRAVEL)
                                               .addOptional(
                                                   ResourceLocation.parse("the_bumblezone:pile_of_pollen_suspicious"));
        tag(PastelBlockTags.C_INFESTED_BLOCKS).add(Blocks.INFESTED_COBBLESTONE)
                                              .add(Blocks.INFESTED_CHISELED_STONE_BRICKS)
                                              .add(Blocks.INFESTED_CRACKED_STONE_BRICKS)
                                              .add(Blocks.INFESTED_DEEPSLATE)
                                              .add(Blocks.INFESTED_STONE)
                                              .add(Blocks.INFESTED_MOSSY_STONE_BRICKS)
                                              .add(Blocks.INFESTED_STONE_BRICKS)
                                              .add(PastelBlocks.INFESTED_BLACKSLAG.get());
        tag(PastelBlockTags.FLOWING_STAFF_MOVE_BLACKLIST).addTag(PastelBlockTags.UNBREAKABLE)
                                                         .addTag(BlockTags.BEDS);

        tag(PastelBlockTags.WARD_DISRUPTABLE).add(PastelBlocks.AZURE_CRYSTAL.get());
        tag(PastelBlockTags.CRYSTAL_SPIKE_BASES).addTag(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
                                                .add(PastelBlocks.FROSTED_DEEPSLATE.get());
        tag(PastelBlockTags.FLOWING_STAFF_MOVE_BLACKLIST).addTag(PastelBlockTags.UNBREAKABLE);
        tag(PastelBlockTags.VIRIDIAN_CRYSTAL_PURITY_SOURCES).add(
            PastelBlocks.MOONSTONE_BLOCK.get(), PastelBlocks.POLISHED_MOONSTONE_BLOCK.get(),
            PastelBlocks.MOONSTONE_POWDER_BLOCK.get(), PastelBlocks.MOONSTONE_CLUSTER.get(),
            PastelBlocks.BUDDING_MOONSTONE.get(), PastelBlocks.MOONSTONE_ORE.get(),
            PastelBlocks.DEEPSLATE_MOONSTONE_ORE.get(), PastelBlocks.BLACKSLAG_MOONSTONE_ORE.get(),
            PastelBlocks.SMALL_MOONSTONE_BUD.get(), PastelBlocks.MEDIUM_MOONSTONE_BUD.get(),
            PastelBlocks.LARGE_MOONSTONE_BUD.get()
        );
    }

}
