package net.minecraft.world.border;

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
public interface IBorderListener {
	void onSizeChanged(WorldBorder var1, double var2);

	void onTransitionStarted(WorldBorder var1, double var2, double var4, long var6);

	void onCenterChanged(WorldBorder var1, double var2, double var4);

	void onWarningTimeChanged(WorldBorder var1, int var2);

	void onWarningDistanceChanged(WorldBorder var1, int var2);

	void onDamageAmountChanged(WorldBorder var1, double var2);

	void onDamageBufferChanged(WorldBorder var1, double var2);
}