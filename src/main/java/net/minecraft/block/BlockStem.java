package net.minecraft.block;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import com.google.common.base.Predicate;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
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
public class BlockStem extends BlockBush implements IGrowable {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
		public boolean apply(EnumFacing enumfacing) {
			return enumfacing != EnumFacing.DOWN;
		}
	});
	private final Block crop;

	protected BlockStem(Block crop) {
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(FACING,
				EnumFacing.UP));
		this.crop = crop;
		this.setTickRandomly(true);
		float f = 0.125F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
		this.setCreativeTab((CreativeTabs) null);
	}

	/**+
	 * Get the actual Block state of this Block at the given
	 * position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
		iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);

		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			if (iblockaccess.getBlockState(blockpos.offset(enumfacing)).getBlock() == this.crop) {
				iblockstate = iblockstate.withProperty(FACING, enumfacing);
				break;
			}
		}

		return iblockstate;
	}

	/**+
	 * is the block grass, dirt or farmland
	 */
	protected boolean canPlaceBlockOn(Block block) {
		return block == Blocks.farmland;
	}

	public void updateTick(World world, BlockPos blockpos, IBlockState iblockstate, EaglercraftRandom random) {
		super.updateTick(world, blockpos, iblockstate, random);
		if (world.getLightFromNeighbors(blockpos.up()) >= 9) {
			float f = BlockCrops.getGrowthChance(this, world, blockpos);
			if (random.nextInt((int) (25.0F / f) + 1) == 0) {
				int i = ((Integer) iblockstate.getValue(AGE)).intValue();
				if (i < 7) {
					iblockstate = iblockstate.withProperty(AGE, Integer.valueOf(i + 1));
					world.setBlockState(blockpos, iblockstate, 2);
				} else {
					for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
						if (world.getBlockState(blockpos.offset(enumfacing)).getBlock() == this.crop) {
							return;
						}
					}

					blockpos = blockpos.offset(EnumFacing.Plane.HORIZONTAL.random(random));
					Block block = world.getBlockState(blockpos.down()).getBlock();
					if (world.getBlockState(blockpos).getBlock().blockMaterial == Material.air
							&& (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass)) {
						world.setBlockState(blockpos, this.crop.getDefaultState());
					}
				}
			}

		}
	}

	public void growStem(World worldIn, BlockPos pos, IBlockState state) {
		int i = ((Integer) state.getValue(AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
		worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(Math.min(7, i))), 2);
	}

	public int getRenderColor(IBlockState iblockstate) {
		if (iblockstate.getBlock() != this) {
			return super.getRenderColor(iblockstate);
		} else {
			int i = ((Integer) iblockstate.getValue(AGE)).intValue();
			int j = i * 32;
			int k = 255 - i * 8;
			int l = i * 4;
			return j << 16 | k << 8 | l;
		}
	}

	public int colorMultiplier(IBlockAccess iblockaccess, BlockPos blockpos, int var3) {
		return this.getRenderColor(iblockaccess.getBlockState(blockpos));
	}

	/**+
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender() {
		float f = 0.125F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos blockpos) {
		this.maxY = (double) ((float) (((Integer) iblockaccess.getBlockState(blockpos).getValue(AGE)).intValue() * 2
				+ 2) / 16.0F);
		float f = 0.125F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float) this.maxY, 0.5F + f);
	}

	protected Item getSeedItem() {
		return this.crop == Blocks.pumpkin ? Items.pumpkin_seeds
				: (this.crop == Blocks.melon_block ? Items.melon_seeds : null);
	}

	/**+
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState var1, EaglercraftRandom var2, int var3) {
		return null;
	}

	public Item getItem(World var1, BlockPos var2) {
		Item item = this.getSeedItem();
		return item != null ? item : null;
	}

	/**+
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(World var1, BlockPos var2, IBlockState iblockstate, boolean var4) {
		return ((Integer) iblockstate.getValue(AGE)).intValue() != 7;
	}

	public boolean canUseBonemeal(World var1, EaglercraftRandom var2, BlockPos var3, IBlockState var4) {
		return true;
	}

	public void grow(World world, EaglercraftRandom var2, BlockPos blockpos, IBlockState iblockstate) {
		this.growStem(world, blockpos, iblockstate);
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
		return new BlockState(this, new IProperty[] { AGE, FACING });
	}
}