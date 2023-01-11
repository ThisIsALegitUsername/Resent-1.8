package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.passive.EntityVillager;
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
public class RenderVillager extends RenderLiving<EntityVillager> {
	private static final ResourceLocation villagerTextures = new ResourceLocation(
			"textures/entity/villager/villager.png");
	private static final ResourceLocation farmerVillagerTextures = new ResourceLocation(
			"textures/entity/villager/farmer.png");
	private static final ResourceLocation librarianVillagerTextures = new ResourceLocation(
			"textures/entity/villager/librarian.png");
	private static final ResourceLocation priestVillagerTextures = new ResourceLocation(
			"textures/entity/villager/priest.png");
	private static final ResourceLocation smithVillagerTextures = new ResourceLocation(
			"textures/entity/villager/smith.png");
	private static final ResourceLocation butcherVillagerTextures = new ResourceLocation(
			"textures/entity/villager/butcher.png");

	public RenderVillager(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelVillager(0.0F), 0.5F);
		this.addLayer(new LayerCustomHead(this.getMainModel().villagerHead));
	}

	public ModelVillager getMainModel() {
		return (ModelVillager) super.getMainModel();
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityVillager entityvillager) {
		switch (entityvillager.getProfession()) {
		case 0:
			return farmerVillagerTextures;
		case 1:
			return librarianVillagerTextures;
		case 2:
			return priestVillagerTextures;
		case 3:
			return smithVillagerTextures;
		case 4:
			return butcherVillagerTextures;
		default:
			return villagerTextures;
		}
	}

	/**+
	 * Allows the render to do any OpenGL state modifications
	 * necessary before the model is rendered. Args: entityLiving,
	 * partialTickTime
	 */
	protected void preRenderCallback(EntityVillager entityvillager, float var2) {
		float f = 0.9375F;
		if (entityvillager.getGrowingAge() < 0) {
			f = (float) ((double) f * 0.5D);
			this.shadowSize = 0.25F;
		} else {
			this.shadowSize = 0.5F;
		}

		GlStateManager.scale(f, f, f);
	}
}