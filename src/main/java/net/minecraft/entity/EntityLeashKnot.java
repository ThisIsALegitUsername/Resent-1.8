package net.minecraft.entity;

import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
public class EntityLeashKnot extends EntityHanging {
	public EntityLeashKnot(World worldIn) {
		super(worldIn);
	}

	public EntityLeashKnot(World worldIn, BlockPos hangingPositionIn) {
		super(worldIn, hangingPositionIn);
		this.setPosition((double) hangingPositionIn.getX() + 0.5D, (double) hangingPositionIn.getY() + 0.5D,
				(double) hangingPositionIn.getZ() + 0.5D);
		float f = 0.125F;
		float f1 = 0.1875F;
		float f2 = 0.25F;
		this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875D, this.posY - 0.25D + 0.125D,
				this.posZ - 0.1875D, this.posX + 0.1875D, this.posY + 0.25D + 0.125D, this.posZ + 0.1875D));
	}

	protected void entityInit() {
		super.entityInit();
	}

	/**+
	 * Updates facing and bounding box based on it
	 */
	public void updateFacingWithBoundingBox(EnumFacing var1) {
	}

	public int getWidthPixels() {
		return 9;
	}

	public int getHeightPixels() {
		return 9;
	}

	public float getEyeHeight() {
		return -0.0625F;
	}

	/**+
	 * Checks if the entity is in range to render by using the past
	 * in distance and comparing it to its average edge length * 64
	 * * renderDistanceWeight Args: distance
	 */
	public boolean isInRangeToRenderDist(double d0) {
		return d0 < 1024.0D;
	}

	/**+
	 * Called when this entity is broken. Entity parameter may be
	 * null.
	 */
	public void onBroken(Entity var1) {
	}

	/**+
	 * Either write this entity to the NBT tag given and return
	 * true, or return false without doing anything. If this returns
	 * false the entity is not saved on disk. Ridden entities return
	 * false here as they are saved with their rider.
	 */
	public boolean writeToNBTOptional(NBTTagCompound var1) {
		return false;
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound var1) {
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound var1) {
	}

	/**+
	 * First layer of player interaction
	 */
	public boolean interactFirst(EntityPlayer entityplayer) {
		return true;
	}

	/**+
	 * checks to make sure painting can be placed there
	 */
	public boolean onValidSurface() {
		return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof BlockFence;
	}

	public static EntityLeashKnot createKnot(World worldIn, BlockPos fence) {
		EntityLeashKnot entityleashknot = new EntityLeashKnot(worldIn, fence);
		entityleashknot.forceSpawn = true;
		worldIn.spawnEntityInWorld(entityleashknot);
		return entityleashknot;
	}

	public static EntityLeashKnot getKnotForPosition(World worldIn, BlockPos pos) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		for (EntityLeashKnot entityleashknot : worldIn.getEntitiesWithinAABB(EntityLeashKnot.class,
				new AxisAlignedBB((double) i - 1.0D, (double) j - 1.0D, (double) k - 1.0D, (double) i + 1.0D,
						(double) j + 1.0D, (double) k + 1.0D))) {
			if (entityleashknot.getHangingPosition().equals(pos)) {
				return entityleashknot;
			}
		}

		return null;
	}
}