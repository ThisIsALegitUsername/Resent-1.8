package net.minecraft.client.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

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
public class GuiControls extends GuiScreen {
	private static final GameSettings.Options[] optionsArr = new GameSettings.Options[] {
			GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };
	private GuiScreen parentScreen;
	protected String screenTitle = "Controls";
	private GameSettings options;
	/**+
	 * The ID of the button that has been pressed.
	 */
	public KeyBinding buttonId = null;
	public long time;
	private GuiKeyBindingList keyBindingList;
	private GuiButton buttonReset;

	public GuiControls(GuiScreen screen, GameSettings settings) {
		this.parentScreen = screen;
		this.options = settings;
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.keyBindingList = new GuiKeyBindingList(this, this.mc);
		this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20,
				I18n.format("gui.done", new Object[0])));
		this.buttonList.add(this.buttonReset = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20,
				I18n.format("controls.resetAll", new Object[0])));
		this.screenTitle = I18n.format("controls.title", new Object[0]);
		int i = 0;

		for (GameSettings.Options gamesettings$options : optionsArr) {
			if (gamesettings$options.getEnumFloat()) {
				this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(),
						this.width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), gamesettings$options));
			} else {
				this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(),
						this.width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), gamesettings$options,
						this.options.getKeyBinding(gamesettings$options)));
			}

			++i;
		}

	}

	/**+
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.keyBindingList.handleMouseInput();
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.id == 200) {
			this.mc.displayGuiScreen(this.parentScreen);
		} else if (parGuiButton.id == 201) {
			for (KeyBinding keybinding : this.mc.gameSettings.keyBindings) {
				keybinding.setKeyCode(keybinding.getKeyCodeDefault());
			}

			KeyBinding.resetKeyBindingArrayAndHash();
		} else if (parGuiButton.id < 100 && parGuiButton instanceof GuiOptionButton) {
			this.options.setOptionValue(((GuiOptionButton) parGuiButton).returnEnumOptions(), 1);
			parGuiButton.displayString = this.options
					.getKeyBinding(GameSettings.Options.getEnumOptions(parGuiButton.id));
		}

	}

	/**+
	 * Called when the mouse is clicked. Args : mouseX, mouseY,
	 * clickedButton
	 */
	protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
		if (this.buttonId != null) {
			this.options.setOptionKeyBinding(this.buttonId, -100 + parInt3);
			this.buttonId = null;
			KeyBinding.resetKeyBindingArrayAndHash();
		} else if (parInt3 != 0 || !this.keyBindingList.mouseClicked(parInt1, parInt2, parInt3)) {
			super.mouseClicked(parInt1, parInt2, parInt3);
		}

	}

	/**+
	 * Called when a mouse button is released. Args : mouseX,
	 * mouseY, releaseButton
	 */
	protected void mouseReleased(int i, int j, int k) {
		if (k != 0 || !this.keyBindingList.mouseReleased(i, j, k)) {
			super.mouseReleased(i, j, k);
		}

	}

	/**+
	 * Fired when a key is typed (except F11 which toggles full
	 * screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char parChar1, int parInt1) {
		if (this.buttonId != null) {
			if (parInt1 == 1) {
				this.options.setOptionKeyBinding(this.buttonId, 0);
			} else if (parInt1 != 0) {
				this.options.setOptionKeyBinding(this.buttonId, parInt1);
			} else if (parChar1 > 0) {
				this.options.setOptionKeyBinding(this.buttonId, parChar1 + 256);
			}

			this.buttonId = null;
			this.time = Minecraft.getSystemTime();
			KeyBinding.resetKeyBindingArrayAndHash();
		} else {
			super.keyTyped(parChar1, parInt1);
		}

	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		this.keyBindingList.drawScreen(i, j, f);
		this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 8, 16777215);
		boolean flag = true;

		for (KeyBinding keybinding : this.options.keyBindings) {
			if (keybinding.getKeyCode() != keybinding.getKeyCodeDefault()) {
				flag = false;
				break;
			}
		}

		this.buttonReset.enabled = !flag;
		super.drawScreen(i, j, f);
	}
}