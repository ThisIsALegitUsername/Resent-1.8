package net.minecraft.network.login.server;

import java.io.IOException;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import net.lax1dude.eaglercraft.v1_8.mojang.authlib.GameProfile;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

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
public class S02PacketLoginSuccess implements Packet<INetHandlerLoginClient> {
	private GameProfile profile;

	public S02PacketLoginSuccess() {
	}

	public S02PacketLoginSuccess(GameProfile profileIn) {
		this.profile = profileIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		String s = parPacketBuffer.readStringFromBuffer(36);
		String s1 = parPacketBuffer.readStringFromBuffer(16);
		EaglercraftUUID uuid = EaglercraftUUID.fromString(s);
		this.profile = new GameProfile(uuid, s1);
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		EaglercraftUUID uuid = this.profile.getId();
		parPacketBuffer.writeString(uuid == null ? "" : uuid.toString());
		parPacketBuffer.writeString(this.profile.getName());
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerLoginClient inethandlerloginclient) {
		inethandlerloginclient.handleLoginSuccess(this);
	}

	public GameProfile getProfile() {
		return this.profile;
	}
}