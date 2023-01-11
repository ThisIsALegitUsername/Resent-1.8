package net.minecraft.item.crafting;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
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
public class RecipesDyes {
	/**+
	 * Adds the dye recipes to the CraftingManager.
	 */
	public void addRecipes(CraftingManager parCraftingManager) {
		for (int i = 0; i < 16; ++i) {
			parCraftingManager.addShapelessRecipe(new ItemStack(Blocks.wool, 1, i), new Object[] {
					new ItemStack(Items.dye, 1, 15 - i), new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0) });
			parCraftingManager.addRecipe(new ItemStack(Blocks.stained_hardened_clay, 8, 15 - i),
					new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.hardened_clay),
							Character.valueOf('X'), new ItemStack(Items.dye, 1, i) });
			parCraftingManager.addRecipe(new ItemStack(Blocks.stained_glass, 8, 15 - i),
					new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.glass),
							Character.valueOf('X'), new ItemStack(Items.dye, 1, i) });
			parCraftingManager.addRecipe(new ItemStack(Blocks.stained_glass_pane, 16, i),
					new Object[] { "###", "###", Character.valueOf('#'), new ItemStack(Blocks.stained_glass, 1, i) });
		}

		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeDamage()),
				new Object[] {
						new ItemStack(Blocks.yellow_flower, 1, BlockFlower.EnumFlowerType.DANDELION.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.POPPY.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.WHITE.getDyeDamage()),
				new Object[] { Items.bone });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.ORANGE.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIME.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.GRAY.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.SILVER.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.GRAY.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.SILVER.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.CYAN.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PURPLE.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.PURPLE.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, EnumDyeColor.MAGENTA.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 4, EnumDyeColor.MAGENTA.getDyeDamage()),
				new Object[] { new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()),
						new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.MAGENTA.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.ALLIUM.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.HOUSTONIA.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.RED_TULIP.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.ORANGE.getDyeDamage()),
				new Object[] {
						new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.PINK_TULIP.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.YELLOW.getDyeDamage()),
				new Object[] {
						new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeDamage()),
				new Object[] {
						new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.SYRINGA.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.RED.getDyeDamage()),
				new Object[] { new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.ROSE.getMeta()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeDamage()),
				new Object[] {
						new ItemStack(Blocks.double_plant, 1, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta()) });

		for (int j = 0; j < 16; ++j) {
			parCraftingManager.addRecipe(new ItemStack(Blocks.carpet, 3, j),
					new Object[] { "##", Character.valueOf('#'), new ItemStack(Blocks.wool, 1, j) });
		}

	}
}