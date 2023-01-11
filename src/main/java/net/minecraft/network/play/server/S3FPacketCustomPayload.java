package net.minecraft.network.play.server;

import java.io.IOException;

import net.lax1dude.eaglercraft.v1_8.netty.ByteBuf;
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
public class S3FPacketCustomPayload implements Packet<INetHandlerPlayClient> {
	private String channel;
	private PacketBuffer data;

	public S3FPacketCustomPayload() {
	}

	public S3FPacketCustomPayload(String channelName, PacketBuffer dataIn) {
		this.channel = channelName;
		this.data = dataIn;
		if (dataIn.writerIndex() > 1048576) {
			throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
		}
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.channel = parPacketBuffer.readStringFromBuffer(20);
		int i = parPacketBuffer.readableBytes();
		if (i >= 0 && i <= 1048576) {
			this.data = new PacketBuffer(parPacketBuffer.readBytes(i));
		} else {
			throw new IOException("Payload may not be larger than 1048576 bytes");
		}
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeString(this.channel);
		parPacketBuffer.writeBytes((ByteBuf) this.data);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleCustomPayload(this);
	}

	public String getChannelName() {
		return this.channel;
	}

	public PacketBuffer getBufferData() {
		return this.data;
	}
}