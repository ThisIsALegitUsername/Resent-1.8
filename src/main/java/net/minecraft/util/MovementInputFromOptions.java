package net.minecraft.util;

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
public class MovementInputFromOptions extends MovementInput {
	private final GameSettings gameSettings;

	public MovementInputFromOptions(GameSettings gameSettingsIn) {
		this.gameSettings = gameSettingsIn;
	}

	public void updatePlayerMoveState() {
		this.moveStrafe = 0.0F;
		this.moveForward = 0.0F;
		if (this.gameSettings.keyBindForward.isKeyDown()) {
			++this.moveForward;
		}

		if (this.gameSettings.keyBindBack.isKeyDown()) {
			--this.moveForward;
		}

		if (this.gameSettings.keyBindLeft.isKeyDown()) {
			++this.moveStrafe;
		}

		if (this.gameSettings.keyBindRight.isKeyDown()) {
			--this.moveStrafe;
		}

		this.jump = this.gameSettings.keyBindJump.isKeyDown();
		this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
		if (this.sneak) {
			this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
			this.moveForward = (float) ((double) this.moveForward * 0.3D);
		}

	}
}