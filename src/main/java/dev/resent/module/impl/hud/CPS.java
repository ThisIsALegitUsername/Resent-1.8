package dev.resent.module.impl.hud;

import java.util.ArrayList;
import java.util.List;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.util.misc.FuncUtils;

public class CPS extends RenderModule {

    public CPS() {
        super("CPS", Category.HUD, 50, 4, true);
    }

    private List<Long> clicks = new ArrayList<>();
    private boolean wasPressed;
    private long lastPressed;

    public int getWidth() { return mc.fontRendererObj.getStringWidth("[CPS: "+ clicks.size() + "]") + 4; }
    public int getHeight() { return mc.fontRendererObj.FONT_HEIGHT+4; }

    @Override
    public void draw() {

        boolean pressed = mc.gameSettings.keyBindAttack.pressed || mc.gameSettings.keyBindUseItem.pressed;

        if(pressed != wasPressed){
            lastPressed = System.currentTimeMillis();
            wasPressed = pressed;
            if(pressed){
                this.clicks.add(lastPressed);
            }
        }

        final long time = System.currentTimeMillis();
        FuncUtils.removeIf(clicks, aLong -> aLong + 1000 < time);

        drawString("[CPS: " + clicks.size() + "]", this.x+2, this.y+2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
    }

}