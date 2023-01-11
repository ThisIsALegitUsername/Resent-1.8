package net.minecraft.block;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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
public class BlockMushroom extends BlockBush implements IGrowable {
	protected BlockMushroom() {
		float f = 0.2F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.setTickRandomly(true);
	}

	public void updateTick(World world, BlockPos blockpos, IBlockState var3, EaglercraftRandom random) {
		if (random.nextInt(25) == 0) {
			int i = 5;
			boolean flag = true;

			for (BlockPos blockpos1 : BlockPos.getAllInBoxMutable(blockpos.add(-4, -1, -4), blockpos.add(4, 1, 4))) {
				if (world.getBlockState(blockpos1).getBlock() == this) {
					--i;
					if (i <= 0) {
						return;
					}
				}
			}

			BlockPos blockpos2 = blockpos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2),
					random.nextInt(3) - 1);

			for (int j = 0; j < 4; ++j) {
				if (world.isAirBlock(blockpos2) && this.canBlockStay(world, blockpos2, this.getDefaultState())) {
					blockpos = blockpos2;
				}

				blockpos2 = blockpos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2),
						random.nextInt(3) - 1);
			}

			if (world.isAirBlock(blockpos2) && this.canBlockStay(world, blockpos2, this.getDefaultState())) {
				world.setBlockState(blockpos2, this.getDefaultState(), 2);
			}
		}

	}

	public boolean canPlaceBlockAt(World world, BlockPos blockpos) {
		return super.canPlaceBlockAt(world, blockpos) && this.canBlockStay(world, blockpos, this.getDefaultState());
	}

	/**+
	 * is the block grass, dirt or farmland
	 */
	protected boolean canPlaceBlockOn(Block block) {
		return block.isFullBlock();
	}

	public boolean canBlockStay(World world, BlockPos blockpos, IBlockState var3) {
		if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
			IBlockState iblockstate = world.getBlockState(blockpos.down());
			return iblockstate.getBlock() == Blocks.mycelium ? true
					: (iblockstate.getBlock() == Blocks.dirt
							&& iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL ? true
									: world.getLight(blockpos) < 13 && this.canPlaceBlockOn(iblockstate.getBlock()));
		} else {
			return false;
		}
	}

	/**+
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
		return true;
	}

	public boolean canUseBonemeal(World var1, EaglercraftRandom random, BlockPos var3, IBlockState var4) {
		return (double) random.nextFloat() < 0.4D;
	}
}