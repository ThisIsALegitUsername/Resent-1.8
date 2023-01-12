package net.minecraft.item.crafting;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

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
public class ShapelessRecipes implements IRecipe {
	private final ItemStack recipeOutput;
	private final List<ItemStack> recipeItems;

	public ShapelessRecipes(ItemStack output, List<ItemStack> inputList) {
		this.recipeOutput = output;
		this.recipeItems = inputList;
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
		ArrayList arraylist = Lists.newArrayList(this.recipeItems);

		for (int i = 0; i < inventorycrafting.getHeight(); ++i) {
			for (int j = 0; j < inventorycrafting.getWidth(); ++j) {
				ItemStack itemstack = inventorycrafting.getStackInRowAndColumn(j, i);
				if (itemstack != null) {
					boolean flag = false;

					for (ItemStack itemstack1 : (List<ItemStack>) arraylist) {
						if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getMetadata() == 32767
								|| itemstack.getMetadata() == itemstack1.getMetadata())) {
							flag = true;
							arraylist.remove(itemstack1);
							break;
						}
					}

					if (!flag) {
						return false;
					}
				}
			}
		}

		return arraylist.isEmpty();
	}

	/**+
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		return this.recipeOutput.copy();
	}

	/**+
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize() {
		return this.recipeItems.size();
	}
}