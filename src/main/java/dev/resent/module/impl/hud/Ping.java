package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;

@RenderModule(name = "Ping display", category = Category.HUD, x = 4, y = 50)
public class Ping extends RenderMod {

    @Override
    public void draw() {
        int ms = 0;
        if (mc.isSingleplayer()) {
            ms = -1;
        }

        ms = (int) mc.getCurrentServerData().pingToServer;

        this.setHeight(mc.fontRendererObj.FONT_HEIGHT + 4);
        this.setWidth(mc.fontRendererObj.getStringWidth("[" + ms + " ms]") + 4);
        drawString("[" + ms + " ms]", this.x + 2, this.y + 2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
    }
}
