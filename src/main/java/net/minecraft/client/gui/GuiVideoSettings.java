package net.minecraft.client.gui;

import java.io.IOException;

import net.minecraft.client.resources.I18n;
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
public class GuiVideoSettings extends GuiScreen {
	private GuiScreen parentGuiScreen;
	protected String screenTitle = "Video Settings";
	private GameSettings guiGameSettings;
	private GuiListExtended optionsRowList;
	/**+
	 * An array of all of GameSettings.Options's video options.
	 */
	private static final GameSettings.Options[] videoOptions = new GameSettings.Options[] {
			GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION,
			GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.ANAGLYPH, GameSettings.Options.VIEW_BOBBING,
			GameSettings.Options.GUI_SCALE, GameSettings.Options.GAMMA, GameSettings.Options.RENDER_CLOUDS,
			GameSettings.Options.PARTICLES, GameSettings.Options.FXAA, GameSettings.Options.MIPMAP_LEVELS,
			GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.ENTITY_SHADOWS, GameSettings.Options.FOG,
			GameSettings.Options.FULLSCREEN, GameSettings.Options.HUD_FPS, GameSettings.Options.HUD_COORDS,
			GameSettings.Options.HUD_PLAYER, GameSettings.Options.HUD_STATS, GameSettings.Options.HUD_WORLD,
			GameSettings.Options.HUD_24H, GameSettings.Options.CHUNK_FIX };

	public GuiVideoSettings(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
		this.parentGuiScreen = parentScreenIn;
		this.guiGameSettings = gameSettingsIn;
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
		this.buttonList.clear();
		this.buttonList.add(
				new GuiButton(200, this.width / 2 - 100, this.height - 27, I18n.format("gui.done", new Object[0])));
		GameSettings.Options[] agamesettings$options = new GameSettings.Options[videoOptions.length];
		int i = 0;

		for (GameSettings.Options gamesettings$options : videoOptions) {
			agamesettings$options[i] = gamesettings$options;
			++i;
		}

		this.optionsRowList = new GuiOptionsRowList(this.mc, this.width, this.height, 32, this.height - 32, 25,
				agamesettings$options);

	}

	/**+
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.optionsRowList.handleMouseInput();
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.enabled) {
			if (parGuiButton.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.parentGuiScreen);
			}

		}
	}

	/**+
	 * Called when the mouse is clicked. Args : mouseX, mouseY,
	 * clickedButton
	 */
	protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
		int i = this.guiGameSettings.guiScale;
		super.mouseClicked(parInt1, parInt2, parInt3);
		this.optionsRowList.mouseClicked(parInt1, parInt2, parInt3);
		if (this.guiGameSettings.guiScale != i) {
			ScaledResolution scaledresolution = new ScaledResolution(this.mc);
			int j = scaledresolution.getScaledWidth();
			int k = scaledresolution.getScaledHeight();
			this.setWorldAndResolution(this.mc, j, k);
		}

	}

	/**+
	 * Called when a mouse button is released. Args : mouseX,
	 * mouseY, releaseButton
	 */
	protected void mouseReleased(int i, int j, int k) {
		int l = this.guiGameSettings.guiScale;
		super.mouseReleased(i, j, k);
		this.optionsRowList.mouseReleased(i, j, k);
		if (this.guiGameSettings.guiScale != l) {
			ScaledResolution scaledresolution = new ScaledResolution(this.mc);
			int i1 = scaledresolution.getScaledWidth();
			int j1 = scaledresolution.getScaledHeight();
			this.setWorldAndResolution(this.mc, i1, j1);
		}

	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		this.optionsRowList.drawScreen(i, j, f);
		this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 5, 16777215);
		super.drawScreen(i, j, f);
	}
}