package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IRegistry;

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
public class ModelManager implements IResourceManagerReloadListener {
	private IRegistry<ModelResourceLocation, IBakedModel> modelRegistry;
	private final TextureMap texMap;
	private final BlockModelShapes modelProvider;
	private IBakedModel defaultModel;

	public ModelManager(TextureMap textures) {
		this.texMap = textures;
		this.modelProvider = new BlockModelShapes(this);
	}

	public void onResourceManagerReload(IResourceManager iresourcemanager) {
		ModelBakery modelbakery = new ModelBakery(iresourcemanager, this.texMap, this.modelProvider);
		this.modelRegistry = modelbakery.setupModelRegistry();
		this.defaultModel = (IBakedModel) this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
		this.modelProvider.reloadModels();
	}

	public IBakedModel getModel(ModelResourceLocation modelLocation) {
		if (modelLocation == null) {
			return this.defaultModel;
		} else {
			IBakedModel ibakedmodel = (IBakedModel) this.modelRegistry.getObject(modelLocation);
			return ibakedmodel == null ? this.defaultModel : ibakedmodel;
		}
	}

	public IBakedModel getMissingModel() {
		return this.defaultModel;
	}

	public TextureMap getTextureMap() {
		return this.texMap;
	}

	public BlockModelShapes getBlockModelShapes() {
		return this.modelProvider;
	}
}