package net.minecraft.client.renderer.tileentity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelBook;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
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
public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentTable> {
	/**+
	 * The texture for the book above the enchantment table.
	 */
	private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation(
			"textures/entity/enchanting_table_book.png");
	private ModelBook field_147541_c = new ModelBook();

	public void renderTileEntityAt(TileEntityEnchantmentTable tileentityenchantmenttable, double d0, double d1,
			double d2, float f, int var9) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) d0 + 0.5F, (float) d1 + 0.75F, (float) d2 + 0.5F);
		float f1 = (float) tileentityenchantmenttable.tickCount + f;
		GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);

		float f2;
		for (f2 = tileentityenchantmenttable.bookRotation
				- tileentityenchantmenttable.bookRotationPrev; f2 >= 3.1415927F; f2 -= 6.2831855F) {
			;
		}

		while (f2 < -3.1415927F) {
			f2 += 6.2831855F;
		}

		float f3 = tileentityenchantmenttable.bookRotationPrev + f2 * f;
		GlStateManager.rotate(-f3 * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
		this.bindTexture(TEXTURE_BOOK);
		float f4 = tileentityenchantmenttable.pageFlipPrev
				+ (tileentityenchantmenttable.pageFlip - tileentityenchantmenttable.pageFlipPrev) * f + 0.25F;
		float f5 = tileentityenchantmenttable.pageFlipPrev
				+ (tileentityenchantmenttable.pageFlip - tileentityenchantmenttable.pageFlipPrev) * f + 0.75F;
		f4 = (f4 - (float) MathHelper.truncateDoubleToInt((double) f4)) * 1.6F - 0.3F;
		f5 = (f5 - (float) MathHelper.truncateDoubleToInt((double) f5)) * 1.6F - 0.3F;
		if (f4 < 0.0F) {
			f4 = 0.0F;
		}

		if (f5 < 0.0F) {
			f5 = 0.0F;
		}

		if (f4 > 1.0F) {
			f4 = 1.0F;
		}

		if (f5 > 1.0F) {
			f5 = 1.0F;
		}

		float f6 = tileentityenchantmenttable.bookSpreadPrev
				+ (tileentityenchantmenttable.bookSpread - tileentityenchantmenttable.bookSpreadPrev) * f;
		GlStateManager.enableCull();
		this.field_147541_c.render((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
	}
}