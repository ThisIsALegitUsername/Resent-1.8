package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.entity.monster.EntityBlaze;
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
public class RenderBlaze extends RenderLiving<EntityBlaze> {
	private static final ResourceLocation blazeTextures = new ResourceLocation("textures/entity/blaze.png");

	public RenderBlaze(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelBlaze(), 0.5F);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityBlaze var1) {
		return blazeTextures;
	}

	public void doRender(EntityBlaze entityliving, double d0, double d1, double d2, float f, float f1) {
		if (DeferredStateManager.isInDeferredPass()) {
			DeferredStateManager.setEmissionConstant(1.0f);
			try {
				super.doRender(entityliving, d0, d1, d2, f, f1);
			} finally {
				DeferredStateManager.setEmissionConstant(0.0f);
			}
		} else {
			super.doRender(entityliving, d0, d1, d2, f, f1);
		}
	}
}