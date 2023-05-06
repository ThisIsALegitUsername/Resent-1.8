package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.ShadersRenderPassFuture;
import net.lax1dude.eaglercraft.v1_8.vector.Matrix4f;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

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
public class LayerWitherAura implements LayerRenderer<EntityWither> {
	private static final ResourceLocation WITHER_ARMOR = new ResourceLocation(
			"textures/entity/wither/wither_armor.png");
	private final RenderWither witherRenderer;
	private final ModelWither witherModel = new ModelWither(0.5F);

	public LayerWitherAura(RenderWither witherRendererIn) {
		this.witherRenderer = witherRendererIn;
	}

	public void doRenderLayer(EntityWither entitywither, float f, float f1, float f2, float f3, float f4, float f5,
			float f6) {
		if (entitywither.isArmored()) {
			if (DeferredStateManager.isInDeferredPass()) {
				if (!DeferredStateManager.isEnableShadowRender()
						&& DeferredStateManager.forwardCallbackHandler != null) {
					final Matrix4f mat = new Matrix4f(GlStateManager.getModelViewReference());
					DeferredStateManager.forwardCallbackHandler.push(new ShadersRenderPassFuture(entitywither) {
						@Override
						public void draw(PassType pass) {
							if (pass == PassType.MAIN) {
								DeferredStateManager.reportForwardRenderObjectPosition2(x, y, z);
							}
							boolean flag = entitywither.isInvisible();
							DeferredStateManager.setDefaultMaterialConstants();
							DeferredStateManager.setRoughnessConstant(0.5f);
							DeferredStateManager.setMetalnessConstant(0.2f);
							DeferredStateManager.setEmissionConstant(0.9f);
							EntityRenderer.disableLightmapStatic();
							GlStateManager.tryBlendFuncSeparate(GL_ONE, GL_ONE, GL_ZERO, GL_ZERO);
							GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
							if (flag) {
								GlStateManager.depthMask(false);
							}
							LayerWitherAura.this.witherRenderer.bindTexture(WITHER_ARMOR);
							GlStateManager.pushMatrix();
							GlStateManager.loadMatrix(mat);
							GlStateManager.matrixMode(GL_TEXTURE);
							GlStateManager.loadIdentity();
							float f7 = (float) entitywither.ticksExisted + f2;
							float f8 = MathHelper.cos(f7 * 0.02F) * 3.0F;
							float f9 = f7 * 0.01F;
							GlStateManager.translate(f8, f9, 0.0F);
							GlStateManager.matrixMode(GL_MODELVIEW);
							GlStateManager.disableCull();
							LayerWitherAura.this.witherModel.setLivingAnimations(entitywither, f, f1, f2);
							LayerWitherAura.this.witherModel.setRotationAngles(f, f1, f2, f3, f4, f5, entitywither);
							LayerWitherAura.this.witherModel
									.setModelAttributes(LayerWitherAura.this.witherRenderer.getMainModel());
							LayerWitherAura.this.witherModel.render(entitywither, f, f1, f3, f4, f5, f6);
							GlStateManager.matrixMode(GL_TEXTURE);
							GlStateManager.loadIdentity();
							GlStateManager.matrixMode(GL_MODELVIEW);
							GlStateManager.popMatrix();
							if (flag) {
								GlStateManager.depthMask(true);
							}
							GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
						}
					});
				}
				return;
			}
			GlStateManager.depthMask(!entitywither.isInvisible());
			this.witherRenderer.bindTexture(WITHER_ARMOR);
			GlStateManager.matrixMode(GL_TEXTURE);
			GlStateManager.loadIdentity();
			float f7 = (float) entitywither.ticksExisted + f2;
			float f8 = MathHelper.cos(f7 * 0.02F) * 3.0F;
			float f9 = f7 * 0.01F;
			GlStateManager.translate(f8, f9, 0.0F);
			GlStateManager.matrixMode(GL_MODELVIEW);
			GlStateManager.enableBlend();
			float f10 = 0.5F;
			GlStateManager.color(f10, f10, f10, 1.0F);
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(GL_ONE, GL_ONE);
			this.witherModel.setLivingAnimations(entitywither, f, f1, f2);
			this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
			this.witherModel.render(entitywither, f, f1, f3, f4, f5, f6);
			GlStateManager.matrixMode(GL_TEXTURE);
			GlStateManager.loadIdentity();
			GlStateManager.matrixMode(GL_MODELVIEW);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.depthMask(true);
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}