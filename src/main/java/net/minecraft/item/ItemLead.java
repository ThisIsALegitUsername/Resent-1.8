package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
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
public class ItemLead extends Item {
	public ItemLead() {
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack var1, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing var5,
			float var6, float var7, float var8) {
		Block block = world.getBlockState(blockpos).getBlock();
		if (block instanceof BlockFence) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean attachToFence(EntityPlayer player, World worldIn, BlockPos fence) {
		EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(worldIn, fence);
		boolean flag = false;
		double d0 = 7.0D;
		int i = fence.getX();
		int j = fence.getY();
		int k = fence.getZ();

		for (EntityLiving entityliving : worldIn.getEntitiesWithinAABB(EntityLiving.class,
				new AxisAlignedBB((double) i - d0, (double) j - d0, (double) k - d0, (double) i + d0, (double) j + d0,
						(double) k + d0))) {
			if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == player) {
				if (entityleashknot == null) {
					entityleashknot = EntityLeashKnot.createKnot(worldIn, fence);
				}

				entityliving.setLeashedToEntity(entityleashknot, true);
				flag = true;
			}
		}

		return flag;
	}
}