package net.minecraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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
public class ItemArmorStand extends Item {
	public ItemArmorStand() {
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float var6, float var7, float var8) {
		if (enumfacing == EnumFacing.DOWN) {
			return false;
		} else {
			boolean flag = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
			BlockPos blockpos1 = flag ? blockpos : blockpos.offset(enumfacing);
			if (!entityplayer.canPlayerEdit(blockpos1, enumfacing, itemstack)) {
				return false;
			} else {
				BlockPos blockpos2 = blockpos1.up();
				boolean flag1 = !world.isAirBlock(blockpos1)
						&& !world.getBlockState(blockpos1).getBlock().isReplaceable(world, blockpos1);
				flag1 = flag1 | (!world.isAirBlock(blockpos2)
						&& !world.getBlockState(blockpos2).getBlock().isReplaceable(world, blockpos2));
				if (flag1) {
					return false;
				} else {
					double d0 = (double) blockpos1.getX();
					double d1 = (double) blockpos1.getY();
					double d2 = (double) blockpos1.getZ();
					List list = world.getEntitiesWithinAABBExcludingEntity((Entity) null,
							AxisAlignedBB.fromBounds(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
					if (list.size() > 0) {
						return false;
					} else {
						--itemstack.stackSize;
						return true;
					}
				}
			}
		}
	}
}