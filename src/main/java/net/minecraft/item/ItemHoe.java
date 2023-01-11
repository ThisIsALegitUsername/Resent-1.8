package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
public class ItemHoe extends Item {
	protected Item.ToolMaterial theToolMaterial;

	public ItemHoe(Item.ToolMaterial material) {
		this.theToolMaterial = material;
		this.maxStackSize = 1;
		this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float var6, float var7, float var8) {
		if (!entityplayer.canPlayerEdit(blockpos.offset(enumfacing), enumfacing, itemstack)) {
			return false;
		} else {
			IBlockState iblockstate = world.getBlockState(blockpos);
			Block block = iblockstate.getBlock();
			if (enumfacing != EnumFacing.DOWN
					&& world.getBlockState(blockpos.up()).getBlock().getMaterial() == Material.air) {
				if (block == Blocks.grass) {
					return this.useHoe(itemstack, entityplayer, world, blockpos, Blocks.farmland.getDefaultState());
				}

				if (block == Blocks.dirt) {
					switch ((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)) {
					case DIRT:
						return this.useHoe(itemstack, entityplayer, world, blockpos, Blocks.farmland.getDefaultState());
					case COARSE_DIRT:
						return this.useHoe(itemstack, entityplayer, world, blockpos,
								Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
					}
				}
			}

			return false;
		}
	}

	protected boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target,
			IBlockState newState) {
		worldIn.playSoundEffect((double) ((float) target.getX() + 0.5F), (double) ((float) target.getY() + 0.5F),
				(double) ((float) target.getZ() + 0.5F), newState.getBlock().stepSound.getStepSound(),
				(newState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F,
				newState.getBlock().stepSound.getFrequency() * 0.8F);
		return true;
	}

	/**+
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D() {
		return true;
	}

	/**+
	 * Returns the name of the material this tool is made from as it
	 * is declared in EnumToolMaterial (meaning diamond would return
	 * "EMERALD")
	 */
	public String getMaterialName() {
		return this.theToolMaterial.toString();
	}
}