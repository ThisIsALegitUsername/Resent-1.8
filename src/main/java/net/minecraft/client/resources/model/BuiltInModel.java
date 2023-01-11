package net.minecraft.client.resources.model;

import java.util.List;

import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.EnumFacing;

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
public class BuiltInModel implements IBakedModel {
	private ItemCameraTransforms cameraTransforms;

	public BuiltInModel(ItemCameraTransforms parItemCameraTransforms) {
		this.cameraTransforms = parItemCameraTransforms;
	}

	public List<BakedQuad> getFaceQuads(EnumFacing var1) {
		return null;
	}

	public List<BakedQuad> getGeneralQuads() {
		return null;
	}

	public boolean isAmbientOcclusion() {
		return false;
	}

	public boolean isGui3d() {
		return true;
	}

	public boolean isBuiltInRenderer() {
		return true;
	}

	public EaglerTextureAtlasSprite getParticleTexture() {
		return null;
	}

	public ItemCameraTransforms getItemCameraTransforms() {
		return this.cameraTransforms;
	}
}