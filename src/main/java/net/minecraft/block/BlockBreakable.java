package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

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
public class BlockBreakable extends Block {
	private boolean ignoreSimilarity;

	protected BlockBreakable(Material materialIn, boolean ignoreSimilarityIn) {
		this(materialIn, ignoreSimilarityIn, materialIn.getMaterialMapColor());
	}

	protected BlockBreakable(Material parMaterial, boolean parFlag, MapColor parMapColor) {
		super(parMaterial, parMapColor);
		this.ignoreSimilarity = parFlag;
	}

	/**+
	 * Used to determine ambient occlusion and culling when
	 * rebuilding chunks for render
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, BlockPos blockpos, EnumFacing enumfacing) {
		IBlockState iblockstate = iblockaccess.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		if (this == Blocks.glass || this == Blocks.stained_glass) {
			if (iblockaccess.getBlockState(blockpos.offset(enumfacing.getOpposite())) != iblockstate) {
				return true;
			}

			if (block == this) {
				return false;
			}
		}

		return !this.ignoreSimilarity && block == this ? false
				: super.shouldSideBeRendered(iblockaccess, blockpos, enumfacing);
	}
}