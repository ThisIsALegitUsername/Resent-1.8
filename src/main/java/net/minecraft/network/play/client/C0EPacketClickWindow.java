package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

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
public class C0EPacketClickWindow implements Packet<INetHandlerPlayServer> {
	private int windowId;
	private int slotId;
	private int usedButton;
	private short actionNumber;
	private ItemStack clickedItem;
	private int mode;

	public C0EPacketClickWindow() {
	}

	public C0EPacketClickWindow(int windowId, int slotId, int usedButton, int mode, ItemStack clickedItem,
			short actionNumber) {
		this.windowId = windowId;
		this.slotId = slotId;
		this.usedButton = usedButton;
		this.clickedItem = clickedItem != null ? clickedItem.copy() : null;
		this.actionNumber = actionNumber;
		this.mode = mode;
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayServer inethandlerplayserver) {
		inethandlerplayserver.processClickWindow(this);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.windowId = parPacketBuffer.readByte();
		this.slotId = parPacketBuffer.readShort();
		this.usedButton = parPacketBuffer.readByte();
		this.actionNumber = parPacketBuffer.readShort();
		this.mode = parPacketBuffer.readByte();
		this.clickedItem = parPacketBuffer.readItemStackFromBuffer();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeByte(this.windowId);
		parPacketBuffer.writeShort(this.slotId);
		parPacketBuffer.writeByte(this.usedButton);
		parPacketBuffer.writeShort(this.actionNumber);
		parPacketBuffer.writeByte(this.mode);
		parPacketBuffer.writeItemStackToBuffer(this.clickedItem);
	}

	public int getWindowId() {
		return this.windowId;
	}

	public int getSlotId() {
		return this.slotId;
	}

	public int getUsedButton() {
		return this.usedButton;
	}

	public short getActionNumber() {
		return this.actionNumber;
	}

	public ItemStack getClickedItem() {
		return this.clickedItem;
	}

	public int getMode() {
		return this.mode;
	}
}