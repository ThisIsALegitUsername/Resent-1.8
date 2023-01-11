package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
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
public class ItemBucket extends Item {
	private Block isFull;

	public ItemBucket(Block containedBlock) {
		this.maxStackSize = 1;
		this.isFull = containedBlock;
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		boolean flag = this.isFull == Blocks.air;
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, entityplayer, flag);
		if (movingobjectposition == null) {
			return itemstack;
		} else {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				BlockPos blockpos = movingobjectposition.getBlockPos();
				if (!world.isBlockModifiable(entityplayer, blockpos)) {
					return itemstack;
				}

				if (flag) {
					if (!entityplayer.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit),
							movingobjectposition.sideHit, itemstack)) {
						return itemstack;
					}

					IBlockState iblockstate = world.getBlockState(blockpos);
					Material material = iblockstate.getBlock().getMaterial();
					if (material == Material.water
							&& ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
						world.setBlockToAir(blockpos);
						entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						return this.fillBucket(itemstack, entityplayer, Items.water_bucket);
					}

					if (material == Material.lava
							&& ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
						world.setBlockToAir(blockpos);
						entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						return this.fillBucket(itemstack, entityplayer, Items.lava_bucket);
					}
				} else {
					if (this.isFull == Blocks.air) {
						return new ItemStack(Items.bucket);
					}

					BlockPos blockpos1 = blockpos.offset(movingobjectposition.sideHit);
					if (!entityplayer.canPlayerEdit(blockpos1, movingobjectposition.sideHit, itemstack)) {
						return itemstack;
					}

					if (this.tryPlaceContainedLiquid(world, blockpos1) && !entityplayer.capabilities.isCreativeMode) {
						entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						return new ItemStack(Items.bucket);
					}
				}
			}

			return itemstack;
		}
	}

	private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket) {
		if (player.capabilities.isCreativeMode) {
			return emptyBuckets;
		} else if (--emptyBuckets.stackSize <= 0) {
			return new ItemStack(fullBucket);
		} else {
			if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket))) {
				player.dropPlayerItemWithRandomChoice(new ItemStack(fullBucket, 1, 0), false);
			}

			return emptyBuckets;
		}
	}

	public boolean tryPlaceContainedLiquid(World worldIn, BlockPos pos) {
		if (this.isFull == Blocks.air) {
			return false;
		} else {
			Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
			boolean flag = !material.isSolid();
			if (!worldIn.isAirBlock(pos) && !flag) {
				return false;
			} else {
				if (worldIn.provider.doesWaterVaporize() && this.isFull == Blocks.flowing_water) {
					int i = pos.getX();
					int j = pos.getY();
					int k = pos.getZ();
					worldIn.playSoundEffect((double) ((float) i + 0.5F), (double) ((float) j + 0.5F),
							(double) ((float) k + 0.5F), "random.fizz", 0.5F,
							2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

					for (int l = 0; l < 8; ++l) {
						worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) i + Math.random(),
								(double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
					}
				} else {
					worldIn.setBlockState(pos, this.isFull.getDefaultState(), 3);
				}

				return true;
			}
		}
	}
}