package net.minecraft.client.gui;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerList;

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
public class ServerSelectionList extends GuiListExtended {
	private final GuiMultiplayer owner;
	private final List<ServerListEntryNormal> field_148198_l = Lists.newArrayList();
	private int selectedSlotIndex = -1;

	public ServerSelectionList(GuiMultiplayer ownerIn, Minecraft mcIn, int widthIn, int heightIn, int topIn,
			int bottomIn, int slotHeightIn) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
		this.owner = ownerIn;
	}

	/**+
	 * Gets the IGuiListEntry object for the given index
	 */
	public GuiListExtended.IGuiListEntry getListEntry(int i) {
		return (GuiListExtended.IGuiListEntry) this.field_148198_l.get(i);
	}

	protected int getSize() {
		return this.field_148198_l.size();
	}

	public void setSelectedSlotIndex(int selectedSlotIndexIn) {
		this.selectedSlotIndex = selectedSlotIndexIn;
	}

	/**+
	 * Returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int i) {
		return i == this.selectedSlotIndex;
	}

	public int func_148193_k() {
		return this.selectedSlotIndex;
	}

	public void func_148195_a(ServerList parServerList) {
		this.field_148198_l.clear();

		for (int i = 0; i < parServerList.countServers(); ++i) {
			this.field_148198_l.add(new ServerListEntryNormal(this.owner, parServerList.getServerData(i)));
		}

	}

	protected int getScrollBarX() {
		return super.getScrollBarX() + 30;
	}

	/**+
	 * Gets the width of the list
	 */
	public int getListWidth() {
		return super.getListWidth() + 85;
	}
}