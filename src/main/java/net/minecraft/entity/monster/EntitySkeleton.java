package net.minecraft.entity.monster;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
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
public class EntitySkeleton extends EntityMob implements IRangedAttackMob {

	public EntitySkeleton(World worldIn) {
		super(worldIn);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(13, new Byte((byte) 0));
	}

	/**+
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return "mob.skeleton.say";
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.skeleton.hurt";
	}

	/**+
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.skeleton.death";
	}

	protected void playStepSound(BlockPos var1, Block var2) {
		this.playSound("mob.skeleton.step", 0.15F, 1.0F);
	}

	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (this.getSkeletonType() == 1 && entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
			}

			return true;
		} else {
			return false;
		}
	}

	/**+
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	/**+
	 * Called frequently so the entity can update its state every
	 * tick as required. For example, zombies and skeletons use this
	 * to react to sunlight and start to burn.
	 */
	public void onLivingUpdate() {

		if (this.getSkeletonType() == 1) {
			this.setSize(0.72F, 2.535F);
		}

		super.onLivingUpdate();
	}

	/**+
	 * Handles updating while being ridden by an entity
	 */
	public void updateRidden() {
		super.updateRidden();
		if (this.ridingEntity instanceof EntityCreature) {
			EntityCreature entitycreature = (EntityCreature) this.ridingEntity;
			this.renderYawOffset = entitycreature.renderYawOffset;
		}

	}

	/**+
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (damagesource.getSourceOfDamage() instanceof EntityArrow
				&& damagesource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			double d0 = entityplayer.posX - this.posX;
			double d1 = entityplayer.posZ - this.posZ;
			if (d0 * d0 + d1 * d1 >= 2500.0D) {
				entityplayer.triggerAchievement(AchievementList.snipeSkeleton);
			}
		} else if (damagesource.getEntity() instanceof EntityCreeper
				&& ((EntityCreeper) damagesource.getEntity()).getPowered()
				&& ((EntityCreeper) damagesource.getEntity()).isAIEnabled()) {
			((EntityCreeper) damagesource.getEntity()).func_175493_co();
			this.entityDropItem(new ItemStack(Items.skull, 1, this.getSkeletonType() == 1 ? 1 : 0), 0.0F);
		}

	}

	protected Item getDropItem() {
		return Items.arrow;
	}

	/**+
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int i) {
		if (this.getSkeletonType() == 1) {
			int j = this.rand.nextInt(3 + i) - 1;

			for (int k = 0; k < j; ++k) {
				this.dropItem(Items.coal, 1);
			}
		} else {
			int l = this.rand.nextInt(3 + i);

			for (int j1 = 0; j1 < l; ++j1) {
				this.dropItem(Items.arrow, 1);
			}
		}

		int i1 = this.rand.nextInt(3 + i);

		for (int k1 = 0; k1 < i1; ++k1) {
			this.dropItem(Items.bone, 1);
		}

	}

	/**+
	 * Causes this Entity to drop a random item.
	 */
	protected void addRandomDrop() {
		if (this.getSkeletonType() == 1) {
			this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
		}

	}

	/**+
	 * Gives armor or weapon for entity based on given
	 * DifficultyInstance
	 */
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyinstance) {
		super.setEquipmentBasedOnDifficulty(difficultyinstance);
		this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
	}

	/**+
	 * Called only once on an entity when first time spawned, via
	 * egg, mob spawner, natural spawning etc, but not called when
	 * entity is reloaded from nbt. Mainly used for initializing
	 * attributes and inventory
	 */
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficultyinstance,
			IEntityLivingData ientitylivingdata) {
		ientitylivingdata = super.onInitialSpawn(difficultyinstance, ientitylivingdata);

		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficultyinstance.getClampedAdditionalDifficulty());
		if (this.getEquipmentInSlot(4) == null) {
			Calendar calendar = this.worldObj.getCurrentDate();
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
				this.setCurrentItemOrArmor(4,
						new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				this.equipmentDropChances[4] = 0.0F;
			}
		}

		return ientitylivingdata;
	}

	/**+
	 * sets this entity's combat AI.
	 */
	public void setCombatTask() {
	}

	/**+
	 * Attack the specified entity using a ranged attack.
	 */
	public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f) {
		EntityArrow entityarrow = new EntityArrow(this.worldObj, this, entitylivingbase, 1.6F,
				(float) (14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
		int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
		entityarrow.setDamage((double) (f * 2.0F) + this.rand.nextGaussian() * 0.25D
				+ (double) ((float) this.worldObj.getDifficulty().getDifficultyId() * 0.11F));
		if (i > 0) {
			entityarrow.setDamage(entityarrow.getDamage() + (double) i * 0.5D + 0.5D);
		}

		if (j > 0) {
			entityarrow.setKnockbackStrength(j);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0
				|| this.getSkeletonType() == 1) {
			entityarrow.setFire(100);
		}

		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(entityarrow);
	}

	/**+
	 * Return this skeleton's type.
	 */
	public int getSkeletonType() {
		return this.dataWatcher.getWatchableObjectByte(13);
	}

	/**+
	 * Set this skeleton's type.
	 */
	public void setSkeletonType(int parInt1) {
		this.dataWatcher.updateObject(13, Byte.valueOf((byte) parInt1));
		this.isImmuneToFire = parInt1 == 1;
		if (parInt1 == 1) {
			this.setSize(0.72F, 2.535F);
		} else {
			this.setSize(0.6F, 1.95F);
		}

	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		if (nbttagcompound.hasKey("SkeletonType", 99)) {
			byte b0 = nbttagcompound.getByte("SkeletonType");
			this.setSkeletonType(b0);
		}

		this.setCombatTask();
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setByte("SkeletonType", (byte) this.getSkeletonType());
	}

	public float getEyeHeight() {
		return this.getSkeletonType() == 1 ? super.getEyeHeight() : 1.74F;
	}

	/**+
	 * Returns the Y Offset of this entity.
	 */
	public double getYOffset() {
		return this.isChild() ? 0.0D : -0.35D;
	}
}