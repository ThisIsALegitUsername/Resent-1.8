package dev.resent.visual.ui.clickgui.rewrite.comp.impl;

import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import net.minecraft.client.Minecraft;

public class CompCustom extends Comp{
	
	public String name;
	
	public CompCustom(float x, float y, Mod m, String name, Setting s) {
        this.x = x;
        this.y = y;
        this.mod = m;
        this.name = name;
        this.setting = s;
    }

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if(isMouseInside(mouseX, mouseY, x, y, x+Minecraft.getMinecraft().fontRendererObj.getStringWidth(name)*2, y+20)) {
			((CustomRectSettingDraw)setting).onPress();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RenderUtils.drawRoundedRect(x, y, x+Minecraft.getMinecraft().fontRendererObj.getStringWidth(name)*2, y+20, 8, isMouseInside(mouseX, mouseY, x, y, x+Minecraft.getMinecraft().fontRendererObj.getStringWidth(name)*2, y+20) ? 0xFF181818 : 0xFF000000);
		Minecraft.getMinecraft().fontRendererObj.drawString(name, x+Minecraft.getMinecraft().fontRendererObj.getStringWidth(name)/2, y+6, -1, false);
	}

}
