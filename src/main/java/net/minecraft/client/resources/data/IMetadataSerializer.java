package net.minecraft.client.resources.data;

import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.json.JSONTypeProvider;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

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
public class IMetadataSerializer {
	private final IRegistry<String, IMetadataSerializer.Registration<? extends IMetadataSection>> metadataSectionSerializerRegistry = new RegistrySimple();

	public <T extends IMetadataSection> void registerMetadataSectionType(
			IMetadataSectionSerializer<T> parIMetadataSectionSerializer, Class<T> parClass1) {
		this.metadataSectionSerializerRegistry.putObject(parIMetadataSectionSerializer.getSectionName(),
				new IMetadataSerializer.Registration(parIMetadataSectionSerializer, parClass1));
	}

	public <T extends IMetadataSection> T parseMetadataSection(String parString1, JSONObject parJsonObject) {
		if (parString1 == null) {
			throw new IllegalArgumentException("Metadata section name cannot be null");
		} else if (!parJsonObject.has(parString1)) {
			return (T) null;
		} else if (parJsonObject.optJSONObject(parString1) == null) {
			throw new IllegalArgumentException("Invalid metadata for \'" + parString1 + "\' - expected object, found "
					+ parJsonObject.get(parString1));
		} else {
			IMetadataSerializer.Registration imetadataserializer$registration = (IMetadataSerializer.Registration) this.metadataSectionSerializerRegistry
					.getObject(parString1);
			if (imetadataserializer$registration == null) {
				throw new IllegalArgumentException("Don\'t know how to handle metadata section \'" + parString1 + "\'");
			} else {
				return (T) ((IMetadataSection) JSONTypeProvider.deserialize(parJsonObject.getJSONObject(parString1),
						imetadataserializer$registration.field_110500_b));
			}
		}
	}

	class Registration<T extends IMetadataSection> {
		final IMetadataSectionSerializer<T> field_110502_a;
		final Class<T> field_110500_b;

		private Registration(IMetadataSectionSerializer<T> parIMetadataSectionSerializer, Class<T> parClass1) {
			this.field_110502_a = parIMetadataSectionSerializer;
			this.field_110500_b = parClass1;
		}
	}
}