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
public class NBTTagByteArray extends NBTBase {
	private byte[] data;

	NBTTagByteArray() {
	}

	public NBTTagByteArray(byte[] data) {
		this.data = data;
	}

	/**+
	 * Write the actual data contents of the tag, implemented in NBT
	 * extension classes
	 */
	void write(DataOutput output) throws IOException {
		output.writeInt(this.data.length);
		output.write(this.data);
	}

	void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
		sizeTracker.read(192L);
		int i = input.readInt();
		sizeTracker.read((long) (8 * i));
		this.data = new byte[i];
		input.readFully(this.data);
	}

	/**+
	 * Gets the type byte for the tag.
	 */
	public byte getId() {
		return (byte) 7;
	}

	public String toString() {
		return "[" + this.data.length + " bytes]";
	}

	/**+
	 * Creates a clone of the tag.
	 */
	public NBTBase copy() {
		byte[] abyte = new byte[this.data.length];
		System.arraycopy(this.data, 0, abyte, 0, this.data.length);
		return new NBTTagByteArray(abyte);
	}

	public boolean equals(Object object) {
		return super.equals(object) ? Arrays.equals(this.data, ((NBTTagByteArray) object).data) : false;
	}

	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.data);
	}

	public byte[] getByteArray() {
		return this.data;
	}
}