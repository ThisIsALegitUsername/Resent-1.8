package net.minecraft.block;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

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
public class BlockGrass extends Block implements IGrowable {
	public static final PropertyBool SNOWY = PropertyBool.create("snowy");

	protected BlockGrass() {
		super(Material.grass);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SNOWY, Boolean.valueOf(false)));
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	/**+
	 * Get the actual Block state of this Block at the given
	 * position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
		Block block = iblockaccess.getBlockState(blockpos.up()).getBlock();
		return iblockstate.withProperty(SNOWY, Boolean.valueOf(block == Blocks.snow || block == Blocks.snow_layer));
	}

	public int getBlockColor() {
		return ColorizerGrass.getGrassColor(0.5D, 1.0D);
	}

	public int getRenderColor(IBlockState var1) {
		return this.getBlockColor();
	}

	public int colorMultiplier(IBlockAccess iblockaccess, BlockPos blockpos, int var3) {
		return BiomeColorHelper.getGrassColorAtPos(iblockaccess, blockpos);
	}

	/**+
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState var1, EaglercraftRandom random, int i) {
		return Blocks.dirt.getItemDropped(
				Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, i);
	}

	/**+
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
		return true;
	}

	public boolean canUseBonemeal(World var1, EaglercraftRandom var2, BlockPos var3, IBlockState var4) {
		return true;
	}

	public void grow(World world, EaglercraftRandom random, BlockPos blockpos, IBlockState var4) {
		BlockPos blockpos1 = blockpos.up();

		for (int i = 0; i < 128; ++i) {
			BlockPos blockpos2 = blockpos1;
			int j = 0;

			while (true) {
				if (j >= i / 16) {
					if (world.getBlockState(blockpos2).getBlock().blockMaterial == Material.air) {
						if (random.nextInt(8) == 0) {
							BlockFlower.EnumFlowerType blockflower$enumflowertype = world
									.getBiomeGenForCoords(blockpos2).pickRandomFlower(random, blockpos2);
							BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
							IBlockState iblockstate = blockflower.getDefaultState()
									.withProperty(blockflower.getTypeProperty(), blockflower$enumflowertype);
							if (blockflower.canBlockStay(world, blockpos2, iblockstate)) {
								world.setBlockState(blockpos2, iblockstate, 3);
							}
						} else {
							IBlockState iblockstate1 = Blocks.tallgrass.getDefaultState()
									.withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
							if (Blocks.tallgrass.canBlockStay(world, blockpos2, iblockstate1)) {
								world.setBlockState(blockpos2, iblockstate1, 3);
							}
						}
					}
					break;
				}

				blockpos2 = blockpos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2,
						random.nextInt(3) - 1);
				if (world.getBlockState(blockpos2.down()).getBlock() != Blocks.grass
						|| world.getBlockState(blockpos2).getBlock().isNormalCube()) {
					break;
				}

				++j;
			}
		}

	}

	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState var1) {
		return 0;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { SNOWY });
	}
}