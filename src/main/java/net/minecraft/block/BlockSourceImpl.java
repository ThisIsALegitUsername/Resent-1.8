package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
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
public class BlockSourceImpl implements IBlockSource {
	private final World worldObj;
	private final BlockPos pos;

	public BlockSourceImpl(World worldIn, BlockPos posIn) {
		this.worldObj = worldIn;
		this.pos = posIn;
	}

	public World getWorld() {
		return this.worldObj;
	}

	public double getX() {
		return (double) this.pos.getX() + 0.5D;
	}

	public double getY() {
		return (double) this.pos.getY() + 0.5D;
	}

	public double getZ() {
		return (double) this.pos.getZ() + 0.5D;
	}

	public BlockPos getBlockPos() {
		return this.pos;
	}

	public int getBlockMetadata() {
		IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
		return iblockstate.getBlock().getMetaFromState(iblockstate);
	}

	public <T extends TileEntity> T getBlockTileEntity() {
		return (T) this.worldObj.getTileEntity(this.pos);
	}
}