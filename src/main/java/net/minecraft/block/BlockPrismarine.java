package net.minecraft.block;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;

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
public class BlockPrismarine extends Block {
	public static PropertyEnum<BlockPrismarine.EnumType> VARIANT;
	public static final int ROUGH_META = BlockPrismarine.EnumType.ROUGH.getMetadata();
	public static final int BRICKS_META = BlockPrismarine.EnumType.BRICKS.getMetadata();
	public static final int DARK_META = BlockPrismarine.EnumType.DARK.getMetadata();

	public BlockPrismarine() {
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPrismarine.EnumType.ROUGH));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public static void bootstrapStates() {
		VARIANT = PropertyEnum.<BlockPrismarine.EnumType>create("variant", BlockPrismarine.EnumType.class);
	}

	/**+
	 * Gets the localized name of this block. Used for the
	 * statistics page.
	 */
	public String getLocalizedName() {
		return StatCollector.translateToLocal(
				this.getUnlocalizedName() + "." + BlockPrismarine.EnumType.ROUGH.getUnlocalizedName() + ".name");
	}

	/**+
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState iblockstate) {
		return iblockstate.getValue(VARIANT) == BlockPrismarine.EnumType.ROUGH ? MapColor.cyanColor
				: MapColor.diamondColor;
	}

	/**+
	 * Gets the metadata of the item this Block can drop. This
	 * method is called when the block gets destroyed. It returns
	 * the metadata of the dropped item based on the old metadata of
	 * the block.
	 */
	public int damageDropped(IBlockState iblockstate) {
		return ((BlockPrismarine.EnumType) iblockstate.getValue(VARIANT)).getMetadata();
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((BlockPrismarine.EnumType) iblockstate.getValue(VARIANT)).getMetadata();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT });
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(VARIANT, BlockPrismarine.EnumType.byMetadata(i));
	}

	/**+
	 * returns a list of blocks with the same ID, but different meta
	 * (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs var2, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, ROUGH_META));
		list.add(new ItemStack(item, 1, BRICKS_META));
		list.add(new ItemStack(item, 1, DARK_META));
	}

	public static enum EnumType implements IStringSerializable {
		ROUGH(0, "prismarine", "rough"), BRICKS(1, "prismarine_bricks", "bricks"), DARK(2, "dark_prismarine", "dark");

		private static final BlockPrismarine.EnumType[] META_LOOKUP = new BlockPrismarine.EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;

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

		public static BlockPrismarine.EnumType byMetadata(int meta) {
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

		static {
			for (BlockPrismarine.EnumType blockprismarine$enumtype : values()) {
				META_LOOKUP[blockprismarine$enumtype.getMetadata()] = blockprismarine$enumtype;
			}

		}
	}
}