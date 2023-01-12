package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

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
public class S12PacketEntityVelocity implements Packet<INetHandlerPlayClient> {
	private int entityID;
	private int motionX;
	private int motionY;
	private int motionZ;

	public S12PacketEntityVelocity() {
	}

	public S12PacketEntityVelocity(Entity entityIn) {
		this(entityIn.getEntityId(), entityIn.motionX, entityIn.motionY, entityIn.motionZ);
	}

	public S12PacketEntityVelocity(int entityIDIn, double motionXIn, double motionYIn, double motionZIn) {
		this.entityID = entityIDIn;
		double d0 = 3.9D;
		if (motionXIn < -d0) {
			motionXIn = -d0;
		}

		if (motionYIn < -d0) {
			motionYIn = -d0;
		}

		if (motionZIn < -d0) {
			motionZIn = -d0;
		}

		if (motionXIn > d0) {
			motionXIn = d0;
		}

		if (motionYIn > d0) {
			motionYIn = d0;
		}

		if (motionZIn > d0) {
			motionZIn = d0;
		}

		this.motionX = (int) (motionXIn * 8000.0D);
		this.motionY = (int) (motionYIn * 8000.0D);
		this.motionZ = (int) (motionZIn * 8000.0D);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityID = parPacketBuffer.readVarIntFromBuffer();
		this.motionX = parPacketBuffer.readShort();
		this.motionY = parPacketBuffer.readShort();
		this.motionZ = parPacketBuffer.readShort();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityID);
		parPacketBuffer.writeShort(this.motionX);
		parPacketBuffer.writeShort(this.motionY);
		parPacketBuffer.writeShort(this.motionZ);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleEntityVelocity(this);
	}

	public int getEntityID() {
		return this.entityID;
	}

	public int getMotionX() {
		return this.motionX;
	}

	public int getMotionY() {
		return this.motionY;
	}

	public int getMotionZ() {
		return this.motionZ;
	}
}