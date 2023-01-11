package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;

import net.minecraft.entity.DataWatcher;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

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
public class S1CPacketEntityMetadata implements Packet<INetHandlerPlayClient> {
	private int entityId;
	private List<DataWatcher.WatchableObject> field_149378_b;

	public S1CPacketEntityMetadata() {
	}

	public S1CPacketEntityMetadata(int entityIdIn, DataWatcher parDataWatcher, boolean parFlag) {
		this.entityId = entityIdIn;
		if (parFlag) {
			this.field_149378_b = parDataWatcher.getAllWatched();
		} else {
			this.field_149378_b = parDataWatcher.getChanged();
		}

	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityId = parPacketBuffer.readVarIntFromBuffer();
		this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(parPacketBuffer);
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityId);
		DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, parPacketBuffer);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleEntityMetadata(this);
	}

	public List<DataWatcher.WatchableObject> func_149376_c() {
		return this.field_149378_b;
	}

	public int getEntityId() {
		return this.entityId;
	}
}