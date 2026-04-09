package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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
        tag(PastelBlockTags.WARD_DISRUPTABLE).add(
            PastelBlocks.AZURITE_ORE.get(), PastelBlocks.DEEPSLATE_AZURITE_ORE.get()
        );
        tag(PastelBlockTags.FLOWING_STAFF_MOVE_BLACKLIST).addTag(PastelBlockTags.UNBREAKABLE);
        tag(PastelBlockTags.CRACKED_BLOCKS).add(
            Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_TILES, Blocks.CRACKED_NETHER_BRICKS,
            Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS
        );
        tag(PastelBlockTags.REALLY_FALLING_BLOCK_BLACKLISTED).addOptionalTag(
            ResourceLocation.fromNamespaceAndPath("c", "relocation_not_supported"));
        tag(PastelBlockTags.FALLING_BLOCK_BLACKLISTED).addTag(PastelBlockTags.REALLY_FALLING_BLOCK_BLACKLISTED)
                                                      .add(
                                                          PastelBlocks.BUDDING_CITRINE.get(),
                                                          PastelBlocks.BUDDING_MOONSTONE.get(),
                                                          PastelBlocks.BUDDING_ONYX.get(),
                                                          PastelBlocks.BUDDING_TOPAZ.get(),
                                                          Blocks.SPAWNER, Blocks.TRIAL_SPAWNER
                                                      );
    }

}
