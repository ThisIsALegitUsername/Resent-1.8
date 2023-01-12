package net.minecraft.entity.ai.attributes;

import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import org.apache.commons.lang3.Validate;

import net.lax1dude.eaglercraft.v1_8.ThreadLocalRandom;
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
public class AttributeModifier {
	private final double amount;
	private final int operation;
	private final String name;
	private final EaglercraftUUID id;
	private boolean isSaved;

	public AttributeModifier(String nameIn, double amountIn, int operationIn) {
		this(MathHelper.getRandomUuid(ThreadLocalRandom.current()), nameIn, amountIn, operationIn);
	}

	public AttributeModifier(EaglercraftUUID idIn, String nameIn, double amountIn, int operationIn) {
		this.isSaved = true;
		this.id = idIn;
		this.name = nameIn;
		this.amount = amountIn;
		this.operation = operationIn;
		Validate.notEmpty(nameIn, "Modifier name cannot be empty", new Object[0]);
		Validate.inclusiveBetween(0L, 2L, (long) operationIn, "Invalid operation");
	}

	public EaglercraftUUID getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getOperation() {
		return this.operation;
	}

	public double getAmount() {
		return this.amount;
	}

	/**+
	 * @see #isSaved
	 */
	public boolean isSaved() {
		return this.isSaved;
	}

	/**+
	 * @see #isSaved
	 */
	public AttributeModifier setSaved(boolean saved) {
		this.isSaved = saved;
		return this;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			AttributeModifier attributemodifier = (AttributeModifier) object;
			if (this.id != null) {
				if (!this.id.equals(attributemodifier.id)) {
					return false;
				}
			} else if (attributemodifier.id != null) {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.id != null ? this.id.hashCode() : 0;
	}

	public String toString() {
		return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name=\'" + this.name
				+ '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
	}
}