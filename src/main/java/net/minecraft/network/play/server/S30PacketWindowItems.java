package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;

import net.minecraft.item.ItemStack;
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
public class S30PacketWindowItems implements Packet<INetHandlerPlayClient> {
	private int windowId;
	private ItemStack[] itemStacks;

	public S30PacketWindowItems() {
	}

	public S30PacketWindowItems(int windowIdIn, List<ItemStack> parList) {
		this.windowId = windowIdIn;
		this.itemStacks = new ItemStack[parList.size()];

		for (int i = 0; i < this.itemStacks.length; ++i) {
			ItemStack itemstack = (ItemStack) parList.get(i);
			this.itemStacks[i] = itemstack == null ? null : itemstack.copy();
		}

	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.windowId = parPacketBuffer.readUnsignedByte();
		short short1 = parPacketBuffer.readShort();
		this.itemStacks = new ItemStack[short1];

		for (int i = 0; i < short1; ++i) {
			this.itemStacks[i] = parPacketBuffer.readItemStackFromBuffer();
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeByte(this.windowId);
		parPacketBuffer.writeShort(this.itemStacks.length);

		for (ItemStack itemstack : this.itemStacks) {
			parPacketBuffer.writeItemStackToBuffer(itemstack);
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleWindowItems(this);
	}

	public int func_148911_c() {
		return this.windowId;
	}

	public ItemStack[] getItemStacks() {
		return this.itemStacks;
	}
}