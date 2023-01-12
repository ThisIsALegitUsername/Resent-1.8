package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

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
public class NpcMerchant implements IMerchant {
	private InventoryMerchant theMerchantInventory;
	private EntityPlayer customer;
	private MerchantRecipeList recipeList;
	private IChatComponent field_175548_d;

	public NpcMerchant(EntityPlayer parEntityPlayer, IChatComponent parIChatComponent) {
		this.customer = parEntityPlayer;
		this.field_175548_d = parIChatComponent;
		this.theMerchantInventory = new InventoryMerchant(parEntityPlayer, this);
	}

	public EntityPlayer getCustomer() {
		return this.customer;
	}

	public void setCustomer(EntityPlayer var1) {
	}

	public MerchantRecipeList getRecipes(EntityPlayer var1) {
		return this.recipeList;
	}

	public void setRecipes(MerchantRecipeList merchantrecipelist) {
		this.recipeList = merchantrecipelist;
	}

	public void useRecipe(MerchantRecipe merchantrecipe) {
		merchantrecipe.incrementToolUses();
	}

	/**+
	 * Notifies the merchant of a possible merchantrecipe being
	 * fulfilled or not. Usually, this is just a sound byte being
	 * played depending if the suggested itemstack is not null.
	 */
	public void verifySellingItem(ItemStack var1) {
	}

	/**+
	 * Get the formatted ChatComponent that will be used for the
	 * sender's username in chat
	 */
	public IChatComponent getDisplayName() {
		return (IChatComponent) (this.field_175548_d != null ? this.field_175548_d
				: new ChatComponentTranslation("entity.Villager.name", new Object[0]));
	}
}