package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.entity.layers.LayerIronGolemFlower;
import net.minecraft.entity.monster.EntityIronGolem;
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
public class RenderIronGolem extends RenderLiving<EntityIronGolem> {
	private static final ResourceLocation ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");

	public RenderIronGolem(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelIronGolem(), 0.5F);
		this.addLayer(new LayerIronGolemFlower(this));
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityIronGolem var1) {
		return ironGolemTextures;
	}

	protected void rotateCorpse(EntityIronGolem entityirongolem, float f, float f1, float f2) {
		super.rotateCorpse(entityirongolem, f, f1, f2);
		if ((double) entityirongolem.limbSwingAmount >= 0.01D) {
			float f3 = 13.0F;
			float f4 = entityirongolem.limbSwing - entityirongolem.limbSwingAmount * (1.0F - f2) + 6.0F;
			float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
			GlStateManager.rotate(6.5F * f5, 0.0F, 0.0F, 1.0F);
		}
	}
}