package net.minecraft.util;

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
public class MouseFilter {
	private float field_76336_a;
	private float field_76334_b;
	private float field_76335_c;

	/**+
	 * Smooths mouse input
	 */
	public float smooth(float parFloat1, float parFloat2) {
		this.field_76336_a += parFloat1;
		parFloat1 = (this.field_76336_a - this.field_76334_b) * parFloat2;
		this.field_76335_c += (parFloat1 - this.field_76335_c) * 0.5F;
		if (parFloat1 > 0.0F && parFloat1 > this.field_76335_c || parFloat1 < 0.0F && parFloat1 < this.field_76335_c) {
			parFloat1 = this.field_76335_c;
		}

		this.field_76334_b += parFloat1;
		return parFloat1;
	}

	public void reset() {
		this.field_76336_a = 0.0F;
		this.field_76334_b = 0.0F;
		this.field_76335_c = 0.0F;
	}
}