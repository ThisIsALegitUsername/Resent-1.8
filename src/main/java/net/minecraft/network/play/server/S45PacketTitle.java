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
public class S45PacketTitle implements Packet<INetHandlerPlayClient> {
	private S45PacketTitle.Type type;
	private IChatComponent message;
	private int fadeInTime;
	private int displayTime;
	private int fadeOutTime;

	public S45PacketTitle() {
	}

	public S45PacketTitle(S45PacketTitle.Type type, IChatComponent message) {
		this(type, message, -1, -1, -1);
	}

	public S45PacketTitle(int fadeInTime, int displayTime, int fadeOutTime) {
		this(S45PacketTitle.Type.TIMES, (IChatComponent) null, fadeInTime, displayTime, fadeOutTime);
	}

	public S45PacketTitle(S45PacketTitle.Type type, IChatComponent message, int fadeInTime, int displayTime,
			int fadeOutTime) {
		this.type = type;
		this.message = message;
		this.fadeInTime = fadeInTime;
		this.displayTime = displayTime;
		this.fadeOutTime = fadeOutTime;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.type = (S45PacketTitle.Type) parPacketBuffer.readEnumValue(S45PacketTitle.Type.class);
		if (this.type == S45PacketTitle.Type.TITLE || this.type == S45PacketTitle.Type.SUBTITLE) {
			this.message = parPacketBuffer.readChatComponent();
		}

		if (this.type == S45PacketTitle.Type.TIMES) {
			this.fadeInTime = parPacketBuffer.readInt();
			this.displayTime = parPacketBuffer.readInt();
			this.fadeOutTime = parPacketBuffer.readInt();
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeEnumValue(this.type);
		if (this.type == S45PacketTitle.Type.TITLE || this.type == S45PacketTitle.Type.SUBTITLE) {
			parPacketBuffer.writeChatComponent(this.message);
		}

		if (this.type == S45PacketTitle.Type.TIMES) {
			parPacketBuffer.writeInt(this.fadeInTime);
			parPacketBuffer.writeInt(this.displayTime);
			parPacketBuffer.writeInt(this.fadeOutTime);
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleTitle(this);
	}

	public S45PacketTitle.Type getType() {
		return this.type;
	}

	public IChatComponent getMessage() {
		return this.message;
	}

	public int getFadeInTime() {
		return this.fadeInTime;
	}

	public int getDisplayTime() {
		return this.displayTime;
	}

	public int getFadeOutTime() {
		return this.fadeOutTime;
	}

	public static enum Type {
		TITLE, SUBTITLE, TIMES, CLEAR, RESET;

		public static S45PacketTitle.Type byName(String name) {
			for (S45PacketTitle.Type s45packettitle$type : values()) {
				if (s45packettitle$type.name().equalsIgnoreCase(name)) {
					return s45packettitle$type;
				}
			}

			return TITLE;
		}

		public static String[] getNames() {
			String[] astring = new String[values().length];
			int i = 0;

			for (S45PacketTitle.Type s45packettitle$type : values()) {
				astring[i++] = s45packettitle$type.name().toLowerCase();
			}

			return astring;
		}
	}
}