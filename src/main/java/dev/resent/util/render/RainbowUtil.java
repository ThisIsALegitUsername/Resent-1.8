package dev.resent.util.render;

public class RainbowUtil {
    public static int getRainbow(float seconds,float saturation, float brightness) {
		float hue = (System.currentTimeMillis()) % (int)(seconds*1000) / (float)(seconds*1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return color;
	}
	
	public static int astolfoColorsDraw(int yOffset, int yTotal) {
        return astolfoColorsDraw(yOffset, yTotal, 50000f);
     }
	
	 public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
         float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
         while (hue > speed) {
            hue -= speed;
         }
         hue /= speed;
         if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
         }
         hue += 0.5F;
         return Color.HSBtoRGB(hue, 0.5f, 1F);
      }
    
    public static int getRainbow1(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.HSBtoRGB((float) (rainbowState / 360.0f), 0.8f, 0.7f);
    }
}
