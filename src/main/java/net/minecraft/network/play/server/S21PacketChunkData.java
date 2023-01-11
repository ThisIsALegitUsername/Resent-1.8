package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

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
public class S21PacketChunkData implements Packet<INetHandlerPlayClient> {
	private int chunkX;
	private int chunkZ;
	private S21PacketChunkData.Extracted extractedData;
	private boolean field_149279_g;

	public S21PacketChunkData() {
	}

	public S21PacketChunkData(Chunk chunkIn, boolean parFlag, int parInt1) {
		this.chunkX = chunkIn.xPosition;
		this.chunkZ = chunkIn.zPosition;
		this.field_149279_g = parFlag;
		this.extractedData = func_179756_a(chunkIn, parFlag, !chunkIn.getWorld().provider.getHasNoSky(), parInt1);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.chunkX = parPacketBuffer.readInt();
		this.chunkZ = parPacketBuffer.readInt();
		this.field_149279_g = parPacketBuffer.readBoolean();
		this.extractedData = new S21PacketChunkData.Extracted();
		this.extractedData.dataSize = parPacketBuffer.readShort();
		this.extractedData.data = parPacketBuffer.readByteArray();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeInt(this.chunkX);
		parPacketBuffer.writeInt(this.chunkZ);
		parPacketBuffer.writeBoolean(this.field_149279_g);
		parPacketBuffer.writeShort((short) (this.extractedData.dataSize & '\uffff'));
		parPacketBuffer.writeByteArray(this.extractedData.data);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleChunkData(this);
	}

	public byte[] func_149272_d() {
		return this.extractedData.data;
	}

	protected static int func_180737_a(int parInt1, boolean parFlag, boolean parFlag2) {
		int i = parInt1 * 2 * 16 * 16 * 16;
		int j = parInt1 * 16 * 16 * 16 / 2;
		int k = parFlag ? parInt1 * 16 * 16 * 16 / 2 : 0;
		int l = parFlag2 ? 256 : 0;
		return i + j + k + l;
	}

	public static S21PacketChunkData.Extracted func_179756_a(Chunk parChunk, boolean parFlag, boolean parFlag2,
			int parInt1) {
		ExtendedBlockStorage[] aextendedblockstorage = parChunk.getBlockStorageArray();
		S21PacketChunkData.Extracted s21packetchunkdata$extracted = new S21PacketChunkData.Extracted();
		ArrayList arraylist = Lists.newArrayList();

		for (int i = 0; i < aextendedblockstorage.length; ++i) {
			ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[i];
			if (extendedblockstorage != null && (!parFlag || !extendedblockstorage.isEmpty())
					&& (parInt1 & 1 << i) != 0) {
				s21packetchunkdata$extracted.dataSize |= 1 << i;
				arraylist.add(extendedblockstorage);
			}
		}

		s21packetchunkdata$extracted.data = new byte[func_180737_a(
				Integer.bitCount(s21packetchunkdata$extracted.dataSize), parFlag2, parFlag)];
		int j = 0;

		for (ExtendedBlockStorage extendedblockstorage1 : (ArrayList<ExtendedBlockStorage>) arraylist) {
			char[] achar = extendedblockstorage1.getData();

			for (char c0 : achar) {
				s21packetchunkdata$extracted.data[j++] = (byte) (c0 & 255);
				s21packetchunkdata$extracted.data[j++] = (byte) (c0 >> 8 & 255);
			}
		}

		for (ExtendedBlockStorage extendedblockstorage2 : (ArrayList<ExtendedBlockStorage>) arraylist) {
			j = func_179757_a(extendedblockstorage2.getBlocklightArray().getData(), s21packetchunkdata$extracted.data,
					j);
		}

		if (parFlag2) {
			for (ExtendedBlockStorage extendedblockstorage3 : (ArrayList<ExtendedBlockStorage>) arraylist) {
				j = func_179757_a(extendedblockstorage3.getSkylightArray().getData(), s21packetchunkdata$extracted.data,
						j);
			}
		}

		if (parFlag) {
			func_179757_a(parChunk.getBiomeArray(), s21packetchunkdata$extracted.data, j);
		}

		return s21packetchunkdata$extracted;
	}

	private static int func_179757_a(byte[] parArrayOfByte, byte[] parArrayOfByte2, int parInt1) {
		System.arraycopy(parArrayOfByte, 0, parArrayOfByte2, parInt1, parArrayOfByte.length);
		return parInt1 + parArrayOfByte.length;
	}

	public int getChunkX() {
		return this.chunkX;
	}

	public int getChunkZ() {
		return this.chunkZ;
	}

	public int getExtractedSize() {
		return this.extractedData.dataSize;
	}

	public boolean func_149274_i() {
		return this.field_149279_g;
	}

	public static class Extracted {
		public byte[] data;
		public int dataSize;
	}
}