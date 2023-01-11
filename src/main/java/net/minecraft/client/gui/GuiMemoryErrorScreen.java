package net.minecraft.client.gui;

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
public class GuiMemoryErrorScreen extends GuiScreen {
	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 155, this.height / 4 + 120 + 12,
				I18n.format("gui.toTitle", new Object[0])));
		this.buttonList.add(new GuiOptionButton(1, this.width / 2 - 155 + 160, this.height / 4 + 120 + 12,
				I18n.format("menu.quit", new Object[0])));
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.id == 0) {
			this.mc.displayGuiScreen(new GuiMainMenu());
		} else if (parGuiButton.id == 1) {
			this.mc.shutdown();
		}

	}

	/**+
	 * Fired when a key is typed (except F11 which toggles full
	 * screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char parChar1, int parInt1) {
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Out of memory!", this.width / 2, this.height / 4 - 60 + 20,
				16777215);
		this.drawString(this.fontRendererObj, "Minecraft has run out of memory.", this.width / 2 - 140,
				this.height / 4 - 60 + 60 + 0, 10526880);
		this.drawString(this.fontRendererObj, "This could be caused by a bug in the game or by the",
				this.width / 2 - 140, this.height / 4 - 60 + 60 + 18, 10526880);
		this.drawString(this.fontRendererObj, "Java Virtual Machine not being allocated enough", this.width / 2 - 140,
				this.height / 4 - 60 + 60 + 27, 10526880);
		this.drawString(this.fontRendererObj, "memory.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 36,
				10526880);
		this.drawString(this.fontRendererObj, "To prevent level corruption, the current game has quit.",
				this.width / 2 - 140, this.height / 4 - 60 + 60 + 54, 10526880);
		this.drawString(this.fontRendererObj, "We\'ve tried to free up enough memory to let you go back to",
				this.width / 2 - 140, this.height / 4 - 60 + 60 + 63, 10526880);
		this.drawString(this.fontRendererObj, "the main menu and back to playing, but this may not have worked.",
				this.width / 2 - 140, this.height / 4 - 60 + 60 + 72, 10526880);
		this.drawString(this.fontRendererObj, "Please restart the game if you see this message again.",
				this.width / 2 - 140, this.height / 4 - 60 + 60 + 81, 10526880);
		super.drawScreen(i, j, f);
	}
}