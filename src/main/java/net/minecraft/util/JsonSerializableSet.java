package net.minecraft.util;

import java.util.Set;

import org.json.JSONArray;

import com.google.common.collect.ForwardingSet;
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
public class JsonSerializableSet extends ForwardingSet<String> implements IJsonSerializable {
	/**+
	 * The set for this ForwardingSet to forward methods to.
	 */
	private final Set<String> underlyingSet = Sets.newHashSet();

	public void fromJson(Object jsonelement) {
		if (jsonelement instanceof JSONArray) {
			JSONArray arr = (JSONArray) jsonelement;
			for (int i = 0; i < arr.length(); ++i) {
				underlyingSet.add(arr.getString(i));
			}
		}

	}

	/**+
	 * Gets the JsonElement that can be serialized.
	 */
	public Object getSerializableElement() {
		JSONArray jsonarray = new JSONArray();

		for (String s : this) {
			jsonarray.put(s);
		}

		return jsonarray;
	}

	protected Set<String> delegate() {
		return this.underlyingSet;
	}
}