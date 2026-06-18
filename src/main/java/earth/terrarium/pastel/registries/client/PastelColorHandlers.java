package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.blocks.decoration.CushionBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class PastelColorHandlers {

    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event
            .register(
                (state, level, pos, tintIndex) -> {
                    if (tintIndex == 0 && state.getBlock() instanceof CushionBlock cushionBlock && cushionBlock
                        .getColor() != null) {
                        return cushionBlock
                            .getColor()
                            .getTextureDiffuseColor();
                    }
                    return 0xFFFFFFFF;
                },
                PastelBlocks.WHITE_CUSHION.value(),
                PastelBlocks.CYAN_CUSHION.value(),
                PastelBlocks.BLACK_CUSHION.value(),
                PastelBlocks.YELLOW_CUSHION.value(),
                PastelBlocks.RED_CUSHION.value(),
                PastelBlocks.PURPLE_CUSHION.value(),
                PastelBlocks.PINK_CUSHION.value(),
                PastelBlocks.ORANGE_CUSHION.value(),
                PastelBlocks.MAGENTA_CUSHION.value(),
                PastelBlocks.LIME_CUSHION.value(),
                PastelBlocks.LIGHT_GRAY_CUSHION.value(),
                PastelBlocks.LIGHT_BLUE_CUSHION.value(),
                PastelBlocks.GREEN_CUSHION.value(),
                PastelBlocks.GRAY_CUSHION.value(),
                PastelBlocks.BROWN_CUSHION.value(),
                PastelBlocks.BLUE_CUSHION.value()
            );
    }

    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event
            .register(
                (stack, tintIndex) -> {
                    if (tintIndex == 0 && stack.getItem() instanceof BlockItem blockItem && blockItem
                        .getBlock() instanceof CushionBlock cushionBlock && cushionBlock.getColor() != null)
                        return cushionBlock
                            .getColor()
                            .getTextureDiffuseColor();
                    return 0xFFFFFFFF;
                },
                PastelBlocks.WHITE_CUSHION.asItem(),
                PastelBlocks.CYAN_CUSHION.asItem(),
                PastelBlocks.BLACK_CUSHION.asItem(),
                PastelBlocks.YELLOW_CUSHION.asItem(),
                PastelBlocks.RED_CUSHION.asItem(),
                PastelBlocks.PURPLE_CUSHION.asItem(),
                PastelBlocks.PINK_CUSHION.asItem(),
                PastelBlocks.ORANGE_CUSHION.asItem(),
                PastelBlocks.MAGENTA_CUSHION.asItem(),
                PastelBlocks.LIME_CUSHION.asItem(),
                PastelBlocks.LIGHT_GRAY_CUSHION.asItem(),
                PastelBlocks.LIGHT_BLUE_CUSHION.asItem(),
                PastelBlocks.GREEN_CUSHION.asItem(),
                PastelBlocks.GRAY_CUSHION.asItem(),
                PastelBlocks.BROWN_CUSHION.asItem(),
                PastelBlocks.BLUE_CUSHION.asItem()
            );
    }
}
