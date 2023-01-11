package net.minecraft.client.renderer.entity.layers;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
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
public class LayerArrow implements LayerRenderer<EntityLivingBase> {
	private final RendererLivingEntity field_177168_a;

	public LayerArrow(RendererLivingEntity parRendererLivingEntity) {
		this.field_177168_a = parRendererLivingEntity;
	}

	public void doRenderLayer(EntityLivingBase entitylivingbase, float var2, float var3, float f, float var5,
			float var6, float var7, float var8) {
		int i = entitylivingbase.getArrowCountInEntity();
		if (i > 0) {
			EntityArrow entityarrow = new EntityArrow(entitylivingbase.worldObj, entitylivingbase.posX,
					entitylivingbase.posY, entitylivingbase.posZ);
			EaglercraftRandom random = new EaglercraftRandom((long) entitylivingbase.getEntityId());
			GlStateManager.disableLighting();

			for (int j = 0; j < i; ++j) {
				GlStateManager.pushMatrix();
				ModelRenderer modelrenderer = this.field_177168_a.getMainModel().getRandomModelBox(random);
				ModelBox modelbox = (ModelBox) modelrenderer.cubeList
						.get(random.nextInt(modelrenderer.cubeList.size()));
				modelrenderer.postRender(0.0625F);
				float f1 = random.nextFloat();
				float f2 = random.nextFloat();
				float f3 = random.nextFloat();
				float f4 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f1) / 16.0F;
				float f5 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f2) / 16.0F;
				float f6 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f3) / 16.0F;
				GlStateManager.translate(f4, f5, f6);
				f1 = f1 * 2.0F - 1.0F;
				f2 = f2 * 2.0F - 1.0F;
				f3 = f3 * 2.0F - 1.0F;
				f1 = f1 * -1.0F;
				f2 = f2 * -1.0F;
				f3 = f3 * -1.0F;
				float f7 = MathHelper.sqrt_float(f1 * f1 + f3 * f3);
				entityarrow.prevRotationYaw = entityarrow.rotationYaw = (float) (Math.atan2((double) f1, (double) f3)
						* 180.0D / 3.1415927410125732D);
				entityarrow.prevRotationPitch = entityarrow.rotationPitch = (float) (Math.atan2((double) f2,
						(double) f7) * 180.0D / 3.1415927410125732D);
				double d0 = 0.0D;
				double d1 = 0.0D;
				double d2 = 0.0D;
				this.field_177168_a.getRenderManager().renderEntityWithPosYaw(entityarrow, d0, d1, d2, 0.0F, f);
				GlStateManager.popMatrix();
			}

			GlStateManager.enableLighting();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}