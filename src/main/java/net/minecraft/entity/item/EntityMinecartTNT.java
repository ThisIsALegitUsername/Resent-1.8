package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
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
public class EntityMinecartTNT extends EntityMinecart {
	private int minecartTNTFuse = -1;

	public EntityMinecartTNT(World worldIn) {
		super(worldIn);
	}

	public EntityMinecartTNT(World worldIn, double parDouble1, double parDouble2, double parDouble3) {
		super(worldIn, parDouble1, parDouble2, parDouble3);
	}

	public EntityMinecart.EnumMinecartType getMinecartType() {
		return EntityMinecart.EnumMinecartType.TNT;
	}

	public IBlockState getDefaultDisplayTile() {
		return Blocks.tnt.getDefaultState();
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		super.onUpdate();
		if (this.minecartTNTFuse > 0) {
			--this.minecartTNTFuse;
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D,
					0.0D, 0.0D, new int[0]);
		} else if (this.minecartTNTFuse == 0) {
			this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
		}

		if (this.isCollidedHorizontally) {
			double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
			if (d0 >= 0.009999999776482582D) {
				this.explodeCart(d0);
			}
		}

	}

	/**+
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getSourceOfDamage();
		if (entity instanceof EntityArrow) {
			EntityArrow entityarrow = (EntityArrow) entity;
			if (entityarrow.isBurning()) {
				this.explodeCart(entityarrow.motionX * entityarrow.motionX + entityarrow.motionY * entityarrow.motionY
						+ entityarrow.motionZ * entityarrow.motionZ);
			}
		}

		return super.attackEntityFrom(damagesource, f);
	}

	public void killMinecart(DamageSource damagesource) {
		super.killMinecart(damagesource);
		double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
		if (!damagesource.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
			this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
		}

		if (damagesource.isFireDamage() || damagesource.isExplosion() || d0 >= 0.009999999776482582D) {
			this.explodeCart(d0);
		}

	}

	/**+
	 * Makes the minecart explode.
	 */
	protected void explodeCart(double parDouble1) {

	}

	public void fall(float f, float f1) {
		if (f >= 3.0F) {
			float f2 = f / 10.0F;
			this.explodeCart((double) (f2 * f2));
		}

		super.fall(f, f1);
	}

	/**+
	 * Called every tick the minecart is on an activator rail. Args:
	 * x, y, z, is the rail receiving power
	 */
	public void onActivatorRailPass(int var1, int var2, int var3, boolean flag) {
		if (flag && this.minecartTNTFuse < 0) {
			this.ignite();
		}

	}

	public void handleStatusUpdate(byte b0) {
		if (b0 == 10) {
			this.ignite();
		} else {
			super.handleStatusUpdate(b0);
		}

	}

	/**+
	 * Ignites this TNT cart.
	 */
	public void ignite() {
		this.minecartTNTFuse = 80;
	}

	/**+
	 * Gets the remaining fuse time in ticks.
	 */
	public int getFuseTicks() {
		return this.minecartTNTFuse;
	}

	/**+
	 * Returns true if the TNT minecart is ignited.
	 */
	public boolean isIgnited() {
		return this.minecartTNTFuse > -1;
	}

	/**+
	 * Explosion resistance of a block relative to this entity
	 */
	public float getExplosionResistance(Explosion explosion, World world, BlockPos blockpos, IBlockState iblockstate) {
		return !this.isIgnited()
				|| !BlockRailBase.isRailBlock(iblockstate) && !BlockRailBase.isRailBlock(world, blockpos.up())
						? super.getExplosionResistance(explosion, world, blockpos, iblockstate)
						: 0.0F;
	}

	public boolean verifyExplosion(Explosion explosion, World world, BlockPos blockpos, IBlockState iblockstate,
			float f) {
		return !this.isIgnited()
				|| !BlockRailBase.isRailBlock(iblockstate) && !BlockRailBase.isRailBlock(world, blockpos.up())
						? super.verifyExplosion(explosion, world, blockpos, iblockstate, f)
						: false;
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		if (nbttagcompound.hasKey("TNTFuse", 99)) {
			this.minecartTNTFuse = nbttagcompound.getInteger("TNTFuse");
		}

	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("TNTFuse", this.minecartTNTFuse);
	}
}