package net.minecraft.util;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

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
public class EnchantmentNameParts {
	private static final EnchantmentNameParts instance = new EnchantmentNameParts();
	private EaglercraftRandom rand = new EaglercraftRandom();
	private String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale "
			.split(" ");

	public static EnchantmentNameParts getInstance() {
		return instance;
	}

	/**+
	 * Randomly generates a new name built up of 3 or 4 randomly
	 * selected words.
	 */
	public String generateNewRandomName() {
		int i = this.rand.nextInt(2) + 3;
		String s = "";

		for (int j = 0; j < i; ++j) {
			if (j > 0) {
				s = s + " ";
			}

			s = s + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
		}

		return s;
	}

	/**+
	 * Resets the underlying random number generator using a given
	 * seed.
	 */
	public void reseedRandomGenerator(long seed) {
		this.rand.setSeed(seed);
	}
}