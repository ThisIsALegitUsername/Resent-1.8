package net.minecraft.client.gui;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

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
public class GuiDisconnected extends GuiScreen {
	private String reason;
	private IChatComponent message;
	private List<String> multilineMessage;
	private final GuiScreen parentScreen;
	private int field_175353_i;

	public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp) {
		this.parentScreen = screen;
		this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
		this.message = chatComp;
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
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(),
				this.width - 50);
		this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100,
				this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT,
				I18n.format("gui.toMenu", new Object[0])));
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.id == 0) {
			this.mc.displayGuiScreen(this.parentScreen);
		}

	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2,
				this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
		int k = this.height / 2 - this.field_175353_i / 2;
		if (this.multilineMessage != null) {
			for (String s : this.multilineMessage) {
				this.drawCenteredString(this.fontRendererObj, s, this.width / 2, k, 16777215);
				k += this.fontRendererObj.FONT_HEIGHT;
			}
		}

		super.drawScreen(i, j, f);
	}

	public static GuiScreen createRateLimitKick(GuiScreen prev) {
		return new GuiDisconnected(prev, "connect.failed", new ChatComponentTranslation("disconnect.tooManyRequests"));
	}
}