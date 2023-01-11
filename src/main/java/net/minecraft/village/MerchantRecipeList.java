package net.minecraft.village;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;

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
public class MerchantRecipeList extends ArrayList<MerchantRecipe> {
	public MerchantRecipeList() {
	}

	public MerchantRecipeList(NBTTagCompound compound) {
		this.readRecipiesFromTags(compound);
	}

	/**+
	 * can par1,par2 be used to in crafting recipe par3
	 */
	public MerchantRecipe canRecipeBeUsed(ItemStack parItemStack, ItemStack parItemStack2, int parInt1) {
		if (parInt1 > 0 && parInt1 < this.size()) {
			MerchantRecipe merchantrecipe1 = (MerchantRecipe) this.get(parInt1);
			return !this.func_181078_a(parItemStack, merchantrecipe1.getItemToBuy())
					|| (parItemStack2 != null || merchantrecipe1.hasSecondItemToBuy())
							&& (!merchantrecipe1.hasSecondItemToBuy()
									|| !this.func_181078_a(parItemStack2, merchantrecipe1.getSecondItemToBuy()))
					|| parItemStack.stackSize < merchantrecipe1.getItemToBuy().stackSize
					|| merchantrecipe1.hasSecondItemToBuy()
							&& parItemStack2.stackSize < merchantrecipe1.getSecondItemToBuy().stackSize ? null
									: merchantrecipe1;
		} else {
			for (int i = 0; i < this.size(); ++i) {
				MerchantRecipe merchantrecipe = (MerchantRecipe) this.get(i);
				if (this.func_181078_a(parItemStack, merchantrecipe.getItemToBuy())
						&& parItemStack.stackSize >= merchantrecipe.getItemToBuy().stackSize
						&& (!merchantrecipe.hasSecondItemToBuy() && parItemStack2 == null
								|| merchantrecipe.hasSecondItemToBuy()
										&& this.func_181078_a(parItemStack2, merchantrecipe.getSecondItemToBuy())
										&& parItemStack2.stackSize >= merchantrecipe.getSecondItemToBuy().stackSize)) {
					return merchantrecipe;
				}
			}

			return null;
		}
	}

	private boolean func_181078_a(ItemStack parItemStack, ItemStack parItemStack2) {
		return ItemStack.areItemsEqual(parItemStack, parItemStack2)
				&& (!parItemStack2.hasTagCompound() || parItemStack.hasTagCompound()
						&& NBTUtil.func_181123_a(parItemStack2.getTagCompound(), parItemStack.getTagCompound(), false));
	}

	public void writeToBuf(PacketBuffer buffer) {
		buffer.writeByte((byte) (this.size() & 255));

		for (int i = 0; i < this.size(); ++i) {
			MerchantRecipe merchantrecipe = (MerchantRecipe) this.get(i);
			buffer.writeItemStackToBuffer(merchantrecipe.getItemToBuy());
			buffer.writeItemStackToBuffer(merchantrecipe.getItemToSell());
			ItemStack itemstack = merchantrecipe.getSecondItemToBuy();
			buffer.writeBoolean(itemstack != null);
			if (itemstack != null) {
				buffer.writeItemStackToBuffer(itemstack);
			}

			buffer.writeBoolean(merchantrecipe.isRecipeDisabled());
			buffer.writeInt(merchantrecipe.getToolUses());
			buffer.writeInt(merchantrecipe.getMaxTradeUses());
		}

	}

	public static MerchantRecipeList readFromBuf(PacketBuffer buffer) throws IOException {
		MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
		int i = buffer.readByte() & 255;

		for (int j = 0; j < i; ++j) {
			ItemStack itemstack = buffer.readItemStackFromBuffer();
			ItemStack itemstack1 = buffer.readItemStackFromBuffer();
			ItemStack itemstack2 = null;
			if (buffer.readBoolean()) {
				itemstack2 = buffer.readItemStackFromBuffer();
			}

			boolean flag = buffer.readBoolean();
			int k = buffer.readInt();
			int l = buffer.readInt();
			MerchantRecipe merchantrecipe = new MerchantRecipe(itemstack, itemstack2, itemstack1, k, l);
			if (flag) {
				merchantrecipe.compensateToolUses();
			}

			merchantrecipelist.add(merchantrecipe);
		}

		return merchantrecipelist;
	}

	public void readRecipiesFromTags(NBTTagCompound compound) {
		NBTTagList nbttaglist = compound.getTagList("Recipes", 10);

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			this.add(new MerchantRecipe(nbttagcompound));
		}

	}

	public NBTTagCompound getRecipiesAsTags() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.size(); ++i) {
			MerchantRecipe merchantrecipe = (MerchantRecipe) this.get(i);
			nbttaglist.appendTag(merchantrecipe.writeToTags());
		}

		nbttagcompound.setTag("Recipes", nbttaglist);
		return nbttagcompound;
	}
}