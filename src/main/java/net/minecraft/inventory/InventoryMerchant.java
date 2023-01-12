package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

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
public class InventoryMerchant implements IInventory {
	private final IMerchant theMerchant;
	private ItemStack[] theInventory = new ItemStack[3];
	private final EntityPlayer thePlayer;
	private MerchantRecipe currentRecipe;
	private int currentRecipeIndex;

	public InventoryMerchant(EntityPlayer thePlayerIn, IMerchant theMerchantIn) {
		this.thePlayer = thePlayerIn;
		this.theMerchant = theMerchantIn;
	}

	/**+
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return this.theInventory.length;
	}

	/**+
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int i) {
		return this.theInventory[i];
	}

	/**+
	 * Removes up to a specified number of items from an inventory
	 * slot and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int i, int j) {
		if (this.theInventory[i] != null) {
			if (i == 2) {
				ItemStack itemstack2 = this.theInventory[i];
				this.theInventory[i] = null;
				return itemstack2;
			} else if (this.theInventory[i].stackSize <= j) {
				ItemStack itemstack1 = this.theInventory[i];
				this.theInventory[i] = null;
				if (this.inventoryResetNeededOnSlotChange(i)) {
					this.resetRecipeAndSlots();
				}

				return itemstack1;
			} else {
				ItemStack itemstack = this.theInventory[i].splitStack(j);
				if (this.theInventory[i].stackSize == 0) {
					this.theInventory[i] = null;
				}

				if (this.inventoryResetNeededOnSlotChange(i)) {
					this.resetRecipeAndSlots();
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	/**+
	 * if par1 slot has changed, does resetRecipeAndSlots need to be
	 * called?
	 */
	private boolean inventoryResetNeededOnSlotChange(int parInt1) {
		return parInt1 == 0 || parInt1 == 1;
	}

	/**+
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int i) {
		if (this.theInventory[i] != null) {
			ItemStack itemstack = this.theInventory[i];
			this.theInventory[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	/**+
	 * Sets the given item stack to the specified slot in the
	 * inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.theInventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		if (this.inventoryResetNeededOnSlotChange(i)) {
			this.resetRecipeAndSlots();
		}

	}

	/**+
	 * Gets the name of this command sender (usually username, but
	 * possibly "Rcon")
	 */
	public String getName() {
		return "mob.villager";
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
	 * Returns the maximum stack size for a inventory slot. Seems to
	 * always be 64, possibly will be extended.
	 */
	public int getInventoryStackLimit() {
		return 64;
	}

	/**+
	 * Do not make give this method the name canInteractWith because
	 * it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.theMerchant.getCustomer() == entityplayer;
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

	/**+
	 * For tile entities, ensures the chunk containing the tile
	 * entity is saved to disk later - the game won't think it
	 * hasn't changed and skip it.
	 */
	public void markDirty() {
		this.resetRecipeAndSlots();
	}

	public void resetRecipeAndSlots() {
		this.currentRecipe = null;
		ItemStack itemstack = this.theInventory[0];
		ItemStack itemstack1 = this.theInventory[1];
		if (itemstack == null) {
			itemstack = itemstack1;
			itemstack1 = null;
		}

		if (itemstack == null) {
			this.setInventorySlotContents(2, (ItemStack) null);
		} else {
			MerchantRecipeList merchantrecipelist = this.theMerchant.getRecipes(this.thePlayer);
			if (merchantrecipelist != null) {
				MerchantRecipe merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack, itemstack1,
						this.currentRecipeIndex);
				if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled()) {
					this.currentRecipe = merchantrecipe;
					this.setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
				} else if (itemstack1 != null) {
					merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack1, itemstack, this.currentRecipeIndex);
					if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled()) {
						this.currentRecipe = merchantrecipe;
						this.setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
					} else {
						this.setInventorySlotContents(2, (ItemStack) null);
					}
				} else {
					this.setInventorySlotContents(2, (ItemStack) null);
				}
			}
		}

		this.theMerchant.verifySellingItem(this.getStackInSlot(2));
	}

	public MerchantRecipe getCurrentRecipe() {
		return this.currentRecipe;
	}

	public void setCurrentRecipeIndex(int currentRecipeIndexIn) {
		this.currentRecipeIndex = currentRecipeIndexIn;
		this.resetRecipeAndSlots();
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
		for (int i = 0; i < this.theInventory.length; ++i) {
			this.theInventory[i] = null;
		}

	}
}