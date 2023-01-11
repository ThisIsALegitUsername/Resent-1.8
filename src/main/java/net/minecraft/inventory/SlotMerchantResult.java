package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

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
public class SlotMerchantResult extends Slot {
	private final InventoryMerchant theMerchantInventory;
	private EntityPlayer thePlayer;
	private int field_75231_g;
	private final IMerchant theMerchant;

	public SlotMerchantResult(EntityPlayer player, IMerchant merchant, InventoryMerchant merchantInventory,
			int slotIndex, int xPosition, int yPosition) {
		super(merchantInventory, slotIndex, xPosition, yPosition);
		this.thePlayer = player;
		this.theMerchant = merchant;
		this.theMerchantInventory = merchantInventory;
	}

	/**+
	 * Check if the stack is a valid item for this slot. Always true
	 * beside for the armor slots.
	 */
	public boolean isItemValid(ItemStack var1) {
		return false;
	}

	/**+
	 * Decrease the size of the stack in slot (first int arg) by the
	 * amount of the second int arg. Returns the new stack.
	 */
	public ItemStack decrStackSize(int i) {
		if (this.getHasStack()) {
			this.field_75231_g += Math.min(i, this.getStack().stackSize);
		}

		return super.decrStackSize(i);
	}

	/**+
	 * the itemStack passed in is the output - ie, iron ingots, and
	 * pickaxes, not ore and wood. Typically increases an internal
	 * count then calls onCrafting(item).
	 */
	protected void onCrafting(ItemStack itemstack, int i) {
		this.field_75231_g += i;
		this.onCrafting(itemstack);
	}

	/**+
	 * the itemStack passed in is the output - ie, iron ingots, and
	 * pickaxes, not ore and wood. Typically increases an internal
	 * count then calls onCrafting(item).
	 */
	protected void onCrafting(ItemStack itemstack) {
		itemstack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
		this.field_75231_g = 0;
	}

	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		this.onCrafting(itemstack);
		MerchantRecipe merchantrecipe = this.theMerchantInventory.getCurrentRecipe();
		if (merchantrecipe != null) {
			ItemStack itemstack1 = this.theMerchantInventory.getStackInSlot(0);
			ItemStack itemstack2 = this.theMerchantInventory.getStackInSlot(1);
			if (this.doTrade(merchantrecipe, itemstack1, itemstack2)
					|| this.doTrade(merchantrecipe, itemstack2, itemstack1)) {
				this.theMerchant.useRecipe(merchantrecipe);
				entityplayer.triggerAchievement(StatList.timesTradedWithVillagerStat);
				if (itemstack1 != null && itemstack1.stackSize <= 0) {
					itemstack1 = null;
				}

				if (itemstack2 != null && itemstack2.stackSize <= 0) {
					itemstack2 = null;
				}

				this.theMerchantInventory.setInventorySlotContents(0, itemstack1);
				this.theMerchantInventory.setInventorySlotContents(1, itemstack2);
			}
		}

	}

	private boolean doTrade(MerchantRecipe trade, ItemStack firstItem, ItemStack secondItem) {
		ItemStack itemstack = trade.getItemToBuy();
		ItemStack itemstack1 = trade.getSecondItemToBuy();
		if (firstItem != null && firstItem.getItem() == itemstack.getItem()) {
			if (itemstack1 != null && secondItem != null && itemstack1.getItem() == secondItem.getItem()) {
				firstItem.stackSize -= itemstack.stackSize;
				secondItem.stackSize -= itemstack1.stackSize;
				return true;
			}

			if (itemstack1 == null && secondItem == null) {
				firstItem.stackSize -= itemstack.stackSize;
				return true;
			}
		}

		return false;
	}
}