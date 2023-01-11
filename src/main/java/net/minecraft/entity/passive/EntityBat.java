package net.minecraft.entity.passive;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
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
public class EntityBat extends EntityAmbientCreature {
	private BlockPos spawnPosition;

	public EntityBat(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 0.9F);
		this.setIsBatHanging(true);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 0));
	}

	/**+
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.1F;
	}

	/**+
	 * Gets the pitch of living sounds in living entities.
	 */
	protected float getSoundPitch() {
		return super.getSoundPitch() * 0.95F;
	}

	/**+
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : "mob.bat.idle";
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.bat.hurt";
	}

	/**+
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.bat.death";
	}

	/**+
	 * Returns true if this entity should push and be pushed by
	 * other entities when colliding.
	 */
	public boolean canBePushed() {
		return false;
	}

	protected void collideWithEntity(Entity var1) {
	}

	protected void collideWithNearbyEntities() {
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
	}

	public boolean getIsBatHanging() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setIsBatHanging(boolean isHanging) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		if (isHanging) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 1)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -2)));
		}

	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		super.onUpdate();
		if (this.getIsBatHanging()) {
			this.motionX = this.motionY = this.motionZ = 0.0D;
			this.posY = (double) MathHelper.floor_double(this.posY) + 1.0D - (double) this.height;
		} else {
			this.motionY *= 0.6000000238418579D;
		}

	}

	protected void updateAITasks() {
		super.updateAITasks();
		BlockPos blockpos = new BlockPos(this);
		BlockPos blockpos1 = blockpos.up();
		if (this.getIsBatHanging()) {
			if (!this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube()) {
				this.setIsBatHanging(false);
				this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, blockpos, 0);
			} else {
				if (this.rand.nextInt(200) == 0) {
					this.rotationYawHead = (float) this.rand.nextInt(360);
				}

				if (this.worldObj.getClosestPlayerToEntity(this, 4.0D) != null) {
					this.setIsBatHanging(false);
					this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, blockpos, 0);
				}
			}
		} else {
			if (this.spawnPosition != null
					&& (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
				this.spawnPosition = null;
			}

			if (this.spawnPosition == null || this.rand.nextInt(30) == 0
					|| this.spawnPosition.distanceSq((double) ((int) this.posX), (double) ((int) this.posY),
							(double) ((int) this.posZ)) < 4.0D) {
				this.spawnPosition = new BlockPos((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
						(int) this.posY + this.rand.nextInt(6) - 2,
						(int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
			}

			double d0 = (double) this.spawnPosition.getX() + 0.5D - this.posX;
			double d1 = (double) this.spawnPosition.getY() + 0.1D - this.posY;
			double d2 = (double) this.spawnPosition.getZ() + 0.5D - this.posZ;
			this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
			this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
			this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
			float f = (float) (MathHelper.func_181159_b(this.motionZ, this.motionX) * 180.0D / 3.1415927410125732D)
					- 90.0F;
			float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
			this.moveForward = 0.5F;
			this.rotationYaw += f1;
			if (this.rand.nextInt(100) == 0 && this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube()) {
				this.setIsBatHanging(true);
			}
		}

	}

	/**+
	 * returns if this entity triggers Block.onEntityWalking on the
	 * blocks they walk on. used for spiders and wolves to prevent
	 * them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}

	public void fall(float var1, float var2) {
	}

	protected void updateFallState(double var1, boolean var3, Block var4, BlockPos var5) {
	}

	/**+
	 * Return whether this entity should NOT trigger a pressure
	 * plate or a tripwire.
	 */
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		this.dataWatcher.updateObject(16, Byte.valueOf(nbttagcompound.getByte("BatFlags")));
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
	}

	/**+
	 * Checks if the entity's current position is a valid location
	 * to spawn this entity.
	 */
	public boolean getCanSpawnHere() {
		BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
		if (blockpos.getY() >= this.worldObj.func_181545_F()) {
			return false;
		} else {
			int i = this.worldObj.getLightFromNeighbors(blockpos);
			byte b0 = 4;
			if (this.isDateAroundHalloween(this.worldObj.getCurrentDate())) {
				b0 = 7;
			} else if (this.rand.nextBoolean()) {
				return false;
			}

			return i > this.rand.nextInt(b0) ? false : super.getCanSpawnHere();
		}
	}

	private boolean isDateAroundHalloween(Calendar parCalendar) {
		return parCalendar.get(2) + 1 == 10 && parCalendar.get(5) >= 20
				|| parCalendar.get(2) + 1 == 11 && parCalendar.get(5) <= 3;
	}

	public float getEyeHeight() {
		return this.height / 2.0F;
	}
}