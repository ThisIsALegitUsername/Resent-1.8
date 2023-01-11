package net.minecraft.client.gui;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.Display;
import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.HString;
import net.lax1dude.eaglercraft.v1_8.internal.EnumPlatformType;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.OpenGlHelper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

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
public class GuiOverlayDebug extends Gui {
	private final Minecraft mc;
	private final FontRenderer fontRenderer;

	public GuiOverlayDebug(Minecraft mc) {
		this.mc = mc;
		this.fontRenderer = mc.fontRendererObj;
	}

	public void renderDebugInfo(ScaledResolution scaledResolutionIn, float partialTicks) {
		int ww = scaledResolutionIn.getScaledWidth();
		int hh = scaledResolutionIn.getScaledHeight();
		this.mc.mcProfiler.startSection("debug");
		if (this.mc.gameSettings.showDebugInfo) {
			GlStateManager.pushMatrix();
			this.renderDebugInfoLeft();
			this.renderDebugInfoRight(scaledResolutionIn);
			GlStateManager.popMatrix();
			if (this.mc.gameSettings.field_181657_aC) {
				this.func_181554_e();
			}
		} else {
			int i = 2;

			if (this.mc.gameSettings.hudFps) {
				drawFPS(2, i);
				i += 9;
			}

			if (this.mc.gameSettings.hudCoords) {
				drawXYZ(2, i);
			}

			if (this.mc.gameSettings.hudPlayer) {
				drawPlayer(ww - 3, 3, partialTicks);
			}
		}

		if (this.mc.currentScreen == null || !(this.mc.currentScreen instanceof GuiChat)) {
			if (this.mc.gameSettings.hudStats) {
				drawStatsHUD(ww - 2, hh - 2);
			}

			if (this.mc.gameSettings.hudWorld) {
				drawWorldHUD(2, hh - 2);
			}
		}

		if (this.mc.gameSettings.hudCoords && this.mc.joinWorldTickCounter < 80) {
			if (this.mc.joinWorldTickCounter > 70) {
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			}
			int i = this.mc.joinWorldTickCounter - 70;
			if (i < 0)
				i = 0;
			drawHideHUD(ww / 2, hh - 70, (10 - i) * 0xFF / 10);
			if (this.mc.joinWorldTickCounter > 70) {
				GlStateManager.disableBlend();
			}
		}

		this.mc.mcProfiler.endSection();
	}

	private void drawFPS(int x, int y) {
		this.fontRenderer.drawStringWithShadow(this.mc.renderGlobal.getDebugInfoShort(), x, y, 0xFFFFFF);
	}

	private void drawXYZ(int x, int y) {
		Entity e = mc.getRenderViewEntity();
		BlockPos blockpos = new BlockPos(e.posX, e.getEntityBoundingBox().minY, e.posZ);
		this.fontRenderer.drawStringWithShadow(
				"x: " + blockpos.getX() + ", y: " + blockpos.getY() + ", z: " + blockpos.getZ(), x, y, 0xFFFFFF);
	}

