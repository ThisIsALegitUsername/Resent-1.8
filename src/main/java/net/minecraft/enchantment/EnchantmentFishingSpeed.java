package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

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
public class EnchantmentFishingSpeed extends Enchantment {
	protected EnchantmentFishingSpeed(int enchID, ResourceLocation enchName, int enchWeight,
			EnumEnchantmentType enchType) {
		super(enchID, enchName, enchWeight, enchType);
		this.setName("fishingSpeed");
	}

	/**+
	 * Returns the minimal value of enchantability needed on the
	 * enchantment level passed.
	 */
	public int getMinEnchantability(int i) {
		return 15 + (i - 1) * 9;
	}

	/**+
	 * Returns the maximum value of enchantability nedded on the
	 * enchantment level passed.
	 */
	public int getMaxEnchantability(int i) {
		return super.getMinEnchantability(i) + 50;
	}

	/**+
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 3;
	}
}