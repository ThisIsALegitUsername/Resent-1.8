package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
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
public class EntityCreeper extends EntityMob {
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;
	/**+
	 * Explosion radius for this creeper.
	 */
	private int explosionRadius = 3;
	private int field_175494_bm = 0;

	public EntityCreeper(World worldIn) {
		super(worldIn);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**+
	 * The maximum height from where the entity is alowed to jump
	 * (used in pathfinder)
	 */
	public int getMaxFallHeight() {
		return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
	}

	public void fall(float f, float f1) {
		super.fall(f, f1);
		this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + f * 1.5F);
		if (this.timeSinceIgnited > this.fuseTime - 5) {
			this.timeSinceIgnited = this.fuseTime - 5;
		}

	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) -1));
		this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(18, Byte.valueOf((byte) 0));
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
			nbttagcompound.setBoolean("powered", true);
		}

		nbttagcompound.setShort("Fuse", (short) this.fuseTime);
		nbttagcompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
		nbttagcompound.setBoolean("ignited", this.hasIgnited());
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte) (nbttagcompound.getBoolean("powered") ? 1 : 0)));
		if (nbttagcompound.hasKey("Fuse", 99)) {
			this.fuseTime = nbttagcompound.getShort("Fuse");
		}

		if (nbttagcompound.hasKey("ExplosionRadius", 99)) {
			this.explosionRadius = nbttagcompound.getByte("ExplosionRadius");
		}

		if (nbttagcompound.getBoolean("ignited")) {
			this.ignite();
		}

	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		if (this.isEntityAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			if (this.hasIgnited()) {
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound("creeper.primed", 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;
			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
			}
		}

		super.onUpdate();
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.creeper.say";
	}

	/**+
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.creeper.death";
	}

	/**+
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (damagesource.getEntity() instanceof EntitySkeleton) {
			int i = Item.getIdFromItem(Items.record_13);
			int j = Item.getIdFromItem(Items.record_wait);
			int k = i + this.rand.nextInt(j - i + 1);
			this.dropItem(Item.getItemById(k), 1);
		} else if (damagesource.getEntity() instanceof EntityCreeper && damagesource.getEntity() != this
				&& ((EntityCreeper) damagesource.getEntity()).getPowered()
				&& ((EntityCreeper) damagesource.getEntity()).isAIEnabled()) {
			((EntityCreeper) damagesource.getEntity()).func_175493_co();
			this.entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
		}

	}

	public boolean attackEntityAsMob(Entity var1) {
		return true;
	}

	/**+
	 * Returns true if the creeper is powered by a lightning bolt.
	 */
	public boolean getPowered() {
		return this.dataWatcher.getWatchableObjectByte(17) == 1;
	}

	/**+
	 * Params: (Float)Render tick. Returns the intensity of the
	 * creeper's flash when it is ignited.
	 */
	public float getCreeperFlashIntensity(float parFloat1) {
		return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * parFloat1)
				/ (float) (this.fuseTime - 2);
	}

	protected Item getDropItem() {
		return Items.gunpowder;
	}

	/**+
	 * Returns the current state of creeper, -1 is idle, 1 is 'in
	 * fuse'
	 */
	public int getCreeperState() {
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	/**+
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int state) {
		this.dataWatcher.updateObject(16, Byte.valueOf((byte) state));
	}

	/**+
	 * Called when a lightning bolt hits the entity.
	 */
	public void onStruckByLightning(EntityLightningBolt entitylightningbolt) {
		super.onStruckByLightning(entitylightningbolt);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte) 1));
	}

	/**+
	 * Called when a player interacts with a mob. e.g. gets milk
	 * from a cow, gets into the saddle on a pig.
	 */
	protected boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F,
					this.rand.nextFloat() * 0.4F + 0.8F);
			entityplayer.swingItem();
		}

		return super.interact(entityplayer);
	}

	public boolean hasIgnited() {
		return this.dataWatcher.getWatchableObjectByte(18) != 0;
	}

	public void ignite() {
		this.dataWatcher.updateObject(18, Byte.valueOf((byte) 1));
	}

	/**+
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled() {
		return this.field_175494_bm < 1 && this.worldObj.getGameRules().getBoolean("doMobLoot");
	}

	public void func_175493_co() {
		++this.field_175494_bm;
	}
}