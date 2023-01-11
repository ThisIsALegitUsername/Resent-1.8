package net.minecraft.block.material;

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
public class MaterialPortal extends Material {
	public MaterialPortal(MapColor color) {
		super(color);
	}

	/**+
	 * Returns true if the block is a considered solid. This is true
	 * by default.
	 */
	public boolean isSolid() {
		return false;
	}

	/**+
	 * Will prevent grass from growing on dirt underneath and kill
	 * any grass below it if it returns true
	 */
	public boolean blocksLight() {
		return false;
	}

	/**+
	 * Returns if this material is considered solid or not
	 */
	public boolean blocksMovement() {
		return false;
	}
}