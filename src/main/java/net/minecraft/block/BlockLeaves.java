package net.minecraft.block;

import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLeaves extends BlockLeavesBase {
	public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
	public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
	int[] surroundings;
	protected int iconIndex;
	protected boolean isTransparent;

	public BlockLeaves() {
		super(Material.leaves, false);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setStepSound(soundTypeGrass);
	}

	public int getBlockColor() {
		return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
	}

	public int getRenderColor(IBlockState var1) {
		return ColorizerFoliage.getFoliageColorBasic();
	}

	public int colorMultiplier(IBlockAccess iblockaccess, BlockPos blockpos, int var3) {
		return BiomeColorHelper.getFoliageColorAtPos(iblockaccess, blockpos);
	}

	public void breakBlock(World world, BlockPos blockpos, IBlockState var3) {
		byte b0 = 1;
		int i = b0 + 1;
		int j = blockpos.getX();
		int k = blockpos.getY();
		int l = blockpos.getZ();
		if (world.isAreaLoaded(new BlockPos(j - i, k - i, l - i), new BlockPos(j + i, k + i, l + i))) {
			for (int i1 = -b0; i1 <= b0; ++i1) {
				for (int j1 = -b0; j1 <= b0; ++j1) {
					for (int k1 = -b0; k1 <= b0; ++k1) {
						BlockPos blockpos1 = blockpos.add(i1, j1, k1);
						IBlockState iblockstate = world.getBlockState(blockpos1);
						if (iblockstate.getBlock().getMaterial() == Material.leaves
								&& !((Boolean) iblockstate.getValue(CHECK_DECAY)).booleanValue()) {
							world.setBlockState(blockpos1, iblockstate.withProperty(CHECK_DECAY, Boolean.valueOf(true)),
									4);
						}
					}
				}
			}
		}

	}

	public void randomDisplayTick(World world, BlockPos blockpos, IBlockState var3, EaglercraftRandom random) {
		if (world.canLightningStrike(blockpos.up()) && !World.doesBlockHaveSolidTopSurface(world, blockpos.down())
				&& random.nextInt(15) == 1) {
			double d0 = (double) ((float) blockpos.getX() + random.nextFloat());
			double d1 = (double) blockpos.getY() - 0.05D;
			double d2 = (double) ((float) blockpos.getZ() + random.nextFloat());
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}

	}

	private void destroy(World worldIn, BlockPos pos) {
		this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
		worldIn.setBlockToAir(pos);
	}

	/**+
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(EaglercraftRandom random) {
		return random.nextInt(20) == 0 ? 1 : 0;
	}

	/**+
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState var1, EaglercraftRandom var2, int var3) {
		return Item.getItemFromBlock(Blocks.sapling);
	}

	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
	}

	protected int getSaplingDropChance(IBlockState state) {
		return 20;
	}

	/**+
	 * Used to determine ambient occlusion and culling when
	 * rebuilding chunks for render
	 */
	public boolean isOpaqueCube() {
		return !this.fancyGraphics;
	}

	/**+
	 * Pass true to draw this block using fancy graphics, or false
	 * for fast graphics.
	 */
	public void setGraphicsLevel(boolean fancy) {
		this.isTransparent = fancy;
		this.fancyGraphics = fancy;
		this.iconIndex = fancy ? 0 : 1;
	}

	public EnumWorldBlockLayer getBlockLayer() {
		return this.isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
	}

	public boolean isVisuallyOpaque() {
		return false;
	}

	public abstract BlockPlanks.EnumType getWoodType(int var1);
}