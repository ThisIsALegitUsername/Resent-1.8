package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
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
public class SlotCrafting extends Slot {
	private final InventoryCrafting craftMatrix;
	private final EntityPlayer thePlayer;
	private int amountCrafted;

	public SlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory parIInventory,
			int slotIndex, int xPosition, int yPosition) {
		super(parIInventory, slotIndex, xPosition, yPosition);
		this.thePlayer = player;
		this.craftMatrix = craftingInventory;
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
			this.amountCrafted += Math.min(i, this.getStack().stackSize);
		}

		return super.decrStackSize(i);
	}

	/**+
	 * the itemStack passed in is the output - ie, iron ingots, and
	 * pickaxes, not ore and wood. Typically increases an internal
	 * count then calls onCrafting(item).
	 */
	protected void onCrafting(ItemStack itemstack, int i) {
		this.amountCrafted += i;
		this.onCrafting(itemstack);
	}

	/**+
	 * the itemStack passed in is the output - ie, iron ingots, and
	 * pickaxes, not ore and wood. Typically increases an internal
	 * count then calls onCrafting(item).
	 */
	protected void onCrafting(ItemStack itemstack) {
		if (this.amountCrafted > 0) {
			itemstack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
		}

		this.amountCrafted = 0;
		if (itemstack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
			this.thePlayer.triggerAchievement(AchievementList.buildWorkBench);
		}

		if (itemstack.getItem() instanceof ItemPickaxe) {
			this.thePlayer.triggerAchievement(AchievementList.buildPickaxe);
		}

		if (itemstack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
			this.thePlayer.triggerAchievement(AchievementList.buildFurnace);
		}

		if (itemstack.getItem() instanceof ItemHoe) {
			this.thePlayer.triggerAchievement(AchievementList.buildHoe);
		}

		if (itemstack.getItem() == Items.bread) {
			this.thePlayer.triggerAchievement(AchievementList.makeBread);
		}

		if (itemstack.getItem() == Items.cake) {
			this.thePlayer.triggerAchievement(AchievementList.bakeCake);
		}

		if (itemstack.getItem() instanceof ItemPickaxe
				&& ((ItemPickaxe) itemstack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD) {
			this.thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
		}

		if (itemstack.getItem() instanceof ItemSword) {
			this.thePlayer.triggerAchievement(AchievementList.buildSword);
		}

		if (itemstack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
			this.thePlayer.triggerAchievement(AchievementList.enchantments);
		}

		if (itemstack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
			this.thePlayer.triggerAchievement(AchievementList.bookcase);
		}

		if (itemstack.getItem() == Items.golden_apple && itemstack.getMetadata() == 1) {
			this.thePlayer.triggerAchievement(AchievementList.overpowered);
		}

	}

	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		this.onCrafting(itemstack);
		ItemStack[] aitemstack = CraftingManager.getInstance().func_180303_b(this.craftMatrix, entityplayer.worldObj);

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);
			ItemStack itemstack2 = aitemstack[i];
			if (itemstack1 != null) {
				this.craftMatrix.decrStackSize(i, 1);
			}

			if (itemstack2 != null) {
				if (this.craftMatrix.getStackInSlot(i) == null) {
					this.craftMatrix.setInventorySlotContents(i, itemstack2);
				} else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack2)) {
					this.thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
				}
			}
		}

	}
}