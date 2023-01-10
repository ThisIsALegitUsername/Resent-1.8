package dev.resent.util.render;

import dev.resent.setting.ModeSetting;
import net.minecraft.client.gui.Gui;

public class RenderUtils {
    
    public static int getColor(ModeSetting asdf) {

        switch (asdf.getValue()) {
            case "Red":
                return new Color(255, 0, 0, 85).getRGB();
            case "Yellow":
                return new Color(255, 255, 0, 85).getRGB();
            case "Green":
                return new Color(0, 255, 0, 85).getRGB();
            case "Blue":
                return new Color(0, 0, 255, 85).getRGB();
            case "Orange":
                return new Color(255, 165, 0, 85).getRGB();
            case "Pink":
                return new Color(255, 102, 255).getRGB();
            case "Black":
                return new Color(0, 0, 0, 85).getRGB();
            case "White":
                return new Color(255, 255, 255, 85).getRGB();
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
