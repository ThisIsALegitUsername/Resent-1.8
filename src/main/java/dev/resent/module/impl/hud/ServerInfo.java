package dev.resent.module.impl.hud;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;

public class ServerInfo extends RenderMod {

    public ServerInfo() {
        super("Server info", Category.HUD, 4, 44, true);
    }

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth(getText()) + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    public void draw() {
        drawString(getText(), this.x + 2, this.y + 2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
    }

    public String getText() {
        if (mc.getCurrentServerData() != null) {
            return "[Playing on: " + mc.getCurrentServerData().serverIP + "]";
        }
        return "[Playing on: Not connected]";
    }
}
