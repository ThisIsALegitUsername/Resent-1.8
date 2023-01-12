package net.minecraft.util;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

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
public class ObjectIntIdentityMap<T> implements IObjectIntIterable<T> {
	private final IdentityHashMap<T, Integer> identityMap = new IdentityHashMap(512);
	private final List<T> objectList = Lists.newArrayList();

	public void put(T key, int value) {
		this.identityMap.put(key, Integer.valueOf(value));

		while (this.objectList.size() <= value) {
			this.objectList.add((T) null);
		}

		this.objectList.set(value, key);
	}

	public int get(T key) {
		Integer integer = (Integer) this.identityMap.get(key);
		return integer == null ? -1 : integer.intValue();
	}

	public final T getByValue(int value) {
		return (T) (value >= 0 && value < this.objectList.size() ? this.objectList.get(value) : null);
	}

	public Iterator<T> iterator() {
		return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
	}
}