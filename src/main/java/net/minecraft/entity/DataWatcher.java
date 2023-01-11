package net.minecraft.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Rotations;

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
public class DataWatcher {
	private final Entity owner;
	/**+
	 * When isBlank is true the DataWatcher is not watching any
	 * objects
	 */
	private boolean isBlank = true;
	private static final Map<Class<?>, Integer> dataTypes = Maps.newHashMap();
	private final Map<Integer, DataWatcher.WatchableObject> watchedObjects = Maps.newHashMap();
	private boolean objectChanged;

	public DataWatcher(Entity owner) {
		this.owner = owner;
	}

	public <T> void addObject(int id, T object) {
		Integer integer = (Integer) dataTypes.get(object.getClass());
		if (integer == null) {
			throw new IllegalArgumentException("Unknown data type: " + object.getClass());
		} else if (id > 31) {
			throw new IllegalArgumentException("Data value id is too big with " + id + "! (Max is " + 31 + ")");
		} else if (this.watchedObjects.containsKey(Integer.valueOf(id))) {
			throw new IllegalArgumentException("Duplicate id value for " + id + "!");
		} else {
			DataWatcher.WatchableObject datawatcher$watchableobject = new DataWatcher.WatchableObject(
					integer.intValue(), id, object);
			this.watchedObjects.put(Integer.valueOf(id), datawatcher$watchableobject);
			this.isBlank = false;
		}
	}

	/**+
	 * Add a new object for the DataWatcher to watch, using the
	 * specified data type.
	 */
	public void addObjectByDataType(int id, int type) {
		DataWatcher.WatchableObject datawatcher$watchableobject = new DataWatcher.WatchableObject(type, id,
				(Object) null);
		this.watchedObjects.put(Integer.valueOf(id), datawatcher$watchableobject);
		this.isBlank = false;
	}

	/**+
	 * gets the bytevalue of a watchable object
	 */
	public byte getWatchableObjectByte(int id) {
		return ((Byte) this.getWatchedObject(id).getObject()).byteValue();
	}

	public short getWatchableObjectShort(int id) {
		return ((Short) this.getWatchedObject(id).getObject()).shortValue();
	}

	/**+
	 * gets a watchable object and returns it as a Integer
	 */
	public int getWatchableObjectInt(int id) {
		return ((Integer) this.getWatchedObject(id).getObject()).intValue();
	}

	public float getWatchableObjectFloat(int id) {
		return ((Float) this.getWatchedObject(id).getObject()).floatValue();
	}

	/**+
	 * gets a watchable object and returns it as a String
	 */
	public String getWatchableObjectString(int id) {
		return (String) this.getWatchedObject(id).getObject();
	}

	/**+
	 * Get a watchable object as an ItemStack.
	 */
	public ItemStack getWatchableObjectItemStack(int id) {
		return (ItemStack) this.getWatchedObject(id).getObject();
	}

