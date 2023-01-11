package net.minecraft.item;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
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
public class ItemSword extends Item {
	private float attackDamage;
	private final Item.ToolMaterial material;

	public ItemSword(Item.ToolMaterial material) {
		this.material = material;
		this.maxStackSize = 1;
		this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.attackDamage = 4.0F + material.getDamageVsEntity();
	}

	/**+
	 * Returns the amount of damage this item will deal. One heart
	 * of damage is equal to 2 damage points.
	 */
	public float getDamageVsEntity() {
		return this.material.getDamageVsEntity();
	}

	public float getStrVsBlock(ItemStack var1, Block block) {
		if (block == Blocks.web) {
			return 15.0F;
		} else {
			Material materialx = block.getMaterial();
			return materialx != Material.plants && materialx != Material.vine && materialx != Material.coral
					&& materialx != Material.leaves && materialx != Material.gourd ? 1.0F : 1.5F;
		}
	}

	/**+
	 * Current implementations of this method in child classes do
	 * not use the entry argument beside ev. They just raise the
	 * damage on the stack.
	 */
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase var2, EntityLivingBase entitylivingbase) {
		itemstack.damageItem(1, entitylivingbase);
		return true;
	}

	/**+
	 * Called when a Block is destroyed using this Item. Return true
	 * to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, BlockPos blockpos,
			EntityLivingBase entitylivingbase) {
		if ((double) block.getBlockHardness(world, blockpos) != 0.0D) {
			itemstack.damageItem(2, entitylivingbase);
		}

		return true;
	}

	/**+
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D() {
		return true;
	}

	/**+
	 * returns the action that specifies what animation to play when
	 * the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack var1) {
		return EnumAction.BLOCK;
	}

	/**+
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack var1) {
		return 72000;
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World var2, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	/**+
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(Block block) {
		return block == Blocks.web;
	}

	/**+
	 * Return the enchantability factor of the item, most of the
	 * time is based on material.
	 */
	public int getItemEnchantability() {
		return this.material.getEnchantability();
	}

	/**+
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName() {
		return this.material.toString();
	}

	/**+
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.material.getRepairItem() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Weapon modifier", (double) this.attackDamage, 0));
		return multimap;
	}
}