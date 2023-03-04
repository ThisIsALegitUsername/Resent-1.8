package dev.resent.util.render;

import dev.resent.module.base.setting.ModeSetting;
import dev.resent.visual.ui.Theme;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
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
            mc.fontRendererObj.drawString(tmp, (float) xTmp, (float) y, i, shadow);
            xTmp += mc.fontRendererObj.getCharWidth(textChar);
        }
    }

    public static void drawChromaRectangle(float x, float y, float width, float height, float speed, int colorbecauseidontwanttoremovecolor) {
        float i = x;
        while (true) {
            if (i + 10 <= width) {
                Gui.drawRect(i, y, i + 10, height, RenderUtils.astolfoColorsDraw(i, GuiScreen.width, speed * 10000));
            } else {
                break;
            }
            i += 10;
        }
        if (width - i != 0) {
            for (float h = i; h < width; h++) {
                Gui.drawRect(h, y, h + 1, height, RenderUtils.astolfoColorsDraw(h, GuiScreen.width, speed * 10000));
            }
        }
    }

    public static int astolfoColorsDraw(float yOffset, int yTotal, float speed) {
        float hue = (float) (System.currentTimeMillis() % (int) speed) + ((yTotal - yOffset) * 9);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5f, 1f);
    }

    public static void drawOtherRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        float z = 0;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }

        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }

    	double x1 = (double)(paramXStart + radius);
    	double y1 = (double)(paramYStart + radius);
    	double x2 = (double)(paramXEnd - radius);
    	double y2 = (double)(paramYEnd - radius);

        if (popPush)
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.blendFunc(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA);
        EaglercraftGPU.glLineWidth(1);
        //glEnable(GL_LINE_SMOOTH);

    	GlStateManager.color(red, green, blue, alpha);
        
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(RealOpenGLEnums.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.begin(RealOpenGLEnums.GL_LINE_SMOOTH, DefaultVertexFormats.POSITION_TEX);

        double degree = Math.PI / 180;
        for (double i = 0; i <= 90; i += 1)
            glVertex2d(x2 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        for (double i = 90; i <= 180; i += 1)
            glVertex2d(x2 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        for (double i = 180; i <= 270; i += 1)
            glVertex2d(x1 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        for (double i = 270; i <= 360; i += 1)
            glVertex2d(x1 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        EaglercraftGPU.glEndList();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        //glDisable(GL_LINE_SMOOTH);
        if (popPush) 
        GlStateManager.popMatrix();
    }

    public static void glVertex2d(double idk, double idk2){
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.pos(idk, idk2, 0);
    }

    public static void drawRoundedRect(final float paramInt1, final float paramInt2, final float paramInt3, final float paramInt4, final float radius, final int color, boolean... forceOverride) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GlStateManager.color(f2, f3, f4, f1);
        if (Theme.getRounded() || forceOverride[0]) {
            drawRoundedRect(paramInt1, paramInt2, paramInt3, paramInt4, radius);
        } else {
            Gui.drawRect((int) paramInt1, (int) paramInt2, (int) paramInt3, (int) paramInt4, color);
        }
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
            worldrenderer.pos((float) (f2 + paramFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 - paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
        }
        tessellator.draw();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        f2 = paramFloat1 + paramFloat5;
        f3 = paramFloat2 + paramFloat5;
        worldrenderer.pos(f2, f3, 0).endVertex();
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            worldrenderer.pos((float) (f2 - paramFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 - paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
        }
        tessellator.draw();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        f2 = paramFloat1 + paramFloat5;
        f3 = paramFloat4 - paramFloat5;
        worldrenderer.pos(f2, f3, 0).endVertex();
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            worldrenderer.pos((float) (f2 - paramFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 + paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
        }
        tessellator.draw();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        f2 = paramFloat3 - paramFloat5;
        f3 = paramFloat4 - paramFloat5;
        worldrenderer.pos(f2, f3, 0).endVertex();
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            worldrenderer.pos((float) (f2 + paramFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 + paramFloat5 * Math.sin(Math.toRadians(f4))), 0).endVertex();
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

    public static void drawRectOutline(double param1, double param2, double width1, double height1, int color) {
        Gui.drawRect(param1, param2, width1, param2 + 1, color);
        Gui.drawRect(param1, param2, param1 + 1, height1, color);
        Gui.drawRect(width1 - 1, param2, width1, height1, color);
        Gui.drawRect(param1, height1 - 1, width1, height1, color);
    }

    public static int getRainbow(float seconds, float saturation, float brightness) {
        float hue = (System.currentTimeMillis()) % (int) (seconds * 1000) / (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int getRainbow1(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.HSBtoRGB((float) (rainbowState / 360.0f), 0.8f, 0.7f);
    }
}
