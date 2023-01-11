package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

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
public class TileEntityNote extends TileEntity {
	public byte note;
	public boolean previousRedstoneState;

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("note", this.note);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.note = nbttagcompound.getByte("note");
		this.note = (byte) MathHelper.clamp_int(this.note, 0, 24);
	}

	/**+
	 * change pitch by -> (currentPitch + 1) % 25
	 */
	public void changePitch() {
		this.note = (byte) ((this.note + 1) % 25);
		this.markDirty();
	}

	public void triggerNote(World worldIn, BlockPos parBlockPos) {
		if (worldIn.getBlockState(parBlockPos.up()).getBlock().getMaterial() == Material.air) {
			Material material = worldIn.getBlockState(parBlockPos.down()).getBlock().getMaterial();
			byte b0 = 0;
			if (material == Material.rock) {
				b0 = 1;
			}

			if (material == Material.sand) {
				b0 = 2;
			}

			if (material == Material.glass) {
				b0 = 3;
			}

			if (material == Material.wood) {
				b0 = 4;
			}

			worldIn.addBlockEvent(parBlockPos, Blocks.noteblock, b0, this.note);
		}
	}
}