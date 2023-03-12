package dev.resent.visual.ui.clickgui.rewrite;

import java.util.ArrayList;

import dev.resent.client.Resent;
import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.GlUtils;
import dev.resent.visual.ui.Theme;
import dev.resent.visual.ui.animation.Animation;
import dev.resent.visual.ui.animation.Direction;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import dev.resent.visual.ui.clickgui.rewrite.comp.impl.CompCheck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGuiRewrite extends GuiScreen{

    public ArrayList<Comp> comps = new ArrayList<>();
    public int x, y, width, height, offset;
    public Animation introAnimation;
    public ScaledResolution sr;
    public boolean closing;
    public Mod selectedMod;

    @Override
    public void drawScreen(int mouseX, int mouseY, float var3) {
        GlUtils.startScale((this.x + this.width) / 2, (this.y + this.height) / 2, introAnimation != null ? (float) introAnimation.getValue() : 1);

        for(Mod m : Resent.INSTANCE.modManager.modules){

        }

        GlUtils.stopScale();

        if(selectedMod != null){
            for (Comp comp : comps) {
                comp.drawScreen(mouseX, mouseY);
            }
        }

        if (closing) {
            comps.clear();
        	if(introAnimation == null) {
        		mc.displayGuiScreen(null);
        		return;
        	}
        		
            introAnimation.setDirection(Direction.BACKWARDS);
            if (introAnimation.isDone(Direction.BACKWARDS)) {
                mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        for(Mod m : Resent.INSTANCE.modManager.modules){

            //replace params with gear icon pos
            if(isMouseInside(mouseX, mouseY, width-20, y+20, width-40, y+40) && mouseButton == 0){
                for(Setting s : m.settings){
                    if(s instanceof BooleanSetting){
                        comps.add(new CompCheck(4, 4, selectedMod, s));
                    }
                }
            }

        }

        if(selectedMod != null){
            for(Comp c : comps){
                c.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

    }

    @Override
    public void initGui() {
        sr = new ScaledResolution(mc);
        x = sr.getScaledWidth()/4;
        y = sr.getScaledHeight()/4;
        width = x + sr.getScaledWidth()/2;
        height = y + sr.getScaledHeight()/2;
        introAnimation = Theme.getAnimation(500, 1, 3, 3.8f, 1.35f, false);
    }

    @Override
    protected void keyTyped(char par1, int key) {
        if (key == 0x01 || key == Minecraft.getMinecraft().gameSettings.keyBindClickGui.keyCode) {
            closing = true;
        }

        if(selectedMod != null){
            for(Comp c : comps){
                c.keyTyped(par1, key);
            }
        }
    }

    public boolean isMouseInside(double mouseX, double mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }
    
}
