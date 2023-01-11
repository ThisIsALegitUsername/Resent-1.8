package net.minecraft.client.renderer.block.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.json.JSONTypeDeserializer;

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
public class BlockFaceUV {
	public float[] uvs;
	public final int rotation;

	public BlockFaceUV(float[] uvsIn, int rotationIn) {
		this.uvs = uvsIn;
		this.rotation = rotationIn;
	}

	public float func_178348_a(int parInt1) {
		if (this.uvs == null) {
			throw new NullPointerException("uvs");
		} else {
			int i = this.func_178347_d(parInt1);
			return i != 0 && i != 1 ? this.uvs[2] : this.uvs[0];
		}
	}

	public float func_178346_b(int parInt1) {
		if (this.uvs == null) {
			throw new NullPointerException("uvs");
		} else {
			int i = this.func_178347_d(parInt1);
			return i != 0 && i != 3 ? this.uvs[3] : this.uvs[1];
		}
	}

	private int func_178347_d(int parInt1) {
		return (parInt1 + this.rotation / 90) % 4;
	}

	public int func_178345_c(int parInt1) {
		return (parInt1 + (4 - this.rotation / 90)) % 4;
	}

	public void setUvs(float[] uvsIn) {
		if (this.uvs == null) {
			this.uvs = uvsIn;
		}

	}

	public static class Deserializer implements JSONTypeDeserializer<JSONObject, BlockFaceUV> {
		public BlockFaceUV deserialize(JSONObject jsonobject) throws JSONException {
			float[] afloat = this.parseUV(jsonobject);
			int i = this.parseRotation(jsonobject);
			return new BlockFaceUV(afloat, i);
		}

		protected int parseRotation(JSONObject parJsonObject) {
			int i = parJsonObject.optInt("rotation", 0);
			if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
				return i;
			} else {
				throw new JSONException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
			}
		}

		private float[] parseUV(JSONObject parJsonObject) {
			if (!parJsonObject.has("uv")) {
				return null;
			} else {
				JSONArray jsonarray = parJsonObject.getJSONArray("uv");
				if (jsonarray.length() != 4) {
					throw new JSONException("Expected 4 uv values, found: " + jsonarray.length());
				} else {
					float[] afloat = new float[4];

					for (int i = 0; i < afloat.length; ++i) {
						afloat[i] = jsonarray.getFloat(i);
					}

					return afloat;
				}
			}
		}
	}
}