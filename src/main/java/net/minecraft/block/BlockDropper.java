package net.minecraft.block;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityHopper;
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
public class BlockDropper extends BlockDispenser {
	private final IBehaviorDispenseItem dropBehavior = new BehaviorDefaultDispenseItem();

	protected IBehaviorDispenseItem getBehavior(ItemStack var1) {
		return this.dropBehavior;
	}

	/**+
	 * Returns a new instance of a block's tile entity class. Called
	 * on placing the block.
	 */
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDropper();
	}

	protected void dispense(World world, BlockPos blockpos) {
		BlockSourceImpl blocksourceimpl = new BlockSourceImpl(world, blockpos);
		TileEntityDispenser tileentitydispenser = (TileEntityDispenser) blocksourceimpl.getBlockTileEntity();
		if (tileentitydispenser != null) {
			int i = tileentitydispenser.getDispenseSlot();
			if (i < 0) {
				world.playAuxSFX(1001, blockpos, 0);
			} else {
				ItemStack itemstack = tileentitydispenser.getStackInSlot(i);
				if (itemstack != null) {
					EnumFacing enumfacing = (EnumFacing) world.getBlockState(blockpos).getValue(FACING);
					BlockPos blockpos1 = blockpos.offset(enumfacing);
					IInventory iinventory = TileEntityHopper.getInventoryAtPosition(world, (double) blockpos1.getX(),
							(double) blockpos1.getY(), (double) blockpos1.getZ());
					ItemStack itemstack1;
					if (iinventory == null) {
						itemstack1 = this.dropBehavior.dispense(blocksourceimpl, itemstack);
						if (itemstack1 != null && itemstack1.stackSize <= 0) {
							itemstack1 = null;
						}
					} else {
						itemstack1 = TileEntityHopper.putStackInInventoryAllSlots(iinventory,
								itemstack.copy().splitStack(1), enumfacing.getOpposite());
						if (itemstack1 == null) {
							itemstack1 = itemstack.copy();
							if (--itemstack1.stackSize <= 0) {
								itemstack1 = null;
							}
						} else {
							itemstack1 = itemstack.copy();
						}
					}

					tileentitydispenser.setInventorySlotContents(i, itemstack1);
				}
			}
		}
	}
}