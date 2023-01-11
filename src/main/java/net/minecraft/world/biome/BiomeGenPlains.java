package net.minecraft.world.biome;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityHorse;
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
public class BiomeGenPlains extends BiomeGenBase {
	protected boolean field_150628_aC;

	protected BiomeGenPlains(int parInt1) {
		super(parInt1);
		this.setTemperatureRainfall(0.8F, 0.4F);
		this.setHeight(height_LowPlains);
		this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 5, 2, 6));
	}

	public BlockFlower.EnumFlowerType pickRandomFlower(EaglercraftRandom random, BlockPos blockpos) {
		double d0 = GRASS_COLOR_NOISE.func_151601_a((double) blockpos.getX() / 200.0D,
				(double) blockpos.getZ() / 200.0D);
		if (d0 < -0.8D) {
			int j = random.nextInt(4);
			switch (j) {
			case 0:
				return BlockFlower.EnumFlowerType.ORANGE_TULIP;
			case 1:
				return BlockFlower.EnumFlowerType.RED_TULIP;
			case 2:
				return BlockFlower.EnumFlowerType.PINK_TULIP;
			case 3:
			default:
				return BlockFlower.EnumFlowerType.WHITE_TULIP;
			}
		} else if (random.nextInt(3) > 0) {
			int i = random.nextInt(3);
			return i == 0 ? BlockFlower.EnumFlowerType.POPPY
					: (i == 1 ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY);
		} else {
			return BlockFlower.EnumFlowerType.DANDELION;
		}
	}

	protected BiomeGenBase createMutatedBiome(int i) {
		BiomeGenPlains biomegenplains = new BiomeGenPlains(i);
		biomegenplains.setBiomeName("Sunflower Plains");
		biomegenplains.field_150628_aC = true;
		biomegenplains.setColor(9286496);
		biomegenplains.field_150609_ah = 14273354;
		return biomegenplains;
	}
}