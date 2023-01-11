package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
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
public class BlockLadder extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	protected BlockLadder() {
		super(Material.circuits);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockpos, IBlockState iblockstate) {
		this.setBlockBoundsBasedOnState(world, blockpos);
		return super.getCollisionBoundingBox(world, blockpos, iblockstate);
	}

	public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockpos) {
		this.setBlockBoundsBasedOnState(world, blockpos);
		return super.getSelectedBoundingBox(world, blockpos);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos blockpos) {
		IBlockState iblockstate = iblockaccess.getBlockState(blockpos);
		if (iblockstate.getBlock() == this) {
			float f = 0.125F;
			switch ((EnumFacing) iblockstate.getValue(FACING)) {
			case NORTH:
				this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
				break;
			case SOUTH:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
				break;
			case WEST:
				this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;
			case EAST:
			default:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
			}

		}
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

	public boolean canPlaceBlockAt(World world, BlockPos blockpos) {
		return world.getBlockState(blockpos.west()).getBlock().isNormalCube() ? true
				: (world.getBlockState(blockpos.east()).getBlock().isNormalCube() ? true
						: (world.getBlockState(blockpos.north()).getBlock().isNormalCube() ? true
								: world.getBlockState(blockpos.south()).getBlock().isNormalCube()));
	}

	/**+
	 * Called by ItemBlocks just before a block is actually set in
	 * the world, to allow for adjustments to the IBlockstate
	 */
	public IBlockState onBlockPlaced(World world, BlockPos blockpos, EnumFacing enumfacing, float var4, float var5,
			float var6, int var7, EntityLivingBase var8) {
		if (enumfacing.getAxis().isHorizontal() && this.canBlockStay(world, blockpos, enumfacing)) {
			return this.getDefaultState().withProperty(FACING, enumfacing);
		} else {
			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
				if (this.canBlockStay(world, blockpos, enumfacing1)) {
					return this.getDefaultState().withProperty(FACING, enumfacing1);
				}
			}

			return this.getDefaultState();
		}
	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
		EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);
		if (!this.canBlockStay(world, blockpos, enumfacing)) {
			this.dropBlockAsItem(world, blockpos, iblockstate, 0);
			world.setBlockToAir(blockpos);
		}

		super.onNeighborBlockChange(world, blockpos, iblockstate, block);
	}

	protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) {
		return worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock().isNormalCube();
	}

	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		EnumFacing enumfacing = EnumFacing.getFront(i);
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((EnumFacing) iblockstate.getValue(FACING)).getIndex();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}
}