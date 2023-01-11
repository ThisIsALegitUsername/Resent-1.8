package net.minecraft.client.main;

import net.minecraft.util.Session;

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
public class GameConfiguration {
	public final GameConfiguration.UserInformation userInfo;
	public final GameConfiguration.DisplayInformation displayInfo;
	public final GameConfiguration.GameInformation gameInfo;

	public GameConfiguration(GameConfiguration.UserInformation userInfoIn,
			GameConfiguration.DisplayInformation displayInfoIn, GameConfiguration.GameInformation gameInfoIn) {
		this.userInfo = userInfoIn;
		this.displayInfo = displayInfoIn;
		this.gameInfo = gameInfoIn;
	}

	public static class DisplayInformation {
		public final int width;
		public final int height;
		public final boolean fullscreen;
		public final boolean checkGlErrors;

		public DisplayInformation(int widthIn, int heightIn, boolean fullscreenIn, boolean checkGlErrorsIn) {
			this.width = widthIn;
			this.height = heightIn;
			this.fullscreen = fullscreenIn;
			this.checkGlErrors = checkGlErrorsIn;
		}
	}

	public static class GameInformation {
		public final boolean isDemo;
		public final String version;

		public GameInformation(boolean isDemoIn, String versionIn) {
			this.isDemo = isDemoIn;
			this.version = versionIn;
		}
	}

	public static class UserInformation {
		public final Session session;

		public UserInformation(Session parSession) {
			this.session = parSession;
		}
	}
}