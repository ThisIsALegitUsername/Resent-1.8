package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.ui.Theme;
import net.minecraft.client.Minecraft;

@RenderModule(name = "FPS", category = Category.HUD, x = 4, y = 38)
public class FPS extends RenderMod {

    public int getWidth() { return mc.fontRendererObj.getStringWidth(getText()) + 4; }
    public int getHeight() { return mc.fontRendererObj.FONT_HEIGHT + 4; }

    public String getText(){
        return "[FPS: " + Minecraft.debugFPS + "]";
    }

    @Override
    public void draw() {
        if (mc.thePlayer != null) {
            drawString(getText(), this.x + 2, this.y + 2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
        }
    }
}
