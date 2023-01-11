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
public class EnchantmentLootBonus extends Enchantment {
	protected EnchantmentLootBonus(int parInt1, ResourceLocation parResourceLocation, int parInt2,
			EnumEnchantmentType parEnumEnchantmentType) {
		super(parInt1, parResourceLocation, parInt2, parEnumEnchantmentType);
		if (parEnumEnchantmentType == EnumEnchantmentType.DIGGER) {
			this.setName("lootBonusDigger");
		} else if (parEnumEnchantmentType == EnumEnchantmentType.FISHING_ROD) {
			this.setName("lootBonusFishing");
		} else {
			this.setName("lootBonus");
		}

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

	/**+
	 * Determines if the enchantment passed can be applyied together
	 * with this enchantment.
	 */
	public boolean canApplyTogether(Enchantment enchantment) {
		return super.canApplyTogether(enchantment) && enchantment.effectId != silkTouch.effectId;
	}
}