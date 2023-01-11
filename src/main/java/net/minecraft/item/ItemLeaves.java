package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

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
public class ItemLeaves extends ItemBlock {
	private final BlockLeaves leaves;

	public ItemLeaves(BlockLeaves block) {
		super(block);
		this.leaves = block;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	/**+
	 * Converts the given ItemStack damage value into a metadata
	 * value to be placed in the world when this Item is placed as a
	 * Block (mostly used with ItemBlocks).
	 */
	public int getMetadata(int damage) {
		return damage | 4;
	}

	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return this.leaves.getRenderColor(this.leaves.getStateFromMeta(stack.getMetadata()));
	}

	/**+
	 * Returns the unlocalized name of this item. This version
	 * accepts an ItemStack so different stacks can have different
	 * names based on their damage or NBT.
	 */
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + this.leaves.getWoodType(stack.getMetadata()).getUnlocalizedName();
	}
}