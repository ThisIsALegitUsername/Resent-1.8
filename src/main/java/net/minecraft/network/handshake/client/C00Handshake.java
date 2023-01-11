package net.minecraft.network.handshake.client;

import java.io.IOException;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

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
public class C00Handshake implements Packet<INetHandlerHandshakeServer> {
	private int protocolVersion;
	private String ip;
	private int port;
	private EnumConnectionState requestedState;

	public C00Handshake() {
	}

	public C00Handshake(int version, String ip, int port, EnumConnectionState requestedState) {
		this.protocolVersion = version;
		this.ip = ip;
		this.port = port;
		this.requestedState = requestedState;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.protocolVersion = parPacketBuffer.readVarIntFromBuffer();
		this.ip = parPacketBuffer.readStringFromBuffer(255);
		this.port = parPacketBuffer.readUnsignedShort();
		this.requestedState = EnumConnectionState.getById(parPacketBuffer.readVarIntFromBuffer());
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.protocolVersion);
		parPacketBuffer.writeString(this.ip);
		parPacketBuffer.writeShort(this.port);
		parPacketBuffer.writeVarIntToBuffer(this.requestedState.getId());
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerHandshakeServer inethandlerhandshakeserver) {
		inethandlerhandshakeserver.processHandshake(this);
	}

	public EnumConnectionState getRequestedState() {
		return this.requestedState;
	}

	public int getProtocolVersion() {
		return this.protocolVersion;
	}
}