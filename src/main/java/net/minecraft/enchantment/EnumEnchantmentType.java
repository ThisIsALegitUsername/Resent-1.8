package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

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
public enum EnumEnchantmentType {
	ALL, ARMOR, ARMOR_FEET, ARMOR_LEGS, ARMOR_TORSO, ARMOR_HEAD, WEAPON, DIGGER, FISHING_ROD, BREAKABLE, BOW;

	/**+
	 * Return true if the item passed can be enchanted by a
	 * enchantment of this type.
	 */
	public boolean canEnchantItem(Item parItem) {
		if (this == ALL) {
			return true;
		} else if (this == BREAKABLE && parItem.isDamageable()) {
			return true;
		} else if (parItem instanceof ItemArmor) {
			if (this == ARMOR) {
				return true;
			} else {
				ItemArmor itemarmor = (ItemArmor) parItem;
				return itemarmor.armorType == 0 ? this == ARMOR_HEAD
						: (itemarmor.armorType == 2 ? this == ARMOR_LEGS
								: (itemarmor.armorType == 1 ? this == ARMOR_TORSO
										: (itemarmor.armorType == 3 ? this == ARMOR_FEET : false)));
			}
		} else {
			return parItem instanceof ItemSword ? this == WEAPON
					: (parItem instanceof ItemTool ? this == DIGGER
							: (parItem instanceof ItemBow ? this == BOW
									: (parItem instanceof ItemFishingRod ? this == FISHING_ROD : false)));
		}
	}
}