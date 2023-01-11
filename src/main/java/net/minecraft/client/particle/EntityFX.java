package net.minecraft.client.particle;

import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;
import net.lax1dude.eaglercraft.v1_8.minecraft.IAcceleratedParticleEngine;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
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
public class EntityFX extends Entity {
	protected int particleTextureIndexX;
	protected int particleTextureIndexY;
	protected float particleTextureJitterX;
	protected float particleTextureJitterY;
	protected int particleAge;
	protected int particleMaxAge;
	protected float particleScale;
	protected float particleGravity;
	protected float particleRed;
	protected float particleGreen;
	protected float particleBlue;
	protected float particleAlpha;
	protected EaglerTextureAtlasSprite particleIcon;
	public static double interpPosX;
	public static double interpPosY;
	public static double interpPosZ;

	protected EntityFX(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn);
		this.particleAlpha = 1.0F;
		this.setSize(0.2F, 0.2F);
		this.setPosition(posXIn, posYIn, posZIn);
		this.lastTickPosX = this.prevPosX = posXIn;
		this.lastTickPosY = this.prevPosY = posYIn;
		this.lastTickPosZ = this.prevPosZ = posZIn;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
		this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
		this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
		this.particleMaxAge = (int) (4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
		this.particleAge = 0;
	}

	public EntityFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn,
			double zSpeedIn) {
		this(worldIn, xCoordIn, yCoordIn, zCoordIn);
		this.motionX = xSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
		this.motionY = ySpeedIn + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
		this.motionZ = zSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
		float f = (float) (Math.random() + Math.random() + 1.0D) * 0.15F;
		float f1 = MathHelper
				.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
		this.motionX = this.motionX / (double) f1 * (double) f * 0.4000000059604645D;
		this.motionY = this.motionY / (double) f1 * (double) f * 0.4000000059604645D + 0.10000000149011612D;
		this.motionZ = this.motionZ / (double) f1 * (double) f * 0.4000000059604645D;
	}

	public EntityFX multiplyVelocity(float multiplier) {
		this.motionX *= (double) multiplier;
		this.motionY = (this.motionY - 0.10000000149011612D) * (double) multiplier + 0.10000000149011612D;
		this.motionZ *= (double) multiplier;
		return this;
	}

	public EntityFX multipleParticleScaleBy(float parFloat1) {
		this.setSize(0.2F * parFloat1, 0.2F * parFloat1);
		this.particleScale *= parFloat1;
		return this;
	}

	public void setRBGColorF(float particleRedIn, float particleGreenIn, float particleBlueIn) {
		this.particleRed = particleRedIn;
		this.particleGreen = particleGreenIn;
		this.particleBlue = particleBlueIn;
	}

	/**+
	 * Sets the particle alpha (float)
	 */
	public void setAlphaF(float alpha) {
		if (this.particleAlpha == 1.0F && alpha < 1.0F) {
			Minecraft.getMinecraft().effectRenderer.moveToAlphaLayer(this);
		} else if (this.particleAlpha < 1.0F && alpha == 1.0F) {
			Minecraft.getMinecraft().effectRenderer.moveToNoAlphaLayer(this);
		}

		this.particleAlpha = alpha;
	}

	public float getRedColorF() {
		return this.particleRed;
	}

	public float getGreenColorF() {
		return this.particleGreen;
	}

	public float getBlueColorF() {
		return this.particleBlue;
	}

	public float getAlpha() {
		return this.particleAlpha;
	}

	/**+
	 * returns if this entity triggers Block.onEntityWalking on the
	 * blocks they walk on. used for spiders and wolves to prevent
	 * them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
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

		this.motionY -= 0.04D * (double) this.particleGravity;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer worldrenderer, Entity var2, float f, float f1, float f2, float f3,
			float f4, float f5) {
		float f6 = (float) this.particleTextureIndexX / 16.0F;
		float f7 = f6 + 0.0624375F;
		float f8 = (float) this.particleTextureIndexY / 16.0F;
		float f9 = f8 + 0.0624375F;
		float f10 = 0.1F * this.particleScale;
		if (this.particleIcon != null) {
			f6 = this.particleIcon.getMinU();
			f7 = this.particleIcon.getMaxU();
			f8 = this.particleIcon.getMinV();
			f9 = this.particleIcon.getMaxV();
		}

		float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
		float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
		float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
		int i = this.getBrightnessForRender(f);
		int j = i >> 16 & '\uffff';
		int k = i & '\uffff';
		worldrenderer
				.pos((double) (f11 - f1 * f10 - f4 * f10), (double) (f12 - f2 * f10),
						(double) (f13 - f3 * f10 - f5 * f10))
				.tex((double) f7, (double) f9)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
		worldrenderer
				.pos((double) (f11 - f1 * f10 + f4 * f10), (double) (f12 + f2 * f10),
						(double) (f13 - f3 * f10 + f5 * f10))
				.tex((double) f7, (double) f8)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
		worldrenderer
				.pos((double) (f11 + f1 * f10 + f4 * f10), (double) (f12 + f2 * f10),
						(double) (f13 + f3 * f10 + f5 * f10))
				.tex((double) f6, (double) f8)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
		worldrenderer
				.pos((double) (f11 + f1 * f10 - f4 * f10), (double) (f12 - f2 * f10),
						(double) (f13 + f3 * f10 - f5 * f10))
				.tex((double) f6, (double) f9)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
	}

	public boolean renderAccelerated(IAcceleratedParticleEngine accelerator, Entity var2, float f, float f1, float f2,
			float f3, float f4, float f5) {
		if (getFXLayer() == 3) {
			return false;
		} else {
			accelerator.drawParticle(this, particleTextureIndexX * 16, particleTextureIndexY * 16,
					getBrightnessForRender(f), 16, particleScale * 0.1f, this.particleRed, this.particleGreen,
					this.particleBlue, this.particleAlpha);
			return true;
		}
	}

	public int getFXLayer() {
		return 0;
	}

	/**+
	 * (abstract) Protected helper method to write subclass entity
	 * data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
	}

	/**+
	 * (abstract) Protected helper method to read subclass entity
	 * data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
	}

	/**+
	 * Sets the particle's icon.
	 */
	public void setParticleIcon(EaglerTextureAtlasSprite icon) {
		int i = this.getFXLayer();
		if (i == 1) {
			this.particleIcon = icon;
		} else {
			throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
		}
	}

	/**+
	 * Public method to set private field particleTextureIndex.
	 */
	public void setParticleTextureIndex(int particleTextureIndex) {
		if (this.getFXLayer() != 0) {
			throw new RuntimeException("Invalid call to Particle.setMiscTex");
		} else {
			this.particleTextureIndexX = particleTextureIndex % 16;
			this.particleTextureIndexY = particleTextureIndex / 16;
		}
	}

	public void nextTextureIndexX() {
		++this.particleTextureIndexX;
	}

	/**+
	 * If returns false, the item will not inflict any damage
	 * against entities.
	 */
	public boolean canAttackWithItem() {
		return false;
	}

	public String toString() {
		return this.getClass().getSimpleName() + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA ("
				+ this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha
				+ "), Age " + this.particleAge;
	}
}