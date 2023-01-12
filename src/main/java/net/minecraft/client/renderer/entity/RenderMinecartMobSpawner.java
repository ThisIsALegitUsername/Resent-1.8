package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
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
public class RenderMinecartMobSpawner extends RenderMinecart<EntityMinecartMobSpawner> {
	public RenderMinecartMobSpawner(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	protected void func_180560_a(EntityMinecartMobSpawner entityminecartmobspawner, float f, IBlockState iblockstate) {
		super.func_180560_a(entityminecartmobspawner, f, iblockstate);
		if (iblockstate.getBlock() == Blocks.mob_spawner) {
			TileEntityMobSpawnerRenderer.renderMob(entityminecartmobspawner.func_98039_d(),
					entityminecartmobspawner.posX, entityminecartmobspawner.posY, entityminecartmobspawner.posZ, f);
		}

	}
}