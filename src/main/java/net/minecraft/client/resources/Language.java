package net.minecraft.client.resources;

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
public class Language implements Comparable<Language> {
	private final String languageCode;
	private final String region;
	private final String name;
	private final boolean bidirectional;

	public Language(String languageCodeIn, String regionIn, String nameIn, boolean bidirectionalIn) {
		this.languageCode = languageCodeIn;
		this.region = regionIn;
		this.name = nameIn;
		this.bidirectional = bidirectionalIn;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public boolean isBidirectional() {
		return this.bidirectional;
	}

	public String toString() {
		return HString.format("%s (%s)", new Object[] { this.name, this.region });
	}

	public boolean equals(Object object) {
		return this == object ? true
				: (!(object instanceof Language) ? false : this.languageCode.equals(((Language) object).languageCode));
	}

	public int hashCode() {
		return this.languageCode.hashCode();
	}

	public int compareTo(Language language) {
		return this.languageCode.compareTo(language.languageCode);
	}
}