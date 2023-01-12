package net.minecraft.world.storage;

import net.minecraft.world.WorldSavedData;

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
public class SaveDataMemoryStorage extends MapStorage {
	public SaveDataMemoryStorage() {
		super((ISaveHandler) null);
	}

	/**+
	 * Loads an existing MapDataBase corresponding to the given
	 * String id from disk, instantiating the given Class, or
	 * returns null if none such file exists. args: Class to
	 * instantiate, String dataid
	 */
	public WorldSavedData loadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
		return (WorldSavedData) this.loadedDataMap.get(dataIdentifier);
	}

	/**+
	 * Assigns the given String id to the given MapDataBase,
	 * removing any existing ones of the same id.
	 */
	public void setData(String dataIdentifier, WorldSavedData data) {
		this.loadedDataMap.put(dataIdentifier, data);
	}

	/**+
	 * Saves all dirty loaded MapDataBases to disk.
	 */
	public void saveAllData() {
	}

	/**+
	 * Returns an unique new data id for the given prefix and saves
	 * the idCounts map to the 'idcounts' file.
	 */
	public int getUniqueDataId(String key) {
		return 0;
	}
}