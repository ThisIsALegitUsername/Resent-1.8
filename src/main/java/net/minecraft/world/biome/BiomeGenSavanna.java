package net.minecraft.world.biome;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.BlockDirt;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
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
public class BiomeGenSavanna extends BiomeGenBase {

	protected BiomeGenSavanna(int parInt1) {
		super(parInt1);
		this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
	}

	protected BiomeGenBase createMutatedBiome(int i) {
		BiomeGenSavanna.Mutated biomegensavanna$mutated = new BiomeGenSavanna.Mutated(i, this);
		biomegensavanna$mutated.temperature = (this.temperature + 1.0F) * 0.5F;
		biomegensavanna$mutated.minHeight = this.minHeight * 0.5F + 0.3F;
		biomegensavanna$mutated.maxHeight = this.maxHeight * 0.5F + 1.2F;
		return biomegensavanna$mutated;
	}

	public static class Mutated extends BiomeGenMutated {
		public Mutated(int parInt1, BiomeGenBase parBiomeGenBase) {
			super(parInt1, parBiomeGenBase);
		}

		public void genTerrainBlocks(World world, EaglercraftRandom random, ChunkPrimer chunkprimer, int i, int j,
				double d0) {
			this.topBlock = Blocks.grass.getDefaultState();
			this.fillerBlock = Blocks.dirt.getDefaultState();
			if (d0 > 1.75D) {
				this.topBlock = Blocks.stone.getDefaultState();
				this.fillerBlock = Blocks.stone.getDefaultState();
			} else if (d0 > -0.5D) {
				this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT,
						BlockDirt.DirtType.COARSE_DIRT);
			}

			this.generateBiomeTerrain(world, random, chunkprimer, i, j, d0);
		}
	}
}