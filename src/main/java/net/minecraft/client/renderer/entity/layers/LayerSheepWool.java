package net.minecraft.client.renderer.entity.layers;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
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
public class LayerSheepWool implements LayerRenderer<EntitySheep> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
	private final RenderSheep sheepRenderer;
	private final ModelSheep1 sheepModel = new ModelSheep1();

	public LayerSheepWool(RenderSheep sheepRendererIn) {
		this.sheepRenderer = sheepRendererIn;
	}

	public void doRenderLayer(EntitySheep entitysheep, float f, float f1, float f2, float f3, float f4, float f5,
			float f6) {
		if (!entitysheep.getSheared() && !entitysheep.isInvisible()) {
			this.sheepRenderer.bindTexture(TEXTURE);
			if (entitysheep.hasCustomName() && "jeb_".equals(entitysheep.getCustomNameTag())) {
				boolean flag = true;
				int i = entitysheep.ticksExisted / 25 + entitysheep.getEntityId();
				int j = EnumDyeColor.values().length;
				int k = i % j;
				int l = (i + 1) % j;
				float f7 = ((float) (entitysheep.ticksExisted % 25) + f2) / 25.0F;
				float[] afloat1 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(k));
				float[] afloat2 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(l));
				GlStateManager.color(afloat1[0] * (1.0F - f7) + afloat2[0] * f7,
						afloat1[1] * (1.0F - f7) + afloat2[1] * f7, afloat1[2] * (1.0F - f7) + afloat2[2] * f7);
			} else {
				float[] afloat = EntitySheep.func_175513_a(entitysheep.getFleeceColor());
				GlStateManager.color(afloat[0], afloat[1], afloat[2]);
			}

			this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
			this.sheepModel.setLivingAnimations(entitysheep, f, f1, f2);
			this.sheepModel.render(entitysheep, f, f1, f3, f4, f5, f6);
		}
	}

	public boolean shouldCombineTextures() {
		return true;
	}
}