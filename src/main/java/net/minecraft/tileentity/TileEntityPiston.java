package net.minecraft.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
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
public class TileEntityPiston extends TileEntity implements ITickable {
	private IBlockState pistonState;
	private EnumFacing pistonFacing;
	private boolean extending;
	private boolean shouldHeadBeRendered;
	private float progress;
	private float lastProgress;
	private List<Entity> field_174933_k = Lists.newArrayList();

	public TileEntityPiston() {
	}

	public TileEntityPiston(IBlockState pistonStateIn, EnumFacing pistonFacingIn, boolean extendingIn,
			boolean shouldHeadBeRenderedIn) {
		this.pistonState = pistonStateIn;
		this.pistonFacing = pistonFacingIn;
		this.extending = extendingIn;
		this.shouldHeadBeRendered = shouldHeadBeRenderedIn;
	}

	public IBlockState getPistonState() {
		return this.pistonState;
	}

	public int getBlockMetadata() {
		return 0;
	}

	/**+
	 * Returns true if a piston is extending
	 */
	public boolean isExtending() {
		return this.extending;
	}

	public EnumFacing getFacing() {
		return this.pistonFacing;
	}

	public boolean shouldPistonHeadBeRendered() {
		return this.shouldHeadBeRendered;
	}

	/**+
	 * Get interpolated progress value (between lastProgress and
	 * progress) given the fractional time between ticks as an
	 * argument
	 */
	public float getProgress(float ticks) {
		if (ticks > 1.0F) {
			ticks = 1.0F;
		}

		return this.lastProgress + (this.progress - this.lastProgress) * ticks;
	}

	public float getOffsetX(float ticks) {
		return this.extending ? (this.getProgress(ticks) - 1.0F) * (float) this.pistonFacing.getFrontOffsetX()
				: (1.0F - this.getProgress(ticks)) * (float) this.pistonFacing.getFrontOffsetX();
	}

	public float getOffsetY(float ticks) {
		return this.extending ? (this.getProgress(ticks) - 1.0F) * (float) this.pistonFacing.getFrontOffsetY()
				: (1.0F - this.getProgress(ticks)) * (float) this.pistonFacing.getFrontOffsetY();
	}

	public float getOffsetZ(float ticks) {
		return this.extending ? (this.getProgress(ticks) - 1.0F) * (float) this.pistonFacing.getFrontOffsetZ()
				: (1.0F - this.getProgress(ticks)) * (float) this.pistonFacing.getFrontOffsetZ();
	}

	private void launchWithSlimeBlock(float parFloat1, float parFloat2) {
		if (this.extending) {
			parFloat1 = 1.0F - parFloat1;
		} else {
			--parFloat1;
		}

		AxisAlignedBB axisalignedbb = Blocks.piston_extension.getBoundingBox(this.worldObj, this.pos, this.pistonState,
				parFloat1, this.pistonFacing);
		if (axisalignedbb != null) {
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, axisalignedbb);
			if (!list.isEmpty()) {
				this.field_174933_k.addAll(list);

				for (Entity entity : this.field_174933_k) {
					if (this.pistonState.getBlock() == Blocks.slime_block && this.extending) {
						switch (this.pistonFacing.getAxis()) {
						case X:
							entity.motionX = (double) this.pistonFacing.getFrontOffsetX();
							break;
						case Y:
							entity.motionY = (double) this.pistonFacing.getFrontOffsetY();
							break;
						case Z:
							entity.motionZ = (double) this.pistonFacing.getFrontOffsetZ();
						}
					} else {
						entity.moveEntity((double) (parFloat2 * (float) this.pistonFacing.getFrontOffsetX()),
								(double) (parFloat2 * (float) this.pistonFacing.getFrontOffsetY()),
								(double) (parFloat2 * (float) this.pistonFacing.getFrontOffsetZ()));
					}
				}

				this.field_174933_k.clear();
			}
		}

	}

	/**+
	 * removes a piston's tile entity (and if the piston is moving,
	 * stops it)
	 */
	public void clearPistonTileEntity() {
		if (this.lastProgress < 1.0F && this.worldObj != null) {
			this.lastProgress = this.progress = 1.0F;
			this.worldObj.removeTileEntity(this.pos);
			this.invalidate();
			if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
				this.worldObj.setBlockState(this.pos, this.pistonState, 3);
				this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
			}
		}

	}

	/**+
	 * Like the old updateEntity(), except more generic.
	 */
	public void update() {
		this.lastProgress = this.progress;
		if (this.lastProgress >= 1.0F) {
			this.launchWithSlimeBlock(1.0F, 0.25F);
			this.worldObj.removeTileEntity(this.pos);
			this.invalidate();
			if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
				this.worldObj.setBlockState(this.pos, this.pistonState, 3);
				this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
			}

		} else {
			this.progress += 0.5F;
			if (this.progress >= 1.0F) {
				this.progress = 1.0F;
			}

			if (this.extending) {
				this.launchWithSlimeBlock(this.progress, this.progress - this.lastProgress + 0.0625F);
			}

		}
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.pistonState = Block.getBlockById(nbttagcompound.getInteger("blockId"))
				.getStateFromMeta(nbttagcompound.getInteger("blockData"));
		this.pistonFacing = EnumFacing.getFront(nbttagcompound.getInteger("facing"));
		this.lastProgress = this.progress = nbttagcompound.getFloat("progress");
		this.extending = nbttagcompound.getBoolean("extending");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
		nbttagcompound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
		nbttagcompound.setInteger("facing", this.pistonFacing.getIndex());
		nbttagcompound.setFloat("progress", this.lastProgress);
		nbttagcompound.setBoolean("extending", this.extending);
	}
}