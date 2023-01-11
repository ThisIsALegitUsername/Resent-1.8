package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
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
public class EnchantmentProtection extends Enchantment {
	/**+
	 * Holds the name to be translated of each protection type.
	 */
	private static final String[] protectionName = new String[] { "all", "fire", "fall", "explosion", "projectile" };
	/**+
	 * Holds the base factor of enchantability needed to be able to
	 * use the enchant.
	 */
	private static final int[] baseEnchantability = new int[] { 1, 10, 5, 5, 3 };
	/**+
	 * Holds how much each level increased the enchantability factor
	 * to be able to use this enchant.
	 */
	private static final int[] levelEnchantability = new int[] { 11, 8, 6, 8, 6 };
	/**+
	 * Used on the formula of base enchantability, this is the
	 * 'window' factor of values to be able to use thing enchant.
	 */
	private static final int[] thresholdEnchantability = new int[] { 20, 12, 10, 12, 15 };
	public final int protectionType;

	public EnchantmentProtection(int parInt1, ResourceLocation parResourceLocation, int parInt2, int parInt3) {
		super(parInt1, parResourceLocation, parInt2, EnumEnchantmentType.ARMOR);
		this.protectionType = parInt3;
		if (parInt3 == 2) {
			this.type = EnumEnchantmentType.ARMOR_FEET;
		}

	}

	/**+
	 * Returns the minimal value of enchantability needed on the
	 * enchantment level passed.
	 */
	public int getMinEnchantability(int i) {
		return baseEnchantability[this.protectionType] + (i - 1) * levelEnchantability[this.protectionType];
	}

	/**+
	 * Returns the maximum value of enchantability nedded on the
	 * enchantment level passed.
	 */
	public int getMaxEnchantability(int i) {
		return this.getMinEnchantability(i) + thresholdEnchantability[this.protectionType];
	}

	/**+
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 4;
	}

	/**+
	 * Calculates the damage protection of the enchantment based on
	 * level and damage source passed.
	 */
	public int calcModifierDamage(int i, DamageSource damagesource) {
		if (damagesource.canHarmInCreative()) {
			return 0;
		} else {
			float f = (float) (6 + i * i) / 3.0F;
			return this.protectionType == 0 ? MathHelper.floor_float(f * 0.75F)
					: (this.protectionType == 1 && damagesource.isFireDamage() ? MathHelper.floor_float(f * 1.25F)
							: (this.protectionType == 2 && damagesource == DamageSource.fall
									? MathHelper.floor_float(f * 2.5F)
									: (this.protectionType == 3 && damagesource.isExplosion()
											? MathHelper.floor_float(f * 1.5F)
											: (this.protectionType == 4 && damagesource.isProjectile()
													? MathHelper.floor_float(f * 1.5F)
													: 0))));
		}
	}

	/**+
	 * Return the name of key in translation table of this
	 * enchantment.
	 */
	public String getName() {
		return "enchantment.protect." + protectionName[this.protectionType];
	}

	/**+
	 * Determines if the enchantment passed can be applyied together
	 * with this enchantment.
	 */
	public boolean canApplyTogether(Enchantment enchantment) {
		if (enchantment instanceof EnchantmentProtection) {
			EnchantmentProtection enchantmentprotection = (EnchantmentProtection) enchantment;
			return enchantmentprotection.protectionType == this.protectionType ? false
					: this.protectionType == 2 || enchantmentprotection.protectionType == 2;
		} else {
			return super.canApplyTogether(enchantment);
		}
	}

	/**+
	 * Gets the amount of ticks an entity should be set fire,
	 * adjusted for fire protection.
	 */
	public static int getFireTimeForEntity(Entity parEntity, int parInt1) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, parEntity.getInventory());
		if (i > 0) {
			parInt1 -= MathHelper.floor_float((float) parInt1 * (float) i * 0.15F);
		}

		return parInt1;
	}

	public static double func_92092_a(Entity parEntity, double parDouble1) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId,
				parEntity.getInventory());
		if (i > 0) {
			parDouble1 -= (double) MathHelper.floor_double(parDouble1 * (double) ((float) i * 0.15F));
		}

		return parDouble1;
	}
}