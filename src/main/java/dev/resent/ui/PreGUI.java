package dev.resent.ui;

import dev.resent.util.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class PreGUI extends GuiScreen{

    @Override
    public void drawScreen(int i, int j, float var3) {
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler:gui/logo.png"));
        Gui.drawModalRectWithCustomSizedTexture(GuiScreen.width/2-20, GuiScreen.height/2-50, 0, 0, 60, 60, 60, 60);
        Gui.drawRect(GuiScreen.width/2-40, GuiScreen.height/2+10, GuiScreen.width/2+40, GuiScreen.height/2+30, isMouseInside(i, j, GuiScreen.width/2-25, GuiScreen.height/2-10, GuiScreen.width/2+25, GuiScreen.height/2+10) ? 0x30FFFFFF : 0x40FFFFFF);
        RenderUtils.drawRectOutline(GuiScreen.width/2-40, GuiScreen.height/2+10, GuiScreen.width/2+40, GuiScreen.height/2+30, 0x80FFFFFF);
        Minecraft.getMinecraft().fontRendererObj.drawString("Settings", GuiScreen.width/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth("Settings")/2, GuiScreen.height/2+30-Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2, -1);
        super.drawScreen(i, j, var3);
    }

    @Override
    protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
        super.mouseClicked(parInt1, parInt2, parInt3);
    }

    @Override
    protected void keyTyped(char parChar1, int parInt1) {
         if (parInt1 == 0x01 || parInt1 == Minecraft.getMinecraft().gameSettings.keyBindClickGui.keyCode) {
            mc.displayGuiScreen(null);
        }
        
        super.keyTyped(parChar1, parInt1);
    }

    public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int width, int height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }
    
}
