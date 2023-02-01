package dev.resent.util.render;

import dev.resent.module.setting.ModeSetting;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderUtils {
    
    public static void drawChromaString(final String string, final int x, final int y, final boolean shadow) {
        final Minecraft mc = Minecraft.getMinecraft();
        int xTmp = x;
        char[] charArray;
        for (int length = (charArray = string.toCharArray()).length, j = 0; j < length; ++j) {
            final char textChar = charArray[j];
            final long l = System.currentTimeMillis() - (xTmp * 10 - y * 10);
            final int i = Color.HSBtoRGB(l % 2000L / 2000.0f, 0.8f, 0.8f);
            final String tmp = String.valueOf(textChar);
            mc.fontRendererObj.drawString(tmp, (float)xTmp, (float)y, i, shadow);
            xTmp += mc.fontRendererObj.getCharWidth(textChar);
        }
    }

    public static void drawRoundedRect(final float paramInt1, final float paramInt2, final float paramInt3, final float paramInt4, final float radius, final int color, boolean... rounded) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GlStateManager.color(f2, f3, f4, f1);
        drawRoundedRect(paramInt1, paramInt2, paramInt3, paramInt4, radius);
    }

    public static void drawRoundedRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5) {
        final int i = 18;
        final float f1 = 90.0f / i;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.enableColorMaterial();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        worldrenderer.begin(5, DefaultVertexFormats.POSITION_TEX);
        
        worldrenderer.pos(paramFloat1 + paramFloat5, paramFloat2, 0).endVertex();
        worldrenderer.pos(paramFloat1 + paramFloat5, paramFloat4, 0).endVertex();
        worldrenderer.pos(paramFloat3 - paramFloat5, paramFloat2, 0).endVertex();
        worldrenderer.pos(paramFloat3 - paramFloat5, paramFloat4, 0).endVertex();
        tessellator.draw();
        worldrenderer.begin(5, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(paramFloat1, paramFloat2 + paramFloat5, 0).endVertex();
        worldrenderer.pos(paramFloat1 + paramFloat5, paramFloat2 + paramFloat5, 0).endVertex();
        worldrenderer.pos(paramFloat1, paramFloat4 - paramFloat5, 0).endVertex();
        worldrenderer.pos(paramFloat1 + paramFloat5, paramFloat4 - paramFloat5, 0).endVertex();
        tessellator.draw();
        worldrenderer.begin(5, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(paramFloat3, paramFloat2 + paramFloat5, 0).endVertex();
        worldrenderer.pos(paramFloat3 - paramFloat5, paramFloat2 + paramFloat5, 0).endVertex();
        worldrenderer.pos(paramFloat3, paramFloat4 - paramFloat5, 0).endVertex();
        worldrenderer.pos(paramFloat3 - paramFloat5, paramFloat4 - paramFloat5, 0).endVertex();
        tessellator.draw();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        float f2 = paramFloat3 - paramFloat5;
        float f3 = paramFloat2 + paramFloat5;
        worldrenderer.pos(f2, f3, 0).endVertex();
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            worldrenderer.pos((float)(f2 + paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 - paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
        }
        tessellator.draw();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        f2 = paramFloat1 + paramFloat5;
        f3 = paramFloat2 + paramFloat5;
        worldrenderer.pos(f2, f3, 0).endVertex();
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            worldrenderer.pos((float)(f2 - paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 - paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
        }
        tessellator.draw();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        f2 = paramFloat1 + paramFloat5;
        f3 = paramFloat4 - paramFloat5;
        worldrenderer.pos(f2, f3, 0).endVertex();
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            worldrenderer.pos((float)(f2 - paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 + paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
        }
        tessellator.draw();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        f2 = paramFloat3 - paramFloat5;
        f3 = paramFloat4 - paramFloat5;
        worldrenderer.pos(f2, f3, 0).endVertex();
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            worldrenderer.pos((float)(f2 + paramFloat5 * Math.cos(Math.toRadians(f4))), (float)(f3 + paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
    }

    public static Color getColorWithoutRGB(ModeSetting asdf) {
        switch (asdf.getValue()) {
            case "Red":
                return Color.RED;
            case "Yellow":
                return Color.YELLOW;
            case "Green":
                return Color.GREEN;
            case "Blue":
                return Color.BLUE;
            case "Orange":
                return Color.ORANGE;
            case "Pink":
                return new Color(255, 102, 255);
            case "Black":
                return Color.BLACK;
            case "White":
                return Color.WHITE;
        }
        return Color.WHITE;
    }

    public static void drawRectOutline(int param1, int param2, int width1, int height1, int color) {
        Gui.drawRect(param1, param2, width1, param2 + 1, color);
        Gui.drawRect(param1, param2, param1 + 1, height1, color);
        Gui.drawRect(width1 - 1, param2, width1, height1, color);
        Gui.drawRect(param1, height1 - 1, width1, height1, color);
    }
    
    public static void drawCenteredScaledString(String text, int param1,int param2, int color, float scale){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,scale);
        Gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, text, (int) (param1 / scale), (int) (param2 / scale), color, false);
        GlStateManager.popMatrix();
    }

}
