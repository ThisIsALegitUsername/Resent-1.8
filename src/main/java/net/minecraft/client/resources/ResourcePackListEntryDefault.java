package net.minecraft.client.resources;

import java.io.IOException;

import org.json.JSONException;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
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
public class ResourcePackListEntryDefault extends ResourcePackListEntry {
	private static final Logger logger = LogManager.getLogger();
	private final IResourcePack field_148320_d;
	private final ResourceLocation resourcePackIcon;

	public ResourcePackListEntryDefault(GuiScreenResourcePacks resourcePacksGUIIn) {
		super(resourcePacksGUIIn);
		this.field_148320_d = this.mc.getResourcePackRepository().rprDefaultResourcePack;

		DynamicTexture dynamictexture;
		try {
			dynamictexture = new DynamicTexture(this.field_148320_d.getPackImage());
		} catch (IOException var4) {
			dynamictexture = TextureUtil.missingTexture;
		}

		this.resourcePackIcon = this.mc.getTextureManager().getDynamicTextureLocation("texturepackicon",
				dynamictexture);
	}

	protected int func_183019_a() {
		return 1;
	}

	protected String func_148311_a() {
		try {
			PackMetadataSection packmetadatasection = (PackMetadataSection) this.field_148320_d
					.getPackMetadata(this.mc.getResourcePackRepository().rprMetadataSerializer, "pack");
			if (packmetadatasection != null) {
				return packmetadatasection.getPackDescription().getFormattedText();
			}
		} catch (JSONException jsonparseexception) {
			logger.error("Couldn\'t load metadata info", jsonparseexception);
		} catch (IOException ioexception) {
			logger.error("Couldn\'t load metadata info", ioexception);
		}

		return EnumChatFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
	}

	protected boolean func_148309_e() {
		return false;
	}

	protected boolean func_148308_f() {
		return false;
	}

	protected boolean func_148314_g() {
		return false;
	}

	protected boolean func_148307_h() {
		return false;
	}

	protected String func_148312_b() {
		return "Default";
	}

	protected void func_148313_c() {
		this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
	}

	protected boolean func_148310_d() {
		return false;
	}
}