package net.minecraft.util;

import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.HString;
import net.lax1dude.eaglercraft.v1_8.IOUtils;

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
public class StringTranslate {
	/**+
	 * Pattern that matches numeric variable placeholders in a
	 * resource string, such as "%d", "%3$d", "%.2f"
	 */
	private static final Pattern numericVariablePattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
	/**+
	 * A Splitter that splits a string on the first "=". For
	 * example, "a=b=c" would split into ["a", "b=c"].
	 */
	private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);
	/**+
	 * Is the private singleton instance of StringTranslate.
	 */
	private static StringTranslate instance = new StringTranslate();
	private final Map<String, String> languageList = Maps.newHashMap();
	private long lastUpdateTimeInMilliseconds;

	public StringTranslate() {
		this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
	}

	public static void doCLINIT() {
		InputStream inputstream = EagRuntime.getResourceStream("/assets/minecraft/lang/en_US.lang");
		for (String s : IOUtils.readLines(inputstream, Charsets.UTF_8)) {
			if (!s.isEmpty() && s.charAt(0) != 35) {
				String[] astring = (String[]) Iterables.toArray(equalSignSplitter.split(s), String.class);
				if (astring != null && astring.length == 2) {
					String s1 = astring[0];
					String s2 = numericVariablePattern.matcher(astring[1]).replaceAll("%s"); // TODO: originally "%$1s"
																								// but must be "%s" to
																								// work with TeaVM
																								// (why?)
					instance.languageList.put(s1, s2);
				}
			}
		}

		instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
	}

	/**+
	 * Return the StringTranslate singleton instance
	 */
	static StringTranslate getInstance() {
		return instance;
	}

	/**+
	 * Replaces all the current instance's translations with the
	 * ones that are passed in.
	 */
	public static synchronized void replaceWith(Map<String, String> parMap) {
		instance.languageList.clear();
		instance.languageList.putAll(parMap);
		instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
	}

	/**+
	 * Translate a key to current language.
	 */
	public synchronized String translateKey(String key) {
		return this.tryTranslateKey(key);
	}

	/**+
	 * Translate a key to current language applying String.format()
	 */
	public synchronized String translateKeyFormat(String key, Object... format) {
		String s = this.tryTranslateKey(key);

		try {
			return HString.format(s, format);
		} catch (IllegalFormatException var5) {
			return "Format error: " + s;
		}
	}

	/**+
	 * Tries to look up a translation for the given key; spits back
	 * the key if no result was found.
	 */
	private String tryTranslateKey(String key) {
		String s = (String) this.languageList.get(key);
		return s == null ? key : s;
	}

	/**+
	 * Returns true if the passed key is in the translation table.
	 */
	public synchronized boolean isKeyTranslated(String key) {
		return this.languageList.containsKey(key);
	}

	/**+
	 * Gets the time, in milliseconds since epoch, that this
	 * instance was last updated
	 */
	public long getLastUpdateTimeInMilliseconds() {
		return this.lastUpdateTimeInMilliseconds;
	}
}