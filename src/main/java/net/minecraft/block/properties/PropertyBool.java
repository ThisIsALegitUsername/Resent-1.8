package net.minecraft.block.properties;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;

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
public class PropertyBool extends PropertyHelper<Boolean> {
	private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(Boolean.valueOf(true), Boolean.valueOf(false));

	protected PropertyBool(String name) {
		super(name, Boolean.class);
	}

	public Collection<Boolean> getAllowedValues() {
		return this.allowedValues;
	}

	public static PropertyBool create(String name) {
		return new PropertyBool(name);
	}

	/**+
	 * Get the name for the given value.
	 */
	public String getName(Object value) {
		return ((Boolean) value).toString();
	}
}