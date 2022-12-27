package dev.resent.util.render;

import dev.resent.setting.ModeSetting;

public class RenderUtils {
    /*public static void drawChromaString(String string, int x, int y, boolean shadow) {
        Minecraft mc = Minecraft.getMinecraft();

        int xTmp = x;
        boolean hasReachedSS = false;
        boolean hasFinished = false;

        for (char textChar : string.toCharArray()) {
            long l = System.currentTimeMillis() - (xTmp * 10 - y * 10);
            int i = Color.HSBtoRGB(l % (int) 2000.0F / 2000.0F, 0.8F, 0.8F);
            String tmp = String.valueOf(textChar);

            if (Character.toString(textChar).equalsIgnoreCase("§")) {
                hasReachedSS = true;
            }

            if (!hasReachedSS) {
                mc.fontRendererObj.drawString(tmp, xTmp, y, i, shadow);
                xTmp += mc.fontRendererObj.getCharWidth(textChar);

                string = string.substring(1);
            } else if (!hasFinished) {

                mc.fontRendererObj.drawString(string, xTmp, y, -1, shadow);
                hasFinished = true;
            }
        }
    }

    public static void drawRoundedRect(int x, int y, int width, int height, int round, int color) {
        int yy = 0;
        for (int xx = round; xx >= 1; --xx) {
            Gui.drawRect(x + xx, y + yy - 1, x + (width - xx), y + yy - 1 + 1, color);
            ++y;
        }
        Gui.drawRect(x, y + yy - 1, x + width, y + yy + (height - (round)) - 1, color);
        yy = 0;
        for (int xx = 1; xx <= round; xx++) {
            Gui.drawRect(x + xx, y + yy + (height - (round)) - 1, x + (width - xx), y + yy + (height - (round)) - 1 + 1,
                    color);
            ++yy;
        }
    }

    public static int astolfoColorsDraw(int yOffset, int yTotal) {
        return astolfoColorsDraw(yOffset, yTotal, 50000f);
    }

    public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
        float hue = (float) (System.currentTimeMillis() % (int) speed) + ((yTotal - yOffset) * 9);
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
*/
    public static void drawRectOutline(int x, int y, int width, int height, int color) {
        Gui.drawRect(x, y, width, y + 1, color);
        Gui.drawRect(x, y, x + 1, height, color);
        Gui.drawRect(width - 1, y, width, height, color);
        Gui.drawRect(x, height - 1, width, height, color);
    }

    public static void drawRoundedRectOutline(int x, int y, int width, int height, int color) {
        drawRoundedRect(x, y, width, y + 0.5f, 2, color);
        drawRoundedRect(x, y, x + 0.5f, height, 2, color);
        drawRoundedRect(width - 0.5f, y, width, height, 2, color);
        drawRoundedRect(x, height - 0.5f, width, height, 2, color);
    }
/*
    public static void drawChromaRectangle(int x, int y, int width, int height) {
        for (int i = x; i < width; i += 10) {
            Gui.drawRect(i, y, i + 10, height, RenderUtils.astolfoColorsDraw(i, GuiScreen.width, 20000f));
        }
    }

    public static void drawRoundedRect(int x, int y, int width, int height, int round) {
        int yy = 0;
        for (int xx = round; xx >= 1; --xx) {
            Gui.drawRect(x + xx, y + yy - 1, x + (width - xx), y + yy - 1 + 1);
            ++y;
        }
        Gui.drawRect(x, y + yy - 1, x + width, y + yy + (height - (round)) - 1);
        yy = 0;
        for (int xx = 1; xx <= round; xx++) {
            Gui.drawRect(x + xx, y + yy + (height - (round)) - 1, x + (width - xx),
                    y + yy + (height - (round)) - 1 + 1);
            ++yy;
        }
    }*/

