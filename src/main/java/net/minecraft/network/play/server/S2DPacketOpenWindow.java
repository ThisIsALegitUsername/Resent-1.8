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
public class S2DPacketOpenWindow implements Packet<INetHandlerPlayClient> {
	private int windowId;
	private String inventoryType;
	private IChatComponent windowTitle;
	private int slotCount;
	private int entityId;

	public S2DPacketOpenWindow() {
	}

	public S2DPacketOpenWindow(int incomingWindowId, String incomingWindowTitle, IChatComponent windowTitleIn) {
		this(incomingWindowId, incomingWindowTitle, windowTitleIn, 0);
	}

	public S2DPacketOpenWindow(int windowIdIn, String guiId, IChatComponent windowTitleIn, int slotCountIn) {
		this.windowId = windowIdIn;
		this.inventoryType = guiId;
		this.windowTitle = windowTitleIn;
		this.slotCount = slotCountIn;
	}

	public S2DPacketOpenWindow(int windowIdIn, String guiId, IChatComponent windowTitleIn, int slotCountIn,
			int incomingEntityId) {
		this(windowIdIn, guiId, windowTitleIn, slotCountIn);
		this.entityId = incomingEntityId;
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleOpenWindow(this);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.windowId = parPacketBuffer.readUnsignedByte();
		this.inventoryType = parPacketBuffer.readStringFromBuffer(32);
		this.windowTitle = parPacketBuffer.readChatComponent();
		this.slotCount = parPacketBuffer.readUnsignedByte();
		if (this.inventoryType.equals("EntityHorse")) {
			this.entityId = parPacketBuffer.readInt();
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeByte(this.windowId);
		parPacketBuffer.writeString(this.inventoryType);
		parPacketBuffer.writeChatComponent(this.windowTitle);
		parPacketBuffer.writeByte(this.slotCount);
		if (this.inventoryType.equals("EntityHorse")) {
			parPacketBuffer.writeInt(this.entityId);
		}

	}

	public int getWindowId() {
		return this.windowId;
	}

	public String getGuiId() {
		return this.inventoryType;
	}

	public IChatComponent getWindowTitle() {
		return this.windowTitle;
	}

	public int getSlotCount() {
		return this.slotCount;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public boolean hasSlots() {
		return this.slotCount > 0;
	}
}