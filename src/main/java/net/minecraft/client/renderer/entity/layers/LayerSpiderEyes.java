package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.OpenGlHelper;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.ShadersRenderPassFuture;
import net.lax1dude.eaglercraft.v1_8.vector.Matrix4f;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
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
public class LayerSpiderEyes implements LayerRenderer<EntitySpider> {
	private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
	private final RenderSpider spiderRenderer;

	public LayerSpiderEyes(RenderSpider spiderRendererIn) {
		this.spiderRenderer = spiderRendererIn;
	}

	public void doRenderLayer(EntitySpider entityspider, float f, float f1, float f2, float f3, float f4, float f5,
			float f6) {
		if (DeferredStateManager.isInDeferredPass()) {
			if (entityspider.isInvisible()) {
				if (!DeferredStateManager.isEnableShadowRender()
						&& DeferredStateManager.forwardCallbackHandler != null) {
					final Matrix4f mat = new Matrix4f(GlStateManager.getModelViewReference());
					DeferredStateManager.forwardCallbackHandler.push(new ShadersRenderPassFuture(entityspider) {
						@Override
						public void draw(PassType pass) {
							if (pass == PassType.MAIN) {
								DeferredStateManager.reportForwardRenderObjectPosition2(x, y, z);
							}
							LayerSpiderEyes.this.spiderRenderer.bindTexture(SPIDER_EYES);
							DeferredStateManager.setDefaultMaterialConstants();
							DeferredStateManager.setRoughnessConstant(0.3f);
							DeferredStateManager.setMetalnessConstant(0.1f);
							DeferredStateManager.setEmissionConstant(0.9f);
							EntityRenderer.disableLightmapStatic();
							GlStateManager.tryBlendFuncSeparate(GL_ONE, GL_ONE, GL_ZERO, GL_ZERO);
							GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
							GlStateManager.depthMask(false);
							GlStateManager.pushMatrix();
							GlStateManager.loadMatrix(mat);
							GlStateManager.disableCull();
							ModelSpider eee = (ModelSpider) LayerSpiderEyes.this.spiderRenderer.getMainModel();
							eee.setLivingAnimations(entityspider, f, f1, f1);
							eee.render(entityspider, f, f1, f3, f4, f5, f6);
							GlStateManager.popMatrix();
							GlStateManager.depthMask(true);
							GlStateManager.enableCull();
							DeferredStateManager.setDefaultMaterialConstants();
							DeferredStateManager.setHDRTranslucentPassBlendFunc();
							GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
						}
					});
				}
				return;
			}
			this.spiderRenderer.bindTexture(SPIDER_EYES);
			DeferredStateManager.setEmissionConstant(0.5f);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enablePolygonOffset();
			GlStateManager.doPolygonOffset(-0.025f, 1.0f);
			this.spiderRenderer.getMainModel().render(entityspider, f, f1, f3, f4, f5, f6);
			GlStateManager.disablePolygonOffset();
			DeferredStateManager.setEmissionConstant(0.0f);
			return;
		}
		this.spiderRenderer.bindTexture(SPIDER_EYES);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GL_ONE, GL_ONE);
		if (entityspider.isInvisible()) {
			GlStateManager.depthMask(false);
		} else {
			GlStateManager.depthMask(true);
		}

		int i = 61680;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.spiderRenderer.getMainModel().render(entityspider, f, f1, f3, f4, f5, f6);
		i = entityspider.getBrightnessForRender(f2);
		j = i % 65536;
		k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		this.spiderRenderer.func_177105_a(entityspider, f2);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}