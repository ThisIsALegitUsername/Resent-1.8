package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
public class EntityIronGolem extends EntityGolem {
	private int homeCheckTimer;
	private int attackTimer;
	private int holdRoseTick;

	public EntityIronGolem(World worldIn) {
		super(worldIn);
		this.setSize(1.4F, 2.9F);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**+
	 * Decrements the entity's air supply when underwater
	 */
	protected int decreaseAirSupply(int i) {
		return i;
	}

	protected void collideWithEntity(Entity entity) {
		if (entity instanceof IMob && !(entity instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
			this.setAttackTarget((EntityLivingBase) entity);
		}

		super.collideWithEntity(entity);
	}

	/**+
	 * Called frequently so the entity can update its state every
	 * tick as required. For example, zombies and skeletons use this
	 * to react to sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.attackTimer > 0) {
			--this.attackTimer;
		}

		if (this.holdRoseTick > 0) {
			--this.holdRoseTick;
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D
				&& this.rand.nextInt(5) == 0) {
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int k = MathHelper.floor_double(this.posZ);
			IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
			Block block = iblockstate.getBlock();
			if (block.getMaterial() != Material.air) {
				this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
						this.posX + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width,
						this.getEntityBoundingBox().minY + 0.1D,
						this.posZ + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width,
						4.0D * ((double) this.rand.nextFloat() - 0.5D), 0.5D,
						((double) this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockstate) });
			}
		}

	}

	/**+
	 * Returns true if this entity can attack entities of the
	 * specified class.
	 */
	public boolean canAttackClass(Class<? extends EntityLivingBase> oclass) {
		return this.isPlayerCreated() && EntityPlayer.class.isAssignableFrom(oclass) ? false
				: (oclass == EntityCreeper.class ? false : super.canAttackClass(oclass));
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("PlayerCreated", this.isPlayerCreated());
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		this.setPlayerCreated(nbttagcompound.getBoolean("PlayerCreated"));
	}

	public boolean attackEntityAsMob(Entity entity) {
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte) 4);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (7 + this.rand.nextInt(15)));
		if (flag) {
			entity.motionY += 0.4000000059604645D;
			this.applyEnchantments(this, entity);
		}

		this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		return flag;
	}

	public void handleStatusUpdate(byte b0) {
		if (b0 == 4) {
			this.attackTimer = 10;
			this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		} else if (b0 == 11) {
			this.holdRoseTick = 400;
		} else {
			super.handleStatusUpdate(b0);
		}

	}

	public int getAttackTimer() {
		return this.attackTimer;
	}

	public void setHoldingRose(boolean parFlag) {
		this.holdRoseTick = parFlag ? 400 : 0;
		this.worldObj.setEntityState(this, (byte) 11);
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.irongolem.hit";
	}

	/**+
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.irongolem.death";
	}

	protected void playStepSound(BlockPos var1, Block var2) {
		this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
	}

	/**+
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int var2) {
		int i = this.rand.nextInt(3);

		for (int j = 0; j < i; ++j) {
			this.dropItemWithOffset(Item.getItemFromBlock(Blocks.red_flower), 1,
					(float) BlockFlower.EnumFlowerType.POPPY.getMeta());
		}

		int l = 3 + this.rand.nextInt(3);

		for (int k = 0; k < l; ++k) {
			this.dropItem(Items.iron_ingot, 1);
		}

	}

	public int getHoldRoseTick() {
		return this.holdRoseTick;
	}

	public boolean isPlayerCreated() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setPlayerCreated(boolean parFlag) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		if (parFlag) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 1)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -2)));
		}

	}
}