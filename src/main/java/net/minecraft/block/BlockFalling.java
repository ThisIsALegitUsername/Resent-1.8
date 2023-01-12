package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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
public class BlockFalling extends Block {
	public static boolean fallInstantly;

	public BlockFalling() {
		super(Material.sand);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public BlockFalling(Material materialIn) {
		super(materialIn);
	}

	public void onBlockAdded(World world, BlockPos blockpos, IBlockState var3) {
		world.scheduleUpdate(blockpos, this, this.tickRate(world));
	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState var3, Block var4) {
		world.scheduleUpdate(blockpos, this, this.tickRate(world));
	}

	protected void onStartFalling(EntityFallingBlock var1) {
	}

	/**+
	 * How many world ticks before ticking
	 */
	public int tickRate(World var1) {
		return 2;
	}

	public static boolean canFallInto(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		Material material = block.blockMaterial;
		return block == Blocks.fire || material == Material.air || material == Material.water
				|| material == Material.lava;
	}

	public void onEndFalling(World var1, BlockPos var2) {
	}
}