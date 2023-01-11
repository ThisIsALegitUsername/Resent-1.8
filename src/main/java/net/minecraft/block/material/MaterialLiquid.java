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
public class MaterialLiquid extends Material {
	public MaterialLiquid(MapColor color) {
		super(color);
		this.setReplaceable();
		this.setNoPushMobility();
	}

	/**+
	 * Returns if blocks of these materials are liquids.
	 */
	public boolean isLiquid() {
		return true;
	}

	/**+
	 * Returns if this material is considered solid or not
	 */
	public boolean blocksMovement() {
		return false;
	}

	/**+
	 * Returns true if the block is a considered solid. This is true
	 * by default.
	 */
	public boolean isSolid() {
		return false;
	}
}