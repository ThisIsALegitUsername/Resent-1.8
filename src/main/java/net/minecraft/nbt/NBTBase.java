package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
	public static final String[] NBT_TYPES = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE",
			"BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };

	abstract void write(DataOutput var1) throws IOException;

	abstract void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException;

	public abstract String toString();

	public abstract byte getId();

	/**+
	 * Creates a new NBTBase object that corresponds with the passed
	 * in id.
	 */
	protected static NBTBase createNewByType(byte id) {
		switch (id) {
		case 0:
			return new NBTTagEnd();
		case 1:
			return new NBTTagByte();
		case 2:
			return new NBTTagShort();
		case 3:
			return new NBTTagInt();
		case 4:
			return new NBTTagLong();
		case 5:
			return new NBTTagFloat();
		case 6:
			return new NBTTagDouble();
		case 7:
			return new NBTTagByteArray();
		case 8:
			return new NBTTagString();
		case 9:
			return new NBTTagList();
		case 10:
			return new NBTTagCompound();
		case 11:
			return new NBTTagIntArray();
		default:
			return null;
		}
	}

	public abstract NBTBase copy();

	/**+
	 * Return whether this compound has no tags.
	 */
	public boolean hasNoTags() {
		return false;
	}

	public boolean equals(Object object) {
		if (!(object instanceof NBTBase)) {
			return false;
		} else {
			NBTBase nbtbase = (NBTBase) object;
			return this.getId() == nbtbase.getId();
		}
	}

	public int hashCode() {
		return this.getId();
	}

	protected String getString() {
		return this.toString();
	}

	public abstract static class NBTPrimitive extends NBTBase {
		public abstract long getLong();

		public abstract int getInt();

		public abstract short getShort();

		public abstract byte getByte();

		public abstract double getDouble();

		public abstract float getFloat();
	}
}