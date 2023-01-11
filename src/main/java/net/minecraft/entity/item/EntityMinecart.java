package net.minecraft.entity.item;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

public abstract class EntityMinecart extends Entity implements IWorldNameable {
	private boolean isInReverse;
	private String entityName;
	/**+
	 * Minecart rotational logic matrix
	 */
	private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } },
			{ { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } },
			{ { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } },
			{ { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
	private int turnProgress;
	private double minecartX;
	private double minecartY;
	private double minecartZ;
	private double minecartYaw;
	private double minecartPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;

	public EntityMinecart(World worldIn) {
		super(worldIn);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.7F);
	}

	public static EntityMinecart func_180458_a(World worldIn, double parDouble1, double parDouble2, double parDouble3,
			EntityMinecart.EnumMinecartType parEnumMinecartType) {
		switch (parEnumMinecartType) {
		case CHEST:
			return new EntityMinecartChest(worldIn, parDouble1, parDouble2, parDouble3);
		case FURNACE:
			return new EntityMinecartFurnace(worldIn, parDouble1, parDouble2, parDouble3);
		case TNT:
			return new EntityMinecartTNT(worldIn, parDouble1, parDouble2, parDouble3);
		case SPAWNER:
			return new EntityMinecartMobSpawner(worldIn, parDouble1, parDouble2, parDouble3);
		case HOPPER:
			return new EntityMinecartHopper(worldIn, parDouble1, parDouble2, parDouble3);
		case COMMAND_BLOCK:
			return new EntityMinecartCommandBlock(worldIn, parDouble1, parDouble2, parDouble3);
		default:
			return new EntityMinecartEmpty(worldIn, parDouble1, parDouble2, parDouble3);
		}
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
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
		this.dataWatcher.addObject(20, new Integer(0));
		this.dataWatcher.addObject(21, new Integer(6));
		this.dataWatcher.addObject(22, Byte.valueOf((byte) 0));
	}

	/**+
	 * Returns a boundingBox used to collide the entity with other
	 * entities and blocks. This enables the entity to be pushable
	 * on contact, like boats or minecarts.
	 */
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.canBePushed() ? entity.getEntityBoundingBox() : null;
	}

	/**+
	 * Returns the collision bounding box for this entity
	 */
	public AxisAlignedBB getCollisionBoundingBox() {
		return null;
	}

	/**+
	 * Returns true if this entity should push and be pushed by
	 * other entities when colliding.
	 */
	public boolean canBePushed() {
		return true;
	}

	public EntityMinecart(World worldIn, double x, double y, double z) {
		this(worldIn);
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/**+
	 * Returns the Y offset from the entity's position for any
	 * entity riding this one.
	 */
	public double getMountedYOffset() {
		return 0.0D;
	}

	/**+
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		return true;
	}

	public void killMinecart(DamageSource parDamageSource) {
		this.setDead();
		if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
			ItemStack itemstack = new ItemStack(Items.minecart, 1);
			if (this.entityName != null) {
				itemstack.setStackDisplayName(this.entityName);
			}

			this.entityDropItem(itemstack, 0.0F);
		}

	}

	/**+
	 * Setups the entity to do the hurt animation. Only used by
	 * packets in multiplayer.
	 */
	public void performHurtAnimation() {
		this.setRollingDirection(-this.getRollingDirection());
		this.setRollingAmplitude(10);
		this.setDamage(this.getDamage() + this.getDamage() * 10.0F);
	}

	/**+
	 * Returns true if other Entities should be prevented from
	 * moving through this Entity.
	 */
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/**+
	 * Will get destroyed next tick.
	 */
	public void setDead() {
		super.setDead();
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		if (this.getRollingAmplitude() > 0) {
			this.setRollingAmplitude(this.getRollingAmplitude() - 1);
		}

		if (this.getDamage() > 0.0F) {
			this.setDamage(this.getDamage() - 1.0F);
		}

		if (this.posY < -64.0D) {
			this.kill();
		}

		if (this.turnProgress > 0) {
			double d4 = this.posX + (this.minecartX - this.posX) / (double) this.turnProgress;
			double d5 = this.posY + (this.minecartY - this.posY) / (double) this.turnProgress;
			double d6 = this.posZ + (this.minecartZ - this.posZ) / (double) this.turnProgress;
			double d1 = MathHelper.wrapAngleTo180_double(this.minecartYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + d1 / (double) this.turnProgress);
			this.rotationPitch = (float) ((double) this.rotationPitch
					+ (this.minecartPitch - (double) this.rotationPitch) / (double) this.turnProgress);
			--this.turnProgress;
			this.setPosition(d4, d5, d6);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		} else {
			this.setPosition(this.posX, this.posY, this.posZ);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
	}

	/**+
	 * Get's the maximum speed for a minecart
	 */
	protected double getMaximumSpeed() {
		return 0.4D;
	}

	/**+
	 * Called every tick the minecart is on an activator rail. Args:
	 * x, y, z, is the rail receiving power
	 */
	public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
	}

	/**+
	 * Moves a minecart that is not attached to a rail
	 */
	protected void moveDerailedMinecart() {
		double d0 = this.getMaximumSpeed();
		this.motionX = MathHelper.clamp_double(this.motionX, -d0, d0);
		this.motionZ = MathHelper.clamp_double(this.motionZ, -d0, d0);
		if (this.onGround) {
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		if (!this.onGround) {
			this.motionX *= 0.949999988079071D;
			this.motionY *= 0.949999988079071D;
			this.motionZ *= 0.949999988079071D;
		}

	}

	protected void func_180460_a(BlockPos parBlockPos, IBlockState parIBlockState) {
		this.fallDistance = 0.0F;
		Vec3 vec3 = this.func_70489_a(this.posX, this.posY, this.posZ);
		this.posY = (double) parBlockPos.getY();
		boolean flag = false;
		boolean flag1 = false;
		BlockRailBase blockrailbase = (BlockRailBase) parIBlockState.getBlock();
		if (blockrailbase == Blocks.golden_rail) {
			flag = ((Boolean) parIBlockState.getValue(BlockRailPowered.POWERED)).booleanValue();
			flag1 = !flag;
		}

		double d0 = 0.0078125D;
		BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection) parIBlockState
				.getValue(blockrailbase.getShapeProperty());
		switch (blockrailbase$enumraildirection) {
		case ASCENDING_EAST:
			this.motionX -= 0.0078125D;
			++this.posY;
			break;
		case ASCENDING_WEST:
			this.motionX += 0.0078125D;
			++this.posY;
			break;
		case ASCENDING_NORTH:
			this.motionZ += 0.0078125D;
			++this.posY;
			break;
		case ASCENDING_SOUTH:
			this.motionZ -= 0.0078125D;
			++this.posY;
		}

		int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
		double d1 = (double) (aint[1][0] - aint[0][0]);
		double d2 = (double) (aint[1][2] - aint[0][2]);
		double d3 = Math.sqrt(d1 * d1 + d2 * d2);
		double d4 = this.motionX * d1 + this.motionZ * d2;
		if (d4 < 0.0D) {
			d1 = -d1;
			d2 = -d2;
		}

		double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		if (d5 > 2.0D) {
			d5 = 2.0D;
		}

		this.motionX = d5 * d1 / d3;
		this.motionZ = d5 * d2 / d3;
		if (this.riddenByEntity instanceof EntityLivingBase) {
			double d6 = (double) ((EntityLivingBase) this.riddenByEntity).moveForward;
			if (d6 > 0.0D) {
				double d7 = -Math.sin((double) (this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F));
				double d8 = Math.cos((double) (this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F));
				double d9 = this.motionX * this.motionX + this.motionZ * this.motionZ;
				if (d9 < 0.01D) {
					this.motionX += d7 * 0.1D;
					this.motionZ += d8 * 0.1D;
					flag1 = false;
				}
			}
		}

		if (flag1) {
			double d17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			if (d17 < 0.03D) {
				this.motionX *= 0.0D;
				this.motionY *= 0.0D;
				this.motionZ *= 0.0D;
			} else {
				this.motionX *= 0.5D;
				this.motionY *= 0.0D;
				this.motionZ *= 0.5D;
			}
		}

		double d18 = 0.0D;
		double d19 = (double) parBlockPos.getX() + 0.5D + (double) aint[0][0] * 0.5D;
		double d20 = (double) parBlockPos.getZ() + 0.5D + (double) aint[0][2] * 0.5D;
		double d21 = (double) parBlockPos.getX() + 0.5D + (double) aint[1][0] * 0.5D;
		double d10 = (double) parBlockPos.getZ() + 0.5D + (double) aint[1][2] * 0.5D;
		d1 = d21 - d19;
		d2 = d10 - d20;
		if (d1 == 0.0D) {
			this.posX = (double) parBlockPos.getX() + 0.5D;
			d18 = this.posZ - (double) parBlockPos.getZ();
		} else if (d2 == 0.0D) {
			this.posZ = (double) parBlockPos.getZ() + 0.5D;
			d18 = this.posX - (double) parBlockPos.getX();
		} else {
			double d11 = this.posX - d19;
			double d12 = this.posZ - d20;
			d18 = (d11 * d1 + d12 * d2) * 2.0D;
		}

		this.posX = d19 + d1 * d18;
		this.posZ = d20 + d2 * d18;
		this.setPosition(this.posX, this.posY, this.posZ);
		double d22 = this.motionX;
		double d23 = this.motionZ;
		if (this.riddenByEntity != null) {
			d22 *= 0.75D;
			d23 *= 0.75D;
		}

		double d13 = this.getMaximumSpeed();
		d22 = MathHelper.clamp_double(d22, -d13, d13);
		d23 = MathHelper.clamp_double(d23, -d13, d13);
		this.moveEntity(d22, 0.0D, d23);
		if (aint[0][1] != 0 && MathHelper.floor_double(this.posX) - parBlockPos.getX() == aint[0][0]
				&& MathHelper.floor_double(this.posZ) - parBlockPos.getZ() == aint[0][2]) {
			this.setPosition(this.posX, this.posY + (double) aint[0][1], this.posZ);
		} else if (aint[1][1] != 0 && MathHelper.floor_double(this.posX) - parBlockPos.getX() == aint[1][0]
				&& MathHelper.floor_double(this.posZ) - parBlockPos.getZ() == aint[1][2]) {
			this.setPosition(this.posX, this.posY + (double) aint[1][1], this.posZ);
		}

		this.applyDrag();
		Vec3 vec31 = this.func_70489_a(this.posX, this.posY, this.posZ);
		if (vec31 != null && vec3 != null) {
			double d14 = (vec3.yCoord - vec31.yCoord) * 0.05D;
			d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			if (d5 > 0.0D) {
				this.motionX = this.motionX / d5 * (d5 + d14);
				this.motionZ = this.motionZ / d5 * (d5 + d14);
			}

			this.setPosition(this.posX, vec31.yCoord, this.posZ);
		}

		int j = MathHelper.floor_double(this.posX);
		int i = MathHelper.floor_double(this.posZ);
		if (j != parBlockPos.getX() || i != parBlockPos.getZ()) {
			d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.motionX = d5 * (double) (j - parBlockPos.getX());
			this.motionZ = d5 * (double) (i - parBlockPos.getZ());
		}

		if (flag) {
			double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			if (d15 > 0.01D) {
				double d16 = 0.06D;
				this.motionX += this.motionX / d15 * d16;
				this.motionZ += this.motionZ / d15 * d16;
			} else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
				if (this.worldObj.getBlockState(parBlockPos.west()).getBlock().isNormalCube()) {
					this.motionX = 0.02D;
				} else if (this.worldObj.getBlockState(parBlockPos.east()).getBlock().isNormalCube()) {
					this.motionX = -0.02D;
				}
			} else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
				if (this.worldObj.getBlockState(parBlockPos.north()).getBlock().isNormalCube()) {
					this.motionZ = 0.02D;
				} else if (this.worldObj.getBlockState(parBlockPos.south()).getBlock().isNormalCube()) {
					this.motionZ = -0.02D;
				}
			}
		}

	}

	protected void applyDrag() {
		if (this.riddenByEntity != null) {
			this.motionX *= 0.996999979019165D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.996999979019165D;
		} else {
			this.motionX *= 0.9599999785423279D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.9599999785423279D;
		}

	}

	/**+
	 * Sets the x,y,z of the entity from the given parameters. Also
	 * seems to set up a bounding box.
	 */
	public void setPosition(double d0, double d1, double d2) {
		this.posX = d0;
		this.posY = d1;
		this.posZ = d2;
		float f = this.width / 2.0F;
		float f1 = this.height;
		this.setEntityBoundingBox(new AxisAlignedBB(d0 - (double) f, d1, d2 - (double) f, d0 + (double) f,
				d1 + (double) f1, d2 + (double) f));
	}

	public Vec3 func_70495_a(double parDouble1, double parDouble2, double parDouble3, double parDouble4) {
		int i = MathHelper.floor_double(parDouble1);
		int j = MathHelper.floor_double(parDouble2);
		int k = MathHelper.floor_double(parDouble3);
		if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k))) {
			--j;
		}

		IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
		if (BlockRailBase.isRailBlock(iblockstate)) {
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection) iblockstate
					.getValue(((BlockRailBase) iblockstate.getBlock()).getShapeProperty());
			parDouble2 = (double) j;
			if (blockrailbase$enumraildirection.isAscending()) {
				parDouble2 = (double) (j + 1);
			}

			int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
			double d0 = (double) (aint[1][0] - aint[0][0]);
			double d1 = (double) (aint[1][2] - aint[0][2]);
			double d2 = Math.sqrt(d0 * d0 + d1 * d1);
			d0 = d0 / d2;
			d1 = d1 / d2;
			parDouble1 = parDouble1 + d0 * parDouble4;
			parDouble3 = parDouble3 + d1 * parDouble4;
			if (aint[0][1] != 0 && MathHelper.floor_double(parDouble1) - i == aint[0][0]
					&& MathHelper.floor_double(parDouble3) - k == aint[0][2]) {
				parDouble2 += (double) aint[0][1];
			} else if (aint[1][1] != 0 && MathHelper.floor_double(parDouble1) - i == aint[1][0]
					&& MathHelper.floor_double(parDouble3) - k == aint[1][2]) {
				parDouble2 += (double) aint[1][1];
			}

			return this.func_70489_a(parDouble1, parDouble2, parDouble3);
		} else {
			return null;
		}
	}

	public Vec3 func_70489_a(double parDouble1, double parDouble2, double parDouble3) {
		int i = MathHelper.floor_double(parDouble1);
		int j = MathHelper.floor_double(parDouble2);
		int k = MathHelper.floor_double(parDouble3);
		if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k))) {
			--j;
		}

		IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
		if (BlockRailBase.isRailBlock(iblockstate)) {
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection) iblockstate
					.getValue(((BlockRailBase) iblockstate.getBlock()).getShapeProperty());
			int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
			double d0 = 0.0D;
			double d1 = (double) i + 0.5D + (double) aint[0][0] * 0.5D;
			double d2 = (double) j + 0.0625D + (double) aint[0][1] * 0.5D;
			double d3 = (double) k + 0.5D + (double) aint[0][2] * 0.5D;
			double d4 = (double) i + 0.5D + (double) aint[1][0] * 0.5D;
			double d5 = (double) j + 0.0625D + (double) aint[1][1] * 0.5D;
			double d6 = (double) k + 0.5D + (double) aint[1][2] * 0.5D;
			double d7 = d4 - d1;
			double d8 = (d5 - d2) * 2.0D;
			double d9 = d6 - d3;
			if (d7 == 0.0D) {
				parDouble1 = (double) i + 0.5D;
				d0 = parDouble3 - (double) k;
			} else if (d9 == 0.0D) {
				parDouble3 = (double) k + 0.5D;
				d0 = parDouble1 - (double) i;
			} else {
				double d10 = parDouble1 - d1;
				double d11 = parDouble3 - d3;
				d0 = (d10 * d7 + d11 * d9) * 2.0D;
			}

			parDouble1 = d1 + d7 * d0;
			parDouble2 = d2 + d8 * d0;
			parDouble3 = d3 + d9 * d0;
			if (d8 < 0.0D) {
				++parDouble2;
			}

			if (d8 > 0.0D) {
				parDouble2 += 0.5D;
			}

			return new Vec3(parDouble1, parDouble2, parDouble3);
		} else {
			return null;
		}
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		if (nbttagcompound.getBoolean("CustomDisplayTile")) {
			int i = nbttagcompound.getInteger("DisplayData");
			if (nbttagcompound.hasKey("DisplayTile", 8)) {
				Block block = Block.getBlockFromName(nbttagcompound.getString("DisplayTile"));
				if (block == null) {
					this.func_174899_a(Blocks.air.getDefaultState());
				} else {
					this.func_174899_a(block.getStateFromMeta(i));
				}
			} else {
				Block block1 = Block.getBlockById(nbttagcompound.getInteger("DisplayTile"));
				if (block1 == null) {
					this.func_174899_a(Blocks.air.getDefaultState());
				} else {
					this.func_174899_a(block1.getStateFromMeta(i));
				}
			}

			this.setDisplayTileOffset(nbttagcompound.getInteger("DisplayOffset"));
		}

		if (nbttagcompound.hasKey("CustomName", 8) && nbttagcompound.getString("CustomName").length() > 0) {
			this.entityName = nbttagcompound.getString("CustomName");
		}

	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		if (this.hasDisplayTile()) {
			nbttagcompound.setBoolean("CustomDisplayTile", true);
			IBlockState iblockstate = this.getDisplayTile();
			ResourceLocation resourcelocation = (ResourceLocation) Block.blockRegistry
					.getNameForObject(iblockstate.getBlock());
			nbttagcompound.setString("DisplayTile", resourcelocation == null ? "" : resourcelocation.toString());
			nbttagcompound.setInteger("DisplayData", iblockstate.getBlock().getMetaFromState(iblockstate));
			nbttagcompound.setInteger("DisplayOffset", this.getDisplayTileOffset());
		}

		if (this.entityName != null && this.entityName.length() > 0) {
			nbttagcompound.setString("CustomName", this.entityName);
		}

	}

	/**+
	 * Applies a velocity to each of the entities pushing them away
	 * from each other. Args: entity
	 */
	public void applyEntityCollision(Entity entity) {

	}

	public void setPositionAndRotation2(double d0, double d1, double d2, float f, float f1, int i, boolean var10) {
		this.minecartX = d0;
		this.minecartY = d1;
		this.minecartZ = d2;
		this.minecartYaw = (double) f;
		this.minecartPitch = (double) f1;
		this.turnProgress = i + 2;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	/**+
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double d0, double d1, double d2) {
		this.velocityX = this.motionX = d0;
		this.velocityY = this.motionY = d1;
		this.velocityZ = this.motionZ = d2;
	}

	/**+
	 * Sets the current amount of damage the minecart has taken.
	 * Decreases over time. The cart breaks when this is over 40.
	 */
	public void setDamage(float parFloat1) {
		this.dataWatcher.updateObject(19, Float.valueOf(parFloat1));
	}

	/**+
	 * Gets the current amount of damage the minecart has taken.
	 * Decreases over time. The cart breaks when this is over 40.
	 */
	public float getDamage() {
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	/**+
	 * Sets the rolling amplitude the cart rolls while being
	 * attacked.
	 */
	public void setRollingAmplitude(int parInt1) {
		this.dataWatcher.updateObject(17, Integer.valueOf(parInt1));
	}

	/**+
	 * Gets the rolling amplitude the cart rolls while being
	 * attacked.
	 */
	public int getRollingAmplitude() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	/**+
	 * Sets the rolling direction the cart rolls while being
	 * attacked. Can be 1 or -1.
	 */
	public void setRollingDirection(int parInt1) {
		this.dataWatcher.updateObject(18, Integer.valueOf(parInt1));
	}

	/**+
	 * Gets the rolling direction the cart rolls while being
	 * attacked. Can be 1 or -1.
	 */
	public int getRollingDirection() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	public abstract EntityMinecart.EnumMinecartType getMinecartType();

	public IBlockState getDisplayTile() {
		return !this.hasDisplayTile() ? this.getDefaultDisplayTile()
				: Block.getStateById(this.getDataWatcher().getWatchableObjectInt(20));
	}

	public IBlockState getDefaultDisplayTile() {
		return Blocks.air.getDefaultState();
	}

	public int getDisplayTileOffset() {
		return !this.hasDisplayTile() ? this.getDefaultDisplayTileOffset()
				: this.getDataWatcher().getWatchableObjectInt(21);
	}

	public int getDefaultDisplayTileOffset() {
		return 6;
	}

	public void func_174899_a(IBlockState parIBlockState) {
		this.getDataWatcher().updateObject(20, Integer.valueOf(Block.getStateId(parIBlockState)));
		this.setHasDisplayTile(true);
	}

	public void setDisplayTileOffset(int parInt1) {
		this.getDataWatcher().updateObject(21, Integer.valueOf(parInt1));
		this.setHasDisplayTile(true);
	}

	public boolean hasDisplayTile() {
		return this.getDataWatcher().getWatchableObjectByte(22) == 1;
	}

	public void setHasDisplayTile(boolean parFlag) {
		this.getDataWatcher().updateObject(22, Byte.valueOf((byte) (parFlag ? 1 : 0)));
	}

	/**+
	 * Sets the custom name tag for this entity
	 */
	public void setCustomNameTag(String s) {
		this.entityName = s;
	}

	/**+
	 * Gets the name of this command sender (usually username, but
	 * possibly "Rcon")
	 */
	public String getName() {
		return this.entityName != null ? this.entityName : super.getName();
	}

	/**+
	 * Returns true if this thing is named
	 */
	public boolean hasCustomName() {
		return this.entityName != null;
	}

	public String getCustomNameTag() {
		return this.entityName;
	}

	/**+
	 * Get the formatted ChatComponent that will be used for the
	 * sender's username in chat
	 */
	public IChatComponent getDisplayName() {
		if (this.hasCustomName()) {
			ChatComponentText chatcomponenttext = new ChatComponentText(this.entityName);
			chatcomponenttext.getChatStyle().setChatHoverEvent(this.getHoverEvent());
			chatcomponenttext.getChatStyle().setInsertion(this.getUniqueID().toString());
			return chatcomponenttext;
		} else {
			ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(this.getName(),
					new Object[0]);
			chatcomponenttranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
			chatcomponenttranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
			return chatcomponenttranslation;
		}
	}

	public static enum EnumMinecartType {
		RIDEABLE(0, "MinecartRideable"), CHEST(1, "MinecartChest"), FURNACE(2, "MinecartFurnace"),
		TNT(3, "MinecartTNT"), SPAWNER(4, "MinecartSpawner"), HOPPER(5, "MinecartHopper"),
		COMMAND_BLOCK(6, "MinecartCommandBlock");

		private static final Map<Integer, EntityMinecart.EnumMinecartType> ID_LOOKUP = Maps.newHashMap();
		private final int networkID;
		private final String name;

		private EnumMinecartType(int networkID, String name) {
			this.networkID = networkID;
			this.name = name;
		}

		public int getNetworkID() {
			return this.networkID;
		}

		/**+
		 * Gets the name of this command sender (usually username, but
		 * possibly "Rcon")
		 */
		public String getName() {
			return this.name;
		}

		public static EntityMinecart.EnumMinecartType byNetworkID(int id) {
			EntityMinecart.EnumMinecartType entityminecart$enumminecarttype = (EntityMinecart.EnumMinecartType) ID_LOOKUP
					.get(Integer.valueOf(id));
			return entityminecart$enumminecarttype == null ? RIDEABLE : entityminecart$enumminecarttype;
		}

		static {
			for (EntityMinecart.EnumMinecartType entityminecart$enumminecarttype : values()) {
				ID_LOOKUP.put(Integer.valueOf(entityminecart$enumminecarttype.getNetworkID()),
						entityminecart$enumminecarttype);
			}

		}
	}
}