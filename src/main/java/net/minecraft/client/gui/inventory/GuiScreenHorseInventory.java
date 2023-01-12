package net.minecraft.client.gui.inventory;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
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
public class GuiScreenHorseInventory extends GuiContainer {
	private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
	private IInventory playerInventory;
	private IInventory horseInventory;
	private EntityHorse horseEntity;
	private float mousePosx;
	private float mousePosY;

	public GuiScreenHorseInventory(IInventory playerInv, IInventory horseInv, EntityHorse horse) {
		super(new ContainerHorseInventory(playerInv, horseInv, horse, Minecraft.getMinecraft().thePlayer));
		this.playerInventory = playerInv;
		this.horseInventory = horseInv;
		this.horseEntity = horse;
		this.allowUserInput = false;
	}

	/**+
	 * Draw the foreground layer for the GuiContainer (everything in
	 * front of the items). Args : mouseX, mouseY
	 */
	protected void drawGuiContainerForegroundLayer(int var1, int var2) {
		this.fontRendererObj.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8,
				this.ySize - 96 + 2, 4210752);
	}

	/**+
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(horseGuiTextures);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		if (this.horseEntity.isChested()) {
			this.drawTexturedModalRect(i + 79, j + 17, 0, this.ySize, 90, 54);
		}

		if (this.horseEntity.canWearArmor()) {
			this.drawTexturedModalRect(i + 7, j + 35, 0, this.ySize + 54, 18, 18);
		}

		GuiInventory.drawEntityOnScreen(i + 51, j + 60, 17, (float) (i + 51) - this.mousePosx,
				(float) (j + 75 - 50) - this.mousePosY, this.horseEntity);
	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.mousePosx = (float) i;
		this.mousePosY = (float) j;
		super.drawScreen(i, j, f);
	}
}