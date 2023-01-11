package net.minecraft.client.renderer.texture;

import java.io.IOException;

import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.resources.IResourceManager;

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
public class DynamicTexture extends AbstractTexture {
	private final int[] dynamicTextureData;
	private final int width;
	private final int height;

	public DynamicTexture(ImageData bufferedImage) {
		this(bufferedImage.width, bufferedImage.height);
		System.arraycopy(bufferedImage.pixels, 0, dynamicTextureData, 0, bufferedImage.pixels.length);
		this.updateDynamicTexture();
	}

	public DynamicTexture(int textureWidth, int textureHeight) {
		this.width = textureWidth;
		this.height = textureHeight;
		this.dynamicTextureData = new int[textureWidth * textureHeight];
		TextureUtil.allocateTexture(this.getGlTextureId(), textureWidth, textureHeight);
	}

	public void loadTexture(IResourceManager resourceManager) throws IOException {
	}

	public void updateDynamicTexture() {
		TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
	}

	public int[] getTextureData() {
		return this.dynamicTextureData;
	}
}