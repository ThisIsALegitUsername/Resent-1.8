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
public class EntityHugeExplodeFX extends EntityFX {
	private int timeSinceStart;
	/**+
	 * the maximum time for the explosion
	 */
	private int maximumTime = 8;

	protected EntityHugeExplodeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double parDouble1,
			double parDouble2, double parDouble3) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
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
		for (int i = 0; i < 6; ++i) {
			double d0 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
			double d1 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
			double d2 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2,
					(double) ((float) this.timeSinceStart / (float) this.maximumTime), 0.0D, 0.0D, new int[0]);
		}

		++this.timeSinceStart;
		if (this.timeSinceStart == this.maximumTime) {
			this.setDead();
		}

	}

	public int getFXLayer() {
		return 1;
	}

	public static class Factory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double d3, double d4,
				double d5, int... var15) {
			return new EntityHugeExplodeFX(world, d0, d1, d2, d3, d4, d5);
		}
	}
}