package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
public class ContainerChest extends Container {
	private IInventory lowerChestInventory;
	private int numRows;

	public ContainerChest(IInventory playerInventory, IInventory chestInventory, EntityPlayer player) {
		this.lowerChestInventory = chestInventory;
		this.numRows = chestInventory.getSizeInventory() / 9;
		chestInventory.openInventory(player);
		int i = (this.numRows - 4) * 18;

		for (int j = 0; j < this.numRows; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(chestInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}

		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.lowerChestInventory.isUseableByPlayer(entityplayer);
	}

	/**+
	 * Take a stack from the specified inventory slot.
	 */
	public ItemStack transferStackInSlot(EntityPlayer var1, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < this.numRows * 9) {
				if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	/**+
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		this.lowerChestInventory.closeInventory(entityplayer);
	}

	/**+
	 * Return this chest container's lower chest inventory.
	 */
	public IInventory getLowerChestInventory() {
		return this.lowerChestInventory;
	}
}