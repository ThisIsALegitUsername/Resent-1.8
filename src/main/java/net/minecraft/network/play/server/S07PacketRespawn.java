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
public class S07PacketRespawn implements Packet<INetHandlerPlayClient> {
	private int dimensionID;
	private EnumDifficulty difficulty;
	private WorldSettings.GameType gameType;
	private WorldType worldType;

	public S07PacketRespawn() {
	}

	public S07PacketRespawn(int dimensionIDIn, EnumDifficulty difficultyIn, WorldType worldTypeIn,
			WorldSettings.GameType gameTypeIn) {
		this.dimensionID = dimensionIDIn;
		this.difficulty = difficultyIn;
		this.gameType = gameTypeIn;
		this.worldType = worldTypeIn;
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleRespawn(this);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.dimensionID = parPacketBuffer.readInt();
		this.difficulty = EnumDifficulty.getDifficultyEnum(parPacketBuffer.readUnsignedByte());
		this.gameType = WorldSettings.GameType.getByID(parPacketBuffer.readUnsignedByte());
		this.worldType = WorldType.parseWorldType(parPacketBuffer.readStringFromBuffer(16));
		if (this.worldType == null) {
			this.worldType = WorldType.DEFAULT;
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeInt(this.dimensionID);
		parPacketBuffer.writeByte(this.difficulty.getDifficultyId());
		parPacketBuffer.writeByte(this.gameType.getID());
		parPacketBuffer.writeString(this.worldType.getWorldTypeName());
	}

	public int getDimensionID() {
		return this.dimensionID;
	}

	public EnumDifficulty getDifficulty() {
		return this.difficulty;
	}

	public WorldSettings.GameType getGameType() {
		return this.gameType;
	}

	public WorldType getWorldType() {
		return this.worldType;
	}
}