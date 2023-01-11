package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.block.Block;
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
public class S24PacketBlockAction implements Packet<INetHandlerPlayClient> {
	private BlockPos blockPosition;
	private int instrument;
	private int pitch;
	private Block block;

	public S24PacketBlockAction() {
	}

	public S24PacketBlockAction(BlockPos blockPositionIn, Block blockIn, int instrumentIn, int pitchIn) {
		this.blockPosition = blockPositionIn;
		this.instrument = instrumentIn;
		this.pitch = pitchIn;
		this.block = blockIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.blockPosition = parPacketBuffer.readBlockPos();
		this.instrument = parPacketBuffer.readUnsignedByte();
		this.pitch = parPacketBuffer.readUnsignedByte();
		this.block = Block.getBlockById(parPacketBuffer.readVarIntFromBuffer() & 4095);
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeBlockPos(this.blockPosition);
		parPacketBuffer.writeByte(this.instrument);
		parPacketBuffer.writeByte(this.pitch);
		parPacketBuffer.writeVarIntToBuffer(Block.getIdFromBlock(this.block) & 4095);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleBlockAction(this);
	}

	public BlockPos getBlockPosition() {
		return this.blockPosition;
	}

	/**+
	 * instrument data for noteblocks
	 */
	public int getData1() {
		return this.instrument;
	}

	/**+
	 * pitch data for noteblocks
	 */
	public int getData2() {
		return this.pitch;
	}

	public Block getBlockType() {
		return this.block;
	}
}