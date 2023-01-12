package net.minecraft.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Maps;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;

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
public class RegistrySimple<K, V> implements IRegistry<K, V> {
	private static final Logger logger = LogManager.getLogger();
	protected final Map<K, V> registryObjects = this.createUnderlyingMap();

	protected Map<K, V> createUnderlyingMap() {
		return Maps.newHashMap();
	}

	public V getObject(K object) {
		return (V) this.registryObjects.get(object);
	}

	/**+
	 * Register an object on this registry.
	 */
	public void putObject(K object, V object1) {
		Validate.notNull(object);
		Validate.notNull(object1);
		if (this.registryObjects.containsKey(object)) {
			logger.debug("Adding duplicate key \'" + object + "\' to registry");
		}

		this.registryObjects.put(object, object1);
	}

	/**+
	 * Gets all the keys recognized by this registry.
	 */
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(this.registryObjects.keySet());
	}

	/**+
	 * Does this registry contain an entry for the given key?
	 */
	public boolean containsKey(K object) {
		return this.registryObjects.containsKey(object);
	}

	public Iterator<V> iterator() {
		return this.registryObjects.values().iterator();
	}
}