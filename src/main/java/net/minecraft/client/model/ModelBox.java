package net.minecraft.client.model;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;

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
public class ModelBox {
	private PositionTextureVertex[] vertexPositions;
	private TexturedQuad[] quadList;
	public final float posX1;
	public final float posY1;
	public final float posZ1;
	public final float posX2;
	public final float posY2;
	public final float posZ2;
	public String boxName;

	public ModelBox(ModelRenderer renderer, int parInt1, int parInt2, float parFloat1, float parFloat2, float parFloat3,
			int parInt3, int parInt4, int parInt5, float parFloat4) {
		this(renderer, parInt1, parInt2, parFloat1, parFloat2, parFloat3, parInt3, parInt4, parInt5, parFloat4,
				renderer.mirror);
	}

	public ModelBox(ModelRenderer renderer, int textureX, int textureY, float parFloat1, float parFloat2,
			float parFloat3, int parInt1, int parInt2, int parInt3, float parFloat4, boolean parFlag) {
		this.posX1 = parFloat1;
		this.posY1 = parFloat2;
		this.posZ1 = parFloat3;
		this.posX2 = parFloat1 + (float) parInt1;
		this.posY2 = parFloat2 + (float) parInt2;
		this.posZ2 = parFloat3 + (float) parInt3;
		this.vertexPositions = new PositionTextureVertex[8];
		this.quadList = new TexturedQuad[6];
		float f = parFloat1 + (float) parInt1;
		float f1 = parFloat2 + (float) parInt2;
		float f2 = parFloat3 + (float) parInt3;
		parFloat1 = parFloat1 - parFloat4;
		parFloat2 = parFloat2 - parFloat4;
		parFloat3 = parFloat3 - parFloat4;
		f = f + parFloat4;
		f1 = f1 + parFloat4;
		f2 = f2 + parFloat4;
		if (parFlag) {
			float f3 = f;
			f = parFloat1;
			parFloat1 = f3;
		}

		PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(parFloat1, parFloat2, parFloat3, 0.0F,
				0.0F);
		PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, parFloat2, parFloat3, 0.0F, 8.0F);
		PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, parFloat3, 8.0F, 8.0F);
		PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(parFloat1, f1, parFloat3, 8.0F, 0.0F);
		PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(parFloat1, parFloat2, f2, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, parFloat2, f2, 0.0F, 8.0F);
		PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
		PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(parFloat1, f1, f2, 8.0F, 0.0F);
		this.vertexPositions[0] = positiontexturevertex7;
		this.vertexPositions[1] = positiontexturevertex;
		this.vertexPositions[2] = positiontexturevertex1;
		this.vertexPositions[3] = positiontexturevertex2;
		this.vertexPositions[4] = positiontexturevertex3;
		this.vertexPositions[5] = positiontexturevertex4;
		this.vertexPositions[6] = positiontexturevertex5;
		this.vertexPositions[7] = positiontexturevertex6;
		this.quadList[0] = new TexturedQuad(
				new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1,
						positiontexturevertex5 },
				textureX + parInt3 + parInt1, textureY + parInt3, textureX + parInt3 + parInt1 + parInt3,
				textureY + parInt3 + parInt2, renderer.textureWidth, renderer.textureHeight);
		this.quadList[1] = new TexturedQuad(
				new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6,
						positiontexturevertex2 },
				textureX, textureY + parInt3, textureX + parInt3, textureY + parInt3 + parInt2, renderer.textureWidth,
				renderer.textureHeight);
		this.quadList[2] = new TexturedQuad(
				new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7,
						positiontexturevertex },
				textureX + parInt3, textureY, textureX + parInt3 + parInt1, textureY + parInt3, renderer.textureWidth,
				renderer.textureHeight);
		this.quadList[3] = new TexturedQuad(
				new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6,
						positiontexturevertex5 },
				textureX + parInt3 + parInt1, textureY + parInt3, textureX + parInt3 + parInt1 + parInt1, textureY,
				renderer.textureWidth, renderer.textureHeight);
		this.quadList[4] = new TexturedQuad(
				new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2,
						positiontexturevertex1 },
				textureX + parInt3, textureY + parInt3, textureX + parInt3 + parInt1, textureY + parInt3 + parInt2,
				renderer.textureWidth, renderer.textureHeight);
		this.quadList[5] = new TexturedQuad(
				new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5,
						positiontexturevertex6 },
				textureX + parInt3 + parInt1 + parInt3, textureY + parInt3,
				textureX + parInt3 + parInt1 + parInt3 + parInt1, textureY + parInt3 + parInt2, renderer.textureWidth,
				renderer.textureHeight);
		if (parFlag) {
			for (int i = 0; i < this.quadList.length; ++i) {
				this.quadList[i].flipFace();
			}
		}

	}

	public void render(WorldRenderer renderer, float scale) {
		for (int i = 0; i < this.quadList.length; ++i) {
			this.quadList[i].draw(renderer, scale);
		}

	}

	public ModelBox setBoxName(String name) {
		this.boxName = name;
		return this;
	}
}