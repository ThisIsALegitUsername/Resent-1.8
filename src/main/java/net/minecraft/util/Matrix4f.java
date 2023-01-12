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
public class Matrix4f extends net.lax1dude.eaglercraft.v1_8.vector.Matrix4f {
	public Matrix4f(float[] parArrayOfFloat) {
		this.m00 = parArrayOfFloat[0];
		this.m01 = parArrayOfFloat[1];
		this.m02 = parArrayOfFloat[2];
		this.m03 = parArrayOfFloat[3];
		this.m10 = parArrayOfFloat[4];
		this.m11 = parArrayOfFloat[5];
		this.m12 = parArrayOfFloat[6];
		this.m13 = parArrayOfFloat[7];
		this.m20 = parArrayOfFloat[8];
		this.m21 = parArrayOfFloat[9];
		this.m22 = parArrayOfFloat[10];
		this.m23 = parArrayOfFloat[11];
		this.m30 = parArrayOfFloat[12];
		this.m31 = parArrayOfFloat[13];
		this.m32 = parArrayOfFloat[14];
		this.m33 = parArrayOfFloat[15];
	}

	public Matrix4f() {
		this.m00 = this.m01 = this.m02 = this.m03 = this.m10 = this.m11 = this.m12 = this.m13 = this.m20 = this.m21 = this.m22 = this.m23 = this.m30 = this.m31 = this.m32 = this.m33 = 0.0F;
	}
}