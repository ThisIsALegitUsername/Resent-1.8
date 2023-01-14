package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import dev.resent.util.misc.FuncUtils;
import java.util.ArrayList;
import java.util.List;
import net.lax1dude.eaglercraft.v1_8.Mouse;

public class CPS extends RenderModule {

    public CPS() {
        super("CPS", Category.HUD, 4, 84, true);
        addSetting(tshadow);
    }

    private final List<Long> clicks = new ArrayList<>();
    private boolean wasPressed;
    private long lastPressed;

    public BooleanSetting tshadow = new BooleanSetting("Text shadow", "", true);

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[00 CPS]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    @Override
    public void draw() {
        final boolean pressed = Mouse.isButtonDown(0) || Mouse.isButtonDown(1);

        if (pressed != wasPressed) {
            lastPressed = System.currentTimeMillis();
            wasPressed = pressed;
            if (pressed) {
                this.clicks.add(lastPressed);
            }
        }

        final long time = System.currentTimeMillis();
        FuncUtils.removeIf(clicks, aLong -> aLong + 1000 < time);

        mc.fontRendererObj.drawString("[" + clicks.size() + " CPS]", this.x + 2, this.y + 2, -1, tshadow.getValue());
    }
}
