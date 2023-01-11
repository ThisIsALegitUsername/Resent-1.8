package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
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
public class ItemBucketMilk extends Item {
	public ItemBucketMilk() {
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	/**+
	 * Called when the player finishes using this Item (E.g.
	 * finishes eating.). Not called when the player stops using the
	 * Item before the action is complete.
	 */
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (!playerIn.capabilities.isCreativeMode) {
			--stack.stackSize;
		}
		playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return stack.stackSize <= 0 ? new ItemStack(Items.bucket) : stack;
	}

	/**+
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	/**+
	 * returns the action that specifies what animation to play when
	 * the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
}