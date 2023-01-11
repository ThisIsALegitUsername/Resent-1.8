package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MathHelper;
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
public class RenderBoat extends Render<EntityBoat> {
	private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
	/**+
	 * instance of ModelBoat for rendering
	 */
	protected ModelBase modelBoat = new ModelBoat();

	public RenderBoat(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
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
	public void doRender(EntityBoat entityboat, double d0, double d1, double d2, float f, float f1) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) d0, (float) d1 + 0.25F, (float) d2);
		GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
		float f2 = (float) entityboat.getTimeSinceHit() - f1;
		float f3 = entityboat.getDamageTaken() - f1;
		if (f3 < 0.0F) {
			f3 = 0.0F;
		}

		if (f2 > 0.0F) {
			GlStateManager.rotate(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float) entityboat.getForwardDirection(), 1.0F,
					0.0F, 0.0F);
		}

		float f4 = 0.75F;
		GlStateManager.scale(f4, f4, f4);
		GlStateManager.scale(1.0F / f4, 1.0F / f4, 1.0F / f4);
		this.bindEntityTexture(entityboat);
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		this.modelBoat.render(entityboat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		super.doRender(entityboat, d0, d1, d2, f, f1);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityBoat var1) {
		return boatTextures;
	}
}