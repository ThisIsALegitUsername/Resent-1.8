package net.minecraft.block;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
public class BlockCarpet extends Block {
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor>create("color",
			EnumDyeColor.class);

	protected BlockCarpet() {
		super(Material.carpet);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBoundsFromMeta(0);
	}

	/**+
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState iblockstate) {
		return ((EnumDyeColor) iblockstate.getValue(COLOR)).getMapColor();
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

	/**+
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender() {
		this.setBlockBoundsFromMeta(0);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
		this.setBlockBoundsFromMeta(0);
	}

	protected void setBlockBoundsFromMeta(int meta) {
		byte b0 = 0;
		float f = (float) (1 * (1 + b0)) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
	}

	public boolean canPlaceBlockAt(World world, BlockPos blockpos) {
		return super.canPlaceBlockAt(world, blockpos) && this.canBlockStay(world, blockpos);
	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState iblockstate, Block var4) {
		this.checkForDrop(world, blockpos, iblockstate);
	}

	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		} else {
			return true;
		}
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return !worldIn.isAirBlock(pos.down());
	}

	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, BlockPos blockpos, EnumFacing enumfacing) {
		return enumfacing == EnumFacing.UP ? true : super.shouldSideBeRendered(iblockaccess, blockpos, enumfacing);
	}

	/**+
	 * Gets the metadata of the item this Block can drop. This
	 * method is called when the block gets destroyed. It returns
	 * the metadata of the dropped item based on the old metadata of
	 * the block.
	 */
	public int damageDropped(IBlockState iblockstate) {
		return ((EnumDyeColor) iblockstate.getValue(COLOR)).getMetadata();
	}

	/**+
	 * returns a list of blocks with the same ID, but different meta
	 * (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (int i = 0; i < 16; ++i) {
			list.add(new ItemStack(item, 1, i));
		}

	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(i));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((EnumDyeColor) iblockstate.getValue(COLOR)).getMetadata();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { COLOR });
	}
}