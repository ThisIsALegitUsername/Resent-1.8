package net.minecraft.client.renderer;

import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;

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
public class ImageBufferDownload implements IImageBuffer {
	private int[] imageData;
	private int imageWidth;
	private int imageHeight;

	public ImageData parseUserSkin(ImageData bufferedimage) {
		if (bufferedimage == null) {
			return null;
		} else {
			this.imageWidth = 64;
			this.imageHeight = 64;
			ImageData bufferedimage1 = new ImageData(this.imageWidth, this.imageHeight, true);
			bufferedimage1.copyPixelsFrom(bufferedimage, 0, 0, bufferedimage.width, bufferedimage.height, 0, 0,
					bufferedimage.width, bufferedimage.height);
			if (bufferedimage.height == 32) {
				bufferedimage1.drawLayer(bufferedimage, 24, 48, 20, 52, 4, 16, 8, 20);
				bufferedimage1.drawLayer(bufferedimage, 28, 48, 24, 52, 8, 16, 12, 20);
				bufferedimage1.drawLayer(bufferedimage, 20, 52, 16, 64, 8, 20, 12, 32);
				bufferedimage1.drawLayer(bufferedimage, 24, 52, 20, 64, 4, 20, 8, 32);
				bufferedimage1.drawLayer(bufferedimage, 28, 52, 24, 64, 0, 20, 4, 32);
				bufferedimage1.drawLayer(bufferedimage, 32, 52, 28, 64, 12, 20, 16, 32);
				bufferedimage1.drawLayer(bufferedimage, 40, 48, 36, 52, 44, 16, 48, 20);
				bufferedimage1.drawLayer(bufferedimage, 44, 48, 40, 52, 48, 16, 52, 20);
				bufferedimage1.drawLayer(bufferedimage, 36, 52, 32, 64, 48, 20, 52, 32);
				bufferedimage1.drawLayer(bufferedimage, 40, 52, 36, 64, 44, 20, 48, 32);
				bufferedimage1.drawLayer(bufferedimage, 44, 52, 40, 64, 40, 20, 44, 32);
				bufferedimage1.drawLayer(bufferedimage, 48, 52, 44, 64, 52, 20, 56, 32);
			}

			this.imageData = bufferedimage1.pixels;
			this.setAreaOpaque(0, 0, 32, 16);
			this.setAreaTransparent(32, 0, 64, 32);
			this.setAreaOpaque(0, 16, 64, 32);
			this.setAreaTransparent(0, 32, 16, 48);
			this.setAreaTransparent(16, 32, 40, 48);
			this.setAreaTransparent(40, 32, 56, 48);
			this.setAreaTransparent(0, 48, 16, 64);
			this.setAreaOpaque(16, 48, 48, 64);
			this.setAreaTransparent(48, 48, 64, 64);
			return bufferedimage1;
		}
	}

	public void skinAvailable() {
	}

	/**+
	 * Makes the given area of the image transparent if it was
	 * previously completely opaque (used to remove the outer layer
	 * of a skin around the head if it was saved all opaque; this
	 * would be redundant so it's assumed that the skin maker is
	 * just using an image editor without an alpha channel)
	 */
	private void setAreaTransparent(int parInt1, int parInt2, int parInt3, int parInt4) {
		if (!this.hasTransparency(parInt1, parInt2, parInt3, parInt4)) {
			for (int i = parInt1; i < parInt3; ++i) {
				for (int j = parInt2; j < parInt4; ++j) {
					this.imageData[i + j * this.imageWidth] &= 16777215;
				}
			}

		}
	}

	/**+
	 * Makes the given area of the image opaque
	 */
	private void setAreaOpaque(int parInt1, int parInt2, int parInt3, int parInt4) {
		for (int i = parInt1; i < parInt3; ++i) {
			for (int j = parInt2; j < parInt4; ++j) {
				this.imageData[i + j * this.imageWidth] |= -16777216;
			}
		}

	}

	/**+
	 * Returns true if the given area of the image contains
	 * transparent pixels
	 */
	private boolean hasTransparency(int parInt1, int parInt2, int parInt3, int parInt4) {
		for (int i = parInt1; i < parInt3; ++i) {
			for (int j = parInt2; j < parInt4; ++j) {
				int k = this.imageData[i + j * this.imageWidth];
				if ((k >> 24 & 255) < 128) {
					return true;
				}
			}
		}

		return false;
	}
}