	private void drawStatsHUD(int x, int y) {
		int i = 9;

		String line = "Walk: " + EnumChatFormatting.YELLOW + HString.format("%.2f", mc.thePlayer.getAIMoveSpeed())
				+ EnumChatFormatting.WHITE + " Flight: "
				+ (mc.thePlayer.capabilities.allowFlying
						? ("" + EnumChatFormatting.YELLOW + mc.thePlayer.capabilities.getFlySpeed())
						: EnumChatFormatting.RED + "No");
		int lw = fontRenderer.getStringWidth(line);
		this.fontRenderer.drawStringWithShadow(line, x - lw, y - i, 0xFFFFFF);
		i += 11;

		line = "Food: " + EnumChatFormatting.YELLOW + mc.thePlayer.getFoodStats().getFoodLevel()
				+ EnumChatFormatting.WHITE + ", Sat: " + EnumChatFormatting.YELLOW
				+ HString.format("%.1f", mc.thePlayer.getFoodStats().getSaturationLevel());
		lw = fontRenderer.getStringWidth(line);
		this.fontRenderer.drawStringWithShadow(line, x - lw, y - i, 0xFFFFFF);
		i += 11;

		line = "Amr: " + EnumChatFormatting.YELLOW + mc.thePlayer.getTotalArmorValue() + EnumChatFormatting.WHITE
				+ ", Health: " + EnumChatFormatting.RED + HString.format("%.1f", mc.thePlayer.getHealth());
		lw = fontRenderer.getStringWidth(line);
		this.fontRenderer.drawStringWithShadow(line, x - lw, y - i, 0xFFFFFF);
		i += 11;

		int xpc = mc.thePlayer.xpBarCap();
		line = "XP: " + EnumChatFormatting.GREEN + MathHelper.floor_float(mc.thePlayer.experience * xpc)
				+ EnumChatFormatting.WHITE + " / " + EnumChatFormatting.GREEN + xpc;
		lw = fontRenderer.getStringWidth(line);
		this.fontRenderer.drawStringWithShadow(line, x - lw, y - i, 0xFFFFFF);
		i += 11;

		Iterator<PotionEffect> potions = mc.thePlayer.getActivePotionEffects().iterator();
		if (potions.hasNext()) {
			while (potions.hasNext()) {
				i += 11;
				PotionEffect e = potions.next();
				int t = e.getDuration() / 20;
				int m = t / 60;
				int s = t % 60;
				int j = e.getAmplifier();
				if (j > 0) {
					line = I18n.format(e.getEffectName())
							+ (j > 0 ? (" " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD
									+ I18n.format("potion.potency." + j) + EnumChatFormatting.RESET) : "")
							+ " [" + EnumChatFormatting.YELLOW + HString.format("%02d:%02d", m, s)
							+ EnumChatFormatting.RESET + "]";
				} else {
					line = I18n.format(e.getEffectName()) + " [" + EnumChatFormatting.YELLOW
							+ HString.format("%02d:%02d", m, s) + EnumChatFormatting.RESET + "]";
				}
				lw = fontRenderer.getStringWidth(line);
				this.fontRenderer.drawStringWithShadow(line, x - lw, y - i, 0xFFFFFF);
			}
		}

	}

	public static final int ticksAtMidnight = 18000;
	public static final int ticksPerDay = 24000;
	public static final int ticksPerHour = 1000;
	public static final double ticksPerMinute = 1000d / 60d;
	public static final double ticksPerSecond = 1000d / 60d / 60d;
	private static final SimpleDateFormat SDFTwentyFour = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	private static final SimpleDateFormat SDFTwelve = new SimpleDateFormat("h:mm aa", Locale.ENGLISH);

	private void drawWorldHUD(int x, int y) {
		/*
		 * Math was taken from: https://github.com/EssentialsX/Essentials/blob/
		 * dc7fb919391d62de45e17b51ae1e6fe3e66d7ac6/Essentials/src/main/java/com/
		 * earth2me/essentials/utils/DescParseTickFormat.java
		 */
		long totalTicks = mc.theWorld.getWorldTime();
		long ticks = totalTicks;
		ticks = ticks - ticksAtMidnight + ticksPerDay;
		final long days = ticks / ticksPerDay;
		ticks -= days * ticksPerDay;
		final long hours = ticks / ticksPerHour;
		ticks -= hours * ticksPerHour;
		final long minutes = (long) Math.floor(ticks / ticksPerMinute);
		final double dticks = ticks - minutes * ticksPerMinute;
		final long seconds = (long) Math.floor(dticks / ticksPerSecond);

		// TODO: why does desktop JRE not apply "GMT" correctly?
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.ENGLISH);

		cal.setLenient(true);
		cal.set(0, Calendar.JANUARY, 1, 0, 0, 0);
		cal.add(Calendar.DAY_OF_YEAR, (int) days);
		cal.add(Calendar.HOUR_OF_DAY, (int) hours);
		cal.add(Calendar.MINUTE, (int) minutes);
		cal.add(Calendar.SECOND, (int) seconds + 1);

