package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
import net.minecraft.entity.monster.EntityEnderman;
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
public class RenderEnderman extends RenderLiving<EntityEnderman> {
	private static final ResourceLocation endermanTextures = new ResourceLocation(
			"textures/entity/enderman/enderman.png");
	private ModelEnderman endermanModel;
	private EaglercraftRandom rnd = new EaglercraftRandom();

	public RenderEnderman(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelEnderman(0.0F), 0.5F);
		this.endermanModel = (ModelEnderman) super.mainModel;
		this.addLayer(new LayerEndermanEyes(this));
		this.addLayer(new LayerHeldBlock(this));
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
	public void doRender(EntityEnderman entityenderman, double d0, double d1, double d2, float f, float f1) {
		this.endermanModel.isCarrying = entityenderman.getHeldBlockState().getBlock().getMaterial() != Material.air;
		this.endermanModel.isAttacking = entityenderman.isScreaming();
		if (entityenderman.isScreaming()) {
			double d3 = 0.02D;
			d0 += this.rnd.nextGaussian() * d3;
			d2 += this.rnd.nextGaussian() * d3;
		}

		super.doRender(entityenderman, d0, d1, d2, f, f1);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityEnderman var1) {
		return endermanTextures;
	}
}