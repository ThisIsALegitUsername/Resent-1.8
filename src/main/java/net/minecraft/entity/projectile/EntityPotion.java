package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
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
public class EntityPotion extends EntityThrowable {
	private ItemStack potionDamage;

	public EntityPotion(World worldIn) {
		super(worldIn);
	}

	public EntityPotion(World worldIn, EntityLivingBase throwerIn, int meta) {
		this(worldIn, throwerIn, new ItemStack(Items.potionitem, 1, meta));
	}

	public EntityPotion(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
		super(worldIn, throwerIn);
		this.potionDamage = potionDamageIn;
	}

	public EntityPotion(World worldIn, double x, double y, double z, int parInt1) {
		this(worldIn, x, y, z, new ItemStack(Items.potionitem, 1, parInt1));
	}

	public EntityPotion(World worldIn, double x, double y, double z, ItemStack potionDamageIn) {
		super(worldIn, x, y, z);
		this.potionDamage = potionDamageIn;
	}

	/**+
	 * Gets the amount of gravity to apply to the thrown entity with
	 * each tick.
	 */
	protected float getGravityVelocity() {
		return 0.05F;
	}

	protected float getVelocity() {
		return 0.5F;
	}

	protected float getInaccuracy() {
		return -20.0F;
	}

	/**+
	 * Sets the PotionEffect by the given id of the potion effect.
	 */
	public void setPotionDamage(int potionId) {
		if (this.potionDamage == null) {
			this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
		}

		this.potionDamage.setItemDamage(potionId);
	}

	/**+
	 * Returns the damage value of the thrown potion that this
	 * EntityPotion represents.
	 */
	public int getPotionDamage() {
		if (this.potionDamage == null) {
			this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
		}

		return this.potionDamage.getMetadata();
	}

	/**+
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition movingobjectposition) {

	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		if (nbttagcompound.hasKey("Potion", 10)) {
			this.potionDamage = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("Potion"));
		} else {
			this.setPotionDamage(nbttagcompound.getInteger("potionValue"));
		}

		if (this.potionDamage == null) {
			this.setDead();
		}

	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		if (this.potionDamage != null) {
			nbttagcompound.setTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
		}

	}
}