package net.minecraft.client.renderer.texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.resources.IResourceManager;
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
public class LayeredTexture extends AbstractTexture {
	private static final Logger logger = LogManager.getLogger();
	public final List<String> layeredTextureNames;

	public LayeredTexture(String... textureNames) {
		this.layeredTextureNames = Lists.newArrayList(textureNames);
	}

	public void loadTexture(IResourceManager parIResourceManager) throws IOException {
		this.deleteGlTexture();
		ImageData bufferedimage = null;

		try {
			for (String s : this.layeredTextureNames) {
				if (s != null) {
					InputStream inputstream = parIResourceManager.getResource(new ResourceLocation(s)).getInputStream();
					ImageData bufferedimage1 = TextureUtil.readBufferedImage(inputstream);
					if (bufferedimage == null) {
						bufferedimage = new ImageData(bufferedimage1.width, bufferedimage1.height, true);
					}

					bufferedimage.drawLayer(bufferedimage1, 0, 0, bufferedimage1.width, bufferedimage1.height, 0, 0,
							bufferedimage1.width, bufferedimage1.height);
				}
			}
		} catch (IOException ioexception) {
			logger.error("Couldn\'t load layered image", ioexception);
			return;
		}

		TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedimage);
	}
}