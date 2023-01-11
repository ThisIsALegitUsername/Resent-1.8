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
public class ModelSign extends ModelBase {
	/**+
	 * The board on a sign that has the writing on it.
	 */
	public ModelRenderer signBoard = new ModelRenderer(this, 0, 0);
	public ModelRenderer signStick;

	public ModelSign() {
		this.signBoard.addBox(-12.0F, -14.0F, -1.0F, 24, 12, 2, 0.0F);
		this.signStick = new ModelRenderer(this, 0, 14);
		this.signStick.addBox(-1.0F, -2.0F, -1.0F, 2, 14, 2, 0.0F);
	}

	/**+
	 * Renders the sign model through TileEntitySignRenderer
	 */
	public void renderSign() {
		this.signBoard.render(0.0625F);
		this.signStick.render(0.0625F);
	}
}