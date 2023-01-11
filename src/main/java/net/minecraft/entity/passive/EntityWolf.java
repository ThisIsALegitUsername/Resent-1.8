package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
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
public class EntityWolf extends EntityTameable {
	private float headRotationCourse;
	private float headRotationCourseOld;
	private boolean isWet;
	private boolean isShaking;
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;

	public EntityWolf(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.8F);
		this.setTamed(false);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
		if (this.isTamed()) {
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		}

		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
	}

	/**+
	 * Sets the active target the Task system uses for tracking
	 */
	public void setAttackTarget(EntityLivingBase entitylivingbase) {
		super.setAttackTarget(entitylivingbase);
		if (entitylivingbase == null) {
			this.setAngry(false);
		} else if (!this.isTamed()) {
			this.setAngry(true);
		}

	}

	protected void updateAITasks() {
		this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(18, new Float(this.getHealth()));
		this.dataWatcher.addObject(19, new Byte((byte) 0));
		this.dataWatcher.addObject(20, new Byte((byte) EnumDyeColor.RED.getMetadata()));
	}

	protected void playStepSound(BlockPos var1, Block var2) {
		this.playSound("mob.wolf.step", 0.15F, 1.0F);
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Angry", this.isAngry());
		nbttagcompound.setByte("CollarColor", (byte) this.getCollarColor().getDyeDamage());
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		this.setAngry(nbttagcompound.getBoolean("Angry"));
		if (nbttagcompound.hasKey("CollarColor", 99)) {
			this.setCollarColor(EnumDyeColor.byDyeDamage(nbttagcompound.getByte("CollarColor")));
		}

	}

	/**+
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return this.isAngry() ? "mob.wolf.growl"
				: (this.rand.nextInt(3) == 0
						? (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine"
								: "mob.wolf.panting")
						: "mob.wolf.bark");
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.wolf.hurt";
	}

	/**+
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.wolf.death";
	}

	/**+
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.4F;
	}

	protected Item getDropItem() {
		return Item.getItemById(-1);
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		super.onUpdate();
		this.headRotationCourseOld = this.headRotationCourse;
		if (this.isBegging()) {
			this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
		} else {
			this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
		}

		if (this.isWet()) {
			this.isWet = true;
			this.isShaking = false;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		} else if ((this.isWet || this.isShaking) && this.isShaking) {
			if (this.timeWolfIsShaking == 0.0F) {
				this.playSound("mob.wolf.shake", this.getSoundVolume(),
						(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
			this.timeWolfIsShaking += 0.05F;
			if (this.prevTimeWolfIsShaking >= 2.0F) {
				this.isWet = false;
				this.isShaking = false;
				this.prevTimeWolfIsShaking = 0.0F;
				this.timeWolfIsShaking = 0.0F;
			}

			if (this.timeWolfIsShaking > 0.4F) {
				float f = (float) this.getEntityBoundingBox().minY;
				int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);

				for (int j = 0; j < i; ++j) {
					float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (double) f1,
							(double) (f + 0.8F), this.posZ + (double) f2, this.motionX, this.motionY, this.motionZ,
							new int[0]);
				}
			}
		}

	}

	/**+
	 * True if the wolf is wet
	 */
	public boolean isWolfWet() {
		return this.isWet;
	}

	/**+
	 * Used when calculating the amount of shading to apply while
	 * the wolf is wet.
	 */
	public float getShadingWhileWet(float parFloat1) {
		return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * parFloat1)
				/ 2.0F * 0.25F;
	}

	public float getShakeAngle(float parFloat1, float parFloat2) {
		float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * parFloat1
				+ parFloat2) / 1.8F;
		if (f < 0.0F) {
			f = 0.0F;
		} else if (f > 1.0F) {
			f = 1.0F;
		}

		return MathHelper.sin(f * 3.1415927F) * MathHelper.sin(f * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
	}

	public float getInterestedAngle(float parFloat1) {
		return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * parFloat1) * 0.15F
				* 3.1415927F;
	}

	public float getEyeHeight() {
		return this.height * 0.8F;
	}

	/**+
	 * The speed it takes to move the entityliving's rotationPitch
	 * through the faceEntity method. This is only currently use in
	 * wolves.
	 */
	public int getVerticalFaceSpeed() {
		return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	/**+
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (this.isEntityInvulnerable(damagesource)) {
			return false;
		} else {
			Entity entity = damagesource.getEntity();
			if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
				f = (f + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(damagesource, f);
		}
	}

	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this),
				(float) ((int) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
		}

		return flag;
	}

	public void setTamed(boolean flag) {
		super.setTamed(flag);
		if (flag) {
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		}

		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
	}

	/**+
	 * Called when a player interacts with a mob. e.g. gets milk
	 * from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (this.isTamed()) {
			if (itemstack != null) {
				if (itemstack.getItem() instanceof ItemFood) {
					ItemFood itemfood = (ItemFood) itemstack.getItem();
					if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
						if (!entityplayer.capabilities.isCreativeMode) {
							--itemstack.stackSize;
						}

						this.heal((float) itemfood.getHealAmount(itemstack));
						if (itemstack.stackSize <= 0) {
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,
									(ItemStack) null);
						}

						return true;
					}
				} else if (itemstack.getItem() == Items.dye) {
					EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());
					if (enumdyecolor != this.getCollarColor()) {
						this.setCollarColor(enumdyecolor);
						if (!entityplayer.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,
									(ItemStack) null);
						}

						return true;
					}
				}
			}
		} else if (itemstack != null && itemstack.getItem() == Items.bone && !this.isAngry()) {
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}

			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack) null);
			}

			return true;
		}

		return super.interact(entityplayer);
	}

	public void handleStatusUpdate(byte b0) {
		if (b0 == 8) {
			this.isShaking = true;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		} else {
			super.handleStatusUpdate(b0);
		}

	}

	public float getTailRotation() {
		return this.isAngry() ? 1.5393804F
				: (this.isTamed()
						? (0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * 3.1415927F
						: 0.62831855F);
	}

	/**+
	 * Checks if the parameter is an item which this animal can be
	 * fed to breed it (wheat, carrots or seeds depending on the
	 * animal type)
	 */
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack == null ? false
				: (!(itemstack.getItem() instanceof ItemFood) ? false
						: ((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat());
	}

	/**+
	 * Will return how many at most can spawn in a chunk at once.
	 */
	public int getMaxSpawnedInChunk() {
		return 8;
	}

	/**+
	 * Determines whether this wolf is angry or not.
	 */
	public boolean isAngry() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	/**+
	 * Sets whether this wolf is angry or not.
	 */
	public void setAngry(boolean angry) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		if (angry) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
		}

	}

	public EnumDyeColor getCollarColor() {
		return EnumDyeColor.byDyeDamage(this.dataWatcher.getWatchableObjectByte(20) & 15);
	}

	public void setCollarColor(EnumDyeColor collarcolor) {
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) (collarcolor.getDyeDamage() & 15)));
	}

	public EntityWolf createChild(EntityAgeable var1) {
		EntityWolf entitywolf = new EntityWolf(this.worldObj);
		String s = this.getOwnerId();
		if (s != null && s.trim().length() > 0) {
			entitywolf.setOwnerId(s);
			entitywolf.setTamed(true);
		}

		return entitywolf;
	}

	public void setBegging(boolean beg) {
		if (beg) {
			this.dataWatcher.updateObject(19, Byte.valueOf((byte) 1));
		} else {
			this.dataWatcher.updateObject(19, Byte.valueOf((byte) 0));
		}

	}

	/**+
	 * Returns true if the mob is currently able to mate with the
	 * specified mob.
	 */
	public boolean canMateWith(EntityAnimal entityanimal) {
		if (entityanimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(entityanimal instanceof EntityWolf)) {
			return false;
		} else {
			EntityWolf entitywolf = (EntityWolf) entityanimal;
			return !entitywolf.isTamed() ? false
					: (entitywolf.isSitting() ? false : this.isInLove() && entitywolf.isInLove());
		}
	}

	public boolean isBegging() {
		return this.dataWatcher.getWatchableObjectByte(19) == 1;
	}

	/**+
	 * Determines if an entity can be despawned, used on idle far
	 * away entities
	 */
	protected boolean canDespawn() {
		return !this.isTamed() && this.ticksExisted > 2400;
	}

	public boolean shouldAttackEntity(EntityLivingBase entitylivingbase, EntityLivingBase entitylivingbase1) {
		if (!(entitylivingbase instanceof EntityCreeper) && !(entitylivingbase instanceof EntityGhast)) {
			if (entitylivingbase instanceof EntityWolf) {
				EntityWolf entitywolf = (EntityWolf) entitylivingbase;
				if (entitywolf.isTamed() && entitywolf.getOwner() == entitylivingbase1) {
					return false;
				}
			}

			return entitylivingbase instanceof EntityPlayer && entitylivingbase1 instanceof EntityPlayer
					&& !((EntityPlayer) entitylivingbase1).canAttackPlayer((EntityPlayer) entitylivingbase) ? false
							: !(entitylivingbase instanceof EntityHorse) || !((EntityHorse) entitylivingbase).isTame();
		} else {
			return false;
		}
	}

	public boolean allowLeashing() {
		return !this.isAngry() && super.allowLeashing();
	}
}