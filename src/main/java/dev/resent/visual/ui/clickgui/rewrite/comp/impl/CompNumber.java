package dev.resent.visual.ui.clickgui.rewrite.comp.impl;

import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.NumberSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class CompNumber extends Comp{
	
	public boolean draggingNumber;
	public boolean settingDrag;
	public int sliderOffset;
	
	public CompNumber(float x, float y, Mod m, Setting s) {
        this.x = x;
        this.y = y;
        this.mod = m;
        this.setting = s;
    }

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if(isMouseInside(mouseX, mouseY, x*2+sliderOffset, y-3, x*2+10+sliderOffset, y+7)) {
			draggingNumber = true;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		NumberSetting ss = ((NumberSetting)setting);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(setting.name + ": sof " + sliderOffset + ", val: " + ss.getValue(), x, y, -1);
        Gui.drawRect(x*2, y, x*2+100, y+4, -1);
        RenderUtils.drawRoundedRect(x*2+sliderOffset, y-3, x*2+10+sliderOffset, y+7, 4, Color.RED.getRGB());
        
        if (settingDrag) {
            sliderOffset = (int) (mouseX - x*2);
            ss.setValue(sliderOffset * (ss.max / 100));
        } else {
            sliderOffset = (int) ((ss.getValue() * 100) / ss.max);
        }

        if (sliderOffset < 0) {
            settingDrag = false;
            sliderOffset = 0;
        } else if (draggingNumber) {
            settingDrag = true;
        }

        if (sliderOffset > 100) {
            settingDrag = false;
            sliderOffset = 100;
        } else if (draggingNumber) {
            settingDrag = true;
        }
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		draggingNumber = false;
		settingDrag = false;
	}
	
}
