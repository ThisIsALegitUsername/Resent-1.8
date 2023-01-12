package net.minecraft.item.crafting;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
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
public class RecipeRepairItem implements IRecipe {
	/**+
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inventorycrafting, World var2) {
		ArrayList arraylist = Lists.newArrayList();

		for (int i = 0; i < inventorycrafting.getSizeInventory(); ++i) {
			ItemStack itemstack = inventorycrafting.getStackInSlot(i);
			if (itemstack != null) {
				arraylist.add(itemstack);
				if (arraylist.size() > 1) {
					ItemStack itemstack1 = (ItemStack) arraylist.get(0);
					if (itemstack.getItem() != itemstack1.getItem() || itemstack1.stackSize != 1
							|| itemstack.stackSize != 1 || !itemstack1.getItem().isDamageable()) {
						return false;
					}
				}
			}
		}

		return arraylist.size() == 2;
	}

	/**+
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ArrayList arraylist = Lists.newArrayList();

		for (int i = 0; i < inventorycrafting.getSizeInventory(); ++i) {
			ItemStack itemstack = inventorycrafting.getStackInSlot(i);
			if (itemstack != null) {
				arraylist.add(itemstack);
				if (arraylist.size() > 1) {
					ItemStack itemstack1 = (ItemStack) arraylist.get(0);
					if (itemstack.getItem() != itemstack1.getItem() || itemstack1.stackSize != 1
							|| itemstack.stackSize != 1 || !itemstack1.getItem().isDamageable()) {
						return null;
					}
				}
			}
		}

		if (arraylist.size() == 2) {
			ItemStack itemstack2 = (ItemStack) arraylist.get(0);
			ItemStack itemstack3 = (ItemStack) arraylist.get(1);
			if (itemstack2.getItem() == itemstack3.getItem() && itemstack2.stackSize == 1 && itemstack3.stackSize == 1
					&& itemstack2.getItem().isDamageable()) {
				Item item = itemstack2.getItem();
				int j = item.getMaxDamage() - itemstack2.getItemDamage();
				int k = item.getMaxDamage() - itemstack3.getItemDamage();
				int l = j + k + item.getMaxDamage() * 5 / 100;
				int i1 = item.getMaxDamage() - l;
				if (i1 < 0) {
					i1 = 0;
				}

				return new ItemStack(itemstack2.getItem(), 1, i1);
			}
		}

		return null;
	}

	/**+
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize() {
		return 4;
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