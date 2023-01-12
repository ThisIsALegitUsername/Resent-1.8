package net.minecraft.inventory;

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
public class ContainerBeacon extends Container {
	private IInventory tileBeacon;
	private final ContainerBeacon.BeaconSlot beaconSlot;

	public ContainerBeacon(IInventory playerInventory, IInventory tileBeaconIn) {
		this.tileBeacon = tileBeaconIn;
		this.addSlotToContainer(this.beaconSlot = new ContainerBeacon.BeaconSlot(tileBeaconIn, 0, 136, 110));
		byte b0 = 36;
		short short1 = 137;

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, b0 + j * 18, short1 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, b0 + k * 18, 58 + short1));
		}

	}

	public void onCraftGuiOpened(ICrafting icrafting) {
		super.onCraftGuiOpened(icrafting);
		icrafting.func_175173_a(this, this.tileBeacon);
	}

	public void updateProgressBar(int i, int j) {
		this.tileBeacon.setField(i, j);
	}

	public IInventory func_180611_e() {
		return this.tileBeacon;
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tileBeacon.isUseableByPlayer(entityplayer);
	}

	/**+
	 * Take a stack from the specified inventory slot.
	 */
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i == 0) {
				if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(itemstack1)
					&& itemstack1.stackSize == 1) {
				if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
					return null;
				}
			} else if (i >= 1 && i < 28) {
				if (!this.mergeItemStack(itemstack1, 28, 37, false)) {
					return null;
				}
			} else if (i >= 28 && i < 37) {
				if (!this.mergeItemStack(itemstack1, 1, 28, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 1, 37, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(entityplayer, itemstack1);
		}

		return itemstack;
	}

	class BeaconSlot extends Slot {
		public BeaconSlot(IInventory parIInventory, int parInt1, int parInt2, int parInt3) {
			super(parIInventory, parInt1, parInt2, parInt3);
		}

		public boolean isItemValid(ItemStack itemstack) {
			return itemstack == null ? false
					: itemstack.getItem() == Items.emerald || itemstack.getItem() == Items.diamond
							|| itemstack.getItem() == Items.gold_ingot || itemstack.getItem() == Items.iron_ingot;
		}

		public int getSlotStackLimit() {
			return 1;
		}
	}
}