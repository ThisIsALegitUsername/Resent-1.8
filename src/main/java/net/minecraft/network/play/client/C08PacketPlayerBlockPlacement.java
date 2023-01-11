package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

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
public class C08PacketPlayerBlockPlacement implements Packet<INetHandlerPlayServer> {
	private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
	private BlockPos position;
	private int placedBlockDirection;
	private ItemStack stack;
	private float facingX;
	private float facingY;
	private float facingZ;

	public C08PacketPlayerBlockPlacement() {
	}

	public C08PacketPlayerBlockPlacement(ItemStack stackIn) {
		this(field_179726_a, 255, stackIn, 0.0F, 0.0F, 0.0F);
	}

	public C08PacketPlayerBlockPlacement(BlockPos positionIn, int placedBlockDirectionIn, ItemStack stackIn,
			float facingXIn, float facingYIn, float facingZIn) {
		this.position = positionIn;
		this.placedBlockDirection = placedBlockDirectionIn;
		this.stack = stackIn != null ? stackIn.copy() : null;
		this.facingX = facingXIn;
		this.facingY = facingYIn;
		this.facingZ = facingZIn;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.position = parPacketBuffer.readBlockPos();
		this.placedBlockDirection = parPacketBuffer.readUnsignedByte();
		this.stack = parPacketBuffer.readItemStackFromBuffer();
		this.facingX = (float) parPacketBuffer.readUnsignedByte() / 16.0F;
		this.facingY = (float) parPacketBuffer.readUnsignedByte() / 16.0F;
		this.facingZ = (float) parPacketBuffer.readUnsignedByte() / 16.0F;
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeBlockPos(this.position);
		parPacketBuffer.writeByte(this.placedBlockDirection);
		parPacketBuffer.writeItemStackToBuffer(this.stack);
		parPacketBuffer.writeByte((int) (this.facingX * 16.0F));
		parPacketBuffer.writeByte((int) (this.facingY * 16.0F));
		parPacketBuffer.writeByte((int) (this.facingZ * 16.0F));
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayServer inethandlerplayserver) {
		inethandlerplayserver.processPlayerBlockPlacement(this);
	}

	public BlockPos getPosition() {
		return this.position;
	}

	public int getPlacedBlockDirection() {
		return this.placedBlockDirection;
	}

	public ItemStack getStack() {
		return this.stack;
	}

	/**+
	 * Returns the offset from xPosition where the actual click took
	 * place.
	 */
	public float getPlacedBlockOffsetX() {
		return this.facingX;
	}

	/**+
	 * Returns the offset from yPosition where the actual click took
	 * place.
	 */
	public float getPlacedBlockOffsetY() {
		return this.facingY;
	}

	/**+
	 * Returns the offset from zPosition where the actual click took
	 * place.
	 */
	public float getPlacedBlockOffsetZ() {
		return this.facingZ;
	}
}