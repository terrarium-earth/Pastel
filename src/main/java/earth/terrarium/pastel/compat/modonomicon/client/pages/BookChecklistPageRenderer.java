package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.RenderedBookTextHolder;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookTextPageRenderer;
import com.klikli_dev.modonomicon.data.BookDataManager;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.compat.modonomicon.pages.BookChecklistPage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public class BookChecklistPageRenderer extends BookTextPageRenderer {

    public BookChecklistPageRenderer(BookChecklistPage page) {
        super(page);
    }

    @Override
    public void onBeginDisplayPage(BookEntryScreen parentScreen, int left, int top) {
        if (!(page instanceof BookChecklistPage checklistPage)) return;
        if (!(page.getText() instanceof RenderedBookTextHolder renderedText)) return;

        super.onBeginDisplayPage(parentScreen, left, top);

        List<MutableComponent> renderedTexts = renderedText.getRenderedText();
        
        ResourceLocation font = BookDataManager.Client.get().safeFont(this.page.getBook().getFont());
        
        int i = 0;
        for (Map.Entry<ResourceLocation, BookTextHolder> entry : checklistPage.getChecklist().entrySet()) {
            boolean hasAchievement = AdvancementHelper.hasAdvancementClient(entry.getKey());
            renderedTexts.get(i).withStyle(Style.EMPTY.withStrikethrough(hasAchievement).withFont(font));
            List<Component> siblings = renderedTexts.get(i).getSiblings();
            siblings.removeLast();
            siblings.add(Component.literal(hasAchievement ? " ✔" : ""));
            i++;
        }
    }

}
