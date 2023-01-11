package net.minecraft.network.status.server;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.json.JSONTypeProvider;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

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
public class S00PacketServerInfo implements Packet<INetHandlerStatusClient> {

	private ServerStatusResponse response;

	public S00PacketServerInfo() {
	}

	public S00PacketServerInfo(ServerStatusResponse responseIn) {
		this.response = responseIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		try {
			this.response = (ServerStatusResponse) JSONTypeProvider.deserialize(
					new JSONObject(parPacketBuffer.readStringFromBuffer(32767)), ServerStatusResponse.class);
		} catch (JSONException exc) {
			throw new IOException("Invalid ServerStatusResponse JSON payload", exc);
		}
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		try {
			parPacketBuffer.writeString(((JSONObject) JSONTypeProvider.serialize(this.response)).toString());
		} catch (JSONException exc) {
			throw new IOException("Invalid ServerStatusResponse JSON payload", exc);
		}
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerStatusClient inethandlerstatusclient) {
		inethandlerstatusclient.handleServerInfo(this);
	}

	public ServerStatusResponse getResponse() {
		return this.response;
	}
}