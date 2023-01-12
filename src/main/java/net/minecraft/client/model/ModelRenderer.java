package net.minecraft.client.model;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;

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
public class ModelRenderer {
	public float textureWidth;
	public float textureHeight;
	private int textureOffsetX;
	private int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled;
	private int displayList;
	public boolean mirror;
	public boolean showModel;
	public boolean isHidden;
	public List<ModelBox> cubeList;
	public List<ModelRenderer> childModels;
	public final String boxName;
	private ModelBase baseModel;
	public float offsetX;
	public float offsetY;
	public float offsetZ;

	public ModelRenderer(ModelBase model, String boxNameIn) {
		this.textureWidth = 64.0F;
		this.textureHeight = 32.0F;
		this.showModel = true;
		this.cubeList = Lists.newArrayList();
		this.baseModel = model;
		model.boxList.add(this);
		this.boxName = boxNameIn;
		this.setTextureSize(model.textureWidth, model.textureHeight);
	}

	public ModelRenderer(ModelBase model) {
		this(model, (String) null);
	}

	public ModelRenderer(ModelBase model, int texOffX, int texOffY) {
		this(model);
		this.setTextureOffset(texOffX, texOffY);
	}

	/**+
	 * Sets the current box's rotation points and rotation angles to
	 * another box.
	 */
	public void addChild(ModelRenderer renderer) {
		if (this.childModels == null) {
			this.childModels = Lists.newArrayList();
		}

		this.childModels.add(renderer);
	}

	public ModelRenderer setTextureOffset(int x, int y) {
		this.textureOffsetX = x;
		this.textureOffsetY = y;
		return this;
	}

	/**+
	 * Creates a textured box. Args: originX, originY, originZ,
	 * width, height, depth, scaleFactor.
	 */
	public ModelRenderer addBox(String partName, float offX, float offY, float offZ, int width, int height, int depth) {
		partName = this.boxName + "." + partName;
		TextureOffset textureoffset = this.baseModel.getTextureOffset(partName);
		this.setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
		this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height,
				depth, 0.0F)).setBoxName(partName));
		return this;
	}

	/**+
	 * Creates a textured box. Args: originX, originY, originZ,
	 * width, height, depth, scaleFactor.
	 */
	public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth) {
		this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height,
				depth, 0.0F));
		return this;
	}

	/**+
	 * Creates a textured box. Args: originX, originY, originZ,
	 * width, height, depth, scaleFactor.
	 */
	public ModelRenderer addBox(float parFloat1, float parFloat2, float parFloat3, int parInt1, int parInt2,
			int parInt3, boolean parFlag) {
		this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, parFloat1, parFloat2, parFloat3,
				parInt1, parInt2, parInt3, 0.0F, parFlag));
		return this;
	}

	/**+
	 * Creates a textured box. Args: originX, originY, originZ,
	 * width, height, depth, scaleFactor.
	 */
	public void addBox(float width, float height, float depth, int scaleFactor, int parInt2, int parInt3,
			float parFloat4) {
		this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, width, height, depth,
				scaleFactor, parInt2, parInt3, parFloat4));
	}

	public void setRotationPoint(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
		this.rotationPointX = rotationPointXIn;
		this.rotationPointY = rotationPointYIn;
		this.rotationPointZ = rotationPointZIn;
	}

	public void render(float parFloat1) {
		if (!this.isHidden) {
			if (this.showModel) {
				if (!this.compiled) {
					this.compileDisplayList(parFloat1);
				}

				GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
						GlStateManager.callList(this.displayList);
						if (this.childModels != null) {
							for (int k = 0; k < this.childModels.size(); ++k) {
								((ModelRenderer) this.childModels.get(k)).render(parFloat1);
							}
						}
					} else {
						GlStateManager.translate(this.rotationPointX * parFloat1, this.rotationPointY * parFloat1,
								this.rotationPointZ * parFloat1);
						GlStateManager.callList(this.displayList);
						if (this.childModels != null) {
							for (int j = 0; j < this.childModels.size(); ++j) {
								((ModelRenderer) this.childModels.get(j)).render(parFloat1);
							}
						}

						GlStateManager.translate(-this.rotationPointX * parFloat1, -this.rotationPointY * parFloat1,
								-this.rotationPointZ * parFloat1);
					}
				} else {
					GlStateManager.pushMatrix();
					GlStateManager.translate(this.rotationPointX * parFloat1, this.rotationPointY * parFloat1,
							this.rotationPointZ * parFloat1);
					if (this.rotateAngleZ != 0.0F) {
						GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F) {
						GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F) {
						GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
					}

					GlStateManager.callList(this.displayList);
					if (this.childModels != null) {
						for (int i = 0; i < this.childModels.size(); ++i) {
							((ModelRenderer) this.childModels.get(i)).render(parFloat1);
						}
					}

					GlStateManager.popMatrix();
				}

				GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
			}
		}
	}

	public void renderWithRotation(float parFloat1) {
		if (!this.isHidden) {
			if (this.showModel) {
				if (!this.compiled) {
					this.compileDisplayList(parFloat1);
				}

				GlStateManager.pushMatrix();
				GlStateManager.translate(this.rotationPointX * parFloat1, this.rotationPointY * parFloat1,
						this.rotationPointZ * parFloat1);
				if (this.rotateAngleY != 0.0F) {
					GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
				}

				if (this.rotateAngleX != 0.0F) {
					GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
				}

				if (this.rotateAngleZ != 0.0F) {
					GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
				}

				GlStateManager.callList(this.displayList);
				GlStateManager.popMatrix();
			}
		}
	}

	/**+
	 * Allows the changing of Angles after a box has been rendered
	 */
	public void postRender(float scale) {
		if (!this.isHidden) {
			if (this.showModel) {
				if (!this.compiled) {
					this.compileDisplayList(scale);
				}

				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
						GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale,
								this.rotationPointZ * scale);
					}
				} else {
					GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale,
							this.rotationPointZ * scale);
					if (this.rotateAngleZ != 0.0F) {
						GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F) {
						GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F) {
						GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
					}
				}

			}
		}
	}

	/**+
	 * Compiles a GL display list for this model
	 */
	private void compileDisplayList(float scale) {
		this.displayList = GLAllocation.generateDisplayLists();
		EaglercraftGPU.glNewList(this.displayList, GL_COMPILE);
		WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();

		for (int i = 0; i < this.cubeList.size(); ++i) {
			((ModelBox) this.cubeList.get(i)).render(worldrenderer, scale);
		}

		EaglercraftGPU.glEndList();
		this.compiled = true;
	}

	/**+
	 * Returns the model renderer with the new texture parameters.
	 */
	public ModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
		this.textureWidth = (float) textureWidthIn;
		this.textureHeight = (float) textureHeightIn;
		return this;
	}
}