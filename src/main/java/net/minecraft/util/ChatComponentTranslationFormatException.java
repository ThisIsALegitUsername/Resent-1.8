package net.minecraft.util;

import net.lax1dude.eaglercraft.v1_8.HString;

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
public class ChatComponentTranslationFormatException extends IllegalArgumentException {
	public ChatComponentTranslationFormatException(ChatComponentTranslation component, String message) {
		super(HString.format("Error parsing: %s: %s", new Object[] { component, message }));
	}

	public ChatComponentTranslationFormatException(ChatComponentTranslation component, int index) {
		super(HString.format("Invalid index %d requested for %s", new Object[] { Integer.valueOf(index), component }));
	}

	public ChatComponentTranslationFormatException(ChatComponentTranslation component, Throwable cause) {
		super(HString.format("Error while parsing: %s", new Object[] { component }), cause);
	}
}