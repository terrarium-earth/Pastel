package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlocks;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(
    Dist.CLIENT
)
public abstract class EnchanterCategory<T extends EnchanterDisplay> extends GatedDisplayCategory<T> {

    public final static ResourceLocation BACKGROUND_TEXTURE = PastelCommon
        .locate(
            "textures/gui/container/enchanter.png"
        );

    public static final EntryIngredient ENCHANTER = EntryIngredients.of(PastelBlocks.ENCHANTER.get());

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PastelBlocks.ENCHANTER.get());
    }

    public abstract int getCraftingTime(@NotNull T display);

    public abstract Component getDescriptionText(@NotNull T display);

    @Override
    public int getDisplayHeight() {
        return 92;
    }

}
