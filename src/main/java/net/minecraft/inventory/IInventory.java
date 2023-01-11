package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorldNameable;

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
public interface IInventory extends IWorldNameable {
	/**+
	 * Returns the number of slots in the inventory.
	 */
	int getSizeInventory();

	/**+
	 * Returns the stack in the given slot.
	 */
	ItemStack getStackInSlot(int var1);

	/**+
	 * Removes up to a specified number of items from an inventory
	 * slot and returns them in a new stack.
	 */
	ItemStack decrStackSize(int var1, int var2);

	/**+
	 * Removes a stack from the given slot and returns it.
	 */
	ItemStack removeStackFromSlot(int var1);

	/**+
	 * Sets the given item stack to the specified slot in the
	 * inventory (can be crafting or armor sections).
	 */
	void setInventorySlotContents(int var1, ItemStack var2);

	/**+
	 * Returns the maximum stack size for a inventory slot. Seems to
	 * always be 64, possibly will be extended.
	 */
	int getInventoryStackLimit();

	/**+
	 * For tile entities, ensures the chunk containing the tile
	 * entity is saved to disk later - the game won't think it
	 * hasn't changed and skip it.
	 */
	void markDirty();

	/**+
	 * Do not make give this method the name canInteractWith because
	 * it clashes with Container
	 */
	boolean isUseableByPlayer(EntityPlayer var1);

	void openInventory(EntityPlayer var1);

	void closeInventory(EntityPlayer var1);

	/**+
	 * Returns true if automation is allowed to insert the given
	 * stack (ignoring stack size) into the given slot.
	 */
	boolean isItemValidForSlot(int var1, ItemStack var2);

	int getField(int var1);

	void setField(int var1, int var2);

	int getFieldCount();

	void clear();
}