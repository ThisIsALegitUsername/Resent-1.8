package net.minecraft.network.play.client;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

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
public class C14PacketTabComplete implements Packet<INetHandlerPlayServer> {
	private String message;
	private BlockPos targetBlock;

	public C14PacketTabComplete() {
	}

	public C14PacketTabComplete(String msg) {
		this(msg, (BlockPos) null);
	}

	public C14PacketTabComplete(String msg, BlockPos target) {
		this.message = msg;
		this.targetBlock = target;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.message = parPacketBuffer.readStringFromBuffer(32767);
		boolean flag = parPacketBuffer.readBoolean();
		if (flag) {
			this.targetBlock = parPacketBuffer.readBlockPos();
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeString(StringUtils.substring(this.message, 0, 32767));
		boolean flag = this.targetBlock != null;
		parPacketBuffer.writeBoolean(flag);
		if (flag) {
			parPacketBuffer.writeBlockPos(this.targetBlock);
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayServer inethandlerplayserver) {
		inethandlerplayserver.processTabComplete(this);
	}

	public String getMessage() {
		return this.message;
	}

	public BlockPos getTargetBlock() {
		return this.targetBlock;
	}
}