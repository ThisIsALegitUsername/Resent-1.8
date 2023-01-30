package dev.resent.util.misc;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;

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
}
