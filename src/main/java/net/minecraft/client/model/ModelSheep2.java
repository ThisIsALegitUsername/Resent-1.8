package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

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
public class ModelSheep2 extends ModelQuadruped {
	private float headRotationAngleX;

	public ModelSheep2() {
		super(12, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
		this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
		this.body = new ModelRenderer(this, 28, 8);
		this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
		this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
	}

	/**+
	 * Used for easily adding entity-dependent animations. The
	 * second and third float params here are the same second and
	 * third as in the setRotationAngles method.
	 */
	public void setLivingAnimations(EntityLivingBase entitylivingbase, float f, float f1, float f2) {
		super.setLivingAnimations(entitylivingbase, f, f1, f2);
		this.head.rotationPointY = 6.0F + ((EntitySheep) entitylivingbase).getHeadRotationPointY(f2) * 9.0F;
		this.headRotationAngleX = ((EntitySheep) entitylivingbase).getHeadRotationAngleX(f2);
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
		this.head.rotateAngleX = this.headRotationAngleX;
	}
}