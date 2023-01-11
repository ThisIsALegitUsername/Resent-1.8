package net.minecraft.util;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

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
public class Rotations {
	protected final float x;
	protected final float y;
	protected final float z;

	public Rotations(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Rotations(NBTTagList nbt) {
		this.x = nbt.getFloatAt(0);
		this.y = nbt.getFloatAt(1);
		this.z = nbt.getFloatAt(2);
	}

	public NBTTagList writeToNBT() {
		NBTTagList nbttaglist = new NBTTagList();
		nbttaglist.appendTag(new NBTTagFloat(this.x));
		nbttaglist.appendTag(new NBTTagFloat(this.y));
		nbttaglist.appendTag(new NBTTagFloat(this.z));
		return nbttaglist;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Rotations)) {
			return false;
		} else {
			Rotations rotations = (Rotations) object;
			return this.x == rotations.x && this.y == rotations.y && this.z == rotations.z;
		}
	}

	/**+
	 * Gets the X axis rotation
	 */
	public float getX() {
		return this.x;
	}

	/**+
	 * Gets the Y axis rotation
	 */
	public float getY() {
		return this.y;
	}

	/**+
	 * Gets the Z axis rotation
	 */
	public float getZ() {
		return this.z;
	}
}