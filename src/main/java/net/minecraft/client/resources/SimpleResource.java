package net.minecraft.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Maps;

import net.lax1dude.eaglercraft.v1_8.IOUtils;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

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
public class SimpleResource implements IResource {
	private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
	private final String resourcePackName;
	private final ResourceLocation srResourceLocation;
	private final InputStream resourceInputStream;
	private final InputStream mcmetaInputStream;
	private final IMetadataSerializer srMetadataSerializer;
	private boolean mcmetaJsonChecked;
	private JSONObject mcmetaJson;

	public SimpleResource(String resourcePackNameIn, ResourceLocation srResourceLocationIn,
			InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn,
			IMetadataSerializer srMetadataSerializerIn) {
		this.resourcePackName = resourcePackNameIn;
		this.srResourceLocation = srResourceLocationIn;
		this.resourceInputStream = resourceInputStreamIn;
		this.mcmetaInputStream = mcmetaInputStreamIn;
		this.srMetadataSerializer = srMetadataSerializerIn;
	}

	public ResourceLocation getResourceLocation() {
		return this.srResourceLocation;
	}

	public InputStream getInputStream() {
		return this.resourceInputStream;
	}

	public boolean hasMetadata() {
		return this.mcmetaInputStream != null;
	}

	public <T extends IMetadataSection> T getMetadata(String s) {
		if (!this.hasMetadata()) {
			return (T) null;
		} else {
			if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
				this.mcmetaJsonChecked = true;

				try {
					this.mcmetaJson = new JSONObject(
							IOUtils.inputStreamToString(this.mcmetaInputStream, StandardCharsets.UTF_8));
				} catch (IOException e) {
					throw new JSONException(e);
				} finally {
					IOUtils.closeQuietly(this.mcmetaInputStream);
				}
			}

			IMetadataSection imetadatasection = (IMetadataSection) this.mapMetadataSections.get(s);
			if (imetadatasection == null) {
				imetadatasection = this.srMetadataSerializer.parseMetadataSection(s, this.mcmetaJson);
			}

			return (T) imetadatasection;
		}
	}

	public String getResourcePackName() {
		return this.resourcePackName;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof SimpleResource)) {
			return false;
		} else {
			SimpleResource simpleresource = (SimpleResource) object;
			if (this.srResourceLocation != null) {
				if (!this.srResourceLocation.equals(simpleresource.srResourceLocation)) {
					return false;
				}
			} else if (simpleresource.srResourceLocation != null) {
				return false;
			}

			if (this.resourcePackName != null) {
				if (!this.resourcePackName.equals(simpleresource.resourcePackName)) {
					return false;
				}
			} else if (simpleresource.resourcePackName != null) {
				return false;
			}

			return true;
		}
	}

	public int hashCode() {
		int i = this.resourcePackName != null ? this.resourcePackName.hashCode() : 0;
		i = 31 * i + (this.srResourceLocation != null ? this.srResourceLocation.hashCode() : 0);
		return i;
	}
}