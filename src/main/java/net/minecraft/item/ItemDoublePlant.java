package net.minecraft.item;

import com.google.common.base.Function;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.world.ColorizerGrass;

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
public class ItemDoublePlant extends ItemMultiTexture {
	public ItemDoublePlant(Block block, Block block2, Function<ItemStack, String> nameFunction) {
		super(block, block2, nameFunction);
	}

	public int getColorFromItemStack(ItemStack itemstack, int i) {
		BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = BlockDoublePlant.EnumPlantType
				.byMetadata(itemstack.getMetadata());
		return blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS
				&& blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN
						? super.getColorFromItemStack(itemstack, i)
						: ColorizerGrass.getGrassColor(0.5D, 1.0D);
	}
}