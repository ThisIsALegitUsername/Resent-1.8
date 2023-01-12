package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
public class RecipesWeapons {
	private String[][] recipePatterns = new String[][] { { "X", "X", "#" } };
	private Object[][] recipeItems;

	/**+
	 * Adds the weapon recipes to the CraftingManager.
	 */
	public void addRecipes(CraftingManager parCraftingManager) {
		recipeItems = new Object[][] {
				{ Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot },
				{ Items.wooden_sword, Items.stone_sword, Items.iron_sword, Items.diamond_sword, Items.golden_sword } };
		for (int i = 0; i < this.recipeItems[0].length; ++i) {
			Object object = this.recipeItems[0][i];

			for (int j = 0; j < this.recipeItems.length - 1; ++j) {
				Item item = (Item) this.recipeItems[j + 1][i];
				parCraftingManager.addRecipe(new ItemStack(item), new Object[] { this.recipePatterns[j],
						Character.valueOf('#'), Items.stick, Character.valueOf('X'), object });
			}
		}

		parCraftingManager.addRecipe(new ItemStack(Items.bow, 1), new Object[] { " #X", "# X", " #X",
				Character.valueOf('X'), Items.string, Character.valueOf('#'), Items.stick });
		parCraftingManager.addRecipe(new ItemStack(Items.arrow, 4),
				new Object[] { "X", "#", "Y", Character.valueOf('Y'), Items.feather, Character.valueOf('X'),
						Items.flint, Character.valueOf('#'), Items.stick });
	}
}