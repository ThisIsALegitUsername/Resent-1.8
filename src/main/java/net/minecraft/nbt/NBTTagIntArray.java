package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

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
public class NBTTagIntArray extends NBTBase {
	private int[] intArray;

	NBTTagIntArray() {
	}

	public NBTTagIntArray(int[] parArrayOfInt) {
		this.intArray = parArrayOfInt;
	}

	/**+
	 * Write the actual data contents of the tag, implemented in NBT
	 * extension classes
	 */
	void write(DataOutput parDataOutput) throws IOException {
		parDataOutput.writeInt(this.intArray.length);

		for (int i = 0; i < this.intArray.length; ++i) {
			parDataOutput.writeInt(this.intArray[i]);
		}

	}

	void read(DataInput parDataInput, int parInt1, NBTSizeTracker parNBTSizeTracker) throws IOException {
		parNBTSizeTracker.read(192L);
		int i = parDataInput.readInt();
		parNBTSizeTracker.read((long) (32 * i));
		this.intArray = new int[i];

		for (int j = 0; j < i; ++j) {
			this.intArray[j] = parDataInput.readInt();
		}

	}

	/**+
	 * Gets the type byte for the tag.
	 */
	public byte getId() {
		return (byte) 11;
	}

	public String toString() {
		String s = "[";

		for (int i : this.intArray) {
			s = s + i + ",";
		}

		return s + "]";
	}

	/**+
	 * Creates a clone of the tag.
	 */
	public NBTBase copy() {
		int[] aint = new int[this.intArray.length];
		System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
		return new NBTTagIntArray(aint);
	}

	public boolean equals(Object object) {
		return super.equals(object) ? Arrays.equals(this.intArray, ((NBTTagIntArray) object).intArray) : false;
	}

	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.intArray);
	}

	public int[] getIntArray() {
		return this.intArray;
	}
}