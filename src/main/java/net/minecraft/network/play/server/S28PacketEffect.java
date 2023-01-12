package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
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
public class S28PacketEffect implements Packet<INetHandlerPlayClient> {
	private int soundType;
	private BlockPos soundPos;
	private int soundData;
	private boolean serverWide;

	public S28PacketEffect() {
	}

	public S28PacketEffect(int soundTypeIn, BlockPos soundPosIn, int soundDataIn, boolean serverWideIn) {
		this.soundType = soundTypeIn;
		this.soundPos = soundPosIn;
		this.soundData = soundDataIn;
		this.serverWide = serverWideIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.soundType = parPacketBuffer.readInt();
		this.soundPos = parPacketBuffer.readBlockPos();
		this.soundData = parPacketBuffer.readInt();
		this.serverWide = parPacketBuffer.readBoolean();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeInt(this.soundType);
		parPacketBuffer.writeBlockPos(this.soundPos);
		parPacketBuffer.writeInt(this.soundData);
		parPacketBuffer.writeBoolean(this.serverWide);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleEffect(this);
	}

	public boolean isSoundServerwide() {
		return this.serverWide;
	}

	public int getSoundType() {
		return this.soundType;
	}

	public int getSoundData() {
		return this.soundData;
	}

	public BlockPos getSoundPos() {
		return this.soundPos;
	}
}