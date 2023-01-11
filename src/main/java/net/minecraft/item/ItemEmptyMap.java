package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
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
public class ItemEmptyMap extends ItemMapBase {
	protected ItemEmptyMap() {
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ItemStack itemstack1 = new ItemStack(Items.filled_map, 1, world.getUniqueDataId("map"));
		String s = "map_" + itemstack1.getMetadata();
		MapData mapdata = new MapData(s);
		world.setItemData(s, mapdata);
		mapdata.scale = 0;
		mapdata.calculateMapCenter(entityplayer.posX, entityplayer.posZ, mapdata.scale);
		mapdata.dimension = (byte) world.provider.getDimensionId();
		mapdata.markDirty();
		--itemstack.stackSize;
		if (itemstack.stackSize <= 0) {
			return itemstack1;
		} else {
			if (!entityplayer.inventory.addItemStackToInventory(itemstack1.copy())) {
				entityplayer.dropPlayerItemWithRandomChoice(itemstack1, false);
			}

			entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
			return itemstack;
		}
	}
}