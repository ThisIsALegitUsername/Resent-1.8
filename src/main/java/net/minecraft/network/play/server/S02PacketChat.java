package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
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
public class S02PacketChat implements Packet<INetHandlerPlayClient> {
	private IChatComponent chatComponent;
	private byte type;

	public S02PacketChat() {
	}

	public S02PacketChat(IChatComponent component) {
		this(component, (byte) 1);
	}

	public S02PacketChat(IChatComponent message, byte typeIn) {
		this.chatComponent = message;
		this.type = typeIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.chatComponent = parPacketBuffer.readChatComponent();
		this.type = parPacketBuffer.readByte();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeChatComponent(this.chatComponent);
		parPacketBuffer.writeByte(this.type);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleChat(this);
	}

	public IChatComponent getChatComponent() {
		return this.chatComponent;
	}

	public boolean isChat() {
		return this.type == 1 || this.type == 2;
	}

	/**+
	 * Returns the id of the area to display the text, 2 for above
	 * the action bar, anything else currently for the chat window
	 */
	public byte getType() {
		return this.type;
	}
}