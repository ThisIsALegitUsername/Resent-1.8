package net.minecraft.entity.ai.attributes;

import net.minecraft.util.MathHelper;

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
public class RangedAttribute extends BaseAttribute {
	private final double minimumValue;
	private final double maximumValue;
	private String description;

	public RangedAttribute(IAttribute parIAttribute, String unlocalizedNameIn, double defaultValue,
			double minimumValueIn, double maximumValueIn) {
		super(parIAttribute, unlocalizedNameIn, defaultValue);
		this.minimumValue = minimumValueIn;
		this.maximumValue = maximumValueIn;
		if (minimumValueIn > maximumValueIn) {
			throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
		} else if (defaultValue < minimumValueIn) {
			throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
		} else if (defaultValue > maximumValueIn) {
			throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
		}
	}

	public RangedAttribute setDescription(String descriptionIn) {
		this.description = descriptionIn;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public double clampValue(double d0) {
		d0 = MathHelper.clamp_double(d0, this.minimumValue, this.maximumValue);
		return d0;
	}
}