    public static void circle(final float x, final float y, final float radius, final int fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void arc(final float x, final float y, final float start, final float end, final float radius,
            final int color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arcEllipse(final float x, final float y, float start, float end, final float w, final float h,
            final int color) {
        EaglerAdapter.glColor3f(0.0f, 0.0f, 0.0f);
        EaglerAdapter.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        final float var11 = (color >> 24 & 0xFF) / 255.0f;
        final float var12 = (color >> 16 & 0xFF) / 255.0f;
        final float var13 = (color >> 8 & 0xFF) / 255.0f;
        final float var14 = (color & 0xFF) / 255.0f;
        final Tessellator var15 = Tessellator.instance;
        EaglerAdapter.glEnable(EaglerAdapter.GL_BLEND);
        EaglerAdapter.glDisable(EaglerAdapter.GL_TEXTURE_2D);
        EaglerAdapter.glBlendFunc(770, 771);
        EaglerAdapter.glColor4f(var12, var13, var14, var11);
        if (var11 > 0.5f) {
            EaglerAdapter.glEnable(EaglerAdapter.GL_LINES);
            EaglerAdapter.glLineWidth(2.0f);
            EaglerAdapter.glBeginQuery(3);
            for (float i = end; i >= start; i -= 4.0f) {
                final float ldx = (float) Math.cos(i * Math.PI / 180.0) * w * 1.001f;
                final float ldy = (float) Math.sin(i * Math.PI / 180.0) * h * 1.001f;
                var15.addVertex(x + ldx, y + ldy, 0);
            }
            EaglerAdapter.glEndQuery();
            EaglerAdapter.glDisable(EaglerAdapter.GL_LINES);
        }
        EaglerAdapter.glBeginQuery(6);
        for (float i = end; i >= start; i -= 4.0f) {
            final float ldx = (float) Math.cos(i * Math.PI / 180.0) * w;
            final float ldy = (float) Math.sin(i * Math.PI / 180.0) * h;
            var15.addVertex(x + ldx, y + ldy, 0);
        }
        EaglerAdapter.glEndQuery();
        EaglerAdapter.glEnable(EaglerAdapter.GL_TEXTURE_2D);
        EaglerAdapter.glDisable(EaglerAdapter.GL_BLEND);
    }

    public static void drawRoundedRect(float x, float y, float x2, float y2, final float round, final int color) {
        x += (float) (round / 2.0f + 0.5);
        y += (float) (round / 2.0f + 0.5);
        x2 -= (float) (round / 2.0f + 0.5);
        y2 -= (float) (round / 2.0f + 0.5);
        Gui.drawRect((int)x, (int)y, (int)x2, (int)y2, color);
        circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        circle(x + round / 2.0f, y + round / 2.0f, round, color);
        circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        Gui.drawRect((int)(x - round / 2.0f - 0.5f), (int)(y + round / 2.0f), (int)x2,  (int)(y2 - (int)round / 2.0f),
                color);
        Gui.drawRect((int)x, ((int)y + (int)round / 2), ((int)x2 + (int)round / 2 + (int)0.5f), ((int)y2 - (int)round / 2),
                color);
        Gui.drawRect(((int)x + (int)round / 2), ((int)y - (int)round / 2 - (int)0.5f), ((int)x2 - (int)round / 2),
                (int) (y2 - round / 2.0f), color);
        Gui.drawRect(((int)x + (int)round / 2), (int)y, (int)(x2 - round / 2.0f), (int)(y2 + round / 2.0f + 0.5f),
                color);
    }
    
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

    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
		float var7 = (float) (par5 >> 24 & 255) / 255.0F;
		float var8 = (float) (par5 >> 16 & 255) / 255.0F;
		float var9 = (float) (par5 >> 8 & 255) / 255.0F;
		float var10 = (float) (par5 & 255) / 255.0F;
		float var11 = (float) (par6 >> 24 & 255) / 255.0F;
		float var12 = (float) (par6 >> 16 & 255) / 255.0F;
		float var13 = (float) (par6 >> 8 & 255) / 255.0F;
		float var14 = (float) (par6 & 255) / 255.0F;
		EaglerAdapter.glDisable(EaglerAdapter.GL_TEXTURE_2D);
		EaglerAdapter.glEnable(EaglerAdapter.GL_BLEND);
		EaglerAdapter.glDisable(EaglerAdapter.GL_ALPHA_TEST);
		EaglerAdapter.glBlendFunc(EaglerAdapter.GL_SRC_ALPHA, EaglerAdapter.GL_ONE_MINUS_SRC_ALPHA);
		EaglerAdapter.glShadeModel(EaglerAdapter.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		var15.addVertex((double) par3, (double) par2, 0.0);
		var15.addVertex((double) par1, (double) par2, 0.0);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex((double) par1, (double) par4, 0.0);
		var15.addVertex((double) par3, (double) par4, 0.0);
		var15.draw();
		EaglerAdapter.glShadeModel(EaglerAdapter.GL_FLAT);
		EaglerAdapter.glDisable(EaglerAdapter.GL_BLEND);
		EaglerAdapter.glEnable(EaglerAdapter.GL_ALPHA_TEST);
		EaglerAdapter.glEnable(EaglerAdapter.GL_TEXTURE_2D);
	}

}
