package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;

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
public class S26PacketMapChunkBulk implements Packet<INetHandlerPlayClient> {
	private int[] xPositions;
	private int[] zPositions;
	private S21PacketChunkData.Extracted[] chunksData;
	private boolean isOverworld;

	public S26PacketMapChunkBulk() {
	}

	public S26PacketMapChunkBulk(List<Chunk> chunks) {
		int i = chunks.size();
		this.xPositions = new int[i];
		this.zPositions = new int[i];
		this.chunksData = new S21PacketChunkData.Extracted[i];
		this.isOverworld = !((Chunk) chunks.get(0)).getWorld().provider.getHasNoSky();

		for (int j = 0; j < i; ++j) {
			Chunk chunk = (Chunk) chunks.get(j);
			S21PacketChunkData.Extracted s21packetchunkdata$extracted = S21PacketChunkData.func_179756_a(chunk, true,
					this.isOverworld, '\uffff');
			this.xPositions[j] = chunk.xPosition;
			this.zPositions[j] = chunk.zPosition;
			this.chunksData[j] = s21packetchunkdata$extracted;
		}

	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.isOverworld = parPacketBuffer.readBoolean();
		int i = parPacketBuffer.readVarIntFromBuffer();
		this.xPositions = new int[i];
		this.zPositions = new int[i];
		this.chunksData = new S21PacketChunkData.Extracted[i];

		for (int j = 0; j < i; ++j) {
			this.xPositions[j] = parPacketBuffer.readInt();
			this.zPositions[j] = parPacketBuffer.readInt();
			this.chunksData[j] = new S21PacketChunkData.Extracted();
			this.chunksData[j].dataSize = parPacketBuffer.readShort() & '\uffff';
			this.chunksData[j].data = new byte[S21PacketChunkData
					.func_180737_a(Integer.bitCount(this.chunksData[j].dataSize), this.isOverworld, true)];
		}

		for (int k = 0; k < i; ++k) {
			parPacketBuffer.readBytes(this.chunksData[k].data);
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeBoolean(this.isOverworld);
		parPacketBuffer.writeVarIntToBuffer(this.chunksData.length);

		for (int i = 0; i < this.xPositions.length; ++i) {
			parPacketBuffer.writeInt(this.xPositions[i]);
			parPacketBuffer.writeInt(this.zPositions[i]);
			parPacketBuffer.writeShort((short) (this.chunksData[i].dataSize & '\uffff'));
		}

		for (int j = 0; j < this.xPositions.length; ++j) {
			parPacketBuffer.writeBytes(this.chunksData[j].data);
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleMapChunkBulk(this);
	}

	public int getChunkX(int parInt1) {
		return this.xPositions[parInt1];
	}

	public int getChunkZ(int parInt1) {
		return this.zPositions[parInt1];
	}

	public int getChunkCount() {
		return this.xPositions.length;
	}

	public byte[] getChunkBytes(int parInt1) {
		return this.chunksData[parInt1].data;
	}

	public int getChunkSize(int parInt1) {
		return this.chunksData[parInt1].dataSize;
	}
}