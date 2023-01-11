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
public class BlockSandStone extends Block {
	public static PropertyEnum<BlockSandStone.EnumType> TYPE;

	public BlockSandStone() {
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockSandStone.EnumType.DEFAULT));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public static void bootstrapStates() {
		TYPE = PropertyEnum.<BlockSandStone.EnumType>create("type", BlockSandStone.EnumType.class);
	}

	/**+
	 * Gets the metadata of the item this Block can drop. This
	 * method is called when the block gets destroyed. It returns
	 * the metadata of the dropped item based on the old metadata of
	 * the block.
	 */
	public int damageDropped(IBlockState iblockstate) {
		return ((BlockSandStone.EnumType) iblockstate.getValue(TYPE)).getMetadata();
	}

	/**+
	 * returns a list of blocks with the same ID, but different meta
	 * (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (BlockSandStone.EnumType blocksandstone$enumtype : BlockSandStone.EnumType.values()) {
			list.add(new ItemStack(item, 1, blocksandstone$enumtype.getMetadata()));
		}

	}

	/**+
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState var1) {
		return MapColor.sandColor;
	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(TYPE, BlockSandStone.EnumType.byMetadata(i));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((BlockSandStone.EnumType) iblockstate.getValue(TYPE)).getMetadata();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}

	public static enum EnumType implements IStringSerializable {
		DEFAULT(0, "sandstone", "default"), CHISELED(1, "chiseled_sandstone", "chiseled"),
		SMOOTH(2, "smooth_sandstone", "smooth");

		private static final BlockSandStone.EnumType[] META_LOOKUP = new BlockSandStone.EnumType[values().length];
		private final int metadata;
		private final String name;
		private final String unlocalizedName;

		private EnumType(int meta, String name, String unlocalizedName) {
			this.metadata = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.metadata;
		}

		public String toString() {
			return this.name;
		}

		public static BlockSandStone.EnumType byMetadata(int meta) {
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
			for (BlockSandStone.EnumType blocksandstone$enumtype : values()) {
				META_LOOKUP[blocksandstone$enumtype.getMetadata()] = blocksandstone$enumtype;
			}

		}
	}
}