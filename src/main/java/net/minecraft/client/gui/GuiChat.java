package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

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
public class GuiChat extends GuiScreen {
	private static final Logger logger = LogManager.getLogger();
	private String historyBuffer = "";
	/**+
	 * keeps position of which chat message you will select when you
	 * press up, (does not increase for duplicated messages sent
	 * immediately after each other)
	 */
	private int sentHistoryCursor = -1;
	private boolean playerNamesFound;
	private boolean waitingOnAutocomplete;
	private int autocompleteIndex;
	private List<String> foundPlayerNames = Lists.newArrayList();
	protected GuiTextField inputField;
	/**+
	 * is the text that appears when you press the chat key and the
	 * input box appears pre-filled
	 */
	private String defaultInputFieldText = "";

	private GuiButton exitButton;

	public GuiChat() {
	}

	public GuiChat(String defaultText) {
		this.defaultInputFieldText = defaultText;
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		if (!(this instanceof GuiSleepMP)) {
			this.buttonList.add(exitButton = new GuiButton(69, this.width - 100, 3, 97, 20, I18n.format("chat.exit")));
		}
		this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
		this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
		this.inputField.setMaxStringLength(100);
		this.inputField.setEnableBackgroundDrawing(false);
		this.inputField.setFocused(true);
		this.inputField.setText(this.defaultInputFieldText);
		this.inputField.setCanLoseFocus(false);
	}

	/**+
	 * Called when the screen is unloaded. Used to disable keyboard
	 * repeat events
	 */
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		this.mc.ingameGUI.getChatGUI().resetScroll();
	}

	/**+
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		this.inputField.updateCursorCounter();
	}

	/**+
	 * Fired when a key is typed (except F11 which toggles full
	 * screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char parChar1, int parInt1) {
		if (parInt1 == 1 && (this.mc.gameSettings.keyBindClose.getKeyCode() == 0 || this.mc.areKeysLocked())) {
			this.mc.displayGuiScreen((GuiScreen) null);
		} else {
			this.waitingOnAutocomplete = false;
			if (parInt1 == 15) {
				this.autocompletePlayerNames();
			} else {
				this.playerNamesFound = false;
			}

			if (parInt1 != 28 && parInt1 != 156) {
				if (parInt1 == 200) {
					this.getSentHistory(-1);
				} else if (parInt1 == 208) {
					this.getSentHistory(1);
				} else if (parInt1 == 201) {
					this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
				} else if (parInt1 == 209) {
					this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
				} else {
					this.inputField.textboxKeyTyped(parChar1, parInt1);
				}
			} else {
				String s = this.inputField.getText().trim();
				if (s.length() > 0) {
					this.sendChatMessage(s);
				}

				this.mc.displayGuiScreen((GuiScreen) null);
			}
		}

	}

	/**+
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (i > 1) {
				i = 1;
			}

			if (i < -1) {
				i = -1;
			}

			if (!isShiftKeyDown()) {
				i *= 7;
			}

			this.mc.ingameGUI.getChatGUI().scroll(i);
		}

	}

	/**+
	 * Called when the mouse is clicked. Args : mouseX, mouseY,
	 * clickedButton
	 */
	protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
		if (parInt3 == 0) {
			IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
			if (this.handleComponentClick(ichatcomponent)) {
				return;
			}
		}

		this.inputField.mouseClicked(parInt1, parInt2, parInt3);
		super.mouseClicked(parInt1, parInt2, parInt3);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 69) {
			this.mc.displayGuiScreen(null);
		}
	}

	/**+
	 * Sets the text of the chat
	 */
	protected void setText(String newChatText, boolean shouldOverwrite) {
		if (shouldOverwrite) {
			this.inputField.setText(newChatText);
		} else {
			this.inputField.writeText(newChatText);
		}

	}

	public void autocompletePlayerNames() {
		if (this.playerNamesFound) {
			this.inputField
					.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false)
							- this.inputField.getCursorPosition());
			if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
				this.autocompleteIndex = 0;
			}
		} else {
			int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
			this.foundPlayerNames.clear();
			this.autocompleteIndex = 0;
			String s = this.inputField.getText().substring(i).toLowerCase();
			String s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
			this.sendAutocompleteRequest(s1, s);
			if (this.foundPlayerNames.isEmpty()) {
				return;
			}

			this.playerNamesFound = true;
			this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
		}

		if (this.foundPlayerNames.size() > 1) {
			StringBuilder stringbuilder = new StringBuilder();

			for (String s2 : this.foundPlayerNames) {
				if (stringbuilder.length() > 0) {
					stringbuilder.append(", ");
				}

				stringbuilder.append(s2);
			}

			this.mc.ingameGUI.getChatGUI()
					.printChatMessageWithOptionalDeletion(new ChatComponentText(stringbuilder.toString()), 1);
		}

		this.inputField.writeText((String) this.foundPlayerNames.get(this.autocompleteIndex++));
	}

	private void sendAutocompleteRequest(String parString1, String parString2) {
		if (parString1.length() >= 1) {
			BlockPos blockpos = null;
			if (this.mc.objectMouseOver != null
					&& this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				blockpos = this.mc.objectMouseOver.getBlockPos();
			}

			this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(parString1, blockpos));
			this.waitingOnAutocomplete = true;
		}
	}

	/**+
	 * input is relative and is applied directly to the
	 * sentHistoryCursor so -1 is the previous message, 1 is the
	 * next message from the current cursor position
	 */
	public void getSentHistory(int msgPos) {
		int i = this.sentHistoryCursor + msgPos;
		int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
		i = MathHelper.clamp_int(i, 0, j);
		if (i != this.sentHistoryCursor) {
			if (i == j) {
				this.sentHistoryCursor = j;
				this.inputField.setText(this.historyBuffer);
			} else {
				if (this.sentHistoryCursor == j) {
					this.historyBuffer = this.inputField.getText();
				}

				this.inputField.setText((String) this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
				this.sentHistoryCursor = i;
			}
		}
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
		this.inputField.drawTextBox();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
		if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null) {
			this.handleComponentHover(ichatcomponent, i, j);
		}

		if (exitButton != null) {
			exitButton.yPosition = 3 + mc.guiAchievement.getHeight();
		}

		super.drawScreen(i, j, f);
	}

	public void onAutocompleteResponse(String[] parArrayOfString) {
		if (this.waitingOnAutocomplete) {
			this.playerNamesFound = false;
			this.foundPlayerNames.clear();

			for (String s : parArrayOfString) {
				if (s.length() > 0) {
					this.foundPlayerNames.add(s);
				}
			}

			String s1 = this.inputField.getText()
					.substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
			String s2 = StringUtils.getCommonPrefix(parArrayOfString);
			if (s2.length() > 0 && !s1.equalsIgnoreCase(s2)) {
				this.inputField
						.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false)
								- this.inputField.getCursorPosition());
				this.inputField.writeText(s2);
			} else if (this.foundPlayerNames.size() > 0) {
				this.playerNamesFound = true;
				this.autocompletePlayerNames();
			}
		}

	}

	/**+
	 * Returns true if this GUI should pause the game when it is
	 * displayed in single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}
}