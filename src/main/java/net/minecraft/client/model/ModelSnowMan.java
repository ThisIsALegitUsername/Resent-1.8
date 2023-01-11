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
public class ModelSnowMan extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer bottomBody;
	public ModelRenderer head;
	public ModelRenderer rightHand;
	public ModelRenderer leftHand;

	public ModelSnowMan() {
		float f = 4.0F;
		float f1 = 0.0F;
		this.head = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f1 - 0.5F);
		this.head.setRotationPoint(0.0F, 0.0F + f, 0.0F);
		this.rightHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
		this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, f1 - 0.5F);
		this.rightHand.setRotationPoint(0.0F, 0.0F + f + 9.0F - 7.0F, 0.0F);
		this.leftHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
		this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, f1 - 0.5F);
		this.leftHand.setRotationPoint(0.0F, 0.0F + f + 9.0F - 7.0F, 0.0F);
		this.body = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 64);
		this.body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, f1 - 0.5F);
		this.body.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
		this.bottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
		this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, f1 - 0.5F);
		this.bottomBody.setRotationPoint(0.0F, 0.0F + f + 20.0F, 0.0F);
	}

	/**+
	 * Sets the model's various rotation angles. For bipeds, par1
	 * and par2 are used for animating the movement of arms and
	 * legs, where par1 represents the time(so that arms and legs
	 * swing back and forth) and par2 represents how "far" arms and
	 * legs can swing at most.
	 */
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.head.rotateAngleY = f3 / 57.295776F;
		this.head.rotateAngleX = f4 / 57.295776F;
		this.body.rotateAngleY = f3 / 57.295776F * 0.25F;
		float f6 = MathHelper.sin(this.body.rotateAngleY);
		float f7 = MathHelper.cos(this.body.rotateAngleY);
		this.rightHand.rotateAngleZ = 1.0F;
		this.leftHand.rotateAngleZ = -1.0F;
		this.rightHand.rotateAngleY = 0.0F + this.body.rotateAngleY;
		this.leftHand.rotateAngleY = 3.1415927F + this.body.rotateAngleY;
		this.rightHand.rotationPointX = f7 * 5.0F;
		this.rightHand.rotationPointZ = -f6 * 5.0F;
		this.leftHand.rotationPointX = -f7 * 5.0F;
		this.leftHand.rotationPointZ = f6 * 5.0F;
	}

	/**+
	 * Sets the models various rotation angles then renders the
	 * model.
	 */
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.body.render(f5);
		this.bottomBody.render(f5);
		this.head.render(f5);
		this.rightHand.render(f5);
		this.leftHand.render(f5);
	}
}