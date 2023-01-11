package net.minecraft.client.renderer.tileentity;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelChest;
import net.minecraft.tileentity.TileEntityEnderChest;
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
public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer<TileEntityEnderChest> {
	private static final ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
	private ModelChest field_147521_c = new ModelChest();

	public void renderTileEntityAt(TileEntityEnderChest tileentityenderchest, double d0, double d1, double d2, float f,
			int i) {
		int j = 0;
		if (tileentityenderchest.hasWorldObj()) {
			j = tileentityenderchest.getBlockMetadata();
		}

		if (i >= 0) {
			this.bindTexture(DESTROY_STAGES[i]);
			GlStateManager.matrixMode(GL_TEXTURE);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(GL_MODELVIEW);
		} else {
			this.bindTexture(ENDER_CHEST_TEXTURE);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.translate((float) d0, (float) d1 + 1.0F, (float) d2 + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		short short1 = 0;
		if (j == 2) {
			short1 = 180;
		}

		if (j == 3) {
			short1 = 0;
		}

		if (j == 4) {
			short1 = 90;
		}

		if (j == 5) {
			short1 = -90;
		}

		GlStateManager.rotate((float) short1, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		float f1 = tileentityenderchest.prevLidAngle
				+ (tileentityenderchest.lidAngle - tileentityenderchest.prevLidAngle) * f;
		f1 = 1.0F - f1;
		f1 = 1.0F - f1 * f1 * f1;
		this.field_147521_c.chestLid.rotateAngleX = -(f1 * 3.1415927F / 2.0F);
		this.field_147521_c.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (i >= 0) {
			GlStateManager.matrixMode(GL_TEXTURE);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(GL_MODELVIEW);
		}

	}
}