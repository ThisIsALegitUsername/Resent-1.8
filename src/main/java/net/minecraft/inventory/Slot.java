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
public class Slot {
	private final int slotIndex;
	public final IInventory inventory;
	public int slotNumber;
	public int xDisplayPosition;
	public int yDisplayPosition;

	public Slot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		this.inventory = inventoryIn;
		this.slotIndex = index;
		this.xDisplayPosition = xPosition;
		this.yDisplayPosition = yPosition;
	}

	/**+
	 * if par2 has more items than par1,
	 * onCrafting(item,countIncrease) is called
	 */
	public void onSlotChange(ItemStack parItemStack, ItemStack parItemStack2) {
		if (parItemStack != null && parItemStack2 != null) {
			if (parItemStack.getItem() == parItemStack2.getItem()) {
				int i = parItemStack2.stackSize - parItemStack.stackSize;
				if (i > 0) {
					this.onCrafting(parItemStack, i);
				}

			}
		}
	}

	/**+
	 * the itemStack passed in is the output - ie, iron ingots, and
	 * pickaxes, not ore and wood. Typically increases an internal
	 * count then calls onCrafting(item).
	 */
	protected void onCrafting(ItemStack var1, int var2) {
	}

	/**+
	 * the itemStack passed in is the output - ie, iron ingots, and
	 * pickaxes, not ore and wood. Typically increases an internal
	 * count then calls onCrafting(item).
	 */
	protected void onCrafting(ItemStack var1) {
	}

	public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
		this.onSlotChanged();
	}

	/**+
	 * Check if the stack is a valid item for this slot. Always true
	 * beside for the armor slots.
	 */
	public boolean isItemValid(ItemStack var1) {
		return true;
	}

	/**+
	 * Helper fnct to get the stack in the slot.
	 */
	public ItemStack getStack() {
		return this.inventory.getStackInSlot(this.slotIndex);
	}

	/**+
	 * Returns if this slot contains a stack.
	 */
	public boolean getHasStack() {
		return this.getStack() != null;
	}

	/**+
	 * Helper method to put a stack in the slot.
	 */
	public void putStack(ItemStack itemstack) {
		this.inventory.setInventorySlotContents(this.slotIndex, itemstack);
		this.onSlotChanged();
	}

	/**+
	 * Called when the stack in a Slot changes
	 */
	public void onSlotChanged() {
		this.inventory.markDirty();
	}

	/**+
	 * Returns the maximum stack size for a given slot (usually the
	 * same as getInventoryStackLimit(), but 1 in the case of armor
	 * slots)
	 */
	public int getSlotStackLimit() {
		return this.inventory.getInventoryStackLimit();
	}

	public int getItemStackLimit(ItemStack var1) {
		return this.getSlotStackLimit();
	}

	public String getSlotTexture() {
		return null;
	}

	/**+
	 * Decrease the size of the stack in slot (first int arg) by the
	 * amount of the second int arg. Returns the new stack.
	 */
	public ItemStack decrStackSize(int i) {
		return this.inventory.decrStackSize(this.slotIndex, i);
	}

	/**+
	 * returns true if the slot exists in the given inventory and
	 * location
	 */
	public boolean isHere(IInventory iinventory, int i) {
		return iinventory == this.inventory && i == this.slotIndex;
	}

	/**+
	 * Return whether this slot's stack can be taken from this slot.
	 */
	public boolean canTakeStack(EntityPlayer var1) {
		return true;
	}

	/**+
	 * Actualy only call when we want to render the white square
	 * effect over the slots. Return always True, except for the
	 * armor slot of the Donkey/Mule (we can't interact with the
	 * Undead and Skeleton horses)
	 */
	public boolean canBeHovered() {
		return true;
	}
}