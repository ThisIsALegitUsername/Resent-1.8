package net.minecraft.block.state;

import com.google.common.base.Predicate;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
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
public class BlockWorldState {
	private final World world;
	private final BlockPos pos;
	private final boolean field_181628_c;
	private IBlockState state;
	private TileEntity tileEntity;
	private boolean tileEntityInitialized;

	public BlockWorldState(World parWorld, BlockPos parBlockPos, boolean parFlag) {
		this.world = parWorld;
		this.pos = parBlockPos;
		this.field_181628_c = parFlag;
	}

	public IBlockState getBlockState() {
		if (this.state == null && (this.field_181628_c || this.world.isBlockLoaded(this.pos))) {
			this.state = this.world.getBlockState(this.pos);
		}

		return this.state;
	}

	public TileEntity getTileEntity() {
		if (this.tileEntity == null && !this.tileEntityInitialized) {
			this.tileEntity = this.world.getTileEntity(this.pos);
			this.tileEntityInitialized = true;
		}

		return this.tileEntity;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public static Predicate<BlockWorldState> hasState(final Predicate<IBlockState> parPredicate) {
		return new Predicate<BlockWorldState>() {
			public boolean apply(BlockWorldState blockworldstate) {
				return blockworldstate != null && parPredicate.apply(blockworldstate.getBlockState());
			}
		};
	}
}