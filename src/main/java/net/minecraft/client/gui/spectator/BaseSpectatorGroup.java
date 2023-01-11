package net.minecraft.client.gui.spectator;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

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
public class BaseSpectatorGroup implements ISpectatorMenuView {
	private final List<ISpectatorMenuObject> field_178671_a = Lists.newArrayList();

	public BaseSpectatorGroup() {
		this.field_178671_a.add(new TeleportToPlayer());
		this.field_178671_a.add(new TeleportToTeam());
	}

	public List<ISpectatorMenuObject> func_178669_a() {
		return this.field_178671_a;
	}

	public IChatComponent func_178670_b() {
		return new ChatComponentText("Press a key to select a command, and again to use it.");
	}
}