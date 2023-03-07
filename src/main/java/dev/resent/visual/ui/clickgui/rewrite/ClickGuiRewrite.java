package dev.resent.visual.ui.clickgui.rewrite;

import dev.resent.util.misc.GlUtils;
import dev.resent.visual.ui.Theme;
import dev.resent.visual.ui.animation.Animation;
import dev.resent.visual.ui.animation.Direction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGuiRewrite extends GuiScreen{

    public Animation introAnimation;
    public int x, y, width, height;
    public ScaledResolution sr;
    public boolean closing;

    @Override
    public void drawScreen(int mouseX, int mouseY, float var3) {
        GlUtils.startScale((this.x + this.width) / 2, (this.y + this.height) / 2, introAnimation != null ? (float) introAnimation.getValue() : 1);

        

        GlUtils.stopScale();

        if (closing) {
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
    protected void mouseClicked(int parInt1, int parInt2, int parInt3) {

    }

    @Override
    public void initGui() {
        sr = new ScaledResolution(mc);
        x = sr.getScaledWidth()/4;
        y = sr.getScaledHeight()/4;
        width = x + sr.getScaledWidth()/2;
        height = y + sr.getScaledHeight()/2;
        introAnimation = Theme.getAnimation(Theme.getAnimationId(), 500, 1, 3, 3.8f, 1.35f, false);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 0x01 || par2 == Minecraft.getMinecraft().gameSettings.keyBindClickGui.keyCode) {
            closing = true;
        }
    }

    public boolean isMouseInside(double mouseX, double mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }
    
}
