package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

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
public class ModelMagmaCube extends ModelBase {
	ModelRenderer[] segments = new ModelRenderer[8];
	ModelRenderer core;

	public ModelMagmaCube() {
		for (int i = 0; i < this.segments.length; ++i) {
			byte b0 = 0;
			int j = i;
			if (i == 2) {
				b0 = 24;
				j = 10;
			} else if (i == 3) {
				b0 = 24;
				j = 19;
			}

			this.segments[i] = new ModelRenderer(this, b0, j);
			this.segments[i].addBox(-4.0F, (float) (16 + i), -4.0F, 8, 1, 8);
		}

		this.core = new ModelRenderer(this, 0, 16);
		this.core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
	}

	/**+
	 * Used for easily adding entity-dependent animations. The
	 * second and third float params here are the same second and
	 * third as in the setRotationAngles method.
	 */
	public void setLivingAnimations(EntityLivingBase entitylivingbase, float var2, float var3, float f) {
		EntityMagmaCube entitymagmacube = (EntityMagmaCube) entitylivingbase;
		float f1 = entitymagmacube.prevSquishFactor
				+ (entitymagmacube.squishFactor - entitymagmacube.prevSquishFactor) * f;
		if (f1 < 0.0F) {
			f1 = 0.0F;
		}

		for (int i = 0; i < this.segments.length; ++i) {
			this.segments[i].rotationPointY = (float) (-(4 - i)) * f1 * 1.7F;
		}

	}

	/**+
	 * Sets the models various rotation angles then renders the
	 * model.
	 */
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.core.render(f5);

		for (int i = 0; i < this.segments.length; ++i) {
			this.segments[i].render(f5);
		}

	}
}