		String timeString = EnumChatFormatting.WHITE + "Day " + ((totalTicks + 30000l) / 24000l) + " ("
				+ EnumChatFormatting.YELLOW
				+ (this.mc.gameSettings.hud24h ? SDFTwentyFour : SDFTwelve).format(cal.getTime())
				+ EnumChatFormatting.WHITE + ")";

		Entity e = mc.getRenderViewEntity();
		BlockPos blockpos = new BlockPos(e.posX, MathHelper.clamp_double(e.getEntityBoundingBox().minY, 0.0D, 254.0D),
				e.posZ);
		BiomeGenBase biome = mc.theWorld.getBiomeGenForCoords(blockpos);

		Chunk c = mc.theWorld.getChunkFromBlockCoords(blockpos);
		int blockLight = c.getLightFor(EnumSkyBlock.BLOCK, blockpos);
		int skyLight = c.getLightFor(EnumSkyBlock.SKY, blockpos) - mc.theWorld.calculateSkylightSubtracted(1.0f);
		int totalLight = Math.max(blockLight, skyLight);
		EnumChatFormatting lightColor = blockLight < 8
				? ((skyLight < 8 || !mc.theWorld.isDaytime()) ? EnumChatFormatting.RED : EnumChatFormatting.YELLOW)
				: EnumChatFormatting.GREEN;
		String lightString = "Light: " + lightColor + totalLight + EnumChatFormatting.WHITE;

		float temp = biome.getFloatTemperature(blockpos);

		String tempString = "Temp: "
				+ ((blockLight > 11 || temp > 0.15f) ? EnumChatFormatting.YELLOW : EnumChatFormatting.AQUA)
				+ HString.format("%.2f", temp) + EnumChatFormatting.WHITE;

