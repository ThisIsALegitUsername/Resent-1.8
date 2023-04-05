package dev.resent.visual.ui.clickgui.rewrite.comp;

import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.Setting;

public abstract class Comp {

    public Mod mod;
    public Setting setting;
    public float x, y, width, height;

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    public void mouseReleased(int mouseX, int mouseY, int state) {}
    public void drawScreen(int mouseX, int mouseY) {}
    public void keyTyped(char typedChar, int keyCode) {}
    
    public boolean isMouseInside(double mouseX, double mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }
    
}
