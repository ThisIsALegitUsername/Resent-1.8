package net.minecraft.world.biome;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import com.google.common.collect.Lists;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

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
public class BiomeGenMutated extends BiomeGenBase {
	protected BiomeGenBase baseBiome;

	public BiomeGenMutated(int id, BiomeGenBase biome) {
		super(id);
		this.baseBiome = biome;
		this.func_150557_a(biome.color, true);
		this.biomeName = biome.biomeName + " M";
		this.topBlock = biome.topBlock;
		this.fillerBlock = biome.fillerBlock;
		this.fillerBlockMetadata = biome.fillerBlockMetadata;
		this.minHeight = biome.minHeight;
		this.maxHeight = biome.maxHeight;
		this.temperature = biome.temperature;
		this.rainfall = biome.rainfall;
		this.waterColorMultiplier = biome.waterColorMultiplier;
		this.enableSnow = biome.enableSnow;
		this.enableRain = biome.enableRain;
		this.spawnableCreatureList = Lists.newArrayList(biome.spawnableCreatureList);
		this.spawnableMonsterList = Lists.newArrayList(biome.spawnableMonsterList);
		this.spawnableCaveCreatureList = Lists.newArrayList(biome.spawnableCaveCreatureList);
		this.spawnableWaterCreatureList = Lists.newArrayList(biome.spawnableWaterCreatureList);
		this.temperature = biome.temperature;
		this.rainfall = biome.rainfall;
		this.minHeight = biome.minHeight + 0.1F;
		this.maxHeight = biome.maxHeight + 0.2F;
	}

	public void genTerrainBlocks(World world, EaglercraftRandom random, ChunkPrimer chunkprimer, int i, int j,
			double d0) {
		this.baseBiome.genTerrainBlocks(world, random, chunkprimer, i, j, d0);
	}

	/**+
	 * returns the chance a creature has to spawn.
	 */
	public float getSpawningChance() {
		return this.baseBiome.getSpawningChance();
	}

	public int getFoliageColorAtPos(BlockPos blockpos) {
		return this.baseBiome.getFoliageColorAtPos(blockpos);
	}

	public int getGrassColorAtPos(BlockPos blockpos) {
		return this.baseBiome.getGrassColorAtPos(blockpos);
	}

	public Class<? extends BiomeGenBase> getBiomeClass() {
		return this.baseBiome.getBiomeClass();
	}

	/**+
	 * returns true if the biome specified is equal to this biome
	 */
	public boolean isEqualTo(BiomeGenBase biomegenbase) {
		return this.baseBiome.isEqualTo(biomegenbase);
	}

	public BiomeGenBase.TempCategory getTempCategory() {
		return this.baseBiome.getTempCategory();
	}
}