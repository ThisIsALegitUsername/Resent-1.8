package net.minecraft.client.gui;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;

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
public class GuiLockIconButton extends GuiButton {
	private boolean field_175231_o = false;

	public GuiLockIconButton(int parInt1, int parInt2, int parInt3) {
		super(parInt1, parInt2, parInt3, 20, 20, "");
	}

	public boolean func_175230_c() {
		return this.field_175231_o;
	}

	public void func_175229_b(boolean parFlag) {
		this.field_175231_o = parFlag;
	}

	/**+
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft minecraft, int i, int j) {
		if (this.visible) {
			minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			boolean flag = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width
					&& j < this.yPosition + this.height;
			GuiLockIconButton.Icon guilockiconbutton$icon;
			if (this.field_175231_o) {
				if (!this.enabled) {
					guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED_DISABLED;
				} else if (flag) {
					guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED_HOVER;
				} else {
					guilockiconbutton$icon = GuiLockIconButton.Icon.LOCKED;
				}
			} else if (!this.enabled) {
				guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED_DISABLED;
			} else if (flag) {
				guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED_HOVER;
			} else {
				guilockiconbutton$icon = GuiLockIconButton.Icon.UNLOCKED;
			}

			this.drawTexturedModalRect(this.xPosition, this.yPosition, guilockiconbutton$icon.func_178910_a(),
					guilockiconbutton$icon.func_178912_b(), this.width, this.height);
		}
	}

	static enum Icon {
		LOCKED(0, 146), LOCKED_HOVER(0, 166), LOCKED_DISABLED(0, 186), UNLOCKED(20, 146), UNLOCKED_HOVER(20, 166),
		UNLOCKED_DISABLED(20, 186);

		private final int field_178914_g;
		private final int field_178920_h;

		private Icon(int parInt2, int parInt3) {
			this.field_178914_g = parInt2;
			this.field_178920_h = parInt3;
		}

		public int func_178910_a() {
			return this.field_178914_g;
		}

		public int func_178912_b() {
			return this.field_178920_h;
		}
	}
}