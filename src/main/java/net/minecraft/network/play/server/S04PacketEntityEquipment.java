package net.minecraft.network.play.server;

import java.io.IOException;

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
public class S04PacketEntityEquipment implements Packet<INetHandlerPlayClient> {
	private int entityID;
	private int equipmentSlot;
	private ItemStack itemStack;

	public S04PacketEntityEquipment() {
	}

	public S04PacketEntityEquipment(int entityIDIn, int parInt1, ItemStack itemStackIn) {
		this.entityID = entityIDIn;
		this.equipmentSlot = parInt1;
		this.itemStack = itemStackIn == null ? null : itemStackIn.copy();
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityID = parPacketBuffer.readVarIntFromBuffer();
		this.equipmentSlot = parPacketBuffer.readShort();
		this.itemStack = parPacketBuffer.readItemStackFromBuffer();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityID);
		parPacketBuffer.writeShort(this.equipmentSlot);
		parPacketBuffer.writeItemStackToBuffer(this.itemStack);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleEntityEquipment(this);
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	public int getEntityID() {
		return this.entityID;
	}

	public int getEquipmentSlot() {
		return this.equipmentSlot;
	}
}