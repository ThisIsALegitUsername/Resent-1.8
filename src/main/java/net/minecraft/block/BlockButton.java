package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockButton extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	private final boolean wooden;

	protected BlockButton(boolean wooden) {
		super(Material.circuits);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED,
				Boolean.valueOf(false)));
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.wooden = wooden;
	}

	public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
		return null;
	}

	/**+
	 * How many world ticks before ticking
	 */
	public int tickRate(World var1) {
		return this.wooden ? 30 : 20;
	}

	/**+
	 * Used to determine ambient occlusion and culling when
	 * rebuilding chunks for render
	 */
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	/**+
	 * Check whether this Block can be placed on the given side
	 */
	public boolean canPlaceBlockOnSide(World world, BlockPos blockpos, EnumFacing enumfacing) {
		return func_181088_a(world, blockpos, enumfacing.getOpposite());
	}

	public boolean canPlaceBlockAt(World world, BlockPos blockpos) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			if (func_181088_a(world, blockpos, enumfacing)) {
				return true;
			}
		}

		return false;
	}

	protected static boolean func_181088_a(World parWorld, BlockPos parBlockPos, EnumFacing parEnumFacing) {
		BlockPos blockpos = parBlockPos.offset(parEnumFacing);
		return parEnumFacing == EnumFacing.DOWN ? World.doesBlockHaveSolidTopSurface(parWorld, blockpos)
				: parWorld.getBlockState(blockpos).getBlock().isNormalCube();
	}

	/**+
	 * Called by ItemBlocks just before a block is actually set in
	 * the world, to allow for adjustments to the IBlockstate
	 */
	public IBlockState onBlockPlaced(World world, BlockPos blockpos, EnumFacing enumfacing, float var4, float var5,
			float var6, int var7, EntityLivingBase var8) {
		return func_181088_a(world, blockpos, enumfacing.getOpposite())
				? this.getDefaultState().withProperty(FACING, enumfacing).withProperty(POWERED, Boolean.valueOf(false))
				: this.getDefaultState().withProperty(FACING, EnumFacing.DOWN).withProperty(POWERED,
						Boolean.valueOf(false));
	}

	/**+
	 * Called when a neighboring block changes.
	 */
	public void onNeighborBlockChange(World world, BlockPos blockpos, IBlockState iblockstate, Block var4) {
		if (this.checkForDrop(world, blockpos, iblockstate)
				&& !func_181088_a(world, blockpos, ((EnumFacing) iblockstate.getValue(FACING)).getOpposite())) {
			this.dropBlockAsItem(world, blockpos, iblockstate, 0);
			world.setBlockToAir(blockpos);
		}

	}

	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (this.canPlaceBlockAt(worldIn, pos)) {
			return true;
		} else {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		}
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos blockpos) {
		this.updateBlockBounds(iblockaccess.getBlockState(blockpos));
	}

	private void updateBlockBounds(IBlockState state) {
		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
		boolean flag = ((Boolean) state.getValue(POWERED)).booleanValue();
		float f = 0.25F;
		float f1 = 0.375F;
		float f2 = (float) (flag ? 1 : 2) / 16.0F;
		float f3 = 0.125F;
		float f4 = 0.1875F;
		switch (enumfacing) {
		case EAST:
			this.setBlockBounds(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
			break;
		case WEST:
			this.setBlockBounds(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
			break;
		case SOUTH:
			this.setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
			break;
		case NORTH:
			this.setBlockBounds(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
			break;
		case UP:
			this.setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
			break;
		case DOWN:
			this.setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
		}

	}

	public boolean onBlockActivated(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer var4,
			EnumFacing var5, float var6, float var7, float var8) {
		if (((Boolean) iblockstate.getValue(POWERED)).booleanValue()) {
			return true;
		} else {
			world.setBlockState(blockpos, iblockstate.withProperty(POWERED, Boolean.valueOf(true)), 3);
			world.markBlockRangeForRenderUpdate(blockpos, blockpos);
			world.playSoundEffect((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D,
					(double) blockpos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
			this.notifyNeighbors(world, blockpos, (EnumFacing) iblockstate.getValue(FACING));
			world.scheduleUpdate(blockpos, this, this.tickRate(world));
			return true;
		}
	}

	public void breakBlock(World world, BlockPos blockpos, IBlockState iblockstate) {
		if (((Boolean) iblockstate.getValue(POWERED)).booleanValue()) {
			this.notifyNeighbors(world, blockpos, (EnumFacing) iblockstate.getValue(FACING));
		}

		super.breakBlock(world, blockpos, iblockstate);
	}

	public int getWeakPower(IBlockAccess var1, BlockPos var2, IBlockState iblockstate, EnumFacing var4) {
		return ((Boolean) iblockstate.getValue(POWERED)).booleanValue() ? 15 : 0;
	}

	public int getStrongPower(IBlockAccess var1, BlockPos var2, IBlockState iblockstate, EnumFacing enumfacing) {
		return !((Boolean) iblockstate.getValue(POWERED)).booleanValue() ? 0
				: (iblockstate.getValue(FACING) == enumfacing ? 15 : 0);
	}

	/**+
	 * Can this block provide power. Only wire currently seems to
	 * have this change based on its state.
	 */
	public boolean canProvidePower() {
		return true;
	}

	/**+
	 * Sets the block's bounds for rendering it as an item
	 */
	public void setBlockBoundsForItemRender() {
		float f = 0.1875F;
		float f1 = 0.125F;
		float f2 = 0.125F;
		this.setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
	}

	private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
		worldIn.notifyNeighborsOfStateChange(pos, this);
		worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		EnumFacing enumfacing;
		switch (i & 7) {
		case 0:
			enumfacing = EnumFacing.DOWN;
			break;
		case 1:
			enumfacing = EnumFacing.EAST;
			break;
		case 2:
			enumfacing = EnumFacing.WEST;
			break;
		case 3:
			enumfacing = EnumFacing.SOUTH;
			break;
		case 4:
			enumfacing = EnumFacing.NORTH;
			break;
		case 5:
		default:
			enumfacing = EnumFacing.UP;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(POWERED,
				Boolean.valueOf((i & 8) > 0));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		int i;
		switch ((EnumFacing) iblockstate.getValue(FACING)) {
		case EAST:
			i = 1;
			break;
		case WEST:
			i = 2;
			break;
		case SOUTH:
			i = 3;
			break;
		case NORTH:
			i = 4;
			break;
		case UP:
		default:
			i = 5;
			break;
		case DOWN:
			i = 0;
		}

		if (((Boolean) iblockstate.getValue(POWERED)).booleanValue()) {
			i |= 8;
		}

		return i;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, POWERED });
	}
}