package net.minecraft.item;

import net.minecraft.block.Block;
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
public class ItemRedstone extends Item {
	public ItemRedstone() {
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float var6, float var7, float var8) {
		boolean flag = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
		BlockPos blockpos1 = flag ? blockpos : blockpos.offset(enumfacing);
		if (!entityplayer.canPlayerEdit(blockpos1, enumfacing, itemstack)) {
			return false;
		} else {
			Block block = world.getBlockState(blockpos1).getBlock();
			if (!world.canBlockBePlaced(block, blockpos1, false, enumfacing, (Entity) null, itemstack)) {
				return false;
			} else if (Blocks.redstone_wire.canPlaceBlockAt(world, blockpos1)) {
				--itemstack.stackSize;
				world.setBlockState(blockpos1, Blocks.redstone_wire.getDefaultState());
				return true;
			} else {
				return false;
			}
		}
	}
}