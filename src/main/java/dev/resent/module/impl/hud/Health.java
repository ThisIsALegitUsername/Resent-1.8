package dev.resent.module.impl.hud;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;

public class Health extends RenderModule {

    public Health() {
        super("Health Display", Category.HUD, 4, 64, true);
        addSetting(tshadow);
    }

    public BooleanSetting tshadow = new BooleanSetting("Text shadow", "", true);

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[" + mc.thePlayer.getHealth() + " Health]") + 4;
    }

    @Override
    public void draw() {
        mc.fontRendererObj.drawString("[" + mc.thePlayer.getHealth() + " Health]", this.x + 2, this.y + 2, Theme.getFontColor(Theme.getId()), tshadow.getValue());
    }
}
