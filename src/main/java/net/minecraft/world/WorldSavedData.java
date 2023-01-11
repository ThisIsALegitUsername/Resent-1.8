package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public abstract class WorldSavedData {
	public final String mapName;
	private boolean dirty;

	public WorldSavedData(String name) {
		this.mapName = name;
	}

	public abstract void readFromNBT(NBTTagCompound var1);

	public abstract void writeToNBT(NBTTagCompound var1);

	/**+
	 * Marks this MapDataBase dirty, to be saved to disk when the
	 * level next saves.
	 */
	public void markDirty() {
		this.setDirty(true);
	}

	/**+
	 * Sets the dirty state of this MapDataBase, whether it needs
	 * saving to disk.
	 */
	public void setDirty(boolean isDirty) {
		this.dirty = isDirty;
	}

	/**+
	 * Whether this MapDataBase needs saving to disk.
	 */
	public boolean isDirty() {
		return this.dirty;
	}
}