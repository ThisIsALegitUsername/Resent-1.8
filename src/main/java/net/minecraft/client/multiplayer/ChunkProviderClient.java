package net.minecraft.client.multiplayer;

import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

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
public class ChunkProviderClient implements IChunkProvider {
	private static final Logger logger = LogManager.getLogger();
	private Chunk blankChunk;
	/**+
	 * The mapping between ChunkCoordinates and Chunks that
	 * ChunkProviderClient maintains.
	 */
	private LongHashMap<Chunk> chunkMapping = new LongHashMap();
	/**+
	 * This may have been intended to be an iterable version of all
	 * currently loaded chunks (MultiplayerChunkCache), with
	 * identical contents to chunkMapping's values. However it is
	 * never actually added to.
	 */
	private List<Chunk> chunkListing = Lists.newArrayList();
	private World worldObj;

	public ChunkProviderClient(World worldIn) {
		this.blankChunk = new EmptyChunk(worldIn, 0, 0);
		this.worldObj = worldIn;
	}

	/**+
	 * Checks to see if a chunk exists at x, z
	 */
	public boolean chunkExists(int var1, int var2) {
		return true;
	}

	/**+
	 * Unload chunk from ChunkProviderClient's hashmap. Called in
	 * response to a Packet50PreChunk with its mode field set to
	 * false
	 */
	public void unloadChunk(int parInt1, int parInt2) {
		Chunk chunk = this.provideChunk(parInt1, parInt2);
		if (!chunk.isEmpty()) {
			chunk.onChunkUnload();
		}

		this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(parInt1, parInt2));
		this.chunkListing.remove(chunk);
	}

	/**+
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(int parInt1, int parInt2) {
		Chunk chunk = new Chunk(this.worldObj, parInt1, parInt2);
		this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(parInt1, parInt2), chunk);
		this.chunkListing.add(chunk);
		chunk.setChunkLoaded(true);
		return chunk;
	}

	/**+
	 * Will return back a chunk, if it doesn't exist and its not a
	 * MP client it will generates all the blocks for the specified
	 * chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(int i, int j) {
		Chunk chunk = (Chunk) this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(i, j));
		return chunk == null ? this.blankChunk : chunk;
	}

	/**+
	 * Two modes of operation: if passed true, save all Chunks in
	 * one go. If passed false, save up to two chunks. Return true
	 * if all chunks have been saved.
	 */
	public boolean saveChunks(boolean var1, IProgressUpdate var2) {
		return true;
	}

	/**+
	 * Save extra data not associated with any Chunk. Not saved
	 * during autosave, only during world unload. Currently
	 * unimplemented.
	 */
	public void saveExtraData() {
	}

	/**+
	 * Unloads chunks that are marked to be unloaded. This is not
	 * guaranteed to unload every such chunk.
	 */
	public boolean unloadQueuedChunks() {
		long i = System.currentTimeMillis();

		for (Chunk chunk : this.chunkListing) {
			chunk.func_150804_b(System.currentTimeMillis() - i > 5L);
		}

		if (System.currentTimeMillis() - i > 100L) {
			logger.info("Warning: Clientside chunk ticking took {} ms",
					new Object[] { Long.valueOf(System.currentTimeMillis() - i) });
		}

		return false;
	}

	/**+
	 * Returns if the IChunkProvider supports saving.
	 */
	public boolean canSave() {
		return false;
	}

	/**+
	 * Populates chunk with ores etc etc
	 */
	public void populate(IChunkProvider var1, int var2, int var3) {
	}

	public boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4) {
		return false;
	}

	/**+
	 * Converts the instance data to a readable string.
	 */
	public String makeString() {
		return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
	}

	public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType var1, BlockPos var2) {
		return null;
	}

	public BlockPos getStrongholdGen(World var1, String var2, BlockPos var3) {
		return null;
	}

	public int getLoadedChunkCount() {
		return this.chunkListing.size();
	}

	public void recreateStructures(Chunk var1, int var2, int var3) {
	}

	/**+
	 * Will return back a chunk, if it doesn't exist and its not a
	 * MP client it will generates all the blocks for the specified
	 * chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(BlockPos blockpos) {
		return this.provideChunk(blockpos.getX() >> 4, blockpos.getZ() >> 4);
	}
}