package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.OpenGlHelper;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.ShadersRenderPassFuture;
import net.lax1dude.eaglercraft.v1_8.vector.Matrix4f;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.entity.monster.EntityEnderman;
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
public class LayerEndermanEyes implements LayerRenderer<EntityEnderman> {
	private static final ResourceLocation field_177203_a = new ResourceLocation(
			"textures/entity/enderman/enderman_eyes.png");
	private final RenderEnderman endermanRenderer;

	public LayerEndermanEyes(RenderEnderman endermanRendererIn) {
		this.endermanRenderer = endermanRendererIn;
	}

	public void doRenderLayer(EntityEnderman entityenderman, float f, float f1, float f2, float f3, float f4, float f5,
			float f6) {
		if (DeferredStateManager.isInDeferredPass()) {
			if (entityenderman.isInvisible()) {
				if (!DeferredStateManager.isEnableShadowRender()
						&& DeferredStateManager.forwardCallbackHandler != null) {
					final Matrix4f mat = new Matrix4f(GlStateManager.getModelViewReference());
					DeferredStateManager.forwardCallbackHandler.push(new ShadersRenderPassFuture(entityenderman) {
						@Override
						public void draw(PassType pass) {
							if (pass == PassType.MAIN) {
								DeferredStateManager.reportForwardRenderObjectPosition2(x, y, z);
							}
							LayerEndermanEyes.this.endermanRenderer.bindTexture(field_177203_a);
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
							ModelEnderman eee = (ModelEnderman) LayerEndermanEyes.this.endermanRenderer.getMainModel();
							eee.isAttacking = entityenderman.isScreaming();
							eee.setLivingAnimations(entityenderman, f, f1, f1);
							eee.render(entityenderman, f, f1, f3, f4, f5, f6);
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
			this.endermanRenderer.bindTexture(field_177203_a);
			EntityRenderer.disableLightmapStatic();
			DeferredStateManager.setEmissionConstant(0.9f);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enablePolygonOffset();
			GlStateManager.doPolygonOffset(-0.025f, 1.0f);
			this.endermanRenderer.getMainModel().render(entityenderman, f, f1, f3, f4, f5, f6);
			GlStateManager.disablePolygonOffset();
			DeferredStateManager.setEmissionConstant(0.0f);
			EntityRenderer.enableLightmapStatic();
			return;
		}
		this.endermanRenderer.bindTexture(field_177203_a);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GL_ONE, GL_ONE);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(!entityenderman.isInvisible());
		char c0 = '\uf0f0';
		int i = c0 % 65536;
		int j = c0 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i / 1.0F, (float) j / 1.0F);
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.endermanRenderer.getMainModel().render(entityenderman, f, f1, f3, f4, f5, f6);
		this.endermanRenderer.func_177105_a(entityenderman, f2);
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}