package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

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
public class InventoryCrafting implements IInventory {
	private final ItemStack[] stackList;
	private final int inventoryWidth;
	private final int inventoryHeight;
	private final Container eventHandler;

	public InventoryCrafting(Container eventHandlerIn, int width, int height) {
		int i = width * height;
		this.stackList = new ItemStack[i];
		this.eventHandler = eventHandlerIn;
		this.inventoryWidth = width;
		this.inventoryHeight = height;
	}

	/**+
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return this.stackList.length;
	}

	/**+
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int i) {
		return i >= this.getSizeInventory() ? null : this.stackList[i];
	}

	/**+
	 * Returns the itemstack in the slot specified (Top left is 0,
	 * 0). Args: row, column
	 */
	public ItemStack getStackInRowAndColumn(int row, int column) {
		return row >= 0 && row < this.inventoryWidth && column >= 0 && column <= this.inventoryHeight
				? this.getStackInSlot(row + column * this.inventoryWidth)
				: null;
	}

	/**+
	 * Gets the name of this command sender (usually username, but
	 * possibly "Rcon")
	 */
	public String getName() {
		return "container.crafting";
	}

	/**+
	 * Returns true if this thing is named
	 */
	public boolean hasCustomName() {
		return false;
	}

	/**+
	 * Get the formatted ChatComponent that will be used for the
	 * sender's username in chat
	 */
	public IChatComponent getDisplayName() {
		return (IChatComponent) (this.hasCustomName() ? new ChatComponentText(this.getName())
				: new ChatComponentTranslation(this.getName(), new Object[0]));
	}

	/**+
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int i) {
		if (this.stackList[i] != null) {
			ItemStack itemstack = this.stackList[i];
			this.stackList[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	/**+
	 * Removes up to a specified number of items from an inventory
	 * slot and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int i, int j) {
		if (this.stackList[i] != null) {
			if (this.stackList[i].stackSize <= j) {
				ItemStack itemstack1 = this.stackList[i];
				this.stackList[i] = null;
				this.eventHandler.onCraftMatrixChanged(this);
				return itemstack1;
			} else {
				ItemStack itemstack = this.stackList[i].splitStack(j);
				if (this.stackList[i].stackSize == 0) {
					this.stackList[i] = null;
				}

				this.eventHandler.onCraftMatrixChanged(this);
				return itemstack;
			}
		} else {
			return null;
		}
	}

	/**+
	 * Sets the given item stack to the specified slot in the
	 * inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.stackList[i] = itemstack;
		this.eventHandler.onCraftMatrixChanged(this);
	}

	/**+
	 * Returns the maximum stack size for a inventory slot. Seems to
	 * always be 64, possibly will be extended.
	 */
	public int getInventoryStackLimit() {
		return 64;
	}

	/**+
	 * For tile entities, ensures the chunk containing the tile
	 * entity is saved to disk later - the game won't think it
	 * hasn't changed and skip it.
	 */
	public void markDirty() {
	}

	/**+
	 * Do not make give this method the name canInteractWith because
	 * it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	public void openInventory(EntityPlayer var1) {
	}

	public void closeInventory(EntityPlayer var1) {
	}

	/**+
	 * Returns true if automation is allowed to insert the given
	 * stack (ignoring stack size) into the given slot.
	 */
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

	public int getField(int var1) {
		return 0;
	}

	public void setField(int var1, int var2) {
	}

	public int getFieldCount() {
		return 0;
	}

	public void clear() {
		for (int i = 0; i < this.stackList.length; ++i) {
			this.stackList[i] = null;
		}

	}

	public int getHeight() {
		return this.inventoryHeight;
	}

	public int getWidth() {
		return this.inventoryWidth;
	}
}