	/**+
	 * is threadsafe, unless it throws an exception, then
	 */
	private DataWatcher.WatchableObject getWatchedObject(int id) {
		DataWatcher.WatchableObject datawatcher$watchableobject;
		try {
			datawatcher$watchableobject = (DataWatcher.WatchableObject) this.watchedObjects.get(Integer.valueOf(id));
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting synched entity data");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Synched entity data");
			crashreportcategory.addCrashSection("Data ID", Integer.valueOf(id));
			throw new ReportedException(crashreport);
		}
		return datawatcher$watchableobject;
	}

	public Rotations getWatchableObjectRotations(int id) {
		return (Rotations) this.getWatchedObject(id).getObject();
	}

	public <T> void updateObject(int id, T newData) {
		DataWatcher.WatchableObject datawatcher$watchableobject = this.getWatchedObject(id);
		if (ObjectUtils.notEqual(newData, datawatcher$watchableobject.getObject())) {
			datawatcher$watchableobject.setObject(newData);
			this.owner.onDataWatcherUpdate(id);
			datawatcher$watchableobject.setWatched(true);
			this.objectChanged = true;
		}

	}

	public void setObjectWatched(int id) {
		this.getWatchedObject(id).watched = true;
		this.objectChanged = true;
	}

	/**+
	 * true if one or more object was changed
	 */
	public boolean hasObjectChanged() {
		return this.objectChanged;
	}

	/**+
	 * Writes the list of watched objects (entity attribute of type
	 * {byte, short, int, float, string, ItemStack,
	 * ChunkCoordinates}) to the specified PacketBuffer
	 */
	public static void writeWatchedListToPacketBuffer(List<DataWatcher.WatchableObject> objectsList,
			PacketBuffer buffer) throws IOException {
		if (objectsList != null) {
			for (DataWatcher.WatchableObject datawatcher$watchableobject : objectsList) {
				writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
			}
		}

		buffer.writeByte(127);
	}

	public List<DataWatcher.WatchableObject> getChanged() {
		ArrayList arraylist = null;
		if (this.objectChanged) {
			for (DataWatcher.WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
				if (datawatcher$watchableobject.isWatched()) {
					datawatcher$watchableobject.setWatched(false);
					if (arraylist == null) {
						arraylist = Lists.newArrayList();
					}

					arraylist.add(datawatcher$watchableobject);
				}
			}
		}

		this.objectChanged = false;
		return arraylist;
	}

	public void writeTo(PacketBuffer buffer) throws IOException {
		for (DataWatcher.WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
			writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
		}
		buffer.writeByte(127);
	}

	public List<DataWatcher.WatchableObject> getAllWatched() {
		ArrayList arraylist = null;

		for (DataWatcher.WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
			if (arraylist == null) {
				arraylist = Lists.newArrayList();
			}

			arraylist.add(datawatcher$watchableobject);
		}

		return arraylist;
	}

	/**+
	 * Writes a watchable object (entity attribute of type {byte,
	 * short, int, float, string, ItemStack, ChunkCoordinates}) to
	 * the specified PacketBuffer
	 */
	private static void writeWatchableObjectToPacketBuffer(PacketBuffer buffer, DataWatcher.WatchableObject object)
			throws IOException {
		int i = (object.getObjectType() << 5 | object.getDataValueId() & 31) & 255;
		buffer.writeByte(i);
		switch (object.getObjectType()) {
		case 0:
			buffer.writeByte(((Byte) object.getObject()).byteValue());
			break;
		case 1:
			buffer.writeShort(((Short) object.getObject()).shortValue());
			break;
		case 2:
			buffer.writeInt(((Integer) object.getObject()).intValue());
			break;
		case 3:
			buffer.writeFloat(((Float) object.getObject()).floatValue());
			break;
		case 4:
			buffer.writeString((String) object.getObject());
			break;
		case 5:
			ItemStack itemstack = (ItemStack) object.getObject();
			buffer.writeItemStackToBuffer(itemstack);
			break;
		case 6:
			BlockPos blockpos = (BlockPos) object.getObject();
			buffer.writeInt(blockpos.getX());
			buffer.writeInt(blockpos.getY());
			buffer.writeInt(blockpos.getZ());
			break;
		case 7:
			Rotations rotations = (Rotations) object.getObject();
			buffer.writeFloat(rotations.getX());
			buffer.writeFloat(rotations.getY());
			buffer.writeFloat(rotations.getZ());
		}

	}

	/**+
	 * Reads a list of watched objects (entity attribute of type
	 * {byte, short, int, float, string, ItemStack,
	 * ChunkCoordinates}) from the supplied PacketBuffer
	 */
	public static List<DataWatcher.WatchableObject> readWatchedListFromPacketBuffer(PacketBuffer buffer)
			throws IOException {
		ArrayList arraylist = null;

		for (byte b0 = buffer.readByte(); b0 != 127; b0 = buffer.readByte()) {
			if (arraylist == null) {
				arraylist = Lists.newArrayList();
			}

			int i = (b0 & 224) >> 5;
			int j = b0 & 31;
			DataWatcher.WatchableObject datawatcher$watchableobject = null;
			switch (i) {
			case 0:
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Byte.valueOf(buffer.readByte()));
				break;
			case 1:
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Short.valueOf(buffer.readShort()));
				break;
			case 2:
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Integer.valueOf(buffer.readInt()));
				break;
			case 3:
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Float.valueOf(buffer.readFloat()));
				break;
			case 4:
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readStringFromBuffer(32767));
				break;
			case 5:
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readItemStackFromBuffer());
				break;
			case 6:
				int k = buffer.readInt();
				int l = buffer.readInt();
				int i1 = buffer.readInt();
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, new BlockPos(k, l, i1));
				break;
			case 7:
				float f = buffer.readFloat();
				float f1 = buffer.readFloat();
				float f2 = buffer.readFloat();
				datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, new Rotations(f, f1, f2));
			}

			arraylist.add(datawatcher$watchableobject);
		}

		return arraylist;
	}

	public void updateWatchedObjectsFromList(List<DataWatcher.WatchableObject> parList) {

		for (DataWatcher.WatchableObject datawatcher$watchableobject : parList) {
			DataWatcher.WatchableObject datawatcher$watchableobject1 = (DataWatcher.WatchableObject) this.watchedObjects
					.get(Integer.valueOf(datawatcher$watchableobject.getDataValueId()));
			if (datawatcher$watchableobject1 != null) {
				datawatcher$watchableobject1.setObject(datawatcher$watchableobject.getObject());
				this.owner.onDataWatcherUpdate(datawatcher$watchableobject.getDataValueId());
			}
		}

		this.objectChanged = true;
	}

	public boolean getIsBlank() {
		return this.isBlank;
	}

	public void func_111144_e() {
		this.objectChanged = false;
	}

	static {
		dataTypes.put(Byte.class, Integer.valueOf(0));
		dataTypes.put(Short.class, Integer.valueOf(1));
		dataTypes.put(Integer.class, Integer.valueOf(2));
		dataTypes.put(Float.class, Integer.valueOf(3));
		dataTypes.put(String.class, Integer.valueOf(4));
		dataTypes.put(ItemStack.class, Integer.valueOf(5));
		dataTypes.put(BlockPos.class, Integer.valueOf(6));
		dataTypes.put(Rotations.class, Integer.valueOf(7));
	}

	public static class WatchableObject {
		private final int objectType;
		private final int dataValueId;
		private Object watchedObject;
		private boolean watched;

		public WatchableObject(int type, int id, Object object) {
			this.dataValueId = id;
			this.watchedObject = object;
			this.objectType = type;
			this.watched = true;
		}

		public int getDataValueId() {
			return this.dataValueId;
		}

		public void setObject(Object object) {
			this.watchedObject = object;
		}

		public Object getObject() {
			return this.watchedObject;
		}

		public int getObjectType() {
			return this.objectType;
		}

		public boolean isWatched() {
			return this.watched;
		}

		public void setWatched(boolean watched) {
			this.watched = watched;
		}
	}
}