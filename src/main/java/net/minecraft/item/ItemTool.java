package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
public class ItemTool extends Item {
	private Set<Block> effectiveBlocks;
	protected float efficiencyOnProperMaterial = 4.0F;
	private float damageVsEntity;
	protected Item.ToolMaterial toolMaterial;

	protected ItemTool(float attackDamage, Item.ToolMaterial material, Set<Block> effectiveBlocks) {
		this.toolMaterial = material;
		this.effectiveBlocks = effectiveBlocks;
		this.maxStackSize = 1;
		this.setMaxDamage(material.getMaxUses());
		this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
		this.damageVsEntity = attackDamage + material.getDamageVsEntity();
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	public float getStrVsBlock(ItemStack var1, Block block) {
		return this.effectiveBlocks.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
	}

	/**+
	 * Current implementations of this method in child classes do
	 * not use the entry argument beside ev. They just raise the
	 * damage on the stack.
	 */
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase var2, EntityLivingBase entitylivingbase) {
		itemstack.damageItem(2, entitylivingbase);
		return true;
	}

	/**+
	 * Called when a Block is destroyed using this Item. Return true
	 * to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, BlockPos blockpos,
			EntityLivingBase entitylivingbase) {
		if ((double) block.getBlockHardness(world, blockpos) != 0.0D) {
			itemstack.damageItem(1, entitylivingbase);
		}

		return true;
	}

	/**+
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D() {
		return true;
	}

	public Item.ToolMaterial getToolMaterial() {
		return this.toolMaterial;
	}

	/**+
	 * Return the enchantability factor of the item, most of the
	 * time is based on material.
	 */
	public int getItemEnchantability() {
		return this.toolMaterial.getEnchantability();
	}

	/**+
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName() {
		return this.toolMaterial.toString();
	}

	/**+
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack itemstack, ItemStack itemstack1) {
		return this.toolMaterial.getRepairItem() == itemstack1.getItem() ? true
				: super.getIsRepairable(itemstack, itemstack1);
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Tool modifier", (double) this.damageVsEntity, 0));
		return multimap;
	}
}