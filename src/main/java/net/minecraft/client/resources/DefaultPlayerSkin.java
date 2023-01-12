package net.minecraft.client.resources;

import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

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
public class DefaultPlayerSkin {
	/**+
	 * The default skin for the Steve model.
	 */
	private static final ResourceLocation TEXTURE_STEVE = new ResourceLocation("textures/entity/steve.png");
	/**+
	 * The default skin for the Alex model.
	 */
	private static final ResourceLocation TEXTURE_ALEX = new ResourceLocation("textures/entity/alex.png");

	/**+
	 * Returns the default skind for versions prior to 1.8, which is
	 * always the Steve texture.
	 */
	public static ResourceLocation getDefaultSkinLegacy() {
		return TEXTURE_STEVE;
	}

	/**+
	 * Retrieves the default skin for this player. Depending on the
	 * model used this will be Alex or Steve.
	 */
	public static ResourceLocation getDefaultSkin(EaglercraftUUID playerUUID) {
		/**+
		 * Checks if a players skin model is slim or the default. The
		 * Alex model is slime while the Steve model is default.
		 */
		return isSlimSkin(playerUUID) ? TEXTURE_ALEX : TEXTURE_STEVE;
	}

	/**+
	 * Retrieves the type of skin that a player is using. The Alex
	 * model is slim while the Steve model is default.
	 */
	public static String getSkinType(EaglercraftUUID playerUUID) {
		/**+
		 * Checks if a players skin model is slim or the default. The
		 * Alex model is slime while the Steve model is default.
		 */
		return isSlimSkin(playerUUID) ? "slim" : "default";
	}

	/**+
	 * Checks if a players skin model is slim or the default. The
	 * Alex model is slime while the Steve model is default.
	 */
	private static boolean isSlimSkin(EaglercraftUUID playerUUID) {
		return (playerUUID.hashCode() & 1) == 1;
	}
}