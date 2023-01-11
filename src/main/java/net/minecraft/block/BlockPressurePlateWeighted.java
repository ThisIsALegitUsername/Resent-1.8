package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
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
public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
	public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
	private final int field_150068_a;

	protected BlockPressurePlateWeighted(Material parMaterial, int parInt1) {
		this(parMaterial, parInt1, parMaterial.getMaterialMapColor());
	}

	protected BlockPressurePlateWeighted(Material parMaterial, int parInt1, MapColor parMapColor) {
		super(parMaterial, parMapColor);
		this.setDefaultState(this.blockState.getBaseState().withProperty(POWER, Integer.valueOf(0)));
		this.field_150068_a = parInt1;
	}

	protected int computeRedstoneStrength(World world, BlockPos blockpos) {
		int i = Math.min(world.getEntitiesWithinAABB(Entity.class, this.getSensitiveAABB(blockpos)).size(),
				this.field_150068_a);
		if (i > 0) {
			float f = (float) Math.min(this.field_150068_a, i) / (float) this.field_150068_a;
			return MathHelper.ceiling_float_int(f * 15.0F);
		} else {
			return 0;
		}
	}

	protected int getRedstoneStrength(IBlockState iblockstate) {
		return ((Integer) iblockstate.getValue(POWER)).intValue();
	}

	protected IBlockState setRedstoneStrength(IBlockState iblockstate, int i) {
		return iblockstate.withProperty(POWER, Integer.valueOf(i));
	}

	/**+
	 * How many world ticks before ticking
	 */
	public int tickRate(World var1) {
		return 10;
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(POWER, Integer.valueOf(i));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((Integer) iblockstate.getValue(POWER)).intValue();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { POWER });
	}
}