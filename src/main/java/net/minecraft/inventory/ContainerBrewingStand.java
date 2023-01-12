package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

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
public class ContainerBrewingStand extends Container {
	private IInventory tileBrewingStand;
	private final Slot theSlot;
	private int brewTime;

	public ContainerBrewingStand(InventoryPlayer playerInventory, IInventory tileBrewingStandIn) {
		this.tileBrewingStand = tileBrewingStandIn;
		this.addSlotToContainer(
				new ContainerBrewingStand.Potion(playerInventory.player, tileBrewingStandIn, 0, 56, 46));
		this.addSlotToContainer(
				new ContainerBrewingStand.Potion(playerInventory.player, tileBrewingStandIn, 1, 79, 53));
		this.addSlotToContainer(
				new ContainerBrewingStand.Potion(playerInventory.player, tileBrewingStandIn, 2, 102, 46));
		this.theSlot = this.addSlotToContainer(new ContainerBrewingStand.Ingredient(tileBrewingStandIn, 3, 79, 17));

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
		icrafting.func_175173_a(this, this.tileBrewingStand);
	}

	/**+
	 * Looks for changes made in the container, sends them to every
	 * listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			if (this.brewTime != this.tileBrewingStand.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getField(0));
			}
		}

		this.brewTime = this.tileBrewingStand.getField(0);
	}

	public void updateProgressBar(int i, int j) {
		this.tileBrewingStand.setField(i, j);
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tileBrewingStand.isUseableByPlayer(entityplayer);
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
			if ((i < 0 || i > 2) && i != 3) {
				if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 3, 4, false)) {
						return null;
					}
				} else if (ContainerBrewingStand.Potion.canHoldPotion(itemstack)) {
					if (!this.mergeItemStack(itemstack1, 0, 3, false)) {
						return null;
					}
				} else if (i >= 4 && i < 31) {
					if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
						return null;
					}
				} else if (i >= 31 && i < 40) {
					if (!this.mergeItemStack(itemstack1, 4, 31, false)) {
						return null;
					}
				} else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
					return null;
				}
			} else {
				if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
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

	class Ingredient extends Slot {
		public Ingredient(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		public boolean isItemValid(ItemStack itemstack) {
			return itemstack != null ? itemstack.getItem().isPotionIngredient(itemstack) : false;
		}

		public int getSlotStackLimit() {
			return 64;
		}
	}

	static class Potion extends Slot {
		private EntityPlayer player;

		public Potion(EntityPlayer playerIn, IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
			this.player = playerIn;
		}

		public boolean isItemValid(ItemStack itemstack) {
			return canHoldPotion(itemstack);
		}

		public int getSlotStackLimit() {
			return 1;
		}

		public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
			if (itemstack.getItem() == Items.potionitem && itemstack.getMetadata() > 0) {
				this.player.triggerAchievement(AchievementList.potion);
			}

			super.onPickupFromSlot(entityplayer, itemstack);
		}

		public static boolean canHoldPotion(ItemStack parItemStack) {
			return parItemStack != null
					&& (parItemStack.getItem() == Items.potionitem || parItemStack.getItem() == Items.glass_bottle);
		}
	}
}