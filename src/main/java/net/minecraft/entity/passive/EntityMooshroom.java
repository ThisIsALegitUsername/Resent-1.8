package net.minecraft.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
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
public class EntityMooshroom extends EntityCow {
	public EntityMooshroom(World worldIn) {
		super(worldIn);
		this.setSize(0.9F, 1.3F);
		this.spawnableBlock = Blocks.mycelium;
	}

	/**+
	 * Called when a player interacts with a mob. e.g. gets milk
	 * from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.bowl && this.getGrowingAge() >= 0) {
			if (itemstack.stackSize == 1) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,
						new ItemStack(Items.mushroom_stew));
				return true;
			}

			if (entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew))
					&& !entityplayer.capabilities.isCreativeMode) {
				entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
				return true;
			}
		}

		if (itemstack != null && itemstack.getItem() == Items.shears && this.getGrowingAge() >= 0) {
			this.setDead();
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX,
					this.posY + (double) (this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);

			return true;
		} else {
			return super.interact(entityplayer);
		}
	}

	public EntityMooshroom createChild(EntityAgeable var1) {
		return new EntityMooshroom(this.worldObj);
	}
}