package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;

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
public class LayerMooshroomMushroom implements LayerRenderer<EntityMooshroom> {
	private final RenderMooshroom mooshroomRenderer;

	public LayerMooshroomMushroom(RenderMooshroom mooshroomRendererIn) {
		this.mooshroomRenderer = mooshroomRendererIn;
	}

	public void doRenderLayer(EntityMooshroom entitymooshroom, float var2, float var3, float var4, float var5,
			float var6, float var7, float var8) {
		if (!entitymooshroom.isChild() && !entitymooshroom.isInvisible()) {
			BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			this.mooshroomRenderer.bindTexture(TextureMap.locationBlocksTexture);
			GlStateManager.enableCull();
			boolean flag = DeferredStateManager.isEnableShadowRender();
			GlStateManager.cullFace(flag ? GL_BACK : GL_FRONT);
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.0F, -1.0F, 1.0F);
			GlStateManager.translate(0.2F, 0.35F, 0.5F);
			GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.pushMatrix();
			GlStateManager.translate(-0.5F, -0.5F, 0.5F);
			blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.1F, 0.0F, -0.6F);
			GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-0.5F, -0.5F, 0.5F);
			blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			((ModelQuadruped) this.mooshroomRenderer.getMainModel()).head.postRender(0.0625F);
			GlStateManager.scale(1.0F, -1.0F, 1.0F);
			GlStateManager.translate(0.0F, 0.7F, -0.2F);
			GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-0.5F, -0.5F, 0.5F);
			blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
			GlStateManager.popMatrix();
			GlStateManager.cullFace(flag ? GL_FRONT : GL_BACK);
			GlStateManager.disableCull();
		}
	}

	public boolean shouldCombineTextures() {
		return true;
	}
}