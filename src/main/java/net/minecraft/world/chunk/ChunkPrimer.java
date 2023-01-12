package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

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
public class ChunkPrimer {
	private final short[] data = new short[65536];
	private final IBlockState defaultState = Blocks.air.getDefaultState();

	public IBlockState getBlockState(int x, int y, int z) {
		int i = x << 12 | z << 8 | y;
		return this.getBlockState(i);
	}

	public IBlockState getBlockState(int index) {
		if (index >= 0 && index < this.data.length) {
			IBlockState iblockstate = (IBlockState) Block.BLOCK_STATE_IDS.getByValue(this.data[index]);
			return iblockstate != null ? iblockstate : this.defaultState;
		} else {
			throw new IndexOutOfBoundsException("The coordinate is out of range");
		}
	}

	public void setBlockState(int x, int y, int z, IBlockState state) {
		int i = x << 12 | z << 8 | y;
		this.setBlockState(i, state);
	}

	public void setBlockState(int index, IBlockState state) {
		if (index >= 0 && index < this.data.length) {
			this.data[index] = (short) Block.BLOCK_STATE_IDS.get(state);
		} else {
			throw new IndexOutOfBoundsException("The coordinate is out of range");
		}
	}
}