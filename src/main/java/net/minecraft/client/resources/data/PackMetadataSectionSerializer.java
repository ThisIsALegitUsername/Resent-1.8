package net.minecraft.client.resources.data;

import org.json.JSONException;
import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.json.JSONTypeSerializer;
import net.lax1dude.eaglercraft.v1_8.json.JSONTypeProvider;
import net.minecraft.util.IChatComponent;

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
public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer<PackMetadataSection>
		implements JSONTypeSerializer<PackMetadataSection, JSONObject> {
	public PackMetadataSection deserialize(JSONObject jsonobject) throws JSONException {
		IChatComponent ichatcomponent = JSONTypeProvider.deserialize(jsonobject.get("description"),
				IChatComponent.class);
		if (ichatcomponent == null) {
			throw new JSONException("Invalid/missing description!");
		} else {
			int i = jsonobject.getInt("pack_format");
			return new PackMetadataSection(ichatcomponent, i);
		}
	}

	public JSONObject serialize(PackMetadataSection packmetadatasection) {
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("pack_format", packmetadatasection.getPackFormat());
		jsonobject.put("description",
				(JSONObject) JSONTypeProvider.serialize(packmetadatasection.getPackDescription()));
		return jsonobject;
	}

	/**+
	 * The name of this section type as it appears in JSON.
	 */
	public String getSectionName() {
		return "pack";
	}
}