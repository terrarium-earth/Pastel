package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.conditional.colored_tree.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ProvisionalBlockAssetProvider extends BlockStateProvider {

    public ProvisionalBlockAssetProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PastelCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (InkColor color : InkColors.all()) {
            var plankTex = blt(color, "planks");
            var log = blt(color, "log");
            var strippedLog = blt(color, "stripped_log");

            texAll(plankTex, ColoredPlankBlock.byColor(color));
            texAll(log, ColoredWoodBlock.byColor(color));
            texAll(strippedLog, ColoredStrippedWoodBlock.byColor(color));
            texAll(blt(color, "leaves"), ColoredLeavesBlock.byColor(color));

            axisBlock(ColoredLogBlock.byColor(color), log, blt(color, "log_top"));
            axisBlock(ColoredStrippedLogBlock.byColor(color), strippedLog, blt(color, "stripped_log_top"));

            stairsBlock(ColoredStairsBlock.byColor(color), plankTex);
            slabBlock(ColoredSlabBlock.byColor(color), modLoc(name(ColoredPlankBlock.byColor(color))), plankTex);

            var button = ColoredWoodenButtonBlock.byColor(color);
            buttonBlock(button, plankTex);
            itemModels().buttonInventory(name(button), plankTex);
            
            pressurePlateBlock(ColoredPressurePlateBlock.byColor(color), plankTex);

            var fence = ColoredFenceBlock.byColor(color);
            fenceBlock(fence, plankTex);
            itemModels().fenceInventory(name(fence), plankTex);
            var gate = ColoredFenceGateBlock.byColor(color);
            fenceGateBlock(gate, plankTex);
            itemModels().withExistingParent(name(gate), modLoc("block/" + name(gate)));
        }
    }

    private void texAll(ResourceLocation texture, Block block) {
        simpleBlock(block, models().cubeAll(name(block), texture));
    }

    private ResourceLocation blt(InkColor color, String type) {
        return modLoc( "block/" + type + "/" + color.getLootName());
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }
}
