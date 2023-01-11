package net.minecraft.client.gui;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.EaglerInputStream;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.lax1dude.eaglercraft.v1_8.EaglercraftVersion;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.crypto.MD5Digest;
import net.lax1dude.eaglercraft.v1_8.crypto.SHA1Digest;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.lax1dude.eaglercraft.v1_8.profile.GuiScreenEditProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

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
public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
	private static final Logger logger = LogManager.getLogger();
	private static final EaglercraftRandom RANDOM = new EaglercraftRandom();
	private float updateCounter;
	private boolean isDefault;
	private static final int lendef = 5987;
	private static final byte[] md5def = new byte[] { -61, -53, -36, 27, 24, 27, 103, -31, -58, -116, 113, -60, -67, -8,
			-77, 30 };
	private static final byte[] sha1def = new byte[] { -107, 77, 108, 49, 11, -100, -8, -119, -1, -100, -85, -55, 18,
			-69, -107, 113, -93, -101, -79, 32 };
	private String splashText;
	private GuiButton buttonResetDemo;
	private int panoramaTimer;
	/**+
	 * Texture allocated for the current viewport of the main menu's
	 * panorama background.
	 */
	private static DynamicTexture viewportTexture = null;
	private boolean field_175375_v = true;
	private String openGLWarning1;
	private String openGLWarning2;
	private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
	private static final ResourceLocation minecraftTitleTextures = new ResourceLocation(
			"textures/gui/title/minecraft.png");
	/**+
	 * An array of all the paths to the panorama pictures.
	 */
	private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {
			new ResourceLocation("textures/gui/title/background/panorama_0.png"),
			new ResourceLocation("textures/gui/title/background/panorama_1.png"),
			new ResourceLocation("textures/gui/title/background/panorama_2.png"),
			new ResourceLocation("textures/gui/title/background/panorama_3.png"),
			new ResourceLocation("textures/gui/title/background/panorama_4.png"),
			new ResourceLocation("textures/gui/title/background/panorama_5.png") };
	private int field_92024_r;
	private int field_92023_s;
	private int field_92022_t;
	private int field_92021_u;
	private int field_92020_v;
	private int field_92019_w;
	private static ResourceLocation backgroundTexture = null;

	public GuiMainMenu() {
		this.splashText = "missingno";
		BufferedReader bufferedreader = null;

		try {
			ArrayList arraylist = Lists.newArrayList();
			bufferedreader = new BufferedReader(new InputStreamReader(
					Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(),
					Charsets.UTF_8));

			String s;
			while ((s = bufferedreader.readLine()) != null) {
				s = s.trim();
				if (!s.isEmpty()) {
					arraylist.add(s);
				}
			}

			if (!arraylist.isEmpty()) {
				while (true) {
					this.splashText = (String) arraylist.get(RANDOM.nextInt(arraylist.size()));
					if (this.splashText.hashCode() != 125780783) {
						break;
					}
				}
			}
		} catch (IOException var12) {
			;
		} finally {
			if (bufferedreader != null) {
				try {
					bufferedreader.close();
				} catch (IOException var11) {
					;
				}
			}

		}

		this.updateCounter = RANDOM.nextFloat();

		MD5Digest md5 = new MD5Digest();
		SHA1Digest sha1 = new SHA1Digest();
		byte[] md5out = new byte[16];
		byte[] sha1out = new byte[20];
		try {
			byte[] bytes = EaglerInputStream.inputStreamToBytesQuiet(
					Minecraft.getMinecraft().getResourceManager().getResource(minecraftTitleTextures).getInputStream());
			if (bytes != null) {
				md5.update(bytes, 0, bytes.length);
				sha1.update(bytes, 0, bytes.length);
				md5.doFinal(md5out, 0);
				sha1.doFinal(sha1out, 0);
				this.isDefault = bytes.length == lendef && Arrays.equals(md5out, md5def)
						&& Arrays.equals(sha1out, sha1def);
			} else {
				this.isDefault = false;
			}
		} catch (IOException e) {
			this.isDefault = false;
		}
	}

	/**+
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		++this.panoramaTimer;
	}

	/**+
	 * Returns true if this GUI should pause the game when it is
	 * displayed in single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
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
		if (viewportTexture == null) {
			viewportTexture = new DynamicTexture(256, 256);
			backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
			this.splashText = "Merry X-mas!";
		} else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
			this.splashText = "Happy new year!";
		} else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
			this.splashText = "OOoooOOOoooo! Spooky!";
		}

		int i = this.height / 4 + 48;

		boolean isFork = !EaglercraftVersion.projectOriginAuthor.equalsIgnoreCase(EaglercraftVersion.projectForkVendor);

		if (isFork && EaglercraftVersion.mainMenuStringF != null && EaglercraftVersion.mainMenuStringF.length() > 0) {
			i += 11;
		}

		this.addSingleplayerMultiplayerButtons(i, 24);

		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, i + 72 + 12, 98, 20,
				I18n.format("menu.options", new Object[0])));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 2, i + 72 + 12, 98, 20,
				I18n.format("menu.editProfile", new Object[0])));

		this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, i + 72 + 12));

		if (isFork) {
			this.openGLWarning1 = EaglercraftVersion.mainMenuStringE;
			this.openGLWarning2 = EaglercraftVersion.mainMenuStringF;
			boolean line2 = this.openGLWarning2 != null && this.openGLWarning2.length() > 0;
			this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
			this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
			int j = Math.max(this.field_92023_s, this.field_92024_r);
			this.field_92022_t = (this.width - j) / 2;
			this.field_92021_u = ((GuiButton) this.buttonList.get(0)).yPosition - (line2 ? 32 : 21);
			this.field_92020_v = this.field_92022_t + j;
			this.field_92019_w = this.field_92021_u + (line2 ? 24 : 11);
		}

		this.mc.func_181537_a(false);
	}

	/**+
	 * Adds Singleplayer and Multiplayer buttons on Main Menu for
	 * players who have bought the game.
	 */
	private void addSingleplayerMultiplayerButtons(int parInt1, int parInt2) {
		// this.buttonList
		// .add(new GuiButton(1, this.width / 2 - 100, parInt1,
		// I18n.format("menu.singleplayer", new Object[0])));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, parInt1 + parInt2 * 0,
				I18n.format("menu.multiplayer", new Object[0])));
		GuiButton btn;
		this.buttonList.add(btn = new GuiButton(14, this.width / 2 - 100, parInt1 + parInt2 * 1,
				I18n.format("menu.forkOnGitlab", new Object[0])));
		btn.enabled = EaglercraftVersion.mainMenuEnableGithubButton;
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if (parGuiButton.id == 5) {
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}

		if (parGuiButton.id == 1) {
			logger.error("Singleplayer was removed dumbass");
		}

		if (parGuiButton.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if (parGuiButton.id == 4) {
			this.mc.displayGuiScreen(new GuiScreenEditProfile(this));
		}

		if (parGuiButton.id == 14) {
			EagRuntime.openLink(EaglercraftVersion.projectForkURL);
		}

	}

	/**+
	 * Draws the main menu panorama
	 */
	private void drawPanorama(int parInt1, int parInt2, float parFloat1) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.matrixMode(GL_PROJECTION);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
		GlStateManager.matrixMode(GL_MODELVIEW);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableCull();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		byte b0 = 8;

		for (int i = 0; i < b0 * b0; ++i) {
			GlStateManager.pushMatrix();
			float f = ((float) (i % b0) / (float) b0 - 0.5F) / 64.0F;
			float f1 = ((float) (i / b0) / (float) b0 - 0.5F) / 64.0F;
			float f2 = 0.0F;
			GlStateManager.translate(f, f1, f2);
			GlStateManager.rotate(MathHelper.sin(((float) this.panoramaTimer + parFloat1) / 400.0F) * 25.0F + 20.0F,
					1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-((float) this.panoramaTimer + parFloat1) * 0.1F, 0.0F, 1.0F, 0.0F);

			for (int j = 0; j < 6; ++j) {
				GlStateManager.pushMatrix();
				if (j == 1) {
					GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				}

				if (j == 2) {
					GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				}

				if (j == 3) {
					GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				}

				if (j == 4) {
					GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				}

				if (j == 5) {
					GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				}

				this.mc.getTextureManager().bindTexture(titlePanoramaPaths[j]);
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				int k = 255 / (i + 1);
				float f3 = 0.0F;
				worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, k).endVertex();
				worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, k).endVertex();
				worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, k).endVertex();
				worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, k).endVertex();
				tessellator.draw();
				GlStateManager.popMatrix();
			}

			GlStateManager.popMatrix();
			GlStateManager.colorMask(true, true, true, false);
		}

		worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.matrixMode(GL_PROJECTION);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL_MODELVIEW);
		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.enableDepth();
	}

	/**+
	 * Rotate and blurs the skybox view in the main menu
	 */
	private void rotateAndBlurSkybox(float parFloat1) {
		this.mc.getTextureManager().bindTexture(backgroundTexture);
		EaglercraftGPU.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		EaglercraftGPU.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		EaglercraftGPU.glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.colorMask(true, true, true, false);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		GlStateManager.disableAlpha();
		byte b0 = 3;

		for (int i = 0; i < b0; ++i) {
			float f = 1.0F / (float) (i + 1);
			int j = this.width;
			int k = this.height;
			float f1 = (float) (i - b0 / 2) / 256.0F;
			worldrenderer.pos((double) j, (double) k, (double) this.zLevel).tex((double) (0.0F + f1), 1.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
			worldrenderer.pos((double) j, 0.0D, (double) this.zLevel).tex((double) (1.0F + f1), 1.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
			worldrenderer.pos(0.0D, 0.0D, (double) this.zLevel).tex((double) (1.0F + f1), 0.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
			worldrenderer.pos(0.0D, (double) k, (double) this.zLevel).tex((double) (0.0F + f1), 0.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
		}

		tessellator.draw();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);
	}

	/**+
	 * Renders the skybox in the main menu
	 */
	private void renderSkybox(int parInt1, int parInt2, float parFloat1) {
		GlStateManager.viewport(0, 0, 256, 256);
		this.drawPanorama(parInt1, parInt2, parFloat1);
		this.rotateAndBlurSkybox(parFloat1);
		this.rotateAndBlurSkybox(parFloat1);
		this.rotateAndBlurSkybox(parFloat1);
		this.rotateAndBlurSkybox(parFloat1);
		this.rotateAndBlurSkybox(parFloat1);
		this.rotateAndBlurSkybox(parFloat1);
		this.rotateAndBlurSkybox(parFloat1);
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		float f = this.width > this.height ? 120.0F / (float) this.width : 120.0F / (float) this.height;
		float f1 = (float) this.height * f / 256.0F;
		float f2 = (float) this.width * f / 256.0F;
		int i = this.width;
		int j = this.height;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldrenderer.pos(0.0D, (double) j, (double) this.zLevel).tex((double) (0.5F - f1), (double) (0.5F + f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		worldrenderer.pos((double) i, (double) j, (double) this.zLevel).tex((double) (0.5F - f1), (double) (0.5F - f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		worldrenderer.pos((double) i, 0.0D, (double) this.zLevel).tex((double) (0.5F + f1), (double) (0.5F - f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		worldrenderer.pos(0.0D, 0.0D, (double) this.zLevel).tex((double) (0.5F + f1), (double) (0.5F + f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		tessellator.draw();
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		GlStateManager.disableAlpha();
		this.renderSkybox(i, j, f);
		GlStateManager.enableAlpha();
		short short1 = 274;
		int k = this.width / 2 - short1 / 2;
		byte b0 = 30;
		this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
		this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
		this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.isDefault || (double) this.updateCounter < 1.0E-4D) {
			this.drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 99, 44);
			this.drawTexturedModalRect(k + 99, b0 + 0, 129, 0, 27, 44);
			this.drawTexturedModalRect(k + 99 + 26, b0 + 0, 126, 0, 3, 44);
			this.drawTexturedModalRect(k + 99 + 26 + 3, b0 + 0, 99, 0, 26, 44);
			this.drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
		} else {
			this.drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 155, 44);
			this.drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
		}

		boolean isForkLabel = ((this.openGLWarning1 != null && this.openGLWarning1.length() > 0)
				|| (this.openGLWarning2 != null && this.openGLWarning2.length() > 0));

		if (isForkLabel) {
			drawRect(this.field_92022_t - 3, this.field_92021_u - 3, this.field_92020_v + 3, this.field_92019_w,
					1428160512);
			if (this.openGLWarning1 != null)
				this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
			if (this.openGLWarning2 != null)
				this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2,
						this.field_92021_u + 12, -1);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) (this.width / 2 + 90), 70.0F, 0.0F);
		GlStateManager.rotate(isForkLabel ? -12.0F : -20.0F, 0.0F, 0.0F, 1.0F);
		float f1 = 1.8F - MathHelper
				.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * 3.1415927F * 2.0F) * 0.1F);
		f1 = f1 * 100.0F / (float) (this.fontRendererObj.getStringWidth(this.splashText) + 32);
		if (isForkLabel) {
			f1 *= 0.8f;
		}
		GlStateManager.scale(f1, f1, f1);
		this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
		GlStateManager.popMatrix();

		String s = EaglercraftVersion.mainMenuStringA;
		this.drawString(this.fontRendererObj, s, 2, this.height - 20, -1);
		s = EaglercraftVersion.mainMenuStringB;
		this.drawString(this.fontRendererObj, s, 2, this.height - 10, -1);

		String s1 = EaglercraftVersion.mainMenuStringC;
		this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2,
				this.height - 20, -1);
		s1 = EaglercraftVersion.mainMenuStringD;
		this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2,
				this.height - 10, -1);

		String lbl = "CREDITS.txt";
		int w = fontRendererObj.getStringWidth(lbl) * 3 / 4;

		if (i >= (this.width - w - 4) && i <= this.width && j >= 0 && j <= 9) {
			drawRect((this.width - w - 4), 0, this.width, 10, 0x55000099);
		} else {
			drawRect((this.width - w - 4), 0, this.width, 10, 0x55200000);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate((this.width - w - 2), 2.0f, 0.0f);
		GlStateManager.scale(0.75f, 0.75f, 0.75f);
		drawString(fontRendererObj, lbl, 0, 0, 16777215);
		GlStateManager.popMatrix();

		super.drawScreen(i, j, f);
	}

	/**+
	 * Called when the mouse is clicked. Args : mouseX, mouseY,
	 * clickedButton
	 */
	protected void mouseClicked(int par1, int par2, int par3) {
		if (par3 == 0) {
			String lbl = "CREDITS.txt";
			int w = fontRendererObj.getStringWidth(lbl) * 3 / 4;
			if (par1 >= (this.width - w - 4) && par1 <= this.width && par2 >= 0 && par2 <= 10) {
				String resStr = EagRuntime.getResourceString("/assets/eagler/CREDITS.txt");
				if (resStr != null) {
					EagRuntime.openCreditsPopup(resStr);
				}
				mc.getSoundHandler()
						.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
				return;
			}
		}
		super.mouseClicked(par1, par2, par3);
	}
}