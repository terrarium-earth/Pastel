package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.page.BookTextPage;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookTextPageRenderer;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.compat.modonomicon.pages.BookConfirmationButtonPage;
import earth.terrarium.pastel.networking.c2s_payloads.GuidebookConfirmationButtonPressedPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.client.gui.components.Button;

public class BookConfirmationButtonPageRenderer extends BookTextPageRenderer {

    public BookConfirmationButtonPageRenderer(BookTextPage page) {
        super(page);
    }

    public boolean isConfirmed() {
        if (!(page instanceof BookConfirmationButtonPage confirmationPage)) return false;
        return AdvancementHelper.hasAdvancement(mc.player, confirmationPage.getCheckedAdvancement());
    }

    @Override
    public void onBeginDisplayPage(BookEntryScreen parentScreen, int left, int top) {
        if (!(page instanceof BookConfirmationButtonPage confirmationPage)) return;
        super.onBeginDisplayPage(parentScreen, left, top);

        boolean completed = isConfirmed();

        BookTextHolder buttonText = completed
                ? confirmationPage.getConfirmedButtonText()
                : confirmationPage.getButtonText();

        Button button = Button.builder(buttonText.getComponent(), this::confirmationButtonClicked)
                .size(BookEntryScreen.PAGE_WIDTH - 12, Button.DEFAULT_HEIGHT)
                .pos(2, BookEntryScreen.PAGE_HEIGHT - 3)
                .build();

        button.active = !completed;
        addButton(button);
    }

    protected void confirmationButtonClicked(Button button) {
        if (!(page instanceof BookConfirmationButtonPage confirmationPage)) return;
        PacketDistributor.sendToServer(new GuidebookConfirmationButtonPressedPayload(confirmationPage.getConfirmationString()));
        button.setMessage(confirmationPage.getConfirmedButtonText().getComponent());
        BookGuiManager.get().openEntry(page.getBook().getId(), page.getParentEntry().getId(), page.getPageNumber());
    }

}
