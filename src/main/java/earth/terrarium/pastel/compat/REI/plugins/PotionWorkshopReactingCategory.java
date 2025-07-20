package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.registries.PastelBlocks;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PotionWorkshopReactingCategory extends GatedItemInformationPageCategory {

    public static final EntryStack<ItemStack> POTION_WORKSHOP_ENTRY = EntryStacks.of(
        PastelBlocks.POTION_WORKSHOP.get());

    @Override
    public Renderer getIcon() {
        return POTION_WORKSHOP_ENTRY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.pastel.rei.potion_workshop_reacting.title");
    }

    @Override
    public CategoryIdentifier<PotionWorkshopReactingDisplay> getCategoryIdentifier() {
        return PastelPlugins.POTION_WORKSHOP_REACTING;
    }

    @Override
    public EntryStack<?> getBackgroundEntryStack() {
        return POTION_WORKSHOP_ENTRY;
    }

}
