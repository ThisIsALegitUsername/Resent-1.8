package net.minecraft.client.model;

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
public class ModelBanner extends ModelBase {
	public ModelRenderer bannerSlate;
	public ModelRenderer bannerStand;
	public ModelRenderer bannerTop;

	public ModelBanner() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.bannerSlate = new ModelRenderer(this, 0, 0);
		this.bannerSlate.addBox(-10.0F, 0.0F, -2.0F, 20, 40, 1, 0.0F);
		this.bannerStand = new ModelRenderer(this, 44, 0);
		this.bannerStand.addBox(-1.0F, -30.0F, -1.0F, 2, 42, 2, 0.0F);
		this.bannerTop = new ModelRenderer(this, 0, 42);
		this.bannerTop.addBox(-10.0F, -32.0F, -1.0F, 20, 2, 2, 0.0F);
	}

	/**+
	 * Renders the banner model in.
	 */
	public void renderBanner() {
		this.bannerSlate.rotationPointY = -32.0F;
		this.bannerSlate.render(0.0625F);
		this.bannerStand.render(0.0625F);
		this.bannerTop.render(0.0625F);
	}
}