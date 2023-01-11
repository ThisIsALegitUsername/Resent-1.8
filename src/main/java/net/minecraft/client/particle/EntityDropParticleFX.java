package net.minecraft.client.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
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
public class EntityDropParticleFX extends EntityFX {
	private Material materialType;
	private int bobTimer;

	protected EntityDropParticleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
			Material parMaterial) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.motionX = this.motionY = this.motionZ = 0.0D;
		if (parMaterial == Material.water) {
			this.particleRed = 0.0F;
			this.particleGreen = 0.0F;
			this.particleBlue = 1.0F;
		} else {
			this.particleRed = 1.0F;
			this.particleGreen = 0.0F;
			this.particleBlue = 0.0F;
		}

		this.setParticleTextureIndex(113);
		this.setSize(0.01F, 0.01F);
		this.particleGravity = 0.06F;
		this.materialType = parMaterial;
		this.bobTimer = 40;
		this.particleMaxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
		this.motionX = this.motionY = this.motionZ = 0.0D;
	}

	public int getBrightnessForRender(float partialTicks) {
		return this.materialType == Material.water ? super.getBrightnessForRender(partialTicks) : 257;
	}

	/**+
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float partialTicks) {
		return this.materialType == Material.water ? super.getBrightness(partialTicks) : 1.0F;
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.materialType == Material.water) {
			this.particleRed = 0.2F;
			this.particleGreen = 0.3F;
			this.particleBlue = 1.0F;
		} else {
			this.particleRed = 1.0F;
			this.particleGreen = 16.0F / (float) (40 - this.bobTimer + 16);
			this.particleBlue = 4.0F / (float) (40 - this.bobTimer + 8);
		}

		this.motionY -= (double) this.particleGravity;
		if (this.bobTimer-- > 0) {
			this.motionX *= 0.02D;
			this.motionY *= 0.02D;
			this.motionZ *= 0.02D;
			this.setParticleTextureIndex(113);
		} else {
			this.setParticleTextureIndex(112);
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		if (this.particleMaxAge-- <= 0) {
			this.setDead();
		}

		if (this.onGround) {
			if (this.materialType == Material.water) {
				this.setDead();
				this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0D, 0.0D,
						0.0D, new int[0]);
			} else {
				this.setParticleTextureIndex(114);
			}

			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

		BlockPos blockpos = new BlockPos(this);
		IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
		Material material = iblockstate.getBlock().getMaterial();
		if (material.isLiquid() || material.isSolid()) {
			double d0 = 0.0D;
			if (iblockstate.getBlock() instanceof BlockLiquid) {
				d0 = (double) BlockLiquid
						.getLiquidHeightPercent(((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue());
			}

			double d1 = (double) (MathHelper.floor_double(this.posY) + 1) - d0;
			if (this.posY < d1) {
				this.setDead();
			}
		}

	}

	public static class LavaFactory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double var9, double var11,
				double var13, int... var15) {
			return new EntityDropParticleFX(world, d0, d1, d2, Material.lava);
		}
	}

	public static class WaterFactory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double var9, double var11,
				double var13, int... var15) {
			return new EntityDropParticleFX(world, d0, d1, d2, Material.water);
		}
	}
}