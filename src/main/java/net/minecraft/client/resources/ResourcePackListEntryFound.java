package net.minecraft.client.resources;

import net.minecraft.client.gui.GuiScreenResourcePacks;

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
public class ResourcePackListEntryFound extends ResourcePackListEntry {
	private final ResourcePackRepository.Entry field_148319_c;

	public ResourcePackListEntryFound(GuiScreenResourcePacks resourcePacksGUIIn,
			ResourcePackRepository.Entry parEntry) {
		super(resourcePacksGUIIn);
		this.field_148319_c = parEntry;
	}

	protected void func_148313_c() {
		this.field_148319_c.bindTexturePackIcon(this.mc.getTextureManager());
	}

	protected int func_183019_a() {
		return this.field_148319_c.func_183027_f();
	}

	protected String func_148311_a() {
		return this.field_148319_c.getTexturePackDescription();
	}

	protected String func_148312_b() {
		return this.field_148319_c.getResourcePackName();
	}

	public ResourcePackRepository.Entry func_148318_i() {
		return this.field_148319_c;
	}
}