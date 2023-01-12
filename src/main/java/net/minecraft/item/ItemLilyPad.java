package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
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
public class ItemLilyPad extends ItemColored {
	public ItemLilyPad(Block block) {
		super(block, false);
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, entityplayer, true);
		if (movingobjectposition == null) {
			return itemstack;
		} else {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				BlockPos blockpos = movingobjectposition.getBlockPos();
				if (!world.isBlockModifiable(entityplayer, blockpos)) {
					return itemstack;
				}

				if (!entityplayer.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit),
						movingobjectposition.sideHit, itemstack)) {
					return itemstack;
				}

				BlockPos blockpos1 = blockpos.up();
				IBlockState iblockstate = world.getBlockState(blockpos);
				if (iblockstate.getBlock().getMaterial() == Material.water
						&& ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0
						&& world.isAirBlock(blockpos1)) {
					world.setBlockState(blockpos1, Blocks.waterlily.getDefaultState());
					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
					}

					entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
				}
			}

			return itemstack;
		}
	}

	public int getColorFromItemStack(ItemStack itemstack, int var2) {
		return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(itemstack.getMetadata()));
	}
}