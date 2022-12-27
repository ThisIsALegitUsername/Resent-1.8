package dev.resent.ui.mods;

import dev.resent.util.misc.Keyboard;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.GuiScreenSingleplayerLoading;
import net.lax1dude.eaglercraft.IntegratedServer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;

@SuppressWarnings("all")
public class MainMenu extends GuiScreen{

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int fh = fontRenderer.FONT_HEIGHT;
        Gui.drawRect(0, 0, width, height, Color.BLACK.getRGB());
        //Gui.drawRect(0, 0, width / 3, height, new Color(50, 50, 50).getRGB());
        EaglerAdapter.glPushMatrix();
        EaglerAdapter.glTranslatef(width/2, height/4, 0);
        EaglerAdapter.glScalef(2.5f, 2.5f, 2.5f);
        EaglerAdapter.glTranslatef(-(width/2), -(height/4), 0);
        fontRenderer.drawString("§4R§fesent", width/2-fontRenderer.getStringWidth("§4R§fesent")/2, height/4, -1);
        EaglerAdapter.glPopMatrix();

        Gui.drawRect(width/2-50, height/2-20, width/2+50, height*2/3, new Color(50, 50, 50, 50).getRGB());
        //RenderUtils.drawRectOutline(width/2-fontRenderer.getStringWidth("Singleplayer")/2, height/2, width/2+fontRenderer.getStringWidth("Singleplayer")/2, height/2+fh, -1);
        fontRenderer.drawString("Singleplayer", width/2-fontRenderer.getStringWidth("Singleplayer")/2, height/2, isMouseInside(mouseX, mouseY, width/2-fontRenderer.getStringWidth("Singleplayer")/2, height/2, width/2+fontRenderer.getStringWidth("Singleplayer")/2, height/2+fh) ? Color.RED.getRGB() : -1);
        //RenderUtils.drawRectOutline(width/2-fontRenderer.getStringWidth("Multiplayer ")/2, height/2+fh*2, width/2+fontRenderer.getStringWidth("Multiplayer")/2, height/2+fh*3, -1);
        fontRenderer.drawString("Multiplayer ", width/2-fontRenderer.getStringWidth("Multiplayer ")/2, height/2+fh*2, isMouseInside(mouseX, mouseY, width/2-fontRenderer.getStringWidth("Multiplayer ")/2, height/2+fh*2, width/2+fontRenderer.getStringWidth("Multiplayer")/2, height/2+fh*3) ? Color.RED.getRGB() : -1);
        //RenderUtils.drawRectOutline(width/2-fontRenderer.getStringWidth("Options     ")/2, height/2+fh*4, width/2+fontRenderer.getStringWidth("Options")/2, height/2+fh*5, -1);
        fontRenderer.drawString("Options     ", width/2-fontRenderer.getStringWidth("Options     ")/2, height/2+fh*4, isMouseInside(mouseX, mouseY, width/2-fontRenderer.getStringWidth("Options     ")/2, height/2+fh*4, width/2+fontRenderer.getStringWidth("Options")/2, height/2+fh*5) ? Color.RED.getRGB() : -1);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int fh = fontRenderer.FONT_HEIGHT;
        if(isMouseInside(mouseX, mouseY, width/2-fontRenderer.getStringWidth("Singleplayer")/2, height/2, width/2+fontRenderer.getStringWidth("Singleplayer")/2, height/2+fh)){
            if (EaglerAdapter.isIntegratedServerAvailable()) {
                if (!IntegratedServer.isAlive()) {
                    IntegratedServer.begin();
                    this.mc.displayGuiScreen(new GuiScreenSingleplayerLoading(new GuiSelectWorld(this),  "starting up integrated server", () -> IntegratedServer.isReady()));
                } else {
                    this.mc.displayGuiScreen(new GuiSelectWorld(this));
                }
            }
        }else if(isMouseInside(mouseX, mouseY, width/2-fontRenderer.getStringWidth("Multiplayer ")/2, height/2+fh*2, width/2+fontRenderer.getStringWidth("Multiplayer")/2, height/2+fh*3)){
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }else if(isMouseInside(mouseX, mouseY, width/2-fontRenderer.getStringWidth("Options     ")/2, height/2+fh*4, width/2+fontRenderer.getStringWidth("Options")/2, height/2+fh*5)){
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
    }

    public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int width, int height) { return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height); }
    
}
