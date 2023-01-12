package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
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
public class BlockTNT extends Block {
	public static final PropertyBool EXPLODE = PropertyBool.create("explode");

	public BlockTNT() {
		super(Material.tnt);
		this.setDefaultState(this.blockState.getBaseState().withProperty(EXPLODE, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	public void onBlockAdded(World world, BlockPos blockpos, IBlockState iblockstate) {
		super.onBlockAdded(world, blockpos, iblockstate);
		if (world.isBlockPowered(blockpos)) {
			this.onBlockDestroyedByPlayer(world, blockpos, iblockstate.withProperty(EXPLODE, Boolean.valueOf(true)));
			world.setBlockToAir(blockpos);
		}

	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState iblockstate, Block var4) {
		if (world.isBlockPowered(blockpos)) {
			this.onBlockDestroyedByPlayer(world, blockpos, iblockstate.withProperty(EXPLODE, Boolean.valueOf(true)));
			world.setBlockToAir(blockpos);
		}

	}

	/**+
	 * Called when a player destroys this Block
	 */
	public void onBlockDestroyedByPlayer(World world, BlockPos blockpos, IBlockState iblockstate) {
		this.explode(world, blockpos, iblockstate, (EntityLivingBase) null);
	}

	public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {

	}

	public boolean onBlockActivated(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer,
			EnumFacing enumfacing, float f, float f1, float f2) {
		if (entityplayer.getCurrentEquippedItem() != null) {
			Item item = entityplayer.getCurrentEquippedItem().getItem();
			if (item == Items.flint_and_steel || item == Items.fire_charge) {
				this.explode(world, blockpos, iblockstate.withProperty(EXPLODE, Boolean.valueOf(true)), entityplayer);
				world.setBlockToAir(blockpos);
				if (item == Items.flint_and_steel) {
					entityplayer.getCurrentEquippedItem().damageItem(1, entityplayer);
				} else if (!entityplayer.capabilities.isCreativeMode) {
					--entityplayer.getCurrentEquippedItem().stackSize;
				}

				return true;
			}
		}

		return super.onBlockActivated(world, blockpos, iblockstate, entityplayer, enumfacing, f, f1, f2);
	}

	/**+
	 * Return whether this block can drop from an explosion.
	 */
	public boolean canDropFromExplosion(Explosion var1) {
		return false;
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(EXPLODE, Boolean.valueOf((i & 1) > 0));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((Boolean) iblockstate.getValue(EXPLODE)).booleanValue() ? 1 : 0;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { EXPLODE });
	}
}