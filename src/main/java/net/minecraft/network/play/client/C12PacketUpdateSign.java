package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

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
public class C12PacketUpdateSign implements Packet<INetHandlerPlayServer> {
	private BlockPos pos;
	private IChatComponent[] lines;

	public C12PacketUpdateSign() {
	}

	public C12PacketUpdateSign(BlockPos pos, IChatComponent[] lines) {
		this.pos = pos;
		this.lines = new IChatComponent[] { lines[0], lines[1], lines[2], lines[3] };
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.pos = parPacketBuffer.readBlockPos();
		this.lines = new IChatComponent[4];

		for (int i = 0; i < 4; ++i) {
			String s = parPacketBuffer.readStringFromBuffer(384);
			IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
			this.lines[i] = ichatcomponent;
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeBlockPos(this.pos);

		for (int i = 0; i < 4; ++i) {
			IChatComponent ichatcomponent = this.lines[i];
			String s = IChatComponent.Serializer.componentToJson(ichatcomponent);
			parPacketBuffer.writeString(s);
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayServer inethandlerplayserver) {
		inethandlerplayserver.processUpdateSign(this);
	}

	public BlockPos getPosition() {
		return this.pos;
	}

	public IChatComponent[] getLines() {
		return this.lines;
	}
}