package net.minecraft.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;

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
public interface IBlockAccess {
	TileEntity getTileEntity(BlockPos var1);

	int getCombinedLight(BlockPos var1, int var2);

	IBlockState getBlockState(BlockPos var1);

	/**+
	 * Checks to see if an air block exists at the provided
	 * location. Note that this only checks to see if the blocks
	 * material is set to air, meaning it is possible for
	 * non-vanilla blocks to still pass this check.
	 */
	boolean isAirBlock(BlockPos var1);

	BiomeGenBase getBiomeGenForCoords(BlockPos var1);

	/**+
	 * set by !chunk.getAreLevelsEmpty
	 */
	boolean extendedLevelsInChunkCache();

	int getStrongPower(BlockPos var1, EnumFacing var2);

	WorldType getWorldType();
}