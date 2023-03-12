package dev.resent.module.impl.hud;

import java.text.DecimalFormat;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;

@RenderModule(name = "BPS", category = Category.HUD, x = 4, y = 16)
public class BPS extends RenderMod{

    public int getWidth() { return mc.fontRendererObj.getStringWidth(getText()) + 4;}
    public int getHeight(){ return 13; }

    public double getBPS() {
        return mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ) * (mc.timer.ticksPerSecond * mc.timer.timerSpeed);
    }

    public String getText(){
        return "BPS: " + new DecimalFormat("0.##").format(getBPS());
    }

    public void draw(){
        drawString(getText(), x+2, y+2);
    }
    
}
