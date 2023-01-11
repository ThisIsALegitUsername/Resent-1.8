package net.minecraft.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
public class ItemBlock extends Item {
	protected final Block block;

	public ItemBlock(Block block) {
		this.block = block;
	}

	/**+
	 * Sets the unlocalized name of this item to the string passed
	 * as the parameter, prefixed by "item."
	 */
	public ItemBlock setUnlocalizedName(String unlocalizedName) {
		super.setUnlocalizedName(unlocalizedName);
		return this;
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float f, float f1, float f2) {
		IBlockState iblockstate = world.getBlockState(blockpos);
		Block blockx = iblockstate.getBlock();
		if (!blockx.isReplaceable(world, blockpos)) {
			blockpos = blockpos.offset(enumfacing);
		}

		if (itemstack.stackSize == 0) {
			return false;
		} else if (!entityplayer.canPlayerEdit(blockpos, enumfacing, itemstack)) {
			return false;
		} else if (world.canBlockBePlaced(this.block, blockpos, false, enumfacing, (Entity) null, itemstack)) {
			int i = this.getMetadata(itemstack.getMetadata());
			IBlockState iblockstate1 = this.block.onBlockPlaced(world, blockpos, enumfacing, f, f1, f2, i,
					entityplayer);
			if (world.setBlockState(blockpos, iblockstate1, 3)) {
				iblockstate1 = world.getBlockState(blockpos);
				if (iblockstate1.getBlock() == this.block) {
					setTileEntityNBT(world, entityplayer, blockpos, itemstack);
					this.block.onBlockPlacedBy(world, blockpos, iblockstate1, entityplayer, itemstack);
				}

				world.playSoundEffect((double) ((float) blockpos.getX() + 0.5F),
						(double) ((float) blockpos.getY() + 0.5F), (double) ((float) blockpos.getZ() + 0.5F),
						this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
						this.block.stepSound.getFrequency() * 0.8F);
				--itemstack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public static boolean setTileEntityNBT(World worldIn, EntityPlayer pos, BlockPos stack, ItemStack parItemStack) {
		return false;
	}

	public boolean canPlaceBlockOnSide(World world, BlockPos blockpos, EnumFacing enumfacing, EntityPlayer var4,
			ItemStack itemstack) {
		Block blockx = world.getBlockState(blockpos).getBlock();
		if (blockx == Blocks.snow_layer) {
			enumfacing = EnumFacing.UP;
		} else if (!blockx.isReplaceable(world, blockpos)) {
			blockpos = blockpos.offset(enumfacing);
		}

		return world.canBlockBePlaced(this.block, blockpos, false, enumfacing, (Entity) null, itemstack);
	}

	/**+
	 * Returns the unlocalized name of this item.
	 */
	public String getUnlocalizedName(ItemStack var1) {
		return this.block.getUnlocalizedName();
	}

	/**+
	 * Returns the unlocalized name of this item.
	 */
	public String getUnlocalizedName() {
		return this.block.getUnlocalizedName();
	}

	/**+
	 * gets the CreativeTab this item is displayed on
	 */
	public CreativeTabs getCreativeTab() {
		return this.block.getCreativeTabToDisplayOn();
	}

	/**+
	 * returns a list of items with the same ID, but different meta
	 * (eg: dye returns 16 items)
	 */
	public void getSubItems(Item item, CreativeTabs creativetabs, List<ItemStack> list) {
		this.block.getSubBlocks(item, creativetabs, list);
	}

	public Block getBlock() {
		return this.block;
	}
}