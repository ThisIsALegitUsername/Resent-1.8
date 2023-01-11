package net.minecraft.world.biome;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.BlockFlower;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.BlockPos;

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
public class BiomeGenSwamp extends BiomeGenBase {
	protected BiomeGenSwamp(int parInt1) {
		super(parInt1);
		this.waterColorMultiplier = 14745518;
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 1, 1, 1));
	}

	public int getGrassColorAtPos(BlockPos blockpos) {
		double d0 = GRASS_COLOR_NOISE.func_151601_a((double) blockpos.getX() * 0.0225D,
				(double) blockpos.getZ() * 0.0225D);
		return d0 < -0.1D ? 5011004 : 6975545;
	}

	public int getFoliageColorAtPos(BlockPos var1) {
		return 6975545;
	}

	public BlockFlower.EnumFlowerType pickRandomFlower(EaglercraftRandom var1, BlockPos var2) {
		return BlockFlower.EnumFlowerType.BLUE_ORCHID;
	}
}