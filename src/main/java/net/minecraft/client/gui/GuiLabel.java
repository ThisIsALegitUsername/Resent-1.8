package net.minecraft.client.gui;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

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
public class GuiLabel extends Gui {
	protected int field_146167_a = 200;
	protected int field_146161_f = 20;
	public int field_146162_g;
	public int field_146174_h;
	private List<String> field_146173_k;
	public int field_175204_i;
	private boolean centered;
	public boolean visible = true;
	private boolean labelBgEnabled;
	private int field_146168_n;
	private int field_146169_o;
	private int field_146166_p;
	private int field_146165_q;
	private FontRenderer fontRenderer;
	private int field_146163_s;

	public GuiLabel(FontRenderer fontRendererObj, int parInt1, int parInt2, int parInt3, int parInt4, int parInt5,
			int parInt6) {
		this.fontRenderer = fontRendererObj;
		this.field_175204_i = parInt1;
		this.field_146162_g = parInt2;
		this.field_146174_h = parInt3;
		this.field_146167_a = parInt4;
		this.field_146161_f = parInt5;
		this.field_146173_k = Lists.newArrayList();
		this.centered = false;
		this.labelBgEnabled = false;
		this.field_146168_n = parInt6;
		this.field_146169_o = -1;
		this.field_146166_p = -1;
		this.field_146165_q = -1;
		this.field_146163_s = 0;
	}

	public void func_175202_a(String parString1) {
		this.field_146173_k.add(I18n.format(parString1, new Object[0]));
	}

	/**+
	 * Sets the Label to be centered
	 */
	public GuiLabel setCentered() {
		this.centered = true;
		return this;
	}

	public void drawLabel(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			this.drawLabelBackground(mc, mouseX, mouseY);
			int i = this.field_146174_h + this.field_146161_f / 2 + this.field_146163_s / 2;
			int j = i - this.field_146173_k.size() * 10 / 2;

			for (int k = 0; k < this.field_146173_k.size(); ++k) {
				if (this.centered) {
					this.drawCenteredString(this.fontRenderer, (String) this.field_146173_k.get(k),
							this.field_146162_g + this.field_146167_a / 2, j + k * 10, this.field_146168_n);
				} else {
					this.drawString(this.fontRenderer, (String) this.field_146173_k.get(k), this.field_146162_g,
							j + k * 10, this.field_146168_n);
				}
			}

		}
	}

	protected void drawLabelBackground(Minecraft mcIn, int parInt1, int parInt2) {
		if (this.labelBgEnabled) {
			int i = this.field_146167_a + this.field_146163_s * 2;
			int j = this.field_146161_f + this.field_146163_s * 2;
			int k = this.field_146162_g - this.field_146163_s;
			int l = this.field_146174_h - this.field_146163_s;
			drawRect(k, l, k + i, l + j, this.field_146169_o);
			this.drawHorizontalLine(k, k + i, l, this.field_146166_p);
			this.drawHorizontalLine(k, k + i, l + j, this.field_146165_q);
			this.drawVerticalLine(k, l, l + j, this.field_146166_p);
			this.drawVerticalLine(k + i, l, l + j, this.field_146165_q);
		}

	}
}