package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
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
public class EntitySuspendFX extends EntityFX {
	protected EntitySuspendFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn - 0.125D, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.particleRed = 0.4F;
		this.particleGreen = 0.4F;
		this.particleBlue = 0.7F;
		this.setParticleTextureIndex(0);
		this.setSize(0.01F, 0.01F);
		this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
		this.motionX = xSpeedIn * 0.0D;
		this.motionY = ySpeedIn * 0.0D;
		this.motionZ = zSpeedIn * 0.0D;
		this.particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
			this.setDead();
		}

		if (this.particleMaxAge-- <= 0) {
			this.setDead();
		}

	}

	public static class Factory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double d3, double d4,
				double d5, int... var15) {
			return new EntitySuspendFX(world, d0, d1, d2, d3, d4, d5);
		}
	}
}