package net.minecraft.client.renderer.entity.layers;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
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
public class LayerWolfCollar implements LayerRenderer<EntityWolf> {
	private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
	private final RenderWolf wolfRenderer;

	public LayerWolfCollar(RenderWolf wolfRendererIn) {
		this.wolfRenderer = wolfRendererIn;
	}

	public void doRenderLayer(EntityWolf entitywolf, float f, float f1, float var4, float f2, float f3, float f4,
			float f5) {
		if (entitywolf.isTamed() && !entitywolf.isInvisible()) {
			this.wolfRenderer.bindTexture(WOLF_COLLAR);
			EnumDyeColor enumdyecolor = EnumDyeColor.byMetadata(entitywolf.getCollarColor().getMetadata());
			float[] afloat = EntitySheep.func_175513_a(enumdyecolor);
			GlStateManager.color(afloat[0], afloat[1], afloat[2]);
			this.wolfRenderer.getMainModel().render(entitywolf, f, f1, f2, f3, f4, f5);
		}
	}

	public boolean shouldCombineTextures() {
		return true;
	}
}