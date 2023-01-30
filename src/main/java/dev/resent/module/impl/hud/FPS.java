package dev.resent.module.impl.hud;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.module.setting.BooleanSetting;
import net.minecraft.client.Minecraft;

public class FPS extends RenderModule {

    public Minecraft mc = Minecraft.getMinecraft();

    public FPS() {
        super("FPS", Category.HUD, 4, 14, true);
        addSetting(tshadow);
    }

    public BooleanSetting tshadow = new BooleanSetting("Text Shadow", "", true);

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth(getText()) + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    public String getText(){
        return "[FPS: " + Minecraft.debugFPS + "]";
    }

    @Override
    public void draw() {
        if (mc.thePlayer != null) {
            mc.fontRendererObj.drawString(getText(), this.x + 2, this.y + 2, Theme.getFontColor(Theme.getFontId()), tshadow.getValue());
        }
    }
}
