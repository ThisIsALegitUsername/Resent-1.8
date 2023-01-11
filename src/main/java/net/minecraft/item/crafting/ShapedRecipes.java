package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
public class ShapedRecipes implements IRecipe {
	private final int recipeWidth;
	private final int recipeHeight;
	private final ItemStack[] recipeItems;
	private final ItemStack recipeOutput;
	private boolean copyIngredientNBT;

	public ShapedRecipes(int width, int height, ItemStack[] parArrayOfItemStack, ItemStack output) {
		this.recipeWidth = width;
		this.recipeHeight = height;
		this.recipeItems = parArrayOfItemStack;
		this.recipeOutput = output;
	}

	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
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

	/**+
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inventorycrafting, World var2) {
		for (int i = 0; i <= 3 - this.recipeWidth; ++i) {
			for (int j = 0; j <= 3 - this.recipeHeight; ++j) {
				if (this.checkMatch(inventorycrafting, i, j, true)) {
					return true;
				}

				if (this.checkMatch(inventorycrafting, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**+
	 * Checks if the region of a crafting inventory is match for the
	 * recipe.
	 */
	private boolean checkMatch(InventoryCrafting parInventoryCrafting, int parInt1, int parInt2, boolean parFlag) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				int k = i - parInt1;
				int l = j - parInt2;
				ItemStack itemstack = null;
				if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
					if (parFlag) {
						itemstack = this.recipeItems[this.recipeWidth - k - 1 + l * this.recipeWidth];
					} else {
						itemstack = this.recipeItems[k + l * this.recipeWidth];
					}
				}

				ItemStack itemstack1 = parInventoryCrafting.getStackInRowAndColumn(i, j);
				if (itemstack1 != null || itemstack != null) {
					if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
						return false;
					}

					if (itemstack.getItem() != itemstack1.getItem()) {
						return false;
					}

					if (itemstack.getMetadata() != 32767 && itemstack.getMetadata() != itemstack1.getMetadata()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**+
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ItemStack itemstack = this.getRecipeOutput().copy();
		if (this.copyIngredientNBT) {
			for (int i = 0; i < inventorycrafting.getSizeInventory(); ++i) {
				ItemStack itemstack1 = inventorycrafting.getStackInSlot(i);
				if (itemstack1 != null && itemstack1.hasTagCompound()) {
					itemstack.setTagCompound((NBTTagCompound) itemstack1.getTagCompound().copy());
				}
			}
		}

		return itemstack;
	}

	/**+
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize() {
		return this.recipeWidth * this.recipeHeight;
	}
}