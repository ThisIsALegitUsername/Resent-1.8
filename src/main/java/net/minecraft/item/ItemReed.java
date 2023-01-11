package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
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
public class ItemReed extends Item {
	private Block block;

	public ItemReed(Block block) {
		this.block = block;
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float f, float f1, float f2) {
		IBlockState iblockstate = world.getBlockState(blockpos);
		Block blockx = iblockstate.getBlock();
		if (blockx == Blocks.snow_layer && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1) {
			enumfacing = EnumFacing.UP;
		} else if (!blockx.isReplaceable(world, blockpos)) {
			blockpos = blockpos.offset(enumfacing);
		}

		if (!entityplayer.canPlayerEdit(blockpos, enumfacing, itemstack)) {
			return false;
		} else if (itemstack.stackSize == 0) {
			return false;
		} else {
			if (world.canBlockBePlaced(this.block, blockpos, false, enumfacing, (Entity) null, itemstack)) {
				IBlockState iblockstate1 = this.block.onBlockPlaced(world, blockpos, enumfacing, f, f1, f2, 0,
						entityplayer);
				if (world.setBlockState(blockpos, iblockstate1, 3)) {
					iblockstate1 = world.getBlockState(blockpos);
					if (iblockstate1.getBlock() == this.block) {
						ItemBlock.setTileEntityNBT(world, entityplayer, blockpos, itemstack);
						iblockstate1.getBlock().onBlockPlacedBy(world, blockpos, iblockstate1, entityplayer, itemstack);
					}

					world.playSoundEffect((double) ((float) blockpos.getX() + 0.5F),
							(double) ((float) blockpos.getY() + 0.5F), (double) ((float) blockpos.getZ() + 0.5F),
							this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
							this.block.stepSound.getFrequency() * 0.8F);
					--itemstack.stackSize;
					return true;
				}
			}

			return false;
		}
	}
}