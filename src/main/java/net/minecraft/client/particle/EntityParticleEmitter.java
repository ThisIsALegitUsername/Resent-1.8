package net.minecraft.client.particle;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
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
public class EntityParticleEmitter extends EntityFX {
	private Entity attachedEntity;
	private int age;
	private int lifetime;
	private EnumParticleTypes particleTypes;

	public EntityParticleEmitter(World worldIn, Entity parEntity, EnumParticleTypes particleTypesIn) {
		super(worldIn, parEntity.posX, parEntity.getEntityBoundingBox().minY + (double) (parEntity.height / 2.0F),
				parEntity.posZ, parEntity.motionX, parEntity.motionY, parEntity.motionZ);
		this.attachedEntity = parEntity;
		this.lifetime = 3;
		this.particleTypes = particleTypesIn;
		this.onUpdate();
	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6,
			float var7, float var8) {
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		for (int i = 0; i < 16; ++i) {
			double d0 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
			double d1 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
			double d2 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
			if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D) {
				double d3 = this.attachedEntity.posX + d0 * (double) this.attachedEntity.width / 4.0D;
				double d4 = this.attachedEntity.getEntityBoundingBox().minY
						+ (double) (this.attachedEntity.height / 2.0F)
						+ d1 * (double) this.attachedEntity.height / 4.0D;
				double d5 = this.attachedEntity.posZ + d2 * (double) this.attachedEntity.width / 4.0D;
				this.worldObj.spawnParticle(this.particleTypes, false, d3, d4, d5, d0, d1 + 0.2D, d2, new int[0]);
			}
		}

		++this.age;
		if (this.age >= this.lifetime) {
			this.setDead();
		}

	}

	public int getFXLayer() {
		return 3;
	}
}