package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
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
public class S22PacketMultiBlockChange implements Packet<INetHandlerPlayClient> {
	private ChunkCoordIntPair chunkPosCoord;
	private S22PacketMultiBlockChange.BlockUpdateData[] changedBlocks;

	public S22PacketMultiBlockChange() {
	}

	public S22PacketMultiBlockChange(int parInt1, short[] crammedPositionsIn, Chunk chunkIn) {
		this.chunkPosCoord = new ChunkCoordIntPair(chunkIn.xPosition, chunkIn.zPosition);
		this.changedBlocks = new S22PacketMultiBlockChange.BlockUpdateData[parInt1];

		for (int i = 0; i < this.changedBlocks.length; ++i) {
			this.changedBlocks[i] = new S22PacketMultiBlockChange.BlockUpdateData(crammedPositionsIn[i], chunkIn);
		}

	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.chunkPosCoord = new ChunkCoordIntPair(parPacketBuffer.readInt(), parPacketBuffer.readInt());
		this.changedBlocks = new S22PacketMultiBlockChange.BlockUpdateData[parPacketBuffer.readVarIntFromBuffer()];

		for (int i = 0; i < this.changedBlocks.length; ++i) {
			this.changedBlocks[i] = new S22PacketMultiBlockChange.BlockUpdateData(parPacketBuffer.readShort(),
					(IBlockState) Block.BLOCK_STATE_IDS.getByValue(parPacketBuffer.readVarIntFromBuffer()));
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeInt(this.chunkPosCoord.chunkXPos);
		parPacketBuffer.writeInt(this.chunkPosCoord.chunkZPos);
		parPacketBuffer.writeVarIntToBuffer(this.changedBlocks.length);

		for (S22PacketMultiBlockChange.BlockUpdateData s22packetmultiblockchange$blockupdatedata : this.changedBlocks) {
			parPacketBuffer.writeShort(s22packetmultiblockchange$blockupdatedata.func_180089_b());
			parPacketBuffer.writeVarIntToBuffer(
					Block.BLOCK_STATE_IDS.get(s22packetmultiblockchange$blockupdatedata.getBlockState()));
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleMultiBlockChange(this);
	}

	public S22PacketMultiBlockChange.BlockUpdateData[] getChangedBlocks() {
		return this.changedBlocks;
	}

	public class BlockUpdateData {
		private final short chunkPosCrammed;
		private final IBlockState blockState;

		public BlockUpdateData(short parShort1, IBlockState state) {
			this.chunkPosCrammed = parShort1;
			this.blockState = state;
		}

		public BlockUpdateData(short parShort1, Chunk chunkIn) {
			this.chunkPosCrammed = parShort1;
			this.blockState = chunkIn.getBlockState(this.getPos());
		}

		public BlockPos getPos() {
			return new BlockPos(S22PacketMultiBlockChange.this.chunkPosCoord.getBlock(this.chunkPosCrammed >> 12 & 15,
					this.chunkPosCrammed & 255, this.chunkPosCrammed >> 8 & 15));
		}

		public short func_180089_b() {
			return this.chunkPosCrammed;
		}

		public IBlockState getBlockState() {
			return this.blockState;
		}
	}
}