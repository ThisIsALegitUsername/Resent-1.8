package net.minecraft.client.resources.data;

import java.util.Collections;
import java.util.List;

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
public class TextureMetadataSection implements IMetadataSection {
	private final boolean textureBlur;
	private final boolean textureClamp;
	private final List<Integer> listMipmaps;

	public TextureMetadataSection(boolean parFlag, boolean parFlag2, List<Integer> parList) {
		this.textureBlur = parFlag;
		this.textureClamp = parFlag2;
		this.listMipmaps = parList;
	}

	public boolean getTextureBlur() {
		return this.textureBlur;
	}

	public boolean getTextureClamp() {
		return this.textureClamp;
	}

	public List<Integer> getListMipmaps() {
		return Collections.unmodifiableList(this.listMipmaps);
	}
}