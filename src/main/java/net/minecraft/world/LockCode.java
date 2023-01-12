package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

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
public class LockCode {
	public static final LockCode EMPTY_CODE = new LockCode("");
	private final String lock;

	public LockCode(String code) {
		this.lock = code;
	}

	public boolean isEmpty() {
		return this.lock == null || this.lock.isEmpty();
	}

	public String getLock() {
		return this.lock;
	}

	public void toNBT(NBTTagCompound nbt) {
		nbt.setString("Lock", this.lock);
	}

	public static LockCode fromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Lock", 8)) {
			String s = nbt.getString("Lock");
			return new LockCode(s);
		} else {
			return EMPTY_CODE;
		}
	}
}