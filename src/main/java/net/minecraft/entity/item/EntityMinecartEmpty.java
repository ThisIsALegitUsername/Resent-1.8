package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
public class EntityMinecartEmpty extends EntityMinecart {
	public EntityMinecartEmpty(World worldIn) {
		super(worldIn);
	}

	public EntityMinecartEmpty(World worldIn, double parDouble1, double parDouble2, double parDouble3) {
		super(worldIn, parDouble1, parDouble2, parDouble3);
	}

	/**+
	 * First layer of player interaction
	 */
	public boolean interactFirst(EntityPlayer entityplayer) {
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer
				&& this.riddenByEntity != entityplayer) {
			return true;
		} else if (this.riddenByEntity != null && this.riddenByEntity != entityplayer) {
			return false;
		} else {
			return true;
		}
	}

	/**+
	 * Called every tick the minecart is on an activator rail. Args:
	 * x, y, z, is the rail receiving power
	 */
	public void onActivatorRailPass(int var1, int var2, int var3, boolean flag) {
		if (flag) {
			if (this.riddenByEntity != null) {
				this.riddenByEntity.mountEntity((Entity) null);
			}

			if (this.getRollingAmplitude() == 0) {
				this.setRollingDirection(-this.getRollingDirection());
				this.setRollingAmplitude(10);
				this.setDamage(50.0F);
				this.setBeenAttacked();
			}
		}

	}

	public EntityMinecart.EnumMinecartType getMinecartType() {
		return EntityMinecart.EnumMinecartType.RIDEABLE;
	}
}