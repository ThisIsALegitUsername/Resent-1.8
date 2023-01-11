package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

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
public class C03PacketPlayer implements Packet<INetHandlerPlayServer> {
	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;
	protected boolean moving;
	protected boolean rotating;

	public C03PacketPlayer() {
	}

	public C03PacketPlayer(boolean isOnGround) {
		this.onGround = isOnGround;
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayServer inethandlerplayserver) {
		inethandlerplayserver.processPlayer(this);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.onGround = parPacketBuffer.readUnsignedByte() != 0;
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeByte(this.onGround ? 1 : 0);
	}

	public double getPositionX() {
		return this.x;
	}

	public double getPositionY() {
		return this.y;
	}

	public double getPositionZ() {
		return this.z;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	public boolean isOnGround() {
		return this.onGround;
	}

	public boolean isMoving() {
		return this.moving;
	}

	public boolean getRotating() {
		return this.rotating;
	}

	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
	}

	public static class C04PacketPlayerPosition extends C03PacketPlayer {
		public C04PacketPlayerPosition() {
			this.moving = true;
		}

		public C04PacketPlayerPosition(double posX, double posY, double posZ, boolean isOnGround) {
			this.x = posX;
			this.y = posY;
			this.z = posZ;
			this.onGround = isOnGround;
			this.moving = true;
		}

		/**+
		 * Reads the raw packet data from the data stream.
		 */
		public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
			this.x = parPacketBuffer.readDouble();
			this.y = parPacketBuffer.readDouble();
			this.z = parPacketBuffer.readDouble();
			super.readPacketData(parPacketBuffer);
		}

		/**+
		 * Writes the raw packet data to the data stream.
		 */
		public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
			parPacketBuffer.writeDouble(this.x);
			parPacketBuffer.writeDouble(this.y);
			parPacketBuffer.writeDouble(this.z);
			super.writePacketData(parPacketBuffer);
		}
	}

	public static class C05PacketPlayerLook extends C03PacketPlayer {
		public C05PacketPlayerLook() {
			this.rotating = true;
		}

		public C05PacketPlayerLook(float playerYaw, float playerPitch, boolean isOnGround) {
			this.yaw = playerYaw;
			this.pitch = playerPitch;
			this.onGround = isOnGround;
			this.rotating = true;
		}

		/**+
		 * Reads the raw packet data from the data stream.
		 */
		public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
			this.yaw = parPacketBuffer.readFloat();
			this.pitch = parPacketBuffer.readFloat();
			super.readPacketData(parPacketBuffer);
		}

		/**+
		 * Writes the raw packet data to the data stream.
		 */
		public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
			parPacketBuffer.writeFloat(this.yaw);
			parPacketBuffer.writeFloat(this.pitch);
			super.writePacketData(parPacketBuffer);
		}
	}

	public static class C06PacketPlayerPosLook extends C03PacketPlayer {
		public C06PacketPlayerPosLook() {
			this.moving = true;
			this.rotating = true;
		}

		public C06PacketPlayerPosLook(double playerX, double playerY, double playerZ, float playerYaw,
				float playerPitch, boolean playerIsOnGround) {
			this.x = playerX;
			this.y = playerY;
			this.z = playerZ;
			this.yaw = playerYaw;
			this.pitch = playerPitch;
			this.onGround = playerIsOnGround;
			this.rotating = true;
			this.moving = true;
		}

		/**+
		 * Reads the raw packet data from the data stream.
		 */
		public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
			this.x = parPacketBuffer.readDouble();
			this.y = parPacketBuffer.readDouble();
			this.z = parPacketBuffer.readDouble();
			this.yaw = parPacketBuffer.readFloat();
			this.pitch = parPacketBuffer.readFloat();
			super.readPacketData(parPacketBuffer);
		}

		/**+
		 * Writes the raw packet data to the data stream.
		 */
		public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
			parPacketBuffer.writeDouble(this.x);
			parPacketBuffer.writeDouble(this.y);
			parPacketBuffer.writeDouble(this.z);
			parPacketBuffer.writeFloat(this.yaw);
			parPacketBuffer.writeFloat(this.pitch);
			super.writePacketData(parPacketBuffer);
		}
	}
}