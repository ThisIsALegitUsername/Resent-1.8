package net.minecraft.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
public class ItemDye extends Item {
	public static final int[] dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799,
			11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };

	public ItemDye() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	/**+
	 * Returns the unlocalized name of this item. This version
	 * accepts an ItemStack so different stacks can have different
	 * names based on their damage or NBT.
	 */
	public String getUnlocalizedName(ItemStack itemstack) {
		int i = itemstack.getMetadata();
		return super.getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(i).getUnlocalizedName();
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float f, float f1, float f2) {
		if (!entityplayer.canPlayerEdit(blockpos.offset(enumfacing), enumfacing, itemstack)) {
			return false;
		} else {
			EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());
			if (enumdyecolor == EnumDyeColor.WHITE) {
				if (applyBonemeal(itemstack, world, blockpos)) {
					return true;
				}
			} else if (enumdyecolor == EnumDyeColor.BROWN) {
				IBlockState iblockstate = world.getBlockState(blockpos);
				Block block = iblockstate.getBlock();
				if (block == Blocks.log && iblockstate.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
					if (enumfacing == EnumFacing.DOWN) {
						return false;
					}

					if (enumfacing == EnumFacing.UP) {
						return false;
					}

					blockpos = blockpos.offset(enumfacing);
					if (world.isAirBlock(blockpos)) {
						IBlockState iblockstate1 = Blocks.cocoa.onBlockPlaced(world, blockpos, enumfacing, f, f1, f2, 0,
								entityplayer);
						world.setBlockState(blockpos, iblockstate1, 2);
						if (!entityplayer.capabilities.isCreativeMode) {
							--itemstack.stackSize;
						}
					}

					return true;
				}
			}

			return false;
		}
	}

	public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
		IBlockState iblockstate = worldIn.getBlockState(target);
		if (iblockstate.getBlock() instanceof IGrowable) {
			IGrowable igrowable = (IGrowable) iblockstate.getBlock();
			if (igrowable.canGrow(worldIn, target, iblockstate, true)) {
				return true;
			}
		}

		return false;
	}

	public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
		if (amount == 0) {
			amount = 15;
		}

		Block block = worldIn.getBlockState(pos).getBlock();
		if (block.getMaterial() != Material.air) {
			block.setBlockBoundsBasedOnState(worldIn, pos);

			for (int i = 0; i < amount; ++i) {
				double d0 = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
						(double) ((float) pos.getX() + itemRand.nextFloat()),
						(double) pos.getY() + (double) itemRand.nextFloat() * block.getBlockBoundsMaxY(),
						(double) ((float) pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
			}

		}
	}

	/**+
	 * Returns true if the item can be used on the given entity,
	 * e.g. shears on sheep.
	 */
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer var2, EntityLivingBase entitylivingbase) {
		if (entitylivingbase instanceof EntitySheep) {
			EntitySheep entitysheep = (EntitySheep) entitylivingbase;
			EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());
			if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor) {
				entitysheep.setFleeceColor(enumdyecolor);
				--itemstack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	/**+
	 * returns a list of items with the same ID, but different meta
	 * (eg: dye returns 16 items)
	 */
	public void getSubItems(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (int i = 0; i < 16; ++i) {
			list.add(new ItemStack(item, 1, i));
		}

	}
}