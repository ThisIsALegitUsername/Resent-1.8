package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
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
public class S14PacketEntity implements Packet<INetHandlerPlayClient> {
	protected int entityId;
	protected byte posX;
	protected byte posY;
	protected byte posZ;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;
	protected boolean field_149069_g;

	public S14PacketEntity() {
	}

	public S14PacketEntity(int entityIdIn) {
		this.entityId = entityIdIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityId = parPacketBuffer.readVarIntFromBuffer();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityId);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleEntityMovement(this);
	}

	public String toString() {
		return "Entity_" + super.toString();
	}

	public Entity getEntity(World worldIn) {
		return worldIn.getEntityByID(this.entityId);
	}

	public byte func_149062_c() {
		return this.posX;
	}

	public byte func_149061_d() {
		return this.posY;
	}

	public byte func_149064_e() {
		return this.posZ;
	}

	public byte func_149066_f() {
		return this.yaw;
	}

	public byte func_149063_g() {
		return this.pitch;
	}

	public boolean func_149060_h() {
		return this.field_149069_g;
	}

	public boolean getOnGround() {
		return this.onGround;
	}

	public static class S15PacketEntityRelMove extends S14PacketEntity {
		public S15PacketEntityRelMove() {
		}

		public S15PacketEntityRelMove(int entityIdIn, byte x, byte y, byte z, boolean onGroundIn) {
			super(entityIdIn);
			this.posX = x;
			this.posY = y;
			this.posZ = z;
			this.onGround = onGroundIn;
		}

		/**+
		 * Reads the raw packet data from the data stream.
		 */
		public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
			super.readPacketData(parPacketBuffer);
			this.posX = parPacketBuffer.readByte();
			this.posY = parPacketBuffer.readByte();
			this.posZ = parPacketBuffer.readByte();
			this.onGround = parPacketBuffer.readBoolean();
		}

		/**+
		 * Writes the raw packet data to the data stream.
		 */
		public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
			super.writePacketData(parPacketBuffer);
			parPacketBuffer.writeByte(this.posX);
			parPacketBuffer.writeByte(this.posY);
			parPacketBuffer.writeByte(this.posZ);
			parPacketBuffer.writeBoolean(this.onGround);
		}
	}

	public static class S16PacketEntityLook extends S14PacketEntity {
		public S16PacketEntityLook() {
			this.field_149069_g = true;
		}

		public S16PacketEntityLook(int entityIdIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
			super(entityIdIn);
			this.yaw = yawIn;
			this.pitch = pitchIn;
			this.field_149069_g = true;
			this.onGround = onGroundIn;
		}

		/**+
		 * Reads the raw packet data from the data stream.
		 */
		public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
			super.readPacketData(parPacketBuffer);
			this.yaw = parPacketBuffer.readByte();
			this.pitch = parPacketBuffer.readByte();
			this.onGround = parPacketBuffer.readBoolean();
		}

		/**+
		 * Writes the raw packet data to the data stream.
		 */
		public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
			super.writePacketData(parPacketBuffer);
			parPacketBuffer.writeByte(this.yaw);
			parPacketBuffer.writeByte(this.pitch);
			parPacketBuffer.writeBoolean(this.onGround);
		}
	}

	public static class S17PacketEntityLookMove extends S14PacketEntity {
		public S17PacketEntityLookMove() {
			this.field_149069_g = true;
		}

		public S17PacketEntityLookMove(int parInt1, byte parByte1, byte parByte2, byte parByte3, byte parByte4,
				byte parByte5, boolean parFlag) {
			super(parInt1);
			this.posX = parByte1;
			this.posY = parByte2;
			this.posZ = parByte3;
			this.yaw = parByte4;
			this.pitch = parByte5;
			this.onGround = parFlag;
			this.field_149069_g = true;
		}

		/**+
		 * Reads the raw packet data from the data stream.
		 */
		public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
			super.readPacketData(parPacketBuffer);
			this.posX = parPacketBuffer.readByte();
			this.posY = parPacketBuffer.readByte();
			this.posZ = parPacketBuffer.readByte();
			this.yaw = parPacketBuffer.readByte();
			this.pitch = parPacketBuffer.readByte();
			this.onGround = parPacketBuffer.readBoolean();
		}

		/**+
		 * Writes the raw packet data to the data stream.
		 */
		public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
			super.writePacketData(parPacketBuffer);
			parPacketBuffer.writeByte(this.posX);
			parPacketBuffer.writeByte(this.posY);
			parPacketBuffer.writeByte(this.posZ);
			parPacketBuffer.writeByte(this.yaw);
			parPacketBuffer.writeByte(this.pitch);
			parPacketBuffer.writeBoolean(this.onGround);
		}
	}
}