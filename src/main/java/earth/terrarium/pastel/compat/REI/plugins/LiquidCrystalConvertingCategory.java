package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.registries.PastelItems;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;

@OnlyIn(Dist.CLIENT)
public class LiquidCrystalConvertingCategory extends FluidConvertingCategory<LiquidCrystalConvertingDisplay> {

    @Override
    public CategoryIdentifier<? extends LiquidCrystalConvertingDisplay> getCategoryIdentifier() {
        return PastelPlugins.LIQUID_CRYSTAL_CONVERTING;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PastelItems.LIQUID_CRYSTAL_BUCKET.get());
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.pastel.rei.liquid_crystal_converting.title");
    }

}
