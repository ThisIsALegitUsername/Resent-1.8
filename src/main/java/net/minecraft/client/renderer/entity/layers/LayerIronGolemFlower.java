package net.minecraft.client.renderer.entity.layers;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.OpenGlHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntityIronGolem;
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
public class LayerIronGolemFlower implements LayerRenderer<EntityIronGolem> {
	private final RenderIronGolem ironGolemRenderer;

	public LayerIronGolemFlower(RenderIronGolem ironGolemRendererIn) {
		this.ironGolemRenderer = ironGolemRendererIn;
	}

	public void doRenderLayer(EntityIronGolem entityirongolem, float var2, float var3, float f, float var5, float var6,
			float var7, float var8) {
		if (entityirongolem.getHoldRoseTick() != 0) {
			BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			GlStateManager.enableRescaleNormal();
			GlStateManager.pushMatrix();
			GlStateManager.rotate(5.0F
					+ 180.0F * ((ModelIronGolem) this.ironGolemRenderer.getMainModel()).ironGolemRightArm.rotateAngleX
							/ 3.1415927F,
					1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(-0.9375F, -0.625F, -0.9375F);
			float f1 = 0.5F;
			GlStateManager.scale(f1, -f1, f1);
			int i = entityirongolem.getBrightnessForRender(f);
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.ironGolemRenderer.bindTexture(TextureMap.locationBlocksTexture);
			blockrendererdispatcher.renderBlockBrightness(Blocks.red_flower.getDefaultState(), 1.0F);
			GlStateManager.popMatrix();
			GlStateManager.disableRescaleNormal();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}