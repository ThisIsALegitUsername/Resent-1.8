package net.minecraft.tileentity;

import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import net.lax1dude.eaglercraft.v1_8.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StringUtils;

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
public class TileEntitySkull extends TileEntity {
	private int skullType;
	private int skullRotation;
	private GameProfile playerProfile = null;

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("SkullType", (byte) (this.skullType & 255));
		nbttagcompound.setByte("Rot", (byte) (this.skullRotation & 255));
		if (this.playerProfile != null) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbttagcompound1, this.playerProfile);
			nbttagcompound.setTag("Owner", nbttagcompound1);
		}

	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.skullType = nbttagcompound.getByte("SkullType");
		this.skullRotation = nbttagcompound.getByte("Rot");
		if (this.skullType == 3) {
			if (nbttagcompound.hasKey("Owner", 10)) {
				this.playerProfile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("Owner"));
			} else if (nbttagcompound.hasKey("ExtraType", 8)) {
				String s = nbttagcompound.getString("ExtraType");
				if (!StringUtils.isNullOrEmpty(s)) {
					this.playerProfile = new GameProfile((EaglercraftUUID) null, s);
					this.updatePlayerProfile();
				}
			}
		}

	}

	public GameProfile getPlayerProfile() {
		return this.playerProfile;
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
		return new S35PacketUpdateTileEntity(this.pos, 4, nbttagcompound);
	}

	public void setType(int type) {
		this.skullType = type;
		this.playerProfile = null;
	}

	public void setPlayerProfile(GameProfile playerProfile) {
		this.skullType = 3;
		this.playerProfile = playerProfile;
		this.updatePlayerProfile();
	}

	private void updatePlayerProfile() {
		this.playerProfile = updateGameprofile(this.playerProfile);
		this.markDirty();
	}

	public static GameProfile updateGameprofile(GameProfile input) {
		return input;
	}

	public int getSkullType() {
		return this.skullType;
	}

	public int getSkullRotation() {
		return this.skullRotation;
	}

	public void setSkullRotation(int rotation) {
		this.skullRotation = rotation;
	}
}