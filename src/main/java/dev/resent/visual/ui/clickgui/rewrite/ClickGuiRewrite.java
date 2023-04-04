package dev.resent.visual.ui.clickgui.rewrite;

import java.io.IOException;
import java.util.ArrayList;

import dev.resent.client.Resent;
import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.FuncUtils;
import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.Theme;
import dev.resent.visual.ui.animation.Animation;
import dev.resent.visual.ui.animation.Direction;
import dev.resent.visual.ui.animation.SimpleAnimation;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import dev.resent.visual.ui.clickgui.rewrite.comp.impl.CompCheck;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ClickGuiRewrite extends GuiScreen{

	public FontRenderer fr;
    public ArrayList<Comp> comps = new ArrayList<>();
    public float x, y, width, height;
    public Animation introAnimation;
    public ScaledResolution sr;
    public boolean closing;
    public Mod selectedMod;
    public String searchString = "";
    public SimpleAnimation partAnimation = new SimpleAnimation(0);
    public int backgroundColor = new Color(18, 18, 18).getRGB(),
    		   primaryColor = 0xFF000000,
    		   secondaryColor = new Color(33, 33, 33).getRGB(),
    		   onSurfaceColor = new Color(3, 218, 197).getRGB(),
    		   secondaryFontColor = new Color(187, 134, 252).getRGB();
    public int partOffset = 0, scrollOffset = 0;
    public String part = "Home";

    @Override
    public void drawScreen(int mouseX, int mouseY, float var3) {
    	
    	int offset = 0;
    	
        GlUtils.startScale((this.x + this.width) / 2, (this.y + this.height) / 2, introAnimation != null ? (float) introAnimation.getValue() : 1);

        //Navigation bar
        RenderUtils.drawRoundedRect(x, y, x+width-60, y+height, 32, secondaryColor);
        
        //Background overlay
        RenderUtils.drawRoundedRect(x+60, y, x+width, y+height, 32, backgroundColor);
        Gui.drawRect(x+60, y, x+102, y+height, backgroundColor);
        
        //Seperating line
        Gui.drawRect(x, y+90, x+width, y+95, secondaryColor);
        
        //Title
        GlStateManager.pushMatrix();
        GlStateManager.translate(x+80, y+36, 0);
        GlStateManager.scale(3.5f, 3.5f, 1);
        GlStateManager.translate(-(x+80), -(y+36), 0);
        fr.drawString("Resent", x+80, y+36, -1, false);
        GlStateManager.popMatrix();
        
        //Navigation selection
        RenderUtils.drawRoundedRect(x+15, (int)y+115+partAnimation.getValue(), x+45, y+145+partAnimation.getValue(), 8, secondaryFontColor);
        
        //Navigation icons
        GlStateManager.color(1,  1,  1);
        mc.getTextureManager().bindTexture(new ResourceLocation("/resent/house.png"));
        Gui.drawModalRectWithCustomSizedTexture(x+20, (int)y+120, 0, 0, 20, 20, 20, 20);
        mc.getTextureManager().bindTexture(new ResourceLocation("/resent/gear.png"));
        Gui.drawModalRectWithCustomSizedTexture(x+20, (int)y+170, 0, 0, 20, 20, 20, 20);
        
        //Search
        RenderUtils.drawRoundedRect(x+width-300, y+25, x+width-50, y+65, 9, secondaryColor);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x+width-290, y+40, 0);
        GlStateManager.scale(1.5f, 1.5f, 1);
        GlStateManager.translate(-(x+width-290), -(y+40), 0);
        if(searchString.length() > 0) {
        	fr.drawString(searchString, x+width-290, y+40, secondaryFontColor, false);
        }else {
        	fr.drawString("Search", x+width-290, y+40, new Color(97, 97, 97).getRGB(), false);
        }
        GlStateManager.popMatrix();
        
        //Draw module button
        for(Mod m : Resent.INSTANCE.modManager.modules){
        	if(!m.isAdmin() && m.getName().toLowerCase().startsWith(searchString.toLowerCase()) && selectedMod == null) {
        		
        		if(y+115+offset+scrollOffset > y+95 && y+185+offset+scrollOffset < y+height) {
        		//Body
        		RenderUtils.drawRoundedRect(x+80, y+115+offset+scrollOffset, x+width-20, y+185+offset+scrollOffset, 16, secondaryColor);
        		
        		//Gear
        		if(m.doesHaveSetting()) {
	        		GlStateManager.color(1, 1, 1);
	        		mc.getTextureManager().bindTexture(new ResourceLocation("/resent/gear.png"));
	        		Gui.drawModalRectWithCustomSizedTexture(x+width-60, (int)y+140+offset+scrollOffset, 0, 0, 20, 20, 20, 20);
        		}
            	//RenderUtils.drawRoundedRect(x+width-60, y+140+offset, x+width-40, y+160+offset, 4, -1);
            	
            	//Toggle
            	RenderUtils.drawRoundedRect(x+90, y+125+offset+scrollOffset, x+140, y+175+offset+scrollOffset, 8, new Color(66, 66, 66).getRGB());
            	
        		GlUtils.startScale(x+90, y+140+offset+scrollOffset, 2);
        		fr.drawString(m.getName(), x+120, y+140+offset+scrollOffset, -1, false);
        		GlStateManager.popMatrix();
        		
        		if(isMouseInside(mouseX, mouseY, x+80, y+115+offset+scrollOffset, x+width-20, y+185+offset+scrollOffset)) {
        			fr.drawString(m.getDescription(), mouseX+8, mouseY, onSurfaceColor, false);
        		}
        		
        		}
            	offset += 80;
        	}
        }

        GlStateManager.popMatrix();

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
    	
    if(isMouseInside(mouseX, mouseY, x+20, (int)y+170, x+40, (int)y+190)) {
    	partAnimation.setAnimation(50-partOffset, 10);
    }else if(isMouseInside(mouseX, mouseY, x+20, (int)y+120, x+40, (int)y+140)) {
    	partAnimation.setAnimation(0-partOffset, 10);
    }

    	int offset = 0;
    	
        for(Mod m : Resent.INSTANCE.modManager.modules){
           	if(!m.isAdmin()) {
	            if(isMouseInside(mouseX, mouseY, x+width-60, y+140+offset, x+width-40, y+160+offset) && mouseButton == 0 && m.doesHaveSetting()){
	                for(Setting s : m.settings){
	                    if(s instanceof BooleanSetting){
	                        comps.add(new CompCheck(4, 4, selectedMod, s));
	                    }
	                }
	            }

	            offset += 80;
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
        }
        
        // Search box stuff
        else if(key == KeyboardConstants.KEY_BACK) {
        	if(searchString.length() != 0) {
        		searchString = searchString.substring(0, searchString.length()-1);
        	}
        }else {
        	if(searchString.length() <= 18) {
        		String balls = ChatAllowedCharacters.filterAllowedCharacters(String.valueOf(par1));
        		if(balls != null && balls != "") {
        			searchString += String.valueOf(par1);
        			scrollOffset = 0;
        		}
        	}
        }
        
    }

    @Override
	public void handleMouseInput() throws IOException {
    	int scroll = Mouse.getEventDWheel();
    	
    	if(scroll > 0) {
    		scrollOffset += 30;
    	}else if(scroll < 0) {
    		scrollOffset -= 30;
    	}
		super.handleMouseInput();
	}

	public boolean isMouseInside(double mouseX, double mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }
	
	public int getMaxScroll() {
		return 500;
	}
    
}
