package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;

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
public class TileEntityEnderChest extends TileEntity implements ITickable {
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;

	/**+
	 * Like the old updateEntity(), except more generic.
	 */
	public void update() {
		if (++this.ticksSinceSync % 20 * 4 == 0) {
			this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
		}

		this.prevLidAngle = this.lidAngle;
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		float f = 0.1F;
		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			double d0 = (double) i + 0.5D;
			double d1 = (double) k + 0.5D;
			this.worldObj.playSoundEffect(d0, (double) j + 0.5D, d1, "random.chestopen", 0.5F,
					this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f2 = this.lidAngle;
			if (this.numPlayersUsing > 0) {
				this.lidAngle += f;
			} else {
				this.lidAngle -= f;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f1 = 0.5F;
			if (this.lidAngle < f1 && f2 >= f1) {
				double d3 = (double) i + 0.5D;
				double d2 = (double) k + 0.5D;
				this.worldObj.playSoundEffect(d3, (double) j + 0.5D, d2, "random.chestclosed", 0.5F,
						this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}

	}

	public boolean receiveClientEvent(int i, int j) {
		if (i == 1) {
			this.numPlayersUsing = j;
			return true;
		} else {
			return super.receiveClientEvent(i, j);
		}
	}

	/**+
	 * invalidates a tile entity
	 */
	public void invalidate() {
		this.updateContainingBlockInfo();
		super.invalidate();
	}

	public void openChest() {
		++this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
	}

	public void closeChest() {
		--this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
	}

	public boolean canBeUsed(EntityPlayer parEntityPlayer) {
		return this.worldObj.getTileEntity(this.pos) != this ? false
				: parEntityPlayer.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
						(double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
}