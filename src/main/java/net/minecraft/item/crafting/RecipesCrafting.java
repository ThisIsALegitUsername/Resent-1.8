package net.minecraft.item.crafting;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
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
public class RecipesCrafting {
	/**+
	 * Adds the crafting recipes to the CraftingManager.
	 */
	public void addRecipes(CraftingManager parCraftingManager) {
		parCraftingManager.addRecipe(new ItemStack(Blocks.chest),
				new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.planks });
		parCraftingManager.addRecipe(new ItemStack(Blocks.trapped_chest), new Object[] { "#-", Character.valueOf('#'),
				Blocks.chest, Character.valueOf('-'), Blocks.tripwire_hook });
		parCraftingManager.addRecipe(new ItemStack(Blocks.ender_chest), new Object[] { "###", "#E#", "###",
				Character.valueOf('#'), Blocks.obsidian, Character.valueOf('E'), Items.ender_eye });
		parCraftingManager.addRecipe(new ItemStack(Blocks.furnace),
				new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.cobblestone });
		parCraftingManager.addRecipe(new ItemStack(Blocks.crafting_table),
				new Object[] { "##", "##", Character.valueOf('#'), Blocks.planks });
		parCraftingManager.addRecipe(new ItemStack(Blocks.sandstone), new Object[] { "##", "##", Character.valueOf('#'),
				new ItemStack(Blocks.sand, 1, BlockSand.EnumType.SAND.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.red_sandstone), new Object[] { "##", "##",
				Character.valueOf('#'), new ItemStack(Blocks.sand, 1, BlockSand.EnumType.RED_SAND.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.sandstone, 4, BlockSandStone.EnumType.SMOOTH.getMetadata()),
				new Object[] { "##", "##", Character.valueOf('#'),
						new ItemStack(Blocks.sandstone, 1, BlockSandStone.EnumType.DEFAULT.getMetadata()) });
		parCraftingManager.addRecipe(
				new ItemStack(Blocks.red_sandstone, 4, BlockRedSandstone.EnumType.SMOOTH.getMetadata()),
				new Object[] { "##", "##", Character.valueOf('#'),
						new ItemStack(Blocks.red_sandstone, 1, BlockRedSandstone.EnumType.DEFAULT.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.sandstone, 1, BlockSandStone.EnumType.CHISELED.getMetadata()),
				new Object[] { "#", "#", Character.valueOf('#'),
						new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab.EnumType.SAND.getMetadata()) });
		parCraftingManager.addRecipe(
				new ItemStack(Blocks.red_sandstone, 1, BlockRedSandstone.EnumType.CHISELED.getMetadata()),
				new Object[] { "#", "#", Character.valueOf('#'),
						new ItemStack(Blocks.stone_slab2, 1, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 1, BlockQuartz.EnumType.CHISELED.getMetadata()),
				new Object[] { "#", "#", Character.valueOf('#'),
						new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab.EnumType.QUARTZ.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 2, BlockQuartz.EnumType.LINES_Y.getMetadata()),
				new Object[] { "#", "#", Character.valueOf('#'),
						new ItemStack(Blocks.quartz_block, 1, BlockQuartz.EnumType.DEFAULT.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.stonebrick, 4), new Object[] { "##", "##",
				Character.valueOf('#'), new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CHISELED_META),
				new Object[] { "#", "#", Character.valueOf('#'),
						new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()) });
		parCraftingManager.addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.MOSSY_META),
				new Object[] { Blocks.stonebrick, Blocks.vine });
		parCraftingManager.addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone, 1),
				new Object[] { Blocks.cobblestone, Blocks.vine });
		parCraftingManager.addRecipe(new ItemStack(Blocks.iron_bars, 16),
				new Object[] { "###", "###", Character.valueOf('#'), Items.iron_ingot });
		parCraftingManager.addRecipe(new ItemStack(Blocks.glass_pane, 16),
				new Object[] { "###", "###", Character.valueOf('#'), Blocks.glass });
		parCraftingManager.addRecipe(new ItemStack(Blocks.redstone_lamp, 1), new Object[] { " R ", "RGR", " R ",
				Character.valueOf('R'), Items.redstone, Character.valueOf('G'), Blocks.glowstone });
		parCraftingManager.addRecipe(new ItemStack(Blocks.beacon, 1),
				new Object[] { "GGG", "GSG", "OOO", Character.valueOf('G'), Blocks.glass, Character.valueOf('S'),
						Items.nether_star, Character.valueOf('O'), Blocks.obsidian });
		parCraftingManager.addRecipe(new ItemStack(Blocks.nether_brick, 1),
				new Object[] { "NN", "NN", Character.valueOf('N'), Items.netherbrick });
		parCraftingManager.addRecipe(new ItemStack(Blocks.stone, 2, BlockStone.EnumType.DIORITE.getMetadata()),
				new Object[] { "CQ", "QC", Character.valueOf('C'), Blocks.cobblestone, Character.valueOf('Q'),
						Items.quartz });
		parCraftingManager.addShapelessRecipe(new ItemStack(Blocks.stone, 1, BlockStone.EnumType.GRANITE.getMetadata()),
				new Object[] { new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetadata()),
						Items.quartz });
		parCraftingManager.addShapelessRecipe(
				new ItemStack(Blocks.stone, 2, BlockStone.EnumType.ANDESITE.getMetadata()),
				new Object[] { new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetadata()),
						Blocks.cobblestone });
		parCraftingManager.addRecipe(new ItemStack(Blocks.dirt, 4, BlockDirt.DirtType.COARSE_DIRT.getMetadata()),
				new Object[] { "DG", "GD", Character.valueOf('D'),
						new ItemStack(Blocks.dirt, 1, BlockDirt.DirtType.DIRT.getMetadata()), Character.valueOf('G'),
						Blocks.gravel });
		parCraftingManager.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata()),
				new Object[] { "SS", "SS", Character.valueOf('S'),
						new ItemStack(Blocks.stone, 1, BlockStone.EnumType.DIORITE.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata()),
				new Object[] { "SS", "SS", Character.valueOf('S'),
						new ItemStack(Blocks.stone, 1, BlockStone.EnumType.GRANITE.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.stone, 4, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata()),
				new Object[] { "SS", "SS", Character.valueOf('S'),
						new ItemStack(Blocks.stone, 1, BlockStone.EnumType.ANDESITE.getMetadata()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.ROUGH_META),
				new Object[] { "SS", "SS", Character.valueOf('S'), Items.prismarine_shard });
		parCraftingManager.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.BRICKS_META),
				new Object[] { "SSS", "SSS", "SSS", Character.valueOf('S'), Items.prismarine_shard });
		parCraftingManager.addRecipe(new ItemStack(Blocks.prismarine, 1, BlockPrismarine.DARK_META),
				new Object[] { "SSS", "SIS", "SSS", Character.valueOf('S'), Items.prismarine_shard,
						Character.valueOf('I'), new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()) });
		parCraftingManager.addRecipe(new ItemStack(Blocks.sea_lantern, 1, 0), new Object[] { "SCS", "CCC", "SCS",
				Character.valueOf('S'), Items.prismarine_shard, Character.valueOf('C'), Items.prismarine_crystals });
	}
}