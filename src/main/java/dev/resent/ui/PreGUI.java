package dev.resent.ui;

import dev.resent.util.render.RenderUtils;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class PreGUI extends GuiScreen{

    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void drawScreen(int i, int j, float var3) {
        
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0.5f, 0);
        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/logo.png"));
        Gui.drawModalRectWithCustomSizedTexture(GuiScreen.width/2-20, GuiScreen.height/2-50, 0, 0, 60, 60, 60, 60);

        Gui.drawRect(GuiScreen.width/2-30, GuiScreen.height/2+20, GuiScreen.width/2+50, GuiScreen.height/2+50, isMouseInside(i, j, GuiScreen.width/2-30, GuiScreen.height/2+20, GuiScreen.width/2+50, GuiScreen.height/2+50) ? 0x40FFFFFF : 0x50FFFFFF);
        RenderUtils.drawRectOutline(GuiScreen.width/2-30, GuiScreen.height/2+20, GuiScreen.width/2+50, GuiScreen.height/2+50, 0x080FFFFFF);
        RenderUtils.drawCenteredScaledString("Mods", GuiScreen.width/2+10, GuiScreen.height/2+35-mc.fontRendererObj.FONT_HEIGHT/2, -1, 1.25f);

        /*Gui.drawRect(GuiScreen.width/2-30, GuiScreen.height/2+55, GuiScreen.width/2+50, GuiScreen.height/2+85, isMouseInside(i, j, GuiScreen.width/2-30, GuiScreen.height/2+55, GuiScreen.width/2+50, GuiScreen.height/2+85) ? 0x40FFFFFF : 0x50FFFFFF);
        RenderUtils.drawRectOutline(GuiScreen.width/2-30, GuiScreen.height/2+55, GuiScreen.width/2+50, GuiScreen.height/2+85, 0x080FFFFFF);
        RenderUtils.drawCenteredScaledString("Cosmetics", GuiScreen.width/2+10, GuiScreen.height/2+70-mc.fontRendererObj.FONT_HEIGHT/2, -1, 1.25f);*/
        super.drawScreen(i, j, var3);
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
        if(isMouseInside(parInt1, parInt2, GuiScreen.width/2-30, GuiScreen.height/2+20, GuiScreen.width/2+50, GuiScreen.height/2+50) && parInt3 == 0){
            mc.displayGuiScreen(new ClickGUI());
        }
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
