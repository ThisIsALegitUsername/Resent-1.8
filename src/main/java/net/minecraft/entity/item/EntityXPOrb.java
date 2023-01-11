package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
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
public class EntityXPOrb extends Entity {
	public int xpColor;
	public int xpOrbAge;
	public int delayBeforeCanPickup;
	/**+
	 * The health of this XP orb.
	 */
	private int xpOrbHealth = 5;
	private int xpValue;
	private EntityPlayer closestPlayer;
	private int xpTargetColor;

	public EntityXPOrb(World worldIn, double x, double y, double z, int expValue) {
		super(worldIn);
		this.setSize(0.5F, 0.5F);
		this.setPosition(x, y, z);
		this.rotationYaw = (float) (Math.random() * 360.0D);
		this.motionX = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
		this.motionY = (double) ((float) (Math.random() * 0.2D) * 2.0F);
		this.motionZ = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
		this.xpValue = expValue;
	}

	/**+
	 * returns if this entity triggers Block.onEntityWalking on the
	 * blocks they walk on. used for spiders and wolves to prevent
	 * them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}

	public EntityXPOrb(World worldIn) {
		super(worldIn);
		this.setSize(0.25F, 0.25F);
	}

	protected void entityInit() {
	}

	public int getBrightnessForRender(float f) {
		float f1 = 0.5F;
		f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
		int i = super.getBrightnessForRender(f);
		int j = i & 255;
		int k = i >> 16 & 255;
		j = j + (int) (f1 * 15.0F * 16.0F);
		if (j > 240) {
			j = 240;
		}

		return j | k << 16;
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		super.onUpdate();
		if (this.delayBeforeCanPickup > 0) {
			--this.delayBeforeCanPickup;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.029999999329447746D;
		if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
			this.motionY = 0.20000000298023224D;
			this.motionX = (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			this.motionZ = (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
		}

		this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D,
				this.posZ);
		double d0 = 8.0D;
		if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
			if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > d0 * d0) {
				this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, d0);
			}

			this.xpTargetColor = this.xpColor;
		}

		if (this.closestPlayer != null && this.closestPlayer.isSpectator()) {
			this.closestPlayer = null;
		}

		if (this.closestPlayer != null) {
			double d1 = (this.closestPlayer.posX - this.posX) / d0;
			double d2 = (this.closestPlayer.posY + (double) this.closestPlayer.getEyeHeight() - this.posY) / d0;
			double d3 = (this.closestPlayer.posZ - this.posZ) / d0;
			double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
			double d5 = 1.0D - d4;
			if (d5 > 0.0D) {
				d5 = d5 * d5;
				this.motionX += d1 / d4 * d5 * 0.1D;
				this.motionY += d2 / d4 * d5 * 0.1D;
				this.motionZ += d3 / d4 * d5 * 0.1D;
			}
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		float f = 0.98F;
		if (this.onGround) {
			f = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX),
					MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ)))
					.getBlock().slipperiness * 0.98F;
		}

		this.motionX *= (double) f;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= (double) f;
		if (this.onGround) {
			this.motionY *= -0.8999999761581421D;
		}

		++this.xpColor;
		++this.xpOrbAge;
		if (this.xpOrbAge >= 6000) {
			this.setDead();
		}

	}

	/**+
	 * Returns if this entity is in water and will end up adding the
	 * waters velocity to the entity
	 */
	public boolean handleWaterMovement() {
		return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this);
	}

	/**+
	 * Will deal the specified amount of damage to the entity if the
	 * entity isn't immune to fire damage. Args: amountDamage
	 */
	protected void dealFireDamage(int i) {
		this.attackEntityFrom(DamageSource.inFire, (float) i);
	}

	/**+
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (this.isEntityInvulnerable(damagesource)) {
			return false;
		} else {
			this.setBeenAttacked();
			this.xpOrbHealth = (int) ((float) this.xpOrbHealth - f);
			if (this.xpOrbHealth <= 0) {
				this.setDead();
			}

			return false;
		}
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setShort("Health", (short) ((byte) this.xpOrbHealth));
		nbttagcompound.setShort("Age", (short) this.xpOrbAge);
		nbttagcompound.setShort("Value", (short) this.xpValue);
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.xpOrbHealth = nbttagcompound.getShort("Health") & 255;
		this.xpOrbAge = nbttagcompound.getShort("Age");
		this.xpValue = nbttagcompound.getShort("Value");
	}

	/**+
	 * Returns the XP value of this XP orb.
	 */
	public int getXpValue() {
		return this.xpValue;
	}

	/**+
	 * Returns a number from 1 to 10 based on how much XP this orb
	 * is worth. This is used by RenderXPOrb to determine what
	 * texture to use.
	 */
	public int getTextureByXP() {
		return this.xpValue >= 2477 ? 10
				: (this.xpValue >= 1237 ? 9
						: (this.xpValue >= 617 ? 8
								: (this.xpValue >= 307 ? 7
										: (this.xpValue >= 149 ? 6
												: (this.xpValue >= 73 ? 5
														: (this.xpValue >= 37 ? 4
																: (this.xpValue >= 17 ? 3
																		: (this.xpValue >= 7 ? 2
																				: (this.xpValue >= 3 ? 1 : 0)))))))));
	}

	/**+
	 * Get a fragment of the maximum experience points value for the
	 * supplied value of experience points value.
	 */
	public static int getXPSplit(int expValue) {
		return expValue >= 2477 ? 2477
				: (expValue >= 1237 ? 1237
						: (expValue >= 617 ? 617
								: (expValue >= 307 ? 307
										: (expValue >= 149 ? 149
												: (expValue >= 73 ? 73
														: (expValue >= 37 ? 37
																: (expValue >= 17 ? 17
																		: (expValue >= 7 ? 7
																				: (expValue >= 3 ? 3 : 1)))))))));
	}

	/**+
	 * If returns false, the item will not inflict any damage
	 * against entities.
	 */
	public boolean canAttackWithItem() {
		return false;
	}
}