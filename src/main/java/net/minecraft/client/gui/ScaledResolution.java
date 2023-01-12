package net.minecraft.client.gui;

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
public class ScaledResolution {
	private final double scaledWidthD;
	private final double scaledHeightD;
	private int scaledWidth;
	private int scaledHeight;
	private int scaleFactor;

	public ScaledResolution(Minecraft parMinecraft) {
		this.scaledWidth = parMinecraft.displayWidth;
		this.scaledHeight = parMinecraft.displayHeight;
		this.scaleFactor = 1;
		boolean flag = parMinecraft.isUnicode();
		int i = parMinecraft.gameSettings.guiScale;
		if (i == 0) {
			i = 1000;
		}

		while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320
				&& this.scaledHeight / (this.scaleFactor + 1) >= 240) {
			++this.scaleFactor;
		}

		if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
			--this.scaleFactor;
		}

		this.scaledWidthD = (double) this.scaledWidth / (double) this.scaleFactor;
		this.scaledHeightD = (double) this.scaledHeight / (double) this.scaleFactor;
		this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
		this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
	}

	public int getScaledWidth() {
		return this.scaledWidth;
	}

	public int getScaledHeight() {
		return this.scaledHeight;
	}

	public double getScaledWidth_double() {
		return this.scaledWidthD;
	}

	public double getScaledHeight_double() {
		return this.scaledHeightD;
	}

	public int getScaleFactor() {
		return this.scaleFactor;
	}
}