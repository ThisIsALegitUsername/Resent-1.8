package dev.resent.util.render;

import dev.resent.setting.ModeSetting;
import net.minecraft.client.gui.Gui;

public class RenderUtils {
    
    public static int getColor(ModeSetting asdf) {
        switch (asdf.getValue()) {
            case "Red":
                return Color.RED.getRGB();
            case "Yellow":
                return Color.YELLOW.getRGB();
            case "Green":
                return Color.GREEN.getRGB();
            case "Blue":
                return Color.BLUE.getRGB();
            case "Orange":
                return Color.ORANGE.getRGB();
            case "Pink":
                return new Color(255, 102, 255).getRGB();
            case "Black":
                return Color.BLACK.getRGB();
            case "White":
                return -1;
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

}
