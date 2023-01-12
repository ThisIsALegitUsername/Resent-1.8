package net.minecraft.entity.item;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
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
public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {
	/**+
	 * Whether this hopper minecart is being blocked by an activator
	 * rail.
	 */
	private boolean isBlocked = true;
	private int transferTicker = -1;
	private BlockPos field_174900_c = BlockPos.ORIGIN;

	public EntityMinecartHopper(World worldIn) {
		super(worldIn);
	}

	public EntityMinecartHopper(World worldIn, double parDouble1, double parDouble2, double parDouble3) {
		super(worldIn, parDouble1, parDouble2, parDouble3);
	}

	public EntityMinecart.EnumMinecartType getMinecartType() {
		return EntityMinecart.EnumMinecartType.HOPPER;
	}

	public IBlockState getDefaultDisplayTile() {
		return Blocks.hopper.getDefaultState();
	}

	public int getDefaultDisplayTileOffset() {
		return 1;
	}

	/**+
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return 5;
	}

	/**+
	 * First layer of player interaction
	 */
	public boolean interactFirst(EntityPlayer entityplayer) {
		return true;
	}

	/**+
	 * Called every tick the minecart is on an activator rail. Args:
	 * x, y, z, is the rail receiving power
	 */
	public void onActivatorRailPass(int var1, int var2, int var3, boolean flag) {
		boolean flag1 = !flag;
		if (flag1 != this.getBlocked()) {
			this.setBlocked(flag1);
		}

	}

	/**+
	 * Get whether this hopper minecart is being blocked by an
	 * activator rail.
	 */
	public boolean getBlocked() {
		return this.isBlocked;
	}

	/**+
	 * Set whether this hopper minecart is being blocked by an
	 * activator rail.
	 */
	public void setBlocked(boolean parFlag) {
		this.isBlocked = parFlag;
	}

	/**+
	 * Returns the worldObj for this tileEntity.
	 */
	public World getWorld() {
		return this.worldObj;
	}

	/**+
	 * Gets the world X position for this hopper entity.
	 */
	public double getXPos() {
		return this.posX;
	}

	/**+
	 * Gets the world Y position for this hopper entity.
	 */
	public double getYPos() {
		return this.posY + 0.5D;
	}

	/**+
	 * Gets the world Z position for this hopper entity.
	 */
	public double getZPos() {
		return this.posZ;
	}

	public boolean func_96112_aD() {
		if (TileEntityHopper.captureDroppedItems(this)) {
			return true;
		} else {
			List list = this.worldObj.getEntitiesWithinAABB(EntityItem.class,
					this.getEntityBoundingBox().expand(0.25D, 0.0D, 0.25D), EntitySelectors.selectAnything);
			if (list.size() > 0) {
				TileEntityHopper.putDropInInventoryAllSlots(this, (EntityItem) list.get(0));
			}

			return false;
		}
	}

	public void killMinecart(DamageSource damagesource) {
		super.killMinecart(damagesource);
		if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
			this.dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), 1, 0.0F);
		}

	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("TransferCooldown", this.transferTicker);
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		this.transferTicker = nbttagcompound.getInteger("TransferCooldown");
	}

	/**+
	 * Sets the transfer ticker, used to determine the delay between
	 * transfers.
	 */
	public void setTransferTicker(int parInt1) {
		this.transferTicker = parInt1;
	}

	/**+
	 * Returns whether the hopper cart can currently transfer an
	 * item.
	 */
	public boolean canTransfer() {
		return this.transferTicker > 0;
	}

	public String getGuiID() {
		return "minecraft:hopper";
	}

	public Container createContainer(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
		return new ContainerHopper(inventoryplayer, this, entityplayer);
	}
}