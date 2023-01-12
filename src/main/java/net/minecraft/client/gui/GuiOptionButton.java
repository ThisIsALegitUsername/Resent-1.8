package net.minecraft.client.gui;

import net.minecraft.client.settings.GameSettings;

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
public class GuiOptionButton extends GuiButton {
	private final GameSettings.Options enumOptions;

	public GuiOptionButton(int parInt1, int parInt2, int parInt3, String parString1) {
		this(parInt1, parInt2, parInt3, (GameSettings.Options) null, parString1);
	}

	public GuiOptionButton(int parInt1, int parInt2, int parInt3, int parInt4, int parInt5, String parString1) {
		super(parInt1, parInt2, parInt3, parInt4, parInt5, parString1);
		this.enumOptions = null;
	}

	public GuiOptionButton(int parInt1, int parInt2, int parInt3, GameSettings.Options parOptions, String parString1) {
		super(parInt1, parInt2, parInt3, 150, 20, parString1);
		this.enumOptions = parOptions;
	}

	public GameSettings.Options returnEnumOptions() {
		return this.enumOptions;
	}
}