package net.minecraft.client.gui.spectator;

import net.lax1dude.eaglercraft.v1_8.mojang.authlib.GameProfile;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
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
public class PlayerMenuObject implements ISpectatorMenuObject {
	private final GameProfile profile;

	public PlayerMenuObject(GameProfile profileIn) {
		this.profile = profileIn;
	}

	public void func_178661_a(SpectatorMenu menu) {
		Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C18PacketSpectate(this.profile.getId()));
	}

	public IChatComponent getSpectatorName() {
		return new ChatComponentText(this.profile.getName());
	}

	public void func_178663_a(float alpha, int parInt1) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(
				Minecraft.getMinecraft().getNetHandler().getSkinCache().getSkin(profile).getResourceLocation());
		GlStateManager.color(1.0F, 1.0F, 1.0F, (float) parInt1 / 255.0F);
		Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
		Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
	}

	public boolean func_178662_A_() {
		return true;
	}
}