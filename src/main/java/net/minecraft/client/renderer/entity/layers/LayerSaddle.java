package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.passive.EntityPig;
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
public class LayerSaddle implements LayerRenderer<EntityPig> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
	private final RenderPig pigRenderer;
	private final ModelPig pigModel = new ModelPig(0.5F);

	public LayerSaddle(RenderPig pigRendererIn) {
		this.pigRenderer = pigRendererIn;
	}

	public void doRenderLayer(EntityPig entitypig, float f, float f1, float var4, float f2, float f3, float f4,
			float f5) {
		if (entitypig.getSaddled()) {
			this.pigRenderer.bindTexture(TEXTURE);
			this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
			this.pigModel.render(entitypig, f, f1, f2, f3, f4, f5);
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}