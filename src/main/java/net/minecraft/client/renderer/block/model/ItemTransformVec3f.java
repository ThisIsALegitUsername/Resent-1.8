package net.minecraft.client.renderer.block.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.json.JSONTypeDeserializer;
import net.lax1dude.eaglercraft.v1_8.vector.Vector3f;
import net.minecraft.util.MathHelper;

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
public class ItemTransformVec3f {
	public static final ItemTransformVec3f DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(),
			new Vector3f(1.0F, 1.0F, 1.0F));
	public final Vector3f rotation;
	public final Vector3f translation;
	public final Vector3f scale;

	public ItemTransformVec3f(Vector3f rotation, Vector3f translation, Vector3f scale) {
		this.rotation = new Vector3f(rotation);
		this.translation = new Vector3f(translation);
		this.scale = new Vector3f(scale);
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (this.getClass() != object.getClass()) {
			return false;
		} else {
			ItemTransformVec3f itemtransformvec3f = (ItemTransformVec3f) object;
			return !this.rotation.equals(itemtransformvec3f.rotation) ? false
					: (!this.scale.equals(itemtransformvec3f.scale) ? false
							: this.translation.equals(itemtransformvec3f.translation));
		}
	}

	public int hashCode() {
		int i = this.rotation.hashCode();
		i = 31 * i + this.translation.hashCode();
		i = 31 * i + this.scale.hashCode();
		return i;
	}

	public static class Deserializer implements JSONTypeDeserializer<JSONObject, ItemTransformVec3f> {
		private static final Vector3f ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
		private static final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
		private static final Vector3f SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);

		public ItemTransformVec3f deserialize(JSONObject jsonobject) throws JSONException {
			Vector3f vector3f = this.parseVector3f(jsonobject, "rotation", ROTATION_DEFAULT);
			Vector3f vector3f1 = this.parseVector3f(jsonobject, "translation", TRANSLATION_DEFAULT);
			vector3f1.scale(0.0625F);
			vector3f1.x = MathHelper.clamp_float(vector3f1.x, -1.5F, 1.5F);
			vector3f1.y = MathHelper.clamp_float(vector3f1.y, -1.5F, 1.5F);
			vector3f1.z = MathHelper.clamp_float(vector3f1.z, -1.5F, 1.5F);
			Vector3f vector3f2 = this.parseVector3f(jsonobject, "scale", SCALE_DEFAULT);
			vector3f2.x = MathHelper.clamp_float(vector3f2.x, -4.0F, 4.0F);
			vector3f2.y = MathHelper.clamp_float(vector3f2.y, -4.0F, 4.0F);
			vector3f2.z = MathHelper.clamp_float(vector3f2.z, -4.0F, 4.0F);
			return new ItemTransformVec3f(vector3f, vector3f1, vector3f2);
		}

		private Vector3f parseVector3f(JSONObject jsonObject, String key, Vector3f defaultValue) {
			if (!jsonObject.has(key)) {
				return defaultValue;
			} else {
				JSONArray jsonarray = jsonObject.getJSONArray(key);
				if (jsonarray.length() != 3) {
					throw new JSONException("Expected 3 " + key + " values, found: " + jsonarray.length());
				} else {
					float[] afloat = new float[3];

					for (int i = 0; i < afloat.length; ++i) {
						afloat[i] = jsonarray.getFloat(i);
					}

					return new Vector3f(afloat[0], afloat[1], afloat[2]);
				}
			}
		}
	}
}