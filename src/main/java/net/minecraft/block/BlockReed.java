package net.minecraft.block;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
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
public class BlockReed extends Block {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

	protected BlockReed() {
		super(Material.plants);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		float f = 0.375F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
		this.setTickRandomly(true);
	}

	public void updateTick(World world, BlockPos blockpos, IBlockState iblockstate, EaglercraftRandom var4) {
		if (world.getBlockState(blockpos.down()).getBlock() == Blocks.reeds
				|| this.checkForDrop(world, blockpos, iblockstate)) {
			if (world.isAirBlock(blockpos.up())) {
				int i;
				for (i = 1; world.getBlockState(blockpos.down(i)).getBlock() == this; ++i) {
					;
				}

				if (i < 3) {
					int j = ((Integer) iblockstate.getValue(AGE)).intValue();
					if (j == 15) {
						world.setBlockState(blockpos.up(), this.getDefaultState());
						world.setBlockState(blockpos, iblockstate.withProperty(AGE, Integer.valueOf(0)), 4);
					} else {
						world.setBlockState(blockpos, iblockstate.withProperty(AGE, Integer.valueOf(j + 1)), 4);
					}
				}
			}

		}
	}

	public boolean canPlaceBlockAt(World world, BlockPos blockpos) {
		Block block = world.getBlockState(blockpos.down()).getBlock();
		if (block == this) {
			return true;
		} else if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand) {
			return false;
		} else {
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
				if (world.getBlockState(blockpos.offset(enumfacing).down()).getBlock()
						.getMaterial() == Material.water) {
					return true;
				}
			}

			return false;
		}
	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState iblockstate, Block var4) {
		this.checkForDrop(world, blockpos, iblockstate);
	}

	protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (this.canBlockStay(worldIn, pos)) {
			return true;
		} else {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos) {
		return this.canPlaceBlockAt(worldIn, pos);
	}

	public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
		return null;
	}

	/**+
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState var1, EaglercraftRandom var2, int var3) {
		return Items.reeds;
	}

	/**+
	 * Used to determine ambient occlusion and culling when
	 * rebuilding chunks for render
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	public Item getItem(World var1, BlockPos var2) {
		return Items.reeds;
	}

	public int colorMultiplier(IBlockAccess iblockaccess, BlockPos blockpos, int var3) {
		return iblockaccess.getBiomeGenForCoords(blockpos).getGrassColorAtPos(blockpos);
	}

	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(AGE, Integer.valueOf(i));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((Integer) iblockstate.getValue(AGE)).intValue();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE });
	}
}