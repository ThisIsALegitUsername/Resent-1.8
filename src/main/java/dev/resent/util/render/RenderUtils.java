package dev.resent.util.render;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA;
import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.GL_SRC_ALPHA;

import dev.resent.module.setting.ModeSetting;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderUtils {
    
    public static void drawChromaString(String string, int x, int y, boolean shadow) {
        Minecraft mc = Minecraft.getMinecraft();

        int xTmp = x;
        for (char textChar : string.toCharArray()) {
            long l = System.currentTimeMillis() - (xTmp * 10 - y * 10);
            int i = Color.HSBtoRGB(l % (int) 2000.0F / 2000.0F, 0.8F, 0.8F);
            String tmp = String.valueOf(textChar);
            mc.fontRendererObj.drawString(tmp, xTmp, y, i, shadow);
            xTmp += mc.fontRendererObj.getCharWidth(textChar);
        }
    }

    public static void drawRoundedRect(final float paramInt1, final float paramInt2, final float paramInt3, final float paramInt4, final float radius, final int color) {
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

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
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

    public static void drawRectOutline(int x, int y, int width, int height, int color) {
        Gui.drawRect(x, y, width, y + 1, color);
        Gui.drawRect(x, y, x + 1, height, color);
        Gui.drawRect(width - 1, y, width, height, color);
        Gui.drawRect(x, height - 1, width, height, color);
    }
    
    public static void drawCenteredScaledString(String text, int x,int y, int color, float scale){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,scale);
        Gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, text, (int) (x / scale), (int) (y / scale), color);
        GlStateManager.popMatrix();
    }

    public static void startScale(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-x, -y, 0);
    }

}
