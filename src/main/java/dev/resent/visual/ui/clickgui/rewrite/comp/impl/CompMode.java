package dev.resent.visual.ui.clickgui.rewrite.comp.impl;

import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.ModeSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;

public class CompMode extends Comp{
	
	public CompMode(float x, float y, float width, Mod m, Setting s) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.mod = m;
        this.setting = s;
    }

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if(isMouseInside(mouseX, mouseY, x+8, y-3, x+18, y+9)) {
			((ModeSetting)setting).cycle(false);
		}
		
		if(isMouseInside(mouseX, mouseY, x+width-138, y-3, x+width-130, y+9)) {
			((ModeSetting)setting).cycle(true);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		RenderUtils.drawRoundedRect(x, y-10, x-90+width-30, y+20, 8, 0xFF000000);
		GlUtils.startScale((x+x-90+width-30)/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(setting.name)/2, y-20, 1.4f);
		Minecraft.getMinecraft().fontRendererObj.drawString(setting.name, (x+x-90+width-30)/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(setting.name)/2-4, y-24, -1, false);
		GlStateManager.popMatrix();
		Minecraft.getMinecraft().fontRendererObj.drawString(((ModeSetting)setting).getValue(), (x+x-90+width-30)/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(((ModeSetting)setting).getValue())/2, y, -1, false);
		Minecraft.getMinecraft().fontRendererObj.drawString("<", x+10, y, -1, false);
		//RenderUtils.drawRectOutline(x+8, y-3, x+18, y+9, -1);
		Minecraft.getMinecraft().fontRendererObj.drawString(">", x+width-135, y, -1, false);
		//RenderUtils.drawRectOutline(x+width-138, y-3, x+width-130, y+9, -1);
	}

}
