package dev.resent.visual.ui.clickgui.rewrite.comp.impl;

import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.Setting;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;

public class CompMode extends Comp{
	
	public CompMode(float x, float y, Mod m, Setting s) {
        this.x = x;
        this.y = y;
        this.mod = m;
        this.setting = s;
    }

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
	}

}
