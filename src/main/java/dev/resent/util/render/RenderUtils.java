package dev.resent.util.render;

import dev.resent.setting.ModeSetting;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

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
