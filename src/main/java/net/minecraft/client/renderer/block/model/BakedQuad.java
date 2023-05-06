package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;

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
public class BakedQuad {
	protected final int[] vertexData;
	protected final int[] vertexDataWithNormals;
	protected final int tintIndex;
	protected final EnumFacing face;

	public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn) {
		this.vertexData = vertexDataIn;
		this.vertexDataWithNormals = null;
		this.tintIndex = tintIndexIn;
		this.face = faceIn;
	}

	public BakedQuad(int[] vertexDataIn, int[] vertexDataWithNormalsIn, int tintIndexIn, EnumFacing faceIn) {
		this.vertexData = vertexDataIn;
		this.vertexDataWithNormals = vertexDataWithNormalsIn;
		this.tintIndex = tintIndexIn;
		this.face = faceIn;
	}

	public int[] getVertexData() {
		return this.vertexData;
	}

	public int[] getVertexDataWithNormals() {
		return this.vertexDataWithNormals;
	}

	public boolean hasTintIndex() {
		return this.tintIndex != -1;
	}

	public int getTintIndex() {
		return this.tintIndex;
	}

	public EnumFacing getFace() {
		return this.face;
	}
}