		this.fontRenderer.drawStringWithShadow(timeString, x, y - 30, 0xFFFFFF);
		this.fontRenderer.drawStringWithShadow("Biome: " + EnumChatFormatting.AQUA + biome.biomeName, x, y - 19,
				0xFFFFFF);
		this.fontRenderer.drawStringWithShadow(lightString + " " + tempString, x, y - 8, 0xFFFFFF);
	}

	private void drawPlayer(int x, int y, float partialTicks) {
		Entity e = mc.getRenderViewEntity();
		if (e != null && e instanceof EntityLivingBase) {
			EntityLivingBase ent = (EntityLivingBase) e;
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			GlStateManager.enableDepth();
			GlStateManager.enableColorMaterial();
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x - 10, (float) y + 36, 50.0F);
			GlStateManager.scale(-17.0F, 17.0F, 17.0F);
			GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			float f = ent.renderYawOffset;
			float f1 = ent.rotationYaw;
			float f2 = ent.prevRotationYaw;
			float f3 = ent.prevRotationYawHead;
			float f4 = ent.rotationYawHead;
			float f5 = ent.prevRenderYawOffset;
			GlStateManager.rotate(115.0F, 0.0F, 1.0F, 0.0F);
			RenderHelper.enableStandardItemLighting();
			float f6 = ent.prevRenderYawOffset + (ent.renderYawOffset - ent.prevRenderYawOffset) * partialTicks;
			ent.rotationYawHead -= f6;
			ent.prevRotationYawHead -= f6;
			ent.rotationYawHead *= 0.5f;
			ent.prevRotationYawHead *= 0.5f;
			ent.renderYawOffset = 0.0f;
			ent.prevRenderYawOffset = 0.0f;
			ent.prevRotationYaw = 0.0f;
			ent.rotationYaw = 0.0f;
			GlStateManager.rotate(-135.0F
					- (ent.prevRotationYawHead + (ent.rotationYawHead - ent.prevRotationYawHead) * partialTicks) * 0.5F,
					0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(ent.rotationPitch * 0.2f, 1.0F, 0.0F, 0.0F);
			RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
			rendermanager.setPlayerViewY(180.0F);
			rendermanager.setRenderShadow(false);
			rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
			rendermanager.setRenderShadow(true);
			ent.renderYawOffset = f;
			ent.rotationYaw = f1;
			ent.prevRotationYaw = f2;
			ent.prevRotationYawHead = f3;
			ent.rotationYawHead = f4;
			ent.prevRenderYawOffset = f5;
			GlStateManager.popMatrix();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableDepth();
			GlStateManager.disableRescaleNormal();
			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.disableTexture2D();
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
	}

	private void drawHideHUD(int x, int y, int fade) {
		drawCenteredString(fontRenderer, I18n.format("options.hud.note"), x, y, 0xEECC00 | (fade << 24));
	}

	private boolean isReducedDebug() {
		return this.mc.thePlayer.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo;
	}

	protected void renderDebugInfoLeft() {
		List list = this.call();

		for (int i = 0; i < list.size(); ++i) {
			String s = (String) list.get(i);
			if (!Strings.isNullOrEmpty(s)) {
				int j = this.fontRenderer.FONT_HEIGHT;
				int k = this.fontRenderer.getStringWidth(s);
				boolean flag = true;
				int l = 2 + j * i;
				drawRect(1, l - 1, 2 + k + 1, l + j - 1, -1873784752);
				this.fontRenderer.drawString(s, 2, l, 14737632);
			}
		}

	}

	protected void renderDebugInfoRight(ScaledResolution parScaledResolution) {
		List list = this.getDebugInfoRight();

		for (int i = 0; i < list.size(); ++i) {
			String s = (String) list.get(i);
			if (!Strings.isNullOrEmpty(s)) {
				int j = this.fontRenderer.FONT_HEIGHT;
				int k = this.fontRenderer.getStringWidth(s);
				int l = parScaledResolution.getScaledWidth() - 2 - k;
				int i1 = 2 + j * i;
				drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
				this.fontRenderer.drawString(s, l, i1, 14737632);
			}
		}

	}

	protected List<String> call() {
		if (!this.mc.gameSettings.showDebugInfo) {
			BlockPos blockpos = new BlockPos(this.mc.getRenderViewEntity().posX,
					this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
			return Lists.newArrayList(new String[] { this.mc.renderGlobal.getDebugInfoShort(),
					"x: " + blockpos.getX() + ", y: " + blockpos.getY() + ", z: " + blockpos.getZ() });
		}

		BlockPos blockpos = new BlockPos(this.mc.getRenderViewEntity().posX,
				this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
		if (this.isReducedDebug()) {
			return Lists.newArrayList(new String[] {
					"Minecraft 1.8.8 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")",
					this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(),
					this.mc.renderGlobal.getDebugInfoEntities(),
					"P: " + this.mc.effectRenderer.getStatistics() + ". T: "
							+ this.mc.theWorld.getDebugLoadedEntities(),
					this.mc.theWorld.getProviderName(), "",
					HString.format("Chunk-relative: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 15),
							Integer.valueOf(blockpos.getY() & 15), Integer.valueOf(blockpos.getZ() & 15) }) });
		} else {
			Entity entity = this.mc.getRenderViewEntity();
			EnumFacing enumfacing = entity.getHorizontalFacing();
			String s = "Invalid";
			switch (enumfacing) {
			case NORTH:
				s = "Towards negative Z";
				break;
			case SOUTH:
				s = "Towards positive Z";
				break;
			case WEST:
				s = "Towards negative X";
				break;
			case EAST:
				s = "Towards positive X";
			}

			ArrayList arraylist = Lists.newArrayList(new String[] {
					"Minecraft 1.8.8 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")",
					this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(),
					this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics()
							+ ". T: " + this.mc.theWorld.getDebugLoadedEntities(),
					this.mc.theWorld.getProviderName(), "",
					HString.format("XYZ: %.3f / %.5f / %.3f",
							new Object[] { Double.valueOf(this.mc.getRenderViewEntity().posX),
									Double.valueOf(this.mc.getRenderViewEntity().getEntityBoundingBox().minY),
									Double.valueOf(this.mc.getRenderViewEntity().posZ) }),
					HString.format("Block: %d %d %d",
							new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()),
									Integer.valueOf(blockpos.getZ()) }),
					HString.format("Chunk: %d %d %d in %d %d %d",
							new Object[] { Integer.valueOf(blockpos.getX() & 15), Integer.valueOf(blockpos.getY() & 15),
									Integer.valueOf(blockpos.getZ() & 15), Integer.valueOf(blockpos.getX() >> 4),
									Integer.valueOf(blockpos.getY() >> 4), Integer.valueOf(blockpos.getZ() >> 4) }),
					HString.format("Facing: %s (%s) (%.1f / %.1f)",
							new Object[] { enumfacing, s,
									Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationYaw)),
									Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationPitch)) }) });
			if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockpos)) {
				Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(blockpos);
				arraylist.add("Biome: " + chunk.getBiome(blockpos).biomeName);
				arraylist.add("Light: " + chunk.getLightSubtracted(blockpos, 0) + " ("
						+ chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " sky, "
						+ chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " block)");
				DifficultyInstance difficultyinstance = this.mc.theWorld.getDifficultyForLocation(blockpos);
				arraylist.add(HString.format("Local Difficulty: %.2f (Day %d)",
						new Object[] { Float.valueOf(difficultyinstance.getAdditionalDifficulty()),
								Long.valueOf(this.mc.theWorld.getWorldTime() / 24000L) }));
			}

			if (this.mc.objectMouseOver != null
					&& this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
					&& this.mc.objectMouseOver.getBlockPos() != null) {
				BlockPos blockpos1 = this.mc.objectMouseOver.getBlockPos();
				arraylist.add(HString.format("Looking at: %d %d %d", new Object[] { Integer.valueOf(blockpos1.getX()),
						Integer.valueOf(blockpos1.getY()), Integer.valueOf(blockpos1.getZ()) }));
			}

			return arraylist;
		}
	}

	protected List<String> getDebugInfoRight() {
		ArrayList arraylist;
		if (EagRuntime.getPlatformType() != EnumPlatformType.JAVASCRIPT) {
			long i = EagRuntime.maxMemory();
			long j = EagRuntime.totalMemory();
			long k = EagRuntime.freeMemory();
			long l = j - k;
			arraylist = Lists.newArrayList(new String[] {
					HString.format("Java: %s %dbit",
							new Object[] { System.getProperty("java.version"),
									Integer.valueOf(this.mc.isJava64bit() ? 64 : 32) }),
					HString.format("Mem: % 2d%% %03d/%03dMB",
							new Object[] { Long.valueOf(l * 100L / i), Long.valueOf(bytesToMb(l)),
									Long.valueOf(bytesToMb(i)) }),
					HString.format("Allocated: % 2d%% %03dMB",
							new Object[] { Long.valueOf(j * 100L / i), Long.valueOf(bytesToMb(j)) }),
					"", HString.format("CPU: %s", new Object[] { "eaglercraft" }), "",
					HString.format("Display: %dx%d (%s)",
							new Object[] { Integer.valueOf(Display.getWidth()), Integer.valueOf(Display.getHeight()),
									EaglercraftGPU.glGetString(7936) }),
					EaglercraftGPU.glGetString(7937), EaglercraftGPU.glGetString(7938) });
		} else {
			arraylist = Lists.newArrayList(
					new String[] { "Java: TeaVM", "", HString.format("CPU: %s", new Object[] { "eaglercraft" }), "",
							HString.format("Display: %dx%d (%s)",
									new Object[] { Integer.valueOf(Display.getWidth()),
											Integer.valueOf(Display.getHeight()), EaglercraftGPU.glGetString(7936) }),
							EaglercraftGPU.glGetString(7937), EaglercraftGPU.glGetString(7938) });
		}
		if (this.isReducedDebug()) {
			return arraylist;
		} else {
			if (this.mc.objectMouseOver != null
					&& this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
					&& this.mc.objectMouseOver.getBlockPos() != null) {
				BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
				IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
				arraylist.add("");
				arraylist.add(String.valueOf(Block.blockRegistry.getNameForObject(iblockstate.getBlock())));

				for (Entry entry : iblockstate.getProperties().entrySet()) {
					String s = ((Comparable) entry.getValue()).toString();
					if (entry.getValue() == Boolean.TRUE) {
						s = EnumChatFormatting.GREEN + s;
					} else if (entry.getValue() == Boolean.FALSE) {
						s = EnumChatFormatting.RED + s;
					}

					arraylist.add(((IProperty) entry.getKey()).getName() + ": " + s);
				}
			}

			return arraylist;
		}
	}

	private void func_181554_e() {
		GlStateManager.disableDepth();
		FrameTimer frametimer = this.mc.func_181539_aj();
		int i = frametimer.func_181749_a();
		int j = frametimer.func_181750_b();
		long[] along = frametimer.func_181746_c();
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		int k = i;
		int l = 0;
		drawRect(0, scaledresolution.getScaledHeight() - 60, 240, scaledresolution.getScaledHeight(), -1873784752);

		while (k != j) {
			int i1 = frametimer.func_181748_a(along[k], 30);
			int j1 = this.func_181552_c(MathHelper.clamp_int(i1, 0, 60), 0, 30, 60);
			this.drawVerticalLine(l, scaledresolution.getScaledHeight(), scaledresolution.getScaledHeight() - i1, j1);
			++l;
			k = frametimer.func_181751_b(k + 1);
		}

		drawRect(1, scaledresolution.getScaledHeight() - 30 + 1, 14, scaledresolution.getScaledHeight() - 30 + 10,
				-1873784752);
		this.fontRenderer.drawString("60", 2, scaledresolution.getScaledHeight() - 30 + 2, 14737632);
		this.drawHorizontalLine(0, 239, scaledresolution.getScaledHeight() - 30, -1);
		drawRect(1, scaledresolution.getScaledHeight() - 60 + 1, 14, scaledresolution.getScaledHeight() - 60 + 10,
				-1873784752);
		this.fontRenderer.drawString("30", 2, scaledresolution.getScaledHeight() - 60 + 2, 14737632);
		this.drawHorizontalLine(0, 239, scaledresolution.getScaledHeight() - 60, -1);
		this.drawHorizontalLine(0, 239, scaledresolution.getScaledHeight() - 1, -1);
		this.drawVerticalLine(0, scaledresolution.getScaledHeight() - 60, scaledresolution.getScaledHeight(), -1);
		this.drawVerticalLine(239, scaledresolution.getScaledHeight() - 60, scaledresolution.getScaledHeight(), -1);
		if (this.mc.gameSettings.limitFramerate <= 120) {
			this.drawHorizontalLine(0, 239,
					scaledresolution.getScaledHeight() - 60 + this.mc.gameSettings.limitFramerate / 2, -16711681);
		}

		GlStateManager.enableDepth();
	}

	private int func_181552_c(int parInt1, int parInt2, int parInt3, int parInt4) {
		return parInt1 < parInt3 ? this.func_181553_a(-16711936, -256, (float) parInt1 / (float) parInt3)
				: this.func_181553_a(-256, -65536, (float) (parInt1 - parInt3) / (float) (parInt4 - parInt3));
	}

	private int func_181553_a(int parInt1, int parInt2, float parFloat1) {
		int i = parInt1 >> 24 & 255;
		int j = parInt1 >> 16 & 255;
		int k = parInt1 >> 8 & 255;
		int l = parInt1 & 255;
		int i1 = parInt2 >> 24 & 255;
		int j1 = parInt2 >> 16 & 255;
		int k1 = parInt2 >> 8 & 255;
		int l1 = parInt2 & 255;
		int i2 = MathHelper.clamp_int((int) ((float) i + (float) (i1 - i) * parFloat1), 0, 255);
		int j2 = MathHelper.clamp_int((int) ((float) j + (float) (j1 - j) * parFloat1), 0, 255);
		int k2 = MathHelper.clamp_int((int) ((float) k + (float) (k1 - k) * parFloat1), 0, 255);
		int l2 = MathHelper.clamp_int((int) ((float) l + (float) (l1 - l) * parFloat1), 0, 255);
		return i2 << 24 | j2 << 16 | k2 << 8 | l2;
	}

	private static long bytesToMb(long bytes) {
		return bytes / 1024L / 1024L;
	}
}