package net.minecraft.tileentity;

import org.json.JSONException;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
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
public class TileEntitySign extends TileEntity {
	public final IChatComponent[] signText = new IChatComponent[] { new ChatComponentText(""),
			new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("") };
	/**+
	 * The index of the line currently being edited. Only used on
	 * client side, but defined on both. Note this is only really
	 * used when the > < are going to be visible.
	 */
	public int lineBeingEdited = -1;
	private boolean isEditable = true;
	private EntityPlayer player;

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		for (int i = 0; i < 4; ++i) {
			String s = IChatComponent.Serializer.componentToJson(this.signText[i]);
			nbttagcompound.setString("Text" + (i + 1), s);
		}

	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.isEditable = false;
		super.readFromNBT(nbttagcompound);
		ICommandSender icommandsender = new ICommandSender() {
			public String getName() {
				return "Sign";
			}

			public IChatComponent getDisplayName() {
				return new ChatComponentText(this.getName());
			}

			public void addChatMessage(IChatComponent var1) {
			}

			public boolean canCommandSenderUseCommand(int var1, String var2) {
				return true;
			}

			public BlockPos getPosition() {
				return TileEntitySign.this.pos;
			}

			public Vec3 getPositionVector() {
				return new Vec3((double) TileEntitySign.this.pos.getX() + 0.5D,
						(double) TileEntitySign.this.pos.getY() + 0.5D, (double) TileEntitySign.this.pos.getZ() + 0.5D);
			}

			public World getEntityWorld() {
				return TileEntitySign.this.worldObj;
			}

			public Entity getCommandSenderEntity() {
				return null;
			}

			public boolean sendCommandFeedback() {
				return false;
			}

		};

		for (int i = 0; i < 4; ++i) {
			String s = nbttagcompound.getString("Text" + (i + 1));

			try {
				IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);

				try {
					this.signText[i] = ChatComponentProcessor.processComponent(icommandsender, ichatcomponent,
							(Entity) null);
				} catch (CommandException var7) {
					this.signText[i] = ichatcomponent;
				}
			} catch (JSONException var8) {
				this.signText[i] = new ChatComponentText(s);
			}
		}

	}

	/**+
	 * Allows for a specialized description packet to be created.
	 * This is often used to sync tile entity data from the server
	 * to the client easily. For example this is used by signs to
	 * synchronise the text to be displayed.
	 */
	public Packet getDescriptionPacket() {
		IChatComponent[] aichatcomponent = new IChatComponent[4];
		System.arraycopy(this.signText, 0, aichatcomponent, 0, 4);
		return new S33PacketUpdateSign(this.worldObj, this.pos, aichatcomponent);
	}

	public boolean func_183000_F() {
		return true;
	}

	public boolean getIsEditable() {
		return this.isEditable;
	}

	/**+
	 * Sets the sign's isEditable flag to the specified parameter.
	 */
	public void setEditable(boolean isEditableIn) {
		this.isEditable = isEditableIn;
		if (!isEditableIn) {
			this.player = null;
		}

	}

	public void setPlayer(EntityPlayer playerIn) {
		this.player = playerIn;
	}

	public EntityPlayer getPlayer() {
		return this.player;
	}

	public boolean executeCommand(final EntityPlayer playerIn) {
		return true;
	}

}