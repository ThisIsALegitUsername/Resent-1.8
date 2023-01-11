package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
public class SlotFurnaceFuel extends Slot {
	public SlotFurnaceFuel(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
		super(inventoryIn, slotIndex, xPosition, yPosition);
	}

	/**+
	 * Check if the stack is a valid item for this slot. Always true
	 * beside for the armor slots.
	 */
	public boolean isItemValid(ItemStack itemstack) {
		return TileEntityFurnace.isItemFuel(itemstack) || isBucket(itemstack);
	}

	public int getItemStackLimit(ItemStack itemstack) {
		return isBucket(itemstack) ? 1 : super.getItemStackLimit(itemstack);
	}

	public static boolean isBucket(ItemStack parItemStack) {
		return parItemStack != null && parItemStack.getItem() != null && parItemStack.getItem() == Items.bucket;
	}
}