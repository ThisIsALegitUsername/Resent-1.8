package net.minecraft.entity.passive;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
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
public class EntityHorse extends EntityAnimal implements IInvBasic {
	private static final Predicate<Entity> horseBreedingSelector = new Predicate<Entity>() {
		public boolean apply(Entity entity) {
			return entity instanceof EntityHorse && ((EntityHorse) entity).isBreeding();
		}
	};
	private static final IAttribute horseJumpStrength = (new RangedAttribute((IAttribute) null, "horse.jumpStrength",
			0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);
	private static final String[] horseArmorTextures = new String[] { null,
			"textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png",
			"textures/entity/horse/armor/horse_armor_diamond.png" };
	private static final String[] HORSE_ARMOR_TEXTURES_ABBR = new String[] { "", "meo", "goo", "dio" };
	private static final int[] armorValues = new int[] { 0, 5, 7, 11 };
	private static final String[] horseTextures = new String[] { "textures/entity/horse/horse_white.png",
			"textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png",
			"textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png",
			"textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
	private static final String[] HORSE_TEXTURES_ABBR = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr",
			"hdb" };
	private static final String[] horseMarkingTextures = new String[] { null,
			"textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png",
			"textures/entity/horse/horse_markings_whitedots.png",
			"textures/entity/horse/horse_markings_blackdots.png" };
	private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
	private int eatingHaystackCounter;
	private int openMouthCounter;
	private int jumpRearingCounter;
	public int field_110278_bp;
	public int field_110279_bq;
	protected boolean horseJumping;
	private AnimalChest horseChest;
	private boolean hasReproduced;
	protected int temper;
	protected float jumpPower;
	private boolean field_110294_bI;
	private float headLean;
	private float prevHeadLean;
	private float rearingAmount;
	private float prevRearingAmount;
	private float mouthOpenness;
	private float prevMouthOpenness;
	private int gallopTime;
	private String texturePrefix;
	private String[] horseTexturesArray = new String[3];
	private boolean field_175508_bO = false;

	public EntityHorse(World worldIn) {
		super(worldIn);
		this.setSize(1.4F, 1.6F);
		this.isImmuneToFire = false;
		this.setChested(false);
		this.initHorseChest();
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Integer.valueOf(0));
		this.dataWatcher.addObject(19, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(20, Integer.valueOf(0));
		this.dataWatcher.addObject(21, String.valueOf(""));
		this.dataWatcher.addObject(22, Integer.valueOf(0));
	}

	public void setHorseType(int type) {
		this.dataWatcher.updateObject(19, Byte.valueOf((byte) type));
		this.resetTexturePrefix();
	}

	/**+
	 * Returns the horse type. 0 = Normal, 1 = Donkey, 2 = Mule, 3 =
	 * Undead Horse, 4 = Skeleton Horse
	 */
	public int getHorseType() {
		return this.dataWatcher.getWatchableObjectByte(19);
	}

	public void setHorseVariant(int variant) {
		this.dataWatcher.updateObject(20, Integer.valueOf(variant));
		this.resetTexturePrefix();
	}

	public int getHorseVariant() {
		return this.dataWatcher.getWatchableObjectInt(20);
	}

	/**+
	 * Gets the name of this command sender (usually username, but
	 * possibly "Rcon")
	 */
	public String getName() {
		if (this.hasCustomName()) {
			return this.getCustomNameTag();
		} else {
			int i = this.getHorseType();
			switch (i) {
			case 0:
			default:
				return StatCollector.translateToLocal("entity.horse.name");
			case 1:
				return StatCollector.translateToLocal("entity.donkey.name");
			case 2:
				return StatCollector.translateToLocal("entity.mule.name");
			case 3:
				return StatCollector.translateToLocal("entity.zombiehorse.name");
			case 4:
				return StatCollector.translateToLocal("entity.skeletonhorse.name");
			}
		}
	}

	private boolean getHorseWatchableBoolean(int parInt1) {
		return (this.dataWatcher.getWatchableObjectInt(16) & parInt1) != 0;
	}

	private void setHorseWatchableBoolean(int parInt1, boolean parFlag) {
		int i = this.dataWatcher.getWatchableObjectInt(16);
		if (parFlag) {
			this.dataWatcher.updateObject(16, Integer.valueOf(i | parInt1));
		} else {
			this.dataWatcher.updateObject(16, Integer.valueOf(i & ~parInt1));
		}

	}

	public boolean isAdultHorse() {
		return !this.isChild();
	}

	public boolean isTame() {
		return this.getHorseWatchableBoolean(2);
	}

	public boolean func_110253_bW() {
		return this.isAdultHorse();
	}

	/**+
	 * Gets the horse's owner
	 */
	public String getOwnerId() {
		return this.dataWatcher.getWatchableObjectString(21);
	}

	public void setOwnerId(String id) {
		this.dataWatcher.updateObject(21, id);
	}

	public float getHorseSize() {
		return 0.5F;
	}

	/**+
	 * "Sets the scale for an ageable entity according to the
	 * boolean parameter, which says if it's a child."
	 */
	public void setScaleForAge(boolean flag) {
		if (flag) {
			this.setScale(this.getHorseSize());
		} else {
			this.setScale(1.0F);
		}

	}

	public boolean isHorseJumping() {
		return this.horseJumping;
	}

	public void setHorseTamed(boolean tamed) {
		this.setHorseWatchableBoolean(2, tamed);
	}

	public void setHorseJumping(boolean jumping) {
		this.horseJumping = jumping;
	}

	public boolean allowLeashing() {
		return !this.isUndead() && super.allowLeashing();
	}

	protected void func_142017_o(float f) {
		if (f > 6.0F && this.isEatingHaystack()) {
			this.setEatingHaystack(false);
		}

	}

	public boolean isChested() {
		return this.getHorseWatchableBoolean(8);
	}

	/**+
	 * Returns type of armor from DataWatcher (0 = iron, 1 = gold, 2
	 * = diamond)
	 */
	public int getHorseArmorIndexSynced() {
		return this.dataWatcher.getWatchableObjectInt(22);
	}

	/**+
	 * 0 = iron, 1 = gold, 2 = diamond
	 */
	private int getHorseArmorIndex(ItemStack itemStackIn) {
		if (itemStackIn == null) {
			return 0;
		} else {
			Item item = itemStackIn.getItem();
			return item == Items.iron_horse_armor ? 1
					: (item == Items.golden_horse_armor ? 2 : (item == Items.diamond_horse_armor ? 3 : 0));
		}
	}

	public boolean isEatingHaystack() {
		return this.getHorseWatchableBoolean(32);
	}

	public boolean isRearing() {
		return this.getHorseWatchableBoolean(64);
	}

	public boolean isBreeding() {
		return this.getHorseWatchableBoolean(16);
	}

	public boolean getHasReproduced() {
		return this.hasReproduced;
	}

	/**+
	 * Set horse armor stack (for example: new
	 * ItemStack(Items.iron_horse_armor))
	 */
	public void setHorseArmorStack(ItemStack itemStackIn) {
		this.dataWatcher.updateObject(22, Integer.valueOf(this.getHorseArmorIndex(itemStackIn)));
		this.resetTexturePrefix();
	}

	public void setBreeding(boolean breeding) {
		this.setHorseWatchableBoolean(16, breeding);
	}

	public void setChested(boolean chested) {
		this.setHorseWatchableBoolean(8, chested);
	}

	public void setHasReproduced(boolean hasReproducedIn) {
		this.hasReproduced = hasReproducedIn;
	}

	public void setHorseSaddled(boolean saddled) {
		this.setHorseWatchableBoolean(4, saddled);
	}

	public int getTemper() {
		return this.temper;
	}

	public void setTemper(int temperIn) {
		this.temper = temperIn;
	}

	public int increaseTemper(int parInt1) {
		int i = MathHelper.clamp_int(this.getTemper() + parInt1, 0, this.getMaxTemper());
		this.setTemper(i);
		return i;
	}

	/**+
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		return this.riddenByEntity != null && this.riddenByEntity.equals(entity) ? false
				: super.attackEntityFrom(damagesource, f);
	}

	/**+
	 * Returns the current armor value as determined by a call to
	 * InventoryPlayer.getTotalArmorValue
	 */
	public int getTotalArmorValue() {
		return armorValues[this.getHorseArmorIndexSynced()];
	}

	/**+
	 * Returns true if this entity should push and be pushed by
	 * other entities when colliding.
	 */
	public boolean canBePushed() {
		return this.riddenByEntity == null;
	}

	public boolean prepareChunkForSpawn() {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);
		this.worldObj.getBiomeGenForCoords(new BlockPos(i, 0, j));
		return true;
	}

