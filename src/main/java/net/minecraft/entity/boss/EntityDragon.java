package net.minecraft.entity.boss;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
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
public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob {
	public double targetX;
	public double targetY;
	public double targetZ;
	/**+
	 * Ring buffer array for the last 64 Y-positions and yaw
	 * rotations. Used to calculate offsets for the animations.
	 */
	public double[][] ringBuffer = new double[64][3];
	/**+
	 * Index into the ring buffer. Incremented once per tick and
	 * restarts at 0 once it reaches the end of the buffer.
	 */
	public int ringBufferIndex = -1;
	public EntityDragonPart[] dragonPartArray;
	public EntityDragonPart dragonPartHead;
	public EntityDragonPart dragonPartBody;
	public EntityDragonPart dragonPartTail1;
	public EntityDragonPart dragonPartTail2;
	public EntityDragonPart dragonPartTail3;
	public EntityDragonPart dragonPartWing1;
	public EntityDragonPart dragonPartWing2;
	public float prevAnimTime;
	public float animTime;
	public boolean forceNewTarget;
	public boolean slowed;
	private Entity target;
	public int deathTicks;
	public EntityEnderCrystal healingEnderCrystal;

	public EntityDragon(World worldIn) {
		super(worldIn);
		this.dragonPartArray = new EntityDragonPart[] {
				this.dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F),
				this.dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F),
				this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
				this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
				this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
				this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F),
				this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F) };
		this.setHealth(this.getMaxHealth());
		this.setSize(16.0F, 8.0F);
		this.noClip = true;
		this.isImmuneToFire = true;
		this.targetY = 100.0D;
		this.ignoreFrustumCheck = true;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
	}

	protected void entityInit() {
		super.entityInit();
	}

	/**+
	 * Returns a double[3] array with movement offsets, used to
	 * calculate trailing tail/neck positions. [0] = yaw offset, [1]
	 * = y offset, [2] = unused, always 0. Parameters: buffer index
	 * offset, partial ticks.
	 */
	public double[] getMovementOffsets(int parInt1, float parFloat1) {
		if (this.getHealth() <= 0.0F) {
			parFloat1 = 0.0F;
		}

		parFloat1 = 1.0F - parFloat1;
		int i = this.ringBufferIndex - parInt1 * 1 & 63;
		int j = this.ringBufferIndex - parInt1 * 1 - 1 & 63;
		double[] adouble = new double[3];
		double d0 = this.ringBuffer[i][0];
		double d1 = MathHelper.wrapAngleTo180_double(this.ringBuffer[j][0] - d0);
		adouble[0] = d0 + d1 * (double) parFloat1;
		d0 = this.ringBuffer[i][1];
		d1 = this.ringBuffer[j][1] - d0;
		adouble[1] = d0 + d1 * (double) parFloat1;
		adouble[2] = this.ringBuffer[i][2] + (this.ringBuffer[j][2] - this.ringBuffer[i][2]) * (double) parFloat1;
		return adouble;
	}

	/**+
	 * Called frequently so the entity can update its state every
	 * tick as required. For example, zombies and skeletons use this
	 * to react to sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		float f = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
		float f1 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
		if (f1 <= -0.3F && f >= -0.3F && !this.isSilent()) {
			this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F,
					0.8F + this.rand.nextFloat() * 0.3F, false);
		}

		this.prevAnimTime = this.animTime;
		if (this.getHealth() <= 0.0F) {
			float f11 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			float f13 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			float f14 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (double) f11,
					this.posY + 2.0D + (double) f13, this.posZ + (double) f14, 0.0D, 0.0D, 0.0D, new int[0]);
		} else {
			this.updateDragonEnderCrystal();
			float f10 = 0.2F
					/ (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F
							+ 1.0F);
			f10 = f10 * (float) Math.pow(2.0D, this.motionY);
			if (this.slowed) {
				this.animTime += f10 * 0.5F;
			} else {
				this.animTime += f10;
			}

			this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
			if (this.isAIDisabled()) {
				this.animTime = 0.5F;
			} else {
				if (this.ringBufferIndex < 0) {
					for (int i = 0; i < this.ringBuffer.length; ++i) {
						this.ringBuffer[i][0] = (double) this.rotationYaw;
						this.ringBuffer[i][1] = this.posY;
					}
				}

				if (++this.ringBufferIndex == this.ringBuffer.length) {
					this.ringBufferIndex = 0;
				}

				this.ringBuffer[this.ringBufferIndex][0] = (double) this.rotationYaw;
				this.ringBuffer[this.ringBufferIndex][1] = this.posY;
				if (this.newPosRotationIncrements > 0) {
					double d10 = this.posX + (this.newPosX - this.posX) / (double) this.newPosRotationIncrements;
					double d0 = this.posY + (this.newPosY - this.posY) / (double) this.newPosRotationIncrements;
					double d1 = this.posZ + (this.newPosZ - this.posZ) / (double) this.newPosRotationIncrements;
					double d2 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double) this.rotationYaw);
					this.rotationYaw = (float) ((double) this.rotationYaw
							+ d2 / (double) this.newPosRotationIncrements);
					this.rotationPitch = (float) ((double) this.rotationPitch
							+ (this.newRotationPitch - (double) this.rotationPitch)
									/ (double) this.newPosRotationIncrements);
					--this.newPosRotationIncrements;
					this.setPosition(d10, d0, d1);
					this.setRotation(this.rotationYaw, this.rotationPitch);
				}

				this.renderYawOffset = this.rotationYaw;
				this.dragonPartHead.width = this.dragonPartHead.height = 3.0F;
				this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
				this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
				this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
				this.dragonPartBody.height = 3.0F;
				this.dragonPartBody.width = 5.0F;
				this.dragonPartWing1.height = 2.0F;
				this.dragonPartWing1.width = 4.0F;
				this.dragonPartWing2.height = 3.0F;
				this.dragonPartWing2.width = 4.0F;
				float f12 = (float) (this.getMovementOffsets(5, 1.0F)[1] - this.getMovementOffsets(10, 1.0F)[1]) * 10.0F
						/ 180.0F * 3.1415927F;
				float f2 = MathHelper.cos(f12);
				float f15 = -MathHelper.sin(f12);
				float f3 = this.rotationYaw * 3.1415927F / 180.0F;
				float f16 = MathHelper.sin(f3);
				float f4 = MathHelper.cos(f3);
				this.dragonPartBody.onUpdate();
				this.dragonPartBody.setLocationAndAngles(this.posX + (double) (f16 * 0.5F), this.posY,
						this.posZ - (double) (f4 * 0.5F), 0.0F, 0.0F);
				this.dragonPartWing1.onUpdate();
				this.dragonPartWing1.setLocationAndAngles(this.posX + (double) (f4 * 4.5F), this.posY + 2.0D,
						this.posZ + (double) (f16 * 4.5F), 0.0F, 0.0F);
				this.dragonPartWing2.onUpdate();
				this.dragonPartWing2.setLocationAndAngles(this.posX - (double) (f4 * 4.5F), this.posY + 2.0D,
						this.posZ - (double) (f16 * 4.5F), 0.0F, 0.0F);

				double[] adouble1 = this.getMovementOffsets(5, 1.0F);
				double[] adouble = this.getMovementOffsets(0, 1.0F);
				float f18 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
				float f19 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
				this.dragonPartHead.onUpdate();
				this.dragonPartHead.setLocationAndAngles(this.posX + (double) (f18 * 5.5F * f2),
						this.posY + (adouble[1] - adouble1[1]) * 1.0D + (double) (f15 * 5.5F),
						this.posZ - (double) (f19 * 5.5F * f2), 0.0F, 0.0F);

				for (int j = 0; j < 3; ++j) {
					EntityDragonPart entitydragonpart = null;
					if (j == 0) {
						entitydragonpart = this.dragonPartTail1;
					}

					if (j == 1) {
						entitydragonpart = this.dragonPartTail2;
					}

					if (j == 2) {
						entitydragonpart = this.dragonPartTail3;
					}

					double[] adouble2 = this.getMovementOffsets(12 + j * 2, 1.0F);
					float f20 = this.rotationYaw * 3.1415927F / 180.0F
							+ this.simplifyAngle(adouble2[0] - adouble1[0]) * 3.1415927F / 180.0F * 1.0F;
					float f21 = MathHelper.sin(f20);
					float f22 = MathHelper.cos(f20);
					float f23 = 1.5F;
					float f24 = (float) (j + 1) * 2.0F;
					entitydragonpart.onUpdate();
					entitydragonpart.setLocationAndAngles(this.posX - (double) ((f16 * f23 + f21 * f24) * f2),
							this.posY + (adouble2[1] - adouble1[1]) * 1.0D - (double) ((f24 + f23) * f15) + 1.5D,
							this.posZ + (double) ((f4 * f23 + f22 * f24) * f2), 0.0F, 0.0F);
				}

			}
		}
	}

	/**+
	 * Updates the state of the enderdragon's current endercrystal.
	 */
	private void updateDragonEnderCrystal() {
		if (this.healingEnderCrystal != null) {
			if (this.healingEnderCrystal.isDead) {
				this.healingEnderCrystal = null;
			} else if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
				this.setHealth(this.getHealth() + 1.0F);
			}
		}

		if (this.rand.nextInt(10) == 0) {
			float f = 32.0F;
			List list = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class,
					this.getEntityBoundingBox().expand((double) f, (double) f, (double) f));
			EntityEnderCrystal entityendercrystal = null;
			double d0 = Double.MAX_VALUE;

			for (EntityEnderCrystal entityendercrystal1 : (List<EntityEnderCrystal>) list) {
				double d1 = entityendercrystal1.getDistanceSqToEntity(this);
				if (d1 < d0) {
					d0 = d1;
					entityendercrystal = entityendercrystal1;
				}
			}

			this.healingEnderCrystal = entityendercrystal;
		}

	}

	/**+
	 * Simplifies the value of a number by adding/subtracting 180 to
	 * the point that the number is between -180 and 180.
	 */
	private float simplifyAngle(double parDouble1) {
		return (float) MathHelper.wrapAngleTo180_double(parDouble1);
	}

	public boolean attackEntityFromPart(EntityDragonPart entitydragonpart, DamageSource damagesource, float f) {
		if (entitydragonpart != this.dragonPartHead) {
			f = f / 4.0F + 1.0F;
		}

		float f1 = this.rotationYaw * 3.1415927F / 180.0F;
		float f2 = MathHelper.sin(f1);
		float f3 = MathHelper.cos(f1);
		this.targetX = this.posX + (double) (f2 * 5.0F) + (double) ((this.rand.nextFloat() - 0.5F) * 2.0F);
		this.targetY = this.posY + (double) (this.rand.nextFloat() * 3.0F) + 1.0D;
		this.targetZ = this.posZ - (double) (f3 * 5.0F) + (double) ((this.rand.nextFloat() - 0.5F) * 2.0F);
		this.target = null;
		if (damagesource.getEntity() instanceof EntityPlayer || damagesource.isExplosion()) {
			this.attackDragonFrom(damagesource, f);
		}

		return true;
	}

	/**+
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (damagesource instanceof EntityDamageSource && ((EntityDamageSource) damagesource).getIsThornsDamage()) {
			this.attackDragonFrom(damagesource, f);
		}

		return false;
	}

	/**+
	 * Provides a way to cause damage to an ender dragon.
	 */
	protected boolean attackDragonFrom(DamageSource source, float amount) {
		return super.attackEntityFrom(source, amount);
	}

	/**+
	 * Called by the /kill command.
	 */
	public void onKillCommand() {
		this.setDead();
	}

	/**+
	 * handles entity death timer, experience orb and particle
	 * creation
	 */
	protected void onDeathUpdate() {
		++this.deathTicks;
		if (this.deathTicks >= 180 && this.deathTicks <= 200) {
			float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
			float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double) f,
					this.posY + 2.0D + (double) f1, this.posZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
		}

		this.moveEntity(0.0D, 0.10000000149011612D, 0.0D);
		this.renderYawOffset = this.rotationYaw += 20.0F;

	}

	/**+
	 * Makes the entity despawn if requirements are reached
	 */
	protected void despawnEntity() {
	}

	/**+
	 * Return the Entity parts making up this Entity (currently only
	 * for dragons)
	 */
	public Entity[] getParts() {
		return this.dragonPartArray;
	}

	/**+
	 * Returns true if other Entities should be prevented from
	 * moving through this Entity.
	 */
	public boolean canBeCollidedWith() {
		return false;
	}

	public World getWorld() {
		return this.worldObj;
	}

	/**+
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return "mob.enderdragon.growl";
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.enderdragon.hit";
	}

	/**+
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 5.0F;
	}
}