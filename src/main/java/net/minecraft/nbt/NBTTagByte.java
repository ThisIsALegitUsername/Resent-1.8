package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

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
public class NBTTagByte extends NBTBase.NBTPrimitive {
	private byte data;

	NBTTagByte() {
	}

	public NBTTagByte(byte data) {
		this.data = data;
	}

	/**+
	 * Write the actual data contents of the tag, implemented in NBT
	 * extension classes
	 */
	void write(DataOutput parDataOutput) throws IOException {
		parDataOutput.writeByte(this.data);
	}

	void read(DataInput parDataInput, int parInt1, NBTSizeTracker parNBTSizeTracker) throws IOException {
		parNBTSizeTracker.read(72L);
		this.data = parDataInput.readByte();
	}

	/**+
	 * Gets the type byte for the tag.
	 */
	public byte getId() {
		return (byte) 1;
	}

	public String toString() {
		return "" + this.data + "b";
	}

	/**+
	 * Creates a clone of the tag.
	 */
	public NBTBase copy() {
		return new NBTTagByte(this.data);
	}

	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagByte nbttagbyte = (NBTTagByte) object;
			return this.data == nbttagbyte.data;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return super.hashCode() ^ this.data;
	}

	public long getLong() {
		return (long) this.data;
	}

	public int getInt() {
		return this.data;
	}

	public short getShort() {
		return (short) this.data;
	}

	public byte getByte() {
		return this.data;
	}

	public double getDouble() {
		return (double) this.data;
	}

	public float getFloat() {
		return (float) this.data;
	}
}