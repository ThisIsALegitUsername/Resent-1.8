package net.minecraft.client.gui;

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
public class GuiListButton extends GuiButton {
	private boolean field_175216_o;
	private String localizationStr;
	private final GuiPageButtonList.GuiResponder guiResponder;

	public GuiListButton(GuiPageButtonList.GuiResponder responder, int parInt1, int parInt2, int parInt3,
			String parString1, boolean parFlag) {
		super(parInt1, parInt2, parInt3, 150, 20, "");
		this.localizationStr = parString1;
		this.field_175216_o = parFlag;
		this.displayString = this.buildDisplayString();
		this.guiResponder = responder;
	}

	/**+
	 * Builds the localized display string for this GuiListButton
	 */
	private String buildDisplayString() {
		return I18n.format(this.localizationStr, new Object[0]) + ": "
				+ (this.field_175216_o ? I18n.format("gui.yes", new Object[0]) : I18n.format("gui.no", new Object[0]));
	}

	public void func_175212_b(boolean parFlag) {
		this.field_175216_o = parFlag;
		this.displayString = this.buildDisplayString();
		this.guiResponder.func_175321_a(this.id, parFlag);
	}

	/**+
	 * Returns true if the mouse has been pressed on this control.
	 * Equivalent of MouseListener.mousePressed(MouseEvent e).
	 */
	public boolean mousePressed(Minecraft minecraft, int i, int j) {
		if (super.mousePressed(minecraft, i, j)) {
			this.field_175216_o = !this.field_175216_o;
			this.displayString = this.buildDisplayString();
			this.guiResponder.func_175321_a(this.id, this.field_175216_o);
			return true;
		} else {
			return false;
		}
	}
}