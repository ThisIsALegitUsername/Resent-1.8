package net.minecraft.world;

import net.minecraft.util.BlockPos;

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
public class ChunkCoordIntPair {
	public final int chunkXPos;
	public final int chunkZPos;

	public ChunkCoordIntPair(int x, int z) {
		this.chunkXPos = x;
		this.chunkZPos = z;
	}

	/**+
	 * converts a chunk coordinate pair to an integer (suitable for
	 * hashing)
	 */
	public static long chunkXZ2Int(int x, int z) {
		return (long) x & 4294967295L | ((long) z & 4294967295L) << 32;
	}

	public int hashCode() {
		int i = 1664525 * this.chunkXPos + 1013904223;
		int j = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
		return i ^ j;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ChunkCoordIntPair)) {
			return false;
		} else {
			ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) object;
			return this.chunkXPos == chunkcoordintpair.chunkXPos && this.chunkZPos == chunkcoordintpair.chunkZPos;
		}
	}

	public int getCenterXPos() {
		return (this.chunkXPos << 4) + 8;
	}

	public int getCenterZPosition() {
		return (this.chunkZPos << 4) + 8;
	}

	/**+
	 * Get the first world X coordinate that belongs to this Chunk
	 */
	public int getXStart() {
		return this.chunkXPos << 4;
	}

	/**+
	 * Get the first world Z coordinate that belongs to this Chunk
	 */
	public int getZStart() {
		return this.chunkZPos << 4;
	}

	/**+
	 * Get the last world X coordinate that belongs to this Chunk
	 */
	public int getXEnd() {
		return (this.chunkXPos << 4) + 15;
	}

	/**+
	 * Get the last world Z coordinate that belongs to this Chunk
	 */
	public int getZEnd() {
		return (this.chunkZPos << 4) + 15;
	}

	/**+
	 * Get the World coordinates of the Block with the given Chunk
	 * coordinates relative to this chunk
	 */
	public BlockPos getBlock(int x, int y, int z) {
		return new BlockPos((this.chunkXPos << 4) + x, y, (this.chunkZPos << 4) + z);
	}

	/**+
	 * Get the coordinates of the Block in the center of this chunk
	 * with the given Y coordinate
	 */
	public BlockPos getCenterBlock(int y) {
		return new BlockPos(this.getCenterXPos(), y, this.getCenterZPosition());
	}

	public String toString() {
		return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
	}
}