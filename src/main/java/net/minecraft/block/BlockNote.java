package net.minecraft.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
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
public class BlockNote extends BlockContainer {
	private static final List<String> INSTRUMENTS = Lists
			.newArrayList(new String[] { "harp", "bd", "snare", "hat", "bassattack" });

	public BlockNote() {
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState var3, Block var4) {
		boolean flag = world.isBlockPowered(blockpos);
		TileEntity tileentity = world.getTileEntity(blockpos);
		if (tileentity instanceof TileEntityNote) {
			TileEntityNote tileentitynote = (TileEntityNote) tileentity;
			if (tileentitynote.previousRedstoneState != flag) {
				if (flag) {
					tileentitynote.triggerNote(world, blockpos);
				}

				tileentitynote.previousRedstoneState = flag;
			}
		}

	}

	public boolean onBlockActivated(World world, BlockPos blockpos, IBlockState var3, EntityPlayer entityplayer,
			EnumFacing var5, float var6, float var7, float var8) {
		return true;
	}

	/**+
	 * Returns a new instance of a block's tile entity class. Called
	 * on placing the block.
	 */
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityNote();
	}

	private String getInstrument(int id) {
		if (id < 0 || id >= INSTRUMENTS.size()) {
			id = 0;
		}

		return (String) INSTRUMENTS.get(id);
	}

	/**+
	 * Called on both Client and Server when World#addBlockEvent is
	 * called
	 */
	public boolean onBlockEventReceived(World world, BlockPos blockpos, IBlockState var3, int i, int j) {
		float f = (float) Math.pow(2.0D, (double) (j - 12) / 12.0D);
		world.playSoundEffect((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D,
				(double) blockpos.getZ() + 0.5D, "note." + this.getInstrument(i), 3.0F, f);
		world.spawnParticle(EnumParticleTypes.NOTE, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 1.2D,
				(double) blockpos.getZ() + 0.5D, (double) j / 24.0D, 0.0D, 0.0D, new int[0]);
		return true;
	}

	/**+
	 * The type of render function called. 3 for standard block
	 * models, 2 for TESR's, 1 for liquids, -1 is no render
	 */
	public int getRenderType() {
		return 3;
	}
}