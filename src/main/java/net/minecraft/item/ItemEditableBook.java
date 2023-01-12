package net.minecraft.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
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
public class ItemEditableBook extends Item {
	public ItemEditableBook() {
		this.setMaxStackSize(1);
	}

	public static boolean validBookTagContents(NBTTagCompound nbt) {
		if (!ItemWritableBook.isNBTValid(nbt)) {
			return false;
		} else if (!nbt.hasKey("title", 8)) {
			return false;
		} else {
			String s = nbt.getString("title");
			return s != null && s.length() <= 32 ? nbt.hasKey("author", 8) : false;
		}
	}

	/**+
	 * Gets the generation of the book (how many times it has been
	 * cloned)
	 */
	public static int getGeneration(ItemStack book) {
		return book.getTagCompound().getInteger("generation");
	}

	public String getItemStackDisplayName(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			NBTTagCompound nbttagcompound = itemstack.getTagCompound();
			String s = nbttagcompound.getString("title");
			if (!StringUtils.isNullOrEmpty(s)) {
				return s;
			}
		}

		return super.getItemStackDisplayName(itemstack);
	}

	/**+
	 * allows items to add custom lines of information to the
	 * mouseover description
	 */
	public void addInformation(ItemStack itemstack, EntityPlayer var2, List<String> list, boolean var4) {
		if (itemstack.hasTagCompound()) {
			NBTTagCompound nbttagcompound = itemstack.getTagCompound();
			String s = nbttagcompound.getString("author");
			if (!StringUtils.isNullOrEmpty(s)) {
				list.add(EnumChatFormatting.GRAY
						+ StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] { s }));
			}

			list.add(EnumChatFormatting.GRAY
					+ StatCollector.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
		}

	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.displayGUIBook(itemstack);
		entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return itemstack;
	}

	private void resolveContents(ItemStack stack, EntityPlayer player) {
		if (stack != null && stack.getTagCompound() != null) {
			NBTTagCompound nbttagcompound = stack.getTagCompound();
			if (!nbttagcompound.getBoolean("resolved")) {
				nbttagcompound.setBoolean("resolved", true);
				if (validBookTagContents(nbttagcompound)) {
					NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);

					for (int i = 0; i < nbttaglist.tagCount(); ++i) {
						String s = nbttaglist.getStringTagAt(i);

						IChatComponent ichatcomponent;
						try {
							ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
							ichatcomponent = ChatComponentProcessor.processComponent(player, ichatcomponent, player);
						} catch (Exception var9) {
							ichatcomponent = new ChatComponentText(s);
						}

						nbttaglist.set(i, new NBTTagString(IChatComponent.Serializer.componentToJson(ichatcomponent)));
					}

					nbttagcompound.setTag("pages", nbttaglist);
				}
			}
		}
	}

	public boolean hasEffect(ItemStack var1) {
		return true;
	}
}