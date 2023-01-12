package net.minecraft.client.gui;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

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
public class GuiOptionsRowList extends GuiListExtended {
	private final List<GuiOptionsRowList.Row> field_148184_k = Lists.newArrayList();

	public GuiOptionsRowList(Minecraft mcIn, int parInt1, int parInt2, int parInt3, int parInt4, int parInt5,
			GameSettings.Options... parArrayOfOptions) {
		super(mcIn, parInt1, parInt2, parInt3, parInt4, parInt5);
		this.field_148163_i = false;

		for (int i = 0; i < parArrayOfOptions.length; i += 2) {
			GameSettings.Options gamesettings$options = parArrayOfOptions[i];
			GameSettings.Options gamesettings$options1 = i < parArrayOfOptions.length - 1 ? parArrayOfOptions[i + 1]
					: null;
			GuiButton guibutton = this.func_148182_a(mcIn, parInt1 / 2 - 155, 0, gamesettings$options);
			GuiButton guibutton1 = this.func_148182_a(mcIn, parInt1 / 2 - 155 + 160, 0, gamesettings$options1);
			this.field_148184_k.add(new GuiOptionsRowList.Row(guibutton, guibutton1));
		}

	}

	private GuiButton func_148182_a(Minecraft mcIn, int parInt1, int parInt2, GameSettings.Options parOptions) {
		if (parOptions == null) {
			return null;
		} else {
			int i = parOptions.returnEnumOrdinal();
			return (GuiButton) (parOptions.getEnumFloat() ? new GuiOptionSlider(i, parInt1, parInt2, parOptions)
					: new GuiOptionButton(i, parInt1, parInt2, parOptions,
							mcIn.gameSettings.getKeyBinding(parOptions)));
		}
	}

	/**+
	 * Gets the IGuiListEntry object for the given index
	 */
	public GuiOptionsRowList.Row getListEntry(int i) {
		return (GuiOptionsRowList.Row) this.field_148184_k.get(i);
	}

	protected int getSize() {
		return this.field_148184_k.size();
	}

	/**+
	 * Gets the width of the list
	 */
	public int getListWidth() {
		return 400;
	}

	protected int getScrollBarX() {
		return super.getScrollBarX() + 32;
	}

	public static class Row implements GuiListExtended.IGuiListEntry {
		private final Minecraft field_148325_a = Minecraft.getMinecraft();
		private final GuiButton field_148323_b;
		private final GuiButton field_148324_c;

		public Row(GuiButton parGuiButton, GuiButton parGuiButton2) {
			this.field_148323_b = parGuiButton;
			this.field_148324_c = parGuiButton2;
		}

		public void drawEntry(int var1, int var2, int i, int var4, int var5, int j, int k, boolean var8) {
			if (this.field_148323_b != null) {
				this.field_148323_b.yPosition = i;
				this.field_148323_b.drawButton(this.field_148325_a, j, k);
			}

			if (this.field_148324_c != null) {
				this.field_148324_c.yPosition = i;
				this.field_148324_c.drawButton(this.field_148325_a, j, k);
			}

		}

		public boolean mousePressed(int var1, int i, int j, int var4, int var5, int var6) {
			if (this.field_148323_b.mousePressed(this.field_148325_a, i, j)) {
				if (this.field_148323_b instanceof GuiOptionButton) {
					this.field_148325_a.gameSettings
							.setOptionValue(((GuiOptionButton) this.field_148323_b).returnEnumOptions(), 1);
					this.field_148323_b.displayString = this.field_148325_a.gameSettings
							.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.id));
				}

				return true;
			} else if (this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, i, j)) {
				if (this.field_148324_c instanceof GuiOptionButton) {
					this.field_148325_a.gameSettings
							.setOptionValue(((GuiOptionButton) this.field_148324_c).returnEnumOptions(), 1);
					this.field_148324_c.displayString = this.field_148325_a.gameSettings
							.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.id));
				}

				return true;
			} else {
				return false;
			}
		}

		public void mouseReleased(int var1, int i, int j, int var4, int var5, int var6) {
			if (this.field_148323_b != null) {
				this.field_148323_b.mouseReleased(i, j);
			}

			if (this.field_148324_c != null) {
				this.field_148324_c.mouseReleased(i, j);
			}

		}

		public void setSelected(int var1, int var2, int var3) {
		}
	}
}