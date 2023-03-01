package dev.resent.visual.ui;

import dev.resent.util.misc.GlUtils;
import dev.resent.util.render.Color;
import dev.resent.visual.ui.animation.SimpleAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class PreGUI extends GuiScreen {

    Minecraft mc = Minecraft.getMinecraft();
    public SimpleAnimation opacityAnimation = new SimpleAnimation(0);
    public SimpleAnimation slideAnimation = new SimpleAnimation(0);

    @Override
    public void drawScreen(int i, int j, float var3) {
    	
    	opacityAnimation.setAnimation(100, 5);
    	slideAnimation.setAnimation(200, 7);
    	
    	boolean isInside = isMouseInside(i, j, GuiScreen.width / 2 - 20, GuiScreen.height / 2 + 20, GuiScreen.width / 2 + 40, GuiScreen.height / 2 + 50);
        mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/logo.png"));
        Gui.drawModalRectWithCustomSizedTexture(GuiScreen.width / 2 - 20, GuiScreen.height / 2 - 250 + (int)slideAnimation.getValue(), 0, 0, 60, 60, 60, 60);
        Gui.drawRect(GuiScreen.width / 2 - 20, GuiScreen.height / 2 + 20, GuiScreen.width / 2 + 40, GuiScreen.height / 2 + 50,  isInside ? 0x90FFFFFF : new Color(230, 230, 230, (int)opacityAnimation.getValue()).getRGB());
        //RenderUtils.drawRectOutline(GuiScreen.width / 2 - 20, GuiScreen.height / 2 + 20, GuiScreen.width / 2 + 40, GuiScreen.height / 2 + 50, 0x080FFFFFF);
        if(opacityAnimation.isDone()) {
            mc.fontRendererObj.drawStringWithShadow("Mods", GuiScreen.width / 2 - 2, GuiScreen.height / 2 + 35 - 9 / 2, -1);
        }
    }

    @Override
    protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
        if (isMouseInside(parInt1, parInt2, GuiScreen.width / 2 - 30, GuiScreen.height / 2 + 20, GuiScreen.width / 2 + 50, GuiScreen.height / 2 + 50) && parInt3 == 0) {
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
