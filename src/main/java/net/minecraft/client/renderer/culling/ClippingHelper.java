package net.minecraft.client.renderer.culling;

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
public class ClippingHelper {
	public float[][] frustum = new float[6][4];
	public float[] projectionMatrix = new float[16];
	public float[] modelviewMatrix = new float[16];
	public float[] clippingMatrix = new float[16];

	private double dot(float[] parArrayOfFloat, double parDouble1, double parDouble2, double parDouble3) {
		return (double) parArrayOfFloat[0] * parDouble1 + (double) parArrayOfFloat[1] * parDouble2
				+ (double) parArrayOfFloat[2] * parDouble3 + (double) parArrayOfFloat[3];
	}

	/**+
	 * Returns true if the box is inside all 6 clipping planes,
	 * otherwise returns false.
	 */
	public boolean isBoxInFrustum(double parDouble1, double parDouble2, double parDouble3, double parDouble4,
			double parDouble5, double parDouble6) {
		for (int i = 0; i < 6; ++i) {
			float[] afloat = this.frustum[i];
			if (this.dot(afloat, parDouble1, parDouble2, parDouble3) <= 0.0D
					&& this.dot(afloat, parDouble4, parDouble2, parDouble3) <= 0.0D
					&& this.dot(afloat, parDouble1, parDouble5, parDouble3) <= 0.0D
					&& this.dot(afloat, parDouble4, parDouble5, parDouble3) <= 0.0D
					&& this.dot(afloat, parDouble1, parDouble2, parDouble6) <= 0.0D
					&& this.dot(afloat, parDouble4, parDouble2, parDouble6) <= 0.0D
					&& this.dot(afloat, parDouble1, parDouble5, parDouble6) <= 0.0D
					&& this.dot(afloat, parDouble4, parDouble5, parDouble6) <= 0.0D) {
				return false;
			}
		}

		return true;
	}
}