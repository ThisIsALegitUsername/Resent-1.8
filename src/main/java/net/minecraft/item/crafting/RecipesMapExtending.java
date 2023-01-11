package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

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
public class RecipesMapExtending extends ShapedRecipes {
	public RecipesMapExtending() {
		super(3, 3,
				new ItemStack[] { new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper),
						new ItemStack(Items.paper), new ItemStack(Items.filled_map, 0, 32767),
						new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper),
						new ItemStack(Items.paper) },
				new ItemStack(Items.map, 0, 0));
	}

	/**+
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		if (!super.matches(inventorycrafting, world)) {
			return false;
		} else {
			ItemStack itemstack = null;

			for (int i = 0; i < inventorycrafting.getSizeInventory() && itemstack == null; ++i) {
				ItemStack itemstack1 = inventorycrafting.getStackInSlot(i);
				if (itemstack1 != null && itemstack1.getItem() == Items.filled_map) {
					itemstack = itemstack1;
				}
			}

			if (itemstack == null) {
				return false;
			} else {
				MapData mapdata = Items.filled_map.getMapData(itemstack, world);
				return mapdata == null ? false : mapdata.scale < 4;
			}
		}
	}

	/**+
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ItemStack itemstack = null;

		for (int i = 0; i < inventorycrafting.getSizeInventory() && itemstack == null; ++i) {
			ItemStack itemstack1 = inventorycrafting.getStackInSlot(i);
			if (itemstack1 != null && itemstack1.getItem() == Items.filled_map) {
				itemstack = itemstack1;
			}
		}

		itemstack = itemstack.copy();
		itemstack.stackSize = 1;
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}

		itemstack.getTagCompound().setBoolean("map_is_scaling", true);
		return itemstack;
	}
}