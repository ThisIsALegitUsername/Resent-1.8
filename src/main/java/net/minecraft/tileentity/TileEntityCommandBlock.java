package net.minecraft.tileentity;

import net.lax1dude.eaglercraft.v1_8.netty.ByteBuf;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
public class TileEntityCommandBlock extends TileEntity {
	private final CommandBlockLogic commandBlockLogic = new CommandBlockLogic() {
		public BlockPos getPosition() {
			return TileEntityCommandBlock.this.pos;
		}

		public Vec3 getPositionVector() {
			return new Vec3((double) TileEntityCommandBlock.this.pos.getX() + 0.5D,
					(double) TileEntityCommandBlock.this.pos.getY() + 0.5D,
					(double) TileEntityCommandBlock.this.pos.getZ() + 0.5D);
		}

		public World getEntityWorld() {
			return TileEntityCommandBlock.this.getWorld();
		}

		public void setCommand(String s) {
			super.setCommand(s);
			TileEntityCommandBlock.this.markDirty();
		}

		public void updateCommand() {
			TileEntityCommandBlock.this.getWorld().markBlockForUpdate(TileEntityCommandBlock.this.pos);
		}

		public int func_145751_f() {
			return 0;
		}

		public void func_145757_a(ByteBuf bytebuf) {
			bytebuf.writeInt(TileEntityCommandBlock.this.pos.getX());
			bytebuf.writeInt(TileEntityCommandBlock.this.pos.getY());
			bytebuf.writeInt(TileEntityCommandBlock.this.pos.getZ());
		}

		public Entity getCommandSenderEntity() {
			return null;
		}
	};

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		this.commandBlockLogic.writeDataToNBT(nbttagcompound);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.commandBlockLogic.readDataFromNBT(nbttagcompound);
	}

	/**+
	 * Allows for a specialized description packet to be created.
	 * This is often used to sync tile entity data from the server
	 * to the client easily. For example this is used by signs to
	 * synchronise the text to be displayed.
	 */
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.pos, 2, nbttagcompound);
	}

	public boolean func_183000_F() {
		return true;
	}

	public CommandBlockLogic getCommandBlockLogic() {
		return this.commandBlockLogic;
	}

}