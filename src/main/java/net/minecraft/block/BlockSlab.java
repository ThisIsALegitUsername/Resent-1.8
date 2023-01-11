package net.minecraft.block;

import java.util.List;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSlab extends Block {
	public static PropertyEnum<BlockSlab.EnumBlockHalf> HALF;

	public BlockSlab(Material materialIn) {
		super(materialIn);
		if (this.isDouble()) {
			this.fullBlock = true;
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}

		this.setLightOpacity(255);
	}

	public static void bootstrapStates() {
		HALF = PropertyEnum.<BlockSlab.EnumBlockHalf>create("half", BlockSlab.EnumBlockHalf.class);
	}

	protected boolean canSilkHarvest() {
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos blockpos) {
		if (this.isDouble()) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			IBlockState iblockstate = iblockaccess.getBlockState(blockpos);
			if (iblockstate.getBlock() == this) {
				if (iblockstate.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
					this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
				} else {
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
				}
			}

		}
	}

	/**+
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender() {
		if (this.isDouble()) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}

	}

	/**+
	 * Add all collision boxes of this Block to the list that
	 * intersect with the given mask.
	 */
	public void addCollisionBoxesToList(World world, BlockPos blockpos, IBlockState iblockstate,
			AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
		this.setBlockBoundsBasedOnState(world, blockpos);
		super.addCollisionBoxesToList(world, blockpos, iblockstate, axisalignedbb, list, entity);
	}

	/**+
	 * Used to determine ambient occlusion and culling when
	 * rebuilding chunks for render
	 */
	public boolean isOpaqueCube() {
		return this.isDouble();
	}

	/**+
	 * Called by ItemBlocks just before a block is actually set in
	 * the world, to allow for adjustments to the IBlockstate
	 */
	public IBlockState onBlockPlaced(World world, BlockPos blockpos, EnumFacing enumfacing, float f, float f1, float f2,
			int i, EntityLivingBase entitylivingbase) {
		IBlockState iblockstate = super.onBlockPlaced(world, blockpos, enumfacing, f, f1, f2, i, entitylivingbase)
				.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		return this.isDouble() ? iblockstate
				: (enumfacing != EnumFacing.DOWN && (enumfacing == EnumFacing.UP || (double) f1 <= 0.5D) ? iblockstate
						: iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.TOP));
	}

	/**+
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(EaglercraftRandom var1) {
		return this.isDouble() ? 2 : 1;
	}

	public boolean isFullCube() {
		return this.isDouble();
	}

	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, BlockPos blockpos, EnumFacing enumfacing) {
		if (this.isDouble()) {
			return super.shouldSideBeRendered(iblockaccess, blockpos, enumfacing);
		} else if (enumfacing != EnumFacing.UP && enumfacing != EnumFacing.DOWN
				&& !super.shouldSideBeRendered(iblockaccess, blockpos, enumfacing)) {
			return false;
		} else {
			BlockPos blockpos1 = blockpos.offset(enumfacing.getOpposite());
			IBlockState iblockstate = iblockaccess.getBlockState(blockpos);
			IBlockState iblockstate1 = iblockaccess.getBlockState(blockpos1);
			boolean flag = isSlab(iblockstate.getBlock()) && iblockstate.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
			boolean flag1 = isSlab(iblockstate1.getBlock())
					&& iblockstate1.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
			return flag1
					? (enumfacing == EnumFacing.DOWN ? true
							: (enumfacing == EnumFacing.UP
									&& super.shouldSideBeRendered(iblockaccess, blockpos, enumfacing) ? true
											: !isSlab(iblockstate.getBlock()) || !flag))
					: (enumfacing == EnumFacing.UP ? true
							: (enumfacing == EnumFacing.DOWN
									&& super.shouldSideBeRendered(iblockaccess, blockpos, enumfacing) ? true
											: !isSlab(iblockstate.getBlock()) || flag));
		}
	}

	protected static boolean isSlab(Block blockIn) {
		return blockIn == Blocks.stone_slab || blockIn == Blocks.wooden_slab || blockIn == Blocks.stone_slab2;
	}

	public abstract String getUnlocalizedName(int var1);

	public int getDamageValue(World world, BlockPos blockpos) {
		return super.getDamageValue(world, blockpos) & 7;
	}

	public abstract boolean isDouble();

	public abstract IProperty<?> getVariantProperty();

	public abstract Object getVariant(ItemStack var1);

	public static enum EnumBlockHalf implements IStringSerializable {
		TOP("top"), BOTTOM("bottom");

		private final String name;

		private EnumBlockHalf(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}
	}
}