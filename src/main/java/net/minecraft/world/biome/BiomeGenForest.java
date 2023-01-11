package net.minecraft.world.biome;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

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
public class BiomeGenForest extends BiomeGenBase {
	private int field_150632_aF;

	public BiomeGenForest(int parInt1, int parInt2) {
		super(parInt1);
		this.field_150632_aF = parInt2;

		this.setFillerBlockMetadata(5159473);
		this.setTemperatureRainfall(0.7F, 0.8F);
		if (this.field_150632_aF == 2) {
			this.field_150609_ah = 353825;
			this.color = 3175492;
			this.setTemperatureRainfall(0.6F, 0.6F);
		}

		if (this.field_150632_aF == 0) {
			this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 5, 4, 4));
		}

	}

	protected BiomeGenBase func_150557_a(int i, boolean flag) {
		if (this.field_150632_aF == 2) {
			this.field_150609_ah = 353825;
			this.color = i;
			if (flag) {
				this.field_150609_ah = (this.field_150609_ah & 16711422) >> 1;
			}

			return this;
		} else {
			return super.func_150557_a(i, flag);
		}
	}

	public BlockFlower.EnumFlowerType pickRandomFlower(EaglercraftRandom random, BlockPos blockpos) {
		if (this.field_150632_aF == 1) {
			double d0 = MathHelper.clamp_double((1.0D + GRASS_COLOR_NOISE
					.func_151601_a((double) blockpos.getX() / 48.0D, (double) blockpos.getZ() / 48.0D)) / 2.0D, 0.0D,
					0.9999D);
			BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType
					.values()[(int) (d0 * (double) BlockFlower.EnumFlowerType.values().length)];
			return blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID
					? BlockFlower.EnumFlowerType.POPPY
					: blockflower$enumflowertype;
		} else {
			return super.pickRandomFlower(random, blockpos);
		}
	}

	public int getGrassColorAtPos(BlockPos blockpos) {
		int i = super.getGrassColorAtPos(blockpos);
		return this.field_150632_aF == 3 ? (i & 16711422) + 2634762 >> 1 : i;
	}

	protected BiomeGenBase createMutatedBiome(final int i) {
		if (this.biomeID == BiomeGenBase.forest.biomeID) {
			BiomeGenForest biomegenforest = new BiomeGenForest(i, 1);
			biomegenforest.setHeight(new BiomeGenBase.Height(this.minHeight, this.maxHeight + 0.2F));
			biomegenforest.setBiomeName("Flower Forest");
			biomegenforest.func_150557_a(6976549, true);
			biomegenforest.setFillerBlockMetadata(8233509);
			return biomegenforest;
		} else {
			return this.biomeID != BiomeGenBase.birchForest.biomeID
					&& this.biomeID != BiomeGenBase.birchForestHills.biomeID ? new BiomeGenMutated(i, this)
							: new BiomeGenMutated(i, this);
		}
	}
}