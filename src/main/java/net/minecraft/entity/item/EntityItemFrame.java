package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

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
public class EntityItemFrame extends EntityHanging {
	/**+
	 * Chance for this item frame's item to drop from the frame.
	 */
	private float itemDropChance = 1.0F;

	public EntityItemFrame(World worldIn) {
		super(worldIn);
	}

	public EntityItemFrame(World worldIn, BlockPos parBlockPos, EnumFacing parEnumFacing) {
		super(worldIn, parBlockPos);
		this.updateFacingWithBoundingBox(parEnumFacing);
	}

	protected void entityInit() {
		this.getDataWatcher().addObjectByDataType(8, 5);
		this.getDataWatcher().addObject(9, Byte.valueOf((byte) 0));
	}

	public float getCollisionBorderSize() {
		return 0.0F;
	}

	/**+
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (this.isEntityInvulnerable(damagesource)) {
			return false;
		} else if (!damagesource.isExplosion() && this.getDisplayedItem() != null) {
			return true;
		} else {
			return super.attackEntityFrom(damagesource, f);
		}
	}

	public int getWidthPixels() {
		return 12;
	}

	public int getHeightPixels() {
		return 12;
	}

	/**+
	 * Checks if the entity is in range to render by using the past
	 * in distance and comparing it to its average edge length * 64
	 * * renderDistanceWeight Args: distance
	 */
	public boolean isInRangeToRenderDist(double d0) {
		double d1 = 16.0D;
		d1 = d1 * 64.0D * this.renderDistanceWeight;
		return d0 < d1 * d1;
	}

	/**+
	 * Called when this entity is broken. Entity parameter may be
	 * null.
	 */
	public void onBroken(Entity entity) {
		this.dropItemOrSelf(entity, true);
	}

	public void dropItemOrSelf(Entity parEntity, boolean parFlag) {
		if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
			ItemStack itemstack = this.getDisplayedItem();
			if (parEntity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) parEntity;
				if (entityplayer.capabilities.isCreativeMode) {
					this.removeFrameFromMap(itemstack);
					return;
				}
			}

			if (parFlag) {
				this.entityDropItem(new ItemStack(Items.item_frame), 0.0F);
			}

			if (itemstack != null && this.rand.nextFloat() < this.itemDropChance) {
				itemstack = itemstack.copy();
				this.removeFrameFromMap(itemstack);
				this.entityDropItem(itemstack, 0.0F);
			}

		}
	}

	/**+
	 * Removes the dot representing this frame's position from the
	 * map when the item frame is broken.
	 */
	private void removeFrameFromMap(ItemStack parItemStack) {
		if (parItemStack != null) {
			if (parItemStack.getItem() == Items.filled_map) {
				MapData mapdata = ((ItemMap) parItemStack.getItem()).getMapData(parItemStack, this.worldObj);
				mapdata.mapDecorations.remove("frame-" + this.getEntityId());
			}

			parItemStack.setItemFrame((EntityItemFrame) null);
		}
	}

	public ItemStack getDisplayedItem() {
		return this.getDataWatcher().getWatchableObjectItemStack(8);
	}

	public void setDisplayedItem(ItemStack parItemStack) {
		this.setDisplayedItemWithUpdate(parItemStack, true);
	}

	private void setDisplayedItemWithUpdate(ItemStack parItemStack, boolean parFlag) {
		if (parItemStack != null) {
			parItemStack = parItemStack.copy();
			parItemStack.stackSize = 1;
			parItemStack.setItemFrame(this);
		}

		this.getDataWatcher().updateObject(8, parItemStack);
		this.getDataWatcher().setObjectWatched(8);
		if (parFlag && this.hangingPosition != null) {
			this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
		}

	}

	/**+
	 * Return the rotation of the item currently on this frame.
	 */
	public int getRotation() {
		return this.getDataWatcher().getWatchableObjectByte(9);
	}

	public void setItemRotation(int parInt1) {
		this.func_174865_a(parInt1, true);
	}

	private void func_174865_a(int parInt1, boolean parFlag) {
		this.getDataWatcher().updateObject(9, Byte.valueOf((byte) (parInt1 % 8)));
		if (parFlag && this.hangingPosition != null) {
			this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
		}

	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		if (this.getDisplayedItem() != null) {
			nbttagcompound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
			nbttagcompound.setByte("ItemRotation", (byte) this.getRotation());
			nbttagcompound.setFloat("ItemDropChance", this.itemDropChance);
		}

		super.writeEntityToNBT(nbttagcompound);
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Item");
		if (nbttagcompound1 != null && !nbttagcompound1.hasNoTags()) {
			this.setDisplayedItemWithUpdate(ItemStack.loadItemStackFromNBT(nbttagcompound1), false);
			this.func_174865_a(nbttagcompound.getByte("ItemRotation"), false);
			if (nbttagcompound.hasKey("ItemDropChance", 99)) {
				this.itemDropChance = nbttagcompound.getFloat("ItemDropChance");
			}

			if (nbttagcompound.hasKey("Direction")) {
				this.func_174865_a(this.getRotation() * 2, false);
			}
		}

		super.readEntityFromNBT(nbttagcompound);
	}

	/**+
	 * First layer of player interaction
	 */
	public boolean interactFirst(EntityPlayer entityplayer) {
		return true;
	}

	public int func_174866_q() {
		return this.getDisplayedItem() == null ? 0 : this.getRotation() % 8 + 1;
	}
}