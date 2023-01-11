package net.minecraft.client.gui;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
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
public class GuiGameOver extends GuiScreen implements GuiYesNoCallback {
	private int enableButtonsTimer;
	private boolean field_146346_f = false;

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
			if (this.mc.isIntegratedServerRunning()) {
				this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96,
						I18n.format("deathScreen.deleteWorld", new Object[0])));
			} else {
				this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96,
						I18n.format("deathScreen.leaveServer", new Object[0])));
			}
		} else {
			this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72,
					I18n.format("deathScreen.respawn", new Object[0])));
			this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96,
					I18n.format("deathScreen.titleScreen", new Object[0])));
			if (this.mc.getSession() == null) {
				((GuiButton) this.buttonList.get(1)).enabled = false;
			}
		}

		for (GuiButton guibutton : this.buttonList) {
			guibutton.enabled = false;
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
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		switch (parGuiButton.id) {
		case 0:
			this.mc.thePlayer.respawnPlayer();
			this.mc.displayGuiScreen((GuiScreen) null);
			break;
		case 1:
			if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
				this.mc.theWorld.sendQuittingDisconnectingPacket();
				this.mc.loadWorld((WorldClient) null);
				this.mc.displayGuiScreen(new GuiMainMenu());
			} else {
				GuiYesNo guiyesno = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "",
						I18n.format("deathScreen.titleScreen", new Object[0]),
						I18n.format("deathScreen.respawn", new Object[0]), 0);
				this.mc.displayGuiScreen(guiyesno);
				guiyesno.setButtonDelay(20);
			}
		}

	}

	public void confirmClicked(boolean flag, int var2) {
		if (flag) {
			this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld((WorldClient) null);
			this.mc.displayGuiScreen(new GuiMainMenu());
		} else {
			this.mc.thePlayer.respawnPlayer();
			this.mc.displayGuiScreen((GuiScreen) null);
		}

	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
		GlStateManager.pushMatrix();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		boolean flag = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
		String s = flag ? I18n.format("deathScreen.title.hardcore", new Object[0])
				: I18n.format("deathScreen.title", new Object[0]);
		this.drawCenteredString(this.fontRendererObj, s, this.width / 2 / 2, 30, 16777215);
		GlStateManager.popMatrix();
		if (flag) {
			this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]),
					this.width / 2, 144, 16777215);
		}

		this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": "
				+ EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / 2, 100, 16777215);
		super.drawScreen(i, j, f);
	}

	/**+
	 * Returns true if this GUI should pause the game when it is
	 * displayed in single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**+
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		super.updateScreen();
		++this.enableButtonsTimer;
		if (this.enableButtonsTimer == 20) {
			for (GuiButton guibutton : this.buttonList) {
				guibutton.enabled = true;
			}
		}

	}
}