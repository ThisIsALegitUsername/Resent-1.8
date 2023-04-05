package dev.resent.visual.ui.clickgui.rewrite.comp.impl;

import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.FuncUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import net.minecraft.client.Minecraft;

public class CompCheck extends Comp{

    public CompCheck(float x, float y, Mod m, Setting s){
        this.x = x;
        this.y = y;
        this.mod = m;
        this.setting = s;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    	RenderUtils.drawRoundedRect(this.x, this.y, this.x+20, this.y+20, 8, ((BooleanSetting)setting).getValue() ? new Color(3, 218, 197).getRGB() : new Color(66, 66, 66).getRGB());
    	Minecraft.getMinecraft().fontRendererObj.drawString(setting.name, this.x+25, this.y+6.5f, -1, false);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(isMouseInside(mouseX, mouseY, this.x, this.y, this.x+20, this.y+20)){
            ((BooleanSetting)setting).toggle();
        }
    }

}
