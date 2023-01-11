package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

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
public class S01PacketJoinGame implements Packet<INetHandlerPlayClient> {
	private int entityId;
	private boolean hardcoreMode;
	private WorldSettings.GameType gameType;
	private int dimension;
	private EnumDifficulty difficulty;
	private int maxPlayers;
	private WorldType worldType;
	private boolean reducedDebugInfo;

	public S01PacketJoinGame() {
	}

	public S01PacketJoinGame(int entityIdIn, WorldSettings.GameType gameTypeIn, boolean hardcoreModeIn, int dimensionIn,
			EnumDifficulty difficultyIn, int maxPlayersIn, WorldType worldTypeIn, boolean reducedDebugInfoIn) {
		this.entityId = entityIdIn;
		this.dimension = dimensionIn;
		this.difficulty = difficultyIn;
		this.gameType = gameTypeIn;
		this.maxPlayers = maxPlayersIn;
		this.hardcoreMode = hardcoreModeIn;
		this.worldType = worldTypeIn;
		this.reducedDebugInfo = reducedDebugInfoIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityId = parPacketBuffer.readInt();
		int i = parPacketBuffer.readUnsignedByte();
		this.hardcoreMode = (i & 8) == 8;
		i = i & -9;
		this.gameType = WorldSettings.GameType.getByID(i);
		this.dimension = parPacketBuffer.readByte();
		this.difficulty = EnumDifficulty.getDifficultyEnum(parPacketBuffer.readUnsignedByte());
		this.maxPlayers = parPacketBuffer.readUnsignedByte();
		this.worldType = WorldType.parseWorldType(parPacketBuffer.readStringFromBuffer(16));
		if (this.worldType == null) {
			this.worldType = WorldType.DEFAULT;
		}

		this.reducedDebugInfo = parPacketBuffer.readBoolean();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeInt(this.entityId);
		int i = this.gameType.getID();
		if (this.hardcoreMode) {
			i |= 8;
		}

		parPacketBuffer.writeByte(i);
		parPacketBuffer.writeByte(this.dimension);
		parPacketBuffer.writeByte(this.difficulty.getDifficultyId());
		parPacketBuffer.writeByte(this.maxPlayers);
		parPacketBuffer.writeString(this.worldType.getWorldTypeName());
		parPacketBuffer.writeBoolean(this.reducedDebugInfo);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleJoinGame(this);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public boolean isHardcoreMode() {
		return this.hardcoreMode;
	}

	public WorldSettings.GameType getGameType() {
		return this.gameType;
	}

	public int getDimension() {
		return this.dimension;
	}

	public EnumDifficulty getDifficulty() {
		return this.difficulty;
	}

	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	public WorldType getWorldType() {
		return this.worldType;
	}

	public boolean isReducedDebugInfo() {
		return this.reducedDebugInfo;
	}
}