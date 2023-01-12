package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
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
public class BlockSlime extends BlockBreakable {
	public BlockSlime() {
		super(Material.clay, false, MapColor.grassColor);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.slipperiness = 0.8F;
	}

	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	/**+
	 * Block's chance to react to a living entity falling on it.
	 */
	public void onFallenUpon(World world, BlockPos blockpos, Entity entity, float f) {
		if (entity.isSneaking()) {
			super.onFallenUpon(world, blockpos, entity, f);
		} else {
			entity.fall(f, 0.0F);
		}

	}

	/**+
	 * Called when an Entity lands on this Block. This method *must*
	 * update motionY because the entity will not do that on its own
	 */
	public void onLanded(World world, Entity entity) {
		if (entity.isSneaking()) {
			super.onLanded(world, entity);
		} else if (entity.motionY < 0.0D) {
			entity.motionY = -entity.motionY;
		}

	}

	/**+
	 * Triggered whenever an entity collides with this block (enters
	 * into the block)
	 */
	public void onEntityCollidedWithBlock(World world, BlockPos blockpos, Entity entity) {
		if (Math.abs(entity.motionY) < 0.1D && !entity.isSneaking()) {
			double d0 = 0.4D + Math.abs(entity.motionY) * 0.2D;
			entity.motionX *= d0;
			entity.motionZ *= d0;
		}

		super.onEntityCollidedWithBlock(world, blockpos, entity);
	}
}