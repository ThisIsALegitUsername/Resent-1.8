package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
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
public class EntityEnderEye extends Entity {
	private double targetX;
	private double targetY;
	private double targetZ;
	private int despawnTimer;
	private boolean shatterOrDrop;

	public EntityEnderEye(World worldIn) {
		super(worldIn);
		this.setSize(0.25F, 0.25F);
	}

	protected void entityInit() {
	}

	/**+
	 * Checks if the entity is in range to render by using the past
	 * in distance and comparing it to its average edge length * 64
	 * * renderDistanceWeight Args: distance
	 */
	public boolean isInRangeToRenderDist(double d0) {
		double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
		if (Double.isNaN(d1)) {
			d1 = 4.0D;
		}

		d1 = d1 * 64.0D;
		return d0 < d1 * d1;
	}

	public EntityEnderEye(World worldIn, double x, double y, double z) {
		super(worldIn);
		this.despawnTimer = 0;
		this.setSize(0.25F, 0.25F);
		this.setPosition(x, y, z);
	}

	public void moveTowards(BlockPos parBlockPos) {
		double d0 = (double) parBlockPos.getX();
		int i = parBlockPos.getY();
		double d1 = (double) parBlockPos.getZ();
		double d2 = d0 - this.posX;
		double d3 = d1 - this.posZ;
		float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);
		if (f > 12.0F) {
			this.targetX = this.posX + d2 / (double) f * 12.0D;
			this.targetZ = this.posZ + d3 / (double) f * 12.0D;
			this.targetY = this.posY + 8.0D;
		} else {
			this.targetX = d0;
			this.targetY = (double) i;
			this.targetZ = d1;
		}

		this.despawnTimer = 0;
		this.shatterOrDrop = this.rand.nextInt(5) > 0;
	}

	/**+
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double d0, double d1, double d2) {
		this.motionX = d0;
		this.motionY = d1;
		this.motionZ = d2;
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
			this.prevRotationYaw = this.rotationYaw = (float) (MathHelper.func_181159_b(d0, d2) * 180.0D
					/ 3.1415927410125732D);
			this.prevRotationPitch = this.rotationPitch = (float) (MathHelper.func_181159_b(d1, (double) f) * 180.0D
					/ 3.1415927410125732D);
		}

	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0D
				/ 3.1415927410125732D);

		for (this.rotationPitch = (float) (MathHelper.func_181159_b(this.motionY, (double) f) * 180.0D
				/ 3.1415927410125732D); this.rotationPitch
						- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

		float f3 = 0.25F;
		if (this.isInWater()) {
			for (int i = 0; i < 4; ++i) {
				this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f3,
						this.posY - this.motionY * (double) f3, this.posZ - this.motionZ * (double) f3, this.motionX,
						this.motionY, this.motionZ, new int[0]);
			}
		} else {
			this.worldObj.spawnParticle(EnumParticleTypes.PORTAL,
					this.posX - this.motionX * (double) f3 + this.rand.nextDouble() * 0.6D - 0.3D,
					this.posY - this.motionY * (double) f3 - 0.5D,
					this.posZ - this.motionZ * (double) f3 + this.rand.nextDouble() * 0.6D - 0.3D, this.motionX,
					this.motionY, this.motionZ, new int[0]);
		}

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
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float var1) {
		return 1.0F;
	}

	public int getBrightnessForRender(float var1) {
		return 15728880;
	}

	/**+
	 * If returns false, the item will not inflict any damage
	 * against entities.
	 */
	public boolean canAttackWithItem() {
		return false;
	}
}