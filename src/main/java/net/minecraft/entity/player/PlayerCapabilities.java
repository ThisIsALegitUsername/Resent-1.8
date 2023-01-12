package net.minecraft.entity.player;

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
public class PlayerCapabilities {
	public boolean disableDamage;
	public boolean isFlying;
	public boolean allowFlying;
	public boolean isCreativeMode;
	/**+
	 * Indicates whether the player is allowed to modify the
	 * surroundings
	 */
	public boolean allowEdit = true;
	private float flySpeed = 0.05F;
	private float walkSpeed = 0.1F;

	public void writeCapabilitiesToNBT(NBTTagCompound tagCompound) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setBoolean("invulnerable", this.disableDamage);
		nbttagcompound.setBoolean("flying", this.isFlying);
		nbttagcompound.setBoolean("mayfly", this.allowFlying);
		nbttagcompound.setBoolean("instabuild", this.isCreativeMode);
		nbttagcompound.setBoolean("mayBuild", this.allowEdit);
		nbttagcompound.setFloat("flySpeed", this.flySpeed);
		nbttagcompound.setFloat("walkSpeed", this.walkSpeed);
		tagCompound.setTag("abilities", nbttagcompound);
	}

	public void readCapabilitiesFromNBT(NBTTagCompound tagCompound) {
		if (tagCompound.hasKey("abilities", 10)) {
			NBTTagCompound nbttagcompound = tagCompound.getCompoundTag("abilities");
			this.disableDamage = nbttagcompound.getBoolean("invulnerable");
			this.isFlying = nbttagcompound.getBoolean("flying");
			this.allowFlying = nbttagcompound.getBoolean("mayfly");
			this.isCreativeMode = nbttagcompound.getBoolean("instabuild");
			if (nbttagcompound.hasKey("flySpeed", 99)) {
				this.flySpeed = nbttagcompound.getFloat("flySpeed");
				this.walkSpeed = nbttagcompound.getFloat("walkSpeed");
			}

			if (nbttagcompound.hasKey("mayBuild", 1)) {
				this.allowEdit = nbttagcompound.getBoolean("mayBuild");
			}
		}

	}

	public float getFlySpeed() {
		return this.flySpeed;
	}

	public void setFlySpeed(float speed) {
		this.flySpeed = speed;
	}

	public float getWalkSpeed() {
		return this.walkSpeed;
	}

	public void setPlayerWalkSpeed(float speed) {
		this.walkSpeed = speed;
	}
}