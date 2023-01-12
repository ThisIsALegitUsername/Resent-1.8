package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;

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
public interface ISaveHandler {
	/**+
	 * Loads and returns the world info
	 */
	WorldInfo loadWorldInfo();

	/**+
	 * Checks the session lock to prevent save collisions
	 */
	void checkSessionLock() throws MinecraftException;

	/**+
	 * Saves the given World Info with the given NBTTagCompound as
	 * the Player.
	 */
	void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2);

	/**+
	 * used to update level.dat from old format to MCRegion format
	 */
	void saveWorldInfo(WorldInfo var1);

	IPlayerFileData getPlayerNBTManager();

	/**+
	 * Called to flush all changes to disk, waiting for them to
	 * complete.
	 */
	void flush();

	/**+
	 * Returns the name of the directory where world information is
	 * saved.
	 */
	String getWorldDirectoryName();
}