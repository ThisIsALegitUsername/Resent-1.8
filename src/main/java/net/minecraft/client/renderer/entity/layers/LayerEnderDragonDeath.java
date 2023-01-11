package net.minecraft.client.renderer.entity.layers;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;

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
public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon> {
	public void doRenderLayer(EntityDragon entitydragon, float var2, float var3, float f, float var5, float var6,
			float var7, float var8) {
		if (entitydragon.deathTicks > 0) {
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			RenderHelper.disableStandardItemLighting();
			float f1 = ((float) entitydragon.deathTicks + f) / 200.0F;
			float f2 = 0.0F;
			if (f1 > 0.8F) {
				f2 = (f1 - 0.8F) / 0.2F;
			}

			EaglercraftRandom random = new EaglercraftRandom(432L);
			GlStateManager.disableTexture2D();
			GlStateManager.shadeModel(GL_SMOOTH);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE);
			GlStateManager.disableAlpha();
			GlStateManager.enableCull();
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, -1.0F, -2.0F);

			for (int i = 0; (float) i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i) {
				GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
				float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
				float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
				worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
				worldrenderer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int) (255.0F * (1.0F - f2))).endVertex();
				worldrenderer.pos(-0.866D * (double) f4, (double) f3, (double) (-0.5F * f4)).color(255, 0, 255, 0)
						.endVertex();
				worldrenderer.pos(0.866D * (double) f4, (double) f3, (double) (-0.5F * f4)).color(255, 0, 255, 0)
						.endVertex();
				worldrenderer.pos(0.0D, (double) f3, (double) (1.0F * f4)).color(255, 0, 255, 0).endVertex();
				worldrenderer.pos(-0.866D * (double) f4, (double) f3, (double) (-0.5F * f4)).color(255, 0, 255, 0)
						.endVertex();
				tessellator.draw();
			}

			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
			GlStateManager.disableCull();
			GlStateManager.disableBlend();
			GlStateManager.shadeModel(GL_FLAT);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
			RenderHelper.enableStandardItemLighting();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}