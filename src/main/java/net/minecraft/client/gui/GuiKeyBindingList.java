package net.minecraft.client.gui;

import java.util.Arrays;

import net.lax1dude.eaglercraft.v1_8.ArrayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

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
public class GuiKeyBindingList extends GuiListExtended {
	private final GuiControls field_148191_k;
	private final Minecraft mc;
	private final GuiListExtended.IGuiListEntry[] listEntries;
	private int maxListLabelWidth = 0;

	public GuiKeyBindingList(GuiControls controls, Minecraft mcIn) {
		super(mcIn, controls.width, controls.height, 63, controls.height - 32, 20);
		this.field_148191_k = controls;
		this.mc = mcIn;
		KeyBinding[] akeybinding = (KeyBinding[]) ArrayUtils.clone(mcIn.gameSettings.keyBindings);
		this.listEntries = new GuiListExtended.IGuiListEntry[akeybinding.length + KeyBinding.getKeybinds().size()];
		Arrays.sort(akeybinding);
		int i = 0;
		String s = null;

		for (KeyBinding keybinding : akeybinding) {
			String s1 = keybinding.getKeyCategory();
			if (!s1.equals(s)) {
				s = s1;
				this.listEntries[i++] = new GuiKeyBindingList.CategoryEntry(s1);
			}

			int j = mcIn.fontRendererObj.getStringWidth(I18n.format(keybinding.getKeyDescription(), new Object[0]));
			if (j > this.maxListLabelWidth) {
				this.maxListLabelWidth = j;
			}

			this.listEntries[i++] = new GuiKeyBindingList.KeyEntry(keybinding);
		}

	}

	protected int getSize() {
		return this.listEntries.length;
	}

	/**+
	 * Gets the IGuiListEntry object for the given index
	 */
	public GuiListExtended.IGuiListEntry getListEntry(int i) {
		return this.listEntries[i];
	}

	protected int getScrollBarX() {
		return super.getScrollBarX() + 15;
	}

	/**+
	 * Gets the width of the list
	 */
	public int getListWidth() {
		return super.getListWidth() + 32;
	}

	public class CategoryEntry implements GuiListExtended.IGuiListEntry {
		private final String labelText;
		private final int labelWidth;

		public CategoryEntry(String parString1) {
			this.labelText = I18n.format(parString1, new Object[0]);
			this.labelWidth = GuiKeyBindingList.this.mc.fontRendererObj.getStringWidth(this.labelText);
		}

		public void drawEntry(int var1, int var2, int i, int var4, int j, int var6, int var7, boolean var8) {
			GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.labelText,
					GuiKeyBindingList.this.mc.currentScreen.width / 2 - this.labelWidth / 2,
					i + j - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
		}

		public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
			return false;
		}

		public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
		}

		public void setSelected(int var1, int var2, int var3) {
		}
	}

	public class KeyEntry implements GuiListExtended.IGuiListEntry {
		private final KeyBinding keybinding;
		private final String keyDesc;
		private final GuiButton btnChangeKeyBinding;
		private final GuiButton btnReset;

		private KeyEntry(KeyBinding parKeyBinding) {
			this.keybinding = parKeyBinding;
			this.keyDesc = I18n.format(parKeyBinding.getKeyDescription(), new Object[0]);
			this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20,
					I18n.format(parKeyBinding.getKeyDescription(), new Object[0]));
			this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
		}

		public void drawEntry(int var1, int i, int j, int var4, int k, int l, int i1, boolean var8) {
			boolean flag = GuiKeyBindingList.this.field_148191_k.buttonId == this.keybinding;
			GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.keyDesc,
					i + 90 - GuiKeyBindingList.this.maxListLabelWidth,
					j + k / 2 - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
			this.btnReset.xPosition = i + 190;
			this.btnReset.yPosition = j;
			this.btnReset.enabled = this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault();
			this.btnReset.drawButton(GuiKeyBindingList.this.mc, l, i1);
			this.btnChangeKeyBinding.xPosition = i + 105;
			this.btnChangeKeyBinding.yPosition = j;
			this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
			boolean flag1 = false;
			if (this.keybinding.getKeyCode() != 0) {
				for (KeyBinding keybindingx : GuiKeyBindingList.this.mc.gameSettings.keyBindings) {
					if (keybindingx != this.keybinding && keybindingx.getKeyCode() == this.keybinding.getKeyCode()) {
						flag1 = true;
						break;
					}
				}
			}

			if (flag) {
				this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW
						+ this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
			} else if (flag1) {
				this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED
						+ this.btnChangeKeyBinding.displayString;
			}

			this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, l, i1);
		}

		public boolean mousePressed(int var1, int i, int j, int var4, int var5, int var6) {
			if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, i, j)) {
				GuiKeyBindingList.this.field_148191_k.buttonId = this.keybinding;
				return true;
			} else if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, i, j)) {
				GuiKeyBindingList.this.mc.gameSettings.setOptionKeyBinding(this.keybinding,
						this.keybinding.getKeyCodeDefault());
				KeyBinding.resetKeyBindingArrayAndHash();
				return true;
			} else {
				return false;
			}
		}

		public void mouseReleased(int var1, int i, int j, int var4, int var5, int var6) {
			this.btnChangeKeyBinding.mouseReleased(i, j);
			this.btnReset.mouseReleased(i, j);
		}

		public void setSelected(int var1, int var2, int var3) {
		}
	}
}