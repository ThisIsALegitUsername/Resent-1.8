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
public class S18PacketEntityTeleport implements Packet<INetHandlerPlayClient> {
	private int entityId;
	private int posX;
	private int posY;
	private int posZ;
	private byte yaw;
	private byte pitch;
	private boolean onGround;

	public S18PacketEntityTeleport() {
	}

	public S18PacketEntityTeleport(Entity entityIn) {
		this.entityId = entityIn.getEntityId();
		this.posX = MathHelper.floor_double(entityIn.posX * 32.0D);
		this.posY = MathHelper.floor_double(entityIn.posY * 32.0D);
		this.posZ = MathHelper.floor_double(entityIn.posZ * 32.0D);
		this.yaw = (byte) ((int) (entityIn.rotationYaw * 256.0F / 360.0F));
		this.pitch = (byte) ((int) (entityIn.rotationPitch * 256.0F / 360.0F));
		this.onGround = entityIn.onGround;
	}

	public S18PacketEntityTeleport(int entityIdIn, int posXIn, int posYIn, int posZIn, byte yawIn, byte pitchIn,
			boolean onGroundIn) {
		this.entityId = entityIdIn;
		this.posX = posXIn;
		this.posY = posYIn;
		this.posZ = posZIn;
		this.yaw = yawIn;
		this.pitch = pitchIn;
		this.onGround = onGroundIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityId = parPacketBuffer.readVarIntFromBuffer();
		this.posX = parPacketBuffer.readInt();
		this.posY = parPacketBuffer.readInt();
		this.posZ = parPacketBuffer.readInt();
		this.yaw = parPacketBuffer.readByte();
		this.pitch = parPacketBuffer.readByte();
		this.onGround = parPacketBuffer.readBoolean();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityId);
		parPacketBuffer.writeInt(this.posX);
		parPacketBuffer.writeInt(this.posY);
		parPacketBuffer.writeInt(this.posZ);
		parPacketBuffer.writeByte(this.yaw);
		parPacketBuffer.writeByte(this.pitch);
		parPacketBuffer.writeBoolean(this.onGround);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleEntityTeleport(this);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getX() {
		return this.posX;
	}

	public int getY() {
		return this.posY;
	}

	public int getZ() {
		return this.posZ;
	}

	public byte getYaw() {
		return this.yaw;
	}

	public byte getPitch() {
		return this.pitch;
	}

	public boolean getOnGround() {
		return this.onGround;
	}
}