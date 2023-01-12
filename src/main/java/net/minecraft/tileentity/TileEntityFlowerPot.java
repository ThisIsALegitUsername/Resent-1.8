package net.minecraft.tileentity;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;

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
public class TileEntityFlowerPot extends TileEntity {
	private Item flowerPotItem;
	private int flowerPotData;

	public TileEntityFlowerPot() {
	}

	public TileEntityFlowerPot(Item potItem, int potData) {
		this.flowerPotItem = potItem;
		this.flowerPotData = potData;
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		ResourceLocation resourcelocation = (ResourceLocation) Item.itemRegistry.getNameForObject(this.flowerPotItem);
		nbttagcompound.setString("Item", resourcelocation == null ? "" : resourcelocation.toString());
		nbttagcompound.setInteger("Data", this.flowerPotData);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		if (nbttagcompound.hasKey("Item", 8)) {
			this.flowerPotItem = Item.getByNameOrId(nbttagcompound.getString("Item"));
		} else {
			this.flowerPotItem = Item.getItemById(nbttagcompound.getInteger("Item"));
		}

		this.flowerPotData = nbttagcompound.getInteger("Data");
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
		nbttagcompound.removeTag("Item");
		nbttagcompound.setInteger("Item", Item.getIdFromItem(this.flowerPotItem));
		return new S35PacketUpdateTileEntity(this.pos, 5, nbttagcompound);
	}

	public void setFlowerPotData(Item potItem, int potData) {
		this.flowerPotItem = potItem;
		this.flowerPotData = potData;
	}

	public Item getFlowerPotItem() {
		return this.flowerPotItem;
	}

	public int getFlowerPotData() {
		return this.flowerPotData;
	}
}