package net.minecraft.block;

import java.util.List;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
public class BlockStone extends Block {
	public static PropertyEnum<BlockStone.EnumType> VARIANT;

	public BlockStone() {
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockStone.EnumType.STONE));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public static void bootstrapStates() {
		VARIANT = PropertyEnum.<BlockStone.EnumType>create("variant", BlockStone.EnumType.class);
	}

	/**+
	 * Gets the localized name of this block. Used for the
	 * statistics page.
	 */
	public String getLocalizedName() {
		return StatCollector.translateToLocal(
				this.getUnlocalizedName() + "." + BlockStone.EnumType.STONE.getUnlocalizedName() + ".name");
	}

	/**+
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState iblockstate) {
		return ((BlockStone.EnumType) iblockstate.getValue(VARIANT)).func_181072_c();
	}

	/**+
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState iblockstate, EaglercraftRandom var2, int var3) {
		return iblockstate.getValue(VARIANT) == BlockStone.EnumType.STONE ? Item.getItemFromBlock(Blocks.cobblestone)
				: Item.getItemFromBlock(Blocks.stone);
	}

	/**+
	 * Gets the metadata of the item this Block can drop. This
	 * method is called when the block gets destroyed. It returns
	 * the metadata of the dropped item based on the old metadata of
	 * the block.
	 */
	public int damageDropped(IBlockState iblockstate) {
		return ((BlockStone.EnumType) iblockstate.getValue(VARIANT)).getMetadata();
	}

	/**+
	 * returns a list of blocks with the same ID, but different meta
	 * (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (BlockStone.EnumType blockstone$enumtype : BlockStone.EnumType.values()) {
			list.add(new ItemStack(item, 1, blockstone$enumtype.getMetadata()));
		}

	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(VARIANT, BlockStone.EnumType.byMetadata(i));
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((BlockStone.EnumType) iblockstate.getValue(VARIANT)).getMetadata();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT });
	}

	public static enum EnumType implements IStringSerializable {
		STONE(0, MapColor.stoneColor, "stone"), GRANITE(1, MapColor.dirtColor, "granite"),
		GRANITE_SMOOTH(2, MapColor.dirtColor, "smooth_granite", "graniteSmooth"),
		DIORITE(3, MapColor.quartzColor, "diorite"),
		DIORITE_SMOOTH(4, MapColor.quartzColor, "smooth_diorite", "dioriteSmooth"),
		ANDESITE(5, MapColor.stoneColor, "andesite"),
		ANDESITE_SMOOTH(6, MapColor.stoneColor, "smooth_andesite", "andesiteSmooth");

		private static final BlockStone.EnumType[] META_LOOKUP = new BlockStone.EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		private final MapColor field_181073_l;

		private EnumType(int parInt2, MapColor parMapColor, String parString2) {
			this(parInt2, parMapColor, parString2, parString2);
		}

		private EnumType(int parInt2, MapColor parMapColor, String parString2, String parString3) {
			this.meta = parInt2;
			this.name = parString2;
			this.unlocalizedName = parString3;
			this.field_181073_l = parMapColor;
		}

		public int getMetadata() {
			return this.meta;
		}

		public MapColor func_181072_c() {
			return this.field_181073_l;
		}

		public String toString() {
			return this.name;
		}

		public static BlockStone.EnumType byMetadata(int meta) {
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
			for (BlockStone.EnumType blockstone$enumtype : values()) {
				META_LOOKUP[blockstone$enumtype.getMetadata()] = blockstone$enumtype;
			}

		}
	}
}