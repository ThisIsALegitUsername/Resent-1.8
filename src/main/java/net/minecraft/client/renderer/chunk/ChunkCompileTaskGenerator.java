package net.minecraft.client.renderer.chunk;

import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
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
public class ChunkCompileTaskGenerator {
	private final RenderChunk renderChunk;
	private final List<Runnable> listFinishRunnables = Lists.newArrayList();
	private final ChunkCompileTaskGenerator.Type type;
	private RegionRenderCacheBuilder regionRenderCacheBuilder;
	private CompiledChunk compiledChunk;
	private ChunkCompileTaskGenerator.Status status = ChunkCompileTaskGenerator.Status.PENDING;
	private boolean finished;
	public long goddamnFuckingTimeout = 0l;
	public long time = 0;

	public ChunkCompileTaskGenerator(RenderChunk renderChunkIn, ChunkCompileTaskGenerator.Type typeIn) {
		this.renderChunk = renderChunkIn;
		this.type = typeIn;
	}

	public ChunkCompileTaskGenerator.Status getStatus() {
		return this.status;
	}

	public RenderChunk getRenderChunk() {
		return this.renderChunk;
	}

	public CompiledChunk getCompiledChunk() {
		return this.compiledChunk;
	}

	public void setCompiledChunk(CompiledChunk compiledChunkIn) {
		this.compiledChunk = compiledChunkIn;
	}

	public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
		return this.regionRenderCacheBuilder;
	}

	public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
		this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
	}

	public void setStatus(ChunkCompileTaskGenerator.Status statusIn) {
		this.status = statusIn;
	}

	public void finish() {
		if (this.type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK
				&& this.status != ChunkCompileTaskGenerator.Status.DONE) {
			this.renderChunk.setNeedsUpdate(true);
		}

		this.finished = true;
		this.status = ChunkCompileTaskGenerator.Status.DONE;

		for (Runnable runnable : this.listFinishRunnables) {
			runnable.run();
		}
	}

	public void addFinishRunnable(Runnable parRunnable) {
		this.listFinishRunnables.add(parRunnable);
		if (this.finished) {
			parRunnable.run();
		}
	}

	public ChunkCompileTaskGenerator.Type getType() {
		return this.type;
	}

	public boolean isFinished() {
		return this.finished;
	}

	public boolean canExecuteYet() {
		if (this.type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
			CompiledChunk ch = this.renderChunk.getCompiledChunk();
			if (DeferredStateManager.isRenderingRealisticWater()) {
				return !ch.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)
						|| !ch.isLayerEmpty(EnumWorldBlockLayer.REALISTIC_WATER);
			} else {
				return !ch.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT);
			}
		} else {
			return true;
		}
	}

	public static enum Status {
		PENDING, COMPILING, UPLOADING, DONE;
	}

	public static enum Type {
		REBUILD_CHUNK, RESORT_TRANSPARENCY;
	}
}