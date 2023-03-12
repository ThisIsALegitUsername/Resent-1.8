package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.util.misc.FuncUtils;
import dev.resent.visual.ui.Theme;

import java.util.ArrayList;
import java.util.List;

@RenderModule(name = "CPS", category = Category.HUD, x = 4, y = 26)
public class CPS extends RenderMod {

    private List<Long> clicks = new ArrayList<>();
    private boolean wasPressed;
    private long lastPressed;

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[CPS: " + clicks.size() + "]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    @Override
    public void draw() {
        boolean pressed = mc.gameSettings.keyBindAttack.pressed || mc.gameSettings.keyBindUseItem.pressed;

        if (pressed != wasPressed) {
            lastPressed = System.currentTimeMillis();
            wasPressed = pressed;
            if (pressed) {
                this.clicks.add(lastPressed);
            }
        }

        final long time = System.currentTimeMillis();
        FuncUtils.removeIf(clicks, aLong -> aLong + 1000 < time);

        drawString("[CPS: " + clicks.size() + "]", this.x + 2, this.y + 2);
    }
}
