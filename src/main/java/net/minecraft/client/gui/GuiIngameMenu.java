package net.minecraft.client.gui;

import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
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
public class GuiIngameMenu extends GuiScreen {

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		byte b0 = -16;
		boolean flag = true;
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + b0,
				I18n.format("menu.returnToMenu", new Object[0])));
		if (!this.mc.isIntegratedServerRunning()) {
			((GuiButton) this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
		}

		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + b0,
				I18n.format("menu.returnToGame", new Object[0])));
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + b0, 98, 20,
				I18n.format("menu.options", new Object[0])));
		GuiButton guibutton;
		this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + b0, 98, 20,
				I18n.format("menu.shareToLan", new Object[0])));
		this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + b0, 98, 20,
				I18n.format("gui.achievements", new Object[0])));
		this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + b0, 98, 20,
				I18n.format("gui.stats", new Object[0])));
		guibutton.enabled = false;
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		switch (parGuiButton.id) {
		case 0:
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
			break;
		case 1:
			boolean flag = this.mc.isIntegratedServerRunning();
			parGuiButton.enabled = false;
			this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld((WorldClient) null);
			if (flag) {
				this.mc.displayGuiScreen(new GuiMainMenu());
			} else {
				this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
			}
		case 2:
		case 3:
		default:
			break;
		case 4:
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
			break;
		case 5:
			this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
			break;
		case 6:
			this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
			break;
		case 7:
			break;
		}

	}

	/**+
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		super.updateScreen();
		if (Mouse.isActuallyGrabbed()) {
			Mouse.setGrabbed(false);
		}
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40,
				16777215);
		super.drawScreen(i, j, f);
	}
}