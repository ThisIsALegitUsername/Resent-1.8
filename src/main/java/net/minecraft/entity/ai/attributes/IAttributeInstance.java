package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

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
public interface IAttributeInstance {
	/**+
	 * Get the Attribute this is an instance of
	 */
	IAttribute getAttribute();

	double getBaseValue();

	void setBaseValue(double var1);

	Collection<AttributeModifier> getModifiersByOperation(int var1);

	Collection<AttributeModifier> func_111122_c();

	boolean hasModifier(AttributeModifier var1);

	/**+
	 * Returns attribute modifier, if any, by the given UUID
	 */
	AttributeModifier getModifier(EaglercraftUUID var1);

	void applyModifier(AttributeModifier var1);

	void removeModifier(AttributeModifier var1);

	void removeAllModifiers();

	double getAttributeValue();
}