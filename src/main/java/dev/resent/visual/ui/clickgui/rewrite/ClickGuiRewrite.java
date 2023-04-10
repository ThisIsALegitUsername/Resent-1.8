package dev.resent.visual.ui.clickgui.rewrite;

import dev.resent.client.Resent;
import dev.resent.module.base.Mod;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import dev.resent.module.base.setting.ModeSetting;
import dev.resent.module.base.setting.NumberSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.Theme;
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

import java.io.IOException;
import java.util.ArrayList;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
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
    public String part = "Home";
    public Animation bgDimAnim;
    public Animation searchCursorAnim;
    @Override
    public void drawScreen(int mouseX, int mouseY, float var3) {
        //GlStateManager.scale(1f,1f,0f);
    	
    	
        int offset = 0;
        // background dim
        drawRect(0, 0, /*used big number bc width and height not working for some reason */ 999999, 999999, new Color(0, 0, 0, (int) bgDimAnim.getValue()).getRGB());
        
        GlUtils.startScale(x+width/2, y+height/2, 1);
        
        GlStateManager.translate(0, (height + y) - introAnimation.getValue(), 0);
        /* !-------------- NECESSARY ELEMENTS -----------------! */

        //Navigation bar
        RenderUtils.drawRoundedRect(x, y, x+width-60, y+height, 32, secondaryColor);

        //Background overlay
        RenderUtils.drawRoundedRect(x+60, y, x+width, y+height, 32, backgroundColor);
        Gui.drawRect(x+60, y, x+102, y+height, backgroundColor);

        //Separating line
        Gui.drawRect(x, y+90, x+width, y+95, secondaryColor);

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
            		new Color(secondaryFontColor.getRed(), secondaryFontColor.getGreen(), secondaryFontColor.getBlue(), (int) searchCursorAnim.getValue()).getRGB());
        }
        
        if (searchCursorAnim.isDone()) {
        	searchCursorAnim.changeDirection();
        }

        GlStateManager.popMatrix();

        /* !------------- HOME/MODULE (SOON) --------------------! */
        //Draw module button
        if(part == "Home") {
        for (Mod m : Resent.INSTANCE.modManager.modules) {
            if (!m.isAdmin() && m.getName().toLowerCase().startsWith(searchString.toLowerCase()) && selectedMod == null) {
                if (y+125+offset+scrollOffset > y+95 && y+155+offset+scrollOffset < y+height) {
                    //Body
                    RenderUtils.drawRoundedRect(x+80, y+125+offset+scrollOffset, x+width-20, y+155+offset+scrollOffset, 16, secondaryColor);

                    //Gear
                    if (m.doesHaveSetting()) {
                        GlStateManager.color(1, 1, 1);
                        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/gear2.png"));
                        Gui.drawModalRectWithCustomSizedTexture(x+width-60, (int) y+120+offset+scrollOffset, 0, 0, 20, 20, 20, 20);
                    }

                    //Toggle
                    RenderUtils.drawRoundedRect(x+100, y+135+offset+scrollOffset, x+110, y+145+offset+scrollOffset, 8, m.isEnabled() ? onSurfaceColor : new Color(66, 66, 66).getRGB());

                    GlUtils.startScale(x+90, y+140+offset+scrollOffset, 2);
                    int i = fr.drawString(m.getName(), x+120, y+140+offset+scrollOffset, -1, false);
                    GlStateManager.popMatrix();
                    GlUtils.startScale(x+120+i / 2, y+120+offset+scrollOffset, 1.5f);
                    fr.drawString(m.getDescription(), x+20+i, y+122+offset+scrollOffset, -1, false);
                    GlStateManager.popMatrix();
                    //                    if (isMouseInside(mouseX, mouseY, x+i+80, y+115+offset+scrollOffset, x+width-20, y+185+offset+scrollOffset)) {
                    //                        fr.drawString(m.getDescription(), mousex+8, mouseY, onSurfaceColor, false);
                    //                    }
                }
                offset += 60;
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
        } else if (isMouseInside(mouseX, mouseY, x+20, (int) y+170, x+40, (int) y+190)) {
            home = false;
            setting = true;
            part = "Setting";
        } else if (isMouseInside(mouseX, mouseY, x+14, y+214, x+46, y+246)) {
        	comps.clear();
        	mc.displayGuiScreen(new HUDConfigScreen(this));
        }

        if (isMouseInside(mouseX, mouseY, x+width-300, y+25, x+width-50, y+65)) {
            isSearchFocused = true;
        } else {
            isSearchFocused = false;
        }

        int offset = 0;

        for (Mod m : Resent.INSTANCE.modManager.modules) {
            if (!m.isAdmin() && m.getName().toLowerCase().startsWith(searchString.toLowerCase()) && selectedMod == null) {
                if (y+125+offset+scrollOffset > y+95 && y+155+offset+scrollOffset < y+height && part == "Home") {
                    if (isMouseInside(mouseX, mouseY, x+width-60, y+140+offset+scrollOffset, x+width-40, y+120+offset+scrollOffset) && mouseButton == 0 && m.doesHaveSetting()) {
                        selectedMod = m;

                        drawSetting();
                    }

                    if (isMouseInside(mouseX, mouseY, x+80, y+125+offset+scrollOffset, x+width-20, y+155+offset+scrollOffset)) {
                        if (mouseButton == 1 && m.doesHaveSetting()) {
                            selectedMod = m;

                            drawSetting();
                        }

                        if (mouseButton == 0 && selectedMod == null) {
                            m.toggle();
                        }
                    }
                }
                offset += 60;
            }
        }

        if (selectedMod != null) {
            if (isMouseInside(mouseX, mouseY, x+87, y+112, x+97, y+125)) {
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
        sr = new ScaledResolution(mc);
        x = sr.getScaledWidth() / 10;
        y = sr.getScaledHeight() / 10;
        width = sr.getScaledWidth() / 1.25F;
        height = sr.getScaledHeight() / 1.25F;
        introAnimation = new EaseInOutQuad(500, height + y);
        bgDimAnim = new EaseInOutQuad(500, 150);
        searchCursorAnim = new EaseInOutQuad(300, 255);
        fr = mc.fontRendererObj;
        partAnimation = new SimpleAnimation(0.0F);
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
        return Resent.INSTANCE.modManager.modules.size() * -53;
    }

    @Override
    public void onGuiClosed() {
        mc.gameSettings.saveOptions();
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
}
