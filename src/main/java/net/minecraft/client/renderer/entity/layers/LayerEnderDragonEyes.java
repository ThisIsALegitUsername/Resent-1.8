package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.OpenGlHelper;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.entity.boss.EntityDragon;
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
public class LayerEnderDragonEyes implements LayerRenderer<EntityDragon> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
	private final RenderDragon dragonRenderer;

	public LayerEnderDragonEyes(RenderDragon dragonRendererIn) {
		this.dragonRenderer = dragonRendererIn;
	}

	public void doRenderLayer(EntityDragon entitydragon, float f, float f1, float f2, float f3, float f4, float f5,
			float f6) {
		this.dragonRenderer.bindTexture(TEXTURE);
		if (DeferredStateManager.isInDeferredPass()) {
			DeferredStateManager.setEmissionConstant(0.5f);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enablePolygonOffset();
			GlStateManager.doPolygonOffset(-0.025f, 1.0f);
			this.dragonRenderer.getMainModel().render(entitydragon, f, f1, f3, f4, f5, f6);
			this.dragonRenderer.func_177105_a(entitydragon, f2);
			GlStateManager.disablePolygonOffset();
			DeferredStateManager.setEmissionConstant(0.0f);
			return;
		}
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GL_ONE, GL_ONE);
		GlStateManager.disableLighting();
		GlStateManager.depthFunc(GL_EQUAL);
		char c0 = '\uf0f0';
		int i = c0 % 65536;
		int j = c0 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i / 1.0F, (float) j / 1.0F);
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.dragonRenderer.getMainModel().render(entitydragon, f, f1, f3, f4, f5, f6);
		this.dragonRenderer.func_177105_a(entitydragon, f2);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.depthFunc(GL_LEQUAL);
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}