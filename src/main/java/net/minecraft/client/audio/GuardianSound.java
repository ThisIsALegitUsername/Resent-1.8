package net.minecraft.client.audio;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

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
public class GuardianSound extends MovingSound {
	private final EntityGuardian guardian;

	public GuardianSound(EntityGuardian guardian) {
		super(new ResourceLocation("minecraft:mob.guardian.attack"));
		this.guardian = guardian;
		this.attenuationType = ISound.AttenuationType.NONE;
		this.repeat = true;
		this.repeatDelay = 0;
	}

	/**+
	 * Like the old updateEntity(), except more generic.
	 */
	public void update() {
		if (!this.guardian.isDead && this.guardian.hasTargetedEntity()) {
			this.xPosF = (float) this.guardian.posX;
			this.yPosF = (float) this.guardian.posY;
			this.zPosF = (float) this.guardian.posZ;
			float f = this.guardian.func_175477_p(0.0F);
			this.volume = 0.0F + 1.0F * f * f;
			this.pitch = 0.7F + 0.5F * f;
		} else {
			this.donePlaying = true;
		}
	}
}