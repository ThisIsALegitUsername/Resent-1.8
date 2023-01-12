package net.minecraft.network.play.server;

import java.io.IOException;

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
public class S06PacketUpdateHealth implements Packet<INetHandlerPlayClient> {
	private float health;
	private int foodLevel;
	private float saturationLevel;

	public S06PacketUpdateHealth() {
	}

	public S06PacketUpdateHealth(float healthIn, int foodLevelIn, float saturationIn) {
		this.health = healthIn;
		this.foodLevel = foodLevelIn;
		this.saturationLevel = saturationIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.health = parPacketBuffer.readFloat();
		this.foodLevel = parPacketBuffer.readVarIntFromBuffer();
		this.saturationLevel = parPacketBuffer.readFloat();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeFloat(this.health);
		parPacketBuffer.writeVarIntToBuffer(this.foodLevel);
		parPacketBuffer.writeFloat(this.saturationLevel);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleUpdateHealth(this);
	}

	public float getHealth() {
		return this.health;
	}

	public int getFoodLevel() {
		return this.foodLevel;
	}

	public float getSaturationLevel() {
		return this.saturationLevel;
	}
}