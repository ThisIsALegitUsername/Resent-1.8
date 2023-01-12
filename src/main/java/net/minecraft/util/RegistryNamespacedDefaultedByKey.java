package net.minecraft.util;

import org.apache.commons.lang3.Validate;

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
public class RegistryNamespacedDefaultedByKey<K, V> extends RegistryNamespaced<K, V> {
	private final K defaultValueKey;
	private V defaultValue;

	public RegistryNamespacedDefaultedByKey(K parObject) {
		this.defaultValueKey = parObject;
	}

	public void register(int id, K parObject, V parObject2) {
		if (this.defaultValueKey.equals(parObject)) {
			this.defaultValue = parObject2;
		}

		super.register(id, parObject, parObject2);
	}

	/**+
	 * validates that this registry's key is non-null
	 */
	public void validateKey() {
		Validate.notNull(this.defaultValueKey);
	}

	public V getObject(K name) {
		Object object = super.getObject(name);
		return (V) (object == null ? this.defaultValue : object);
	}

	/**+
	 * Gets the object identified by the given ID.
	 */
	public V getObjectById(int id) {
		Object object = super.getObjectById(id);
		return (V) (object == null ? this.defaultValue : object);
	}
}