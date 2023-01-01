package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files are (c) 2022 LAX1DUDE. All Rights Reserved.
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
public class GuiLanguage extends GuiScreen {
	protected GuiScreen parentScreen;
	private GuiLanguage.List list;
	private final GameSettings game_settings_3;
	private final LanguageManager languageManager;
	private GuiOptionButton forceUnicodeFontBtn;
	private GuiOptionButton confirmSettingsBtn;

	public GuiLanguage(GuiScreen screen, GameSettings gameSettingsObj, LanguageManager manager) {
		this.parentScreen = screen;
		this.game_settings_3 = gameSettingsObj;
		this.languageManager = manager;
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
        this.buttonList.add(this.forceUnicodeFontBtn = new GuiOptionButton(100, width / 2 - 155, height - 38,
                GameSettings.Options.FORCE_UNICODE_FONT,
                this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
        this.buttonList.add(this.confirmSettingsBtn = new GuiOptionButton(6, width / 2 - 155 + 160,
                height - 38, I18n.format("gui.done")));
        this.list = new GuiLanguage.List(this.mc);
        this.list.registerScrollButtons(7, 8);
    }

	/**+
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.list.handleMouseInput();
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.enabled) {
			switch (parGuiButton.id) {
			case 5:
				break;
			case 6:
				this.mc.displayGuiScreen(this.parentScreen);
				break;
			case 100:
				if (parGuiButton instanceof GuiOptionButton) {
					this.game_settings_3.setOptionValue(((GuiOptionButton) parGuiButton).returnEnumOptions(), 1);
					parGuiButton.displayString = this.game_settings_3
							.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
					ScaledResolution scaledresolution = new ScaledResolution(this.mc);
					int i = scaledresolution.getScaledWidth();
					int j = scaledresolution.getScaledHeight();
					this.setWorldAndResolution(this.mc, i, j);
				}
				break;
			default:
				this.list.actionPerformed(parGuiButton);
			}

		}
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
        this.list.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, I18n.format("options.language"), width / 2,
                16, 16777215);
        this.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning") + ")",
                width / 2, height - 56, 8421504);
        super.drawScreen(i, j, f);
    }

	class List extends GuiSlot {
		private final java.util.List<String> langCodeList = Lists.newArrayList();
		private final Map<String, Language> languageMap = Maps.newHashMap();

		public List(Minecraft mcIn) {
            super(mcIn, GuiScreen.width, GuiScreen.height, 32, GuiScreen.height - 65 + 4, 18);

			for (Language language : GuiLanguage.this.languageManager.getLanguages()) {
				this.languageMap.put(language.getLanguageCode(), language);
				this.langCodeList.add(language.getLanguageCode());
			}

		}

		protected int getSize() {
			return this.langCodeList.size();
		}

		protected void elementClicked(int i, boolean var2, int var3, int var4) {
            Language language = this.languageMap.get(this.langCodeList.get(i));
			GuiLanguage.this.languageManager.setCurrentLanguage(language);
			GuiLanguage.this.game_settings_3.language = language.getLanguageCode();
			this.mc.loadingScreen.eaglerShow(I18n.format("resourcePack.load.refreshing"),
					I18n.format("resourcePack.load.pleaseWait"));
			this.mc.refreshResources();
			GuiLanguage.this.fontRendererObj.setUnicodeFlag(GuiLanguage.this.languageManager.isCurrentLocaleUnicode()
					|| GuiLanguage.this.game_settings_3.forceUnicodeFont);
            GuiLanguage.this.fontRendererObj
                    .setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
            GuiLanguage.this.confirmSettingsBtn.displayString = I18n.format("gui.done");
            GuiLanguage.this.forceUnicodeFontBtn.displayString = GuiLanguage.this.game_settings_3
                    .getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
			GuiLanguage.this.game_settings_3.saveOptions();
			GuiLanguage.this.mc.displayGuiScreen(GuiLanguage.this);
		}

		protected boolean isSelected(int i) {
            return this.langCodeList.get(i)
                    .equals(GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
		}

		protected int getContentHeight() {
			return this.getSize() * 18;
		}

		protected void drawBackground() {
			GuiLanguage.this.drawDefaultBackground();
		}

		protected void drawSlot(int i, int var2, int j, int var4, int var5, int var6) {
			GuiLanguage.this.fontRendererObj.setBidiFlag(true);
			GuiLanguage.this.drawCenteredString(GuiLanguage.this.fontRendererObj,
                    this.languageMap.get(this.langCodeList.get(i)).toString(), this.width / 2, j + 1,
                    16777215);
			GuiLanguage.this.fontRendererObj
					.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
		}
	}
}