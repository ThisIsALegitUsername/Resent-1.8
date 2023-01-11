package net.minecraft.client.audio;

import java.util.Map;

import com.google.common.collect.Maps;

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
public enum SoundCategory {
	MASTER("master", 0), MUSIC("music", 1), RECORDS("record", 2), WEATHER("weather", 3), BLOCKS("block", 4),
	MOBS("hostile", 5), ANIMALS("neutral", 6), PLAYERS("player", 7), AMBIENT("ambient", 8), VOICE("voice", 9);

	private static final Map<String, SoundCategory> NAME_CATEGORY_MAP = Maps.newHashMap();
	private static final Map<Integer, SoundCategory> ID_CATEGORY_MAP = Maps.newHashMap();
	private final String categoryName;
	private final int categoryId;

	private SoundCategory(String name, int id) {
		this.categoryName = name;
		this.categoryId = id;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public static SoundCategory getCategory(String name) {
		return (SoundCategory) NAME_CATEGORY_MAP.get(name);
	}

	static {
		for (SoundCategory soundcategory : values()) {
			if (NAME_CATEGORY_MAP.containsKey(soundcategory.getCategoryName())
					|| ID_CATEGORY_MAP.containsKey(Integer.valueOf(soundcategory.getCategoryId()))) {
				throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + soundcategory);
			}

			NAME_CATEGORY_MAP.put(soundcategory.getCategoryName(), soundcategory);
			ID_CATEGORY_MAP.put(Integer.valueOf(soundcategory.getCategoryId()), soundcategory);
		}

	}
}