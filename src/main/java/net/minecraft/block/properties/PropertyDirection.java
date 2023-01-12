package net.minecraft.block.properties;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.util.EnumFacing;

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
public class PropertyDirection extends PropertyEnum<EnumFacing> {
	protected PropertyDirection(String name, Collection<EnumFacing> values) {
		super(name, EnumFacing.class, values);
	}

	/**+
	 * Create a new PropertyDirection with the given name
	 */
	public static PropertyDirection create(String name) {
		/**+
		 * Create a new PropertyDirection with the given name
		 */
		return create(name, Predicates.alwaysTrue());
	}

	/**+
	 * Create a new PropertyDirection with the given name
	 */
	public static PropertyDirection create(String name, Predicate<EnumFacing> filter) {
		/**+
		 * Create a new PropertyDirection with the given name
		 */
		return create(name, Collections2.filter(Lists.newArrayList(EnumFacing.values()), filter));
	}

	/**+
	 * Create a new PropertyDirection with the given name
	 */
	public static PropertyDirection create(String name, Collection<EnumFacing> values) {
		return new PropertyDirection(name, values);
	}
}