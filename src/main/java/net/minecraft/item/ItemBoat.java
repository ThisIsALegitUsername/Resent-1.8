package net.minecraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
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
public class ItemBoat extends Item {
	public ItemBoat() {
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTransport);
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		float f = 1.0F;
		float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
		float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
		double d0 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double) f;
		double d1 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double) f
				+ (double) entityplayer.getEyeHeight();
		double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double) f;
		Vec3 vec3 = new Vec3(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
		MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3, vec31, true);
		if (movingobjectposition == null) {
			return itemstack;
		} else {
			Vec3 vec32 = entityplayer.getLook(f);
			boolean flag = false;
			float f9 = 1.0F;
			List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer,
					entityplayer.getEntityBoundingBox()
							.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3)
							.expand((double) f9, (double) f9, (double) f9));

			for (int i = 0; i < list.size(); ++i) {
				Entity entity = (Entity) list.get(i);
				if (entity.canBeCollidedWith()) {
					float f10 = entity.getCollisionBorderSize();
					AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double) f10, (double) f10,
							(double) f10);
					if (axisalignedbb.isVecInside(vec3)) {
						flag = true;
					}
				}
			}

			if (flag) {
				return itemstack;
			} else {
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					BlockPos blockpos = movingobjectposition.getBlockPos();
					if (world.getBlockState(blockpos).getBlock() == Blocks.snow_layer) {
						blockpos = blockpos.down();
					}

					EntityBoat entityboat = new EntityBoat(world, (double) ((float) blockpos.getX() + 0.5F),
							(double) ((float) blockpos.getY() + 1.0F), (double) ((float) blockpos.getZ() + 0.5F));
					entityboat.rotationYaw = (float) (((MathHelper
							.floor_double((double) (entityplayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);
					if (!world.getCollidingBoundingBoxes(entityboat,
							entityboat.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
						return itemstack;
					}

					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
					}

					entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
				}

				return itemstack;
			}
		}
	}
}