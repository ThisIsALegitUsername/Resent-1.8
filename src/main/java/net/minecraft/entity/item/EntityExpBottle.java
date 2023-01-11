package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
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
public class EntityExpBottle extends EntityThrowable {
	public EntityExpBottle(World worldIn) {
		super(worldIn);
	}

	public EntityExpBottle(World worldIn, EntityLivingBase parEntityLivingBase) {
		super(worldIn, parEntityLivingBase);
	}

	public EntityExpBottle(World worldIn, double parDouble1, double parDouble2, double parDouble3) {
		super(worldIn, parDouble1, parDouble2, parDouble3);
	}

	/**+
	 * Gets the amount of gravity to apply to the thrown entity with
	 * each tick.
	 */
	protected float getGravityVelocity() {
		return 0.07F;
	}

	protected float getVelocity() {
		return 0.7F;
	}

	protected float getInaccuracy() {
		return -20.0F;
	}

	/**+
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition var1) {

	}
}