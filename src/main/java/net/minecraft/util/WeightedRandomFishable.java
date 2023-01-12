package net.minecraft.util;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

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
public class WeightedRandomFishable extends WeightedRandom.Item {
	private final ItemStack returnStack;
	private float maxDamagePercent;
	private boolean enchantable;

	public WeightedRandomFishable(ItemStack returnStackIn, int itemWeightIn) {
		super(itemWeightIn);
		this.returnStack = returnStackIn;
	}

	public ItemStack getItemStack(EaglercraftRandom random) {
		ItemStack itemstack = this.returnStack.copy();
		if (this.maxDamagePercent > 0.0F) {
			int i = (int) (this.maxDamagePercent * (float) this.returnStack.getMaxDamage());
			int j = itemstack.getMaxDamage() - random.nextInt(random.nextInt(i) + 1);
			if (j > i) {
				j = i;
			}

			if (j < 1) {
				j = 1;
			}

			itemstack.setItemDamage(j);
		}

		if (this.enchantable) {
			EnchantmentHelper.addRandomEnchantment(random, itemstack, 30);
		}

		return itemstack;
	}

	public WeightedRandomFishable setMaxDamagePercent(float maxDamagePercentIn) {
		this.maxDamagePercent = maxDamagePercentIn;
		return this;
	}

	public WeightedRandomFishable setEnchantable() {
		this.enchantable = true;
		return this;
	}
}