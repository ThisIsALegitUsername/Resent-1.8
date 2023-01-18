package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import dev.resent.util.misc.CPSUtils;

public class CPS extends RenderModule {

    public CPS() {
        super("CPS", Category.HUD, 4, 84, true);
        addSetting(tshadow);
    }

    public BooleanSetting tshadow = new BooleanSetting("Text shadow", "", true);

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[00 CPS]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    @Override
    public void draw() {
        mc.fontRendererObj.drawString("[" + CPSUtils.getLeftCPS()+CPSUtils.getRightCPS() + " CPS]", this.x + 2, this.y + 2, -1, tshadow.getValue());
    }
    
}
