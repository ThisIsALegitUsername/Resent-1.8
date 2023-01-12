package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
public class EntityDamageSourceIndirect extends EntityDamageSource {
	private Entity indirectEntity;

	public EntityDamageSourceIndirect(String parString1, Entity parEntity, Entity indirectEntityIn) {
		super(parString1, parEntity);
		this.indirectEntity = indirectEntityIn;
	}

	public Entity getSourceOfDamage() {
		return this.damageSourceEntity;
	}

	public Entity getEntity() {
		return this.indirectEntity;
	}

	/**+
	 * Gets the death message that is displayed when the player dies
	 */
	public IChatComponent getDeathMessage(EntityLivingBase entitylivingbase) {
		IChatComponent ichatcomponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName()
				: this.indirectEntity.getDisplayName();
		ItemStack itemstack = this.indirectEntity instanceof EntityLivingBase
				? ((EntityLivingBase) this.indirectEntity).getHeldItem()
				: null;
		String s = "death.attack." + this.damageType;
		String s1 = s + ".item";
		return itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1)
				? new ChatComponentTranslation(s1,
						new Object[] { entitylivingbase.getDisplayName(), ichatcomponent,
								itemstack.getChatComponent() })
				: new ChatComponentTranslation(s, new Object[] { entitylivingbase.getDisplayName(), ichatcomponent });
	}
}