package dev.resent.visual.ui.clickgui.rewrite.comp.impl;

import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.FuncUtils;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;

public class CompCheck extends Comp{

    public CompCheck(int x, int y, Mod m, Setting s){
        this.x = x;
        this.y = y;
        this.mod = m;
        this.setting = s;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        //replace with checkbox location
        if(FuncUtils.isInside(mouseX, mouseY, x, y, width, height)){
            ((BooleanSetting)setting).toggle();
        }
    }

}
