package net.minecraft.client.renderer.chunk;

import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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
public class CompiledChunk {
	public static final CompiledChunk DUMMY = new CompiledChunk() {
		protected void setLayerUsed(EnumWorldBlockLayer layer) {
			throw new UnsupportedOperationException();
		}

		public void setLayerStarted(EnumWorldBlockLayer layer) {
			throw new UnsupportedOperationException();
		}

		public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
			return true;
		}
	};
	private final boolean[] layersUsed = new boolean[EnumWorldBlockLayer.values().length];
	private final boolean[] layersStarted = new boolean[EnumWorldBlockLayer.values().length];
	private boolean empty = true;
	private final List<TileEntity> tileEntities = Lists.newArrayList();
	private SetVisibility setVisibility = new SetVisibility();
	private WorldRenderer.State state;
	private WorldRenderer.State stateWater;

	public boolean isEmpty() {
		return this.empty;
	}

	protected void setLayerUsed(EnumWorldBlockLayer enumworldblocklayer) {
		this.empty = false;
		this.layersUsed[enumworldblocklayer.ordinal()] = true;
	}

	public boolean isLayerEmpty(EnumWorldBlockLayer layer) {
		return !this.layersUsed[layer.ordinal()];
	}

	public void setLayerStarted(EnumWorldBlockLayer enumworldblocklayer) {
		this.layersStarted[enumworldblocklayer.ordinal()] = true;
	}

	public boolean isLayerStarted(EnumWorldBlockLayer layer) {
		return this.layersStarted[layer.ordinal()];
	}

	public List<TileEntity> getTileEntities() {
		return this.tileEntities;
	}

	public void addTileEntity(TileEntity tileEntityIn) {
		this.tileEntities.add(tileEntityIn);
	}

	public boolean isVisible(EnumFacing enumfacing, EnumFacing enumfacing1) {
		return this.setVisibility.isVisible(enumfacing, enumfacing1);
	}

	public void setVisibility(SetVisibility visibility) {
		this.setVisibility = visibility;
	}

	public WorldRenderer.State getState() {
		return this.state;
	}

	public void setState(WorldRenderer.State stateIn) {
		this.state = stateIn;
	}

	public WorldRenderer.State getStateRealisticWater() {
		return this.stateWater;
	}

	public void setStateRealisticWater(WorldRenderer.State stateIn) {
		this.stateWater = stateIn;
	}
}