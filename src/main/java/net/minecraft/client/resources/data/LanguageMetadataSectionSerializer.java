package net.minecraft.client.resources.data;

import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Sets;

import net.minecraft.client.resources.Language;

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
public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer<LanguageMetadataSection> {
	public LanguageMetadataSection deserialize(JSONObject jsonobject) throws JSONException {
		HashSet hashset = Sets.newHashSet();

		for (String s : jsonobject.keySet()) {
			JSONObject jsonobject1 = jsonobject.getJSONObject(s);
			String s1 = jsonobject1.getString("region");
			String s2 = jsonobject1.getString("name");
			boolean flag = jsonobject1.optBoolean("bidirectional", false);
			if (s1.isEmpty()) {
				throw new JSONException("Invalid language->\'" + s + "\'->region: empty value");
			}

			if (s2.isEmpty()) {
				throw new JSONException("Invalid language->\'" + s + "\'->name: empty value");
			}

			if (!hashset.add(new Language(s, s1, s2, flag))) {
				throw new JSONException("Duplicate language->\'" + s + "\' defined");
			}
		}

		return new LanguageMetadataSection(hashset);
	}

	/**+
	 * The name of this section type as it appears in JSON.
	 */
	public String getSectionName() {
		return "language";
	}
}