package dev.resent.module.impl.hud;

import java.util.ArrayList;
import java.util.List;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.util.misc.FuncUtils;
import net.lax1dude.eaglercraft.v1_8.Mouse;

public class CPS extends RenderModule {

    public CPS() {
        super("CPS", Category.HUD, 50, 4, true);
        addSetting(tshadow);
    }

    private List<Long> clicks = new ArrayList<>();
    private boolean wasPressed;
    private long lastPressed;

    public BooleanSetting tshadow = new BooleanSetting("Text shadow", "", true);
    public int getWidth() { return mc.fontRendererObj.getStringWidth("[CPS: 00]") + 4; }
    public int getHeight() { return mc.fontRendererObj.FONT_HEIGHT+4; }

    @Override
    public void draw() {
        final boolean pressed = Mouse.isButtonDown(0) || Mouse.isButtonDown(1);

        if(pressed != wasPressed){
            lastPressed = System.currentTimeMillis();
            wasPressed = pressed;
            if(pressed){
                this.clicks.add(lastPressed);
            }
        }

        final long time = System.currentTimeMillis();
        FuncUtils.removeIf(clicks, aLong -> aLong + 1000 < time);

        mc.fontRendererObj.drawString("CPS: " + clicks.size(), this.x+2, this.y+2, -1, tshadow.getValue());
    }

}