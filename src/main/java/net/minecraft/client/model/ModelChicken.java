package net.minecraft.client.model;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
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
public class ModelChicken extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;
	public ModelRenderer bill;
	public ModelRenderer chin;

	public ModelChicken() {
		byte b0 = 16;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
		this.head.setRotationPoint(0.0F, (float) (-1 + b0), -4.0F);
		this.bill = new ModelRenderer(this, 14, 0);
		this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
		this.bill.setRotationPoint(0.0F, (float) (-1 + b0), -4.0F);
		this.chin = new ModelRenderer(this, 14, 4);
		this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
		this.chin.setRotationPoint(0.0F, (float) (-1 + b0), -4.0F);
		this.body = new ModelRenderer(this, 0, 9);
		this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
		this.body.setRotationPoint(0.0F, (float) b0, 0.0F);
		this.rightLeg = new ModelRenderer(this, 26, 0);
		this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		this.rightLeg.setRotationPoint(-2.0F, (float) (3 + b0), 1.0F);
		this.leftLeg = new ModelRenderer(this, 26, 0);
		this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		this.leftLeg.setRotationPoint(1.0F, (float) (3 + b0), 1.0F);
		this.rightWing = new ModelRenderer(this, 24, 13);
		this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
		this.rightWing.setRotationPoint(-4.0F, (float) (-3 + b0), 0.0F);
		this.leftWing = new ModelRenderer(this, 24, 13);
		this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
		this.leftWing.setRotationPoint(4.0F, (float) (-3 + b0), 0.0F);
	}

	/**+
	 * Sets the models various rotation angles then renders the
	 * model.
	 */
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (this.isChild) {
			float f6 = 2.0F;
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 5.0F * f5, 2.0F * f5);
			this.head.render(f5);
			this.bill.render(f5);
			this.chin.render(f5);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
			this.body.render(f5);
			this.rightLeg.render(f5);
			this.leftLeg.render(f5);
			this.rightWing.render(f5);
			this.leftWing.render(f5);
			GlStateManager.popMatrix();
		} else {
			this.head.render(f5);
			this.bill.render(f5);
			this.chin.render(f5);
			this.body.render(f5);
			this.rightLeg.render(f5);
			this.leftLeg.render(f5);
			this.rightWing.render(f5);
			this.leftWing.render(f5);
		}

	}

	/**+
	 * Sets the model's various rotation angles. For bipeds, par1
	 * and par2 are used for animating the movement of arms and
	 * legs, where par1 represents the time(so that arms and legs
	 * swing back and forth) and par2 represents how "far" arms and
	 * legs can swing at most.
	 */
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float var6, Entity var7) {
		this.head.rotateAngleX = f4 / 57.295776F;
		this.head.rotateAngleY = f3 / 57.295776F;
		this.bill.rotateAngleX = this.head.rotateAngleX;
		this.bill.rotateAngleY = this.head.rotateAngleY;
		this.chin.rotateAngleX = this.head.rotateAngleX;
		this.chin.rotateAngleY = this.head.rotateAngleY;
		this.body.rotateAngleX = 1.5707964F;
		this.rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		this.leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
		this.rightWing.rotateAngleZ = f2;
		this.leftWing.rotateAngleZ = -f2;
	}
}