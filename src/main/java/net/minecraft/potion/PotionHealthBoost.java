package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
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
public class PotionHealthBoost extends Potion {
	public PotionHealthBoost(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
		super(potionID, location, badEffect, potionColor);
	}

	public void removeAttributesModifiersFromEntity(EntityLivingBase entitylivingbase,
			BaseAttributeMap baseattributemap, int i) {
		super.removeAttributesModifiersFromEntity(entitylivingbase, baseattributemap, i);
		if (entitylivingbase.getHealth() > entitylivingbase.getMaxHealth()) {
			entitylivingbase.setHealth(entitylivingbase.getMaxHealth());
		}

	}
}