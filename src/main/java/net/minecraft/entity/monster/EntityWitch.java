package net.minecraft.entity.monster;

import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
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
public class EntityWitch extends EntityMob implements IRangedAttackMob {
	private static final EaglercraftUUID MODIFIER_UUID = EaglercraftUUID
			.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty",
			-0.25D, 0)).setSaved(false);
	/**+
	 * List of items a witch should drop on death.
	 */
	private static final Item[] witchDrops = new Item[] { Items.glowstone_dust, Items.sugar, Items.redstone,
			Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
	private int witchAttackTimer;

	public EntityWitch(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.95F);
	}

	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(21, Byte.valueOf((byte) 0));
	}

	/**+
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return null;
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return null;
	}

	/**+
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return null;
	}

	/**+
	 * Set whether this witch is aggressive at an entity.
	 */
	public void setAggressive(boolean aggressive) {
		this.getDataWatcher().updateObject(21, Byte.valueOf((byte) (aggressive ? 1 : 0)));
	}

	/**+
	 * Return whether this witch is aggressive at an entity.
	 */
	public boolean getAggressive() {
		return this.getDataWatcher().getWatchableObjectByte(21) == 1;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	public void handleStatusUpdate(byte b0) {
		if (b0 == 15) {
			for (int i = 0; i < this.rand.nextInt(35) + 10; ++i) {
				this.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH,
						this.posX + this.rand.nextGaussian() * 0.12999999523162842D,
						this.getEntityBoundingBox().maxY + 0.5D + this.rand.nextGaussian() * 0.12999999523162842D,
						this.posZ + this.rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		} else {
			super.handleStatusUpdate(b0);
		}

	}

	/**+
	 * Reduces damage, depending on potions
	 */
	protected float applyPotionDamageCalculations(DamageSource damagesource, float f) {
		f = super.applyPotionDamageCalculations(damagesource, f);
		if (damagesource.getEntity() == this) {
			f = 0.0F;
		}

		if (damagesource.isMagicDamage()) {
			f = (float) ((double) f * 0.15D);
		}

		return f;
	}

	/**+
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int i) {
		int j = this.rand.nextInt(3) + 1;

		for (int k = 0; k < j; ++k) {
			int l = this.rand.nextInt(3);
			Item item = witchDrops[this.rand.nextInt(witchDrops.length)];
			if (i > 0) {
				l += this.rand.nextInt(i + 1);
			}

			for (int i1 = 0; i1 < l; ++i1) {
				this.dropItem(item, 1);
			}
		}

	}

	/**+
	 * Attack the specified entity using a ranged attack.
	 */
	public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float var2) {
		if (!this.getAggressive()) {
			EntityPotion entitypotion = new EntityPotion(this.worldObj, this, 32732);
			double d0 = entitylivingbase.posY + (double) entitylivingbase.getEyeHeight() - 1.100000023841858D;
			entitypotion.rotationPitch -= -20.0F;
			double d1 = entitylivingbase.posX + entitylivingbase.motionX - this.posX;
			double d2 = d0 - this.posY;
			double d3 = entitylivingbase.posZ + entitylivingbase.motionZ - this.posZ;
			float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
			if (f >= 8.0F && !entitylivingbase.isPotionActive(Potion.moveSlowdown)) {
				entitypotion.setPotionDamage(32698);
			} else if (entitylivingbase.getHealth() >= 8.0F && !entitylivingbase.isPotionActive(Potion.poison)) {
				entitypotion.setPotionDamage(32660);
			} else if (f <= 3.0F && !entitylivingbase.isPotionActive(Potion.weakness)
					&& this.rand.nextFloat() < 0.25F) {
				entitypotion.setPotionDamage(32696);
			}

			entitypotion.setThrowableHeading(d1, d2 + (double) (f * 0.2F), d3, 0.75F, 8.0F);
			this.worldObj.spawnEntityInWorld(entitypotion);
		}
	}

	public float getEyeHeight() {
		return 1.62F;
	}
}