package net.minecraft.world.biome;

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
public class BiomeGenSnow extends BiomeGenBase {
	private boolean field_150615_aC;

	public BiomeGenSnow(int parInt1, boolean parFlag) {
		super(parInt1);
		this.field_150615_aC = parFlag;
		if (parFlag) {
			this.topBlock = Blocks.snow.getDefaultState();
		}

		this.spawnableCreatureList.clear();
	}

	protected BiomeGenBase createMutatedBiome(int i) {
		BiomeGenBase biomegenbase = (new BiomeGenSnow(i, true)).func_150557_a(13828095, true)
				.setBiomeName(this.biomeName + " Spikes").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F)
				.setHeight(new BiomeGenBase.Height(this.minHeight + 0.1F, this.maxHeight + 0.1F));
		biomegenbase.minHeight = this.minHeight + 0.3F;
		biomegenbase.maxHeight = this.maxHeight + 0.4F;
		return biomegenbase;
	}
}