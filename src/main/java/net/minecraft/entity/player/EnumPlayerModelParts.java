package net.minecraft.entity.player;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

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
public enum EnumPlayerModelParts {
	CAPE(0, "cape"), JACKET(1, "jacket"), LEFT_SLEEVE(2, "left_sleeve"), RIGHT_SLEEVE(3, "right_sleeve"),
	LEFT_PANTS_LEG(4, "left_pants_leg"), RIGHT_PANTS_LEG(5, "right_pants_leg"), HAT(6, "hat");

	private final int partId;
	private final int partMask;
	private final String partName;
	private final IChatComponent field_179339_k;

	private EnumPlayerModelParts(int partIdIn, String partNameIn) {
		this.partId = partIdIn;
		this.partMask = 1 << partIdIn;
		this.partName = partNameIn;
		this.field_179339_k = new ChatComponentTranslation("options.modelPart." + partNameIn, new Object[0]);
	}

	public int getPartMask() {
		return this.partMask;
	}

	public int getPartId() {
		return this.partId;
	}

	public String getPartName() {
		return this.partName;
	}

	public IChatComponent func_179326_d() {
		return this.field_179339_k;
	}
}