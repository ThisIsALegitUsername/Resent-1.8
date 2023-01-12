package net.minecraft.block.properties;

import java.util.Collection;
import java.util.HashSet;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

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
public class PropertyInteger extends PropertyHelper<Integer> {
	private final ImmutableSet<Integer> allowedValues;

	protected PropertyInteger(String name, int min, int max) {
		super(name, Integer.class);
		if (min < 0) {
			throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
		} else if (max <= min) {
			throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
		} else {
			HashSet hashset = Sets.newHashSet();

			for (int i = min; i <= max; ++i) {
				hashset.add(Integer.valueOf(i));
			}

			this.allowedValues = ImmutableSet.copyOf(hashset);
		}
	}

	public Collection<Integer> getAllowedValues() {
		return this.allowedValues;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			if (!super.equals(object)) {
				return false;
			} else {
				PropertyInteger propertyinteger = (PropertyInteger) object;
				return this.allowedValues.equals(propertyinteger.allowedValues);
			}
		} else {
			return false;
		}
	}

	public int hashCode() {
		int i = super.hashCode();
		i = 31 * i + this.allowedValues.hashCode();
		return i;
	}

	public static PropertyInteger create(String name, int min, int max) {
		return new PropertyInteger(name, min, max);
	}

	/**+
	 * Get the name for the given value.
	 */
	public String getName(Object integer) {
		return integer.toString();
	}
}