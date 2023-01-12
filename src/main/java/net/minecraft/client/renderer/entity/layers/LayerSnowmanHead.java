package net.minecraft.client.renderer.entity.layers;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
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
public class LayerSnowmanHead implements LayerRenderer<EntitySnowman> {
	private final RenderSnowMan snowManRenderer;

	public LayerSnowmanHead(RenderSnowMan snowManRendererIn) {
		this.snowManRenderer = snowManRendererIn;
	}

	public void doRenderLayer(EntitySnowman entitysnowman, float var2, float var3, float var4, float var5, float var6,
			float var7, float var8) {
		if (!entitysnowman.isInvisible()) {
			GlStateManager.pushMatrix();
			this.snowManRenderer.getMainModel().head.postRender(0.0625F);
			float f = 0.625F;
			GlStateManager.translate(0.0F, -0.34375F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.scale(f, -f, -f);
			Minecraft.getMinecraft().getItemRenderer().renderItem(entitysnowman, new ItemStack(Blocks.pumpkin, 1),
					ItemCameraTransforms.TransformType.HEAD);
			GlStateManager.popMatrix();
		}
	}

	public boolean shouldCombineTextures() {
		return true;
	}
}