package net.minecraft.client.renderer.tileentity;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import java.util.List;

import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files are (c) 2022 LAX1DUDE. All Rights Reserved.
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
public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon> {
	private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");

	public void renderTileEntityAt(TileEntityBeacon tileentitybeacon, double d0, double d1, double d2, float f,
			int var9) {
		float f1 = tileentitybeacon.shouldBeamRender();
		GlStateManager.alphaFunc(GL_GREATER, 0.1F);
		if (f1 > 0.0F) {
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			GlStateManager.disableFog();
			List list = tileentitybeacon.getBeamSegments();
			int i = 0;

			for (int j = 0; j < list.size(); ++j) {
				TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = (TileEntityBeacon.BeamSegment) list.get(j);
				int k = i + tileentitybeacon$beamsegment.getHeight();
				this.bindTexture(beaconBeam);
				EaglercraftGPU.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, 10497.0F);
				EaglercraftGPU.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, 10497.0F);
				GlStateManager.disableLighting();
				GlStateManager.disableCull();
				GlStateManager.disableBlend();
				GlStateManager.depthMask(true);
				GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, 1, 1, 0);
				double d3 = (double) tileentitybeacon.getWorld().getTotalWorldTime() + (double) f;
				double d4 = MathHelper.func_181162_h(-d3 * 0.2D - (double) MathHelper.floor_double(-d3 * 0.1D));
				float f2 = tileentitybeacon$beamsegment.getColors()[0];
				float f3 = tileentitybeacon$beamsegment.getColors()[1];
				float f4 = tileentitybeacon$beamsegment.getColors()[2];
				double d5 = d3 * 0.025D * -1.5D;
				double d6 = 0.2D;
				double d7 = 0.5D + Math.cos(d5 + 2.356194490192345D) * 0.2D;
				double d8 = 0.5D + Math.sin(d5 + 2.356194490192345D) * 0.2D;
				double d9 = 0.5D + Math.cos(d5 + 0.7853981633974483D) * 0.2D;
				double d10 = 0.5D + Math.sin(d5 + 0.7853981633974483D) * 0.2D;
				double d11 = 0.5D + Math.cos(d5 + 3.9269908169872414D) * 0.2D;
				double d12 = 0.5D + Math.sin(d5 + 3.9269908169872414D) * 0.2D;
				double d13 = 0.5D + Math.cos(d5 + 5.497787143782138D) * 0.2D;
				double d14 = 0.5D + Math.sin(d5 + 5.497787143782138D) * 0.2D;
				double d15 = 0.0D;
				double d16 = 1.0D;
				double d17 = -1.0D + d4;
				double d18 = (double) ((float) tileentitybeacon$beamsegment.getHeight() * f1) * 2.5D + d17;
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				worldrenderer.pos(d0 + d7, d1 + (double) k, d2 + d8).tex(1.0D, d18).color(f2, f3, f4, 1.0F).endVertex();
				worldrenderer.pos(d0 + d7, d1 + (double) i, d2 + d8).tex(1.0D, d17).color(f2, f3, f4, 1.0F).endVertex();
				worldrenderer.pos(d0 + d9, d1 + (double) i, d2 + d10).tex(0.0D, d17).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d9, d1 + (double) k, d2 + d10).tex(0.0D, d18).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d13, d1 + (double) k, d2 + d14).tex(1.0D, d18).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d13, d1 + (double) i, d2 + d14).tex(1.0D, d17).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d11, d1 + (double) i, d2 + d12).tex(0.0D, d17).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d11, d1 + (double) k, d2 + d12).tex(0.0D, d18).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d9, d1 + (double) k, d2 + d10).tex(1.0D, d18).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d9, d1 + (double) i, d2 + d10).tex(1.0D, d17).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d13, d1 + (double) i, d2 + d14).tex(0.0D, d17).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d13, d1 + (double) k, d2 + d14).tex(0.0D, d18).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d11, d1 + (double) k, d2 + d12).tex(1.0D, d18).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d11, d1 + (double) i, d2 + d12).tex(1.0D, d17).color(f2, f3, f4, 1.0F)
						.endVertex();
				worldrenderer.pos(d0 + d7, d1 + (double) i, d2 + d8).tex(0.0D, d17).color(f2, f3, f4, 1.0F).endVertex();
				worldrenderer.pos(d0 + d7, d1 + (double) k, d2 + d8).tex(0.0D, d18).color(f2, f3, f4, 1.0F).endVertex();
				tessellator.draw();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
				GlStateManager.depthMask(false);
				d5 = 0.2D;
				d6 = 0.2D;
				d7 = 0.8D;
				d8 = 0.2D;
				d9 = 0.2D;
				d10 = 0.8D;
				d11 = 0.8D;
				d12 = 0.8D;
				d13 = 0.0D;
				d14 = 1.0D;
				d15 = -1.0D + d4;
				d16 = (double) ((float) tileentitybeacon$beamsegment.getHeight() * f1) + d15;
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				worldrenderer.pos(d0 + 0.2D, d1 + (double) k, d2 + 0.2D).tex(1.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.2D, d1 + (double) i, d2 + 0.2D).tex(1.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) i, d2 + 0.2D).tex(0.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) k, d2 + 0.2D).tex(0.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) k, d2 + 0.8D).tex(1.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) i, d2 + 0.8D).tex(1.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.2D, d1 + (double) i, d2 + 0.8D).tex(0.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.2D, d1 + (double) k, d2 + 0.8D).tex(0.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) k, d2 + 0.2D).tex(1.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) i, d2 + 0.2D).tex(1.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) i, d2 + 0.8D).tex(0.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.8D, d1 + (double) k, d2 + 0.8D).tex(0.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.2D, d1 + (double) k, d2 + 0.8D).tex(1.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.2D, d1 + (double) i, d2 + 0.8D).tex(1.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.2D, d1 + (double) i, d2 + 0.2D).tex(0.0D, d15).color(f2, f3, f4, 0.125F)
						.endVertex();
				worldrenderer.pos(d0 + 0.2D, d1 + (double) k, d2 + 0.2D).tex(0.0D, d16).color(f2, f3, f4, 0.125F)
						.endVertex();
				tessellator.draw();
				GlStateManager.enableLighting();
				GlStateManager.enableTexture2D();
				GlStateManager.depthMask(true);
				i = k;
			}

			GlStateManager.enableFog();
		}

	}

	public boolean func_181055_a() {
		return true;
	}
}