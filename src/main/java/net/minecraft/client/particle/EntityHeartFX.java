package net.minecraft.client.particle;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.entity.Entity;
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
public class EntityHeartFX extends EntityFX {
	float particleScaleOverTime;

	protected EntityHeartFX(World worldIn, double parDouble1, double parDouble2, double parDouble3, double parDouble4,
			double parDouble5, double parDouble6) {
		this(worldIn, parDouble1, parDouble2, parDouble3, parDouble4, parDouble5, parDouble6, 2.0F);
	}

	protected EntityHeartFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double parDouble1,
			double parDouble2, double parDouble3, float scale) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.009999999776482582D;
		this.motionY *= 0.009999999776482582D;
		this.motionZ *= 0.009999999776482582D;
		this.motionY += 0.1D;
		this.particleScale *= 0.75F;
		this.particleScale *= scale;
		this.particleScaleOverTime = this.particleScale;
		this.particleMaxAge = 16;
		this.noClip = false;
		this.setParticleTextureIndex(80);
	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer worldrenderer, Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		float f6 = ((float) this.particleAge + f) / (float) this.particleMaxAge * 32.0F;
		f6 = MathHelper.clamp_float(f6, 0.0F, 1.0F);
		this.particleScale = this.particleScaleOverTime * f6;
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

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		if (this.posY == this.prevPosY) {
			this.motionX *= 1.1D;
			this.motionZ *= 1.1D;
		}

		this.motionX *= 0.8600000143051147D;
		this.motionY *= 0.8600000143051147D;
		this.motionZ *= 0.8600000143051147D;
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

	}

	public static class AngryVillagerFactory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double d3, double d4,
				double d5, int... var15) {
			EntityHeartFX entityheartfx = new EntityHeartFX(world, d0, d1 + 0.5D, d2, d3, d4, d5);
			entityheartfx.setParticleTextureIndex(81);
			entityheartfx.setRBGColorF(1.0F, 1.0F, 1.0F);
			return entityheartfx;
		}
	}

	public static class Factory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double d3, double d4,
				double d5, int... var15) {
			return new EntityHeartFX(world, d0, d1, d2, d3, d4, d5);
		}
	}
}