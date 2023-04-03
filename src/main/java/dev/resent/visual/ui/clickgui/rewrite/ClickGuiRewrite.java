package dev.resent.visual.ui.clickgui.rewrite;

import java.util.ArrayList;

import dev.resent.client.Resent;
import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.Theme;
import dev.resent.visual.ui.animation.Animation;
import dev.resent.visual.ui.animation.Direction;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import dev.resent.visual.ui.clickgui.rewrite.comp.impl.CompCheck;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGuiRewrite extends GuiScreen{

	public FontRenderer fr;
    public ArrayList<Comp> comps = new ArrayList<>();
    public float x, y, width, height, offset;
    public Animation introAnimation;
    public ScaledResolution sr;
    public boolean closing;
    public Mod selectedMod;
    public int backgroundColor = new Color(12, 12, 12).getRGB(), primaryColor = 0xFF000000, secondaryColor = new Color(22, 22, 22).getRGB();

    @Override
    public void drawScreen(int mouseX, int mouseY, float var3) {
        GlUtils.startScale((this.x + this.width) / 2, (this.y + this.height) / 2, introAnimation != null ? (float) introAnimation.getValue() : 1);

        //Navigation bar
        RenderUtils.drawRoundedRect(x, y, x+width-60, y+height, 32, secondaryColor);
        
        //Background overlay
        RenderUtils.drawRoundedRect(x+60, y, x+width, y+height, 32, backgroundColor);
        Gui.drawRect(x+60, y, x+102, y+height, backgroundColor);
        
        //Seperating line
        Gui.drawRect(x, y+90, x+width, y+95, secondaryColor);
        
        //Search
        RenderUtils.drawRoundedRect(x+width-300, y+25, x+width-50, y+65, 9, new Color(22, 22, 22).getRGB());
        
        GlStateManager.translate(x+80, y+36, 0);
        GlStateManager.scale(3, 3, 1);
        GlStateManager.translate(-(x+80), -(y+36), 0);
        fr.drawString("Resent", x+80, y+36, -1, false);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        
        //Draw module button
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
        x = sr.getScaledWidth()/10;
        y = sr.getScaledHeight()/10;
        width = sr.getScaledWidth()/1.25f;
        height = sr.getScaledHeight()/1.25f;
        introAnimation = Theme.getAnimation(500, 1, 3, 3.8f, 1.35f, false);
        fr = mc.fontRendererObj;
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
        }else {
        	
        }
    }

    public boolean isMouseInside(double mouseX, double mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }
    
}
