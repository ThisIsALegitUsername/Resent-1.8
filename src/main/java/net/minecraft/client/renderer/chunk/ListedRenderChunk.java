package net.minecraft.client.renderer.chunk;

import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

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
public class ListedRenderChunk extends RenderChunk {
	private final int[] baseDisplayList;

	public ListedRenderChunk(World worldIn, RenderGlobal renderGlobalIn, BlockPos pos, int indexIn) {
		super(worldIn, renderGlobalIn, pos, indexIn);
		this.baseDisplayList = new int[EnumWorldBlockLayer.values().length];
		for (int i = 0; i < this.baseDisplayList.length; ++i) {
			this.baseDisplayList[i] = GLAllocation.generateDisplayLists();
		}
	}

	public int getDisplayList(EnumWorldBlockLayer layer, CompiledChunk parCompiledChunk) {
		return !parCompiledChunk.isLayerEmpty(layer) ? this.baseDisplayList[layer.ordinal()] : -1;
	}

	public void deleteGlResources() {
		super.deleteGlResources();
		for (int i = 0; i < this.baseDisplayList.length; ++i) {
			GLAllocation.deleteDisplayLists(this.baseDisplayList[i]);
		}
	}

	public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) {
		super.rebuildChunk(x, y, z, generator);
		EnumWorldBlockLayer[] layers = EnumWorldBlockLayer.values();
		for (int i = 0; i < layers.length; ++i) {
			if (generator.getCompiledChunk().isLayerEmpty(layers[i])) {
				EaglercraftGPU.flushDisplayList(this.baseDisplayList[i]);
			}
		}
	}
}