package net.minecraft.world;

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
public class DifficultyInstance {
	private final EnumDifficulty worldDifficulty;
	private final float additionalDifficulty;

	public DifficultyInstance(EnumDifficulty worldDifficulty, long worldTime, long chunkInhabitedTime,
			float moonPhaseFactor) {
		this.worldDifficulty = worldDifficulty;
		this.additionalDifficulty = this.calculateAdditionalDifficulty(worldDifficulty, worldTime, chunkInhabitedTime,
				moonPhaseFactor);
	}

	public float getAdditionalDifficulty() {
		return this.additionalDifficulty;
	}

	public float getClampedAdditionalDifficulty() {
		return this.additionalDifficulty < 2.0F ? 0.0F
				: (this.additionalDifficulty > 4.0F ? 1.0F : (this.additionalDifficulty - 2.0F) / 2.0F);
	}

	private float calculateAdditionalDifficulty(EnumDifficulty difficulty, long worldTime, long chunkInhabitedTime,
			float moonPhaseFactor) {
		if (difficulty == EnumDifficulty.PEACEFUL) {
			return 0.0F;
		} else {
			boolean flag = difficulty == EnumDifficulty.HARD;
			float f = 0.75F;
			float f1 = MathHelper.clamp_float(((float) worldTime + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
			f = f + f1;
			float f2 = 0.0F;
			f2 = f2 + MathHelper.clamp_float((float) chunkInhabitedTime / 3600000.0F, 0.0F, 1.0F)
					* (flag ? 1.0F : 0.75F);
			f2 = f2 + MathHelper.clamp_float(moonPhaseFactor * 0.25F, 0.0F, f1);
			if (difficulty == EnumDifficulty.EASY) {
				f2 *= 0.5F;
			}

			f = f + f2;
			return (float) difficulty.getDifficultyId() * f;
		}
	}
}