package net.minecraft.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
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
public class BlockPressurePlate extends BlockBasePressurePlate {
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	private final BlockPressurePlate.Sensitivity sensitivity;

	protected BlockPressurePlate(Material materialIn, BlockPressurePlate.Sensitivity sensitivityIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.valueOf(false)));
		this.sensitivity = sensitivityIn;
	}

	protected int getRedstoneStrength(IBlockState iblockstate) {
		return ((Boolean) iblockstate.getValue(POWERED)).booleanValue() ? 15 : 0;
	}

	protected IBlockState setRedstoneStrength(IBlockState iblockstate, int i) {
		return iblockstate.withProperty(POWERED, Boolean.valueOf(i > 0));
	}

	protected int computeRedstoneStrength(World world, BlockPos blockpos) {
		AxisAlignedBB axisalignedbb = this.getSensitiveAABB(blockpos);
		List<Entity> list;
		switch (this.sensitivity) {
		case EVERYTHING:
			list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, axisalignedbb);
			break;
		case MOBS:
			list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			break;
		default:
			return 0;
		}

		if (!list.isEmpty()) {
			for (Entity entity : list) {
				if (!entity.doesEntityNotTriggerPressurePlate()) {
					return 15;
				}
			}
		}

		return 0;
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(POWERED, Boolean.valueOf(i == 1));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((Boolean) iblockstate.getValue(POWERED)).booleanValue() ? 1 : 0;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { POWERED });
	}

	public static enum Sensitivity {
		EVERYTHING, MOBS;
	}
}