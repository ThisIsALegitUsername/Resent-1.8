package net.minecraft.client.particle;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
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
public class EntityLavaFX extends EntityFX {
	private float lavaParticleScale;

	protected EntityLavaFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.800000011920929D;
		this.motionY *= 0.800000011920929D;
		this.motionZ *= 0.800000011920929D;
		this.motionY = (double) (this.rand.nextFloat() * 0.4F + 0.05F);
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		this.particleScale *= this.rand.nextFloat() * 2.0F + 0.2F;
		this.lavaParticleScale = this.particleScale;
		this.particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		this.noClip = false;
		this.setParticleTextureIndex(49);
	}

	public int getBrightnessForRender(float f) {
		float f1 = ((float) this.particleAge + f) / (float) this.particleMaxAge;
		f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
		int i = super.getBrightnessForRender(f);
		short short1 = 240;
		int j = i >> 16 & 255;
		return short1 | j << 16;
	}

	/**+
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float var1) {
		return 1.0F;
	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer worldrenderer, Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		float f6 = ((float) this.particleAge + f) / (float) this.particleMaxAge;
		this.particleScale = this.lavaParticleScale * (1.0F - f6 * f6);
		super.renderParticle(worldrenderer, entity, f, f1, f2, f3, f4, f5);
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}

		float f = (float) this.particleAge / (float) this.particleMaxAge;
		if (this.rand.nextFloat() > f) {
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX,
					this.motionY, this.motionZ, new int[0]);
		}

		this.motionY -= 0.03D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9990000128746033D;
		this.motionY *= 0.9990000128746033D;
		this.motionZ *= 0.9990000128746033D;
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

	}

	public static class Factory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double var9, double var11,
				double var13, int... var15) {
			return new EntityLavaFX(world, d0, d1, d2);
		}
	}
}