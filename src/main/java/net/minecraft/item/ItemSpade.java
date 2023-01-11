package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

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
public class ItemSpade extends ItemTool {
	private static Set<Block> EFFECTIVE_ON;

	public static void bootstrap() {
		EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass,
				Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand });
	}

	public ItemSpade(Item.ToolMaterial material) {
		super(1.0F, material, EFFECTIVE_ON);
	}

	/**+
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(Block block) {
		return block == Blocks.snow_layer ? true : block == Blocks.snow;
	}
}