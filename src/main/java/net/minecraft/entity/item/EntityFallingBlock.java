package net.minecraft.entity.item;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
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
public class EntityFallingBlock extends Entity {
	private IBlockState fallTile;
	public int fallTime;
	public boolean shouldDropItem = true;
	private boolean canSetAsBlock;
	private boolean hurtEntities;
	private int fallHurtMax = 40;
	private float fallHurtAmount = 2.0F;
	public NBTTagCompound tileEntityData;

	public EntityFallingBlock(World worldIn) {
		super(worldIn);
	}

	public EntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
		super(worldIn);
		this.fallTile = fallingBlockState;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/**+
	 * returns if this entity triggers Block.onEntityWalking on the
	 * blocks they walk on. used for spiders and wolves to prevent
	 * them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
	}

	/**+
	 * Returns true if other Entities should be prevented from
	 * moving through this Entity.
	 */
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		Block block = this.fallTile.getBlock();
		if (block.getMaterial() == Material.air) {
			this.setDead();
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			if (this.fallTime++ == 0) {
				BlockPos blockpos = new BlockPos(this);
				if (this.worldObj.getBlockState(blockpos).getBlock() == block) {
					this.worldObj.setBlockToAir(blockpos);
				}
			}

			this.motionY -= 0.03999999910593033D;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.9800000190734863D;

		}
	}

	public void fall(float f, float var2) {
		Block block = this.fallTile.getBlock();
		if (this.hurtEntities) {
			int i = MathHelper.ceiling_float_int(f - 1.0F);
			if (i > 0) {
				ArrayList arraylist = Lists.newArrayList(
						this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
				boolean flag = block == Blocks.anvil;
				DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;

				for (Entity entity : (List<Entity>) arraylist) {
					entity.attackEntityFrom(damagesource, (float) Math
							.min(MathHelper.floor_float((float) i * this.fallHurtAmount), this.fallHurtMax));
				}

				if (flag && (double) this.rand.nextFloat() < 0.05000000074505806D + (double) i * 0.05D) {
					int j = ((Integer) this.fallTile.getValue(BlockAnvil.DAMAGE)).intValue();
					++j;
					if (j > 2) {
						this.canSetAsBlock = true;
					} else {
						this.fallTile = this.fallTile.withProperty(BlockAnvil.DAMAGE, Integer.valueOf(j));
					}
				}
			}
		}

	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		Block block = this.fallTile != null ? this.fallTile.getBlock() : Blocks.air;
		ResourceLocation resourcelocation = (ResourceLocation) Block.blockRegistry.getNameForObject(block);
		nbttagcompound.setString("Block", resourcelocation == null ? "" : resourcelocation.toString());
		nbttagcompound.setByte("Data", (byte) block.getMetaFromState(this.fallTile));
		nbttagcompound.setByte("Time", (byte) this.fallTime);
		nbttagcompound.setBoolean("DropItem", this.shouldDropItem);
		nbttagcompound.setBoolean("HurtEntities", this.hurtEntities);
		nbttagcompound.setFloat("FallHurtAmount", this.fallHurtAmount);
		nbttagcompound.setInteger("FallHurtMax", this.fallHurtMax);
		if (this.tileEntityData != null) {
			nbttagcompound.setTag("TileEntityData", this.tileEntityData);
		}

	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		int i = nbttagcompound.getByte("Data") & 255;
		if (nbttagcompound.hasKey("Block", 8)) {
			this.fallTile = Block.getBlockFromName(nbttagcompound.getString("Block")).getStateFromMeta(i);
		} else if (nbttagcompound.hasKey("TileID", 99)) {
			this.fallTile = Block.getBlockById(nbttagcompound.getInteger("TileID")).getStateFromMeta(i);
		} else {
			this.fallTile = Block.getBlockById(nbttagcompound.getByte("Tile") & 255).getStateFromMeta(i);
		}

		this.fallTime = nbttagcompound.getByte("Time") & 255;
		Block block = this.fallTile.getBlock();
		if (nbttagcompound.hasKey("HurtEntities", 99)) {
			this.hurtEntities = nbttagcompound.getBoolean("HurtEntities");
			this.fallHurtAmount = nbttagcompound.getFloat("FallHurtAmount");
			this.fallHurtMax = nbttagcompound.getInteger("FallHurtMax");
		} else if (block == Blocks.anvil) {
			this.hurtEntities = true;
		}

		if (nbttagcompound.hasKey("DropItem", 99)) {
			this.shouldDropItem = nbttagcompound.getBoolean("DropItem");
		}

		if (nbttagcompound.hasKey("TileEntityData", 10)) {
			this.tileEntityData = nbttagcompound.getCompoundTag("TileEntityData");
		}

		if (block == null || block.getMaterial() == Material.air) {
			this.fallTile = Blocks.sand.getDefaultState();
		}

	}

	public World getWorldObj() {
		return this.worldObj;
	}

	public void setHurtEntities(boolean parFlag) {
		this.hurtEntities = parFlag;
	}

	/**+
	 * Return whether this entity should be rendered as on fire.
	 */
	public boolean canRenderOnFire() {
		return false;
	}

	public void addEntityCrashInfo(CrashReportCategory crashreportcategory) {
		super.addEntityCrashInfo(crashreportcategory);
		if (this.fallTile != null) {
			Block block = this.fallTile.getBlock();
			crashreportcategory.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
			crashreportcategory.addCrashSection("Immitating block data",
					Integer.valueOf(block.getMetaFromState(this.fallTile)));
		}

	}

	public IBlockState getBlock() {
		return this.fallTile;
	}
}