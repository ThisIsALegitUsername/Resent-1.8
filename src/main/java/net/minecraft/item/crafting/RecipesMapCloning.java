package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
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
public class RecipesMapCloning implements IRecipe {
	/**+
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inventorycrafting, World var2) {
		int i = 0;
		ItemStack itemstack = null;

		for (int j = 0; j < inventorycrafting.getSizeInventory(); ++j) {
			ItemStack itemstack1 = inventorycrafting.getStackInSlot(j);
			if (itemstack1 != null) {
				if (itemstack1.getItem() == Items.filled_map) {
					if (itemstack != null) {
						return false;
					}

					itemstack = itemstack1;
				} else {
					if (itemstack1.getItem() != Items.map) {
						return false;
					}

					++i;
				}
			}
		}

		return itemstack != null && i > 0;
	}

	/**+
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		int i = 0;
		ItemStack itemstack = null;

		for (int j = 0; j < inventorycrafting.getSizeInventory(); ++j) {
			ItemStack itemstack1 = inventorycrafting.getStackInSlot(j);
			if (itemstack1 != null) {
				if (itemstack1.getItem() == Items.filled_map) {
					if (itemstack != null) {
						return null;
					}

					itemstack = itemstack1;
				} else {
					if (itemstack1.getItem() != Items.map) {
						return null;
					}

					++i;
				}
			}
		}

		if (itemstack != null && i >= 1) {
			ItemStack itemstack2 = new ItemStack(Items.filled_map, i + 1, itemstack.getMetadata());
			if (itemstack.hasDisplayName()) {
				itemstack2.setStackDisplayName(itemstack.getDisplayName());
			}

			return itemstack2;
		} else {
			return null;
		}
	}

	/**+
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize() {
		return 9;
	}

	public ItemStack getRecipeOutput() {
		return null;
	}

	public ItemStack[] getRemainingItems(InventoryCrafting inventorycrafting) {
		ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSizeInventory()];

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack = inventorycrafting.getStackInSlot(i);
			if (itemstack != null && itemstack.getItem().hasContainerItem()) {
				aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
			}
		}

		return aitemstack;
	}
}