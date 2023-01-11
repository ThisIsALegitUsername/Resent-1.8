package net.minecraft.nbt;

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
public class NBTSizeTracker {
	public static final NBTSizeTracker INFINITE = new NBTSizeTracker(0L) {
		/**+
		 * Tracks the reading of the given amount of bits(!)
		 */
		public void read(long bits) {
		}
	};
	private final long max;
	private long read;

	public NBTSizeTracker(long max) {
		this.max = max;
	}

	/**+
	 * Tracks the reading of the given amount of bits(!)
	 */
	public void read(long i) {
		this.read += i / 8L;
		if (this.read > this.max) {
			throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.read
					+ "bytes where max allowed: " + this.max);
		}
	}
}