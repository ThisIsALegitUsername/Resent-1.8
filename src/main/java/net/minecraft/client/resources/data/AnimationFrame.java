package net.minecraft.client.resources.data;

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
public class AnimationFrame {
	private final int frameIndex;
	private final int frameTime;

	public AnimationFrame(int parInt1) {
		this(parInt1, -1);
	}

	public AnimationFrame(int parInt1, int parInt2) {
		this.frameIndex = parInt1;
		this.frameTime = parInt2;
	}

	public boolean hasNoTime() {
		return this.frameTime == -1;
	}

	public int getFrameTime() {
		return this.frameTime;
	}

	public int getFrameIndex() {
		return this.frameIndex;
	}
}