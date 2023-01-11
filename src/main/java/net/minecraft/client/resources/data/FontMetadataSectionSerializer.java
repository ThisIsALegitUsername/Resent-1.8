package net.minecraft.client.resources.data;

import org.apache.commons.lang3.Validate;
import org.json.JSONException;
import org.json.JSONObject;

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
public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer<FontMetadataSection> {
	public FontMetadataSection deserialize(JSONObject jsonobject) throws JSONException {
		float[] afloat = new float[256];
		float[] afloat1 = new float[256];
		float[] afloat2 = new float[256];
		float f = 1.0F;
		float f1 = 0.0F;
		float f2 = 0.0F;
		if (jsonobject.has("characters")) {
			if (!(jsonobject.get("characters") instanceof JSONObject)) {
				throw new JSONException(
						"Invalid font->characters: expected object, was " + jsonobject.get("characters"));
			}

			JSONObject jsonobject1 = jsonobject.getJSONObject("characters");
			if (jsonobject1.has("default")) {
				if (!(jsonobject1.get("default") instanceof JSONObject)) {
					throw new JSONException(
							"Invalid font->characters->default: expected object, was " + jsonobject1.get("default"));
				}

				JSONObject jsonobject2 = jsonobject1.getJSONObject("default");
				f = jsonobject2.optFloat("width", f);
				Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double) f, "Invalid default width");
				f1 = jsonobject2.optFloat("spacing", f1);
				Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double) f1, "Invalid default spacing");
				f2 = jsonobject2.optFloat("left", f1);
				Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double) f2, "Invalid default left");
			}

			for (int i = 0; i < 256; ++i) {
				JSONObject jsonobject3 = jsonobject1.optJSONObject(Integer.toString(i));
				float f3 = f;
				float f4 = f1;
				float f5 = f2;
				if (jsonobject3 != null) {
					f3 = jsonobject3.optFloat("width", f);
					Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double) f3, "Invalid width");
					f4 = jsonobject3.optFloat("spacing", f1);
					Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double) f4, "Invalid spacing");
					f5 = jsonobject3.optFloat("left", f2);
					Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double) f5, "Invalid left");
				}

				afloat[i] = f3;
				afloat1[i] = f4;
				afloat2[i] = f5;
			}
		}

		return new FontMetadataSection(afloat, afloat2, afloat1);
	}

	/**+
	 * The name of this section type as it appears in JSON.
	 */
	public String getSectionName() {
		return "font";
	}
}