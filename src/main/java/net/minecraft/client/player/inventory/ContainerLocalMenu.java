package net.minecraft.client.player.inventory;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

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
public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer {
	private String guiID;
	private Map<Integer, Integer> field_174895_b = Maps.newHashMap();

	public ContainerLocalMenu(String id, IChatComponent title, int slotCount) {
		super(title, slotCount);
		this.guiID = id;
	}

	public int getField(int i) {
		return this.field_174895_b.containsKey(Integer.valueOf(i))
				? ((Integer) this.field_174895_b.get(Integer.valueOf(i))).intValue()
				: 0;
	}

	public void setField(int i, int j) {
		this.field_174895_b.put(Integer.valueOf(i), Integer.valueOf(j));
	}

	public int getFieldCount() {
		return this.field_174895_b.size();
	}

	public boolean isLocked() {
		return false;
	}

	public void setLockCode(LockCode var1) {
	}

	public LockCode getLockCode() {
		return LockCode.EMPTY_CODE;
	}

	public String getGuiID() {
		return this.guiID;
	}

	public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
		throw new UnsupportedOperationException();
	}
}