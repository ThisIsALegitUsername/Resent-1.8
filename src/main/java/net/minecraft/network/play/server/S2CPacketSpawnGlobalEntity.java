package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

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
public class S2CPacketSpawnGlobalEntity implements Packet<INetHandlerPlayClient> {
	private int entityId;
	private int x;
	private int y;
	private int z;
	private int type;

	public S2CPacketSpawnGlobalEntity() {
	}

	public S2CPacketSpawnGlobalEntity(Entity entityIn) {
		this.entityId = entityIn.getEntityId();
		this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
		this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
		this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
		if (entityIn instanceof EntityLightningBolt) {
			this.type = 1;
		}

	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityId = parPacketBuffer.readVarIntFromBuffer();
		this.type = parPacketBuffer.readByte();
		this.x = parPacketBuffer.readInt();
		this.y = parPacketBuffer.readInt();
		this.z = parPacketBuffer.readInt();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityId);
		parPacketBuffer.writeByte(this.type);
		parPacketBuffer.writeInt(this.x);
		parPacketBuffer.writeInt(this.y);
		parPacketBuffer.writeInt(this.z);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleSpawnGlobalEntity(this);
	}

	public int func_149052_c() {
		return this.entityId;
	}

	public int func_149051_d() {
		return this.x;
	}

	public int func_149050_e() {
		return this.y;
	}

	public int func_149049_f() {
		return this.z;
	}

	public int func_149053_g() {
		return this.type;
	}
}