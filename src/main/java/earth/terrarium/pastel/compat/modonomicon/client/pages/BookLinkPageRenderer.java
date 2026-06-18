package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookTextPageRenderer;
import earth.terrarium.pastel.compat.modonomicon.pages.BookLinkPage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;

public class BookLinkPageRenderer extends BookTextPageRenderer {

    private static final int BUTTON_HEIGHT = Button.DEFAULT_HEIGHT;

    private static final int BUTTON_WIDTH = BookEntryScreen.PAGE_WIDTH - 12;

    private static final int BUTTON_X = 2;

    private static final int BUTTON_Y = BookEntryScreen.PAGE_HEIGHT - BUTTON_HEIGHT - 3;

    public BookLinkPageRenderer(BookLinkPage page) {
        super(page);
    }

    @Override
    public void onBeginDisplayPage(BookEntryScreen parentScreen, int left, int top) {
        if (!(page instanceof BookLinkPage linkPage)) return;

        super.onBeginDisplayPage(parentScreen, left, top);

        addButton(
            Button
                .builder(
                    linkPage
                        .getLinkText()
                        .getComponent(),
                    (b) -> {
                    }
                )
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .pos(BUTTON_X, BUTTON_Y)
                .build()
        );
    }

    @Nullable @Override
    public Style getClickedComponentStyleAt(double pMouseX, double pMouseY) {
        if (!(page instanceof BookLinkPage linkPage)) return null;

        if (pMouseX >= BUTTON_X && pMouseY >= BUTTON_Y && pMouseX < BUTTON_X + BUTTON_WIDTH && pMouseY < BUTTON_Y + BUTTON_HEIGHT) {
            return linkPage
                .getLinkText()
                .getComponent()
                .getStyle();
        }

        return super.getClickedComponentStyleAt(pMouseX, pMouseY);
    }

}
