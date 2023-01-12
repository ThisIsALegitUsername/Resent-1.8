package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

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
public class GuiCustomizeSkin extends GuiScreen {
	private final GuiScreen parentScreen;
	private String title;

	public GuiCustomizeSkin(GuiScreen parentScreenIn) {
		this.parentScreen = parentScreenIn;
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		int i = 0;
		this.title = I18n.format("options.skinCustomisation.title", new Object[0]);

		for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
			this.buttonList.add(new GuiCustomizeSkin.ButtonPart(enumplayermodelparts.getPartId(),
					this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20,
					enumplayermodelparts));
			++i;
		}

		if (i % 2 == 1) {
			++i;
		}

		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1),
				I18n.format("gui.done", new Object[0])));
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.enabled) {
			if (parGuiButton.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.parentScreen);
			} else if (parGuiButton instanceof GuiCustomizeSkin.ButtonPart) {
				EnumPlayerModelParts enumplayermodelparts = ((GuiCustomizeSkin.ButtonPart) parGuiButton).playerModelParts;
				this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
				parGuiButton.displayString = this.func_175358_a(enumplayermodelparts);
			}

		}
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
		super.drawScreen(i, j, f);
	}

	private String func_175358_a(EnumPlayerModelParts playerModelParts) {
		String s;
		if (this.mc.gameSettings.getModelParts().contains(playerModelParts)) {
			s = I18n.format("options.on", new Object[0]);
		} else {
			s = I18n.format("options.off", new Object[0]);
		}

		/*
		 * TODO: I changed this to getUnformattedText() from getFormattedText() because
		 * the latter was returning a pink formatting code at the end for no reason
		 */
		return playerModelParts.func_179326_d().getUnformattedText() + ": " + s;
	}

	class ButtonPart extends GuiButton {
		private final EnumPlayerModelParts playerModelParts;

		private ButtonPart(int parInt1, int parInt2, int parInt3, int parInt4, int parInt5,
				EnumPlayerModelParts playerModelParts) {
			super(parInt1, parInt2, parInt3, parInt4, parInt5, GuiCustomizeSkin.this.func_175358_a(playerModelParts));
			this.playerModelParts = playerModelParts;
		}
	}
}