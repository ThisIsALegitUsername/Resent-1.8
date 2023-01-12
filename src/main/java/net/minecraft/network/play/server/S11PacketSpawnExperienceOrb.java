package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.item.EntityXPOrb;
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
public class S11PacketSpawnExperienceOrb implements Packet<INetHandlerPlayClient> {
	private int entityID;
	private int posX;
	private int posY;
	private int posZ;
	private int xpValue;

	public S11PacketSpawnExperienceOrb() {
	}

	public S11PacketSpawnExperienceOrb(EntityXPOrb xpOrb) {
		this.entityID = xpOrb.getEntityId();
		this.posX = MathHelper.floor_double(xpOrb.posX * 32.0D);
		this.posY = MathHelper.floor_double(xpOrb.posY * 32.0D);
		this.posZ = MathHelper.floor_double(xpOrb.posZ * 32.0D);
		this.xpValue = xpOrb.getXpValue();
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityID = parPacketBuffer.readVarIntFromBuffer();
		this.posX = parPacketBuffer.readInt();
		this.posY = parPacketBuffer.readInt();
		this.posZ = parPacketBuffer.readInt();
		this.xpValue = parPacketBuffer.readShort();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityID);
		parPacketBuffer.writeInt(this.posX);
		parPacketBuffer.writeInt(this.posY);
		parPacketBuffer.writeInt(this.posZ);
		parPacketBuffer.writeShort(this.xpValue);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleSpawnExperienceOrb(this);
	}

	public int getEntityID() {
		return this.entityID;
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

	public int getXPValue() {
		return this.xpValue;
	}
}