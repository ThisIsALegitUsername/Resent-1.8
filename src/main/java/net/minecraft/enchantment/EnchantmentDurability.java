package net.minecraft.enchantment;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
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
public class EnchantmentDurability extends Enchantment {
	protected EnchantmentDurability(int enchID, ResourceLocation enchName, int enchWeight) {
		super(enchID, enchName, enchWeight, EnumEnchantmentType.BREAKABLE);
		this.setName("durability");
	}

	/**+
	 * Returns the minimal value of enchantability needed on the
	 * enchantment level passed.
	 */
	public int getMinEnchantability(int i) {
		return 5 + (i - 1) * 8;
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
	 * Determines if this enchantment can be applied to a specific
	 * ItemStack.
	 */
	public boolean canApply(ItemStack itemstack) {
		return itemstack.isItemStackDamageable() ? true : super.canApply(itemstack);
	}

	/**+
	 * Used by ItemStack.attemptDamageItem. Randomly determines if a
	 * point of damage should be negated using the enchantment level
	 * (par1). If the ItemStack is Armor then there is a flat 60%
	 * chance for damage to be negated no matter the enchantment
	 * level, otherwise there is a 1-(par/1) chance for damage to be
	 * negated.
	 */
	public static boolean negateDamage(ItemStack parItemStack, int parInt1, EaglercraftRandom parRandom) {
		return parItemStack.getItem() instanceof ItemArmor && parRandom.nextFloat() < 0.6F ? false
				: parRandom.nextInt(parInt1 + 1) > 0;
	}
}