package net.minecraft.world.biome;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

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
public class BiomeGenHills extends BiomeGenBase {
	private int field_150635_aE = 0;
	private int field_150636_aF = 1;
	private int field_150637_aG = 2;
	private int field_150638_aH;

	protected BiomeGenHills(int parInt1, boolean parFlag) {
		super(parInt1);
		this.field_150638_aH = this.field_150635_aE;
		if (parFlag) {
			this.field_150638_aH = this.field_150636_aF;
		}

	}

	public void genTerrainBlocks(World world, EaglercraftRandom random, ChunkPrimer chunkprimer, int i, int j,
			double d0) {
		this.topBlock = Blocks.grass.getDefaultState();
		this.fillerBlock = Blocks.dirt.getDefaultState();
		if ((d0 < -1.0D || d0 > 2.0D) && this.field_150638_aH == this.field_150637_aG) {
			this.topBlock = Blocks.gravel.getDefaultState();
			this.fillerBlock = Blocks.gravel.getDefaultState();
		} else if (d0 > 1.0D && this.field_150638_aH != this.field_150636_aF) {
			this.topBlock = Blocks.stone.getDefaultState();
			this.fillerBlock = Blocks.stone.getDefaultState();
		}

		this.generateBiomeTerrain(world, random, chunkprimer, i, j, d0);
	}

	/**+
	 * this creates a mutation specific to Hills biomes
	 */
	private BiomeGenHills mutateHills(BiomeGenBase parBiomeGenBase) {
		this.field_150638_aH = this.field_150637_aG;
		this.func_150557_a(parBiomeGenBase.color, true);
		this.setBiomeName(parBiomeGenBase.biomeName + " M");
		this.setHeight(new BiomeGenBase.Height(parBiomeGenBase.minHeight, parBiomeGenBase.maxHeight));
		this.setTemperatureRainfall(parBiomeGenBase.temperature, parBiomeGenBase.rainfall);
		return this;
	}

	protected BiomeGenBase createMutatedBiome(int i) {
		return (new BiomeGenHills(i, false)).mutateHills(this);
	}
}