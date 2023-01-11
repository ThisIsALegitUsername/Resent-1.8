package net.minecraft.client.gui;

import net.minecraft.util.IProgressUpdate;

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
public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
	private String field_146591_a = "";
	private String field_146589_f = "";
	private int progress;
	private boolean doneWorking;

	/**+
	 * Shows the 'Saving level' string.
	 */
	public void displaySavingString(String s) {
		this.resetProgressAndMessage(s);
	}

	/**+
	 * this string, followed by "working..." and then the "%
	 * complete" are the 3 lines shown. This resets progress to 0,
	 * and the WorkingString to "working...".
	 */
	public void resetProgressAndMessage(String s) {
		this.field_146591_a = s;
		this.displayLoadingString("Working...");
	}

	/**+
	 * Displays a string on the loading screen supposed to indicate
	 * what is being done currently.
	 */
	public void displayLoadingString(String s) {
		this.field_146589_f = s;
		this.setLoadingProgress(0);
	}

	/**+
	 * Updates the progress bar on the loading screen to the
	 * specified amount. Args: loadProgress
	 */
	public void setLoadingProgress(int i) {
		this.progress = i;
	}

	public void setDoneWorking() {
		this.doneWorking = true;
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		if (this.doneWorking) {
			if (!this.mc.func_181540_al()) {
				this.mc.displayGuiScreen((GuiScreen) null);
			}

		} else {
			this.drawDefaultBackground();
			this.drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
			this.drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.progress + "%",
					this.width / 2, 90, 16777215);
			super.drawScreen(i, j, f);
		}
	}
}