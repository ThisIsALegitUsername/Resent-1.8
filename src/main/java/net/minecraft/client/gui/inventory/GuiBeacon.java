package net.minecraft.client.gui.inventory;

import net.lax1dude.eaglercraft.v1_8.netty.Unpooled;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
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
public class GuiBeacon extends GuiContainer {
	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
	private IInventory tileBeacon;
	private GuiBeacon.ConfirmButton beaconConfirmButton;
	private boolean buttonsNotDrawn;

	public GuiBeacon(InventoryPlayer playerInventory, IInventory tileBeaconIn) {
		super(new ContainerBeacon(playerInventory, tileBeaconIn));
		this.tileBeacon = tileBeaconIn;
		this.xSize = 230;
		this.ySize = 219;
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		super.initGui();
		this.buttonList
				.add(this.beaconConfirmButton = new GuiBeacon.ConfirmButton(-1, this.guiLeft + 164, this.guiTop + 107));
		this.buttonList.add(new GuiBeacon.CancelButton(-2, this.guiLeft + 190, this.guiTop + 107));
		this.buttonsNotDrawn = true;
		this.beaconConfirmButton.enabled = false;
	}

	/**+
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		super.updateScreen();
		int i = this.tileBeacon.getField(0);
		int j = this.tileBeacon.getField(1);
		int k = this.tileBeacon.getField(2);
		if (this.buttonsNotDrawn && i >= 0) {
			this.buttonsNotDrawn = false;

			for (int l = 0; l <= 2; ++l) {
				int i1 = TileEntityBeacon.effectsList[l].length;
				int j1 = i1 * 22 + (i1 - 1) * 2;

				for (int k1 = 0; k1 < i1; ++k1) {
					int l1 = TileEntityBeacon.effectsList[l][k1].id;
					GuiBeacon.PowerButton guibeacon$powerbutton = new GuiBeacon.PowerButton(l << 8 | l1,
							this.guiLeft + 76 + k1 * 24 - j1 / 2, this.guiTop + 22 + l * 25, l1, l);
					this.buttonList.add(guibeacon$powerbutton);
					if (l >= i) {
						guibeacon$powerbutton.enabled = false;
					} else if (l1 == j) {
						guibeacon$powerbutton.func_146140_b(true);
					}
				}
			}

			byte b0 = 3;
			int i2 = TileEntityBeacon.effectsList[b0].length + 1;
			int j2 = i2 * 22 + (i2 - 1) * 2;

			for (int k2 = 0; k2 < i2 - 1; ++k2) {
				int l2 = TileEntityBeacon.effectsList[b0][k2].id;
				GuiBeacon.PowerButton guibeacon$powerbutton2 = new GuiBeacon.PowerButton(b0 << 8 | l2,
						this.guiLeft + 167 + k2 * 24 - j2 / 2, this.guiTop + 47, l2, b0);
				this.buttonList.add(guibeacon$powerbutton2);
				if (b0 >= i) {
					guibeacon$powerbutton2.enabled = false;
				} else if (l2 == k) {
					guibeacon$powerbutton2.func_146140_b(true);
				}
			}

			if (j > 0) {
				GuiBeacon.PowerButton guibeacon$powerbutton1 = new GuiBeacon.PowerButton(b0 << 8 | j,
						this.guiLeft + 167 + (i2 - 1) * 24 - j2 / 2, this.guiTop + 47, j, b0);
				this.buttonList.add(guibeacon$powerbutton1);
				if (b0 >= i) {
					guibeacon$powerbutton1.enabled = false;
				} else if (j == k) {
					guibeacon$powerbutton1.func_146140_b(true);
				}
			}
		}

		this.beaconConfirmButton.enabled = this.tileBeacon.getStackInSlot(0) != null && j > 0;
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.id == -2) {
			this.mc.displayGuiScreen((GuiScreen) null);
		} else if (parGuiButton.id == -1) {
			String s = "MC|Beacon";
			PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
			packetbuffer.writeInt(this.tileBeacon.getField(1));
			packetbuffer.writeInt(this.tileBeacon.getField(2));
			this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(s, packetbuffer));
			this.mc.displayGuiScreen((GuiScreen) null);
		} else if (parGuiButton instanceof GuiBeacon.PowerButton) {
			if (((GuiBeacon.PowerButton) parGuiButton).func_146141_c()) {
				return;
			}

			int j = parGuiButton.id;
			int k = j & 255;
			int i = j >> 8;
			if (i < 3) {
				this.tileBeacon.setField(1, k);
			} else {
				this.tileBeacon.setField(2, k);
			}

			this.buttonList.clear();
			this.initGui();
			this.updateScreen();
		}

	}

	/**+
	 * Draw the foreground layer for the GuiContainer (everything in
	 * front of the items). Args : mouseX, mouseY
	 */
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		RenderHelper.disableStandardItemLighting();
		this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10,
				14737632);
		this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10,
				14737632);

		for (GuiButton guibutton : this.buttonList) {
			if (guibutton.isMouseOver()) {
				guibutton.drawButtonForegroundLayer(i - this.guiLeft, j - this.guiTop);
				break;
			}
		}

		RenderHelper.enableGUIStandardItemLighting();
	}

	/**+
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(beaconGuiTextures);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		this.itemRender.zLevel = 100.0F;
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), i + 42, j + 109);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.diamond), i + 42 + 22, j + 109);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.gold_ingot), i + 42 + 44, j + 109);
		this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.iron_ingot), i + 42 + 66, j + 109);
		this.itemRender.zLevel = 0.0F;
	}

	static class Button extends GuiButton {
		private final ResourceLocation field_146145_o;
		private final int field_146144_p;
		private final int field_146143_q;
		private boolean field_146142_r;

		protected Button(int parInt1, int parInt2, int parInt3, ResourceLocation parResourceLocation, int parInt4,
				int parInt5) {
			super(parInt1, parInt2, parInt3, 22, 22, "");
			this.field_146145_o = parResourceLocation;
			this.field_146144_p = parInt4;
			this.field_146143_q = parInt5;
		}

		public void drawButton(Minecraft minecraft, int i, int j) {
			if (this.visible) {
				minecraft.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width
						&& j < this.yPosition + this.height;
				short short1 = 219;
				int k = 0;
				if (!this.enabled) {
					k += this.width * 2;
				} else if (this.field_146142_r) {
					k += this.width * 1;
				} else if (this.hovered) {
					k += this.width * 3;
				}

				this.drawTexturedModalRect(this.xPosition, this.yPosition, k, short1, this.width, this.height);
				if (!GuiBeacon.beaconGuiTextures.equals(this.field_146145_o)) {
					minecraft.getTextureManager().bindTexture(this.field_146145_o);
				}

				this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p,
						this.field_146143_q, 18, 18);
			}
		}

		public boolean func_146141_c() {
			return this.field_146142_r;
		}

		public void func_146140_b(boolean parFlag) {
			this.field_146142_r = parFlag;
		}
	}

	class CancelButton extends GuiBeacon.Button {
		public CancelButton(int parInt1, int parInt2, int parInt3) {
			super(parInt1, parInt2, parInt3, GuiBeacon.beaconGuiTextures, 112, 220);
		}

		public void drawButtonForegroundLayer(int i, int j) {
			GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.cancel", new Object[0]), i, j);
		}
	}

	class ConfirmButton extends GuiBeacon.Button {
		public ConfirmButton(int parInt1, int parInt2, int parInt3) {
			super(parInt1, parInt2, parInt3, GuiBeacon.beaconGuiTextures, 90, 220);
		}

		public void drawButtonForegroundLayer(int i, int j) {
			GuiBeacon.this.drawCreativeTabHoveringText(I18n.format("gui.done", new Object[0]), i, j);
		}
	}

	class PowerButton extends GuiBeacon.Button {
		private final int field_146149_p;
		private final int field_146148_q;

		public PowerButton(int parInt1, int parInt2, int parInt3, int parInt4, int parInt5) {
			super(parInt1, parInt2, parInt3, GuiContainer.inventoryBackground,
					0 + Potion.potionTypes[parInt4].getStatusIconIndex() % 8 * 18,
					198 + Potion.potionTypes[parInt4].getStatusIconIndex() / 8 * 18);
			this.field_146149_p = parInt4;
			this.field_146148_q = parInt5;
		}

		public void drawButtonForegroundLayer(int i, int j) {
			String s = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object[0]);
			if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id) {
				s = s + " II";
			}

			GuiBeacon.this.drawCreativeTabHoveringText(s, i, j);
		}
	}
}