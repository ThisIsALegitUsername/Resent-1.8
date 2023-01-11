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
public class ModelBoat extends ModelBase {
	public ModelRenderer[] boatSides = new ModelRenderer[5];

	public ModelBoat() {
		this.boatSides[0] = new ModelRenderer(this, 0, 8);
		this.boatSides[1] = new ModelRenderer(this, 0, 0);
		this.boatSides[2] = new ModelRenderer(this, 0, 0);
		this.boatSides[3] = new ModelRenderer(this, 0, 0);
		this.boatSides[4] = new ModelRenderer(this, 0, 0);
		byte b0 = 24;
		byte b1 = 6;
		byte b2 = 20;
		byte b3 = 4;
		this.boatSides[0].addBox((float) (-b0 / 2), (float) (-b2 / 2 + 2), -3.0F, b0, b2 - 4, 4, 0.0F);
		this.boatSides[0].setRotationPoint(0.0F, (float) b3, 0.0F);
		this.boatSides[1].addBox((float) (-b0 / 2 + 2), (float) (-b1 - 1), -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[1].setRotationPoint((float) (-b0 / 2 + 1), (float) b3, 0.0F);
		this.boatSides[2].addBox((float) (-b0 / 2 + 2), (float) (-b1 - 1), -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[2].setRotationPoint((float) (b0 / 2 - 1), (float) b3, 0.0F);
		this.boatSides[3].addBox((float) (-b0 / 2 + 2), (float) (-b1 - 1), -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[3].setRotationPoint(0.0F, (float) b3, (float) (-b2 / 2 + 1));
		this.boatSides[4].addBox((float) (-b0 / 2 + 2), (float) (-b1 - 1), -1.0F, b0 - 4, b1, 2, 0.0F);
		this.boatSides[4].setRotationPoint(0.0F, (float) b3, (float) (b2 / 2 - 1));
		this.boatSides[0].rotateAngleX = 1.5707964F;
		this.boatSides[1].rotateAngleY = 4.712389F;
		this.boatSides[2].rotateAngleY = 1.5707964F;
		this.boatSides[3].rotateAngleY = 3.1415927F;
	}

	/**+
	 * Sets the models various rotation angles then renders the
	 * model.
	 */
	public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float f) {
		for (int i = 0; i < 5; ++i) {
			this.boatSides[i].render(f);
		}

	}
}