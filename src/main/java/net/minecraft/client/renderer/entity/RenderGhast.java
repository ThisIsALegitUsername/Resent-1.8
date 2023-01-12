package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.entity.monster.EntityGhast;
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
public class RenderGhast extends RenderLiving<EntityGhast> {
	private static final ResourceLocation ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
	private static final ResourceLocation ghastShootingTextures = new ResourceLocation(
			"textures/entity/ghast/ghast_shooting.png");

	public RenderGhast(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelGhast(), 0.5F);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityGhast entityghast) {
		return entityghast.isAttacking() ? ghastShootingTextures : ghastTextures;
	}

	/**+
	 * Allows the render to do any OpenGL state modifications
	 * necessary before the model is rendered. Args: entityLiving,
	 * partialTickTime
	 */
	protected void preRenderCallback(EntityGhast var1, float var2) {
		float f = 1.0F;
		float f1 = (8.0F + f) / 2.0F;
		float f2 = (8.0F + 1.0F / f) / 2.0F;
		GlStateManager.scale(f2, f1, f2);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}