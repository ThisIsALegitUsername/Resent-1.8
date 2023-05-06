package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

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
public class ChunkCache implements IBlockAccess {
	protected int chunkX;
	protected int chunkZ;
	protected Chunk[][] chunkArray;
	protected boolean hasExtendedLevels;
	protected World worldObj;

	public ChunkCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
		this.worldObj = worldIn;
		this.chunkX = posFromIn.getX() - subIn >> 4;
		this.chunkZ = posFromIn.getZ() - subIn >> 4;
		int i = posToIn.getX() + subIn >> 4;
		int j = posToIn.getZ() + subIn >> 4;
		this.chunkArray = new Chunk[i - this.chunkX + 1][j - this.chunkZ + 1];
		this.hasExtendedLevels = true;

		for (int k = this.chunkX; k <= i; ++k) {
			for (int l = this.chunkZ; l <= j; ++l) {
				this.chunkArray[k - this.chunkX][l - this.chunkZ] = worldIn.getChunkFromChunkCoords(k, l);
			}
		}

		for (int i1 = posFromIn.getX() >> 4; i1 <= posToIn.getX() >> 4; ++i1) {
			for (int j1 = posFromIn.getZ() >> 4; j1 <= posToIn.getZ() >> 4; ++j1) {
				Chunk chunk = this.chunkArray[i1 - this.chunkX][j1 - this.chunkZ];
				if (chunk != null && !chunk.getAreLevelsEmpty(posFromIn.getY(), posToIn.getY())) {
					this.hasExtendedLevels = false;
				}
			}
		}

	}

	/**+
	 * set by !chunk.getAreLevelsEmpty
	 */
	public boolean extendedLevelsInChunkCache() {
		return this.hasExtendedLevels;
	}

	public TileEntity getTileEntity(BlockPos blockpos) {
		int i = (blockpos.getX() >> 4) - this.chunkX;
		int j = (blockpos.getZ() >> 4) - this.chunkZ;
		return this.chunkArray[i][j].getTileEntity(blockpos, Chunk.EnumCreateEntityType.IMMEDIATE);
	}

	public int getCombinedLight(BlockPos blockpos, int i) {
		int j = this.getLightForExt(EnumSkyBlock.SKY, blockpos);
		int k = this.getLightForExt(EnumSkyBlock.BLOCK, blockpos);
		if (k < i) {
			k = i;
		}

		return j << 20 | k << 4;
	}

	public IBlockState getBlockState(BlockPos blockpos) {
		if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
			int i = (blockpos.getX() >> 4) - this.chunkX;
			int j = (blockpos.getZ() >> 4) - this.chunkZ;
			if (i >= 0 && i < this.chunkArray.length && j >= 0 && j < this.chunkArray[i].length) {
				Chunk chunk = this.chunkArray[i][j];
				if (chunk != null) {
					return chunk.getBlockState(blockpos);
				}
			}
		}

		return Blocks.air.getDefaultState();
	}

	public BiomeGenBase getBiomeGenForCoords(BlockPos blockpos) {
		return this.worldObj.getBiomeGenForCoords(blockpos);
	}

	private int getLightForExt(EnumSkyBlock pos, BlockPos parBlockPos) {
		if (pos == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky()) {
			return Chunk.getNoSkyLightValue();
		} else if (parBlockPos.getY() >= 0 && parBlockPos.getY() < 256) {
			if (this.getBlockState(parBlockPos).getBlock().getUseNeighborBrightness()) {
				int l = 0;

				for (EnumFacing enumfacing : EnumFacing.values()) {
					int k = this.getLightFor(pos, parBlockPos.offset(enumfacing));
					if (k > l) {
						l = k;
					}

					if (l >= 15) {
						return l;
					}
				}

				return l;
			} else {
				int i = (parBlockPos.getX() >> 4) - this.chunkX;
				int j = (parBlockPos.getZ() >> 4) - this.chunkZ;
				return this.chunkArray[i][j].getLightFor(pos, parBlockPos);
			}
		} else {
			return pos.defaultLightValue;
		}
	}

	/**+
	 * Checks to see if an air block exists at the provided
	 * location. Note that this only checks to see if the blocks
	 * material is set to air, meaning it is possible for
	 * non-vanilla blocks to still pass this check.
	 */
	public boolean isAirBlock(BlockPos blockpos) {
		return this.getBlockState(blockpos).getBlock().getMaterial() == Material.air;
	}

	public int getLightFor(EnumSkyBlock pos, BlockPos parBlockPos) {
		if (parBlockPos.getY() >= 0 && parBlockPos.getY() < 256) {
			int i = (parBlockPos.getX() >> 4) - this.chunkX;
			int j = (parBlockPos.getZ() >> 4) - this.chunkZ;
			return this.chunkArray[i][j].getLightFor(pos, parBlockPos);
		} else {
			return pos.defaultLightValue;
		}
	}

	public int getStrongPower(BlockPos blockpos, EnumFacing enumfacing) {
		IBlockState iblockstate = this.getBlockState(blockpos);
		return iblockstate.getBlock().getStrongPower(this, blockpos, iblockstate, enumfacing);
	}

	public WorldType getWorldType() {
		return this.worldObj.getWorldType();
	}
}