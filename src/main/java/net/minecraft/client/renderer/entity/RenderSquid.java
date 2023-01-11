package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.passive.EntitySquid;
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
public class RenderSquid extends RenderLiving<EntitySquid> {
	private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");

	public RenderSquid(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
		super(renderManagerIn, modelBaseIn, shadowSizeIn);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntitySquid var1) {
		return squidTextures;
	}

	protected void rotateCorpse(EntitySquid entitysquid, float var2, float f, float f1) {
		float f2 = entitysquid.prevSquidPitch + (entitysquid.squidPitch - entitysquid.prevSquidPitch) * f1;
		float f3 = entitysquid.prevSquidYaw + (entitysquid.squidYaw - entitysquid.prevSquidYaw) * f1;
		GlStateManager.translate(0.0F, 0.5F, 0.0F);
		GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, -1.2F, 0.0F);
	}

	/**+
	 * Defines what float the third param in setRotationAngles of
	 * ModelBase is
	 */
	protected float handleRotationFloat(EntitySquid entitysquid, float f) {
		return entitysquid.lastTentacleAngle + (entitysquid.tentacleAngle - entitysquid.lastTentacleAngle) * f;
	}
}