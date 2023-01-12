package net.minecraft.client.renderer;

import net.minecraft.util.BlockPos;

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
public class DestroyBlockProgress {
	private final int miningPlayerEntId;
	private final BlockPos position;
	private int partialBlockProgress;
	private int createdAtCloudUpdateTick;

	public DestroyBlockProgress(int miningPlayerEntIdIn, BlockPos positionIn) {
		this.miningPlayerEntId = miningPlayerEntIdIn;
		this.position = positionIn;
	}

	public BlockPos getPosition() {
		return this.position;
	}

	/**+
	 * inserts damage value into this partially destroyed Block. -1
	 * causes client renderer to delete it, otherwise ranges from 1
	 * to 10
	 */
	public void setPartialBlockDamage(int damage) {
		if (damage > 10) {
			damage = 10;
		}

		this.partialBlockProgress = damage;
	}

	public int getPartialBlockDamage() {
		return this.partialBlockProgress;
	}

	/**+
	 * saves the current Cloud update tick into the
	 * PartiallyDestroyedBlock
	 */
	public void setCloudUpdateTick(int createdAtCloudUpdateTickIn) {
		this.createdAtCloudUpdateTick = createdAtCloudUpdateTickIn;
	}

	/**+
	 * retrieves the 'date' at which the PartiallyDestroyedBlock was
	 * created
	 */
	public int getCreationCloudUpdateTick() {
		return this.createdAtCloudUpdateTick;
	}
}