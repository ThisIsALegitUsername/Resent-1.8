package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

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
public class BiomeColorHelper {
	private static final BiomeColorHelper.ColorResolver field_180291_a = new BiomeColorHelper.ColorResolver() {
		public int getColorAtPos(BiomeGenBase blockPosition, BlockPos parBlockPos) {
			return blockPosition.getGrassColorAtPos(parBlockPos);
		}
	};
	private static final BiomeColorHelper.ColorResolver field_180289_b = new BiomeColorHelper.ColorResolver() {
		public int getColorAtPos(BiomeGenBase biomegenbase, BlockPos blockpos) {
			return biomegenbase.getFoliageColorAtPos(blockpos);
		}
	};
	private static final BiomeColorHelper.ColorResolver field_180290_c = new BiomeColorHelper.ColorResolver() {
		public int getColorAtPos(BiomeGenBase biomegenbase, BlockPos var2) {
			return biomegenbase.waterColorMultiplier;
		}
	};

	private static int func_180285_a(IBlockAccess parIBlockAccess, BlockPos parBlockPos,
			BiomeColorHelper.ColorResolver parColorResolver) {
		int i = 0;
		int j = 0;
		int k = 0;

		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(parBlockPos.add(-1, 0, -1),
				parBlockPos.add(1, 0, 1))) {
			int l = parColorResolver.getColorAtPos(parIBlockAccess.getBiomeGenForCoords(blockpos$mutableblockpos),
					blockpos$mutableblockpos);
			i += (l & 16711680) >> 16;
			j += (l & '\uff00') >> 8;
			k += l & 255;
		}

		return (i / 9 & 255) << 16 | (j / 9 & 255) << 8 | k / 9 & 255;
	}

	public static int getGrassColorAtPos(IBlockAccess parIBlockAccess, BlockPos parBlockPos) {
		return func_180285_a(parIBlockAccess, parBlockPos, field_180291_a);
	}

	public static int getFoliageColorAtPos(IBlockAccess parIBlockAccess, BlockPos parBlockPos) {
		return func_180285_a(parIBlockAccess, parBlockPos, field_180289_b);
	}

	public static int getWaterColorAtPos(IBlockAccess parIBlockAccess, BlockPos parBlockPos) {
		return func_180285_a(parIBlockAccess, parBlockPos, field_180290_c);
	}

	interface ColorResolver {
		int getColorAtPos(BiomeGenBase var1, BlockPos var2);
	}
}