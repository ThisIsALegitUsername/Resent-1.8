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
public class FrameTimer {
	private final long[] field_181752_a = new long[240];
	private int field_181753_b;
	private int field_181754_c;
	private int field_181755_d;

	public void func_181747_a(long parLong1) {
		this.field_181752_a[this.field_181755_d] = parLong1;
		++this.field_181755_d;
		if (this.field_181755_d == 240) {
			this.field_181755_d = 0;
		}

		if (this.field_181754_c < 240) {
			this.field_181753_b = 0;
			++this.field_181754_c;
		} else {
			this.field_181753_b = this.func_181751_b(this.field_181755_d + 1);
		}

	}

	public int func_181748_a(long parLong1, int parInt1) {
		double d0 = (double) parLong1 / 1.6666666E7D;
		return (int) (d0 * (double) parInt1);
	}

	public int func_181749_a() {
		return this.field_181753_b;
	}

	public int func_181750_b() {
		return this.field_181755_d;
	}

	public int func_181751_b(int parInt1) {
		return parInt1 % 240;
	}

	public long[] func_181746_c() {
		return this.field_181752_a;
	}
}