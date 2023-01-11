package net.minecraft.world.storage;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

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
public class MapData extends WorldSavedData {
	public int xCenter;
	public int zCenter;
	public byte dimension;
	public byte scale;
	/**+
	 * colours
	 */
	public byte[] colors = new byte[16384];
	/**+
	 * Holds a reference to the MapInfo of the players who own a
	 * copy of the map
	 */
	public List<MapData.MapInfo> playersArrayList = Lists.newArrayList();
	private Map<EntityPlayer, MapData.MapInfo> playersHashMap = Maps.newHashMap();
	public Map<String, Vec4b> mapDecorations = Maps.newLinkedHashMap();

	public MapData(String mapname) {
		super(mapname);
	}

	public void calculateMapCenter(double x, double z, int mapScale) {
		int i = 128 * (1 << mapScale);
		int j = MathHelper.floor_double((x + 64.0D) / (double) i);
		int k = MathHelper.floor_double((z + 64.0D) / (double) i);
		this.xCenter = j * i + i / 2 - 64;
		this.zCenter = k * i + i / 2 - 64;
	}

	/**+
	 * reads in data from the NBTTagCompound into this MapDataBase
	 */
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.dimension = nbttagcompound.getByte("dimension");
		this.xCenter = nbttagcompound.getInteger("xCenter");
		this.zCenter = nbttagcompound.getInteger("zCenter");
		this.scale = nbttagcompound.getByte("scale");
		this.scale = (byte) MathHelper.clamp_int(this.scale, 0, 4);
		short short1 = nbttagcompound.getShort("width");
		short short2 = nbttagcompound.getShort("height");
		if (short1 == 128 && short2 == 128) {
			this.colors = nbttagcompound.getByteArray("colors");
		} else {
			byte[] abyte = nbttagcompound.getByteArray("colors");
			this.colors = new byte[16384];
			int i = (128 - short1) / 2;
			int j = (128 - short2) / 2;

			for (int k = 0; k < short2; ++k) {
				int l = k + j;
				if (l >= 0 || l < 128) {
					for (int i1 = 0; i1 < short1; ++i1) {
						int j1 = i1 + i;
						if (j1 >= 0 || j1 < 128) {
							this.colors[j1 + l * 128] = abyte[i1 + k * short1];
						}
					}
				}
			}
		}

	}

	/**+
	 * write data to NBTTagCompound from this MapDataBase, similar
	 * to Entities and TileEntities
	 */
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setByte("dimension", this.dimension);
		nbttagcompound.setInteger("xCenter", this.xCenter);
		nbttagcompound.setInteger("zCenter", this.zCenter);
		nbttagcompound.setByte("scale", this.scale);
		nbttagcompound.setShort("width", (short) 128);
		nbttagcompound.setShort("height", (short) 128);
		nbttagcompound.setByteArray("colors", this.colors);
	}

	/**+
	 * Adds the player passed to the list of visible players and
	 * checks to see which players are visible
	 */
	public void updateVisiblePlayers(EntityPlayer player, ItemStack mapStack) {
		if (!this.playersHashMap.containsKey(player)) {
			MapData.MapInfo mapdata$mapinfo = new MapData.MapInfo(player);
			this.playersHashMap.put(player, mapdata$mapinfo);
			this.playersArrayList.add(mapdata$mapinfo);
		}

		if (!player.inventory.hasItemStack(mapStack)) {
			this.mapDecorations.remove(player.getName());
		}

		for (int i = 0; i < this.playersArrayList.size(); ++i) {
			MapData.MapInfo mapdata$mapinfo1 = (MapData.MapInfo) this.playersArrayList.get(i);
			if (!mapdata$mapinfo1.entityplayerObj.isDead
					&& (mapdata$mapinfo1.entityplayerObj.inventory.hasItemStack(mapStack)
							|| mapStack.isOnItemFrame())) {
				if (!mapStack.isOnItemFrame() && mapdata$mapinfo1.entityplayerObj.dimension == this.dimension) {
					this.updateDecorations(0, mapdata$mapinfo1.entityplayerObj.worldObj,
							mapdata$mapinfo1.entityplayerObj.getName(), mapdata$mapinfo1.entityplayerObj.posX,
							mapdata$mapinfo1.entityplayerObj.posZ,
							(double) mapdata$mapinfo1.entityplayerObj.rotationYaw);
				}
			} else {
				this.playersHashMap.remove(mapdata$mapinfo1.entityplayerObj);
				this.playersArrayList.remove(mapdata$mapinfo1);
			}
		}

		if (mapStack.isOnItemFrame()) {
			EntityItemFrame entityitemframe = mapStack.getItemFrame();
			BlockPos blockpos = entityitemframe.getHangingPosition();
			this.updateDecorations(1, player.worldObj, "frame-" + entityitemframe.getEntityId(),
					(double) blockpos.getX(), (double) blockpos.getZ(),
					(double) (entityitemframe.facingDirection.getHorizontalIndex() * 90));
		}

		if (mapStack.hasTagCompound() && mapStack.getTagCompound().hasKey("Decorations", 9)) {
			NBTTagList nbttaglist = mapStack.getTagCompound().getTagList("Decorations", 10);

			for (int j = 0; j < nbttaglist.tagCount(); ++j) {
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
				if (!this.mapDecorations.containsKey(nbttagcompound.getString("id"))) {
					this.updateDecorations(nbttagcompound.getByte("type"), player.worldObj,
							nbttagcompound.getString("id"), nbttagcompound.getDouble("x"),
							nbttagcompound.getDouble("z"), nbttagcompound.getDouble("rot"));
				}
			}
		}

	}

	private void updateDecorations(int type, World worldIn, String entityIdentifier, double worldX, double worldZ,
			double rotation) {
		int i = 1 << this.scale;
		float f = (float) (worldX - (double) this.xCenter) / (float) i;
		float f1 = (float) (worldZ - (double) this.zCenter) / (float) i;
		byte b0 = (byte) ((int) ((double) (f * 2.0F) + 0.5D));
		byte b1 = (byte) ((int) ((double) (f1 * 2.0F) + 0.5D));
		byte b3 = 63;
		byte b2;
		if (f >= (float) (-b3) && f1 >= (float) (-b3) && f <= (float) b3 && f1 <= (float) b3) {
			rotation = rotation + (rotation < 0.0D ? -8.0D : 8.0D);
			b2 = (byte) ((int) (rotation * 16.0D / 360.0D));
			if (this.dimension < 0) {
				int j = (int) (worldIn.getWorldInfo().getWorldTime() / 10L);
				b2 = (byte) (j * j * 34187121 + j * 121 >> 15 & 15);
			}
		} else {
			if (Math.abs(f) >= 320.0F || Math.abs(f1) >= 320.0F) {
				this.mapDecorations.remove(entityIdentifier);
				return;
			}

			type = 6;
			b2 = 0;
			if (f <= (float) (-b3)) {
				b0 = (byte) ((int) ((double) (b3 * 2) + 2.5D));
			}

			if (f1 <= (float) (-b3)) {
				b1 = (byte) ((int) ((double) (b3 * 2) + 2.5D));
			}

			if (f >= (float) b3) {
				b0 = (byte) (b3 * 2 + 1);
			}

			if (f1 >= (float) b3) {
				b1 = (byte) (b3 * 2 + 1);
			}
		}

		this.mapDecorations.put(entityIdentifier, new Vec4b((byte) type, b0, b1, b2));
	}

	public Packet getMapPacket(ItemStack mapStack, World worldIn, EntityPlayer player) {
		MapData.MapInfo mapdata$mapinfo = (MapData.MapInfo) this.playersHashMap.get(player);
		return mapdata$mapinfo == null ? null : mapdata$mapinfo.getPacket(mapStack);
	}

	public void updateMapData(int x, int y) {
		super.markDirty();

		for (MapData.MapInfo mapdata$mapinfo : this.playersArrayList) {
			mapdata$mapinfo.update(x, y);
		}

	}

	public MapData.MapInfo getMapInfo(EntityPlayer player) {
		MapData.MapInfo mapdata$mapinfo = (MapData.MapInfo) this.playersHashMap.get(player);
		if (mapdata$mapinfo == null) {
			mapdata$mapinfo = new MapData.MapInfo(player);
			this.playersHashMap.put(player, mapdata$mapinfo);
			this.playersArrayList.add(mapdata$mapinfo);
		}

		return mapdata$mapinfo;
	}

	public class MapInfo {
		public final EntityPlayer entityplayerObj;
		private boolean field_176105_d = true;
		private int minX = 0;
		private int minY = 0;
		private int maxX = 127;
		private int maxY = 127;
		private int field_176109_i;
		public int field_82569_d;

		public MapInfo(EntityPlayer player) {
			this.entityplayerObj = player;
		}

		public Packet getPacket(ItemStack stack) {
			if (this.field_176105_d) {
				this.field_176105_d = false;
				return new S34PacketMaps(stack.getMetadata(), MapData.this.scale, MapData.this.mapDecorations.values(),
						MapData.this.colors, this.minX, this.minY, this.maxX + 1 - this.minX,
						this.maxY + 1 - this.minY);
			} else {
				return this.field_176109_i++ % 5 == 0 ? new S34PacketMaps(stack.getMetadata(), MapData.this.scale,
						MapData.this.mapDecorations.values(), MapData.this.colors, 0, 0, 0, 0) : null;
			}
		}

		public void update(int x, int y) {
			if (this.field_176105_d) {
				this.minX = Math.min(this.minX, x);
				this.minY = Math.min(this.minY, y);
				this.maxX = Math.max(this.maxX, x);
				this.maxY = Math.max(this.maxY, y);
			} else {
				this.field_176105_d = true;
				this.minX = x;
				this.minY = y;
				this.maxX = x;
				this.maxY = y;
			}

		}
	}
}