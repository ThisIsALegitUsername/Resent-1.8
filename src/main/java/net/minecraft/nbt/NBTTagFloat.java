package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.util.MathHelper;

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
public class NBTTagFloat extends NBTBase.NBTPrimitive {
	private float data;

	NBTTagFloat() {
	}

	public NBTTagFloat(float data) {
		this.data = data;
	}

	/**+
	 * Write the actual data contents of the tag, implemented in NBT
	 * extension classes
	 */
	void write(DataOutput parDataOutput) throws IOException {
		parDataOutput.writeFloat(this.data);
	}

	void read(DataInput parDataInput, int parInt1, NBTSizeTracker parNBTSizeTracker) throws IOException {
		parNBTSizeTracker.read(96L);
		this.data = parDataInput.readFloat();
	}

	/**+
	 * Gets the type byte for the tag.
	 */
	public byte getId() {
		return (byte) 5;
	}

	public String toString() {
		return "" + this.data + "f";
	}

	/**+
	 * Creates a clone of the tag.
	 */
	public NBTBase copy() {
		return new NBTTagFloat(this.data);
	}

	public boolean equals(Object object) {
		if (super.equals(object)) {
			NBTTagFloat nbttagfloat = (NBTTagFloat) object;
			return this.data == nbttagfloat.data;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return super.hashCode() ^ Float.floatToIntBits(this.data);
	}

	public long getLong() {
		return (long) this.data;
	}

	public int getInt() {
		return MathHelper.floor_float(this.data);
	}

	public short getShort() {
		return (short) (MathHelper.floor_float(this.data) & '\uffff');
	}

	public byte getByte() {
		return (byte) (MathHelper.floor_float(this.data) & 255);
	}

	public double getDouble() {
		return (double) this.data;
	}

	public float getFloat() {
		return this.data;
	}
}