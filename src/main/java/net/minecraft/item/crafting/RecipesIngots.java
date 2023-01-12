package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

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
public class RecipesIngots {
	private Object[][] recipeItems;

	/**+
	 * Adds the ingot recipes to the CraftingManager.
	 */
	public void addRecipes(CraftingManager parCraftingManager) {
		recipeItems = new Object[][] { { Blocks.gold_block, new ItemStack(Items.gold_ingot, 9) },
				{ Blocks.iron_block, new ItemStack(Items.iron_ingot, 9) },
				{ Blocks.diamond_block, new ItemStack(Items.diamond, 9) },
				{ Blocks.emerald_block, new ItemStack(Items.emerald, 9) },
				{ Blocks.lapis_block, new ItemStack(Items.dye, 9, EnumDyeColor.BLUE.getDyeDamage()) },
				{ Blocks.redstone_block, new ItemStack(Items.redstone, 9) },
				{ Blocks.coal_block, new ItemStack(Items.coal, 9, 0) },
				{ Blocks.hay_block, new ItemStack(Items.wheat, 9) },
				{ Blocks.slime_block, new ItemStack(Items.slime_ball, 9) } };
		for (int i = 0; i < this.recipeItems.length; ++i) {
			Block block = (Block) this.recipeItems[i][0];
			ItemStack itemstack = (ItemStack) this.recipeItems[i][1];
			parCraftingManager.addRecipe(new ItemStack(block),
					new Object[] { "###", "###", "###", Character.valueOf('#'), itemstack });
			parCraftingManager.addRecipe(itemstack, new Object[] { "#", Character.valueOf('#'), block });
		}

		parCraftingManager.addRecipe(new ItemStack(Items.gold_ingot),
				new Object[] { "###", "###", "###", Character.valueOf('#'), Items.gold_nugget });
		parCraftingManager.addRecipe(new ItemStack(Items.gold_nugget, 9),
				new Object[] { "#", Character.valueOf('#'), Items.gold_ingot });
	}
}