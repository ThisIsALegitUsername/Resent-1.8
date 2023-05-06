package net.minecraft.client.renderer.chunk;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.minecraft.ChunkUpdateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.entity.Entity;
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
public class ChunkRenderWorker {
	private static final Logger LOGGER = LogManager.getLogger();
	private final ChunkUpdateManager chunkRenderDispatcher;
	private final RegionRenderCacheBuilder regionRenderCacheBuilder;

	public ChunkRenderWorker(ChunkUpdateManager parChunkRenderDispatcher) {
		this(parChunkRenderDispatcher, (RegionRenderCacheBuilder) null);
	}

	public ChunkRenderWorker(ChunkUpdateManager chunkRenderDispatcherIn,
			RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
		this.chunkRenderDispatcher = chunkRenderDispatcherIn;
		this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
	}

	protected void processTask(final ChunkCompileTaskGenerator generator) throws InterruptedException {
		if (generator.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
			if (!generator.isFinished()) {
				LOGGER.warn("Chunk render task was " + generator.getStatus()
						+ " when I expected it to be pending; ignoring task");
			}

			return;
		}

		generator.setStatus(ChunkCompileTaskGenerator.Status.COMPILING);

		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		if (entity == null) {
			generator.finish();
		} else {
			generator.setRegionRenderCacheBuilder(this.getRegionRenderCacheBuilder());
			float f = (float) entity.posX;
			float f1 = (float) entity.posY + entity.getEyeHeight();
			float f2 = (float) entity.posZ;
			ChunkCompileTaskGenerator.Type chunkcompiletaskgenerator$type = generator.getType();
			if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
				generator.getRenderChunk().rebuildChunk(f, f1, f2, generator);
			} else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
				generator.getRenderChunk().resortTransparency(f, f1, f2, generator);
			}

			if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
				if (!generator.isFinished()) {
					LOGGER.warn("Chunk render task was " + generator.getStatus()
							+ " when I expected it to be compiling; aborting task");
				}

				this.freeRenderBuilder(generator);
				return;
			}

			generator.setStatus(ChunkCompileTaskGenerator.Status.UPLOADING);

			final CompiledChunk compiledchunk = generator.getCompiledChunk();
			if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
				for (EnumWorldBlockLayer enumworldblocklayer : EnumWorldBlockLayer.values()) {
					if (!compiledchunk.isLayerEmpty(enumworldblocklayer)) {
						this.chunkRenderDispatcher.uploadChunk(enumworldblocklayer,
								generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer),
								generator.getRenderChunk(), compiledchunk);
						generator.getRenderChunk().setCompiledChunk(compiledchunk);
						generator.setStatus(ChunkCompileTaskGenerator.Status.DONE);
					}
				}
			} else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
				this.chunkRenderDispatcher.uploadChunk(
						EnumWorldBlockLayer.TRANSLUCENT, generator.getRegionRenderCacheBuilder()
								.getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT),
						generator.getRenderChunk(), compiledchunk);
				if (DeferredStateManager.isRenderingRealisticWater()) {
					this.chunkRenderDispatcher.uploadChunk(
							EnumWorldBlockLayer.REALISTIC_WATER, generator.getRegionRenderCacheBuilder()
									.getWorldRendererByLayer(EnumWorldBlockLayer.REALISTIC_WATER),
							generator.getRenderChunk(), compiledchunk);
				}
				generator.getRenderChunk().setCompiledChunk(compiledchunk);
				generator.setStatus(ChunkCompileTaskGenerator.Status.DONE);
			}

		}
	}

	private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
		return this.regionRenderCacheBuilder;
	}

	private void freeRenderBuilder(ChunkCompileTaskGenerator taskGenerator) {

	}
}