	public void dropChests() {

	}

	private void func_110266_cB() {
		this.openHorseMouth();
		if (!this.isSilent()) {
			this.worldObj.playSoundAtEntity(this, "eating", 1.0F,
					1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
		}

	}

	public void fall(float f, float f1) {
		if (f > 1.0F) {
			this.playSound("mob.horse.land", 0.4F, 1.0F);
		}

		int i = MathHelper.ceiling_float_int((f * 0.5F - 3.0F) * f1);
		if (i > 0) {
			this.attackEntityFrom(DamageSource.fall, (float) i);
			if (this.riddenByEntity != null) {
				this.riddenByEntity.attackEntityFrom(DamageSource.fall, (float) i);
			}

			Block block = this.worldObj
					.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double) this.prevRotationYaw, this.posZ))
					.getBlock();
			if (block.getMaterial() != Material.air && !this.isSilent()) {
				Block.SoundType block$soundtype = block.stepSound;
				this.worldObj.playSoundAtEntity(this, block$soundtype.getStepSound(),
						block$soundtype.getVolume() * 0.5F, block$soundtype.getFrequency() * 0.75F);
			}

		}
	}

	/**+
	 * Returns number of slots depending horse type
	 */
	private int getChestSize() {
		int i = this.getHorseType();
		return !this.isChested() || i != 1 && i != 2 ? 2 : 17;
	}

	private void initHorseChest() {
		AnimalChest animalchest = this.horseChest;
		this.horseChest = new AnimalChest("HorseChest", this.getChestSize());
		this.horseChest.setCustomName(this.getName());
		if (animalchest != null) {
			animalchest.func_110132_b(this);
			int i = Math.min(animalchest.getSizeInventory(), this.horseChest.getSizeInventory());

			for (int j = 0; j < i; ++j) {
				ItemStack itemstack = animalchest.getStackInSlot(j);
				if (itemstack != null) {
					this.horseChest.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}

		this.horseChest.func_110134_a(this);
		this.updateHorseSlots();
	}

	/**+
	 * Updates the items in the saddle and armor slots of the
	 * horse's inventory.
	 */
	private void updateHorseSlots() {

	}

	/**+
	 * Called by InventoryBasic.onInventoryChanged() on a array that
	 * is never filled.
	 */
	public void onInventoryChanged(InventoryBasic var1) {
		int i = this.getHorseArmorIndexSynced();
		boolean flag = this.isHorseSaddled();
		this.updateHorseSlots();
		if (this.ticksExisted > 20) {
			if (i == 0 && i != this.getHorseArmorIndexSynced()) {
				this.playSound("mob.horse.armor", 0.5F, 1.0F);
			} else if (i != this.getHorseArmorIndexSynced()) {
				this.playSound("mob.horse.armor", 0.5F, 1.0F);
			}

			if (!flag && this.isHorseSaddled()) {
				this.playSound("mob.horse.leather", 0.5F, 1.0F);
			}
		}

	}

	/**+
	 * Checks if the entity's current position is a valid location
	 * to spawn this entity.
	 */
	public boolean getCanSpawnHere() {
		this.prepareChunkForSpawn();
		return super.getCanSpawnHere();
	}

	protected EntityHorse getClosestHorse(Entity entityIn, double distance) {
		double d0 = Double.MAX_VALUE;
		Entity entity = null;

		for (Entity entity1 : this.worldObj.getEntitiesInAABBexcluding(entityIn,
				entityIn.getEntityBoundingBox().addCoord(distance, distance, distance), horseBreedingSelector)) {
			double d1 = entity1.getDistanceSq(entityIn.posX, entityIn.posY, entityIn.posZ);
			if (d1 < d0) {
				entity = entity1;
				d0 = d1;
			}
		}

		return (EntityHorse) entity;
	}

	public double getHorseJumpStrength() {
		return this.getEntityAttribute(horseJumpStrength).getAttributeValue();
	}

	/**+
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		this.openHorseMouth();
		int i = this.getHorseType();
		return i == 3 ? "mob.horse.zombie.death"
				: (i == 4 ? "mob.horse.skeleton.death"
						: (i != 1 && i != 2 ? "mob.horse.death" : "mob.horse.donkey.death"));
	}

	protected Item getDropItem() {
		boolean flag = this.rand.nextInt(4) == 0;
		int i = this.getHorseType();
		return i == 4 ? Items.bone : (i == 3 ? (flag ? null : Items.rotten_flesh) : Items.leather);
	}

	/**+
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		this.openHorseMouth();
		if (this.rand.nextInt(3) == 0) {
			this.makeHorseRear();
		}

		int i = this.getHorseType();
		return i == 3 ? "mob.horse.zombie.hit"
				: (i == 4 ? "mob.horse.skeleton.hit" : (i != 1 && i != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit"));
	}

	public boolean isHorseSaddled() {
		return this.getHorseWatchableBoolean(4);
	}

	/**+
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		this.openHorseMouth();
		if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked()) {
			this.makeHorseRear();
		}

		int i = this.getHorseType();
		return i == 3 ? "mob.horse.zombie.idle"
				: (i == 4 ? "mob.horse.skeleton.idle"
						: (i != 1 && i != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle"));
	}

	protected String getAngrySoundName() {
		this.openHorseMouth();
		this.makeHorseRear();
		int i = this.getHorseType();
		return i != 3 && i != 4 ? (i != 1 && i != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
	}

	protected void playStepSound(BlockPos blockpos, Block block) {
		Block.SoundType block$soundtype = block.stepSound;
		if (this.worldObj.getBlockState(blockpos.up()).getBlock() == Blocks.snow_layer) {
			block$soundtype = Blocks.snow_layer.stepSound;
		}

		if (!block.getMaterial().isLiquid()) {
			int i = this.getHorseType();
			if (this.riddenByEntity != null && i != 1 && i != 2) {
				++this.gallopTime;
				if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
					this.playSound("mob.horse.gallop", block$soundtype.getVolume() * 0.15F,
							block$soundtype.getFrequency());
					if (i == 0 && this.rand.nextInt(10) == 0) {
						this.playSound("mob.horse.breathe", block$soundtype.getVolume() * 0.6F,
								block$soundtype.getFrequency());
					}
				} else if (this.gallopTime <= 5) {
					this.playSound("mob.horse.wood", block$soundtype.getVolume() * 0.15F,
							block$soundtype.getFrequency());
				}
			} else if (block$soundtype == Block.soundTypeWood) {
				this.playSound("mob.horse.wood", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
			} else {
				this.playSound("mob.horse.soft", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
			}
		}

	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(horseJumpStrength);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D);
	}

	/**+
	 * Will return how many at most can spawn in a chunk at once.
	 */
	public int getMaxSpawnedInChunk() {
		return 6;
	}

	public int getMaxTemper() {
		return 100;
	}

	/**+
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.8F;
	}

	/**+
	 * Get number of ticks, at least during which the living entity
	 * will be silent.
	 */
	public int getTalkInterval() {
		return 400;
	}

	public boolean func_110239_cn() {
		return this.getHorseType() == 0 || this.getHorseArmorIndexSynced() > 0;
	}

	private void resetTexturePrefix() {
		this.texturePrefix = null;
	}

	public boolean func_175507_cI() {
		return this.field_175508_bO;
	}

	private void setHorseTexturePaths() {
		this.texturePrefix = "horse/";
		this.horseTexturesArray[0] = null;
		this.horseTexturesArray[1] = null;
		this.horseTexturesArray[2] = null;
		int i = this.getHorseType();
		int j = this.getHorseVariant();
		if (i == 0) {
			int k = j & 255;
			int l = (j & '\uff00') >> 8;
			if (k >= horseTextures.length) {
				this.field_175508_bO = false;
				return;
			}

			this.horseTexturesArray[0] = horseTextures[k];
			this.texturePrefix = this.texturePrefix + HORSE_TEXTURES_ABBR[k];
			if (l >= horseMarkingTextures.length) {
				this.field_175508_bO = false;
				return;
			}

			this.horseTexturesArray[1] = horseMarkingTextures[l];
			this.texturePrefix = this.texturePrefix + HORSE_MARKING_TEXTURES_ABBR[l];
		} else {
			this.horseTexturesArray[0] = "";
			this.texturePrefix = this.texturePrefix + "_" + i + "_";
		}

		int i1 = this.getHorseArmorIndexSynced();
		if (i1 >= horseArmorTextures.length) {
			this.field_175508_bO = false;
		} else {
			this.horseTexturesArray[2] = horseArmorTextures[i1];
			this.texturePrefix = this.texturePrefix + HORSE_ARMOR_TEXTURES_ABBR[i1];
			this.field_175508_bO = true;
		}
	}

	public String getHorseTexture() {
		if (this.texturePrefix == null) {
			this.setHorseTexturePaths();
		}

		return this.texturePrefix;
	}

	public String[] getVariantTexturePaths() {
		if (this.texturePrefix == null) {
			this.setHorseTexturePaths();
		}

		return this.horseTexturesArray;
	}

	public void openGUI(EntityPlayer playerEntity) {

	}

	/**+
	 * Called when a player interacts with a mob. e.g. gets milk
	 * from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.spawn_egg) {
			return super.interact(entityplayer);
		} else if (!this.isTame() && this.isUndead()) {
			return false;
		} else if (this.isTame() && this.isAdultHorse() && entityplayer.isSneaking()) {
			this.openGUI(entityplayer);
			return true;
		} else if (this.func_110253_bW() && this.riddenByEntity != null) {
			return super.interact(entityplayer);
		} else {
			if (itemstack != null) {
				boolean flag = false;
				if (this.canWearArmor()) {
					byte b0 = -1;
					if (itemstack.getItem() == Items.iron_horse_armor) {
						b0 = 1;
					} else if (itemstack.getItem() == Items.golden_horse_armor) {
						b0 = 2;
					} else if (itemstack.getItem() == Items.diamond_horse_armor) {
						b0 = 3;
					}

					if (b0 >= 0) {
						if (!this.isTame()) {
							this.makeHorseRearWithSound();
							return true;
						}

						this.openGUI(entityplayer);
						return true;
					}
				}

				if (!flag && !this.isUndead()) {
					float f = 0.0F;
					short short1 = 0;
					byte b1 = 0;
					if (itemstack.getItem() == Items.wheat) {
						f = 2.0F;
						short1 = 20;
						b1 = 3;
					} else if (itemstack.getItem() == Items.sugar) {
						f = 1.0F;
						short1 = 30;
						b1 = 3;
					} else if (Block.getBlockFromItem(itemstack.getItem()) == Blocks.hay_block) {
						f = 20.0F;
						short1 = 180;
					} else if (itemstack.getItem() == Items.apple) {
						f = 3.0F;
						short1 = 60;
						b1 = 3;
					} else if (itemstack.getItem() == Items.golden_carrot) {
						f = 4.0F;
						short1 = 60;
						b1 = 5;
						if (this.isTame() && this.getGrowingAge() == 0) {
							flag = true;
							this.setInLove(entityplayer);
						}
					} else if (itemstack.getItem() == Items.golden_apple) {
						f = 10.0F;
						short1 = 240;
						b1 = 10;
						if (this.isTame() && this.getGrowingAge() == 0) {
							flag = true;
							this.setInLove(entityplayer);
						}
					}

					if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
						this.heal(f);
						flag = true;
					}

					if (!this.isAdultHorse() && short1 > 0) {
						this.addGrowth(short1);
						flag = true;
					}

					if (b1 > 0 && (flag || !this.isTame()) && b1 < this.getMaxTemper()) {
						flag = true;
						this.increaseTemper(b1);
					}

					if (flag) {
						this.func_110266_cB();
					}
				}

				if (!this.isTame() && !flag) {
					if (itemstack != null && itemstack.interactWithEntity(entityplayer, this)) {
						return true;
					}

					this.makeHorseRearWithSound();
					return true;
				}

				if (!flag && this.canCarryChest() && !this.isChested()
						&& itemstack.getItem() == Item.getItemFromBlock(Blocks.chest)) {
					this.setChested(true);
					this.playSound("mob.chickenplop", 1.0F,
							(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					flag = true;
					this.initHorseChest();
				}

				if (!flag && this.func_110253_bW() && !this.isHorseSaddled() && itemstack.getItem() == Items.saddle) {
					this.openGUI(entityplayer);
					return true;
				}

				if (flag) {
					if (!entityplayer.capabilities.isCreativeMode && --itemstack.stackSize == 0) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,
								(ItemStack) null);
					}

					return true;
				}
			}

			if (this.func_110253_bW() && this.riddenByEntity == null) {
				if (itemstack != null && itemstack.interactWithEntity(entityplayer, this)) {
					return true;
				} else {
					this.mountTo(entityplayer);
					return true;
				}
			} else {
				return super.interact(entityplayer);
			}
		}
	}

	private void mountTo(EntityPlayer player) {
		player.rotationYaw = this.rotationYaw;
		player.rotationPitch = this.rotationPitch;
		this.setEatingHaystack(false);
		this.setRearing(false);

	}

	/**+
	 * Return true if the horse entity can wear an armor
	 */
	public boolean canWearArmor() {
		return this.getHorseType() == 0;
	}

	/**+
	 * Return true if the horse entity can carry a chest.
	 */
	public boolean canCarryChest() {
		int i = this.getHorseType();
		return i == 2 || i == 1;
	}

	/**+
	 * Dead and sleeping entities cannot move
	 */
	protected boolean isMovementBlocked() {
		return this.riddenByEntity != null && this.isHorseSaddled() ? true
				: this.isEatingHaystack() || this.isRearing();
	}

	/**+
	 * Used to know if the horse can be leashed, if he can mate, or
	 * if we can interact with him
	 */
	public boolean isUndead() {
		int i = this.getHorseType();
		return i == 3 || i == 4;
	}

	/**+
	 * Return true if the horse entity is sterile (Undead || Mule)
	 */
	public boolean isSterile() {
		return this.isUndead() || this.getHorseType() == 2;
	}

	/**+
	 * Checks if the parameter is an item which this animal can be
	 * fed to breed it (wheat, carrots or seeds depending on the
	 * animal type)
	 */
	public boolean isBreedingItem(ItemStack var1) {
		return false;
	}

	private void func_110210_cH() {
		this.field_110278_bp = 1;
	}

	/**+
	 * Called frequently so the entity can update its state every
	 * tick as required. For example, zombies and skeletons use this
	 * to react to sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		if (this.rand.nextInt(200) == 0) {
			this.func_110210_cH();
		}

		super.onLivingUpdate();
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		super.onUpdate();
		if (this.dataWatcher.hasObjectChanged()) {
			this.dataWatcher.func_111144_e();
			this.resetTexturePrefix();
		}

		if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
			this.openMouthCounter = 0;
			this.setHorseWatchableBoolean(128, false);
		}

		if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
			this.field_110278_bp = 0;
		}

		if (this.field_110279_bq > 0) {
			++this.field_110279_bq;
			if (this.field_110279_bq > 300) {
				this.field_110279_bq = 0;
			}
		}

		this.prevHeadLean = this.headLean;
		if (this.isEatingHaystack()) {
			this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;
			if (this.headLean > 1.0F) {
				this.headLean = 1.0F;
			}
		} else {
			this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
			if (this.headLean < 0.0F) {
				this.headLean = 0.0F;
			}
		}

		this.prevRearingAmount = this.rearingAmount;
		if (this.isRearing()) {
			this.prevHeadLean = this.headLean = 0.0F;
			this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
			if (this.rearingAmount > 1.0F) {
				this.rearingAmount = 1.0F;
			}
		} else {
			this.field_110294_bI = false;
			this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount
					- this.rearingAmount) * 0.6F - 0.05F;
			if (this.rearingAmount < 0.0F) {
				this.rearingAmount = 0.0F;
			}
		}

		this.prevMouthOpenness = this.mouthOpenness;
		if (this.getHorseWatchableBoolean(128)) {
			this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;
			if (this.mouthOpenness > 1.0F) {
				this.mouthOpenness = 1.0F;
			}
		} else {
			this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
			if (this.mouthOpenness < 0.0F) {
				this.mouthOpenness = 0.0F;
			}
		}

	}

	private void openHorseMouth() {

	}

	/**+
	 * Return true if the horse entity ready to mate. (no rider, not
	 * riding, tame, adult, not steril...)
	 */
	private boolean canMate() {
		return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse()
				&& !this.isSterile() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
	}

	public void setEating(boolean flag) {
		this.setHorseWatchableBoolean(32, flag);
	}

	public void setEatingHaystack(boolean parFlag) {
		this.setEating(parFlag);
	}

	public void setRearing(boolean rearing) {
		if (rearing) {
			this.setEatingHaystack(false);
		}

		this.setHorseWatchableBoolean(64, rearing);
	}

	private void makeHorseRear() {

	}

	public void makeHorseRearWithSound() {
		this.makeHorseRear();
		String s = this.getAngrySoundName();
		if (s != null) {
			this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
		}

	}

	public void dropChestItems() {
		this.dropItemsInChest(this, this.horseChest);
		this.dropChests();
	}

	private void dropItemsInChest(Entity entityIn, AnimalChest animalChestIn) {

	}

	public boolean setTamedBy(EntityPlayer player) {
		this.setOwnerId(player.getUniqueID().toString());
		this.setHorseTamed(true);
		return true;
	}

	/**+
	 * Moves the entity based on the specified heading. Args:
	 * strafe, forward
	 */
	public void moveEntityWithHeading(float f, float f1) {
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.isHorseSaddled()) {
			this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
			this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			f = ((EntityLivingBase) this.riddenByEntity).moveStrafing * 0.5F;
			f1 = ((EntityLivingBase) this.riddenByEntity).moveForward;
			if (f1 <= 0.0F) {
				f1 *= 0.25F;
				this.gallopTime = 0;
			}

			if (this.onGround && this.jumpPower == 0.0F && this.isRearing() && !this.field_110294_bI) {
				f = 0.0F;
				f1 = 0.0F;
			}

			if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround) {
				this.motionY = this.getHorseJumpStrength() * (double) this.jumpPower;
				if (this.isPotionActive(Potion.jump)) {
					this.motionY += (double) ((float) (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1)
							* 0.1F);
				}

				this.setHorseJumping(true);
				this.isAirBorne = true;
				if (f1 > 0.0F) {
					float f2 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F);
					float f3 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
					this.motionX += (double) (-0.4F * f2 * this.jumpPower);
					this.motionZ += (double) (0.4F * f3 * this.jumpPower);
					this.playSound("mob.horse.jump", 0.4F, 1.0F);
				}

				this.jumpPower = 0.0F;
			}

			this.stepHeight = 1.0F;
			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

			if (this.onGround) {
				this.jumpPower = 0.0F;
				this.setHorseJumping(false);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
			if (f4 > 1.0F) {
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		} else {
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(f, f1);
		}
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("EatingHaystack", this.isEatingHaystack());
		nbttagcompound.setBoolean("ChestedHorse", this.isChested());
		nbttagcompound.setBoolean("HasReproduced", this.getHasReproduced());
		nbttagcompound.setBoolean("Bred", this.isBreeding());
		nbttagcompound.setInteger("Type", this.getHorseType());
		nbttagcompound.setInteger("Variant", this.getHorseVariant());
		nbttagcompound.setInteger("Temper", this.getTemper());
		nbttagcompound.setBoolean("Tame", this.isTame());
		nbttagcompound.setString("OwnerUUID", this.getOwnerId());
		if (this.isChested()) {
			NBTTagList nbttaglist = new NBTTagList();

			for (int i = 2; i < this.horseChest.getSizeInventory(); ++i) {
				ItemStack itemstack = this.horseChest.getStackInSlot(i);
				if (itemstack != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					itemstack.writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}

			nbttagcompound.setTag("Items", nbttaglist);
		}

		if (this.horseChest.getStackInSlot(1) != null) {
			nbttagcompound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
		}

		if (this.horseChest.getStackInSlot(0) != null) {
			nbttagcompound.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
		}

	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		this.setEatingHaystack(nbttagcompound.getBoolean("EatingHaystack"));
		this.setBreeding(nbttagcompound.getBoolean("Bred"));
		this.setChested(nbttagcompound.getBoolean("ChestedHorse"));
		this.setHasReproduced(nbttagcompound.getBoolean("HasReproduced"));
		this.setHorseType(nbttagcompound.getInteger("Type"));
		this.setHorseVariant(nbttagcompound.getInteger("Variant"));
		this.setTemper(nbttagcompound.getInteger("Temper"));
		this.setHorseTamed(nbttagcompound.getBoolean("Tame"));
		String s = nbttagcompound.getString("OwnerUUID");
		if (s.length() > 0) {
			this.setOwnerId(s);
		}

		IAttributeInstance iattributeinstance = this.getAttributeMap().getAttributeInstanceByName("Speed");
		if (iattributeinstance != null) {
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
					.setBaseValue(iattributeinstance.getBaseValue() * 0.25D);
		}

		if (this.isChested()) {
			NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
			this.initHorseChest();

			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound1.getByte("Slot") & 255;
				if (j >= 2 && j < this.horseChest.getSizeInventory()) {
					this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}

		if (nbttagcompound.hasKey("ArmorItem", 10)) {
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("ArmorItem"));
			if (itemstack != null && isArmorItem(itemstack.getItem())) {
				this.horseChest.setInventorySlotContents(1, itemstack);
			}
		}

		if (nbttagcompound.hasKey("SaddleItem", 10)) {
			ItemStack itemstack1 = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("SaddleItem"));
			if (itemstack1 != null && itemstack1.getItem() == Items.saddle) {
				this.horseChest.setInventorySlotContents(0, itemstack1);
			}
		} else if (nbttagcompound.getBoolean("Saddle")) {
			this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
		}

		this.updateHorseSlots();
	}

	/**+
	 * Returns true if the mob is currently able to mate with the
	 * specified mob.
	 */
	public boolean canMateWith(EntityAnimal entityanimal) {
		if (entityanimal == this) {
			return false;
		} else if (entityanimal.getClass() != this.getClass()) {
			return false;
		} else {
			EntityHorse entityhorse = (EntityHorse) entityanimal;
			if (this.canMate() && entityhorse.canMate()) {
				int i = this.getHorseType();
				int j = entityhorse.getHorseType();
				return i == j || i == 0 && j == 1 || i == 1 && j == 0;
			} else {
				return false;
			}
		}
	}

	public EntityAgeable createChild(EntityAgeable entityageable) {
		EntityHorse entityhorse = (EntityHorse) entityageable;
		EntityHorse entityhorse1 = new EntityHorse(this.worldObj);
		int i = this.getHorseType();
		int j = entityhorse.getHorseType();
		int k = 0;
		if (i == j) {
			k = i;
		} else if (i == 0 && j == 1 || i == 1 && j == 0) {
			k = 2;
		}

		if (k == 0) {
			int i1 = this.rand.nextInt(9);
			int l;
			if (i1 < 4) {
				l = this.getHorseVariant() & 255;
			} else if (i1 < 8) {
				l = entityhorse.getHorseVariant() & 255;
			} else {
				l = this.rand.nextInt(7);
			}

			int j1 = this.rand.nextInt(5);
			if (j1 < 2) {
				l = l | this.getHorseVariant() & '\uff00';
			} else if (j1 < 4) {
				l = l | entityhorse.getHorseVariant() & '\uff00';
			} else {
				l = l | this.rand.nextInt(5) << 8 & '\uff00';
			}

			entityhorse1.setHorseVariant(l);
		}

		entityhorse1.setHorseType(k);
		double d1 = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()
				+ entityageable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()
				+ (double) this.getModifiedMaxHealth();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(d1 / 3.0D);
		double d2 = this.getEntityAttribute(horseJumpStrength).getBaseValue()
				+ entityageable.getEntityAttribute(horseJumpStrength).getBaseValue() + this.getModifiedJumpStrength();
		entityhorse1.getEntityAttribute(horseJumpStrength).setBaseValue(d2 / 3.0D);
		double d0 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue()
				+ entityageable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue()
				+ this.getModifiedMovementSpeed();
		entityhorse1.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(d0 / 3.0D);
		return entityhorse1;
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
		int i = 0;
		int j = 0;
		if (ientitylivingdata instanceof EntityHorse.GroupData) {
			i = ((EntityHorse.GroupData) ientitylivingdata).horseType;
			j = ((EntityHorse.GroupData) ientitylivingdata).horseVariant & 255 | this.rand.nextInt(5) << 8;
		} else {
			if (this.rand.nextInt(10) == 0) {
				i = 1;
			} else {
				int k = this.rand.nextInt(7);
				int l = this.rand.nextInt(5);
				i = 0;
				j = k | l << 8;
			}

			ientitylivingdata = new EntityHorse.GroupData(i, j);
		}

		this.setHorseType(i);
		this.setHorseVariant(j);
		if (this.rand.nextInt(5) == 0) {
			this.setGrowingAge(-24000);
		}

		if (i != 4 && i != 3) {
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
					.setBaseValue((double) this.getModifiedMaxHealth());
			if (i == 0) {
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
						.setBaseValue(this.getModifiedMovementSpeed());
			} else {
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776D);
			}
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
		}

		if (i != 2 && i != 1) {
			this.getEntityAttribute(horseJumpStrength).setBaseValue(this.getModifiedJumpStrength());
		} else {
			this.getEntityAttribute(horseJumpStrength).setBaseValue(0.5D);
		}

		this.setHealth(this.getMaxHealth());
		return ientitylivingdata;
	}

	public float getGrassEatingAmount(float parFloat1) {
		return this.prevHeadLean + (this.headLean - this.prevHeadLean) * parFloat1;
	}

	public float getRearingAmount(float parFloat1) {
		return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * parFloat1;
	}

	public float getMouthOpennessAngle(float parFloat1) {
		return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * parFloat1;
	}

	public void setJumpPower(int jumpPowerIn) {
		if (this.isHorseSaddled()) {
			if (jumpPowerIn < 0) {
				jumpPowerIn = 0;
			} else {
				this.field_110294_bI = true;
				this.makeHorseRear();
			}

			if (jumpPowerIn >= 90) {
				this.jumpPower = 1.0F;
			} else {
				this.jumpPower = 0.4F + 0.4F * (float) jumpPowerIn / 90.0F;
			}
		}

	}

	/**+
	 * "Spawns particles for the horse entity. par1 tells whether to
	 * spawn hearts. If it is false, it spawns smoke."
	 */
	protected void spawnHorseParticles(boolean parFlag) {
		EnumParticleTypes enumparticletypes = parFlag ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;

		for (int i = 0; i < 7; ++i) {
			double d0 = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.worldObj.spawnParticle(enumparticletypes,
					this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
					this.posY + 0.5D + (double) (this.rand.nextFloat() * this.height),
					this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2,
					new int[0]);
		}

	}

	public void handleStatusUpdate(byte b0) {
		if (b0 == 7) {
			this.spawnHorseParticles(true);
		} else if (b0 == 6) {
			this.spawnHorseParticles(false);
		} else {
			super.handleStatusUpdate(b0);
		}

	}

	public void updateRiderPosition() {
		super.updateRiderPosition();
		if (this.prevRearingAmount > 0.0F) {
			float f = MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F);
			float f1 = MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F);
			float f2 = 0.7F * this.prevRearingAmount;
			float f3 = 0.15F * this.prevRearingAmount;
			this.riddenByEntity.setPosition(this.posX + (double) (f2 * f),
					this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (double) f3,
					this.posZ - (double) (f2 * f1));
			if (this.riddenByEntity instanceof EntityLivingBase) {
				((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
			}
		}

	}

	/**+
	 * Returns randomized max health
	 */
	private float getModifiedMaxHealth() {
		return 15.0F + (float) this.rand.nextInt(8) + (float) this.rand.nextInt(9);
	}

	/**+
	 * Returns randomized jump strength
	 */
	private double getModifiedJumpStrength() {
		return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D
				+ this.rand.nextDouble() * 0.2D;
	}

	/**+
	 * Returns randomized movement speed
	 */
	private double getModifiedMovementSpeed() {
		return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D
				+ this.rand.nextDouble() * 0.3D) * 0.25D;
	}

	/**+
	 * Returns true if given item is horse armor
	 */
	public static boolean isArmorItem(Item parItem) {
		return parItem == Items.iron_horse_armor || parItem == Items.golden_horse_armor
				|| parItem == Items.diamond_horse_armor;
	}

	/**+
	 * returns true if this entity is by a ladder, false otherwise
	 */
	public boolean isOnLadder() {
		return false;
	}

	public float getEyeHeight() {
		return this.height;
	}

	public boolean replaceItemInInventory(int i, ItemStack itemstack) {
		if (i == 499 && this.canCarryChest()) {
			if (itemstack == null && this.isChested()) {
				this.setChested(false);
				this.initHorseChest();
				return true;
			}

			if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.chest) && !this.isChested()) {
				this.setChested(true);
				this.initHorseChest();
				return true;
			}
		}

		int j = i - 400;
		if (j >= 0 && j < 2 && j < this.horseChest.getSizeInventory()) {
			if (j == 0 && itemstack != null && itemstack.getItem() != Items.saddle) {
				return false;
			} else if (j != 1 || (itemstack == null || isArmorItem(itemstack.getItem())) && this.canWearArmor()) {
				this.horseChest.setInventorySlotContents(j, itemstack);
				this.updateHorseSlots();
				return true;
			} else {
				return false;
			}
		} else {
			int k = i - 500 + 2;
			if (k >= 2 && k < this.horseChest.getSizeInventory()) {
				this.horseChest.setInventorySlotContents(k, itemstack);
				return true;
			} else {
				return false;
			}
		}
	}

	public static class GroupData implements IEntityLivingData {
		public int horseType;
		public int horseVariant;

		public GroupData(int type, int variant) {
			this.horseType = type;
			this.horseVariant = variant;
		}
	}
}