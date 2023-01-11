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
public class EntityNoteFX extends EntityFX {
	float noteParticleScale;

	protected EntityNoteFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double parDouble1,
			double parDouble2, double parDouble3) {
		this(worldIn, xCoordIn, yCoordIn, zCoordIn, parDouble1, parDouble2, parDouble3, 2.0F);
	}

	protected EntityNoteFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double parDouble1,
			double parDouble2, double parDouble3, float parFloat1) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.009999999776482582D;
		this.motionY *= 0.009999999776482582D;
		this.motionZ *= 0.009999999776482582D;
		this.motionY += 0.2D;
		this.particleRed = MathHelper.sin(((float) parDouble1 + 0.0F) * 3.1415927F * 2.0F) * 0.65F + 0.35F;
		this.particleGreen = MathHelper.sin(((float) parDouble1 + 0.33333334F) * 3.1415927F * 2.0F) * 0.65F + 0.35F;
		this.particleBlue = MathHelper.sin(((float) parDouble1 + 0.6666667F) * 3.1415927F * 2.0F) * 0.65F + 0.35F;
		this.particleScale *= 0.75F;
		this.particleScale *= parFloat1;
		this.noteParticleScale = this.particleScale;
		this.particleMaxAge = 6;
		this.noClip = false;
		this.setParticleTextureIndex(64);
	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer worldrenderer, Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		float f6 = ((float) this.particleAge + f) / (float) this.particleMaxAge * 32.0F;
		f6 = MathHelper.clamp_float(f6, 0.0F, 1.0F);
		this.particleScale = this.noteParticleScale * f6;
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

		this.motionX *= 0.6600000262260437D;
		this.motionY *= 0.6600000262260437D;
		this.motionZ *= 0.6600000262260437D;
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

	}

	public static class Factory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double d3, double d4,
				double d5, int... var15) {
			return new EntityNoteFX(world, d0, d1, d2, d3, d4, d5);
		}
	}
}