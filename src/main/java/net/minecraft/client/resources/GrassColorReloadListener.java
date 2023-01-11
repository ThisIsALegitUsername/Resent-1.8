package net.minecraft.client.resources;

import java.io.IOException;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;

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
public class GrassColorReloadListener implements IResourceManagerReloadListener {
	private static final ResourceLocation LOC_GRASS_PNG = new ResourceLocation("textures/colormap/grass.png");

	public void onResourceManagerReload(IResourceManager iresourcemanager) {
		try {
			ColorizerGrass.setGrassBiomeColorizer(
					TextureUtil.convertComponentOrder(TextureUtil.readImageData(iresourcemanager, LOC_GRASS_PNG)));
		} catch (IOException var3) {
			;
		}

	}
}