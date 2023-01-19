package dev.resent.util.render;

public class RainbowUtil {

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
