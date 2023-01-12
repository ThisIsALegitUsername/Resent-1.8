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
public class RecipesTools {
	private String[][] recipePatterns = new String[][] { { "XXX", " # ", " # " }, { "X", "#", "#" },
			{ "XX", "X#", " #" }, { "XX", " #", " #" } };
	private Object[][] recipeItems;

	/**+
	 * Adds the tool recipes to the CraftingManager.
	 */
	public void addRecipes(CraftingManager parCraftingManager) {
		recipeItems = new Object[][] {
				{ Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot },
				{ Items.wooden_pickaxe, Items.stone_pickaxe, Items.iron_pickaxe, Items.diamond_pickaxe,
						Items.golden_pickaxe },
				{ Items.wooden_shovel, Items.stone_shovel, Items.iron_shovel, Items.diamond_shovel,
						Items.golden_shovel },
				{ Items.wooden_axe, Items.stone_axe, Items.iron_axe, Items.diamond_axe, Items.golden_axe },
				{ Items.wooden_hoe, Items.stone_hoe, Items.iron_hoe, Items.diamond_hoe, Items.golden_hoe } };
		for (int i = 0; i < this.recipeItems[0].length; ++i) {
			Object object = this.recipeItems[0][i];

			for (int j = 0; j < this.recipeItems.length - 1; ++j) {
				Item item = (Item) this.recipeItems[j + 1][i];
				parCraftingManager.addRecipe(new ItemStack(item), new Object[] { this.recipePatterns[j],
						Character.valueOf('#'), Items.stick, Character.valueOf('X'), object });
			}
		}

		parCraftingManager.addRecipe(new ItemStack(Items.shears),
				new Object[] { " #", "# ", Character.valueOf('#'), Items.iron_ingot });
	}
}