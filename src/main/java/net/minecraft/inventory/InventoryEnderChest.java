package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

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
public class InventoryEnderChest extends InventoryBasic {
	private TileEntityEnderChest associatedChest;

	public InventoryEnderChest() {
		super("container.enderchest", false, 27);
	}

	public void setChestTileEntity(TileEntityEnderChest chestTileEntity) {
		this.associatedChest = chestTileEntity;
	}

	public void loadInventoryFromNBT(NBTTagList parNBTTagList) {
		for (int i = 0; i < this.getSizeInventory(); ++i) {
			this.setInventorySlotContents(i, (ItemStack) null);
		}

		for (int k = 0; k < parNBTTagList.tagCount(); ++k) {
			NBTTagCompound nbttagcompound = parNBTTagList.getCompoundTagAt(k);
			int j = nbttagcompound.getByte("Slot") & 255;
			if (j >= 0 && j < this.getSizeInventory()) {
				this.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
			}
		}

	}

	public NBTTagList saveInventoryToNBT() {
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.getSizeInventory(); ++i) {
			ItemStack itemstack = this.getStackInSlot(i);
			if (itemstack != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				itemstack.writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		return nbttaglist;
	}

	/**+
	 * Do not make give this method the name canInteractWith because
	 * it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.associatedChest != null && !this.associatedChest.canBeUsed(entityplayer) ? false
				: super.isUseableByPlayer(entityplayer);
	}

	public void openInventory(EntityPlayer entityplayer) {
		if (this.associatedChest != null) {
			this.associatedChest.openChest();
		}

		super.openInventory(entityplayer);
	}

	public void closeInventory(EntityPlayer entityplayer) {
		if (this.associatedChest != null) {
			this.associatedChest.closeChest();
		}

		super.closeInventory(entityplayer);
		this.associatedChest = null;
	}
}