package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
public class EntityDamageSource extends DamageSource {
	protected Entity damageSourceEntity;
	/**+
	 * Whether this EntityDamageSource is from an entity wearing
	 * Thorns-enchanted armor.
	 */
	private boolean isThornsDamage = false;

	public EntityDamageSource(String parString1, Entity damageSourceEntityIn) {
		super(parString1);
		this.damageSourceEntity = damageSourceEntityIn;
	}

	/**+
	 * Sets this EntityDamageSource as originating from Thorns armor
	 */
	public EntityDamageSource setIsThornsDamage() {
		this.isThornsDamage = true;
		return this;
	}

	public boolean getIsThornsDamage() {
		return this.isThornsDamage;
	}

	public Entity getEntity() {
		return this.damageSourceEntity;
	}

	/**+
	 * Gets the death message that is displayed when the player dies
	 */
	public IChatComponent getDeathMessage(EntityLivingBase entitylivingbase) {
		ItemStack itemstack = this.damageSourceEntity instanceof EntityLivingBase
				? ((EntityLivingBase) this.damageSourceEntity).getHeldItem()
				: null;
		String s = "death.attack." + this.damageType;
		String s1 = s + ".item";
		return itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1)
				? new ChatComponentTranslation(s1,
						new Object[] { entitylivingbase.getDisplayName(), this.damageSourceEntity.getDisplayName(),
								itemstack.getChatComponent() })
				: new ChatComponentTranslation(s,
						new Object[] { entitylivingbase.getDisplayName(), this.damageSourceEntity.getDisplayName() });
	}

	/**+
	 * Return whether this damage source will have its damage amount
	 * scaled based on the current difficulty.
	 */
	public boolean isDifficultyScaled() {
		return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase
				&& !(this.damageSourceEntity instanceof EntityPlayer);
	}
}