package net.minecraft.entity;

import net.minecraft.util.MathHelper;

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
public class EntityBodyHelper {
	private EntityLivingBase theLiving;
	private int rotationTickCounter;
	private float prevRenderYawHead;

	public EntityBodyHelper(EntityLivingBase parEntityLivingBase) {
		this.theLiving = parEntityLivingBase;
	}

	/**+
	 * Update the Head and Body rendenring angles
	 */
	public void updateRenderAngles() {
		double d0 = this.theLiving.posX - this.theLiving.prevPosX;
		double d1 = this.theLiving.posZ - this.theLiving.prevPosZ;
		if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
			this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
			this.theLiving.rotationYawHead = this.computeAngleWithBound(this.theLiving.renderYawOffset,
					this.theLiving.rotationYawHead, 75.0F);
			this.prevRenderYawHead = this.theLiving.rotationYawHead;
			this.rotationTickCounter = 0;
		} else {
			float f = 75.0F;
			if (Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) > 15.0F) {
				this.rotationTickCounter = 0;
				this.prevRenderYawHead = this.theLiving.rotationYawHead;
			} else {
				++this.rotationTickCounter;
				boolean flag = true;
				if (this.rotationTickCounter > 10) {
					f = Math.max(1.0F - (float) (this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
				}
			}

			this.theLiving.renderYawOffset = this.computeAngleWithBound(this.theLiving.rotationYawHead,
					this.theLiving.renderYawOffset, f);
		}
	}

	/**+
	 * Return the new angle2 such that the difference between angle1
	 * and angle2 is lower than angleMax. Args : angle1, angle2,
	 * angleMax
	 */
	private float computeAngleWithBound(float parFloat1, float parFloat2, float parFloat3) {
		float f = MathHelper.wrapAngleTo180_float(parFloat1 - parFloat2);
		if (f < -parFloat3) {
			f = -parFloat3;
		}

		if (f >= parFloat3) {
			f = parFloat3;
		}

		return parFloat1 - f;
	}
}