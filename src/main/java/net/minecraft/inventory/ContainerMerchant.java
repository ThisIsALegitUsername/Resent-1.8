package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
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
public class ContainerMerchant extends Container {
	private IMerchant theMerchant;
	private InventoryMerchant merchantInventory;
	private final World theWorld;

	public ContainerMerchant(InventoryPlayer playerInventory, IMerchant merchant, World worldIn) {
		this.theMerchant = merchant;
		this.theWorld = worldIn;
		this.merchantInventory = new InventoryMerchant(playerInventory.player, merchant);
		this.addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
		this.addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
		this.addSlotToContainer(
				new SlotMerchantResult(playerInventory.player, merchant, this.merchantInventory, 2, 120, 53));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

	}

	public InventoryMerchant getMerchantInventory() {
		return this.merchantInventory;
	}

	public void onCraftGuiOpened(ICrafting icrafting) {
		super.onCraftGuiOpened(icrafting);
	}

	/**+
	 * Looks for changes made in the container, sends them to every
	 * listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}

	/**+
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged(IInventory iinventory) {
		this.merchantInventory.resetRecipeAndSlots();
		super.onCraftMatrixChanged(iinventory);
	}

	public void setCurrentRecipeIndex(int currentRecipeIndex) {
		this.merchantInventory.setCurrentRecipeIndex(currentRecipeIndex);
	}

	public void updateProgressBar(int var1, int var2) {
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.theMerchant.getCustomer() == entityplayer;
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
			} else if (i != 0 && i != 1) {
				if (i >= 3 && i < 30) {
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

	/**+
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		this.theMerchant.setCustomer((EntityPlayer) null);
		super.onContainerClosed(entityplayer);
	}
}