package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
public class ContainerHorseInventory extends Container {
	private IInventory horseInventory;
	private EntityHorse theHorse;

	public ContainerHorseInventory(IInventory playerInventory, final IInventory horseInventoryIn,
			final EntityHorse horse, EntityPlayer player) {
		this.horseInventory = horseInventoryIn;
		this.theHorse = horse;
		byte b0 = 3;
		horseInventoryIn.openInventory(player);
		int i = (b0 - 4) * 18;
		this.addSlotToContainer(new Slot(horseInventoryIn, 0, 8, 18) {
			public boolean isItemValid(ItemStack itemstack) {
				return super.isItemValid(itemstack) && itemstack.getItem() == Items.saddle && !this.getHasStack();
			}
		});
		this.addSlotToContainer(new Slot(horseInventoryIn, 1, 8, 36) {
			public boolean isItemValid(ItemStack itemstack) {
				return super.isItemValid(itemstack) && horse.canWearArmor()
						&& EntityHorse.isArmorItem(itemstack.getItem());
			}

			public boolean canBeHovered() {
				return horse.canWearArmor();
			}
		});
		if (horse.isChested()) {
			for (int j = 0; j < b0; ++j) {
				for (int k = 0; k < 5; ++k) {
					this.addSlotToContainer(new Slot(horseInventoryIn, 2 + k + j * 5, 80 + k * 18, 18 + j * 18));
				}
			}
		}

		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 102 + l * 18 + i));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 160 + i));
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.horseInventory.isUseableByPlayer(entityplayer) && this.theHorse.isEntityAlive()
				&& this.theHorse.getDistanceToEntity(entityplayer) < 8.0F;
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
			if (i < this.horseInventory.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.horseInventory.getSizeInventory(), this.inventorySlots.size(),
						true)) {
					return null;
				}
			} else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
					return null;
				}
			} else if (this.getSlot(0).isItemValid(itemstack1)) {
				if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
					return null;
				}
			} else if (this.horseInventory.getSizeInventory() <= 2
					|| !this.mergeItemStack(itemstack1, 2, this.horseInventory.getSizeInventory(), false)) {
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
		this.horseInventory.closeInventory(entityplayer);
	}
}