package net.minecraft.world.chunk;

import java.util.List;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
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
public class EmptyChunk extends Chunk {
	public EmptyChunk(World worldIn, int x, int z) {
		super(worldIn, x, z);
	}

	/**+
	 * Checks whether the chunk is at the X/Z location specified
	 */
	public boolean isAtLocation(int x, int z) {
		return x == this.xPosition && z == this.zPosition;
	}

	/**+
	 * Returns the value in the height map at this x, z coordinate
	 * in the chunk
	 */
	public int getHeightValue(int x, int z) {
		return 0;
	}

	/**+
	 * Generates the height map for a chunk from scratch
	 */
	public void generateHeightMap() {
	}

	/**+
	 * Generates the initial skylight map for the chunk upon
	 * generation or load.
	 */
	public void generateSkylightMap() {
	}

	public Block getBlock(BlockPos pos) {
		return Blocks.air;
	}

	public int getBlockLightOpacity(BlockPos pos) {
		return 255;
	}

	public int getBlockMetadata(BlockPos pos) {
		return 0;
	}

	public int getLightFor(EnumSkyBlock pos, BlockPos parBlockPos) {
		return pos.defaultLightValue;
	}

	public void setLightFor(EnumSkyBlock pos, BlockPos value, int parInt1) {
	}

	public int getLightSubtracted(BlockPos pos, int amount) {
		return 0;
	}

	/**+
	 * Adds an entity to the chunk. Args: entity
	 */
	public void addEntity(Entity entityIn) {
	}

	/**+
	 * removes entity using its y chunk coordinate as its index
	 */
	public void removeEntity(Entity entityIn) {
	}

	/**+
	 * Removes entity at the specified index from the entity array.
	 */
	public void removeEntityAtIndex(Entity entityIn, int parInt1) {
	}

	public boolean canSeeSky(BlockPos pos) {
		return false;
	}

	public TileEntity getTileEntity(BlockPos pos, Chunk.EnumCreateEntityType parEnumCreateEntityType) {
		return null;
	}

	public void addTileEntity(TileEntity tileEntityIn) {
	}

	public void addTileEntity(BlockPos pos, TileEntity tileEntityIn) {
	}

	public void removeTileEntity(BlockPos pos) {
	}

	/**+
	 * Called when this Chunk is loaded by the ChunkProvider
	 */
	public void onChunkLoad() {
	}

	/**+
	 * Called when this Chunk is unloaded by the ChunkProvider
	 */
	public void onChunkUnload() {
	}

	/**+
	 * Sets the isModified flag for this Chunk
	 */
	public void setChunkModified() {
	}

	/**+
	 * Fills the given list of all entities that intersect within
	 * the given bounding box that aren't the passed entity.
	 */
	public void getEntitiesWithinAABBForEntity(Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill,
			Predicate<? super Entity> parPredicate) {
	}

	public <T extends Entity> void getEntitiesOfTypeWithinAAAB(Class<? extends T> entityClass, AxisAlignedBB aabb,
			List<T> listToFill, Predicate<? super T> parPredicate) {
	}

	/**+
	 * Returns true if this Chunk needs to be saved
	 */
	public boolean needsSaving(boolean parFlag) {
		return false;
	}

	public EaglercraftRandom getRandomWithSeed(long seed) {
		return new EaglercraftRandom(this.getWorld().getSeed() + (long) (this.xPosition * this.xPosition * 4987142)
				+ (long) (this.xPosition * 5947611) + (long) (this.zPosition * this.zPosition) * 4392871L
				+ (long) (this.zPosition * 389711) ^ seed);
	}

	public boolean isEmpty() {
		return true;
	}

	/**+
	 * Returns whether the ExtendedBlockStorages containing levels
	 * (in blocks) from arg 1 to arg 2 are fully empty (true) or not
	 * (false).
	 */
	public boolean getAreLevelsEmpty(int startY, int endY) {
		return true;
	}
}