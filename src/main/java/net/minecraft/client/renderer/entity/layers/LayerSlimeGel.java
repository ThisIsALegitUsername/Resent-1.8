package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.ShadersRenderPassFuture;
import net.lax1dude.eaglercraft.v1_8.vector.Matrix4f;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.MathHelper;

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
public class LayerSlimeGel implements LayerRenderer<EntitySlime> {
	private final RenderSlime slimeRenderer;
	private final ModelBase slimeModel = new ModelSlime(0);

	public LayerSlimeGel(RenderSlime slimeRendererIn) {
		this.slimeRenderer = slimeRendererIn;
	}

	public void doRenderLayer(EntitySlime entityslime, float f, float f1, float var4, float f2, float f3, float f4,
			float f5) {
		if (DeferredStateManager.isInDeferredPass()) {
			if (DeferredStateManager.forwardCallbackHandler != null) {
				final Matrix4f mat = new Matrix4f(GlStateManager.getModelViewReference());
				DeferredStateManager.forwardCallbackHandler.push(new ShadersRenderPassFuture(entityslime) {
					@Override
					public void draw(PassType pass) {
						if (pass == PassType.MAIN) {
							DeferredStateManager.reportForwardRenderObjectPosition2(x, y, z);
						}
						DeferredStateManager.setDefaultMaterialConstants();
						DeferredStateManager.setRoughnessConstant(0.3f);
						DeferredStateManager.setMetalnessConstant(0.1f);
						boolean flag = LayerSlimeGel.this.slimeRenderer.setBrightness(entityslime, partialTicks,
								LayerSlimeGel.this.shouldCombineTextures());
						EntityRenderer.enableLightmapStatic();
						GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
						GlStateManager.pushMatrix();
						GlStateManager.loadMatrix(mat);
						RenderManager.setupLightmapCoords(entityslime, partialTicks);
						LayerSlimeGel.this.slimeModel
								.setModelAttributes(LayerSlimeGel.this.slimeRenderer.getMainModel());
						LayerSlimeGel.this.slimeRenderer.bindTexture(RenderSlime.slimeTextures);
						LayerSlimeGel.this.slimeModel.render(entityslime, f, f1, f2, f3, f4, f5);
						GlStateManager.popMatrix();
						EntityRenderer.disableLightmapStatic();
						if (flag) {
							LayerSlimeGel.this.slimeRenderer.unsetBrightness();
						}
					}
				});
			}
			return;
		}
		if (!entityslime.isInvisible()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
			this.slimeModel.render(entityslime, f, f1, f2, f3, f4, f5);
			GlStateManager.disableBlend();
		}
	}

	public boolean shouldCombineTextures() {
		return true;
	}
}