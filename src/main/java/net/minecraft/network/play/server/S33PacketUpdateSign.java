package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
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
public class S33PacketUpdateSign implements Packet<INetHandlerPlayClient> {
	private World world;
	private BlockPos blockPos;
	private IChatComponent[] lines;

	public S33PacketUpdateSign() {
	}

	public S33PacketUpdateSign(World worldIn, BlockPos blockPosIn, IChatComponent[] linesIn) {
		this.world = worldIn;
		this.blockPos = blockPosIn;
		this.lines = new IChatComponent[] { linesIn[0], linesIn[1], linesIn[2], linesIn[3] };
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.blockPos = parPacketBuffer.readBlockPos();
		this.lines = new IChatComponent[4];

		for (int i = 0; i < 4; ++i) {
			this.lines[i] = parPacketBuffer.readChatComponent();
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeBlockPos(this.blockPos);

		for (int i = 0; i < 4; ++i) {
			parPacketBuffer.writeChatComponent(this.lines[i]);
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleUpdateSign(this);
	}

	public BlockPos getPos() {
		return this.blockPos;
	}

	public IChatComponent[] getLines() {
		return this.lines;
	}
}