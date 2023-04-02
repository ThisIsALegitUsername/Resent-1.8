package dev.resent.module.impl.hud;

import java.util.ArrayList;
import java.util.List;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.util.misc.FuncUtils;
import net.lax1dude.eaglercraft.v1_8.Mouse;

@RenderModule(name = "CPS", category = Category.HUD, x = 4, y = 26)
public class CPS extends RenderMod {

    private List<Long> clicks = new ArrayList<>();
    public boolean wasPressed;
    public long lastPressed;

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[CPS: " + clicks.size() + "]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    public void draw() {
        boolean pressed = Mouse.isButtonDown(0) || Mouse.isButtonDown(1);

        if (pressed != wasPressed) {
            lastPressed = System.currentTimeMillis();
            wasPressed = pressed;
            if (pressed) {
                this.clicks.add(lastPressed);
            }
        }

        final long time = System.currentTimeMillis();
        FuncUtils.removeIf(clicks, sinceLast -> sinceLast + 1000 < time);

        drawString("[CPS: " + clicks.size() + "]", this.x + 2, this.y + 2);
    }
}
