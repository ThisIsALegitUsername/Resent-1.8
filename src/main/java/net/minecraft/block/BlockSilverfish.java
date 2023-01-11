package net.minecraft.block;

import java.util.List;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
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
public class BlockSilverfish extends Block {
	public static PropertyEnum<BlockSilverfish.EnumType> VARIANT;

	public BlockSilverfish() {
		super(Material.clay);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockSilverfish.EnumType.STONE));
		this.setHardness(0.0F);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	public static void bootstrapStates() {
		VARIANT = PropertyEnum.<BlockSilverfish.EnumType>create("variant", BlockSilverfish.EnumType.class);
	}

	/**+
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(EaglercraftRandom var1) {
		return 0;
	}

	public static boolean canContainSilverfish(IBlockState blockState) {
		Block block = blockState.getBlock();
		return blockState == Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE)
				|| block == Blocks.cobblestone || block == Blocks.stonebrick;
	}

	protected ItemStack createStackedBlock(IBlockState iblockstate) {
		switch ((BlockSilverfish.EnumType) iblockstate.getValue(VARIANT)) {
		case COBBLESTONE:
			return new ItemStack(Blocks.cobblestone);
		case STONEBRICK:
			return new ItemStack(Blocks.stonebrick);
		case MOSSY_STONEBRICK:
			return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.MOSSY.getMetadata());
		case CRACKED_STONEBRICK:
			return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CRACKED.getMetadata());
		case CHISELED_STONEBRICK:
			return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CHISELED.getMetadata());
		default:
			return new ItemStack(Blocks.stone);
		}
	}

	public int getDamageValue(World world, BlockPos blockpos) {
		IBlockState iblockstate = world.getBlockState(blockpos);
		return iblockstate.getBlock().getMetaFromState(iblockstate);
	}

	/**+
	 * returns a list of blocks with the same ID, but different meta
	 * (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (BlockSilverfish.EnumType blocksilverfish$enumtype : BlockSilverfish.EnumType.values()) {
			list.add(new ItemStack(item, 1, blocksilverfish$enumtype.getMetadata()));
		}

	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(VARIANT, BlockSilverfish.EnumType.byMetadata(i));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((BlockSilverfish.EnumType) iblockstate.getValue(VARIANT)).getMetadata();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT });
	}

	public static enum EnumType implements IStringSerializable {
		STONE(0, "stone") {
			public IBlockState getModelBlock() {
				return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE);
			}
		},
		COBBLESTONE(1, "cobblestone", "cobble") {
			public IBlockState getModelBlock() {
				return Blocks.cobblestone.getDefaultState();
			}
		},
		STONEBRICK(2, "stone_brick", "brick") {
			public IBlockState getModelBlock() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT,
						BlockStoneBrick.EnumType.DEFAULT);
			}
		},
		MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick") {
			public IBlockState getModelBlock() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT,
						BlockStoneBrick.EnumType.MOSSY);
			}
		},
		CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick") {
			public IBlockState getModelBlock() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT,
						BlockStoneBrick.EnumType.CRACKED);
			}
		},
		CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick") {
			public IBlockState getModelBlock() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT,
						BlockStoneBrick.EnumType.CHISELED);
			}
		};

		private static final BlockSilverfish.EnumType[] META_LOOKUP = new BlockSilverfish.EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;

		private EnumType(int meta, String name) {
			this(meta, name, name);
		}

		private EnumType(int meta, String name, String unlocalizedName) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.meta;
		}

		public String toString() {
			return this.name;
		}

		public static BlockSilverfish.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		public String getName() {
			return this.name;
		}

		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}

		public abstract IBlockState getModelBlock();

		public static BlockSilverfish.EnumType forModelBlock(IBlockState model) {
			for (BlockSilverfish.EnumType blocksilverfish$enumtype : values()) {
				if (model == blocksilverfish$enumtype.getModelBlock()) {
					return blocksilverfish$enumtype;
				}
			}

			return STONE;
		}

		static {
			for (BlockSilverfish.EnumType blocksilverfish$enumtype : values()) {
				META_LOOKUP[blocksilverfish$enumtype.getMetadata()] = blocksilverfish$enumtype;
			}

		}
	}
}