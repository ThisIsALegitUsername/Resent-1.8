package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
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
public class BlockSoulSand extends Block {
	public BlockSoulSand() {
		super(Material.sand, MapColor.brownColor);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos blockpos, IBlockState var3) {
		float f = 0.125F;
		return new AxisAlignedBB((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(),
				(double) (blockpos.getX() + 1), (double) ((float) (blockpos.getY() + 1) - f),
				(double) (blockpos.getZ() + 1));
	}

	/**+
	 * Called When an Entity Collided with the Block
	 */
	public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity entity) {
		entity.motionX *= 0.4D;
		entity.motionZ *= 0.4D;
	}
}