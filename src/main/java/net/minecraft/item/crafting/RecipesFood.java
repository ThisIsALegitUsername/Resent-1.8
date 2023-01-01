package net.minecraft.item.crafting;

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
 * EaglercraftX 1.8 patch files are (c) 2022 LAX1DUDE. All Rights Reserved.
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
public class RecipesFood {
	/**+
	 * Adds the food recipes to the CraftingManager.
	 */
	public void addRecipes(CraftingManager parCraftingManager) {
        parCraftingManager.addShapelessRecipe(new ItemStack(Items.mushroom_stew),
                Blocks.brown_mushroom, Blocks.red_mushroom, Items.bowl);
        parCraftingManager.addRecipe(new ItemStack(Items.cookie, 8), "#X#", Character.valueOf('X'),
                new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeDamage()), Character.valueOf('#'), Items.wheat);
        parCraftingManager.addRecipe(new ItemStack(Items.rabbit_stew),
                " R ", "CPM", " B ", Character.valueOf('R'), new ItemStack(Items.cooked_rabbit),
                Character.valueOf('C'), Items.carrot, Character.valueOf('P'), Items.baked_potato,
                Character.valueOf('M'), Blocks.brown_mushroom, Character.valueOf('B'), Items.bowl);
        parCraftingManager.addRecipe(new ItemStack(Items.rabbit_stew),
                " R ", "CPD", " B ", Character.valueOf('R'), new ItemStack(Items.cooked_rabbit),
                Character.valueOf('C'), Items.carrot, Character.valueOf('P'), Items.baked_potato,
                Character.valueOf('D'), Blocks.red_mushroom, Character.valueOf('B'), Items.bowl);
        parCraftingManager.addRecipe(new ItemStack(Blocks.melon_block),
                "MMM", "MMM", "MMM", Character.valueOf('M'), Items.melon);
        parCraftingManager.addRecipe(new ItemStack(Items.melon_seeds),
                "M", Character.valueOf('M'), Items.melon);
        parCraftingManager.addRecipe(new ItemStack(Items.pumpkin_seeds, 4),
                "M", Character.valueOf('M'), Blocks.pumpkin);
        parCraftingManager.addShapelessRecipe(new ItemStack(Items.pumpkin_pie),
                Blocks.pumpkin, Items.sugar, Items.egg);
        parCraftingManager.addShapelessRecipe(new ItemStack(Items.fermented_spider_eye),
                Items.spider_eye, Blocks.brown_mushroom, Items.sugar);
        parCraftingManager.addShapelessRecipe(new ItemStack(Items.blaze_powder, 2), Items.blaze_rod);
        parCraftingManager.addShapelessRecipe(new ItemStack(Items.magma_cream),
                Items.blaze_powder, Items.slime_ball);
    }
}