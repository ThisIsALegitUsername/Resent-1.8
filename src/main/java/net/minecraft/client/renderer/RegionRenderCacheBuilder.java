package net.minecraft.client.renderer;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.util.EnumWorldBlockLayer;

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
public class RegionRenderCacheBuilder {
	private final WorldRenderer[] worldRenderers = new WorldRenderer[EnumWorldBlockLayer.values().length];

	public RegionRenderCacheBuilder() {
		this.worldRenderers[EnumWorldBlockLayer.SOLID.ordinal()] = new WorldRenderer(2097152);
		this.worldRenderers[EnumWorldBlockLayer.CUTOUT.ordinal()] = new WorldRenderer(131072);
		this.worldRenderers[EnumWorldBlockLayer.CUTOUT_MIPPED.ordinal()] = new WorldRenderer(131072);
		this.worldRenderers[EnumWorldBlockLayer.TRANSLUCENT.ordinal()] = new WorldRenderer(262144);
		this.worldRenderers[EnumWorldBlockLayer.REALISTIC_WATER.ordinal()] = new WorldRenderer(262145);
		this.worldRenderers[EnumWorldBlockLayer.GLASS_HIGHLIGHTS.ordinal()] = new WorldRenderer(131072);
	}

	public WorldRenderer getWorldRendererByLayer(EnumWorldBlockLayer layer) {
		return this.worldRenderers[layer.ordinal()];
	}

	public WorldRenderer getWorldRendererByLayerId(int id) {
		return this.worldRenderers[id];
	}
}