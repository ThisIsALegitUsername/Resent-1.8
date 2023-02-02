package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;

@RenderModule()
public class Ping extends RenderMod {

    public Ping() {
        super("Ping Display", Category.HUD, 4, 54, true);
    }

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
