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
public class NBTTagString extends NBTBase {
	private String data;

	public NBTTagString() {
		this.data = "";
	}

	public NBTTagString(String data) {
		this.data = data;
		if (data == null) {
			throw new IllegalArgumentException("Empty string not allowed");
		}
	}

	/**+
	 * Write the actual data contents of the tag, implemented in NBT
	 * extension classes
	 */
	void write(DataOutput parDataOutput) throws IOException {
		parDataOutput.writeUTF(this.data);
	}

	void read(DataInput parDataInput, int parInt1, NBTSizeTracker parNBTSizeTracker) throws IOException {
		parNBTSizeTracker.read(288L);
		this.data = parDataInput.readUTF();
		parNBTSizeTracker.read((long) (16 * this.data.length()));
	}

	/**+
	 * Gets the type byte for the tag.
	 */
	public byte getId() {
		return (byte) 8;
	}

	public String toString() {
		return "\"" + this.data.replace("\"", "\\\"") + "\"";
	}

	/**+
	 * Creates a clone of the tag.
	 */
	public NBTBase copy() {
		return new NBTTagString(this.data);
	}

	/**+
	 * Return whether this compound has no tags.
	 */
	public boolean hasNoTags() {
		return this.data.isEmpty();
	}

	public boolean equals(Object object) {
		if (!super.equals(object)) {
			return false;
		} else {
			NBTTagString nbttagstring = (NBTTagString) object;
			return this.data == null && nbttagstring.data == null
					|| this.data != null && this.data.equals(nbttagstring.data);
		}
	}

	public int hashCode() {
		return super.hashCode() ^ this.data.hashCode();
	}

	public String getString() {
		return this.data;
	}
}