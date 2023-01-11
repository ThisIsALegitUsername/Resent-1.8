package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.entity.layers.LayerHeldItemWitch;
import net.minecraft.entity.monster.EntityWitch;
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
public class RenderWitch extends RenderLiving<EntityWitch> {
	private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");

	public RenderWitch(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelWitch(0.0F), 0.5F);
		this.addLayer(new LayerHeldItemWitch(this));
	}

	/**+
	 * Actually renders the given argument. This is a synthetic
	 * bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual
	 * work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(EntityWitch entitywitch, double d0, double d1, double d2, float f, float f1) {
		((ModelWitch) this.mainModel).field_82900_g = entitywitch.getHeldItem() != null;
		super.doRender(entitywitch, d0, d1, d2, f, f1);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityWitch var1) {
		return witchTextures;
	}

	public void transformHeldFull3DItemLayer() {
		GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	}

	/**+
	 * Allows the render to do any OpenGL state modifications
	 * necessary before the model is rendered. Args: entityLiving,
	 * partialTickTime
	 */
	protected void preRenderCallback(EntityWitch var1, float var2) {
		float f = 0.9375F;
		GlStateManager.scale(f, f, f);
	}
}