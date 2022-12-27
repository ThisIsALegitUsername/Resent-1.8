package dev.resent.util.render;

public class Color {
	
	 public static final Color white     = new Color(255, 255, 255);

	    /**
	     * The color white.  In the default sRGB space.
	     * @since 1.4
	     */
	    public static final Color WHITE = white;

	public final static Color lightGray = new Color(192, 192, 192);

	/**
	 * The color light gray.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color LIGHT_GRAY = lightGray;

	/**
	 * The color gray.  In the default sRGB space.
	 */
	public final static Color gray      = new Color(128, 128, 128);

	/**
	 * The color gray.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color GRAY = gray;

	/**
	 * The color dark gray.  In the default sRGB space.
	 */
	public final static Color darkGray  = new Color(64, 64, 64);

	/**
	 * The color dark gray.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color DARK_GRAY = darkGray;

	/**
	 * The color black.  In the default sRGB space.
	 */
	public final static Color black     = new Color(0, 0, 0);

	/**
	 * The color black.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color BLACK = black;

	/**
	 * The color red.  In the default sRGB space.
	 */
	public final static Color red       = new Color(255, 0, 0);

	/**
	 * The color red.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color RED = red;

	/**
	 * The color pink.  In the default sRGB space.
	 */
	public final static Color pink      = new Color(255, 175, 175);

	/**
	 * The color pink.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color PINK = pink;

	/**
	 * The color orange.  In the default sRGB space.
	 */
	public final static Color orange    = new Color(255, 165, 0);

	/**
	 * The color orange.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color ORANGE = orange;

	/**
	 * The color yellow.  In the default sRGB space.
	 */
	public final static Color yellow    = new Color(255, 255, 0);

	/**
	 * The color yellow.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color YELLOW = yellow;

	/**
	 * The color green.  In the default sRGB space.
	 */
	public final static Color green     = new Color(0, 255, 0);

	/**
	 * The color green.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color GREEN = green;

	/**
	 * The color magenta.  In the default sRGB space.
	 */
	public final static Color magenta   = new Color(255, 0, 255);

	/**
	 * The color magenta.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color MAGENTA = magenta;

	/**
	 * The color cyan.  In the default sRGB space.
	 */
	public final static Color cyan      = new Color(0, 255, 255);

	/**
	 * The color cyan.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color CYAN = cyan;

	/**
	 * The color blue.  In the default sRGB space.
	 */
	public final static Color blue      = new Color(0, 0, 255);

	/**
	 * The color blue.  In the default sRGB space.
	 * @since 1.4
	 */
	public final static Color BLUE = blue;
	
	int value;

	public Color(int r, int g, int b, int a) {
        value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
        testColorValueRange(r,g,b,a);
    }
	
