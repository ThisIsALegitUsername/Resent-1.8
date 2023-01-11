package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
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
public class EntityEnderPearl extends EntityThrowable {
	private EntityLivingBase field_181555_c;

	public EntityEnderPearl(World parWorld) {
		super(parWorld);
	}

	public EntityEnderPearl(World worldIn, EntityLivingBase parEntityLivingBase) {
		super(worldIn, parEntityLivingBase);
		this.field_181555_c = parEntityLivingBase;
	}

	public EntityEnderPearl(World worldIn, double parDouble1, double parDouble2, double parDouble3) {
		super(worldIn, parDouble1, parDouble2, parDouble3);
	}

	/**+
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		EntityLivingBase entitylivingbase = this.getThrower();
		if (movingobjectposition.entityHit != null) {
			if (movingobjectposition.entityHit == this.field_181555_c) {
				return;
			}

			movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase),
					0.0F);
		}

		for (int i = 0; i < 32; ++i) {
			this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D,
					this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian(), new int[0]);
		}

	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		EntityLivingBase entitylivingbase = this.getThrower();
		if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.isEntityAlive()) {
			this.setDead();
		} else {
			super.onUpdate();
		}

	}
}