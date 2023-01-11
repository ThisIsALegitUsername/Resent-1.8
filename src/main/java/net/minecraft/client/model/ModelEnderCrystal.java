package net.minecraft.client.model;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
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
public class ModelEnderCrystal extends ModelBase {
	private ModelRenderer cube;
	/**+
	 * The glass model for the Ender Crystal.
	 */
	private ModelRenderer glass = new ModelRenderer(this, "glass");
	private ModelRenderer base;

	public ModelEnderCrystal(float parFloat1, boolean parFlag) {
		this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		this.cube = new ModelRenderer(this, "cube");
		this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		if (parFlag) {
			this.base = new ModelRenderer(this, "base");
			this.base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
		}

	}

	/**+
	 * Sets the models various rotation angles then renders the
	 * model.
	 */
	public void render(Entity var1, float var2, float f, float f1, float var5, float var6, float f2) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		GlStateManager.translate(0.0F, -0.5F, 0.0F);
		if (this.base != null) {
			this.base.render(f2);
		}

		GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, 0.8F + f1, 0.0F);
		GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
		this.glass.render(f2);
		float f3 = 0.875F;
		GlStateManager.scale(f3, f3, f3);
		GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
		GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
		this.glass.render(f2);
		GlStateManager.scale(f3, f3, f3);
		GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
		GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
		this.cube.render(f2);
		GlStateManager.popMatrix();
	}
}