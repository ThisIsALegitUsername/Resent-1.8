package net.minecraft.world.chunk.storage;

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
public class NibbleArrayReader {
	public final byte[] data;
	private final int depthBits;
	private final int depthBitsPlusFour;

	public NibbleArrayReader(byte[] dataIn, int depthBitsIn) {
		this.data = dataIn;
		this.depthBits = depthBitsIn;
		this.depthBitsPlusFour = depthBitsIn + 4;
	}

	public int get(int parInt1, int parInt2, int parInt3) {
		int i = parInt1 << this.depthBitsPlusFour | parInt3 << this.depthBits | parInt2;
		int j = i >> 1;
		int k = i & 1;
		return k == 0 ? this.data[j] & 15 : this.data[j] >> 4 & 15;
	}
}