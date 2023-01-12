package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
public class ItemPickaxe extends ItemTool {
	private static Set<Block> EFFECTIVE_ON;

	public static void bootstrap() {
		EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone,
				Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab,
				Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore,
				Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone,
				Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore, Blocks.sandstone,
				Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab });
	}

	protected ItemPickaxe(Item.ToolMaterial material) {
		super(2.0F, material, EFFECTIVE_ON);
	}

	/**+
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(Block blockIn) {
		return blockIn == Blocks.obsidian ? this.toolMaterial.getHarvestLevel() == 3
				: (blockIn != Blocks.diamond_block && blockIn != Blocks.diamond_ore
						? (blockIn != Blocks.emerald_ore && blockIn != Blocks.emerald_block
								? (blockIn != Blocks.gold_block && blockIn != Blocks.gold_ore
										? (blockIn != Blocks.iron_block && blockIn != Blocks.iron_ore
												? (blockIn != Blocks.lapis_block && blockIn != Blocks.lapis_ore
														? (blockIn != Blocks.redstone_ore
																&& blockIn != Blocks.lit_redstone_ore
																		? (blockIn.getMaterial() == Material.rock ? true
																				: (blockIn
																						.getMaterial() == Material.iron
																								? true
																								: blockIn
																										.getMaterial() == Material.anvil))
																		: this.toolMaterial.getHarvestLevel() >= 2)
														: this.toolMaterial.getHarvestLevel() >= 1)
												: this.toolMaterial.getHarvestLevel() >= 1)
										: this.toolMaterial.getHarvestLevel() >= 2)
								: this.toolMaterial.getHarvestLevel() >= 2)
						: this.toolMaterial.getHarvestLevel() >= 2);
	}

	public float getStrVsBlock(ItemStack stack, Block block) {
		return block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil
				&& block.getMaterial() != Material.rock ? super.getStrVsBlock(stack, block)
						: this.efficiencyOnProperMaterial;
	}
}