	public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }
	
	 private static void testColorValueRange(int r, int g, int b, int a) {
	        boolean rangeError = false;
	        String badComponentString = "";

	        if ( a < 0 || a > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Alpha";
	        }
	        if ( r < 0 || r > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Red";
	        }
	        if ( g < 0 || g > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Green";
	        }
	        if ( b < 0 || b > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Blue";
	        }
	        if ( rangeError == true ) {
	        throw new IllegalArgumentException("Color parameter outside of expected range:"
	                                           + badComponentString);
	        }
	    }
	 
	 public static int HSBtoRGB(float hue, float saturation, float brightness) {
	        int r = 0, g = 0, b = 0;
	        if (saturation == 0) {
	            r = g = b = (int) (brightness * 255.0f + 0.5f);
	        } else {
	            float h = (hue - (float)Math.floor(hue)) * 6.0f;
	            float f = h - (float)java.lang.Math.floor(h);
	            float p = brightness * (1.0f - saturation);
	            float q = brightness * (1.0f - saturation * f);
	            float t = brightness * (1.0f - (saturation * (1.0f - f)));
	            switch ((int) h) {
	            case 0:
	                r = (int) (brightness * 255.0f + 0.5f);
	                g = (int) (t * 255.0f + 0.5f);
	                b = (int) (p * 255.0f + 0.5f);
	                break;
	            case 1:
	                r = (int) (q * 255.0f + 0.5f);
	                g = (int) (brightness * 255.0f + 0.5f);
	                b = (int) (p * 255.0f + 0.5f);
	                break;
	            case 2:
	                r = (int) (p * 255.0f + 0.5f);
	                g = (int) (brightness * 255.0f + 0.5f);
	                b = (int) (t * 255.0f + 0.5f);
	                break;
	            case 3:
	                r = (int) (p * 255.0f + 0.5f);
	                g = (int) (q * 255.0f + 0.5f);
	                b = (int) (brightness * 255.0f + 0.5f);
	                break;
	            case 4:
	                r = (int) (t * 255.0f + 0.5f);
	                g = (int) (p * 255.0f + 0.5f);
	                b = (int) (brightness * 255.0f + 0.5f);
	                break;
	            case 5:
	                r = (int) (brightness * 255.0f + 0.5f);
	                g = (int) (p * 255.0f + 0.5f);
	                b = (int) (q * 255.0f + 0.5f);
	                break;
	            }
	        }
	        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
	    }
	 
	 public int getRGB() {
	        return value;
	    }
	 
	 public int getRed() {
	        return (getRGB() >> 16) & 0xFF;
	    }

	    /**
	     * Returns the green component in the range 0-255 in the default sRGB
	     * space.
	     * @return the green component.
	     * @see #getRGB
	     */
	    public int getGreen() {
	        return (getRGB() >> 8) & 0xFF;
	    }

	    /**
	     * Returns the blue component in the range 0-255 in the default sRGB
	     * space.
	     * @return the blue component.
	     * @see #getRGB
	     */
	    public int getBlue() {
	        return (getRGB() >> 0) & 0xFF;
	    }

	    /**
	     * Returns the alpha component in the range 0-255.
	     * @return the alpha component.
	     * @see #getRGB
	     */
	    public int getAlpha() {
	        return (getRGB() >> 24) & 0xff;
	    }
	    
	    public Color brighter() {
	        int r = getRed();
	        int g = getGreen();
	        int b = getBlue();
	        int alpha = getAlpha();

	        /* From 2D group:
	         * 1. black.brighter() should return grey
	         * 2. applying brighter to blue will always return blue, brighter
	         * 3. non pure color (non zero rgb) will eventually return white
	         */
	        int i = (int)(1.0/(1.0-FACTOR));
	        if ( r == 0 && g == 0 && b == 0) {
	            return new Color(i, i, i, alpha);
	        }
	        if ( r > 0 && r < i ) r = i;
	        if ( g > 0 && g < i ) g = i;
	        if ( b > 0 && b < i ) b = i;

	        return new Color(Math.min((int)(r/FACTOR), 255),
	                         Math.min((int)(g/FACTOR), 255),
	                         Math.min((int)(b/FACTOR), 255),
	                         alpha);
	    }

	    /**
	     * Creates a new {@code Color} that is a darker version of this
	     * {@code Color}.
	     * <p>
	     * This method applies an arbitrary scale factor to each of the three RGB
	     * components of this {@code Color} to create a darker version of
	     * this {@code Color}.
	     * The {@code alpha} value is preserved.
	     * Although {@code brighter} and
	     * {@code darker} are inverse operations, the results of a series
	     * of invocations of these two methods might be inconsistent because
	     * of rounding errors.
	     * @return  a new {@code Color} object that is
	     *                    a darker version of this {@code Color}
	     *                    with the same {@code alpha} value.
	     * @see        Color#brighter
	     * @since      1.0
	     */
	    public Color darker() {
	        return new Color(Math.max((int)(getRed()  *FACTOR), 0),
	                         Math.max((int)(getGreen()*FACTOR), 0),
	                         Math.max((int)(getBlue() *FACTOR), 0),
	                         getAlpha());
	    }
	    
	    private static final double FACTOR = 0.7;
}