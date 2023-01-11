package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

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
public class ContainerFurnace extends Container {
	private final IInventory tileFurnace;
	private int field_178152_f;
	private int field_178153_g;
	private int field_178154_h;
	private int field_178155_i;

	public ContainerFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory) {
		this.tileFurnace = furnaceInventory;
		this.addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17));
		this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 116, 35));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

	}

	public void onCraftGuiOpened(ICrafting icrafting) {
		super.onCraftGuiOpened(icrafting);
		icrafting.func_175173_a(this, this.tileFurnace);
	}

	/**+
	 * Looks for changes made in the container, sends them to every
	 * listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			if (this.field_178152_f != this.tileFurnace.getField(2)) {
				icrafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
			}

			if (this.field_178154_h != this.tileFurnace.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
			}

			if (this.field_178155_i != this.tileFurnace.getField(1)) {
				icrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
			}

			if (this.field_178153_g != this.tileFurnace.getField(3)) {
				icrafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
			}
		}

		this.field_178152_f = this.tileFurnace.getField(2);
		this.field_178154_h = this.tileFurnace.getField(0);
		this.field_178155_i = this.tileFurnace.getField(1);
		this.field_178153_g = this.tileFurnace.getField(3);
	}

	public void updateProgressBar(int i, int j) {
		this.tileFurnace.setField(i, j);
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tileFurnace.isUseableByPlayer(entityplayer);
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
			if (i == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (i != 1 && i != 0) {
				if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (i >= 3 && i < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (i >= 30 && i < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
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
}