package net.minecraft.client.gui;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;

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
public class GuiSleepMP extends GuiChat {
	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40,
				I18n.format("multiplayer.stopSleeping", new Object[0])));
	}

	/**+
	 * Fired when a key is typed (except F11 which toggles full
	 * screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char parChar1, int parInt1) {
		if (parInt1 == 1) {
			this.wakeFromSleep();
		} else if (parInt1 != 28 && parInt1 != 156) {
			super.keyTyped(parChar1, parInt1);
		} else {
			String s = this.inputField.getText().trim();
			if (!s.isEmpty()) {
				this.mc.thePlayer.sendChatMessage(s);
			}

			this.inputField.setText("");
			this.mc.ingameGUI.getChatGUI().resetScroll();
		}

	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.id == 1) {
			this.wakeFromSleep();
		} else {
			super.actionPerformed(parGuiButton);
		}

	}

	private void wakeFromSleep() {
		NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
		nethandlerplayclient.addToSendQueue(
				new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
	}
}