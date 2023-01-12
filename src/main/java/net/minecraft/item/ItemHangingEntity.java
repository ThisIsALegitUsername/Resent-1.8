package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
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
public class ItemHangingEntity extends Item {
	private final Class<? extends EntityHanging> hangingEntityClass;

	public ItemHangingEntity(Class<? extends EntityHanging> entityClass) {
		this.hangingEntityClass = entityClass;
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float var6, float var7, float var8) {
		if (enumfacing == EnumFacing.DOWN) {
			return false;
		} else if (enumfacing == EnumFacing.UP) {
			return false;
		} else {
			BlockPos blockpos1 = blockpos.offset(enumfacing);
			if (!entityplayer.canPlayerEdit(blockpos1, enumfacing, itemstack)) {
				return false;
			} else {
				EntityHanging entityhanging = this.createEntity(world, blockpos1, enumfacing);
				if (entityhanging != null && entityhanging.onValidSurface()) {
					--itemstack.stackSize;
				}

				return true;
			}
		}
	}

	private EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
		return (EntityHanging) (this.hangingEntityClass == EntityPainting.class
				? new EntityPainting(worldIn, pos, clickedSide)
				: (this.hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(worldIn, pos, clickedSide)
						: null));
	}
}