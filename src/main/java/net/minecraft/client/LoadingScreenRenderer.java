package net.minecraft.client;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;

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
public class LoadingScreenRenderer implements IProgressUpdate {
	private String message = "";
	private Minecraft mc;
	/**+
	 * The text currently displayed (i.e. the argument to the last
	 * call to printText or func_73722_d)
	 */
	private String currentlyDisplayedText = "";
	/**+
	 * The system's time represented in milliseconds.
	 */
	private long systemTime = Minecraft.getSystemTime();
	private boolean field_73724_e;
	private ScaledResolution scaledResolution;

	public LoadingScreenRenderer(Minecraft mcIn) {
		this.mc = mcIn;
		this.scaledResolution = new ScaledResolution(mcIn);
	}

	/**+
	 * this string, followed by "working..." and then the "%
	 * complete" are the 3 lines shown. This resets progress to 0,
	 * and the WorkingString to "working...".
	 */
	public void resetProgressAndMessage(String message) {
		this.field_73724_e = false;
		this.displayString(message);
	}

	/**+
	 * Shows the 'Saving level' string.
	 */
	public void displaySavingString(String message) {
		this.field_73724_e = true;
		this.displayString(message);
	}

	private void displayString(String message) {
		this.currentlyDisplayedText = message;
		if (!this.mc.running) {
			if (!this.field_73724_e) {
				throw new MinecraftError();
			}
		} else {
			GlStateManager.clear(GL_DEPTH_BUFFER_BIT);
			GlStateManager.matrixMode(GL_PROJECTION);
			GlStateManager.loadIdentity();
			ScaledResolution scaledresolution = new ScaledResolution(this.mc);
			GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(),
					scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
			GlStateManager.matrixMode(GL_MODELVIEW);
			GlStateManager.loadIdentity();
			GlStateManager.translate(0.0F, 0.0F, -200.0F);
		}
	}

	/**+
	 * Displays a string on the loading screen supposed to indicate
	 * what is being done currently.
	 */
	public void displayLoadingString(String message) {
		if (!this.mc.running) {
			if (!this.field_73724_e) {
				throw new MinecraftError();
			}
		} else {
			this.systemTime = 0L;
			this.message = message;
			this.setLoadingProgress(-1);
			this.systemTime = 0L;
		}
	}

	public void eaglerShow(String line1, String line2) {
		if (!this.mc.running) {
			if (!this.field_73724_e) {
				throw new MinecraftError();
			}
		} else {
			this.systemTime = 0L;
			this.currentlyDisplayedText = line1;
			this.message = line2;
			this.setLoadingProgress(-1);
			this.systemTime = 0L;
		}
	}

	public void eaglerShowRefreshResources() {
		eaglerShow(I18n.format("resourcePack.load.refreshing"), I18n.format("resourcePack.load.pleaseWait"));
	}

	/**+
	 * Updates the progress bar on the loading screen to the
	 * specified amount. Args: loadProgress
	 */
	public void setLoadingProgress(int progress) {
		if (!this.mc.running) {
			if (!this.field_73724_e) {
				throw new MinecraftError();
			}
		} else {
			long i = Minecraft.getSystemTime();
			if (i - this.systemTime >= 100L) {
				this.systemTime = i;
				ScaledResolution scaledresolution = new ScaledResolution(this.mc);
				int j = scaledresolution.getScaleFactor();
				int k = scaledresolution.getScaledWidth();
				int l = scaledresolution.getScaledHeight();
				GlStateManager.clear(GL_DEPTH_BUFFER_BIT);
				GlStateManager.matrixMode(GL_PROJECTION);
				GlStateManager.loadIdentity();
				GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(),
						scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
				GlStateManager.matrixMode(GL_MODELVIEW);
				GlStateManager.loadIdentity();
				GlStateManager.translate(0.0F, 0.0F, -200.0F);
				GlStateManager.clear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer worldrenderer = tessellator.getWorldRenderer();
				this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
				float f = 32.0F;
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				worldrenderer.pos(0.0D, (double) l, 0.0D).tex(0.0D, (double) ((float) l / f)).color(64, 64, 64, 255)
						.endVertex();
				worldrenderer.pos((double) k, (double) l, 0.0D).tex((double) ((float) k / f), (double) ((float) l / f))
						.color(64, 64, 64, 255).endVertex();
				worldrenderer.pos((double) k, 0.0D, 0.0D).tex((double) ((float) k / f), 0.0D).color(64, 64, 64, 255)
						.endVertex();
				worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(64, 64, 64, 255).endVertex();
				tessellator.draw();
				if (progress >= 0) {
					byte b0 = 100;
					byte b1 = 2;
					int i1 = k / 2 - b0 / 2;
					int j1 = l / 2 + 16;
					GlStateManager.disableTexture2D();
					worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
					worldrenderer.pos((double) i1, (double) j1, 0.0D).color(128, 128, 128, 255).endVertex();
					worldrenderer.pos((double) i1, (double) (j1 + b1), 0.0D).color(128, 128, 128, 255).endVertex();
					worldrenderer.pos((double) (i1 + b0), (double) (j1 + b1), 0.0D).color(128, 128, 128, 255)
							.endVertex();
					worldrenderer.pos((double) (i1 + b0), (double) j1, 0.0D).color(128, 128, 128, 255).endVertex();
					worldrenderer.pos((double) i1, (double) j1, 0.0D).color(128, 255, 128, 255).endVertex();
					worldrenderer.pos((double) i1, (double) (j1 + b1), 0.0D).color(128, 255, 128, 255).endVertex();
					worldrenderer.pos((double) (i1 + progress), (double) (j1 + b1), 0.0D).color(128, 255, 128, 255)
							.endVertex();
					worldrenderer.pos((double) (i1 + progress), (double) j1, 0.0D).color(128, 255, 128, 255)
							.endVertex();
					tessellator.draw();
					GlStateManager.enableTexture2D();
				}

				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
				this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText,
						(float) ((k - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2),
						(float) (l / 2 - 4 - 16), 16777215);
				this.mc.fontRendererObj.drawStringWithShadow(this.message,
						(float) ((k - this.mc.fontRendererObj.getStringWidth(this.message)) / 2),
						(float) (l / 2 - 4 + 8), 16777215);
				this.mc.updateDisplay();

				try {
					Thread.yield();
				} catch (Exception var15) {
					;
				}

			}
		}
	}

	public void setDoneWorking() {
	}
}