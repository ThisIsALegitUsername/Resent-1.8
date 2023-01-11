package net.minecraft.world.gen;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

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
public class NoiseGeneratorOctaves extends NoiseGenerator {
	private NoiseGeneratorImproved[] generatorCollection;
	private int octaves;

	public NoiseGeneratorOctaves(EaglercraftRandom parRandom, int parInt1) {
		this.octaves = parInt1;
		this.generatorCollection = new NoiseGeneratorImproved[parInt1];

		for (int i = 0; i < parInt1; ++i) {
			this.generatorCollection[i] = new NoiseGeneratorImproved(parRandom);
		}

	}

	/**+
	 * Bouncer function to the main one with some default arguments.
	 */
	public double[] generateNoiseOctaves(double[] parArrayOfDouble, int parInt1, int parInt2, int parInt3, int parInt4,
			int parInt5, int parInt6, double parDouble1, double parDouble2, double parDouble3) {
		if (parArrayOfDouble == null) {
			parArrayOfDouble = new double[parInt4 * parInt5 * parInt6];
		} else {
			for (int i = 0; i < parArrayOfDouble.length; ++i) {
				parArrayOfDouble[i] = 0.0D;
			}
		}

		double d3 = 1.0D;

		for (int j = 0; j < this.octaves; ++j) {
			double d0 = (double) parInt1 * d3 * parDouble1;
			double d1 = (double) parInt2 * d3 * parDouble2;
			double d2 = (double) parInt3 * d3 * parDouble3;
			long k = MathHelper.floor_double_long(d0);
			long l = MathHelper.floor_double_long(d2);
			d0 = d0 - (double) k;
			d2 = d2 - (double) l;
			k = k % 16777216L;
			l = l % 16777216L;
			d0 = d0 + (double) k;
			d2 = d2 + (double) l;
			this.generatorCollection[j].populateNoiseArray(parArrayOfDouble, d0, d1, d2, parInt4, parInt5, parInt6,
					parDouble1 * d3, parDouble2 * d3, parDouble3 * d3, d3);
			d3 /= 2.0D;
		}

		return parArrayOfDouble;
	}

	/**+
	 * Bouncer function to the main one with some default arguments.
	 */
	public double[] generateNoiseOctaves(double[] parArrayOfDouble, int parInt1, int parInt2, int parInt3, int parInt4,
			double parDouble1, double parDouble2, double parDouble3) {
		return this.generateNoiseOctaves(parArrayOfDouble, parInt1, 10, parInt2, parInt3, 1, parInt4, parDouble1, 1.0D,
				parDouble2);
	}
}