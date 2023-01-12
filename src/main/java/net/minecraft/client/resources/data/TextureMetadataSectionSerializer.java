package net.minecraft.client.resources.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer<TextureMetadataSection> {
	public TextureMetadataSection deserialize(JSONObject jsonobject) throws JSONException {
		boolean flag = jsonobject.optBoolean("blur", false);
		boolean flag1 = jsonobject.optBoolean("clamp", false);
		ArrayList arraylist = Lists.newArrayList();
		if (jsonobject.has("mipmaps")) {
			try {
				JSONArray jsonarray = jsonobject.getJSONArray("mipmaps");

				for (int i = 0; i < jsonarray.length(); ++i) {
					Object jsonelement = jsonarray.get(i);
					if (jsonelement instanceof Number) {
						try {
							arraylist.add(((Number) jsonelement).intValue());
						} catch (NumberFormatException numberformatexception) {
							throw new JSONException(
									"Invalid texture->mipmap->" + i + ": expected number, was " + jsonelement,
									numberformatexception);
						}
					} else if (jsonelement instanceof JSONObject) {
						throw new JSONException(
								"Invalid texture->mipmap->" + i + ": expected number, was " + jsonelement);
					}
				}
			} catch (ClassCastException classcastexception) {
				throw new JSONException("Invalid texture->mipmaps: expected array, was " + jsonobject.get("mipmaps"),
						classcastexception);
			}
		}

		return new TextureMetadataSection(flag, flag1, arraylist);
	}

	/**+
	 * The name of this section type as it appears in JSON.
	 */
	public String getSectionName() {
		return "texture";
	}
}