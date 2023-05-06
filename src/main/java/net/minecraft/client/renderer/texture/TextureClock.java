package net.minecraft.client.renderer.texture;

import net.lax1dude.eaglercraft.v1_8.internal.IFramebufferGL;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

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
public class TextureClock extends EaglerTextureAtlasSprite {
	private double field_94239_h;
	private double field_94240_i;

	public TextureClock(String iconName) {
		super(iconName);
	}

	public void updateAnimation(IFramebufferGL[] copyColorFramebuffer) {
		if (!this.framesTextureData.isEmpty()) {
			Minecraft minecraft = Minecraft.getMinecraft();
			double d0 = 0.0D;
			if (minecraft.theWorld != null && minecraft.thePlayer != null) {
				d0 = (double) minecraft.theWorld.getCelestialAngle(1.0F);
				if (!minecraft.theWorld.provider.isSurfaceWorld()) {
					d0 = Math.random();
				}
			}

			double d1;
			for (d1 = d0 - this.field_94239_h; d1 < -0.5D; ++d1) {
				;
			}

			while (d1 >= 0.5D) {
				--d1;
			}

			d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
			this.field_94240_i += d1 * 0.1D;
			this.field_94240_i *= 0.8D;
			this.field_94239_h += this.field_94240_i;

			int i;
			for (i = (int) ((this.field_94239_h + 1.0D) * (double) this.framesTextureData.size())
					% this.framesTextureData
							.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {
				;
			}

			if (i != this.frameCounter) {
				this.frameCounter = i;
				animationCache.copyFrameLevelsToTex2D(this.frameCounter, this.originX, this.originY, this.width,
						this.height, copyColorFramebuffer);
			}

		}
	}

}