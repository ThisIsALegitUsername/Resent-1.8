package net.minecraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
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
public class ItemBanner extends ItemBlock {
	public ItemBanner() {
		super(Blocks.standing_banner);
		this.maxStackSize = 16;
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float var6, float var7, float var8) {
		if (enumfacing == EnumFacing.DOWN) {
			return false;
		} else if (!world.getBlockState(blockpos).getBlock().getMaterial().isSolid()) {
			return false;
		} else {
			blockpos = blockpos.offset(enumfacing);
			if (!entityplayer.canPlayerEdit(blockpos, enumfacing, itemstack)) {
				return false;
			} else if (!Blocks.standing_banner.canPlaceBlockAt(world, blockpos)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public String getItemStackDisplayName(ItemStack itemstack) {
		String s = "item.banner.";
		EnumDyeColor enumdyecolor = this.getBaseColor(itemstack);
		s = s + enumdyecolor.getUnlocalizedName() + ".name";
		return StatCollector.translateToLocal(s);
	}

	/**+
	 * allows items to add custom lines of information to the
	 * mouseover description
	 */
	public void addInformation(ItemStack itemstack, EntityPlayer var2, List<String> list, boolean var4) {
		NBTTagCompound nbttagcompound = itemstack.getSubCompound("BlockEntityTag", false);
		if (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) {
			NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);

			for (int i = 0; i < nbttaglist.tagCount() && i < 6; ++i) {
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound1.getInteger("Color"));
				TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = TileEntityBanner.EnumBannerPattern
						.getPatternByID(nbttagcompound1.getString("Pattern"));
				if (tileentitybanner$enumbannerpattern != null) {
					list.add(StatCollector
							.translateToLocal("item.banner." + tileentitybanner$enumbannerpattern.getPatternName() + "."
									+ enumdyecolor.getUnlocalizedName()));
				}
			}

		}
	}

	public int getColorFromItemStack(ItemStack itemstack, int i) {
		if (i == 0) {
			return 16777215;
		} else {
			EnumDyeColor enumdyecolor = this.getBaseColor(itemstack);
			return enumdyecolor.getMapColor().colorValue;
		}
	}

	/**+
	 * returns a list of items with the same ID, but different meta
	 * (eg: dye returns 16 items)
	 */
	public void getSubItems(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			TileEntityBanner.func_181020_a(nbttagcompound, enumdyecolor.getDyeDamage(), (NBTTagList) null);
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setTag("BlockEntityTag", nbttagcompound);
			ItemStack itemstack = new ItemStack(item, 1, enumdyecolor.getDyeDamage());
			itemstack.setTagCompound(nbttagcompound1);
			list.add(itemstack);
		}

	}

	/**+
	 * gets the CreativeTab this item is displayed on
	 */
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabDecorations;
	}

	private EnumDyeColor getBaseColor(ItemStack stack) {
		NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
		EnumDyeColor enumdyecolor = null;
		if (nbttagcompound != null && nbttagcompound.hasKey("Base")) {
			enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound.getInteger("Base"));
		} else {
			enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
		}

		return enumdyecolor;
	}
}