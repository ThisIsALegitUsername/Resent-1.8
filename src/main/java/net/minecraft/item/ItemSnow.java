package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
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
public class ItemSnow extends ItemBlock {
	public ItemSnow(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float f, float f1, float f2) {
		if (itemstack.stackSize == 0) {
			return false;
		} else if (!entityplayer.canPlayerEdit(blockpos, enumfacing, itemstack)) {
			return false;
		} else {
			IBlockState iblockstate = world.getBlockState(blockpos);
			Block block = iblockstate.getBlock();
			BlockPos blockpos1 = blockpos;
			if ((enumfacing != EnumFacing.UP || block != this.block) && !block.isReplaceable(world, blockpos)) {
				blockpos1 = blockpos.offset(enumfacing);
				iblockstate = world.getBlockState(blockpos1);
				block = iblockstate.getBlock();
			}

			if (block == this.block) {
				int i = ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue();
				if (i <= 7) {
					IBlockState iblockstate1 = iblockstate.withProperty(BlockSnow.LAYERS, Integer.valueOf(i + 1));
					AxisAlignedBB axisalignedbb = this.block.getCollisionBoundingBox(world, blockpos1, iblockstate1);
					if (axisalignedbb != null && world.checkNoEntityCollision(axisalignedbb)
							&& world.setBlockState(blockpos1, iblockstate1, 2)) {
						world.playSoundEffect((double) ((float) blockpos1.getX() + 0.5F),
								(double) ((float) blockpos1.getY() + 0.5F), (double) ((float) blockpos1.getZ() + 0.5F),
								this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
								this.block.stepSound.getFrequency() * 0.8F);
						--itemstack.stackSize;
						return true;
					}
				}
			}

			return super.onItemUse(itemstack, entityplayer, world, blockpos1, enumfacing, f, f1, f2);
		}
	}

	/**+
	 * Converts the given ItemStack damage value into a metadata
	 * value to be placed in the world when this Item is placed as a
	 * Block (mostly used with ItemBlocks).
	 */
	public int getMetadata(int i) {
		return i;
	}
}