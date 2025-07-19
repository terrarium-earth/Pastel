package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.klikli_dev.modonomicon.client.render.page.BookTextPageRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.compat.modonomicon.pages.BookStatusEffectPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;

public class BookStatusEffectPageRenderer extends BookTextPageRenderer {

    private final TextureAtlasSprite statusEffectSprite;

    public BookStatusEffectPageRenderer(BookStatusEffectPage page) {
        super(page);
        var statusEffect = BuiltInRegistries.MOB_EFFECT.getHolder(page.getStatusEffectId())
                                                       .orElse(null);
        this.statusEffectSprite = Minecraft.getInstance()
                                           .getMobEffectTextures()
                                           .get(statusEffect);
    }

    @Override
    public int getTextY() {
        return 50;
    }

    @Override
    public void render(GuiGraphics drawContext, int mouseX, int mouseY, float ticks) {
        super.render(drawContext, mouseX, mouseY, ticks);

        RenderSystem.enableBlend();
        drawContext.blit(49, 24, 0, 18, 18, statusEffectSprite);
    }

}
