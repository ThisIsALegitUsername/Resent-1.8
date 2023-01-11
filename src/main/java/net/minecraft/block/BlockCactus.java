package net.minecraft.block;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
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
public class BlockCactus extends Block {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

	protected BlockCactus() {
		super(Material.cactus);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	public void updateTick(World world, BlockPos blockpos, IBlockState iblockstate, EaglercraftRandom var4) {
		BlockPos blockpos1 = blockpos.up();
		if (world.isAirBlock(blockpos1)) {
			int i;
			for (i = 1; world.getBlockState(blockpos.down(i)).getBlock() == this; ++i) {
				;
			}

			if (i < 3) {
				int j = ((Integer) iblockstate.getValue(AGE)).intValue();
				if (j == 15) {
					world.setBlockState(blockpos1, this.getDefaultState());
					IBlockState iblockstate1 = iblockstate.withProperty(AGE, Integer.valueOf(0));
					world.setBlockState(blockpos, iblockstate1, 4);
					this.onNeighborBlockChange(world, blockpos1, iblockstate1, this);
				} else {
					world.setBlockState(blockpos, iblockstate.withProperty(AGE, Integer.valueOf(j + 1)), 4);
				}

			}
		}
	}

	public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos blockpos, IBlockState var3) {
		float f = 0.0625F;
		return new AxisAlignedBB((double) ((float) blockpos.getX() + f), (double) blockpos.getY(),
				(double) ((float) blockpos.getZ() + f), (double) ((float) (blockpos.getX() + 1) - f),
				(double) ((float) (blockpos.getY() + 1) - f), (double) ((float) (blockpos.getZ() + 1) - f));
	}

	public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos blockpos) {
		float f = 0.0625F;
		return new AxisAlignedBB((double) ((float) blockpos.getX() + f), (double) blockpos.getY(),
				(double) ((float) blockpos.getZ() + f), (double) ((float) (blockpos.getX() + 1) - f),
				(double) (blockpos.getY() + 1), (double) ((float) (blockpos.getZ() + 1) - f));
	}

	public boolean isFullCube() {
		return false;
	}

	/**+
	 * Used to determine ambient occlusion and culling when
	 * rebuilding chunks for render
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean canPlaceBlockAt(World world, BlockPos blockpos) {
		return super.canPlaceBlockAt(world, blockpos) ? this.canBlockStay(world, blockpos) : false;
	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState var3, Block var4) {
		if (!this.canBlockStay(world, blockpos)) {
			world.destroyBlock(blockpos, true);
		}

	}

	public boolean canBlockStay(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getMaterial().isSolid()) {
				return false;
			}
		}

		Block block = worldIn.getBlockState(pos.down()).getBlock();
		return block == Blocks.cactus || block == Blocks.sand;
	}

	/**+
	 * Called When an Entity Collided with the Block
	 */
	public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity entity) {
		entity.attackEntityFrom(DamageSource.cactus, 1.0F);
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