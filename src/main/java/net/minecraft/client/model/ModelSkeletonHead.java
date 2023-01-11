package net.minecraft.client.model;

import net.minecraft.entity.Entity;

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
public class ModelSkeletonHead extends ModelBase {
	public ModelRenderer skeletonHead;

	public ModelSkeletonHead() {
		this(0, 35, 64, 64);
	}

	public ModelSkeletonHead(int parInt1, int parInt2, int parInt3, int parInt4) {
		this.textureWidth = parInt3;
		this.textureHeight = parInt4;
		this.skeletonHead = new ModelRenderer(this, parInt1, parInt2);
		this.skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	/**+
	 * Sets the models various rotation angles then renders the
	 * model.
	 */
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.skeletonHead.render(f5);
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
		this.skeletonHead.rotateAngleY = f3 / 57.295776F;
		this.skeletonHead.rotateAngleX = f4 / 57.295776F;
	}
}