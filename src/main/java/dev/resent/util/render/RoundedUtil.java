/*package dev.resent.util.render;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class RoundedUtil {
    final static Minecraft mc = Minecraft.getMinecraft();
    final static FontRenderer fr = mc.fontRendererObj;


    public static void drawSmoothRoundedRect(float x, float y, float x1, float y1, float radius, int color) {
        glPushAttrib(0);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        x1 *= 2.0D;
        y1 *= 2.0D;
        glEnable(RealOpenGLEnums.GL_BLEND);
        glDisable(RealOpenGLEnums.GL_TEXTURE_2D);
        glEnable(RealOpenGLEnums.GL_LINE_SMOOTH);
        setColor(color);
        glEnable(RealOpenGLEnums.GL_LINE_SMOOTH);
        glBegin(RealOpenGLEnums.GL_POLYGON);
        int i;
        for (i = 0; i <= 90; i += 3)
          glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 90; i <= 180; i += 3)
          glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 0; i <= 90; i += 3)
          glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        for (i = 90; i <= 180; i += 3)
          glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        glEnd();
        glBegin(RealOpenGLEnums.GL_LINE_LOOP);
        for (i = 0; i <= 90; i += 3)
          glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 90; i <= 180; i += 3)
          glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 0; i <= 90; i += 3)
          glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        for (i = 90; i <= 180; i += 3)
          glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        glEnd();
        GlStateManager.enableTexture2D();
GlStateManager.disableBlend();
        glDisable(RealOpenGLEnums.GL_LINE_SMOOTH);
        glDisable(RealOpenGLEnums.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.scale(2.0D, 2.0D, 2.0D);
        glPopAttrib();
        glLineWidth(1);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
    public static void drawRoundedRect(float x, float y, float x1, float y1, float radius, int color) {
        glPushAttrib(0);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        x1 *= 2.0D;
        y1 *= 2.0D;
        glEnable(RealOpenGLEnums.GL_BLEND);
        glDisable(RealOpenGLEnums.GL_TEXTURE_2D);
        glEnable(RealOpenGLEnums.GL_LINE_SMOOTH);
        setColor(color);
        glEnable(RealOpenGLEnums.GL_LINE_SMOOTH);
        glBegin(RealOpenGLEnums.GL_POLYGON);
        int i;
        for (i = 0; i <= 90; i += 3)
          glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 90; i <= 180; i += 3)
          glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 0; i <= 90; i += 3)
          glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        for (i = 90; i <= 180; i += 3)
          glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        glEnd();
        GlStateManager.enableTexture2D();
GlStateManager.disableBlend();
GlStateManager.disableBlend();
        GlStateManager.scale(2.0D, 2.0D, 2.0D);
        glEnable(RealOpenGLEnums.GL_BLEND);
        glPopAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
}*/
