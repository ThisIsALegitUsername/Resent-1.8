package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

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
public class S2APacketParticles implements Packet<INetHandlerPlayClient> {
	private EnumParticleTypes particleType;
	private float xCoord;
	private float yCoord;
	private float zCoord;
	private float xOffset;
	private float yOffset;
	private float zOffset;
	private float particleSpeed;
	private int particleCount;
	private boolean longDistance;
	private int[] particleArguments;

	public S2APacketParticles() {
	}

	public S2APacketParticles(EnumParticleTypes particleTypeIn, boolean longDistanceIn, float x, float y, float z,
			float xOffsetIn, float yOffset, float zOffset, float particleSpeedIn, int particleCountIn,
			int... particleArgumentsIn) {
		this.particleType = particleTypeIn;
		this.longDistance = longDistanceIn;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.xOffset = xOffsetIn;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		this.particleSpeed = particleSpeedIn;
		this.particleCount = particleCountIn;
		this.particleArguments = particleArgumentsIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.particleType = EnumParticleTypes.getParticleFromId(parPacketBuffer.readInt());
		if (this.particleType == null) {
			this.particleType = EnumParticleTypes.BARRIER;
		}

		this.longDistance = parPacketBuffer.readBoolean();
		this.xCoord = parPacketBuffer.readFloat();
		this.yCoord = parPacketBuffer.readFloat();
		this.zCoord = parPacketBuffer.readFloat();
		this.xOffset = parPacketBuffer.readFloat();
		this.yOffset = parPacketBuffer.readFloat();
		this.zOffset = parPacketBuffer.readFloat();
		this.particleSpeed = parPacketBuffer.readFloat();
		this.particleCount = parPacketBuffer.readInt();
		int i = this.particleType.getArgumentCount();
		this.particleArguments = new int[i];

		for (int j = 0; j < i; ++j) {
			this.particleArguments[j] = parPacketBuffer.readVarIntFromBuffer();
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeInt(this.particleType.getParticleID());
		parPacketBuffer.writeBoolean(this.longDistance);
		parPacketBuffer.writeFloat(this.xCoord);
		parPacketBuffer.writeFloat(this.yCoord);
		parPacketBuffer.writeFloat(this.zCoord);
		parPacketBuffer.writeFloat(this.xOffset);
		parPacketBuffer.writeFloat(this.yOffset);
		parPacketBuffer.writeFloat(this.zOffset);
		parPacketBuffer.writeFloat(this.particleSpeed);
		parPacketBuffer.writeInt(this.particleCount);
		int i = this.particleType.getArgumentCount();

		for (int j = 0; j < i; ++j) {
			parPacketBuffer.writeVarIntToBuffer(this.particleArguments[j]);
		}

	}

	public EnumParticleTypes getParticleType() {
		return this.particleType;
	}

	public boolean isLongDistance() {
		return this.longDistance;
	}

	/**+
	 * Gets the x coordinate to spawn the particle.
	 */
	public double getXCoordinate() {
		return (double) this.xCoord;
	}

	/**+
	 * Gets the y coordinate to spawn the particle.
	 */
	public double getYCoordinate() {
		return (double) this.yCoord;
	}

	/**+
	 * Gets the z coordinate to spawn the particle.
	 */
	public double getZCoordinate() {
		return (double) this.zCoord;
	}

	/**+
	 * Gets the x coordinate offset for the particle. The particle
	 * may use the offset for particle spread.
	 */
	public float getXOffset() {
		return this.xOffset;
	}

	/**+
	 * Gets the y coordinate offset for the particle. The particle
	 * may use the offset for particle spread.
	 */
	public float getYOffset() {
		return this.yOffset;
	}

	/**+
	 * Gets the z coordinate offset for the particle. The particle
	 * may use the offset for particle spread.
	 */
	public float getZOffset() {
		return this.zOffset;
	}

	/**+
	 * Gets the speed of the particle animation (used in client side
	 * rendering).
	 */
	public float getParticleSpeed() {
		return this.particleSpeed;
	}

	/**+
	 * Gets the amount of particles to spawn
	 */
	public int getParticleCount() {
		return this.particleCount;
	}

	/**+
	 * Gets the particle arguments. Some particles rely on block
	 * and/or item ids and sometimes metadata ids to color or
	 * texture the particle.
	 */
	public int[] getParticleArgs() {
		return this.particleArguments;
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleParticles(this);
	}
}