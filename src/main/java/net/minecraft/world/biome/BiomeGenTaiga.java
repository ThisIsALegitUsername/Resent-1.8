package net.minecraft.world.biome;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.BlockDirt;
import net.minecraft.entity.passive.EntityWolf;
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
public class BiomeGenTaiga extends BiomeGenBase {
	private int field_150644_aH;

	public BiomeGenTaiga(int parInt1, int parInt2) {
		super(parInt1);
		this.field_150644_aH = parInt2;
		this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 4));
	}

	public void genTerrainBlocks(World world, EaglercraftRandom random, ChunkPrimer chunkprimer, int i, int j,
			double d0) {
		if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
			this.topBlock = Blocks.grass.getDefaultState();
			this.fillerBlock = Blocks.dirt.getDefaultState();
			if (d0 > 1.75D) {
				this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT,
						BlockDirt.DirtType.COARSE_DIRT);
			} else if (d0 > -0.95D) {
				this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT,
						BlockDirt.DirtType.PODZOL);
			}
		}

		this.generateBiomeTerrain(world, random, chunkprimer, i, j, d0);
	}

	protected BiomeGenBase createMutatedBiome(int i) {
		return this.biomeID == BiomeGenBase.megaTaiga.biomeID ? (new BiomeGenTaiga(i, 2)).func_150557_a(5858897, true)
				.setBiomeName("Mega Spruce Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F)
				.setHeight(new BiomeGenBase.Height(this.minHeight, this.maxHeight)) : super.createMutatedBiome(i);
	}
}