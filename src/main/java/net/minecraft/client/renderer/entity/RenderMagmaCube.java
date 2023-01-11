package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.entity.monster.EntityMagmaCube;
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
public class RenderMagmaCube extends RenderLiving<EntityMagmaCube> {
	private static final ResourceLocation magmaCubeTextures = new ResourceLocation(
			"textures/entity/slime/magmacube.png");

	public RenderMagmaCube(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelMagmaCube(), 0.25F);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityMagmaCube var1) {
		return magmaCubeTextures;
	}

	/**+
	 * Allows the render to do any OpenGL state modifications
	 * necessary before the model is rendered. Args: entityLiving,
	 * partialTickTime
	 */
	protected void preRenderCallback(EntityMagmaCube entitymagmacube, float f) {
		int i = entitymagmacube.getSlimeSize();
		float f1 = (entitymagmacube.prevSquishFactor
				+ (entitymagmacube.squishFactor - entitymagmacube.prevSquishFactor) * f) / ((float) i * 0.5F + 1.0F);
		float f2 = 1.0F / (f1 + 1.0F);
		float f3 = (float) i;
		GlStateManager.scale(f2 * f3, 1.0F / f2 * f3, f2 * f3);
	}
}