package net.minecraft.world;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files are (c) 2022-2023 LAX1DUDE. All Rights Reserved.
 * 
 * WITH THE EXCEPTION OF PATCH FILES, MINIFIED JAVASCRIPT, AND ALL FILES
 * NORMALLY FOUND IN AN UNMODIFIED MINECRAFT RESOURCE PACK, YOU ARE NOT ALLOWED
 * TO SHARE, DISTRIBUTE, OR REPURPOSE ANY FILE USED BY OR PRODUCED BY THE
 * SOFTWARE IN THIS REPOSITORY WITHOUT PRIOR PERMISSION FROM THE PROJECT AUTHOR.
 * 
 * NOT FOR COMMERCIAL OR MALICIOUS USE
 * 
 * (please read the 'LICENSE' file this repo's root directory for more info) 
 * 
 */
public class ColorizerGrass {
	/**+
	 * Color buffer for grass
	 */
	private static int[] grassBuffer = new int[65536];

	public static void setGrassBiomeColorizer(int[] parArrayOfInt) {
		grassBuffer = parArrayOfInt;
	}

	/**+
	 * Gets grass color from temperature and humidity. Args:
	 * temperature, humidity
	 */
	public static int getGrassColor(double parDouble1, double parDouble2) {
		parDouble2 = parDouble2 * parDouble1;
		int i = (int) ((1.0D - parDouble1) * 255.0D);
		int j = (int) ((1.0D - parDouble2) * 255.0D);
		int k = j << 8 | i;
		return k > grassBuffer.length ? -65281 : grassBuffer[k];
	}
}