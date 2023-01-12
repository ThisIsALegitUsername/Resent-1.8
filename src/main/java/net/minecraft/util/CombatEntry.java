package net.minecraft.util;

import net.minecraft.entity.EntityLivingBase;

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
public class CombatEntry {
	private final DamageSource damageSrc;
	private final int field_94567_b;
	private final float damage;
	private final float health;
	private final String field_94566_e;
	private final float fallDistance;

	public CombatEntry(DamageSource damageSrcIn, int parInt1, float healthAmount, float damageAmount, String parString1,
			float fallDistanceIn) {
		this.damageSrc = damageSrcIn;
		this.field_94567_b = parInt1;
		this.damage = damageAmount;
		this.health = healthAmount;
		this.field_94566_e = parString1;
		this.fallDistance = fallDistanceIn;
	}

	/**+
	 * Get the DamageSource of the CombatEntry instance.
	 */
	public DamageSource getDamageSrc() {
		return this.damageSrc;
	}

	public float func_94563_c() {
		return this.damage;
	}

	/**+
	 * Returns true if {@link
	 * net.minecraft.util.DamageSource#getEntity() damage source} is
	 * a living entity
	 */
	public boolean isLivingDamageSrc() {
		return this.damageSrc.getEntity() instanceof EntityLivingBase;
	}

	public String func_94562_g() {
		return this.field_94566_e;
	}

	public IChatComponent getDamageSrcDisplayName() {
		return this.getDamageSrc().getEntity() == null ? null : this.getDamageSrc().getEntity().getDisplayName();
	}

	public float getDamageAmount() {
		return this.damageSrc == DamageSource.outOfWorld ? Float.MAX_VALUE : this.fallDistance;
	}
}