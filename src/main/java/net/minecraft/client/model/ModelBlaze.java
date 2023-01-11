package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

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
public class ModelBlaze extends ModelBase {
	/**+
	 * The sticks that fly around the Blaze.
	 */
	private ModelRenderer[] blazeSticks = new ModelRenderer[12];
	private ModelRenderer blazeHead;

	public ModelBlaze() {
		for (int i = 0; i < this.blazeSticks.length; ++i) {
			this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
			this.blazeSticks[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
		}

		this.blazeHead = new ModelRenderer(this, 0, 0);
		this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
	}

	/**+
	 * Sets the models various rotation angles then renders the
	 * model.
	 */
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.blazeHead.render(f5);

		for (int i = 0; i < this.blazeSticks.length; ++i) {
			this.blazeSticks[i].render(f5);
		}

	}

	/**+
	 * Sets the model's various rotation angles. For bipeds, par1
	 * and par2 are used for animating the movement of arms and
	 * legs, where par1 represents the time(so that arms and legs
	 * swing back and forth) and par2 represents how "far" arms and
	 * legs can swing at most.
	 */
	public void setRotationAngles(float var1, float var2, float f, float f1, float f2, float var6, Entity var7) {
		float f3 = f * 3.1415927F * -0.1F;

		for (int i = 0; i < 4; ++i) {
			this.blazeSticks[i].rotationPointY = -2.0F + MathHelper.cos(((float) (i * 2) + f) * 0.25F);
			this.blazeSticks[i].rotationPointX = MathHelper.cos(f3) * 9.0F;
			this.blazeSticks[i].rotationPointZ = MathHelper.sin(f3) * 9.0F;
			++f3;
		}

		f3 = 0.7853982F + f * 3.1415927F * 0.03F;

		for (int j = 4; j < 8; ++j) {
			this.blazeSticks[j].rotationPointY = 2.0F + MathHelper.cos(((float) (j * 2) + f) * 0.25F);
			this.blazeSticks[j].rotationPointX = MathHelper.cos(f3) * 7.0F;
			this.blazeSticks[j].rotationPointZ = MathHelper.sin(f3) * 7.0F;
			++f3;
		}

		f3 = 0.47123894F + f * 3.1415927F * -0.05F;

		for (int k = 8; k < 12; ++k) {
			this.blazeSticks[k].rotationPointY = 11.0F + MathHelper.cos(((float) k * 1.5F + f) * 0.5F);
			this.blazeSticks[k].rotationPointX = MathHelper.cos(f3) * 5.0F;
			this.blazeSticks[k].rotationPointZ = MathHelper.sin(f3) * 5.0F;
			++f3;
		}

		this.blazeHead.rotateAngleY = f1 / 57.295776F;
		this.blazeHead.rotateAngleX = f2 / 57.295776F;
	}
}