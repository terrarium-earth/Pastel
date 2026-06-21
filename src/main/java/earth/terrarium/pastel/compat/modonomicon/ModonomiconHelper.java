package earth.terrarium.pastel.compat.modonomicon;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.MultiblockPreviewRenderer;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModonomiconHelper {

    public static void renderIngredientStack(
        GuiGraphics drawContext,
        BookEntryScreen parentScreen,
        int x,
        int y,
        int mouseX,
        int mouseY,
        IngredientStack ingredientStack
    ) {
        List<ItemStack> stacks = ingredientStack
            .getItems()
            .toList();
        if (!stacks.isEmpty()) {
            parentScreen
                .renderItemStack(
                    drawContext,
                    x,
                    y,
                    mouseX,
                    mouseY,
                    stacks.get(parentScreen.getTicksInBook() / 20 % stacks.size())
                );
        }
    }

    // probably a helper function _somewhere_ out there, but im not finding it LOL
    public static void renderIngredient(
            GuiGraphics drawContext,
            BookEntryScreen parentScreen,
            int x,
            int y,
            int mouseX,
            int mouseY,
            Ingredient ingredient
    ) {
        var stacks = ingredient.getItems();
        if (stacks.length > 0) {
            parentScreen
                    .renderItemStack(
                            drawContext,
                            x,
                            y,
                            mouseX,
                            mouseY,
                            stacks[parentScreen.getTicksInBook() / 20 % stacks.length]
                    );
        }
    }

    public static void renderMultiblock(Multiblock multiblock, Component text, BlockPos pos, Rotation rotation) {
        MultiblockPreviewRenderer.setMultiblock(multiblock, text, false);
        MultiblockPreviewRenderer.anchorTo(pos, rotation);
    }

    /**
     * Clears multiblock if the currently rendered one matches the one in the argument
     * If null is passed, any multiblock will get cleared
     */
    public static void clearRenderedMultiblock(@Nullable Multiblock multiblock) {
        Multiblock currentlyRenderedMultiblock = MultiblockPreviewRenderer.getMultiblock();
        if (currentlyRenderedMultiblock == null || currentlyRenderedMultiblock != multiblock) {
            return;
        }
        MultiblockPreviewRenderer.setMultiblock(null, Component.empty(), false);
    }

}
