package net.minecraft.client.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
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
public class FallbackResourceManager implements IResourceManager {
	private static final Logger logger = LogManager.getLogger();
	protected final List<IResourcePack> resourcePacks = Lists.newArrayList();
	private final IMetadataSerializer frmMetadataSerializer;

	public FallbackResourceManager(IMetadataSerializer frmMetadataSerializerIn) {
		this.frmMetadataSerializer = frmMetadataSerializerIn;
	}

	public void addResourcePack(IResourcePack resourcePack) {
		this.resourcePacks.add(resourcePack);
	}

	public Set<String> getResourceDomains() {
		return null;
	}

	public IResource getResource(ResourceLocation location) throws IOException {
		IResourcePack iresourcepack = null;
		ResourceLocation resourcelocation = getLocationMcmeta(location);

		for (int i = this.resourcePacks.size() - 1; i >= 0; --i) {
			IResourcePack iresourcepack1 = (IResourcePack) this.resourcePacks.get(i);
			if (iresourcepack == null && iresourcepack1.resourceExists(resourcelocation)) {
				iresourcepack = iresourcepack1;
			}

			if (iresourcepack1.resourceExists(location)) {
				InputStream inputstream = null;
				if (iresourcepack != null) {
					inputstream = this.getInputStream(resourcelocation, iresourcepack);
				}

				return new SimpleResource(iresourcepack1.getPackName(), location,
						this.getInputStream(location, iresourcepack1), inputstream, this.frmMetadataSerializer);
			}
		}

		throw new FileNotFoundException(location.toString());
	}

	protected InputStream getInputStream(ResourceLocation location, IResourcePack resourcePack) throws IOException {
		return resourcePack.getInputStream(location);
	}

	public List<IResource> getAllResources(ResourceLocation location) throws IOException {
		ArrayList arraylist = Lists.newArrayList();
		ResourceLocation resourcelocation = getLocationMcmeta(location);

		for (IResourcePack iresourcepack : this.resourcePacks) {
			if (iresourcepack.resourceExists(location)) {
				InputStream inputstream = iresourcepack.resourceExists(resourcelocation)
						? this.getInputStream(resourcelocation, iresourcepack)
						: null;
				arraylist.add(new SimpleResource(iresourcepack.getPackName(), location,
						this.getInputStream(location, iresourcepack), inputstream, this.frmMetadataSerializer));
			}
		}

		if (arraylist.isEmpty()) {
			throw new FileNotFoundException(location.toString());
		} else {
			return arraylist;
		}
	}

	static ResourceLocation getLocationMcmeta(ResourceLocation location) {
		return new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + ".mcmeta");
	}

}