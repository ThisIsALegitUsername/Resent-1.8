package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
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
public class MovingSoundMinecartRiding extends MovingSound {
	private final EntityPlayer player;
	private final EntityMinecart minecart;

	public MovingSoundMinecartRiding(EntityPlayer playerRiding, EntityMinecart minecart) {
		super(new ResourceLocation("minecraft:minecart.inside"));
		this.player = playerRiding;
		this.minecart = minecart;
		this.attenuationType = ISound.AttenuationType.NONE;
		this.repeat = true;
		this.repeatDelay = 0;
	}

	/**+
	 * Like the old updateEntity(), except more generic.
	 */
	public void update() {
		if (!this.minecart.isDead && this.player.isRiding() && this.player.ridingEntity == this.minecart) {
			float f = MathHelper.sqrt_double(
					this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
			if ((double) f >= 0.01D) {
				this.volume = 0.0F + MathHelper.clamp_float(f, 0.0F, 1.0F) * 0.75F;
			} else {
				this.volume = 0.0F;
			}

		} else {
			this.donePlaying = true;
		}
	}
}