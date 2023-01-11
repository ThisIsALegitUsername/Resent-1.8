package net.minecraft.util;

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
public class ChatAllowedCharacters {
	/**+
	 * Array of the special characters that are allowed in any text
	 * drawing of Minecraft.
	 */
	public static final char[] allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?',
			'*', '\\', '<', '>', '|', '\"', ':' };

	public static boolean isAllowedCharacter(char character) {
		return character != 167 && character >= 32 && character != 127;
	}

	/**+
	 * Filter string by only keeping those characters for which
	 * isAllowedCharacter() returns true.
	 */
	public static String filterAllowedCharacters(String input) {
		StringBuilder stringbuilder = new StringBuilder();

		for (char c0 : input.toCharArray()) {
			if (isAllowedCharacter(c0)) {
				stringbuilder.append(c0);
			}
		}

		return stringbuilder.toString();
	}
}