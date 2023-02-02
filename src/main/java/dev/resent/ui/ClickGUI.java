package dev.resent.ui;

import java.io.IOException;

import dev.resent.Resent;
import dev.resent.animation.Animation;
import dev.resent.animation.Direction;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;
import dev.resent.module.setting.Setting;
import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends GuiScreen {

    public Animation introAnimation;
    public Mod modWatching = null;
    public ScaledResolution sr;
    public int x, y, width, height;
    public int offset = 0;
    public FontRenderer fr;
    public boolean close = false;

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
        int xo = 0;
        int xy = -30;

        sr = new ScaledResolution(mc);
        fr = Minecraft.getMinecraft().fontRendererObj;
        width = GuiScreen.width - x;
        height = GuiScreen.height - y;
        x = sr.getScaledWidth() / 8 + xo;
        y = sr.getScaledHeight() - 10 + xy;
        int off = 0;

        for (Mod m : Resent.INSTANCE.modManager.modules) {
            int fh = fr.FONT_HEIGHT;

            if (isMouseInside(mouseX, mouseY, this.x + 90 + xo - 1 + 10, height - 2 - fh * -(off) + 51 - 1 - offset, this.x + 90 + xo - 1 + 21, height + 30 - fh * (-off) + 30 - 1 + 2 - 1 - offset) && m.hasSetting && modWatching == null) {
                // Open settings
                this.modWatching = m;
            } else if (isMouseInside(mouseX, mouseY, x - fr.FONT_HEIGHT + 2, height + 27 + fr.FONT_HEIGHT + 2, x - fr.FONT_HEIGHT + 6 + fr.getStringWidth("<"), height + 33 + fr.FONT_HEIGHT + 2 + fr.getStringWidth("<")) && mouseButton == 0) {
                // Close settings
                this.modWatching = null;
            } else if (isMouseInside(mouseX, mouseY, width + 15, height - 10, width + 25, height + 7)) {
                // Close ui
                mc.displayGuiScreen(null);
                this.modWatching = null;
            } else if (isMouseInside(mouseX, mouseY, this.x + 10 + xo - 2 + 10, height - 2 - fh * -(off) + 50 - 2 - offset, this.x + 90 + xo + 22, height + 30 - fh * (-off) + 30 + 2 - offset) && mouseButton == 0 && modWatching == null) {
                // Toggle mod
                m.toggle();
            } else if (isMouseInside(mouseX, mouseY, GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 - 5, GuiScreen.height - y - fr.FONT_HEIGHT, GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout"), GuiScreen.height - y + 5) && mouseButton == 0) {
                mc.displayGuiScreen(new HUDConfigScreen());
                this.modWatching = null;
            }
            if (xo > width / 2) {
                xo = 0;
                off += 3;
            } else {
                xo += 100;
            }
        }

        if (modWatching != null) {
            int var = 0;
            fr.drawString("<", x - fr.FONT_HEIGHT + 4, height + 29 + fr.FONT_HEIGHT + 2, -1);
            for (int asdf = 0; asdf < this.modWatching.settings.size(); asdf++) {
                BooleanSetting b;
                ModeSetting m;
                Setting s = this.modWatching.settings.get(asdf);

                if (s instanceof BooleanSetting) {
                    b = (BooleanSetting) s;
                    if (isMouseInside(mouseX, mouseY, this.x + 6 + 1 + 6, height - fr.FONT_HEIGHT + 50 - offset + var + 1, this.x + 15 - 1 + 6, height - fr.FONT_HEIGHT + 50 + fr.FONT_HEIGHT - offset + var - 1) && mouseButton == 0) {
                        b.toggle();
                    }
                }

                if (s instanceof ModeSetting) {
                    m = (ModeSetting) s;
                    if (isMouseInside(mouseX, mouseY, this.x + 24, height - fr.FONT_HEIGHT + 50 + var, this.x + 24 + fr.getStringWidth(s.name + ": " + m.getValue()), height - fr.FONT_HEIGHT + 50 + var + fr.FONT_HEIGHT) && mouseButton == 0) m.next();
                }

                var += fr.FONT_HEIGHT + 2;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        sr = new ScaledResolution(mc);
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
        int xo = 0;
        int xy = -30;

        fr = Minecraft.getMinecraft().fontRendererObj;
        width = GuiScreen.width - x;
        height = GuiScreen.height - y;
        x = sr.getScaledWidth() / 8 + xo;
        y = sr.getScaledHeight() - 10 + xy;
        int off = 0;

        if(close) {
			introAnimation.setDirection(Direction.BACKWARDS);
			if(introAnimation.isDone(Direction.BACKWARDS)) {
				mc.displayGuiScreen(null);
			}
		}
        
        GlUtils.startScale((this.x + this.width)/2, (this.y + this.height) / 2, (float) introAnimation.getValue());

        // background
        drawRect(x - 10, y + 20, width + 35, height - 10, new Color(35, 39, 42, 200).getRGB());
        fr.drawString(Resent.NAME + " Client " + Resent.VERSION, x + 8, height - 2, -1);
        RenderUtils.drawRectOutline(GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 - 5, GuiScreen.height - y - fr.FONT_HEIGHT, GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout"), GuiScreen.height - y + 5, -1);
        drawRect(
            GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 - 4,
            GuiScreen.height - y - fr.FONT_HEIGHT + 1,
            GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout") - 1,
            GuiScreen.height - y + 4,
            isMouseInside(mouseX, mouseY, GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 - 4, GuiScreen.height - y - fr.FONT_HEIGHT + 1, GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 + 5 + fr.getStringWidth("Edit Layout") - 1, GuiScreen.height - y + 4) ? new Color(105, 105, 105, 65).getRGB() : new Color(211, 211, 211, 65).getRGB()
        );

        fr.drawStringWithShadow("Edit Layout", GuiScreen.width / 2 - fr.getStringWidth("Edit Layout") / 2 + 1, GuiScreen.height - y - fr.FONT_HEIGHT + fr.FONT_HEIGHT / 2 - 1, -1);

        // close
        // RenderUtils.drawRectOutline(width+15, height-5, width+26, height+8, new Color(200, 200, 200, 90).getRGB());
        fr.drawString("X", width + 18, height - 2, -1);

        // white line
        drawRect(x - 8, height + 29, width + 33, height + 30, -1);
        GlUtils.stopScale();
        for (Mod m : Resent.INSTANCE.modManager.modules) {
            if (this.modWatching == null) {
                int fh = fr.FONT_HEIGHT;
                if (height - 2 - fh * -(off) + 50 - 2 - offset > height + 29 && height + 30 - fh * (-off) + 30 + 2 - offset < y + 20 && introAnimation.isDone()) {
                    // Enabled outline
                    RenderUtils.drawRectOutline(this.x + 10 + xo - 2 + 10, height - 2 - fh * -(off) + 50 - 2 - offset, this.x + 90 + xo + 22, height + 30 - fh * (-off) + 30 + 2 - offset, m.isEnabled() ? Color.GREEN.getRGB() : Color.RED.getRGB());
                    drawRect(
                        this.x + 10 + xo - 1 + 10,
                        height - 2 - fh * -(off) + 50 - 1 - offset,
                        this.x + 90 + xo - 1 + 22,
                        height + 30 - fh * (-off) + 30 - 1 + 2 - offset,
                        isMouseInside(mouseX, mouseY, this.x + 10 + xo - 1 + 10, height - 2 - fh * -(off) + 50 - 1 - offset, this.x + 90 + xo - 1 + 22, height + 30 - fh * (-off) + 30 - 1 + 2 - offset) ? new Color(105, 105, 105, 65).getRGB() : new Color(211, 211, 211, 65).getRGB()
                    );

                    if (m.hasSetting) {
                        //fr.drawString("o", this.x+99+xo, height - 2 - fh * -(off) + 51 + 1 - offset, isMouseInside(mouseX, mouseY, this.x + 90 + xo - 1 + 10, height - 2 - fh * -(off) + 51 + 1 - offset, this.x + 90 + xo - 1 + 10 + fr.getStringWidth("o"), height - 2 - fh * -(off) + 51 + 1 - offset + fr.FONT_HEIGHT) ? new Color(105, 105, 105, 65).getRGB() : -1);
                        GlStateManager.enableBlend();
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 255f);
                        //GlStateManager.tryBlendFuncSeparate(GL_ONE_MINUS_DST_COLOR, GL_ONE_MINUS_SRC_COLOR, 1, 0);
                        if(isMouseInside(mouseX, mouseY, this.x + 90 + xo - 1 + 10, height - 2 - fh * -(off) + 51 + 1 - offset, this.x + 90 + xo - 1 + 10 + fr.getStringWidth("o"), height - 2 - fh * -(off) + 51 + 1 - offset + fr.FONT_HEIGHT))
                        GlStateManager.color(1,1,1,0.5f);
                        this.mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/gear.png"));
                        Gui.drawModalRectWithCustomSizedTexture(this.x+99+xo, height - 2 - fh * -(off) + 51 + 1 - offset, 0, 0, 8, 8, 8, 8);
                        GlStateManager.disableBlend();
                        //RenderUtils.drawRectOutline(this.x+90+xo-1+10, height-2-fh*-(off)+51+1-offset, this.x+90+xo-1+10+fr.getStringWidth("o"), height-2-fh*-(off)+51+1-offset+fr.FONT_HEIGHT, -1);
                        //fr.drawString("+", this.x + 90 + xo - 1 + 10, height - 2 - fh * -(off) + 51 + 1 - offset, -1);
                        //fr.drawString(" x", this.x + 90 + xo - 1 + 10, height - 2 - fh * -(off) + 51 + 1 - offset, -1);
                        // drawRect(this.x+90+xo-1+10, height-2-fh*-(off)+51-1-offset,
                        // this.x+90+xo-1+21, height+30-fh*(-off)+30-1+2-1-offset, -1);
                    }

                    fr.drawStringWithShadow(m.name, this.x + 15 + 7 + xo, height - fh * -(off) + 50 - offset, -1);
                }
            } else if (this.modWatching != null) {
                int var = 0;
                fr.drawString("<", x - fr.FONT_HEIGHT + 4, height + 29 + fr.FONT_HEIGHT + 2, -1);
                fr.drawStringWithShadow("Resent - " + modWatching.name, GuiScreen.width / 2 - (fr.getStringWidth("Resent - " + modWatching.name) / 2), height + 29 - fr.FONT_HEIGHT - 2, -1);

                for (int amogus = 0; amogus < this.modWatching.settings.size(); amogus++) {
                    BooleanSetting b;
                    ModeSetting mo = null;
                    Setting s = this.modWatching.settings.get(amogus);
                    if (s instanceof BooleanSetting) {
                        b = (BooleanSetting) s;
                        RenderUtils.drawRectOutline(this.x + 6 + 6, height - fr.FONT_HEIGHT + 50 + var, this.x + 15 + 6, height - fr.FONT_HEIGHT + 50 + fr.FONT_HEIGHT + var, b.getValue() ? Color.GREEN.getRGB() : Color.RED.getRGB());
                        drawRect(
                            this.x + 6 + 1 + 6,
                            height - fr.FONT_HEIGHT + 50 + var + 1,
                            this.x + 15 - 1 + 6,
                            height - fr.FONT_HEIGHT + 50 + fr.FONT_HEIGHT + var - 1,
                            isMouseInside(mouseX, mouseY, this.x + 6 + 1 + 6, height - fr.FONT_HEIGHT + 50 + var + 1, this.x + 15 - 1 + 6, height - fr.FONT_HEIGHT + 50 + fr.FONT_HEIGHT + var - 1) ? new Color(211, 211, 211, 65).getRGB() : new Color(105, 105, 105, 65).getRGB()
                        );
                    }

                    if (s instanceof ModeSetting) {
                        mo = (ModeSetting) s;
                        //RenderUtils.drawRectOutline(this.x+18+6-2, height-fr.FONT_HEIGHT+50+var, this.x+18+6+fr.getStringWidth(this.modWatching.settings.get(amogus).name + ": " + m.getValue() + 2), height-fr.FONT_HEIGHT+50+var+fr.FONT_HEIGHT, -1);
                    }

                    if (s instanceof ModeSetting) {
                        /*RenderUtils.drawRectOutline(this.x+24-fr.FONT_HEIGHT-2,
                               this.height-fr.FONT_HEIGHT+50+var, this.x+24,
                               this.height-fr.FONT_HEIGHT+50+var+fr.FONT_HEIGHT, -1);
                       RenderUtils.drawRectOutline(
                               this.x + 24,
                               this.height - fr.FONT_HEIGHT + 50 + var,
                               this.x + 24
                                       + fr.getStringWidth(
                                               s.name + ": " + m.getValue())
                                       + 2,
                               this.height - fr.FONT_HEIGHT + 50 + var + fr.FONT_HEIGHT, -1);*/
                        //RenderUtils.drawRectOutline(this.x+24, height-fr.FONT_HEIGHT+50+var, this.x+24+fr.getStringWidth(s.name+": "+m.getValue()), height-fr.FONT_HEIGHT+50+var+fr.FONT_HEIGHT, -1);
                        fr.drawStringWithShadow(s.name + ": " + mo.getValue(), this.x + 18 + 6, height - fr.FONT_HEIGHT + 50 + var, -1);
                    } else {
                        fr.drawStringWithShadow(s.name, this.x + 18 + 6, height - fr.FONT_HEIGHT + 50 + var, -1);
                    }

                    var += fr.FONT_HEIGHT + 2;
                }
                // RenderUtils.drawRectOutline(x-fr.FONT_HEIGHT+2,
                // height+27+fr.FONT_HEIGHT+2,x-fr.FONT_HEIGHT+6+fr.getStringWidth("<"),
                // height+33+fr.FONT_HEIGHT+2+fr.getStringWidth("<"), new Color(200, 200,
                // 200,90).getRGB());
            }

            if (xo > width / 2) {
                xo = 0;
                off += 3;
            } else {
                xo += 100;
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int width, int height) {
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
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                    .start();
            } else if (wheel > 0) {
                new Thread(() -> {
                    for (int i = 0; i < 20; i++) {
                        offset = MathHelper.clamp_int(offset - 1, 0, getListMaxScroll());
                        try {
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
        return 60 + 70 - this.height;
    }
}
