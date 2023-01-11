package net.minecraft.world;

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
public enum EnumDifficulty {
	PEACEFUL(0, "options.difficulty.peaceful"), EASY(1, "options.difficulty.easy"),
	NORMAL(2, "options.difficulty.normal"), HARD(3, "options.difficulty.hard");

	private static final EnumDifficulty[] difficultyEnums = new EnumDifficulty[values().length];
	private final int difficultyId;
	private final String difficultyResourceKey;

	private EnumDifficulty(int difficultyIdIn, String difficultyResourceKeyIn) {
		this.difficultyId = difficultyIdIn;
		this.difficultyResourceKey = difficultyResourceKeyIn;
	}

	public int getDifficultyId() {
		return this.difficultyId;
	}

	public static EnumDifficulty getDifficultyEnum(int parInt1) {
		return difficultyEnums[parInt1 % difficultyEnums.length];
	}

	public String getDifficultyResourceKey() {
		return this.difficultyResourceKey;
	}

	static {
		for (EnumDifficulty enumdifficulty : values()) {
			difficultyEnums[enumdifficulty.difficultyId] = enumdifficulty;
		}

	}
}