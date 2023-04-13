package dev.resent.visual.ui.clickgui.rewrite;

import java.io.IOException;
import java.util.ArrayList;

import dev.resent.client.Resent;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import dev.resent.module.base.setting.ModeSetting;
import dev.resent.module.base.setting.NumberSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.animation.Animation;
import dev.resent.visual.ui.animation.Direction;
import dev.resent.visual.ui.animation.SimpleAnimation;
import dev.resent.visual.ui.animation.impl.EaseInOutQuad;
import dev.resent.visual.ui.clickgui.HUDConfigScreen;
import dev.resent.visual.ui.clickgui.rewrite.comp.Comp;
import dev.resent.visual.ui.clickgui.rewrite.comp.impl.CompCheck;
import dev.resent.visual.ui.clickgui.rewrite.comp.impl.CompCustom;
import dev.resent.visual.ui.clickgui.rewrite.comp.impl.CompMode;
import dev.resent.visual.ui.clickgui.rewrite.comp.impl.CompNumber;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ClickGuiRewrite extends GuiScreen {

    public FontRenderer fr;
    public ArrayList<Comp> comps = new ArrayList<>();
    public float x, y, width, height;
    public Animation introAnimation;
    public ScaledResolution sr;
    public boolean closing, isSearchFocused, home = true, setting;
    public Mod selectedMod;
    public String searchString = "";
    public SimpleAnimation partAnimation;
    public int backgroundColor = new Color(18, 18, 18).getRGB(), primaryColor = 0xFF000000, secondaryColor = new Color(33, 33, 33).getRGB(), onSurfaceColor = new Color(3, 218, 197).getRGB();
    public Color secondaryFontColor = new Color(187, 134, 252);
    public int scrollOffset = 0;
    public String part = "Home", currentView = "normalView";
    public Animation bgDimAnim;
    public Animation searchCursorAnim;
    public Category selectedCategory = null;

    @Override
    public void drawScreen(int mouseX, int mouseY, float var3) {
        int offset = 0;
        // background dim
        drawRect(0, 0, 999999, 999999, new Color(0, 0, 0, (int) bgDimAnim.getValue()).getRGB());
        
        GlUtils.startScale(x+width/2, y+height/2, 1);
        GlStateManager.translate(0, (height + y) - introAnimation.getValue(), 0);
        /* !-------------- NECESSARY ELEMENTS -----------------! */

        //Navigation bar
        RenderUtils.drawRoundedRect(x, y, x+width-60, y+height, 16, secondaryColor);

        //Background overlay
        RenderUtils.drawRoundedRect(x+60, y, x+width, y+height, 16, backgroundColor);
        Gui.drawRect(x+60, y, x+102, y+height, backgroundColor);

        //Separating line
        Gui.drawRect(x, y+80, x+width, y+85, secondaryColor);

        //Title
        GlStateManager.pushMatrix();
        GlStateManager.translate(x+80, y+36, 0);
        GlStateManager.scale(3.5F, 3.5F, 1);
        GlStateManager.translate(-(x+80), -(y+36), 0);
        fr.drawString("Resent", x+80, y+36, -1, false);
        GlStateManager.popMatrix();

        if (setting) {
            partAnimation.setAnimation(50, 9);
        }
        if (home) {
            partAnimation.setAnimation(0, 9);
        }

        //Navigation selection
        RenderUtils.drawRoundedRect(x+15, y+115+partAnimation.getValue(), x+45, y+145+partAnimation.getValue(), 8, secondaryFontColor.getRGB());

        if(isMouseInside(mouseX, mouseY, x+20, y+220, x+40, y+240)) {
        	RenderUtils.drawRoundedRect(x+14, y+214, x+46, y+246, 16.5f, secondaryFontColor.getRGB());
        }
        
        //Navigation icons
        GlStateManager.color(1, 1, 1);
        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/house.png"));
        Gui.drawModalRectWithCustomSizedTexture(x+20, (int) y+120, 0, 0, 20, 20, 20, 20);
        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/gear2.png"));
        Gui.drawModalRectWithCustomSizedTexture(x+20, (int) y+170, 0, 0, 20, 20, 20, 20);
        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/edit.png"));
        Gui.drawModalRectWithCustomSizedTexture(x+20, (int) y+220, 0, 0, 20, 20, 20, 20);
        
        //Search
        RenderUtils.drawRoundedRect(x+width-300, y+25, x+width-50, y+65, 9, secondaryColor);

        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/search.png"));
        Gui.drawModalRectWithCustomSizedTexture(x+width-290, (int) y+36, 0, 0, 20, 20, 20, 20);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x+width-290, y+40, 0);
        GlStateManager.scale(1.5F, 1.5F, 1);
        GlStateManager.translate(-(x+width-290), -(y+40), 0);
        if (searchString.length() > 0) {
            fr.drawString(searchString, x+width-270, y+40, secondaryFontColor.getRGB(), false);
        } else if (!isSearchFocused) {
            fr.drawString(EnumChatFormatting.ITALIC+"Search", x+width-270, y+40, new Color(97, 97, 97).getRGB(), false);
        }

        if (isSearchFocused) {
            drawRect(x+width-271+fr.getStringWidth(searchString), y+38, x+width-270+fr.getStringWidth(searchString), y+50, 
            		new Color(187, 134, 252, (int) searchCursorAnim.getValue()).getRGB());

            if (searchCursorAnim.isDone()) {
                searchCursorAnim.changeDirection();
            }
        }

        GlStateManager.popMatrix();
        
        // Scroll Bar
        RenderUtils.drawRoundedRect((x + width) - 20, y+125, (x+width) - 10, (y+height) - 20, 4, secondaryColor);
        // purple bar thing
        float barSize = Resent.INSTANCE.modManager.modsInCategory(selectedCategory).size() / 4.2f;
        
        if (barSize > 9) {
        	barSize = 9;
        }
        
        int scrollThing = Math.round(scrollOffset / (Resent.INSTANCE.modManager.modsInCategory(selectedCategory).size() / 2.85f));
        
        float barSize2 = ((y+height) - (20 * barSize))-scrollThing;
        
        if (barSize2 < (y+125)-scrollThing) {
        	scrollThing /= 2.85f;
        	barSize2 = (((y+height) - (20 * 3.5f))-scrollThing);
        	
        }
        
        RenderUtils.drawRoundedRect((x + width) - 20, (y+125)-scrollThing, (x+width) - 10, barSize2, 4, 
        		new Color(secondaryFontColor.getRed() - 40, secondaryFontColor.getGreen() - 40, secondaryFontColor.getBlue() - 40).getRGB());
        RenderUtils.drawRoundedRect((x + width) - 21, (y+125)-scrollThing, (x+width) - 11, barSize2, 4, secondaryFontColor.getRGB());
        
        
        // Mod Categories
        RenderUtils.drawRoundedRect(x + 80, y+90, (x + width) - 30, y+120, 8, new Color(30, 30, 30).getRGB());
        // pls dont remove the thing that switches the colors, doesnt look good without the color switching
        if (selectedCategory == null) {
        	RenderUtils.drawRoundedRect(x + 85, y+95, x + 130, y+115, 8, secondaryFontColor.getRGB());
        }
        else {
        	RenderUtils.drawRoundedRect(x + 85, y+95, x + 130, y+115, 8, new Color(40, 40, 40).getRGB());
        }
        if (selectedCategory == Category.HUD) {
        	RenderUtils.drawRoundedRect(x + 135, y+95, x + 180, y+115, 8, secondaryFontColor.getRGB());
        }
        else {
        	RenderUtils.drawRoundedRect(x + 135, y+95, x + 180, y+115, 8, new Color(40, 40, 40).getRGB());
        }
        if (selectedCategory == Category.MISC) {
        	RenderUtils.drawRoundedRect(x + 185, y+95, x + 230, y+115, 8, secondaryFontColor.getRGB());
        }
        else {
        	RenderUtils.drawRoundedRect(x + 185, y+95, x + 230, y+115, 8, new Color(40, 40, 40).getRGB());
        }
        fr.drawString("All", (int) x+102, (int) y+102, -1);
        fr.drawString("HUD", (int) x+149, (int) y+102, -1);
        fr.drawString("Misc", (int) x+198, (int) y+102, -1);

        // Switch ClickGui Mod View
        drawRect(x+width-75, y+95, x+width-74, y+ 115, new Color(90, 90, 90).getRGB());

        if (isMouseInside(mouseX, mouseY, (x+width)-70, y+90, (x+width)-40, y+120)) {
        	RenderUtils.drawRoundedRect((x+width)-72, y+92, (x+width)-37, y+117, 2, secondaryFontColor.getRGB());
        }
        if (currentView == "gridView") {
        	mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/button_gridView.png"));
        }
        else {
        	mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/button_normalView.png"));
        }
        
        GlStateManager.color(1, 1, 1);
        Gui.drawModalRectWithCustomSizedTexture((x+width)-70, (int) y+90, 0, 0, 30, 30, 30, 30);

        /* !------------- HOME/MODULE (SOON) --------------------! */
        //Draw module button
        int offsetX = 0;
        int numElements = (int) Math.floor(width/90) - 2;
        int numElementsX = 0;
        if(part == "Home") {
        for (Mod m : Resent.INSTANCE.modManager.modsInCategory(selectedCategory)) {
            if (!m.isAdmin() && m.getName().toLowerCase().startsWith(searchString.toLowerCase()) && selectedMod == null) {
            	if (currentView == "normalView") {
	                if (y+125+offset+scrollOffset > y+95 && y+175+offset+scrollOffset < y+height) {
	                    //Body
	                    RenderUtils.drawRoundedRect(x+80, y+125+offset+scrollOffset, x+width-30, y+175+offset+scrollOffset, 16, secondaryColor);
	
	                    //Gear
	                    if (m.doesHaveSetting()) {
	                    	if (isMouseInside(mouseX, mouseY, x+width-70, y+140+offset+scrollOffset, x+width-50, y+140+offset+scrollOffset+20)) {
	                    		RenderUtils.drawRoundedRect(x+width-75, y+135+offset+scrollOffset, x+width-45, y+165+offset+scrollOffset, 8, secondaryFontColor.getRGB());
	                    	}
	                        GlStateManager.color(1, 1, 1);
	                        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/gear2.png"));
	                        Gui.drawModalRectWithCustomSizedTexture(x+width-70, (int) y+140+offset+scrollOffset, 0, 0, 20, 20, 20, 20);
	                    }
	
	                    //Toggle
	                    m.toggleAnimation.setAnimation(m.isEnabled() ? 20 : 0, 12);
	                    RenderUtils.drawRoundedRect(x+90, y+140+offset+scrollOffset, x+125, y+155+offset+scrollOffset, 8, new Color(66, 66, 66).getRGB(), true);
	                    RenderUtils.drawRoundedRect(x+90, y+140+offset+scrollOffset, x+105+m.toggleAnimation.getValue(), y+155+offset+scrollOffset, 8, secondaryFontColor.getRGB(), true);
	                    RenderUtils.drawRoundedRect(x+91+m.toggleAnimation.getValue(), y+140+offset+scrollOffset, x+106+m.toggleAnimation.getValue(), y+155+offset+scrollOffset, 8, new Color(180, 180, 180).getRGB(), true);
	                    RenderUtils.drawRoundedRect(x+90+m.toggleAnimation.getValue(), y+140+offset+scrollOffset, x+105+m.toggleAnimation.getValue(), y+155+offset+scrollOffset, 8, -1, true);
	                    
	                    GlUtils.startScale(x+90, y+140+offset+scrollOffset, 2);
	                    int i = fr.drawString(m.getName(), x+120, y+140+offset+scrollOffset, -1, false);
	                    GlStateManager.popMatrix();
	                    GlUtils.startScale(x+120+i / 2, y+120+offset+scrollOffset, 1.2f);
	                    if (!m.getDescription().startsWith("No des")) {
	                    	fr.drawString(m.getDescription(), x+20+i, y+140+offset+scrollOffset, -1, false);
	                    }
	                    GlStateManager.popMatrix();
	                }
	                offset += 60;
	            }else if(currentView == "gridView") {
            		if (y+125+offset+scrollOffset > y+95 && y+240+offset+scrollOffset < y+height) {
	                    //Body
	                    RenderUtils.drawRoundedRect(x+85+offsetX, y+125+offset+scrollOffset, x+175+offsetX, y+250+offset+scrollOffset, 16, secondaryColor);
	                    //Gear
	                    if (m.doesHaveSetting()) {
	                    	if (isMouseInside(mouseX, mouseY, x+140+offsetX, (int) y+220+offset+scrollOffset, x+160+offsetX, (int) y+240+offset+scrollOffset)) {
	                    		RenderUtils.drawRoundedRect(x+135+offsetX, (int) y+215+offset+scrollOffset, x+165+offsetX, (int) y+245+offset+scrollOffset, 8, secondaryFontColor.getRGB());
	                    	}
	                        GlStateManager.color(1, 1, 1);
	                        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/gear2.png"));
	                        Gui.drawModalRectWithCustomSizedTexture(x+140+offsetX, (int) y+220+offset+scrollOffset, 0, 0, 20, 20, 20, 20);
	                    }
	
	                    //Toggle
	                    m.toggleAnimation.setAnimation(m.isEnabled() ? 20 : 0, 12);
	                    RenderUtils.drawRoundedRect(x+92+offsetX, y+222+offset+scrollOffset, x+127+offsetX, y+237+offset+scrollOffset, 8, new Color(66, 66, 66).getRGB(), true);
	                    RenderUtils.drawRoundedRect(x+92+offsetX, y+222+offset+scrollOffset, x+107+offsetX+m.toggleAnimation.getValue(), y+237+offset+scrollOffset, 8, secondaryFontColor.getRGB(), true);
	                    RenderUtils.drawRoundedRect(x+93+offsetX+m.toggleAnimation.getValue(), y+222+offset+scrollOffset, x+108+offsetX+m.toggleAnimation.getValue(), y+237+offset+scrollOffset, 8, new Color(180, 180, 180).getRGB(), true);
	                    RenderUtils.drawRoundedRect(x+92+offsetX+m.toggleAnimation.getValue(), y+222+offset+scrollOffset, x+107+offsetX+m.toggleAnimation.getValue(), y+237+offset+scrollOffset, 8, -1, true);
	                    
	                    GlUtils.startScale(x+92+offsetX, y+180+offset+scrollOffset, 1);
	                    fr.drawString(m.getName(), x+92+offsetX, y+180+offset+scrollOffset, -1, false);
	                    GlStateManager.popMatrix();
	                    GlUtils.startScale(x+92+offsetX, y+190+offset+scrollOffset, 0.7f);
	                    if (!m.getDescription().startsWith("No des")) {
	                    	String description0 = (m.getDescription() + "                                                             ").substring(0, 21);
	                    	String description1 = (m.getDescription() + "                                                             ").substring(21, 41);
	                    	String description2 = (m.getDescription() + "                                                             ").substring(41, 61);
	                    	fr.drawString(description0, x+92+offsetX, y+200+offset+scrollOffset, -1, false);
	                    	fr.drawString(description1, x+92+offsetX, y+210+offset+scrollOffset, -1, false);
	                    	fr.drawString(description2, x+92+offsetX, y+220+offset+scrollOffset, -1, false);
	                    }
	                    GlStateManager.popMatrix();
	                }
            		numElementsX++;
            		offsetX += 100;
            		if (numElementsX % numElements == 0) {
            			offset += 130;
            			offsetX = 0;
            		}
	               
            	}
            }
            
        }
        }else if(part == "Setting") {
        	fr.drawString("Not sure what to put here yet. DM me with suggestions! My discord is hooman#1196.", x+100, y+120, -1, false);
        }

        /* !------------- SETTINGS ----------------! */

        if (selectedMod != null) {
            RenderUtils.drawRoundedRect(x+80, y+105, x+width-20, height+30, 16, secondaryColor);
            fr.drawString("<", x+90, y+115+offset, -1, false);

            for (Comp comp : comps) {
                comp.drawScreen(mouseX, mouseY);
            }
        }

        if (closing) {
            comps.clear();
            if (introAnimation == null) {
                mc.displayGuiScreen(null);
                return;
            }
            
            introAnimation.setDirection(Direction.BACKWARDS);
            bgDimAnim.setDirection(Direction.BACKWARDS);
            if (introAnimation.isDone(Direction.BACKWARDS)) {
                mc.displayGuiScreen(null);
            }
        }
        
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseInside(mouseX, mouseY, x+20, (int) y+120, x+40, (int) y+140)) {
            setting = false;
            home = true;
            part = "Home";
            playPressSound();
        } else if (isMouseInside(mouseX, mouseY, x+20, (int) y+170, x+40, (int) y+190)) {
            home = false;
            setting = true;
            part = "Setting";
            playPressSound();
        } else if (isMouseInside(mouseX, mouseY, x+14, y+214, x+46, y+246)) {
        	comps.clear();
        	mc.displayGuiScreen(new HUDConfigScreen(this));
        	playPressSound();
        }

        if (isMouseInside(mouseX, mouseY, x+width-300, y+25, x+width-50, y+65))
            isSearchFocused = true; else isSearchFocused = false;
        
        if (isMouseInside(mouseX, mouseY, x + 85, y+95, x + 130, y+115) && mouseButton == 0) {
        	playPressSound();
        	selectedCategory = null;
        }
        if (isMouseInside(mouseX, mouseY, x + 135, y+95, x + 180, y+115)) {
        	playPressSound();
        	selectedCategory = Category.HUD;
        }
        if (isMouseInside(mouseX, mouseY, x + 185, y+95, x + 230, y+115)) {
        	playPressSound();
        	selectedCategory = Category.MISC;
        }
        
        if (isMouseInside(mouseX, mouseY, (x+width)-70, y+90, (x+width)-40, y+120) && mouseButton == 0) {
        	playPressSound();
        	if(currentView == "normalView"){
                currentView = "gridView";
            }else if(currentView == "gridView"){
                currentView = "normalView";
            }
        }

        int offset = 0;
        int offsetX = 0;
        int numElements = (int) Math.floor(width/90) - 2;
        int numElementsX = 0;
        for (Mod m : Resent.INSTANCE.modManager.modsInCategory(selectedCategory)) {
            if (!m.isAdmin() && m.getName().toLowerCase().startsWith(searchString.toLowerCase()) && selectedMod == null) {
                if (currentView == "normalView") {
                    if (y+125+offset+scrollOffset > y+95 && y+175+offset+scrollOffset < y+height && part == "Home") {
                    if (isMouseInside(mouseX, mouseY, x+width-70, y+140+offset+scrollOffset, x+width-50, y+140+offset+scrollOffset+20) && mouseButton == 0 && m.doesHaveSetting()) {
                        selectedMod = m;
                        playPressSound();
                        drawSetting();
                    }

                    if (isMouseInside(mouseX, mouseY, x+80, y+125+offset+scrollOffset, x+width-20, y+175+offset+scrollOffset)) {
                        if (mouseButton == 1 && m.doesHaveSetting()) {
                            selectedMod = m;
                            playPressSound();
                            drawSetting();
                        }

                        if (mouseButton == 0 && selectedMod == null) {
                            m.toggle();
                            playPressSound();
                        }
                    }
                }
                    offset += 60;
                } else if(currentView == "gridView"){
                        if (y+125+offset+scrollOffset > y+95 && y+240+offset+scrollOffset < y+height) {
                		if (isMouseInside(mouseX, mouseY, x+140+offsetX, (int) y+220+offset+scrollOffset, x+160+offsetX, (int) y+240+offset+scrollOffset) && mouseButton == 0 && m.doesHaveSetting()) {
	                        selectedMod = m;
	                        playPressSound();
	                        drawSetting();
	                    }
	
	                    if (isMouseInside(mouseX, mouseY, x+85+offsetX, y+125+offset+scrollOffset, x+175+offsetX, y+250+offset+scrollOffset)) {
                            if (mouseButton == 1 && m.doesHaveSetting()) {
                                selectedMod = m;
                                playPressSound();
                                drawSetting();
                            }
    
                            if (mouseButton == 0 && selectedMod == null) {
                                m.toggle();
                                playPressSound();
                            }
	                    }
                	}

                    numElementsX++;
                    offsetX += 100;
                    if (numElementsX % numElements == 0) {
                        offset += 130;
                        offsetX = 0;
                    }
                }
            }
        }

        if (selectedMod != null) {
            if (isMouseInside(mouseX, mouseY, x+87, y+112, x+97, y+125)) {
            	playPressSound();
                selectedMod = null;
                comps.clear();
            }

            for (Comp c : comps) {
                c.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void initGui() {
    	this.mc.gameSettings.loadOptions();
        sr = new ScaledResolution(mc);
        x = sr.getScaledWidth() / 10;
        y = sr.getScaledHeight() / 10;
        width = sr.getScaledWidth() / 1.25F;
        height = sr.getScaledHeight() / 1.25F;
        //introAnimation = Theme.getAnimation(500, 255, 3, 3.8F, 1.35F, false);
        introAnimation = new EaseInOutQuad(500, height + y);
        searchCursorAnim = new EaseInOutQuad(300, 255);
        fr = mc.fontRendererObj;
        partAnimation = new SimpleAnimation(0.0F);
        bgDimAnim = new EaseInOutQuad(500, 150);
    }

    @Override
    protected void keyTyped(char par1, int key) {
        if (key == 0x01 || key == Minecraft.getMinecraft().gameSettings.keyBindClickGui.keyCode) {
            closing = true;
        }

        if (selectedMod != null) {
            for (Comp c : comps) {
                c.keyTyped(par1, key);
            }
        }

        // Search box stuff
        if (key == KeyboardConstants.KEY_BACK && isSearchFocused) {
            if (searchString.length() != 0) {
                searchString = searchString.substring(0, searchString.length()-1);
            }
        } else if (searchString.length() <= 18 && isSearchFocused) {
            String balls = ChatAllowedCharacters.filterAllowedCharacters(String.valueOf(par1));
            if (balls != "") {
                searchString += String.valueOf(par1);
                scrollOffset = 0;
            }
        }
    }

    public void handleMouseInput() throws IOException {
        int scroll = Mouse.getEventDWheel();

        if (scroll > 0) {
            scrollOffset = MathHelper.clamp_int(scrollOffset+60, getMaxScroll(), 0);
        } else if (scroll < 0) {
            scrollOffset = MathHelper.clamp_int(scrollOffset-60, getMaxScroll(), 0);
        }
        super.handleMouseInput();
    }

    public int getMaxScroll() {
        return Resent.INSTANCE.modManager.modsInCategory(selectedCategory).size() * -53;
    }

    @Override
    public void onGuiClosed() {
        this.mc.gameSettings.saveOptions();
    }
    
    public void drawSetting() {
        int settingYOffset = 0;
        int settingXOffset = 0;
        int modeSettingYOffset = 0;
        for (Setting s : selectedMod.settings) {
            if (s instanceof BooleanSetting) {
                comps.add(new CompCheck(x+110+settingXOffset, y+125+settingYOffset, selectedMod, s));
            }
            if(s instanceof ModeSetting) {
            	settingXOffset -= fr.getStringWidth(s.name)+50;
            	comps.add(new CompMode(x+90, y+200+settingYOffset+modeSettingYOffset, width, selectedMod, s));
            	modeSettingYOffset += 50;
            }
            if(s instanceof NumberSetting) {
            	comps.add(new CompNumber(x+135, y+125+settingYOffset, selectedMod, s));
            }
            if(s instanceof CustomRectSettingDraw) {
            	settingYOffset += 25;
            	settingXOffset = 0;
            	comps.add(new CompCustom(x+110+settingXOffset, y+125+settingYOffset, selectedMod, s.name, s));
            }
            
            if(x+155+settingXOffset+fr.getStringWidth(s.name) > x+width-20-fr.getStringWidth(s.name)*2) {
            	settingXOffset = 0;
            	settingYOffset += 25;
            }else {
            	settingXOffset += fr.getStringWidth(s.name)+50;
            }
        }
    }

	@Override
	protected void mouseReleased(int i, int j, int k) {
		if (selectedMod != null) {
            for (Comp c : comps) {
                c.mouseReleased(i, j, k);
            }
        }
	}
	
	public void playPressSound() {
        this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }
}
