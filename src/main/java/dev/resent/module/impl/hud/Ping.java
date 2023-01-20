package dev.resent.module.impl.hud;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.module.setting.BooleanSetting;

public class Ping extends RenderModule {

    public Ping() {
        super("Ping Display", Category.HUD, 4, 54, true);
        addSetting(tshadow);
    }

    public BooleanSetting tshadow = new BooleanSetting("Text Shadow", "", true);

    @Override
    public void draw() {
        int ms = 0;
        if (mc.isSingleplayer()) {
            ms = Theme.getFontColor(Theme.getId());
        }
        ms = (int) mc.getCurrentServerData().pingToServer;

        this.setHeight(mc.fontRendererObj.FONT_HEIGHT + 4);
        this.setWidth(mc.fontRendererObj.getStringWidth("[" + ms + " ms]") + 4);
        mc.fontRendererObj.drawString("[" + ms + " ms]", this.x + 2, this.y + 2, Theme.getFontColor(Theme.getId()), tshadow.getValue());
    }
}
