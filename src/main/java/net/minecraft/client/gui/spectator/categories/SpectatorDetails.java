package net.minecraft.client.gui.spectator.categories;

import java.util.List;

import com.google.common.base.Objects;

import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;

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
public class SpectatorDetails {
	private final ISpectatorMenuView field_178684_a;
	private final List<ISpectatorMenuObject> field_178682_b;
	private final int field_178683_c;

	public SpectatorDetails(ISpectatorMenuView parISpectatorMenuView, List<ISpectatorMenuObject> parList, int parInt1) {
		this.field_178684_a = parISpectatorMenuView;
		this.field_178682_b = parList;
		this.field_178683_c = parInt1;
	}

	public ISpectatorMenuObject func_178680_a(int parInt1) {
		return parInt1 >= 0 && parInt1 < this.field_178682_b.size() ? (ISpectatorMenuObject) Objects
				.firstNonNull(this.field_178682_b.get(parInt1), SpectatorMenu.field_178657_a)
				: SpectatorMenu.field_178657_a;
	}

	public int func_178681_b() {
		return this.field_178683_c;
	}
}