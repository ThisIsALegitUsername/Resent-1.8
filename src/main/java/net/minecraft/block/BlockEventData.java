package net.minecraft.block;

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
public class BlockEventData {
	private BlockPos position;
	private Block blockType;
	private int eventID;
	private int eventParameter;

	public BlockEventData(BlockPos pos, Block blockType, int eventId, int parInt1) {
		this.position = pos;
		this.eventID = eventId;
		this.eventParameter = parInt1;
		this.blockType = blockType;
	}

	public BlockPos getPosition() {
		return this.position;
	}

	/**+
	 * Get the Event ID (different for each BlockID)
	 */
	public int getEventID() {
		return this.eventID;
	}

	public int getEventParameter() {
		return this.eventParameter;
	}

	public Block getBlock() {
		return this.blockType;
	}

	public boolean equals(Object parObject) {
		if (!(parObject instanceof BlockEventData)) {
			return false;
		} else {
			BlockEventData blockeventdata = (BlockEventData) parObject;
			return this.position.equals(blockeventdata.position) && this.eventID == blockeventdata.eventID
					&& this.eventParameter == blockeventdata.eventParameter
					&& this.blockType == blockeventdata.blockType;
		}
	}

	public String toString() {
		return "TE(" + this.position + ")," + this.eventID + "," + this.eventParameter + "," + this.blockType;
	}
}