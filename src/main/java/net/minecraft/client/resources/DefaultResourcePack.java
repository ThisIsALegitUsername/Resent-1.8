package net.minecraft.client.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.renderer.texture.TextureUtil;
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
public class DefaultResourcePack implements IResourcePack {
	public static final Set<String> defaultResourceDomains = ImmutableSet.of("minecraft", "eagler");

	public InputStream getInputStream(ResourceLocation parResourceLocation) throws IOException {
		InputStream inputstream = this.getResourceStream(parResourceLocation);
		if (inputstream != null) {
			return inputstream;
		} else {
			InputStream inputstream1 = this.getInputStreamAssets(parResourceLocation);
			if (inputstream1 != null) {
				return inputstream1;
			} else {
				throw new FileNotFoundException(parResourceLocation.getResourcePath());
			}
		}
	}

	public InputStream getInputStreamAssets(ResourceLocation location) throws FileNotFoundException {
		return null;
	}

	private InputStream getResourceStream(ResourceLocation location) {
		return EagRuntime
				.getResourceStream("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath());
	}

	public boolean resourceExists(ResourceLocation resourcelocation) {
		return this.getResourceStream(resourcelocation) != null;
	}

	public Set<String> getResourceDomains() {
		return defaultResourceDomains;
	}

	public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer parIMetadataSerializer, String parString1)
			throws IOException {
		try {
			return AbstractResourcePack.readMetadata(parIMetadataSerializer,
					EagRuntime.getResourceStream("pack.mcmeta"), parString1);
		} catch (RuntimeException var4) {
			return (T) null;
		}
	}

	public ImageData getPackImage() throws IOException {
		return TextureUtil.readBufferedImage(EagRuntime.getResourceStream("pack.png"));
	}

	public String getPackName() {
		return "Default";
	}
}