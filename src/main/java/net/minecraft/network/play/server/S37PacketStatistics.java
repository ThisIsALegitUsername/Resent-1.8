package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

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
public class S37PacketStatistics implements Packet<INetHandlerPlayClient> {
	private Map<StatBase, Integer> field_148976_a;

	public S37PacketStatistics() {
	}

	public S37PacketStatistics(Map<StatBase, Integer> parMap) {
		this.field_148976_a = parMap;
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleStatistics(this);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		int i = parPacketBuffer.readVarIntFromBuffer();
		this.field_148976_a = Maps.newHashMap();

		for (int j = 0; j < i; ++j) {
			StatBase statbase = StatList.getOneShotStat(parPacketBuffer.readStringFromBuffer(32767));
			int k = parPacketBuffer.readVarIntFromBuffer();
			if (statbase != null) {
				this.field_148976_a.put(statbase, Integer.valueOf(k));
			}
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.field_148976_a.size());

		for (Entry entry : this.field_148976_a.entrySet()) {
			parPacketBuffer.writeString(((StatBase) entry.getKey()).statId);
			parPacketBuffer.writeVarIntToBuffer(((Integer) entry.getValue()).intValue());
		}

	}

	public Map<StatBase, Integer> func_148974_c() {
		return this.field_148976_a;
	}
}