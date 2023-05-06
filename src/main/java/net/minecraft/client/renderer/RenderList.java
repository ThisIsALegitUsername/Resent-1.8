package net.minecraft.client.renderer;

import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;

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
public class RenderList extends ChunkRenderContainer {
	public void renderChunkLayer(EnumWorldBlockLayer enumworldblocklayer) {
		if (this.initialized) {
			for (RenderChunk renderchunk : this.renderChunks) {
				ListedRenderChunk listedrenderchunk = (ListedRenderChunk) renderchunk;
				GlStateManager.pushMatrix();
				this.preRenderChunk(renderchunk, enumworldblocklayer);
				EaglercraftGPU.glCallList(
						listedrenderchunk.getDisplayList(enumworldblocklayer, listedrenderchunk.getCompiledChunk()));
				GlStateManager.popMatrix();
			}

			GlStateManager.resetColor();
			this.renderChunks.clear();
		}
	}
}