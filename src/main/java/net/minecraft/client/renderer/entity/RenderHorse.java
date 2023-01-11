package net.minecraft.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityHorse;
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
public class RenderHorse extends RenderLiving<EntityHorse> {
	private static final Map<String, ResourceLocation> field_110852_a = Maps.newHashMap();
	private static final ResourceLocation whiteHorseTextures = new ResourceLocation(
			"textures/entity/horse/horse_white.png");
	private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
	private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
	private static final ResourceLocation zombieHorseTextures = new ResourceLocation(
			"textures/entity/horse/horse_zombie.png");
	private static final ResourceLocation skeletonHorseTextures = new ResourceLocation(
			"textures/entity/horse/horse_skeleton.png");

	public RenderHorse(RenderManager rendermanagerIn, ModelHorse model, float shadowSizeIn) {
		super(rendermanagerIn, model, shadowSizeIn);
	}

	/**+
	 * Allows the render to do any OpenGL state modifications
	 * necessary before the model is rendered. Args: entityLiving,
	 * partialTickTime
	 */
	protected void preRenderCallback(EntityHorse entityhorse, float f) {
		float f1 = 1.0F;
		int i = entityhorse.getHorseType();
		if (i == 1) {
			f1 *= 0.87F;
		} else if (i == 2) {
			f1 *= 0.92F;
		}

		GlStateManager.scale(f1, f1, f1);
		super.preRenderCallback(entityhorse, f);
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityHorse entityhorse) {
		if (!entityhorse.func_110239_cn()) {
			switch (entityhorse.getHorseType()) {
			case 0:
			default:
				return whiteHorseTextures;
			case 1:
				return donkeyTextures;
			case 2:
				return muleTextures;
			case 3:
				return zombieHorseTextures;
			case 4:
				return skeletonHorseTextures;
			}
		} else {
			return this.func_110848_b(entityhorse);
		}
	}

	private ResourceLocation func_110848_b(EntityHorse horse) {
		String s = horse.getHorseTexture();
		if (!horse.func_175507_cI()) {
			return null;
		} else {
			ResourceLocation resourcelocation = (ResourceLocation) field_110852_a.get(s);
			if (resourcelocation == null) {
				resourcelocation = new ResourceLocation(s);
				Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation,
						new LayeredTexture(horse.getVariantTexturePaths()));
				field_110852_a.put(s, resourcelocation);
			}

			return resourcelocation;
		}
	}
}