package dev.resent.visual.ui;

import java.io.IOException;
import java.util.Comparator;

import dev.resent.client.ClientInfo;
import dev.resent.client.Resent;
import dev.resent.module.base.Mod;
import dev.resent.module.base.ModManager;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import dev.resent.module.base.setting.ModeSetting;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.animation.Animation;
import dev.resent.visual.ui.animation.Direction;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends GuiScreen {

    public Animation introAnimation;
    public Mod openedMod = null;
    public ScaledResolution sr;
    public int x, y, width, height;
    public int offset = 0;
    public FontRenderer fr;
    public boolean close = false;
    public Category selectedCategory = null;

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
        int xo = 0;
        int off = 0;
        
        if(isMouseInside(mouseX, mouseY, x, height+14, x+20, height+25)) {
        	selectedCategory = null;
        }else if(isMouseInside(mouseX, mouseY, x+24, height+14, x+46, height+25)) {
        	selectedCategory = Category.HUD;
        }else if(isMouseInside(mouseX, mouseY, x+50, height+14, x+74, height+25)) {
        	selectedCategory = Category.MISC;
        }
        
        if (isMouseInside(mouseX, mouseY, sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 - 5, sr.getScaledHeight() - y - 9, sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout"), sr.getScaledHeight() - y + 5) && mouseButton == 0) {
            mc.displayGuiScreen(new HUDConfigScreen());
            this.openedMod = null;
        }

        Resent.INSTANCE.modManager.modsInCategory(selectedCategory).sort(Comparator.comparingInt(m -> fr.getStringWidth(m.getName())));
        for (Mod m : Resent.INSTANCE.modManager.modsInCategory(selectedCategory) ) {
            int fh = 9;

            if (isMouseInside(mouseX, mouseY, this.x + 90 + xo - 1 + 10, height - 2 - fh * -(off) + 51 - 1 - offset, this.x + 90 + xo - 1 + 21, height + 30 - fh * (-off) + 30 - 1 + 2 - 1 - offset) && m.isHasSetting() && openedMod == null) {
                // Open settings
                this.openedMod = m;
            } else if (isMouseInside(mouseX, mouseY, x - 9 + 2, height + 27 + 9 + 2, x - 9 + 6 + fr.getStringWidth("<"), height + 33 + 9 + 2 + fr.getStringWidth("<")) && mouseButton == 0) {
                // Close settings
                this.openedMod = null;
            } else if (isMouseInside(mouseX, mouseY, width + 15, height - 10, width + 25, height + 7)) {
                // Close ui
                mc.displayGuiScreen(null);
                this.openedMod = null;
            }else if(isMouseInside(mouseX, mouseY, this.x+48+xo, height-2-fh*-(off)+70-1-offset, this.x+80+xo, height+30-fh*-off+30+2-offset+17) && ModManager.clickGui.guiTheme.getValue() == "New" && openedMod == null){
                //toggle new
            	m.toggle();
            } else if (isMouseInside(mouseX, mouseY, this.x + 10 + xo - 2 + 10, height - 2 - fh * -(off) + 50 - 2 - offset, this.x + 90 + xo + 22, height + 30 - fh * (-off) + 30 + 2 - offset) && mouseButton == 0 && openedMod == null && ModManager.clickGui.guiTheme.getValue() == "Classic revised") {
                //toggle classic
                m.toggle();
            }
            if (xo > width / 2) {
                xo = 0;
                off += 5;
            } else {
                xo += 100;
            }
        }

        if (openedMod != null) {
            int var = 0;
            for (Setting s : this.openedMod.settings) {

                if (s instanceof BooleanSetting) {
                    if (isMouseInside(mouseX, mouseY, this.x + 13, height - 9 + 50 - offset + var + 1, this.x + 20, height - 9 + 50 + 9 - offset + var - 1) && mouseButton == 0) {
                        ((BooleanSetting)s).toggle();
                    }
                }

                if (s instanceof ModeSetting) {
                    if(isMouseInside(mouseX, mouseY, this.x+360, height+40+var, this.x+370, height+50+var) && mouseButton == 0) {
                    	((ModeSetting)s).cycle(false);
                    }
                    if(isMouseInside(mouseX, mouseY, this.x+458, height+40+var, this.x+470, height+50+var) && mouseButton == 0) {
                        ((ModeSetting)s).cycle(true);
                    }
                }

                if(s instanceof CustomRectSettingDraw){
                    if(isMouseInside(mouseX, mouseY, x+21, height+41+var, x+27+fr.getStringWidth(s.name), height+var+53)){
                        ((CustomRectSettingDraw)s).onChange();
                    }
                }

                var += 9 + 2;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float par3) {
        sr = new ScaledResolution(mc);
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
        int xo = 0;

        fr = Minecraft.getMinecraft().fontRendererObj;
        width = sr.getScaledWidth() - x;
        height = sr.getScaledHeight() - y;
        x = sr.getScaledWidth() / 8 + xo;
        y = sr.getScaledHeight() - 40;
        int off = 0;

        if (close) {
        	if(introAnimation == null) {
        		mc.displayGuiScreen(null);
        		return;
        	}
        		
            introAnimation.setDirection(Direction.BACKWARDS);
            if (introAnimation.isDone(Direction.BACKWARDS)) {
                mc.displayGuiScreen(null);
            }
        }

        GlUtils.startScale((this.x + this.width) / 2, (this.y + this.height) / 2, introAnimation != null ? (float) introAnimation.getValue() : 1);

        // background
        drawRect(x - 10, y + 20, width + 35, height - 10, new Color(35, 39, 42, 200).getRGB());
        fr.drawString(ClientInfo.name + " Client " + ClientInfo.version, x + 8, height - 2, -1);
        
        //RenderUtils.drawRectOutline(sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 - 5, sr.getScaledHeight() - y - 9, sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout"), sr.getScaledHeight() - y + 5, -1);
       drawRect(
            sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 - 4,
            sr.getScaledHeight() - y - 9 + 1,
            sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout") - 1,
            sr.getScaledHeight() - y + 4,
            isMouseInside(mouseX, mouseY, sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 - 4, sr.getScaledHeight() - y - 9 + 1, sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout") - 1, sr.getScaledHeight() - y + 4) ? new Color(150, 150, 150, 65).getRGB() : new Color(211, 211, 211, 65).getRGB()
        );

        fr.drawStringWithShadow("Edit Layout", sr.getScaledWidth() / 2 - fr.getStringWidth("Edit Layout") / 2 + 1, sr.getScaledHeight() - y - 9 + 9 / 2 - 1, -1);

        // close
        fr.drawString("X", width + 18, height - 2, -1);
        
        //categories
        drawRect(x+0.8d, height+14, x+20, height+25, isMouseInside(mouseX, mouseY, x, height+14, x+20, height+25) ?  new Color(150, 150, 150, 65).getRGB() : new Color(211, 211, 211, 65).getRGB());
        drawRect(x+23d, height+14, x+47, height+25, isMouseInside(mouseX, mouseY, x+24, height+14, x+46, height+25) ?  new Color(150, 150, 150, 65).getRGB() : new Color(211, 211, 211, 65).getRGB());
        drawRect(x+50, height+14, x+75, height+25, isMouseInside(mouseX, mouseY, x+50, height+14, x+74, height+25) ?  new Color(150, 150, 150, 65).getRGB() : new Color(211, 211, 211, 65).getRGB());
        fr.drawStringWithShadow("All", x+5f, height+16, -1);
        fr.drawStringWithShadow("Hud", x+26.5f, height+16, -1);
        fr.drawStringWithShadow("Misc", x+53, height+16, -1);
        
        //white line
        drawRect(x - 8, height + 29, width + 33, height + 30, -1);
        GlUtils.stopScale();
        
        Resent.INSTANCE.modManager.modsInCategory(selectedCategory).sort(Comparator.comparingInt(m -> fr.getStringWidth(m.getName())));
        for (Mod m : Resent.INSTANCE.modManager.modsInCategory(selectedCategory)) {
            if (this.openedMod == null) {
                int fh = 9;
                if (height - 2 - fh * -(off) + 50 - 2 - offset > height + 29 && height + 40 - fh * (-off) + 30 +15 - offset < y + 20 && (introAnimation != null ? introAnimation.isDone() : true)) {
                    // Enabled outline
                	
                	m.toggleAnimation.setAnimation(m.isEnabled() ? 20 : 0, 5);
                    if(ModManager.clickGui.guiTheme.getValue() == "New"){
                        RenderUtils.drawRoundedRect(this.x+48+xo, height-2-fh*-(off)+70-1-offset, this.x+80+xo, height+30-fh*-off+30+2-offset+17, 6, new Color(97, 97, 97).getRGB(), true);
                        RenderUtils.drawRoundedRect(this.x+48+xo, height-2-fh*-(off)+70-1-offset, this.x+60+xo+m.toggleAnimation.getValue(), height+30-fh*-off+30+2-offset+17, 6, Color.green.getRGB(), true);
                        RenderUtils.drawRoundedRect(this.x+48+xo+m.toggleAnimation.getValue(), height-2-fh*-(off)+70-1-offset, this.x+60+xo+m.toggleAnimation.getValue(), height+30-fh*-off+30+2-offset+17, 6, -1, true);
                    }else if(ModManager.clickGui.guiTheme.getValue() == "Classic revised"){
                        RenderUtils.drawRoundedRect(this.x + 10 + xo - 2 + 10, height - 2 - fh * -(off) + 50 - 2 - offset, this.x + 90 + xo + 22, height + 30 - fh * (-off) + 30 + 2 - offset, 4, m.isEnabled() ? Color.GREEN.getRGB() : Color.RED.getRGB(), true);
                    }

                    if(ModManager.clickGui.guiTheme.getValue().equals("New")) {
	                    drawRect(
	                        this.x + 10 + xo - 1 + 10,
	                        height - 2 - fh * -(off) + 50 - 1 - offset,
	                        this.x + 90 + xo - 1 + 22,
	                        height + 85 - fh * (-off) - offset,
	                        /*isMouseInside(mouseX, mouseY, this.x + 10 + xo - 1 + 10, height - 2 - fh * -(off) + 50 - 1 - offset, this.x + 90 + xo - 1 + 22, height + 30 - fh * (-off) + 30 - 1 + 2 - offset) ? new Color(105, 105, 105, 65).getRGB() :*/ new Color(211, 211, 211, 65).getRGB()
	                    );
                    }

                    if (m.isHasSetting()) {
                        GlStateManager.enableBlend();
                        this.mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/gear.png"));
                        Gui.drawModalRectWithCustomSizedTexture(this.x + 99 + xo, height - 2 - fh * -(off) + 51 + 1 - offset, 0, 0, 8, 8, 8, 8);
                        GlStateManager.disableBlend();
                    }

                    fr.drawStringWithShadow(m.getName(), this.x + 24 + xo, height - fh * -(off) + 50 - offset, -1);
                }
            } else if (this.openedMod != null) {
                int var = 0;
                fr.drawString("<", x - 9 + 4, height + 29 + 9 + 2, -1);
                fr.drawStringWithShadow(ClientInfo.name + " - " + openedMod.getName(), sr.getScaledWidth() / 2 - (fr.getStringWidth("Resent - " + openedMod.getName()) / 2), height + 29 - 9 - 2, -1);

                for (int amogus = 0; amogus < this.openedMod.settings.size(); amogus++) {
                    Setting s = this.openedMod.settings.get(amogus);
                    if(s instanceof CustomRectSettingDraw){
                        drawRect(x+21, height+41+var, x+27+fr.getStringWidth(s.name), height+var+53, isMouseInside(mouseX, mouseY, x+21, height+39+var, x+26+fr.getStringWidth(s.name), height+var+51) ? new Color(150, 150, 150).getRGB() : new Color(211, 211, 211).getRGB());
                        //RenderUtils.drawRectOutline(x+21, height+41+var, x+27+fr.getStringWidth(s.name), height+var+53, -1);
                        fr.drawStringWithShadow(s.name, this.x + 24, height +43 + var, -1);
                        var += 3;
                    }
                    if (s instanceof BooleanSetting) {
                        drawRect(this.x + 11, height - 9 + 50 + var, this.x + 19, height - 9 + 50 + 9 + var - 1, isMouseInside(mouseX, mouseY, this.x + 11, height - 9 + 50 + var, this.x + 19, height - 9 + 50 + 9 + var - 1) ? new Color(211, 211, 211, 65).getRGB() : new Color(105, 105, 105, 65).getRGB());
                        fr.drawStringWithShadow(s.name, this.x + 18 + 6, height - fr.FONT_HEIGHT + 50 + var, -1);
                        if (((BooleanSetting)s).getValue()) {
                            mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/check.png"));
                            Gui.drawModalRectWithCustomSizedTexture(this.x + 9, height + 39 + var, 0, 0, 12, 12, 12, 12);
                        }
                    }
                    if (s instanceof ModeSetting) {
                        fr.drawStringWithShadow(s.name, this.x + 18 + 6, height - 9 + 50 + var, -1);
                        fr.drawStringWithShadow(((ModeSetting)s).getValue(), width-100-fr.getStringWidth(((ModeSetting)s).getValue())/2, height+41+var, -1);
                        fr.drawStringWithShadow(EnumChatFormatting.RED + "<", width - 150, height-9+50+var, -1);
                        //RenderUtils.drawRectOutline(this.x+370, height+39+var, this.x+360, height+50+var, -1);
                        fr.drawStringWithShadow(EnumChatFormatting.RED + ">", this.x+463, height-9+50+var, -1);
                        //RenderUtils.drawRectOutline(this.x+458, height+40+var, this.x+470, height+50+var, -1);
                    }

                    var += 11;
                }
            }

            if (xo > width / 2) {
                xo = 0;
                off += 5;
            } else {
                xo += 100;
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean isMouseInside(double mouseX, double mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(true);
        mc.gameSettings.saveOptions();
    }

    @Override
    public void initGui() {
        mc.gameSettings.loadOptions();
        introAnimation = Theme.getAnimation(Theme.getAnimationId(), 500, 1, 3, 3.8f, 1.35f, false);
    }

    protected void keyTyped(char par1, int par2) {
        if (par2 == 0x01 || par2 == Minecraft.getMinecraft().gameSettings.keyBindClickGui.keyCode) {
            close = true;
        }
    }

    @Override
    public void handleMouseInput() {
        if (getListMaxScroll() + this.height >= this.height) {
            int wheel = Mouse.getEventDWheel();
            if (wheel < 0) {
                new Thread(() -> {
                    for (int i = 0; i < 20; i++) {
                        offset = MathHelper.clamp_int(offset + 1, 0, getListMaxScroll());
                        try {
                            if(ModManager.clickGui.scroll.getValue())
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else if (wheel > 0) {
                new Thread(() -> {
                    for (int i = 0; i < 20; i++) {
                        offset = MathHelper.clamp_int(offset - 1, 0, getListMaxScroll());
                        try {
                            if(ModManager.clickGui.scroll.getValue())
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                    .start();
            }
            try {
                super.handleMouseInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
    }

    private int getListMaxScroll() {
        return 130 - this.height;
    }
}
