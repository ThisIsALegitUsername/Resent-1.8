package net.minecraft.item;

import com.google.common.base.Function;

import net.minecraft.block.Block;

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
public class ItemMultiTexture extends ItemBlock {
	protected final Block theBlock;
	protected final Function<ItemStack, String> nameFunction;

	public ItemMultiTexture(Block block, Block block2, Function<ItemStack, String> nameFunction) {
		super(block);
		this.theBlock = block2;
		this.nameFunction = nameFunction;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public ItemMultiTexture(Block block, Block block2, final String[] namesByMeta) {
		this(block, block2, new Function<ItemStack, String>() {
			public String apply(ItemStack parItemStack) {
				int i = parItemStack.getMetadata();
				if (i < 0 || i >= namesByMeta.length) {
					i = 0;
				}

				return namesByMeta[i];
			}
		});
	}

	/**+
	 * Converts the given ItemStack damage value into a metadata
	 * value to be placed in the world when this Item is placed as a
	 * Block (mostly used with ItemBlocks).
	 */
	public int getMetadata(int i) {
		return i;
	}

	/**+
	 * Returns the unlocalized name of this item. This version
	 * accepts an ItemStack so different stacks can have different
	 * names based on their damage or NBT.
	 */
	public String getUnlocalizedName(ItemStack itemstack) {
		return super.getUnlocalizedName() + "." + (String) this.nameFunction.apply(itemstack);
	}
}