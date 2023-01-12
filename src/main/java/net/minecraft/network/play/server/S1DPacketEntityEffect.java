package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

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
public class S1DPacketEntityEffect implements Packet<INetHandlerPlayClient> {
	private int entityId;
	private byte effectId;
	private byte amplifier;
	private int duration;
	private byte hideParticles;

	public S1DPacketEntityEffect() {
	}

	public S1DPacketEntityEffect(int entityIdIn, PotionEffect effect) {
		this.entityId = entityIdIn;
		this.effectId = (byte) (effect.getPotionID() & 255);
		this.amplifier = (byte) (effect.getAmplifier() & 255);
		if (effect.getDuration() > 32767) {
			this.duration = 32767;
		} else {
			this.duration = effect.getDuration();
		}

		this.hideParticles = (byte) (effect.getIsShowParticles() ? 1 : 0);
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.entityId = parPacketBuffer.readVarIntFromBuffer();
		this.effectId = parPacketBuffer.readByte();
		this.amplifier = parPacketBuffer.readByte();
		this.duration = parPacketBuffer.readVarIntFromBuffer();
		this.hideParticles = parPacketBuffer.readByte();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.entityId);
		parPacketBuffer.writeByte(this.effectId);
		parPacketBuffer.writeByte(this.amplifier);
		parPacketBuffer.writeVarIntToBuffer(this.duration);
		parPacketBuffer.writeByte(this.hideParticles);
	}

	public boolean func_149429_c() {
		return this.duration == 32767;
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleEntityEffect(this);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public byte getEffectId() {
		return this.effectId;
	}

	public byte getAmplifier() {
		return this.amplifier;
	}

	public int getDuration() {
		return this.duration;
	}

	public boolean func_179707_f() {
		return this.hideParticles != 0;
	}
}