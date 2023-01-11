package net.minecraft.util;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.Maps;

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
public class MapPopulator {
	public static <K, V> Map<K, V> createMap(Iterable<K> keys, Iterable<V> values) {
		/**+
		 * Populate the given Map with the given keys and values.
		 */
		return populateMap(keys, values, Maps.newLinkedHashMap());
	}

	public static <K, V> Map<K, V> populateMap(Iterable<K> keys, Iterable<V> values, Map<K, V> map) {
		Iterator iterator = values.iterator();

		for (Object object : keys) {
			map.put((K) object, (V) iterator.next());
		}

		if (iterator.hasNext()) {
			throw new NoSuchElementException();
		} else {
			return map;
		}
	}
}