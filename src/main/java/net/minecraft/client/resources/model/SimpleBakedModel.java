package net.minecraft.client.resources.model;

import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
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
public class SimpleBakedModel implements IBakedModel {
	protected final List<BakedQuad> generalQuads;
	protected final List<List<BakedQuad>> faceQuads;
	protected final boolean ambientOcclusion;
	protected final boolean gui3d;
	protected final EaglerTextureAtlasSprite texture;
	protected final ItemCameraTransforms cameraTransforms;

	public SimpleBakedModel(List<BakedQuad> parList, List<List<BakedQuad>> parList2, boolean parFlag, boolean parFlag2,
			EaglerTextureAtlasSprite parTextureAtlasSprite, ItemCameraTransforms parItemCameraTransforms) {
		this.generalQuads = parList;
		this.faceQuads = parList2;
		this.ambientOcclusion = parFlag;
		this.gui3d = parFlag2;
		this.texture = parTextureAtlasSprite;
		this.cameraTransforms = parItemCameraTransforms;
	}

	public List<BakedQuad> getFaceQuads(EnumFacing enumfacing) {
		return (List) this.faceQuads.get(enumfacing.ordinal());
	}

	public List<BakedQuad> getGeneralQuads() {
		return this.generalQuads;
	}

	public boolean isAmbientOcclusion() {
		return this.ambientOcclusion;
	}

	public boolean isGui3d() {
		return this.gui3d;
	}

	public boolean isBuiltInRenderer() {
		return false;
	}

	public EaglerTextureAtlasSprite getParticleTexture() {
		return this.texture;
	}

	public ItemCameraTransforms getItemCameraTransforms() {
		return this.cameraTransforms;
	}

	public static class Builder {
		private final List<BakedQuad> builderGeneralQuads;
		private final List<List<BakedQuad>> builderFaceQuads;
		private final boolean builderAmbientOcclusion;
		private EaglerTextureAtlasSprite builderTexture;
		private boolean builderGui3d;
		private ItemCameraTransforms builderCameraTransforms;

		public Builder(ModelBlock parModelBlock) {
			this(parModelBlock.isAmbientOcclusion(), parModelBlock.isGui3d(), parModelBlock.func_181682_g());
		}

		public Builder(IBakedModel parIBakedModel, EaglerTextureAtlasSprite parTextureAtlasSprite) {
			this(parIBakedModel.isAmbientOcclusion(), parIBakedModel.isGui3d(),
					parIBakedModel.getItemCameraTransforms());
			this.builderTexture = parIBakedModel.getParticleTexture();

			for (EnumFacing enumfacing : EnumFacing.values()) {
				this.addFaceBreakingFours(parIBakedModel, parTextureAtlasSprite, enumfacing);
			}

			this.addGeneralBreakingFours(parIBakedModel, parTextureAtlasSprite);
		}

		private void addFaceBreakingFours(IBakedModel parIBakedModel, EaglerTextureAtlasSprite parTextureAtlasSprite,
				EnumFacing parEnumFacing) {
			for (BakedQuad bakedquad : parIBakedModel.getFaceQuads(parEnumFacing)) {
				this.addFaceQuad(parEnumFacing, new BreakingFour(bakedquad, parTextureAtlasSprite));
			}

		}

		private void addGeneralBreakingFours(IBakedModel parIBakedModel,
				EaglerTextureAtlasSprite parTextureAtlasSprite) {
			for (BakedQuad bakedquad : parIBakedModel.getGeneralQuads()) {
				this.addGeneralQuad(new BreakingFour(bakedquad, parTextureAtlasSprite));
			}

		}

		private Builder(boolean parFlag, boolean parFlag2, ItemCameraTransforms parItemCameraTransforms) {
			this.builderGeneralQuads = Lists.newArrayList();
			this.builderFaceQuads = Lists.newArrayListWithCapacity(6);

			for (EnumFacing enumfacing : EnumFacing.values()) {
				this.builderFaceQuads.add(Lists.newArrayList());
			}

			this.builderAmbientOcclusion = parFlag;
			this.builderGui3d = parFlag2;
			this.builderCameraTransforms = parItemCameraTransforms;
		}

		public SimpleBakedModel.Builder addFaceQuad(EnumFacing parEnumFacing, BakedQuad parBakedQuad) {
			((List) this.builderFaceQuads.get(parEnumFacing.ordinal())).add(parBakedQuad);
			return this;
		}

		public SimpleBakedModel.Builder addGeneralQuad(BakedQuad parBakedQuad) {
			this.builderGeneralQuads.add(parBakedQuad);
			return this;
		}

		public SimpleBakedModel.Builder setTexture(EaglerTextureAtlasSprite parTextureAtlasSprite) {
			this.builderTexture = parTextureAtlasSprite;
			return this;
		}

		public IBakedModel makeBakedModel() {
			if (this.builderTexture == null) {
				throw new RuntimeException("Missing particle!");
			} else {
				return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads,
						this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture,
						this.builderCameraTransforms);
			}
		}
	}
}