package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
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
public class S0EPacketSpawnObject implements Packet<INetHandlerPlayClient> {
	private int entityId;
	private int x;
	private int y;
	private int z;
	private int speedX;
	private int speedY;
	private int speedZ;
	private int pitch;
	private int yaw;
	private int type;
	private int field_149020_k;

	public S0EPacketSpawnObject() {
	}

	public S0EPacketSpawnObject(Entity entityIn, int typeIn) {
		this(entityIn, typeIn, 0);
	}

	public S0EPacketSpawnObject(Entity entityIn, int typeIn, int parInt1) {
		this.entityId = entityIn.getEntityId();
		this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
		this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
		this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
		this.pitch = MathHelper.floor_float(entityIn.rotationPitch * 256.0F / 360.0F);
		this.yaw = MathHelper.floor_float(entityIn.rotationYaw * 256.0F / 360.0F);
		this.type = typeIn;
		this.field_149020_k = parInt1;
		if (parInt1 > 0) {
			double d0 = entityIn.motionX;
			double d1 = entityIn.motionY;
			double d2 = entityIn.motionZ;
			double d3 = 3.9D;
			if (d0 < -d3) {
				d0 = -d3;
			}

			if (d1 < -d3) {
				d1 = -d3;
			}

			if (d2 < -d3) {
				d2 = -d3;
			}

			if (d0 > d3) {
				d0 = d3;
			}

			if (d1 > d3) {
				d1 = d3;
			}

			if (d2 > d3) {
				d2 = d3;
			}

			this.speedX = (int) (d0 * 8000.0D);
			this.speedY = (int) (d1 * 8000.0D);
			this.speedZ = (int) (d2 * 8000.0D);
		}

	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityId = parPacketBuffer.readVarIntFromBuffer();
		this.type = parPacketBuffer.readByte();
		this.x = parPacketBuffer.readInt();
		this.y = parPacketBuffer.readInt();
		this.z = parPacketBuffer.readInt();
		this.pitch = parPacketBuffer.readByte();
		this.yaw = parPacketBuffer.readByte();
		this.field_149020_k = parPacketBuffer.readInt();
		if (this.field_149020_k > 0) {
			this.speedX = parPacketBuffer.readShort();
			this.speedY = parPacketBuffer.readShort();
			this.speedZ = parPacketBuffer.readShort();
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityId);
		parPacketBuffer.writeByte(this.type);
		parPacketBuffer.writeInt(this.x);
		parPacketBuffer.writeInt(this.y);
		parPacketBuffer.writeInt(this.z);
		parPacketBuffer.writeByte(this.pitch);
		parPacketBuffer.writeByte(this.yaw);
		parPacketBuffer.writeInt(this.field_149020_k);
		if (this.field_149020_k > 0) {
			parPacketBuffer.writeShort(this.speedX);
			parPacketBuffer.writeShort(this.speedY);
			parPacketBuffer.writeShort(this.speedZ);
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleSpawnObject(this);
	}

	public int getEntityID() {
		return this.entityId;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public int getSpeedX() {
		return this.speedX;
	}

	public int getSpeedY() {
		return this.speedY;
	}

	public int getSpeedZ() {
		return this.speedZ;
	}

	public int getPitch() {
		return this.pitch;
	}

	public int getYaw() {
		return this.yaw;
	}

	public int getType() {
		return this.type;
	}

	public int func_149009_m() {
		return this.field_149020_k;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public void setZ(int newZ) {
		this.z = newZ;
	}

	public void setSpeedX(int newSpeedX) {
		this.speedX = newSpeedX;
	}

	public void setSpeedY(int newSpeedY) {
		this.speedY = newSpeedY;
	}

	public void setSpeedZ(int newSpeedZ) {
		this.speedZ = newSpeedZ;
	}

	public void func_149002_g(int parInt1) {
		this.field_149020_k = parInt1;
	}
}