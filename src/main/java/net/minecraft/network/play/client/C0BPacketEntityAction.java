package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.entity.Entity;
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
public class C0BPacketEntityAction implements Packet<INetHandlerPlayServer> {
	private int entityID;
	private C0BPacketEntityAction.Action action;
	private int auxData;

	public C0BPacketEntityAction() {
	}

	public C0BPacketEntityAction(Entity entity, C0BPacketEntityAction.Action action) {
		this(entity, action, 0);
	}

	public C0BPacketEntityAction(Entity entity, C0BPacketEntityAction.Action action, int auxData) {
		this.entityID = entity.getEntityId();
		this.action = action;
		this.auxData = auxData;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityID = parPacketBuffer.readVarIntFromBuffer();
		this.action = (C0BPacketEntityAction.Action) parPacketBuffer.readEnumValue(C0BPacketEntityAction.Action.class);
		this.auxData = parPacketBuffer.readVarIntFromBuffer();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityID);
		parPacketBuffer.writeEnumValue(this.action);
		parPacketBuffer.writeVarIntToBuffer(this.auxData);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayServer inethandlerplayserver) {
		inethandlerplayserver.processEntityAction(this);
	}

	public C0BPacketEntityAction.Action getAction() {
		return this.action;
	}

	public int getAuxData() {
		return this.auxData;
	}

	public static enum Action {
		START_SNEAKING, STOP_SNEAKING, STOP_SLEEPING, START_SPRINTING, STOP_SPRINTING, RIDING_JUMP, OPEN_INVENTORY;
	}
}