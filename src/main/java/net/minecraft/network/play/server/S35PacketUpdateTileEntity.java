package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
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
public class S35PacketUpdateTileEntity implements Packet<INetHandlerPlayClient> {
	private BlockPos blockPos;
	private int metadata;
	private NBTTagCompound nbt;

	public S35PacketUpdateTileEntity() {
	}

	public S35PacketUpdateTileEntity(BlockPos blockPosIn, int metadataIn, NBTTagCompound nbtIn) {
		this.blockPos = blockPosIn;
		this.metadata = metadataIn;
		this.nbt = nbtIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.blockPos = parPacketBuffer.readBlockPos();
		this.metadata = parPacketBuffer.readUnsignedByte();
		this.nbt = parPacketBuffer.readNBTTagCompoundFromBuffer();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeBlockPos(this.blockPos);
		parPacketBuffer.writeByte((byte) this.metadata);
		parPacketBuffer.writeNBTTagCompoundToBuffer(this.nbt);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleUpdateTileEntity(this);
	}

	public BlockPos getPos() {
		return this.blockPos;
	}

	public int getTileEntityType() {
		return this.metadata;
	}

	public NBTTagCompound getNbtCompound() {
		return this.nbt;
	}
}