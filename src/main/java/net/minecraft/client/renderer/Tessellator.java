package net.minecraft.client.renderer;

import net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldVertexBufferUploader;

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
public class Tessellator {
	private WorldRenderer worldRenderer;
	private WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();

	public static final int GL_TRIANGLES = RealOpenGLEnums.GL_TRIANGLES;
	public static final int GL_TRIANGLE_STRIP = RealOpenGLEnums.GL_TRIANGLE_STRIP;
	public static final int GL_TRIANGLE_FAN = RealOpenGLEnums.GL_TRIANGLE_FAN;
	public static final int GL_QUADS = RealOpenGLEnums.GL_QUADS;
	public static final int GL_LINES = RealOpenGLEnums.GL_LINES;
	public static final int GL_LINE_STRIP = RealOpenGLEnums.GL_LINE_STRIP;
	public static final int GL_LINE_LOOP = RealOpenGLEnums.GL_LINE_LOOP;

	/**+
	 * The static instance of the Tessellator.
	 */
	private static final Tessellator instance = new Tessellator(2097152);

	public static Tessellator getInstance() {
		return instance;
	}

	public Tessellator(int bufferSize) {
		this.worldRenderer = new WorldRenderer(bufferSize);
	}

	/**+
	 * Draws the data set up in this tessellator and resets the
	 * state to prepare for new drawing.
	 */
	public void draw() {
		this.worldRenderer.finishDrawing();
		this.vboUploader.func_181679_a(this.worldRenderer);
	}

	public WorldRenderer getWorldRenderer() {
		return this.worldRenderer;
	}
}