package dev.resent.util.misc;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GlUtils {
    
    public static void startScale(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-x, -y, 0);
    }

    public static void stopScale(){
        GlStateManager.popMatrix();
    }

    public static void startTranslate(float x, float y) {
    	GlStateManager.pushMatrix();
    	GlStateManager.translate(x, y, 0);
    }
    
    public static void stopTranslate() {
    	GlStateManager.popMatrix();
    }

    public static void drawCenteredScaledString(String text, int param1,int param2, int color, float scale){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,scale);
        Gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, text, (int) (param1 / scale), (int) (param2 / scale), color, true);
        GlStateManager.popMatrix();
    }    
}
