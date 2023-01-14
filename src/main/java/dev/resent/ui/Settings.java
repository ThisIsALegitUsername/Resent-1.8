package dev.resent.ui;
/*package dev.resent.ui.mods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dev.resent.setting.BooleanSetting;
import dev.resent.setting.Setting;
import dev.resent.util.misc.Keyboard;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
GlStateManager.popMatrix();
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ScaledResolution;

public class Settings extends GuiScreen {

    public ScaledResolution sr;

    public List<Setting> settings = new ArrayList<>(); //TODO: bind module class arrays with setting arrays
    public int x, y, width, height;
    public int offset = 0;
    public float offsety = 20;
    public FontRenderer fr;

    //Temporary test settings
    public static BooleanSetting rain = new BooleanSetting("Rain", "", false);
    public static BooleanSetting scoreboard = new BooleanSetting("Scoreboard", "", true);
    public static BooleanSetting nametag = new BooleanSetting("Self Nametags", "", true);

    public Settings() {
        addSetting(rain, scoreboard, nametag);
    }

    public void addSetting(Setting... setting) {
        for (Setting s : setting)
            this.settings.add(s);
    }
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int ox = 10;
        int oy = -30;
        sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        fr = Minecraft.getMinecraft().fontRenderer;
        width = GuiScreen.width-x;
        height = GuiScreen.height-y;
        x = sr.getScaledWidth()/8 + ox;
        y = sr.getScaledHeight()/(int)1.1-10+oy;
        int off = 0;


        for (int i = 0; i < settings.size(); i++) {

            //bolean setting
            if (mouseButton == 0 && isMouseInside(mouseX, mouseY, this.x+3+5, height-2-fr.FONT_HEIGHT*-(off), this.x+12+5, height-2-fr.FONT_HEIGHT*(-off)+fr.FONT_HEIGHT) && settings.get(i) instanceof BooleanSetting) {
                Setting s = settings.get(i);
                BooleanSetting b = (BooleanSetting) s;
                    b.setValue(!b.getValue());
            }
            
            off += 2;
        }

    }



@Override
public void drawScreen(int mouseX, int mouseY, float par3) {
    offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
    int xo = 10;
    int xy = -30;
    
        sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        fr = Minecraft.getMinecraft().fontRenderer;
        width = GuiScreen.width-x;
        height = GuiScreen.height-y;
        x = sr.getScaledWidth()/8+xo;
        y = sr.getScaledHeight()/(int)1.1-10+xy;
        int off = 0;

        //background
        RenderUtils.drawRoundedRect(x, y, width, height, 4, Color.DARK_GRAY.getRGB());

        //for each settings it will do this, i dont use i as offset because it made it weird idk
        for (int i = 0; i < settings.size(); i++) {

            Setting s = settings.get(i);
            int fh = fr.FONT_HEIGHT;

            //booleansetting

            //if its current module is a boolean setting
            if (settings.get(i) instanceof BooleanSetting) {
                BooleanSetting  b = (BooleanSetting) s;
                //Draw enabled outline
                RenderUtils.drawRectOutline(this.x+3+5, height-2-fh*-(off)-offset, this.x+12+5,
                        height-2-fh*(-off)+fh-offset,
                        b.getValue() ? Color.GREEN.getRGB() : Color.RED.getRGB());

                //Draw outline fill
                Gui.drawRect(this.x+4+5, height-1-fh*-(off)-offset, this.x+11+5,
                        height-3-fh*(-off)+fh-offset, isMouseInside(mouseX, mouseY, this.x+4+5,
                                height-1-fh*-(off)-offset, this.x+11+5,
                                height-3-fh*(-off)+fh-offset) ? Color.LIGHT_GRAY.getRGB() : Color.GRAY.getRGB());
            }
        

            //Sort settings based on length
            settings.sort(Comparator.comparingInt(set -> fr.getStringWidth(((Setting) set).name)).reversed());

            
            //Draw setting name
            fr.drawStringWithShadow(settings.get(i).name, this.x+15+5, height-2-fh*-(off)-offset,-1);    

            off += 2;
        }

    }

    public boolean doesGuiPauseGame() { return false; }
    public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int width, int height) { return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height); }
    public void onGuiClosed() { EaglerAdapter.enableRepeatEvents(false); mc.gameSettings.saveOptions(); }
    protected void keyTyped(char par1, int par2) { if (par2 == Keyboard.KEY_ESCAPE || par2 == Minecraft.getMinecraft().gameSettings.keyBindClickGui.keyCode) { mc.displayGuiScreen(null); } }

    @Override
    public void handleMouseInput() {
        if (getListMaxScroll() + this.height >= this.height) {
            int wheel = EaglerAdapter.mouseGetEventDWheel();
            if (wheel < 0) {
                for (int i = 0; i < 20; i++) {
                    offset = MathHelper.clamp_int(offset + 1, 0, getListMaxScroll());
                }
            } else if (wheel > 0) {
                for (int i = 0; i < 20; i++) {
                    offset = MathHelper.clamp_int(offset - 1, 0, getListMaxScroll());
                }
            }
            super.handleMouseInput();
        }
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
    }

    private int getListMaxScroll() {
        return 70 - this.height;
    }
}*/
