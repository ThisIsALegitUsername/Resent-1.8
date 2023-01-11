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
public class StatCollector {
	private static StringTranslate localizedName = StringTranslate.getInstance();
	/**+
	 * A StringTranslate instance using the hardcoded default locale
	 * (en_US). Used as a fallback in case the shared
	 * StringTranslate singleton instance fails to translate a key.
	 */
	private static StringTranslate fallbackTranslator = new StringTranslate();

	/**+
	 * Translates a Stat name
	 */
	public static String translateToLocal(String key) {
		return localizedName.translateKey(key);
	}

	/**+
	 * Translates a Stat name with format args
	 */
	public static String translateToLocalFormatted(String key, Object... format) {
		return localizedName.translateKeyFormat(key, format);
	}

	/**+
	 * Translates a Stat name using the fallback (hardcoded en_US)
	 * locale. Looks like it's only intended to be used if
	 * translateToLocal fails.
	 */
	public static String translateToFallback(String key) {
		return fallbackTranslator.translateKey(key);
	}

	/**+
	 * Determines whether or not translateToLocal will find a
	 * translation for the given key.
	 */
	public static boolean canTranslate(String key) {
		return localizedName.isKeyTranslated(key);
	}

	/**+
	 * Gets the time, in milliseconds since epoch, that the
	 * translation mapping was last updated
	 */
	public static long getLastTranslationUpdateTimeInMilliseconds() {
		return localizedName.getLastUpdateTimeInMilliseconds();
	}
}