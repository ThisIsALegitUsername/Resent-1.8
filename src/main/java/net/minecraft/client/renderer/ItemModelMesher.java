package net.minecraft.client.renderer;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
public class ItemModelMesher {
	private final Map<Integer, ModelResourceLocation> simpleShapes = Maps.newHashMap();
	private final Map<Integer, IBakedModel> simpleShapesCache = Maps.newHashMap();
	private final Map<Item, ItemMeshDefinition> shapers = Maps.newHashMap();
	private final ModelManager modelManager;

	public ItemModelMesher(ModelManager modelManager) {
		this.modelManager = modelManager;
	}

	public EaglerTextureAtlasSprite getParticleIcon(Item item) {
		return this.getParticleIcon(item, 0);
	}

	public EaglerTextureAtlasSprite getParticleIcon(Item item, int meta) {
		return this.getItemModel(new ItemStack(item, 1, meta)).getParticleTexture();
	}

	public IBakedModel getItemModel(ItemStack stack) {
		Item item = stack.getItem();
		IBakedModel ibakedmodel = this.getItemModel(item, this.getMetadata(stack));
		if (ibakedmodel == null) {
			ItemMeshDefinition itemmeshdefinition = (ItemMeshDefinition) this.shapers.get(item);
			if (itemmeshdefinition != null) {
				ibakedmodel = this.modelManager.getModel(itemmeshdefinition.getModelLocation(stack));
			}
		}

		if (ibakedmodel == null) {
			ibakedmodel = this.modelManager.getMissingModel();
		}

		return ibakedmodel;
	}

	protected int getMetadata(ItemStack stack) {
		return stack.isItemStackDamageable() ? 0 : stack.getMetadata();
	}

	protected IBakedModel getItemModel(Item item, int meta) {
		return (IBakedModel) this.simpleShapesCache.get(Integer.valueOf(this.getIndex(item, meta)));
	}

	private int getIndex(Item item, int meta) {
		return Item.getIdFromItem(item) << 16 | meta;
	}

	public void register(Item item, int meta, ModelResourceLocation location) {
		this.simpleShapes.put(Integer.valueOf(this.getIndex(item, meta)), location);
		this.simpleShapesCache.put(Integer.valueOf(this.getIndex(item, meta)), this.modelManager.getModel(location));
	}

	public void register(Item item, ItemMeshDefinition definition) {
		this.shapers.put(item, definition);
	}

	public ModelManager getModelManager() {
		return this.modelManager;
	}

	public void rebuildCache() {
		this.simpleShapesCache.clear();

		for (Entry entry : this.simpleShapes.entrySet()) {
			this.simpleShapesCache.put((Integer) entry.getKey(),
					this.modelManager.getModel((ModelResourceLocation) entry.getValue()));
		}

	}
}