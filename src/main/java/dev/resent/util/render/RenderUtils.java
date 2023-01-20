package dev.resent.util.render;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import dev.resent.setting.ModeSetting;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderUtils {

    public static int getColor(ModeSetting asdf) {
        switch (asdf.getValue()) {
            case "Red":
                return new Color(255, 0, 0, 140).getRGB();
            case "Yellow":
                return new Color(255, 255, 0, 140).getRGB();
            case "Green":
                return new Color(0, 255, 0, 140).getRGB();
            case "Blue":
                return new Color(0, 0, 255, 140).getRGB();
            case "Orange":
                return new Color(255, 165, 0, 140).getRGB();
            case "Pink":
                return new Color(255, 102, 255, 140).getRGB();
            case "Black":
                return new Color(0, 0, 0, 140).getRGB();
            case "White":
                return new Color(255, 255, 255, 140).getRGB();
        }
        return -1;
    }

    public static void drawChromaRect(int zLevel, int x, int y, int width, int height) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)(y + height), (float)0.0f);
        GlStateManager.rotate((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        int p_drawGradientRect_5_ = Color.HSBtoRGB((float)((System.currentTimeMillis() - (long)x * 10L - (long)y * 10L) % 2000L) / 2000.0f, 0.8f, 0.8f);
        int p_drawGradientRect_6_ = Color.HSBtoRGB((float)((System.currentTimeMillis() - (long)(x + width / 2) * 10L - (long)y * 10L) % 2000L) / 2000.0f, 0.8f, 0.8f);
        float lvt_11_1_ = (float)(p_drawGradientRect_6_ >> 24 & 0xFF) / 255.0f;
        float lvt_12_1_ = (float)(p_drawGradientRect_6_ >> 16 & 0xFF) / 255.0f;
        float lvt_13_1_ = (float)(p_drawGradientRect_6_ >> 8 & 0xFF) / 255.0f;
        float lvt_14_1_ = (float)(p_drawGradientRect_6_ & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        Tessellator lvt_15_1_ = Tessellator.getInstance();
        WorldRenderer lvt_16_1_ = lvt_15_1_.getWorldRenderer();
        lvt_16_1_.begin(7, DefaultVertexFormats.POSITION_COLOR);
        lvt_16_1_.pos((double)height, (double)width, (double)zLevel).color(lvt_12_1_, lvt_13_1_, lvt_14_1_, lvt_11_1_).endVertex();
        lvt_15_1_.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
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
