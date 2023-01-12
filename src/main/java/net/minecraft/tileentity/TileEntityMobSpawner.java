package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

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
public class TileEntityMobSpawner extends TileEntity implements ITickable {
	private final MobSpawnerBaseLogic spawnerLogic = new MobSpawnerBaseLogic() {
		public void func_98267_a(int i) {
			TileEntityMobSpawner.this.worldObj.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.mob_spawner, i, 0);
		}

		public World getSpawnerWorld() {
			return TileEntityMobSpawner.this.worldObj;
		}

		public BlockPos getSpawnerPosition() {
			return TileEntityMobSpawner.this.pos;
		}

		public void setRandomEntity(
				MobSpawnerBaseLogic.WeightedRandomMinecart mobspawnerbaselogic$weightedrandomminecart) {
			super.setRandomEntity(mobspawnerbaselogic$weightedrandomminecart);
			if (this.getSpawnerWorld() != null) {
				this.getSpawnerWorld().markBlockForUpdate(TileEntityMobSpawner.this.pos);
			}

		}
	};

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.spawnerLogic.readFromNBT(nbttagcompound);
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		this.spawnerLogic.writeToNBT(nbttagcompound);
	}

	/**+
	 * Like the old updateEntity(), except more generic.
	 */
	public void update() {
		this.spawnerLogic.updateSpawner();
	}

	/**+
	 * Allows for a specialized description packet to be created.
	 * This is often used to sync tile entity data from the server
	 * to the client easily. For example this is used by signs to
	 * synchronise the text to be displayed.
	 */
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		nbttagcompound.removeTag("SpawnPotentials");
		return new S35PacketUpdateTileEntity(this.pos, 1, nbttagcompound);
	}

	public boolean receiveClientEvent(int i, int j) {
		return this.spawnerLogic.setDelayToMin(i) ? true : super.receiveClientEvent(i, j);
	}

	public boolean func_183000_F() {
		return true;
	}

	public MobSpawnerBaseLogic getSpawnerBaseLogic() {
		return this.spawnerLogic;
	}
}